--2. Создать сущность фильм (с характеристиками): уникальный идентификатор, наименование фильма, описание фильма.
create table if not exists movie (
	id serial primary key, 
	name varchar(50), 
	description varchar(200));

--3. В нашем кинотеатре только один зал, поэтому создадим схему рассадки.
--Создать сущность место: уникальный идентификатор, номер места (например "А1")
create table if not exists place (
	id serial primary key, 
	name varchar(4));
	
--4. Создать сущность сеанс: уникальный идентификатор, идентификатор фильма(связь), дата+время(timestamp тип данных), цена.
create table if not exists session (
	id serial primary key, 
	id_movie integer references movie(id),
	datetime timestamp,
	price numeric(6,0));
	
--5. Создать сущность билет: уникальный идентификатор, идентификатор места (связь), идентификатор сеанса (связь), куплен или нет.

create table if not exists ticket (
	id serial primary key,
	id_place integer references place(id),
	id_session integer references session(id),
	is_paid boolean default false,
	UNIQUE (id_place, id_session));

