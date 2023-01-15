create table roles
(
    id   bigserial    not null
        constraint roles_pkey
            primary key,
    name varchar(255) not null
);

alter table roles
    owner to postgres;

create table users
(
    id         bigserial    not null
        constraint users_pkey
            primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null
);

alter table users
    owner to postgres;

create table users_roles
(
    user_id  bigint not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references users,
    roles_id bigint not null
        constraint fka62j07k5mhgifpp955h37ponj
            references roles
);

alter table users_roles
    owner to postgres;

