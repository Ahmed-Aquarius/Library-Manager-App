use library_manager;

CREATE TABLE users (
	id varchar(15) primary key default(1),
    `password` varchar(20) not null,
    name varchar(30) not null,
    role enum('admin', 'regular_user'),
    registered_at timestamp default(now())
);

CREATE TABLE books (
	id varchar(15) primary key default(1),
    title varchar(100) not null,
    author varchar (50) not null,
    genre enum('novel: romantic', 'novel: fantasy', 'novel: action', 'educational: computer_science', 'educational: medicine', 'educational: business', 'other'),
    no_available_copies int,
    added_at timestamp default(now()),
    
    constraint no_available_copies_positive check (no_available_copies >= 0)
);

CREATE TABLE borrowed_books (
	user_id varchar(15) not null,
    borrowed_book_id varchar(10) not null,
    
    foreign key (user_id) references users(id),
    foreign key (borrowed_book_id) references books(id),
    primary key (user_id, borrowed_book_id)
);

-- seed the users table
INSERT INTO users (password, name, role)
VALUES ('123', 'ahmed', 'admin');
