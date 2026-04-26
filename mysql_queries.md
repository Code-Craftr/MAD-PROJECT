# Backend MySQL Queries

This document contains all the MySQL queries used by the QManage backend for handling application logic, as well as the setup schema.

## 1. Application Logic Queries (from Controllers)

### User Authentication (`userController.js`)
**Check if a user already exists (Registration)**
```sql
SELECT id FROM users WHERE email = ?
```

**Insert a new user (Registration)**
```sql
INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)
```

**Find user by email and password (Login)**
```sql
SELECT id, name, email, phone FROM users WHERE email = ? AND password = ?
```

### Outlets & Menus (`outletController.js`)
**Get all outlets, ordered by open status and rating**
```sql
SELECT * FROM outlets ORDER BY is_open DESC, rating DESC
```

**Get available menu items for a specific outlet**
```sql
SELECT * FROM menu_items WHERE outlet_id = ? AND is_available = true
```

### Order Management (`orderController.js`)
*(Note: These queries are executed within a MySQL transaction)*

**Insert a new order**
```sql
INSERT INTO orders (user_id, outlet_id, total_amount, token_number) VALUES (?, ?, ?, ?)
```

**Insert order items for an order**
```sql
INSERT INTO order_items (order_id, menu_item_id, quantity, price) VALUES (?, ?, ?, ?)
```

**Get all orders for a specific user (with outlet details)**
```sql
SELECT o.*, ot.name as outletName, ot.image_url as outletImage 
FROM orders o 
JOIN outlets ot ON o.outlet_id = ot.id 
WHERE o.user_id = ? 
ORDER BY o.created_at DESC
```

---

## 2. Database Schema & Setup Queries (`database/setup.sql`)

### Database Creation
```sql
CREATE DATABASE IF NOT EXISTS Qmanage;
USE Qmanage;
```

### Table Definitions
**Users Table**
```sql
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL DEFAULT '',
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_users_email (email)
);
```

**Outlets Table**
```sql
CREATE TABLE IF NOT EXISTS outlets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    categories VARCHAR(255) DEFAULT '',
    rating DECIMAL(2, 1) DEFAULT 0.0,
    wait_time_minutes INT UNSIGNED DEFAULT 0,
    queue_count INT UNSIGNED DEFAULT 0,
    image_res_name VARCHAR(100) DEFAULT 'placeholder_food',
    image_url VARCHAR(500) DEFAULT '',
    is_open BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_outlets_name (name),
    INDEX idx_outlets_open (is_open)
);
```

**Menu Items Table**
```sql
CREATE TABLE IF NOT EXISTS menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    outlet_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(100) DEFAULT '',
    is_veg BOOLEAN DEFAULT TRUE,
    image_res_name VARCHAR(100) DEFAULT 'placeholder_food',
    image_url VARCHAR(500) DEFAULT '',
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_menu_item_outlet_name (outlet_id, name),
    INDEX idx_menu_items_outlet_id (outlet_id),
    INDEX idx_menu_items_category (category),
    FOREIGN KEY (outlet_id) REFERENCES outlets(id) ON DELETE CASCADE
);
```

**Orders Table**
```sql
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    outlet_id INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Received', 'Preparing', 'Ready', 'Completed', 'Cancelled') DEFAULT 'Received',
    token_number VARCHAR(20) DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_orders_token_number (token_number),
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_outlet_id (outlet_id),
    INDEX idx_orders_status (status),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (outlet_id) REFERENCES outlets(id) ON DELETE CASCADE
);
```

**Order Items Table**
```sql
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10, 2) NOT NULL,
    UNIQUE KEY uk_order_item_unique (order_id, menu_item_id),
    INDEX idx_order_items_order_id (order_id),
    INDEX idx_order_items_menu_item_id (menu_item_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE
);
```

**Reviews Table**
```sql
CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    outlet_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (outlet_id) REFERENCES outlets(id) ON DELETE CASCADE
);
```

### Initial Data Seeding
**Sample Outlets**
```sql
INSERT INTO outlets (name, categories, rating, wait_time_minutes, queue_count, image_res_name, is_open) VALUES
('Campus Cafe', 'Coffee • Snacks • Beverages', 4.5, 5, 3, 'placeholder_food', true),
('Food Court', 'Rolls • Indian • Fried', 4.2, 12, 8, 'placeholder_food', true),
('Juice Bar', 'Juices • Smoothies • Shakes', 4.7, 3, 2, 'placeholder_food', true),
('Quick Bites', 'Sandwiches • Burgers • Wraps', 4.0, 8, 5, 'placeholder_food', false)
ON DUPLICATE KEY UPDATE
    categories = VALUES(categories),
    rating = VALUES(rating),
    wait_time_minutes = VALUES(wait_time_minutes),
    queue_count = VALUES(queue_count),
    image_res_name = VALUES(image_res_name),
    is_open = VALUES(is_open);
```

**Sample Menu Items**
```sql
INSERT INTO menu_items (outlet_id, name, description, price, category, is_veg, image_res_name, is_available) VALUES
((SELECT id FROM outlets WHERE name = 'Campus Cafe'), 'Cappuccino', 'Rich and creamy cappuccino', 120.00, 'Beverages', true, 'placeholder_food', true),
((SELECT id FROM outlets WHERE name = 'Campus Cafe'), 'Veg Sandwich', 'Fresh veggies with cheese', 80.00, 'Snacks', true, 'placeholder_food', true),
((SELECT id FROM outlets WHERE name = 'Food Court'), 'Paneer Roll', 'Spicy paneer stuffed roll', 100.00, 'Rolls', true, 'placeholder_food', true),
((SELECT id FROM outlets WHERE name = 'Food Court'), 'Chicken Biryani', 'Aromatic chicken biryani', 180.00, 'Main Course', false, 'placeholder_food', true),
((SELECT id FROM outlets WHERE name = 'Juice Bar'), 'Mango Smoothie', 'Fresh mango smoothie', 90.00, 'Smoothies', true, 'placeholder_food', true),
((SELECT id FROM outlets WHERE name = 'Quick Bites'), 'Veg Burger', 'Classic veg burger with fries', 110.00, 'Burgers', true, 'placeholder_food', true)
ON DUPLICATE KEY UPDATE
    description = VALUES(description),
    price = VALUES(price),
    category = VALUES(category),
    is_veg = VALUES(is_veg),
    image_res_name = VALUES(image_res_name),
    is_available = VALUES(is_available);
```
