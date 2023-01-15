create table bookmarks
(
    id      bigserial not null
        constraint bookmarks_pkey
            primary key,
    post_id bigint
        constraint fk7nbb4ldgek7ux7y6lu0y4g826
            references posts
            on delete cascade,
    user_id bigint
        constraint fkdbsho2e05w5r13fkjqfjmge5f
            references users
            on delete cascade
);

alter table bookmarks
    owner to postgres;

