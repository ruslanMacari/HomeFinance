create table currencies
(
    id       bigint      not null generated always as identity primary key,
    char_code varchar(5)  not null,
    code     varchar(5)  not null
        constraint unique_currencies_by_code
            unique,
    name     varchar(45) not null
        constraint unique_currencies_by_name
            unique
);

create table currency_rates
(
    id         bigint         not null generated always as identity primary key,
    date       date           not null,
    rate       decimal(38, 2) not null,
    currency_id bigint
        constraint fk_currency_rate_currency
            references currencies (id)
);

create table users
(
    id       bigint      not null generated always as identity primary key,
    enabled  boolean     not null,
    name     varchar(45) not null
        constraint unique_users_by_name
            unique,
    password varchar(60) not null
);

create table user_roles
(
    id     bigint      not null generated always as identity primary key,
    role   varchar(45) not null,
    user_id bigint      not null
        constraint fk_user_roles_users
            references users (id),
    constraint unique_user_role_by_role_and_user_id
        unique (role, user_id)
);

