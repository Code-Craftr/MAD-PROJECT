const { OAuth2Client } = require('google-auth-library');
const db = require('../config/db');
require('dotenv').config();

const client = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);

const googleLogin = async (req, res) => {
    try {
        const { idToken } = req.body;

        if (!idToken) {
            return res.status(400).json({ success: false, message: 'ID Token is required' });
        }

        // Verify Google ID Token
        const ticket = await client.verifyIdToken({
            idToken: idToken,
            audience: process.env.GOOGLE_CLIENT_ID,
        });

        const payload = ticket.getPayload();
        const { email, name, picture, sub: googleId } = payload;

        // Check if user exists in our DB
        const [users] = await db.query('SELECT * FROM users WHERE email = ?', [email]);

        let user;
        if (users.length === 0) {
            // Register new user
            const [result] = await db.query(
                'INSERT INTO users (name, email, password) VALUES (?, ?, ?)',
                [name, email, 'google-auth-' + googleId] // Placeholder password
            );
            user = { id: result.insertId, name, email };
        } else {
            user = users[0];
        }

        res.json({
            success: true,
            message: 'Google login successful',
            user: {
                id: user.id,
                name: user.name,
                email: user.email
            }
        });

    } catch (error) {
        console.error('Google Login Error:', error.message);
        res.status(401).json({ success: false, message: 'Invalid Google token' });
    }
};

module.exports = { googleLogin };
