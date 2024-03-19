create table owners (
    id uuid primary key not null,
    name varchar(255),
    birthday date
);

create table colors (
    id bigint primary key not null,
    color varchar(50)
);

create table cats (
    id uuid primary key not null,
    name varchar(255),
    birthday date,
    breed varchar(255),
    color_id bigint,
    owner_id uuid
);

create table cat_friends (
    first_cat_id uuid,
    second_cat_id uuid,
    primary key (first_cat_id, second_cat_id)
);

alter table owners owner to "user";
alter table colors owner to "user";
alter table cats owner to "user";
alter table cat_friends owner to "user";

alter table cats add foreign key (color_id) references colors(id);
alter table cats add foreign key (owner_id) references owners(id);

alter table cat_friends add foreign key (first_cat_id) references cats(id);
alter table cat_friends add foreign key (second_cat_id) references cats(id);