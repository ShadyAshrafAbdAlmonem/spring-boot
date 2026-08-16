-- Sample data for Inventory Management System
-- This script will run automatically when the application starts (spring.sql.init.mode=always)

-- Insert Categories
INSERT IGNORE INTO categories (id, name, description, created_at, updated_at) VALUES
(1, 'Electronics', 'Electronic devices and accessories', NOW(), NOW()),
(2, 'Clothing', 'Apparel and fashion items', NOW(), NOW()),
(3, 'Food & Beverages', 'Food products and drinks', NOW(), NOW()),
(4, 'Home & Garden', 'Home improvement and garden supplies', NOW(), NOW()),
(5, 'Sports', 'Sports equipment and accessories', NOW(), NOW());

-- Insert Brands
INSERT IGNORE INTO brands (id, name, description, created_at, updated_at) VALUES
(1, 'Apple', 'Apple Inc. - Technology company', NOW(), NOW()),
(2, 'Samsung', 'Samsung Electronics', NOW(), NOW()),
(3, 'Nike', 'Nike Inc. - Sportswear', NOW(), NOW()),
(4, 'Adidas', 'Adidas AG - Sportswear', NOW(), NOW()),
(5, 'Sony', 'Sony Corporation', NOW(), NOW());

-- Insert Sample Users (password: Admin123! encoded with BCrypt)
INSERT IGNORE INTO users (id, username, email, password, first_name, last_name, phone, active, created_at, updated_at) VALUES
(1, 'admin', 'admin@example.com', '$2a$10$rH7H7z7H7H7H7H7H7H7H7O', 'Admin', 'User', '+1234567890', true, NOW(), NOW()),
(2, 'manager', 'manager@example.com', '$2a$10$rH7H7z7H7H7H7H7H7H7H7O', 'Inventory', 'Manager', '+1234567891', true, NOW(), NOW()),
(3, 'user', 'user@example.com', '$2a$10$rH7H7z7H7H7H7H7H7H7H7O', 'Test', 'User', '+1234567892', true, NOW(), NOW());

-- Insert Roles
INSERT IGNORE INTO roles (id, name, description, created_at, updated_at) VALUES
(1, 'ROLE_ADMIN', 'Administrator role', NOW(), NOW()),
(2, 'ROLE_MANAGER', 'Inventory Manager role', NOW(), NOW()),
(3, 'ROLE_USER', 'Regular user role', NOW(), NOW());

-- Insert User-Role mappings
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Insert Warehouses
INSERT IGNORE INTO warehouses (id, name, location, capacity, created_at, updated_at) VALUES
(1, 'Main Warehouse', 'Building A, Floor 1', 10000, NOW(), NOW()),
(2, 'Secondary Warehouse', 'Building B, Floor 2', 5000, NOW(), NOW());

-- Insert Sample Products
INSERT IGNORE INTO products (id, name, sku, description, quantity, min_quantity, price, category_id, brand_id, warehouse_id, active, expiry_date, created_at, updated_at) VALUES
(1, 'iPhone 15 Pro', 'IPHONE-15-PRO-001', 'Latest Apple iPhone with A17 Pro chip', 50, 10, 999.99, 1, 1, 1, true, '2025-12-31', NOW(), NOW()),
(2, 'Samsung Galaxy S24', 'SAMSUNG-S24-001', 'Samsung flagship smartphone', 30, 8, 899.99, 1, 2, 1, true, '2025-12-31', NOW(), NOW()),
(3, 'Nike Air Max 270', 'NIKE-AM270-001', 'Nike Air Max 270 running shoes', 100, 20, 150.00, 2, 3, 1, true, '2026-06-30', NOW(), NOW()),
(4, 'Adidas Ultraboost', 'ADIDAS-UB-001', 'Adidas Ultraboost 22', 75, 15, 180.00, 2, 4, 1, true, '2026-06-30', NOW(), NOW()),
(5, 'Sony WH-1000XM5', 'SONY-XM5-001', 'Sony wireless noise-canceling headphones', 25, 5, 349.99, 1, 5, 1, true, '2026-12-31', NOW(), NOW()),
(6, 'MacBook Pro 16"', 'MACBOOK-PRO-16', 'Apple MacBook Pro with M3 chip', 15, 3, 2499.99, 1, 1, 1, true, '2026-12-31', NOW(), NOW()),
(7, 'Samsung 65" TV', 'SAMSUNG-TV-65', 'Samsung 65 inch 4K Smart TV', 10, 2, 1299.99, 1, 2, 2, true, '2027-12-31', NOW(), NOW()),
(8, 'Nike Dri-FIT T-Shirt', 'NIKE-TSHIRT-001', 'Nike Dri-FIT short sleeve t-shirt', 200, 50, 35.00, 2, 3, 1, true, '2027-12-31', NOW(), NOW()),
(9, 'Organic Coffee Beans', 'COFFEE-ORGANIC', 'Premium organic coffee beans 1kg', 150, 30, 25.00, 3, 1, 2, true, '2025-06-30', NOW(), NOW()),
(10, 'Garden Tool Set', 'GARDEN-TOOL-001', 'Complete garden tool set with bag', 40, 10, 89.99, 4, 2, 2, true, '2028-12-31', NOW(), NOW());

