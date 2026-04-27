const db = require('./config/db');

async function checkTable() {
    try {
        const [columns] = await db.query('DESCRIBE menu_items');
        console.log('Columns in menu_items:');
        console.table(columns);
        process.exit(0);
    } catch (error) {
        console.error('Error:', error.message);
        process.exit(1);
    }
}

checkTable();
