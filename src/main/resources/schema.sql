drop table if exists uuser;

create table uuser
(
    ID  bigint auto_increment primary key,
    EMAIL    VARCHAR(255),
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR(255),
    PHONE    VARCHAR(255),
    REG_DATE      TIMESTAMP

);