-- Insert Inventory Records
INSERT IGNORE INTO inventory (id, product_id, quantity, location, created_at, updated_at) VALUES
(1, 1, 50, 'A1-Shelf-01', NOW(), NOW()),
(2, 2, 30, 'A1-Shelf-02', NOW(), NOW()),
(3, 3, 100, 'A2-Shelf-01', NOW(), NOW()),
(4, 4, 75, 'A2-Shelf-02', NOW(), NOW()),
(5, 5, 25, 'A3-Shelf-01', NOW(), NOW()),
(6, 6, 15, 'A3-Shelf-02', NOW(), NOW()),
(7, 7, 10, 'B1-Shelf-01', NOW(), NOW()),
(8, 8, 200, 'B2-Shelf-01', NOW(), NOW()),
(9, 9, 150, 'C1-Shelf-01', NOW(), NOW()),
(10, 10, 40, 'C2-Shelf-01', NOW(), NOW());

-- Insert Addresses
INSERT IGNORE INTO addresses (id, user_id, street, city, state, postal_code, country, created_at, updated_at) VALUES
(1, 1, '123 Admin Street', 'New York', 'NY', '10001', 'USA', NOW(), NOW()),
(2, 2, '456 Manager Ave', 'Los Angeles', 'CA', '90001', 'USA', NOW(), NOW()),
(3, 3, '789 User Blvd', 'Chicago', 'IL', '60601', 'USA', NOW(), NOW());

-- Insert Sample Suppliers
INSERT IGNORE INTO suppliers (id, name, contact_email, contact_phone, address, created_at, updated_at) VALUES
(1, 'Apple Inc.', 'supply@apple.com', '+1-800-APL-CARE', 'Cupertino, CA, USA', NOW(), NOW()),
(2, 'Samsung Electronics', 'supply@samsung.com', '+82-2-2255-0114', 'Seoul, South Korea', NOW(), NOW()),
(3, 'Nike Inc.', 'supply@nike.com', '+1-503-671-6453', 'Beaverton, OR, USA', NOW(), NOW());

-- Insert Activity Logs
INSERT IGNORE INTO activity_logs (id, user_id, action, entity_type, entity_id, description, created_at) VALUES
(1, 1, 'CREATE', 'PRODUCT', 1, 'Created product iPhone 15 Pro', NOW()),
(2, 1, 'CREATE', 'PRODUCT', 2, 'Created product Samsung Galaxy S24', NOW()),
(3, 1, 'UPDATE', 'PRODUCT', 1, 'Updated quantity of iPhone 15 Pro to 50', NOW()),
(4, 2, 'CREATE', 'CATEGORY', 1, 'Created category Electronics', NOW()),
(5, 3, 'VIEW', 'PRODUCT', 1, 'Viewed product iPhone 15 Pro', NOW());

-- Insert Sample Coupons
INSERT IGNORE INTO coupons (id, code, description, discount_type, discount_value, min_purchase, max_discount, valid_from, valid_until, active, created_at, updated_at) VALUES
(1, 'WELCOME10', '10% off for new users', 'PERCENTAGE', 10.00, 50.00, 20.00, '2024-01-01', '2024-12-31', true, NOW(), NOW()),
(2, 'SAVE20', 'Save $20 on orders over $100', 'FIXED', 20.00, 100.00, 20.00, '2024-01-01', '2024-12-31', true, NOW(), NOW()),
(3, 'BULK50', '50% off on bulk orders', 'PERCENTAGE', 50.00, 200.00, 100.00, '2024-01-01', '2024-06-30', true, NOW(), NOW());

-- Insert Sample Settings
INSERT IGNORE INTO settings (id, setting_key, setting_value, description, created_at, updated_at) VALUES
(1, 'app.name', 'Inventory Management System', 'Application name', NOW(), NOW()),
(2, 'app.version', '1.0.0', 'Application version', NOW(), NOW()),
(3, 'email.notifications.enabled', 'true', 'Enable email notifications', NOW(), NOW()),
(4, 'low.stock.threshold', '10', 'Default low stock threshold', NOW(), NOW()),
(5, 'tax.rate', '0.10', 'Default tax rate (10%)', NOW(), NOW());

