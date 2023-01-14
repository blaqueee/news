alter table users
add column avatar_url varchar(255) not null default '';

create table categories
(
    id   bigserial    not null
        constraint categories_pkey
            primary key,
    name varchar(255) not null
);

alter table categories
    owner to postgres;

create table posts
(
    id           bigserial    not null
        constraint posts_pkey
            primary key,
    created_time timestamp    not null,
    description  varchar(255) not null,
    image_url    varchar(255) not null,
    text         varchar(500) not null,
    title        varchar(255) not null,
    category_id  bigint
        constraint fkijnwr3brs8vaosl80jg9rp7uc
            references categories,
    user_id      bigint
        constraint fk5lidm6cqbc7u4xhqpxm898qme
            references users
);

alter table posts
    owner to postgres;

