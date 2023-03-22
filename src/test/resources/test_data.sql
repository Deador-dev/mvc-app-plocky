INSERT INTO roles(id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO user(id, email, first_name, last_name, password, activation_code, is_activated)
VALUES (1, 'admin@gmail.com', 'admin', 'admin',
        '$2a$10$LMGEnMQ8b/ndBvf.qks7aOWJsmXCnV04BlcqfK9NI0Cd/XmLhhn5C', 'Confirmed', true),
       (2, 'not_activated_user@gmail.com', 'user1', 'user1',
        '$2a$10$45XfEkSM2oYoL7gp5gaPDuoESI7N2vjK5R8r1B8Fdvy3Ne5.IIgbK', '98022005-c59b-4dee-bb9f-d092873216f0', false),
       (3, 'user@gmail.com', 'user2', 'user2',
        '$2a$10$4oxB/A0NA99v7LM/Mc07vu8O3vhY5kmJy4bEvUHQWXx98bRTpf9P6', 'Confirmed', true);

INSERT INTO user_role(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2);

INSERT INTO category(category_id, name)
VALUES (1, 'Samsung'),
       (2, 'Xiaomi'),
       (3, 'iPhone');

INSERT INTO product(id, battery, bluetooth, brand, connectivity, count_of_views, description,
                    display_resolution, front_camera_resolution, image_name, main_camera_resolution, matrix_type, model,
                    name, nfc, number_of_cores, number_of_front_cameras, number_of_main_cameras, number_of_sim_cards,
                    operating_system, price, price_after_action, price_before_action, processor, ram, screen_size,
                    storage_capacity, weight, category_id)
VALUES (1, '4000', 'Bluetooth 5.0', 'Samsung', '5G', 10, 'Description samsung', '2532x1170', '12',
        '68b33de1-de09-4505-8a5d-ec152a62ef90.023-03-14 151359.png', '12', 'OLED', 'Galaxy S23',
        'Samsung Galaxy S23', 'Yes', '2+4', '1', '3', '2', 'Android', 999.0, 899.0, 999.0,
        'Snapdragon 5.3', '32', '6.1', '128', 110.0, 1),
       (2, '4200', 'Bluetooth 5.0', 'Xiaomi', '5G', 200, 'Description xiaomi', '2532x1170', '16',
        'b451c356-b1e1-48ac-97ea-fdf9b00fa7ea.some.png', '20', 'OLED', 'Xiaomi Redmi 9',
        'Xiaomi Redmi 9', 'Yes', '2+4', '1', '2', '2', 'Android', 600.0, 550.0, 600.0,
        'Snapdragon 5.3', '16', '6.1', '128', 100.0, 2);

INSERT INTO cart(id, price, quantity, user_id)
VALUES (1, 999.0, 1, 1),
       (2, 1599.0, 2, 3);

INSERT INTO cart_item(id, cart_id, product_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2);

INSERT INTO orders(id, additional_information, address, delivery_status, email, first_name, last_name, phone_number,
                   postcode, total_amount, town_city, user_id)
VALUES (1, 'Some information', 'Lvivska street', 'In progress', 'admin@gmail.com', 'admin', 'admin', '+380985743829',
        '749123', 600.0, 'Lviv', 1),
       (2, 'Some information', 'Bankova street', 'Delivered', 'user@gmail.com', 'user2', 'user2', '+380987329340',
        '587902', 1599.0, 'Kyiv', 3);

INSERT INTO order_item(id, order_id, product_id)
VALUES (1, 1, 2),
       (2, 2, 1),
       (3, 2, 2);