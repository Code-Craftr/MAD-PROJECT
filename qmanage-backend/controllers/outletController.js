const db = require('../config/db');

// GET /api/outlets
const getAllOutlets = async (req, res) => {
    try {
        const [outlets] = await db.query('SELECT * FROM outlets ORDER BY is_open DESC, rating DESC');
        res.json({ success: true, outlets });
    } catch (error) {
        console.error('Get Outlets Error:', error.message);
        res.status(500).json({ success: false, message: 'Error fetching outlets' });
    }
};

// GET /api/outlets/:id/menu
const getOutletMenu = async (req, res) => {
    try {
        const { id } = req.params;
        const [menuItems] = await db.query(
            'SELECT * FROM menu_items WHERE outlet_id = ? AND is_available = true',
            [id]
        );
        res.json({ success: true, menuItems });
    } catch (error) {
        console.error('Get Menu Error:', error.message);
        res.status(500).json({ success: false, message: 'Error fetching menu items' });
    }
};

module.exports = { getAllOutlets, getOutletMenu };
