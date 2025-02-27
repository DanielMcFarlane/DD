-- Insert Users
INSERT INTO users (username, first_name, last_name, email, password, role, restaurant_id) VALUES
--admins
('admin','Daniel', 'McFarlane', 'admin@daniel.com', 'password', 'Admin', NULL),
('jd123','John', 'Doe', 'john.doe@example.com', 'hashed_password', 'admin', NULL),
--staff
('staffPP','Owner', 'Pizza Place', 'owner@pizza.com', 'hashed_password', 'staff', 1),
('staffBH','Owner', 'Burger House', 'owner@bhouse.com', 'hashed_password', 'staff', 2),
('staff1','John', 'Doe', 'john1.doe@example.com', 'password', 'Staff', 1),
('staff2','Alice', 'Smith', 'alice@example.com', 'password', 'Staff', 2),
('staff3','Billy', 'Bob', 'billy@example.com', 'password', 'Staff', 3),
('ron','Rons Quality', 'Snacks', 'ron@ronsqualitysnacks.com', 'password', 'Staff', 4),
--customers
('daniel', 'Daniel' , 'McFarlane', 'daniel@daniel.com', 'password', 'Customer', NULL),
--quick test
('1', 'Test' , 'Admin', 'test@admin.com', '1', 'Admin', NULL),
('2', 'Test' , 'Staff', 'test1@staff.com', '2', 'Staff', 2),
('3', 'Test' , 'Customer', 'test@customer.com', '3', 'Customer', NULL),
('4', 'Test' , 'Staff', 'test2@staff.com',  '4', 'Staff', 4);

-- Insert Restaurants
INSERT INTO restaurants (name, cuisine) VALUES
('La Bella Italia', 'Italian'),
('Sakura Sushi Bar', 'Japanese'),
('Spice Route', 'Indian'),
('Rons Quality Snacks', 'Banging');

-- Insert Menus
INSERT INTO menus (restaurant_id, name, description) VALUES
(1, 'Italian Menu', 'Traditional Italian dishes'),
(2, 'Sushi Menu', 'Authentic Japanese sushi'),
(3, 'Indian Menu', 'A variety of classic Indian dishes');

-- Insert Menu Items (Food with serving_size)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Margherita Pizza', 7.99, 'Food', 'Tomato, mozzarella, and basil', 'Single Portion', NULL),
(1, 'Spaghetti Carbonara', 9.99, 'Food', 'Classic Italian pasta with creamy sauce and pancetta', 'Family Size', NULL),
(2, 'Salmon Nigiri', 6.99, 'Food', 'Fresh salmon on vinegared rice', 'Single Portion', NULL),
(2, 'Tuna Sashimi', 7.99, 'Food', 'Fresh tuna served raw', 'Family Size', NULL),
(3, 'Chicken Tikka Masala', 8.99, 'Food', 'Tender chicken in a rich, spiced curry', 'Single Portion', NULL),
(3, 'Vegetable Biryani', 11.99, 'Food', 'Flavourful rice with mixed vegetables and spices', 'Family Size', NULL);

-- Insert Menu Items (Drinks with is_alcoholic)
INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES
(1, 'Chianti', 9.99, 'Drink', 'Rich Italian red wine', '750ml', TRUE),
(1, 'Sparkling Water', 2.50, 'Drink', 'Refreshing sparkling water', '500ml', FALSE),
(2, 'Sake', 12.99, 'Drink', 'Traditional Japanese rice wine', '300ml', TRUE),
(2, 'Green Tea', 2.99, 'Drink', 'Freshly brewed Japanese green tea', '500ml', FALSE),
(3, 'Mango Lassi', 1.99, 'Drink', 'Sweet and creamy yoghurt-based drink', '500ml', FALSE),
(3, 'Kingfisher Beer', 3.50, 'Drink', 'Popular Indian lager', '650ml', TRUE);