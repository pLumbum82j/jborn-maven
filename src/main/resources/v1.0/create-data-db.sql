insert into users(first_name, last_name, login, email, password) VALUES ('Илья', 'Смирнов', 'pb82', 'pb82@mail.ru', 'c4ca4238a0b923820dcc509a6f75849b');
insert into users(first_name, last_name, login, email, password) VALUES ('Наталья', 'Смирнова', 'nata', 'natastar@mail.ru', 'c4ca4238a0b923820dcc509a6f75849b');
insert into users(first_name, last_name, login, email, password) VALUES ('Иван', 'Штепа', 'shtepa', 'shtepa87@yandex.ru', 'c4ca4238a0b923820dcc509a6f75849b');
insert into users(first_name, last_name, login, email, password) VALUES ('Павел', 'Цемежевский', 'pahan', 'pasha66@gmail.com', 'c4ca4238a0b923820dcc509a6f75849b');

insert into spending_category(category_name) VALUES ('Свой Перевод');
insert into spending_category(category_name) VALUES ('Продукты');
insert into spending_category(category_name) VALUES ('Зарплата');
insert into spending_category(category_name) VALUES ('Автомобиль');
insert into spending_category(category_name) VALUES ('Развлечения');
insert into spending_category(category_name) VALUES ('Здоровье');

insert into bill(name_account, user_id, values) VALUES ('Сбербанк', 1,10000);
insert into bill(name_account, user_id, values) VALUES ('Тинькоф', 2,9000000);
insert into bill(name_account, user_id, values) VALUES ('Альфабанк', 2,787);
insert into bill(name_account, user_id, values) VALUES ('Яндекс', 1,7777777);
insert into bill(name_account, user_id, values) VALUES ('Сбербанк', 3,3);
insert into bill(name_account, user_id, values) VALUES ('Убрир', 4,100);

insert into transaction(date, name_account_id, values, spending_category_id) VALUES ('2024-01-01', 1, -100, 1);
insert into transaction(date, name_account_id, values, spending_category_id) VALUES ('2024-01-02', 2, 300, 2);
insert into transaction(date, name_account_id, values, spending_category_id) VALUES ('2024-01-01', 3, -1000, 3);
insert into transaction(date, name_account_id, values, spending_category_id) VALUES ('2024-01-02', 4, -10, 4);
insert into transaction(date, name_account_id, values, spending_category_id) VALUES ('2024-01-02', 2, -1000, 5);
