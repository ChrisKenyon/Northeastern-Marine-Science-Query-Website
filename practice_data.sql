/*
show variables like 'secure_file_priv';
LOAD DATA INFILE "ABWTUSCAHS3_2000_pgsql.txt" into table sensor_record;
*/

insert into sites (LATITUDE, LONGITUDE, COUNTRY, STATE_PROVINCE, LOCATION_NAME, DISPLAY_ID) values (30.00, 30.00, 'USA', 'MA', 'Whales Waterfront', '1234');
select * from sites;
