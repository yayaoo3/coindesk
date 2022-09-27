create table if not exists Demo (
    id varchar(50) not null,
    name varchar(25) not null,
    type varchar(25) not null,
    PRIMARY KEY (id)
);

create table if not exists Cryptocurrency (
    id varchar(50) not null,
    chartName varchar(25) not null,
    disclaimer varchar(100) not null,
    updatedTime date not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

--create table if not exists Cryptocurrency_Bpi (
--    id varchar(50) not null,
--    code varchar(15) not null,
--    PRIMARY KEY (id,code)
--);

create table if not exists Exchange_Rates (
    id varchar(50) not null,
    code varchar(15) not null,
    symbol varchar(25) not null,
    rate varchar(25) not null,
    description varchar(25) not null,
    rate_float DOUBLE not null,
    PRIMARY KEY (id,code)
);
