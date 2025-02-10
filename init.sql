
--2. Создать сущность фильм (с характеристиками): уникальный идентификатор, наименование фильма, описание фильма.
create table film (
	id serial primary key, 
	film_name varchar(50), 
	description varchar(200));

--3. В нашем кинотеатре только один зал, поэтому создадим схему рассадки.
--Создать сущность место: уникальный идентификатор, номер места (например "А1")
create table place (
	id serial primary key, 
	place_name varchar(2));
	
--4. Создать сущность сеанс: уникальный идентификатор, идентификатор фильма(связь), дата+время(timestamp тип данных), цена.
create table film_session (
	id serial primary key, 
	id_film integer references film(id),
	date timestamp,
	price numeric(6,0));
	
--5. Создать сущность билет: уникальный идентификатор, идентификатор места (связь), идентификатор сеанса (связь), куплен или нет.
create table ticket (
	id serial primary key,
	id_place integer references place(id),
	id_session integer references film_session(id),
	is_paid boolean);

Графическое отображение того, что должно получиться здесь: https://drawsql.app/teams/vtv-1/diagrams/cinema
6. Создать репозиторий и положить скрипты в файл init.sql.
7. Отправить код на Github.
8. Запустить создание таблиц в базе данных.
