create table comments
(
    id           bigserial    not null
        constraint comments_pkey
            primary key,
    created_time timestamp    not null,
    text         varchar(255) not null,
    parent_id    bigint
        constraint fklri30okf66phtcgbe5pok7cc0
            references comments
            on delete cascade,
    post_id      bigint
        constraint fkh4c7lvsc298whoyd4w9ta25cr
            references posts
            on delete cascade,
    user_id      bigint
        constraint fk8omq0tc18jd43bu5tjh6jvraq
            references users
            on delete cascade
);

alter table comments
    owner to postgres;

