-- Qmanage Database Setup Script
-- Run this in MySQL Workbench or MySQL CLI

-- Create database
CREATE DATABASE IF NOT EXISTS Qmanage;
USE Qmanage;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL DEFAULT '',
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Outlets table
CREATE TABLE IF NOT EXISTS outlets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    categories VARCHAR(255) DEFAULT '',
    rating FLOAT DEFAULT 0.0,
    wait_time VARCHAR(50) DEFAULT '',
    queue_count VARCHAR(50) DEFAULT '',
    image_url VARCHAR(500) DEFAULT '',
    is_open BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu items table
CREATE TABLE IF NOT EXISTS menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    outlet_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT DEFAULT '',
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(100) DEFAULT '',
    image_url VARCHAR(500) DEFAULT '',
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (outlet_id) REFERENCES outlets(id) ON DELETE CASCADE
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    outlet_id INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('Received', 'Preparing', 'Ready', 'Completed', 'Cancelled') DEFAULT 'Received',
    token_number VARCHAR(20) DEFAULT '',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (outlet_id) REFERENCES outlets(id) ON DELETE CASCADE
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE
);

-- Insert sample outlets
INSERT INTO outlets (name, categories, rating, wait_time, queue_count, image_url, is_open) VALUES
('Campus Cafe', 'Coffee • Snacks • Beverages', 4.5, '5 mins wait', '3 in queue', '', true),
('Food Court', 'Rolls • Indian • Fried', 4.2, '12 mins wait', '8 in queue', '', true),
('Juice Bar', 'Juices • Smoothies • Shakes', 4.7, '3 mins wait', '2 in queue', '', true),
('Quick Bites', 'Sandwiches • Burgers • Wraps', 4.0, '8 mins wait', '5 in queue', '', false);

-- Insert sample menu items
INSERT INTO menu_items (outlet_id, name, description, price, category, image_url, is_available) VALUES
-- Campus Cafe items
(1, 'Cappuccino', 'Rich and creamy cappuccino', 120.00, 'Beverages', '', true),
(1, 'Latte', 'Smooth cafe latte', 140.00, 'Beverages', '', true),
(1, 'Veg Sandwich', 'Fresh veggies with cheese', 80.00, 'Snacks', '', true),
(1, 'Chocolate Muffin', 'Freshly baked chocolate muffin', 60.00, 'Snacks', '', true),
-- Food Court items
(2, 'Paneer Roll', 'Spicy paneer stuffed roll', 100.00, 'Rolls', '', true),
(2, 'Chicken Biryani', 'Aromatic chicken biryani', 180.00, 'Main Course', '', true),
(2, 'Veg Thali', 'Complete veg meal', 150.00, 'Main Course', '', true),
(2, 'French Fries', 'Crispy golden fries', 70.00, 'Sides & Extras', '', true),
-- Juice Bar items
(3, 'Mango Smoothie', 'Fresh mango smoothie', 90.00, 'Smoothies', '', true),
(3, 'Orange Juice', 'Freshly squeezed orange juice', 60.00, 'Juices', '', true),
(3, 'Banana Shake', 'Creamy banana milkshake', 80.00, 'Shakes', '', true),
-- Quick Bites items
(4, 'Veg Burger', 'Classic veg burger with fries', 110.00, 'Burgers', '', true),
(4, 'Chicken Wrap', 'Grilled chicken wrap', 130.00, 'Wraps', '', true),
(4, 'Club Sandwich', 'Triple decker club sandwich', 150.00, 'Sandwiches', '', true);

SELECT 'Database setup completed successfully!' AS status;
