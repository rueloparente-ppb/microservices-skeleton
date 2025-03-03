truncate table orders cascade;
alter sequence order_id_seq restart with 100;
alter sequence order_item_id_seq restart with 100;

insert into orders (id,order_number,username,
                    customer_name,customer_email,customer_phone,
                    delivery_address_line1,delivery_address_line2,delivery_address_city,
                    delivery_address_state,delivery_address_zip_code,delivery_address_country,
                    status,comments) values
                                         (1, 'order-123', 'user', 'ruelo', 'ruelo@gmail.com', '11111111', '123 Main St', 'Apt 1', 'Dallas', 'TX', '75001', 'USA', 'NEW', null),
                                         (2, 'order-456', 'user', 'parente', 'parente@gmail.com', '2222222', '123 Main St', 'Apt 1', 'Hyderabad', 'TS', '500072', 'USA', 'NEW', null)
;

insert into order_items (order_id, code, name, price, quantity) values
                                         (1, 'item-123', 'item-123', 10.00, 1),
                                         (1, 'item-456', 'item-456', 20.00, 2),
                                         (2, 'item-789', 'item-789', 30.00, 3);