-- Insert Sample Currencies
INSERT IGNORE INTO currencies (id, code, name, symbol, exchange_rate, created_at, updated_at) VALUES
(1, 'USD', 'US Dollar', '$', 1.0000, NOW(), NOW()),
(2, 'EUR', 'Euro', '€', 0.9200, NOW(), NOW()),
(3, 'GBP', 'British Pound', '£', 0.7900, NOW(), NOW());

-- Insert Sample Payment Methods
INSERT IGNORE INTO payment_methods (id, name, description, active, created_at, updated_at) VALUES
(1, 'Credit Card', 'Visa, Mastercard, Amex', true, NOW(), NOW()),
(2, 'PayPal', 'PayPal payment', true, NOW(), NOW()),
(3, 'Bank Transfer', 'Direct bank transfer', true, NOW(), NOW());

-- Insert Sample Shipment Methods
INSERT IGNORE INTO shipment_methods (id, name, description, cost, estimated_days, active, created_at, updated_at) VALUES
(1, 'Standard Shipping', 'Standard ground shipping', 5.99, 5, true, NOW(), NOW()),
(2, 'Express Shipping', 'Express delivery', 15.99, 2, true, NOW(), NOW()),
(3, 'Next Day Air', 'Next day delivery', 29.99, 1, true, NOW(), NOW());

-- Insert Sample Tax Rates
INSERT IGNORE INTO tax_rates (id, name, rate, country, state, active, created_at, updated_at) VALUES
(1, 'US Sales Tax', 0.10, 'USA', 'CA', true, NOW(), NOW()),
(2, 'EU VAT', 0.20, 'EU', NULL, true, NOW(), NOW()),
(3, 'UK VAT', 0.20, 'UK', NULL, true, NOW(), NOW());

-- Insert Sample Discounts
INSERT IGNORE INTO discounts (id, name, description, discount_type, value, min_quantity, start_date, end_date, active, created_at, updated_at) VALUES
(1, 'Summer Sale', 'Summer season discount', 'PERCENTAGE', 15.00, 1, '2024-06-01', '2024-08-31', true, NOW(), NOW()),
(2, 'Winter Sale', 'Winter season discount', 'PERCENTAGE', 20.00, 1, '2024-12-01', '2025-02-28', true, NOW(), NOW()),
(3, 'Bulk Discount', 'Discount for bulk purchases', 'PERCENTAGE', 25.00, 10, '2024-01-01', '2024-12-31', true, NOW(), NOW());

-- Insert Sample Customers
INSERT IGNORE INTO customers (id, user_id, first_name, last_name, email, phone, total_orders, total_spent, created_at, updated_at) VALUES
(1, 3, 'Test', 'User', 'user@example.com', '+1234567892', 0, 0.00, NOW(), NOW()),
(2, NULL, 'John', 'Doe', 'john.doe@example.com', '+1234567893', 5, 1250.00, NOW(), NOW()),
(3, NULL, 'Jane', 'Smith', 'jane.smith@example.com', '+1234567894', 3, 750.00, NOW(), NOW());

-- Insert Sample Product Images
INSERT IGNORE INTO product_images (id, product_id, image_url, alt_text, is_primary, created_at, updated_at) VALUES
(1, 1, 'https://example.com/images/iphone15pro.jpg', 'iPhone 15 Pro', true, NOW(), NOW()),
(2, 2, 'https://example.com/images/galaxys24.jpg', 'Samsung Galaxy S24', true, NOW(), NOW()),
(3, 3, 'https://example.com/images/nike-air-max.jpg', 'Nike Air Max 270', true, NOW(), NOW()),
(4, 4, 'https://example.com/images/adidas-ultraboost.jpg', 'Adidas Ultraboost', true, NOW(), NOW()),
(5, 5, 'https://example.com/images/sony-xm5.jpg', 'Sony WH-1000XM5', true, NOW(), NOW());

-- Insert Sample Favorites
INSERT IGNORE INTO favorites (id, user_id, product_id, created_at) VALUES
(1, 3, 1, NOW()),
(2, 3, 3, NOW()),
(3, 3, 5, NOW());

-- Insert Sample Reviews
INSERT IGNORE INTO reviews (id, product_id, user_id, rating, comment, created_at, updated_at) VALUES
(1, 1, 3, 5, 'Excellent phone! Best iPhone ever.', NOW(), NOW()),
(2, 3, 3, 4, 'Great shoes, very comfortable.', NOW(), NOW()),
(3, 5, 3, 5, 'Best noise-canceling headphones!', NOW(), NOW());

-- Insert Sample Wishlist Items
INSERT IGNORE INTO wishlists (id, user_id, product_id, created_at) VALUES
(1, 3, 2, NOW()),
(2, 3, 6, NOW()),
(3, 3, 7, NOW());

-- Success message
SELECT 'Sample data inserted successfully!' AS message;