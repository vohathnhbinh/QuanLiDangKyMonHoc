CREATE DATABASE IF NOT EXISTS managecourses;
USE managecourses;

CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(20),
    password VARCHAR(20),
    fullname NVARCHAR(50),
    role ENUM('STUDENT', 'STAFF')
);

CREATE TABLE IF NOT EXISTS student (
	user_id INT NOT NULL PRIMARY KEY REFERENCES user (user_id),
    student_number VARCHAR(10),
    gender ENUM('MALE', 'FEMALE'),
    class_id INT REFERENCES class (class_id)
);

CREATE TRIGGER stu_num BEFORE INSERT ON student
FOR EACH ROW SET new.student_number = CONCAT("SV", LPAD(new.user_id, 3, "0"));

CREATE TABLE IF NOT EXISTS staff (
	user_id INT NOT NULL PRIMARY KEY REFERENCES user (user_id),
    staff_number VARCHAR(10)
);

CREATE TRIGGER staf_num BEFORE INSERT ON staff
FOR EACH ROW SET new.staff_number = CONCAT("GV", LPAD(new.user_id, 3, "0"));

CREATE TABLE IF NOT EXISTS subject (
	subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject_number VARCHAR(10),
    subject_name NVARCHAR(30),
    credit_amount INT
);

CREATE TRIGGER subj_num BEFORE INSERT ON subject
FOR EACH ROW SET new.subject_number = CONCAT("MH", LPAD(new.subject_id, 3, "0"));

CREATE TABLE IF NOT EXISTS semester (
	semester_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(3),
    school_year VARCHAR(10),
    start_date DATE,
    end_date DATE
);

CREATE TABLE IF NOT EXISTS class (
	class_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class_size INT,
    male_size INT,
    female_size INT
);

CREATE TABLE IF NOT EXISTS course (
	course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    semester_id INT REFERENCES semester (semester_id),
    subject_id INT REFERENCES subject (subject_id),
    teacher_name NVARCHAR(50),
    classroom VARCHAR(5),
    date_of_week ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
    shift ENUM('ONE', 'TWO', 'THREE', 'FOUR'),
    max_slot int
);

CREATE TABLE IF NOT EXISTS session (
	session_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    semester_id INT REFERENCES semester (semester_id),
    begin_date DATETIME,
    expired_date DATETIME
);

CREATE TABLE IF NOT EXISTS student_course (
	user_id INT NOT NULL REFERENCES student (user_id),
    course_id INT NOT NULL REFERENCES course (course_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, course_id)
);