CREATE DATABASE IF NOT EXISTS ManageCourses;
USE ManageCourses;

CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(20),
    password VARCHAR(20),
    fullname NVARCHAR(50),
    role INT
);

CREATE TABLE IF NOT exists student (
	user_id int NOT NULL PRIMARY KEY REFERENCES user (user_id),
    student_id varchar(10)
);

CREATE TABLE IF NOT exists staff (
	user_id int NOT NULL PRIMARY KEY REFERENCES user (user_id),
    staff_id varchar(10)
);