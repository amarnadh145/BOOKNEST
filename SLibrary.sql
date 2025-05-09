-- Create the database
CREATE DATABASE IF NOT EXISTS SLibrary;
USE SLibrary;

-- Create admins table (updated from 'users' table)
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Create category table
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    catname VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'Active' NOT NULL
);

-- Create author table
CREATE TABLE IF NOT EXISTS author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    phone VARCHAR(10) NOT NULL
);

-- Create publisher table
CREATE TABLE IF NOT EXISTS publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    phone VARCHAR(10) NOT NULL
);

-- Create book table
CREATE TABLE IF NOT EXISTS book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category INT NOT NULL,
    author INT NOT NULL,
    publisher INT NOT NULL,
    contents TEXT,
    pages VARCHAR(50),
    edition VARCHAR(50),
    FOREIGN KEY (category) REFERENCES category(id) ON UPDATE CASCADE,
    FOREIGN KEY (author) REFERENCES author(id) ON UPDATE CASCADE,
    FOREIGN KEY (publisher) REFERENCES publisher(id) ON UPDATE CASCADE
);

-- Create member table (removed status field as it's not used in the application)
CREATE TABLE IF NOT EXISTS member (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(10) NOT NULL,
    address TEXT NOT NULL
);

-- Create lendbook table (for book issues)
CREATE TABLE IF NOT EXISTS lendbook (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookid INT NOT NULL,
    memberid VARCHAR(10) NOT NULL,
    issuedate DATE NOT NULL,
    returndate DATE NOT NULL,
    FOREIGN KEY (bookid) REFERENCES book(id) ON UPDATE CASCADE,
    FOREIGN KEY (memberid) REFERENCES member(id) ON UPDATE CASCADE
);

-- Create returnbook table with proper member_id field
CREATE TABLE IF NOT EXISTS returnbook (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(10) NOT NULL,
    membername VARCHAR(255) NOT NULL,
    bookname VARCHAR(255) NOT NULL,
    returndate DATE NOT NULL,
    elap INT NOT NULL DEFAULT 0,
    fine DECIMAL(10,2) NOT NULL DEFAULT 0.00
);


