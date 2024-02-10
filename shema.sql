CREATE TABLE users(
                      id serial primary key,
                      first_name varchar(255),
                      last_name varchar(255),
                      login varchar(50) unique,
                      email varchar(50),
                      password varchar(32)
);

CREATE TABLE spending_category (
                                   id serial primary key,
                                   category_name varchar(50) unique
);

CREATE TABLE bill (
                      id serial primary key,
                      number_accounts bigint unique NOT NULL,
                      user_id bigint REFERENCES users (id) NOT NULL,
                      values bigint NOT NULL
);

CREATE TABLE transaction (
                             id serial primary key,
                             date timestamp,
                             number_accounts_id bigint REFERENCES bill(id),
                             values bigint NOT NULL,
                             spending_category_id bigint REFERENCES spending_category(id) NOT NULL
);

insert into users(first_name, last_name, login, email, password) VALUES ('Илья', 'Смирнов', 'pb82', 'pb82@mail.ru', '202cb962ac59075b964b07152d234b70');
insert into users(first_name, last_name, login, email, password) VALUES ('Наталья', 'Смирнова', 'nata', 'natastar@mail.ru', '202cb962ac59075b964b07152d234b70');
insert into users(first_name, last_name, login, email, password) VALUES ('Иван', 'Штепа', 'shtepa', 'shtepa87@yandex.ru', '202cb962ac59075b964b07152d234b70');
insert into users(first_name, last_name, login, email, password) VALUES ('Павел', 'Цемежевский', 'pahan', 'pasha66@gmail.com', '202cb962ac59075b964b07152d234b70');

insert into spending_category(category_name) VALUES ('Продукты');
insert into spending_category(category_name) VALUES ('Зарплата');
insert into spending_category(category_name) VALUES ('Автомобиль');
insert into spending_category(category_name) VALUES ('Развлечения');
insert into spending_category(category_name) VALUES ('Здоровье');

insert into bill(number_accounts, user_id, values) VALUES ('123321', 1,10000);
insert into bill(number_accounts, user_id, values) VALUES ('333333', 2,9000000);
insert into bill(number_accounts, user_id, values) VALUES ('555555', 2,787);
insert into bill(number_accounts, user_id, values) VALUES ('777777', 3,7777777);
insert into bill(number_accounts, user_id, values) VALUES ('32545', 3,3);
insert into bill(number_accounts, user_id, values) VALUES ('525851', 4,100);

insert into transaction(date, number_accounts_id, values, spending_category_id) VALUES ('2024-01-01', 1, -100, 1);
insert into transaction(date, number_accounts_id, values, spending_category_id) VALUES ('2024-01-02', 2, 300, 2);
insert into transaction(date, number_accounts_id, values, spending_category_id) VALUES ('2024-01-01', 3, -1000, 3);
insert into transaction(date, number_accounts_id, values, spending_category_id) VALUES ('2024-01-02', 4, -10, 4);
insert into transaction(date, number_accounts_id, values, spending_category_id) VALUES ('2024-01-02', 2, -1000, 5);

select u.first_name, u.last_name, b.number_accounts
from users as u
inner join bill b on u.id = b.user_id
where first_name = 'Иван';

select u.first_name, u.last_name, b.number_accounts, t.date, c.category_name, t.values
from users as u
join bill as b on b.user_id = u.id
join transaction as t on t.number_accounts_id = b.id
join spending_category as c on c.id = t.spending_category_id
where u.first_name = 'Наталья' and t.date = '2024-01-02';

select u.first_name as Имя, u.last_name as Фамилия, b.number_accounts as Номер_счёта, b.values as Сумма
from users as u
join bill as b on b.user_id = u.id
order by u.first_name;