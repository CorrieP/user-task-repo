create sequence user_seq start 2001 increment 1;
create sequence task_seq start 5001 increment 1;

create table "user"
(
    id              bigint not null
        constraint idx_user_primary
            primary key default nextval('user_seq'),
    created         timestamp with time zone,
    modified        timestamp with time zone,
    username        varchar(255),
    first_name      varchar(255),
    last_name       varchar(255),
    active          bool default true
);


create table task
(
    id              bigint not null
        constraint idx_task_primary
            primary key default nextval('task_seq'),
    created         timestamp with time zone,
    modified        timestamp with time zone,
    name            varchar(255),
    description     varchar(500),
    date_time       timestamp with time zone,
    active          bool default true,
    user_id         bigint
        constraint task_user_fk
            references "user"
            on update restrict on delete restrict
);