-- Insert Users
INSERT INTO users (username, first_name, last_name, email, password, role, restaurant_id) VALUES
--admins
('admin','Daniel', 'McFarlane', 'admin@daniel.com', 'password', 'Admin', NULL),
('jd123','John', 'Doe', 'john.doe@example.com', 'hashed_password', 'admin', NULL),
--staff
('staffPP','Owner', 'Pizza Place', 'owner@pizza.com', 'hashed_password', 'staff', 1),
('staffBH','Owner', 'Burger House', 'owner@bhouse.com', 'hashed_password', 'staff', 2),
('staff1','John', 'Doe', 'john1.doe@example.com', 'password', 'Staff', 3),
('staff2','Alice', 'Smith', 'alice@example.com', 'password', 'Staff', 4),
('staff3','Billy', 'Bob', 'billy@example.com', 'password', 'Staff', 5),
('ron','Rons Quality', 'Snacks', 'ron@ronsqualitysnacks.com', 'password', 'Staff', 6),
--customers
('daniel', 'Daniel' , 'McFarlane', 'daniel@daniel.com', 'password', 'Customer', NULL),
--quick test
('1', 'Test' , 'Admin', 'test@admin.com', '1', 'Admin', NULL),
('2', 'Test' , 'Staff', 'test1@staff.com', '2', 'Staff', 4),
('3', 'Test' , 'Customer', 'test@customer.com', '3', 'Customer', NULL);

-- Insert Restaurants
INSERT INTO restaurants (name, cuisine, image_url) VALUES
('Pizza Place', 'Italian', 'src/images/pizzaplace.jpg'),
('Burger House', 'American', 'src/images/burgerhouse.jpg'),
('La Bella Italia', 'Italian', 'src/images/labellaitalia.jpg'),
('Sakura Sushi Bar', 'Japanese', 'src/images/sakurasushi.jpg'),
('Spice Route', 'Indian', 'src/images/spiceroute.jpg'),
('Rons Quality Snacks', 'Banging', 'src/images/ronsqualitysnacks.jpg');

-- Insert Menus
INSERT INTO menus (restaurant_id, name, description) VALUES
(1, 'Pizza Menu', 'A variety of classic and gourmet pizzas'),
(2, 'Burger Menu', 'Delicious burgers with fresh ingredients'),
(3, 'Italian Menu', 'Traditional Italian dishes'),
(4, 'Sushi Menu', 'Authentic Japanese sushi'),
(5, 'Indian Menu', 'A variety of classic Indian dishes'),
(6, 'Rons Menu', 'Rons Quality Snacks');

-- Insert Menu Items (Food)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Margherita Pizza', 10.99, 'Food', 'Tomato, mozzarella, and basil', 'Large', NULL),
(2, 'Cheeseburger', 8.99, 'Food', 'Juicy burger with melted cheese', 'Medium', NULL),
(3, 'Margherita Pizza', 7.99, 'Food', 'Tomato, mozzarella, and basil', 'Single Portion', NULL),
(3, 'Spaghetti Carbonara', 9.99, 'Food', 'Classic Italian pasta with creamy sauce and pancetta', 'Family Size', NULL),
(4, 'Tuna Sashimi', 7.99, 'Food', 'Fresh tuna served raw', 'Family Size', NULL),
(4, 'Salmon Nigiri', 6.99, 'Food', 'Fresh salmon on sushi rice', 'Single Portion', NULL),
(5, 'Chicken Tikka Masala', 8.99, 'Food', 'Tender chicken in a rich, spiced curry', 'Single Portion', NULL),
(5, 'Vegetable Biryani', 11.99, 'Food', 'Flavourful rice with mixed vegetables and spices', 'Family Size', NULL),
(6, 'Boston Belly Buster', 4.50, 'Food', 'Sausage, bacon, egg, burger and potato scone all in one roll', 'Single Portion', NULL),
(6, 'Tiktok Special', 5.00, 'Food', 'A sub roll filled with griddle fried chicken, seasonings, topped with BBQ pulled pork and crispy onions', 'Single Portion', NULL);

-- Insert Menu Items (Drinks)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Coca Cola', 2.50, 'Drink', 'Chilled soft drink', '500ml', FALSE),
(2, 'Red Wine', 12.99, 'Drink', 'Rich and full-bodied red wine', '750ml', TRUE),
(3, 'Chianti', 9.99, 'Drink', 'Rich Italian red wine', '750ml', TRUE),
(3, 'Sparkling Water', 2.50, 'Drink', 'Refreshing sparkling water', '500ml', FALSE),
(4, 'Sake', 12.99, 'Drink', 'Traditional Japanese rice wine', '300ml', TRUE),
(4, 'Green Tea', 2.99, 'Drink', 'Freshly brewed Japanese green tea', '500ml', FALSE),
(5, 'Mango Lassi', 1.99, 'Drink', 'Sweet and creamy yoghurt-based drink', '500ml', FALSE),
(5, 'Kingfisher Beer', 3.50, 'Drink', 'Popular Indian lager', '650ml', TRUE),
(6, 'Irn Bru', 2.00, 'Drink', 'Refreshing Scottish soft drink', '500ml', FALSE),
(6, 'Tea', 1.50, 'Drink', 'Hot freshly brewed tea', '500ml', FALSE);