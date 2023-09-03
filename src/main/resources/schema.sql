drop table if exists uuser;

create table uuser
(
    ID  bigint auto_increment primary key,
    EMAIL    VARCHAR(255),
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR(255),
    PHONE    VARCHAR(255),
    REG_DATE      TIMESTAMP,
    update_DATE      TIMESTAMP

);

create table NOTICE
(
    ID               bigint auto_increment primary key,
    TITLE            VARCHAR(255),
    contents         VARCHAR(255),

    hits             integer,
    likes            integer,

    reg_date         timestamp,
    UPDATE_DATE      TIMESTAMP,
    delete_date      TIMESTAMP,
    deleted          boolean,

    USER_ID          bigint,
    constraint FK_NOTICE_USER_ID foreign key(USER_ID) references UUSER(ID)

);