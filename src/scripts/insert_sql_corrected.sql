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
('3', 'Test' , 'Customer', 'test@customer.com', '3', 'Customer', NULL),
('4', 'Test' , 'Staff', 'test2@staff.com',  '4', 'Staff', 6);

-- Insert Restaurants
INSERT INTO restaurants (name, cuisine) VALUES
('Pizza Place', 'Italian'),
('Burger House', 'American'),
('La Bella Italia', 'Italian'),
('Sakura Sushi Bar', 'Japanese'),
('Spice Route', 'Indian'),
('Rons Quality Snacks', 'Banging');

-- Insert Menus
INSERT INTO menus (restaurant_id, name, description) VALUES
(1, 'Pizza Menu', 'A variety of classic and gourmet pizzas'),
(2, 'Burger Menu', 'Delicious burgers with fresh ingredients'),
(3, 'Italian Menu', 'Traditional Italian dishes'),
(4, 'Sushi Menu', 'Authentic Japanese sushi'),
(5, 'Indian Menu', 'A variety of classic Indian dishes');

-- Insert Menu Items (Food with serving_size)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Margherita Pizza', 10.99, 'Food', 'Tomato, mozzarella, and basil', 'Large', NULL),
(2, 'Cheeseburger', 8.99, 'Food', 'Juicy burger with melted cheese', 'Medium', NULL),
(3, 'Spaghetti Carbonara', 9.99, 'Food', 'Classic Italian pasta with creamy sauce and pancetta', 'Family Size', NULL),
(4, 'Salmon Nigiri', 6.99, 'Food', 'Fresh salmon on vinegared rice', 'Single Portion', NULL),
(5, 'Chicken Tikka Masala', 8.99, 'Food', 'Tender chicken in a rich, spiced curry', 'Single Portion', NULL);

-- Insert Menu Items (Drinks with is_alcoholic)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Coca Cola', 2.50, 'Drink', 'Chilled soft drink', '500ml', FALSE),
(2, 'Red Wine', 12.99, 'Drink', 'Rich and full-bodied red wine', '750ml', TRUE),
(3, 'Chianti', 9.99, 'Drink', 'Rich Italian red wine', '750ml', TRUE),
(4, 'Sake', 12.99, 'Drink', 'Traditional Japanese rice wine', '300ml', TRUE),
(5, 'Kingfisher Beer', 3.50, 'Drink', 'Popular Indian lager', '650ml', TRUE);