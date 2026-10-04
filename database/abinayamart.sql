-- ============================================================
-- AbinayaMart Database (MySQL 8+)
-- Developer: Abinaya | J.J College of Engineering and Technology
-- ============================================================
CREATE DATABASE IF NOT EXISTS abinayamart
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE abinayamart;

-- Users: BUYER / SELLER / ADMIN
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_role CHECK (role IN ('BUYER','SELLER','ADMIN'))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  seller_id INT NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT,
  category VARCHAR(80) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_products_seller FOREIGN KEY (seller_id)
    REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT chk_price CHECK (price >= 0),
  CONSTRAINT chk_stock CHECK (stock >= 0),
  INDEX idx_products_name (name),
  INDEX idx_products_category (category)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  buyer_id INT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PLACED',
  payment_mode VARCHAR(20) NOT NULL DEFAULT 'COD',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_buyer FOREIGN KEY (buyer_id)
    REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS order_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  qty INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_items_order FOREIGN KEY (order_id)
    REFERENCES orders(id) ON DELETE CASCADE,
  CONSTRAINT fk_items_product FOREIGN KEY (product_id)
    REFERENCES products(id),
  CONSTRAINT chk_qty CHECK (qty > 0)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reviews (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL,
  buyer_id INT NOT NULL,
  rating INT NOT NULL,
  comment VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reviews_product FOREIGN KEY (product_id)
    REFERENCES products(id) ON DELETE CASCADE,
  CONSTRAINT fk_reviews_buyer FOREIGN KEY (buyer_id)
    REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT chk_rating CHECK (rating BETWEEN 1 AND 5),
  UNIQUE KEY uq_review (product_id, buyer_id)
) ENGINE=InnoDB;

-- Seed users (plain-text passwords for college demo; use hashing in production)
INSERT INTO users (name, email, password, role) VALUES
  ('Abinaya Admin',  'admin@abinayamart',  'admin123',  'ADMIN'),
  ('Demo Seller',    'seller@abinayamart', 'seller123', 'SELLER'),
  ('Demo Buyer',     'buyer@abinayamart',  'buyer123',  'BUYER')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Seed products (seller id = 2, works when auto-increment starts at 1)
INSERT INTO products (seller_id, name, description, category, price, stock) VALUES
  (2, 'Cotton T-Shirt', 'Comfortable cotton t-shirt', 'Fashion', 499.00, 50),
  (2, 'Running Shoes', 'Lightweight running shoes', 'Footwear', 1999.00, 30),
  (2, 'Smartphone X1', '6GB RAM, 128GB storage phone', 'Electronics', 14999.00, 15),
  (2, 'Cooker 5L', 'Pressure cooker 5 litre', 'Home & Kitchen', 1899.00, 25),
  (2, 'Java Programming Book', 'Learn Java step by step', 'Books', 599.00, 100)
ON DUPLICATE KEY UPDATE name = VALUES(name);
