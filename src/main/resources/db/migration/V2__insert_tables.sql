INSERT INTO category (name) VALUES ('Electronics'), ('Books'), ('Clothing');

INSERT INTO product (name, description, price, image_url, category_id) VALUES
                                                                           ('Laptop', 'High performance laptop', 999.99, 'image1.jpeg', 1),
                                                                           ('Smartphone', 'Latest model smartphone', 599.99, 'image1.jpeg', 1),
                                                                           ('Novel', 'Bestselling novel', 19.99, 'image1.jpeg', 2),
                                                                           ('Laptop', 'High performance laptop', 999.99, 'image1.jpeg', 1),
                                                                           ('Smartphone', 'Latest model smartphone', 599.99, 'image1.jpeg', 1),
                                                                           ('Novel', 'Bestselling novel', 19.99, 'image1.jpeg', 2),
                                                                           ('Laptop', 'High performance laptop', 999.99, 'image1.jpeg', 1),
                                                                           ('Smartphone', 'Latest model smartphone', 599.99, 'image1.jpeg', 1),
                                                                           ('Novel', 'Bestselling novel', 19.99, 'image1.jpeg', 2),
                                                                           ('Laptop', 'High performance laptop', 999.99, 'image1.jpeg', 1),
                                                                           ('Smartphone', 'Latest model smartphone', 599.99, 'image1.jpeg', 1),
                                                                           ('Novel', 'Bestselling novel', 19.99, 'image1.jpeg', 2),
                                                                           ('Laptop', 'High performance laptop', 999.99, 'image1.jpeg', 1),
                                                                           ('Smartphone', 'Latest model smartphone', 599.99, 'image1.jpeg', 1),
                                                                           ('Novel', 'Bestselling novel', 19.99, 'image1.jpeg', 2);

-- Sample user (password is 'password' encoded with BCrypt, use a tool to generate)
INSERT INTO user (username, password, email, roles) VALUES
    ('user', '$2a$10$examplehashedpasswordhere', 'user@example.com', 'ROLE_USER');