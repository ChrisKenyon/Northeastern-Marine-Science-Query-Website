CREATE DATABASE IF NOT EXISTS SAVE_THE_WHALES;

-- add specifications for the database at some point
USE SAVE_THE_WHALES;

CREATE TABLE IF NOT EXISTS SITES (
	ID int primary key auto_increment,
	LATITUDE double not null,
	LONGITUDE double not null,
	COUNTRY varchar(100) not null,
	STATE_PROVINCE varchar(200) not null,
	LOCATION_NAME varchar(200) not null,
	DISPLAY_ID varchar(50) not null 
);

drop table sites
CREATE TABLE IF NOT EXISTS MICROSITES (
	ID int primary key auto_increment,
	DISPLAY_ID varchar(50) unique not null,
	BIOMIMIC varchar(50) not null,
	ZONE varchar(50),
	SUB_ZONE varchar(50),
	WAVE_EXPOSURE varchar(50),
	TIDE_HEIGHT double,
	SITE_ID int not null,
	foreign key(SITE_ID) references SITES(ID) on update restrict on delete restrict
);


CREATE TABLE IF NOT EXISTS SENSOR_RECORD (
	ID int primary key auto_increment,
	DATE_TIME datetime not null,
	TEMPERATURE double not null,
	MICROSITE_ID int not null,
	foreign key (MICROSITE_ID) references MICROSITES(ID) on update restrict on delete restrict
);


