create schema new_releases_bot;

--drop table new_releases_bot.user_channels;

create table new_releases_bot.user_channels
(
    id           serial  not null,
    user_id      int     not null,
    channel_name varchar not null,
    channel_id bigint not null
);

--drop table new_releases_bot.releases;

create table new_releases_bot.releases
(
    id           serial  not null,
    release_id   varchar not null,
    artists      varchar not null,
    release_name varchar not null,
    release_type varchar not null,
    release_date date    not null,
    url          varchar not null
);
