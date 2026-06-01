
CREATE DATABASE IF NOT EXISTS PhoneStoreDB;
USE PhoneStoreDB;


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