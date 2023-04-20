create table PERSONS (
    id integer not null auto_increment,
    personal_id varchar(11) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    gender varchar(10) not null,
    date_of_birth date not null,
    phone_number varchar(20) not null,
    email varchar(50) not null,

    created_by VARCHAR(255),
    created_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP

    primary key (id)
);
