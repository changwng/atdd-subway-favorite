create table if not exists STATION
(
    id bigint auto_increment not null,
    name varchar(255) not null unique,
    created_at datetime,
    primary key(id)
);

create table if not exists LINE
(
    id bigint auto_increment not null,
    name varchar(255) not null,
    start_time time not null,
    end_time time not null,
    interval_time int not null,
    created_at datetime,
    updated_at datetime,
    primary key(id)
);

create table if not exists LINE_STATION
(
    line bigint not null,
    station_id bigint not null,
    pre_station_id bigint,
    distance int,
    duration int,
    created_at datetime,
    updated_at datetime
);

create table if not exists MEMBER
(
    id bigint auto_increment not null,
    email varchar(255) not null unique,
    name varchar(255) not null,
    password varchar(255) not null,
    primary key(id)
);

create table if not exists FAVORITE
(
    member bigint not null,
    source_id bigint not null,
    target_id bigint not null
);

-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('1호선', '03:00', '19:00', 15);
-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('2호선', '05:00', '23:00', 5);
-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('4호선', '05:00', '23:00', 10);
-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('5호선', '05:00', '23:00', 10);
-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('9호선', '05:00', '23:00', 9);
-- INSERT INTO LINE (name, start_time, end_time, interval_time) VALUES('토안', '05:00', '23:00', 9);
--
-- INSERT INTO STATION (name)
-- VALUES
-- ('구로'),
-- ('신도림'),
-- ('신길'),
-- ('용산'),
-- ('서울역'),
-- ('충정로'),
-- ('당산'),
-- ('영등포구청'),
-- ('대림'),
-- ('여의도'),
-- ('동작'),
-- ('삼각지'),
-- ('시청'),
-- ('토니'),
-- ('토늬'),
-- ('유안'),
-- ('유으');
--
-- INSERT INTO LINE_STATION (line, pre_station_id, station_id, distance, duration)
-- VALUES
-- (1, null, 1, 0, 0),
-- (1, 1, 2, 2, 2),
-- (1, 2, 3, 4, 5),
-- (1, 3, 4, 7, 8),
-- (1, 4, 5, 5, 5),
-- (1, 5, 13, 1, 2),
--
-- (2, null, 13, 0, 0),
-- (2, 13, 6, 3, 2),
-- (2, 6, 7, 10, 13),
-- (2, 7, 8, 2, 2),
-- (2, 8, 2, 4, 4),
-- (2, 2, 9, 5, 3),
--
-- (3, null, 11, 0, 0),
-- (3, 11, 12, 4, 7),
-- (3, 12, 5, 5, 4),
--
-- (4, null, 8, 0, 0),
-- (4, 8, 3, 4, 4),
-- (4, 3, 10, 3, 2),
-- (4, 10, 6, 8, 9),
--
-- (5, null, 7, 0, 0),
-- (5, 7, 10, 5, 4),
-- (5, 10, 11, 9, 7),
--
-- (6, null, 14, 0, 0),
-- (6, 14, 15, 10, 10),
-- (6, 15, 16, 10, 10),
-- (6, 16, 17, 10, 10);
--
-- INSERT INTO MEMBER(email, name, password) VALUES('qkrtmddhks95@gmail.com', 'toney', '1');