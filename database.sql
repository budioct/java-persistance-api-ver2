CREATE DATABASE belajar_spring_data_jpa;

USE belajar_spring_data_jpa;

CREATE TABLE customers
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

ALTER TABLE customers
    ADD COLUMN primary_email VARCHAR(150);

ALTER TABLE customers
    ADD COLUMN age TINYINT;

ALTER TABLE customers
    ADD COLUMN married BOOLEAN;

ALTER TABLE customers
    ADD COLUMN type VARCHAR(50);

CREATE TABLE categories
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

SELECT *
FROM categories;

ALTER TABLE categories
    ADD COLUMN description VARCHAR(500);

ALTER TABLE categories
    ADD COLUMN create_at TIMESTAMP;

ALTER TABLE categories
    ADD COLUMN update_at TIMESTAMP;

ALTER TABLE categories
    ADD COLUMN created_date TIMESTAMP;

ALTER TABLE categories
    ADD COLUMN last_modified_date TIMESTAMP;

CREATE TABLE images
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    image       BLOB
) ENGINE = InnoDB;

ALTER TABLE images
    RENAME COLUMN NAME TO name;

CREATE TABLE products
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    price       BIGINT       NOT NULL,
    category_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_products_categories (category_id) REFERENCES categories (id)
) ENGINE InnoDB;

SELECT *
FROM products;

DELETE
FROM products
WHERE name = 'Samsung Galaxy S9';

CREATE TABLE members
(
    id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(150) NOT NULL,
    title      VARCHAR(100),
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
) ENGINE InnoDB;

ALTER TABLE members
    ADD COLUMN middle_name VARCHAR(100) AFTER first_name;


CREATE TABLE departments
(
    company_id    VARCHAR(100) NOT NULL,
    department_id VARCHAR(100) NOT NULL,
    name          VARCHAR(150) NOT NULL,
    PRIMARY KEY (company_id, department_id)
) ENGINE InnoDB;

CREATE TABLE hobbies
(
    id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id INT          NOT NULL,
    name      VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_members_hobbies (member_id)
        REFERENCES members (id)
) ENGINE InnoDB;

CREATE TABLE skills
(
    id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id INT          NOT NULL,
    name      VARCHAR(100) NOT NULL,
    value     INT          NOT NULL,
    FOREIGN KEY fk_members_skills (member_id)
        REFERENCES members (id),
    CONSTRAINT skills_unique UNIQUE (member_id, name)
) ENGINE InnoDB;


select *
from customers;

select *
from members;

select *
from categories;

CREATE TABLE credentials
(
    id       VARCHAR(100) NOT NULL PRIMARY KEY,
    email    VARCHAR(150) NOT NULL,
    password VARCHAR(150) NOT NULL
) ENGINE InnoDB;

CREATE TABLE users
(
    id   VARCHAR(100) NOT NULL PRIMARY KEY,
    name VARCHAR(150) NOT NULL
) ENGINE InnoDB;

select *
from users
         inner join credentials on users.id = credentials.id;

CREATE TABLE wallets
(
    id      INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    balance BIGINT       NOT NULL,
    FOREIGN KEY fk_users_wallet (user_id) REFERENCES users (id)
) ENGINE InnoDB;

select *
from wallets;
select *
from users
         inner join wallets w on users.id = w.user_id;

CREATE TABLE brands
(
    id          VARCHAR(100) NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500)
) ENGINE InnoDB;

select *
from brands;

CREATE TABLE products
(
    id          VARCHAR(100) NOT NULL PRIMARY KEY,
    brand_id    VARCHAR(100) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    price       BIGINT       NOT NULL,
    description VARCHAR(500),
    FOREIGN KEY fk_brands_products (brand_id) REFERENCES brands (id)
) ENGINE InnoDB;

select *
from products;
select *
from brands;

CREATE TABLE users_like_products
(
    user_id    VARCHAR(100) NOT NULL,
    product_id VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_users_users_like_products (user_id) REFERENCES users (id),
    FOREIGN KEY fk_products_users_like_products (product_id) REFERENCES products (id),
    PRIMARY KEY (user_id, product_id)
) ENGINE InnoDB;

select *
from users;
select *
from products;
select *
from users_like_products;
select *
from customers;

# single table inheritance
CREATE TABLE employees
(
    id             VARCHAR(100) NOT NULL PRIMARY KEY,
    type           VARCHAR(50)  NOT NULL,
    name           VARCHAR(100) NOT NULL,
    total_manager  INT,
    total_employee INT
) ENGINE InnoDB;

select *
from employees

# join table inheritance
CREATE TABLE payments
(
    id     VARCHAR(100) NOT NULL PRIMARY KEY,
    amount BIGINT       NOT NULL
) ENGINE InnoDB;

select *
from payments;

CREATE TABLE payments_credit_card
(
    id          VARCHAR(100) NOT NULL PRIMARY KEY,
    masked_card VARCHAR(100) NOT NULL,
    bank        VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_payments_credit_card (id) REFERENCES payments (id)
) ENGINE InnoDB;

select *
from payments_credit_card;

CREATE TABLE payments_gopay
(
    id       VARCHAR(100) NOT NULL PRIMARY KEY,
    gopay_id VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_payments_gopay (id) REFERENCES payments (id)
) ENGINE InnoDB;

select *
from payments_gopay;

# table per class inheritance
CREATE TABLE transactions
(
    id        VARCHAR(100) NOT NULL PRIMARY KEY,
    balance   BIGINT       NOT NULL,
    create_at TIMESTAMP    NOT NULL
) ENGINE InnoDB;

select * from transactions;

CREATE TABLE transactions_creadit
(
    id            VARCHAR(100) NOT NULL PRIMARY KEY,
    balance       BIGINT       NOT NULL,
    create_at     TIMESTAMP    NOT NULL,
    credit_amount BIGINT       NOT NULL
) ENGINE InnoDB;

select * from transactions_creadit;

CREATE TABLE transactions_debit
(
    id           VARCHAR(100) NOT NULL PRIMARY KEY,
    balance      BIGINT       NOT NULL,
    create_at    TIMESTAMP    NOT NULL,
    debit_amount BIGINT       NOT NULL
) ENGINE InnoDB;

select * from transactions_debit;

# LOCKING
select * from brands;
select * from products;
select * from members;

alter table brands
    add column create_at TIMESTAMP;

alter table brands
    add column update_at TIMESTAMP;

ALTER TABLE brands
    add column version BIGINT;

# Schema Generator
CREATE DATABASE belajar_spring_data_jpa_test;
USE belajar_spring_data_jpa_test;

USE belajar_spring_data_jpa;
