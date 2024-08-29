CREATE DATABASE cinema_db WITH ENCODING 'UTF8';
CREATE USER cinema_app with password 'cinema';
GRANT ALL PRIVILEGES ON DATABASE cinema_db to cinema_app;
