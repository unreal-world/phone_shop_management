
-- CREATE DATABASE IF NOT EXISTS PhoneStoreDB;
-- USE PhoneStoreDB;


CREATE TABLE User (
    userID VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phoneNumber VARCHAR(15),
    role ENUM('CUSTOMER', 'ADMIN') NOT NULL
);


CREATE TABLE Address (
    addressID VARCHAR(50) PRIMARY KEY,
    userID VARCHAR(50),
    city VARCHAR(50),
    ward VARCHAR(50),
    street VARCHAR(100),
    houseNumber VARCHAR(20),
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);


CREATE TABLE Product (
    productID VARCHAR(50) PRIMARY KEY,
    productName VARCHAR(200) NOT NULL,
    brand VARCHAR(100),
    price DOUBLE NOT NULL,
    stock_quantity INT DEFAULT 0,
    description TEXT,
    isDeleted BOOLEAN DEFAULT FALSE
);


CREATE TABLE Image (
    imageID VARCHAR(50) PRIMARY KEY,
    productID VARCHAR(50) UNIQUE,
    imageSource LONGTEXT,
    FOREIGN KEY (productID) REFERENCES Product(productID) ON DELETE CASCADE
);


CREATE TABLE Cart (
    cartID VARCHAR(50) PRIMARY KEY,
    userID VARCHAR(50) UNIQUE,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);


CREATE TABLE CartItem (
    cartItemID VARCHAR(50) PRIMARY KEY,
    cartID VARCHAR(50),
    productID VARCHAR(50),
    quantity INT NOT NULL,
    FOREIGN KEY (cartID) REFERENCES Cart(cartID) ON DELETE CASCADE,
    FOREIGN KEY (productID) REFERENCES Product(productID)
);


CREATE TABLE `Order` (
    orderID VARCHAR(50) PRIMARY KEY,
    userID VARCHAR(50),
    orderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    orderStatus ENUM('PROCESSING', 'DELIVERING', 'DELIVERED', 'CANCELLED') NOT NULL,
    addressID VARCHAR(50),
    receiver VARCHAR(100),
    phoneNumber VARCHAR(15),
    discount DOUBLE DEFAULT 0.0,
    finalTotal DOUBLE DEFAULT 0.0,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (addressID) REFERENCES Address(addressID)
);


CREATE TABLE OrderDetail (
    orderDetailID VARCHAR(50) PRIMARY KEY,
    orderID VARCHAR(50),
    productID VARCHAR(50),
    quantity INT NOT NULL,
    unitPrice DOUBLE NOT NULL,
    totalPrice DOUBLE NOT NULL,
    FOREIGN KEY (orderID) REFERENCES `Order`(orderID) ON DELETE CASCADE,
    FOREIGN KEY (productID) REFERENCES Product(productID)
);

-- ========================================================
-- DỮ LIỆU KHỞI TẠO MỚI (CHỈNH SỬA TÙY Ý TRƯỚC KHI IMPORT)
-- ========================================================

-- 1. Tài khoản Admin mặc định
-- Đăng nhập bằng tài khoản: admin / admin123
INSERT INTO User (userID, username, password, fullName, email, phoneNumber, role) VALUES 
('admin-default-id', 'admin', 'admin123', 'Administrator', 'admin@phonestore.com', '0987654321', 'ADMIN');

-- B?ng luu token d?t l?i m?t kh?u (Qu�n m?t kh?u)
CREATE TABLE IF NOT EXISTS PasswordResetToken (
    tokenID     VARCHAR(50) PRIMARY KEY,
    token       VARCHAR(100) NOT NULL UNIQUE,
    userID      VARCHAR(50) NOT NULL,
    expiryDate  DATETIME NOT NULL,
    isUsed      BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userID) REFERENCES User(userID) ON DELETE CASCADE
);

-- 2. Du lieu san pham mau (Sample Products)
INSERT INTO Product (productID, productName, brand, price, stock_quantity, description, isDeleted) VALUES
('prod-001', 'Xiaomi Redmi Note 13 6GB/128GB', 'Xiaomi', 4290000, 45, 'Man hinh AMOLED 120Hz, Snapdragon 685, pin 5000mAh', 0),
('prod-002', 'Samsung Galaxy A15 8GB/128GB', 'Samsung', 4490000, 50, 'Man hinh Super AMOLED, Helio G99, pin 5000mAh', 0),
('prod-003', 'Vivo V30e 8GB/256GB', 'Vivo', 8490000, 22, 'Thiet ke sieu mong, camera vong sang Aura, Snapdragon 6 Gen 1', 0),
('prod-004', 'OPPO Reno 11 5G 8GB/256GB', 'OPPO', 9990000, 18, 'Chuyen gia chan dung, sac nhanh SuperVOOC 67W', 0),
('prod-005', 'iPhone 13 128GB', 'Apple', 13490000, 30, 'Chip A15 Bionic manh me, thiet ke vien nhom sang trong', 0),
('prod-006', 'Xiaomi 14 12GB/256GB', 'Xiaomi', 18490000, 25, 'Ong kinh Leica cao cap, Snapdragon 8 Gen 3 dinh cao', 0),
('prod-007', 'Samsung Galaxy S24 Ultra 256GB', 'Samsung', 26990000, 20, 'Khung vien Titan, Galaxy AI thong minh, but S-Pen', 0),
('prod-008', 'iPhone 15 Pro Max 256GB', 'Apple', 29990000, 15, 'Khung vien Titan, chip A17 Pro, camera zoom quang hoc 5x', 0);

INSERT INTO Image (imageID, productID, imageSource) VALUES
('img-001', 'prod-001', 'https://cdn.tgdd.vn/Products/Images/42/309831/xiaomi-redmi-note-13-den-thumb-600x600.jpg'),
('img-002', 'prod-002', 'https://cdn.tgdd.vn/Products/Images/42/319326/samsung-galaxy-a15-xanh-thumb-600x600.jpg'),
('img-003', 'prod-003', 'https://cdn.tgdd.vn/Products/Images/42/323380/vivo-v30e-nau-thumb-600x600.jpg'),
('img-004', 'prod-004', 'https://cdn.tgdd.vn/Products/Images/42/313366/oppo-reno11-5g-xanh-thumb-600x600.jpg'),
('img-005', 'prod-005', 'https://cdn.tgdd.vn/Products/Images/42/250258/iphone-13-starlight-thumb-600x600.jpg'),
('img-006', 'prod-006', 'https://cdn.tgdd.vn/Products/Images/42/321899/xiaomi-14-black-thumb-600x600.jpg'),
('img-007', 'prod-007', 'https://cdn.tgdd.vn/Products/Images/42/307174/samsung-galaxy-s24-ultra-xam-thumb-600x600.jpg'),
('img-008', 'prod-008', 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-titan-tu-nhien-thumb-600x600.jpg');
