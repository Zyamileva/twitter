---- DDL--------------------------------------------------------
create table if not exists users(
    id uuid primary key,
    username varchar(20) not null,
    login varchar(14) not null unique,
    about varchar(140),
    location varchar(30),
    registered_since timestamp not null,
    follower_ids uuid array not null,
    following_ids uuid array not null,
    official_account boolean not null
);

create table if not exists tweets(
    id uuid primary key,
    user_id uuid not null,
    reply_tweet_id uuid,
    date_posted timestamp not null,
    content varchar(140) not null,
    mentioned_user_ids uuid array not null,
    foreign key (user_id) references users(id),
    foreign key (reply_tweet_id) references tweets(id)
);

create table if not exists likes(
    id uuid primary key,
    user_id uuid not null,
    tweet_id uuid not null,
    date_posted timestamp not null,
    foreign key (user_id) references users(id),
    foreign key (tweet_id) references tweets(id),
    constraint pair_like unique (user_id, tweet_id)
);

create table if not exists retweets(
    id uuid primary key,
    user_id uuid not null,
    tweet_id varchar(1) not null,
    date_posted timestamp not null,
    foreign key (user_id) references users(id),
    foreign key (tweet_id) references tweets(id),
    constraint pair_retweet unique (user_id, tweet_id)
);

alter table retweets alter column tweet_id set data type uuid

---- DML--------------------------------------------------------

insert into users values
(
    random_uuid(), 'Kate Zyamileva', '@Kate', 'Girl', 'DE, New Castle',
    now(), array[], array[], true
),
(
    random_uuid(), 'Anna Zyamileva', '@Anna', 'Girl', 'DE, Delaware',
    now(), array[], array[], true
);

select *
from users;

update users
set follower_ids = array['79b8527e-7bec-4dc3-b4e8-a6394cbb657f']
where id = '0a90027a-ece3-48b1-9bc9-ef192b4e1e13';

update users
set follower_ids = array['0a90027a-ece3-48b1-9bc9-ef192b4e1e13']
where id = '79b8527e-7bec-4dc3-b4e8-a6394cbb657f';

delete from users
where id = '0a90027a-ece3-48b1-9bc9-ef192b4e1e13';

select *
from users;

insert into users values
(
    random_uuid(), 'Kate Zyamileva', '@Kate', 'Girl', 'DE, New Castle',
    now(), array[], array[], true
);

update users
set follower_ids = array['f27498a7-b14b-4231-9b42-b4820a30362c']
where id = '79b8527e-7bec-4dc3-b4e8-a6394cbb657f';

update users
set following_ids = array['79b8527e-7bec-4dc3-b4e8-a6394cbb657f']
where id = 'f27498a7-b14b-4231-9b42-b4820a30362c';


insert into tweets values
(
    random_uuid(), '79b8527e-7bec-4dc3-b4e8-a6394cbb657f', null, now(), 'Hello, my friends', array[]
);

select *
from tweets;

select *
from tweets
where id='bd477886-20c4-4fcf-888c-23306ae3f883';

delete from tweets
where id = '4949953e-2d04-46d6-80d1-2b44f2a0452e';

insert into likes values
('79b8527e-7bec-4dc3-b4e8-a6394cbb657f', 'bd477886-20c4-4fcf-888c-23306ae3f883', now()
);

select *
from likes;

insert into retweets values
('79b8527e-7bec-4dc3-b4e8-a6394cbb657f', 'bd477886-20c4-4fcf-888c-23306ae3f883', now()
);

select *
from retweets;


drop likes;