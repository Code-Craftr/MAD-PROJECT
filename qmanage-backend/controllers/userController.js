const db = require('../config/db');

// POST /api/users/register
const registerUser = async (req, res) => {
    try {
        const { name, email, password, phone } = req.body;

        // Validate required fields
        if (!email || !password) {
            return res.status(400).json({
                success: false,
                message: 'Email and password are required'
            });
        }

        // Check if user already exists
        const [existingUsers] = await db.query(
            'SELECT id FROM users WHERE email = ?',
            [email]
        );

        if (existingUsers.length > 0) {
            return res.status(409).json({
                success: false,
                message: 'User with this email already exists'
            });
        }

        // Insert new user
        const [result] = await db.query(
            'INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)',
            [name || '', email, password, phone || '']
        );

        res.status(201).json({
            success: true,
            message: 'User registered successfully',
            user: {
                id: result.insertId,
                name: name || '',
                email: email,
                phone: phone || ''
            }
        });

    } catch (error) {
        console.error('Register Error:', error.message);
        res.status(500).json({
            success: false,
            message: 'Server error during registration'
        });
    }
};

// POST /api/users/login
const loginUser = async (req, res) => {
    try {
        const { email, password } = req.body;

        // Validate required fields
        if (!email || !password) {
            return res.status(400).json({
                success: false,
                message: 'Email and password are required'
            });
        }

        // Find user by email and password
        const [users] = await db.query(
            'SELECT id, name, email, phone FROM users WHERE email = ? AND password = ?',
            [email, password]
        );

        if (users.length === 0) {
            return res.status(401).json({
                success: false,
                message: 'Invalid email or password'
            });
        }

        const user = users[0];
        res.status(200).json({
            success: true,
            message: 'Login successful',
            user: {
                id: user.id,
                name: user.name,
                email: user.email,
                phone: user.phone
            }
        });

    } catch (error) {
        console.error('Login Error:', error.message);
        res.status(500).json({
            success: false,
            message: 'Server error during login'
        });
    }
};

module.exports = { registerUser, loginUser };
