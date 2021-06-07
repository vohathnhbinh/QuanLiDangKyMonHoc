CREATE DATABASE IF NOT EXISTS managecourses;
USE managecourses;

CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(20),
    password VARCHAR(20),
    fullname NVARCHAR(50),
    role ENUM('STUDENT', 'STAFF'),
    UNIQUE key (username)
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
    subject_name NVARCHAR(100),
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
    class_name varchar(10),
    class_size INT default 0,
    male_size INT default 0,
    female_size INT default 0
);

CREATE TABLE IF NOT EXISTS teacher (
	teacher_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    teacher_name NVARCHAR(50)
);

CREATE TABLE IF NOT EXISTS course (
	course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    semester_id INT REFERENCES semester (semester_id),
    subject_id INT REFERENCES subject (subject_id),
    teacher_id INT REFERENCES teacher (teacher_id),
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
    create_on TIMESTAMP,
    PRIMARY KEY (user_id, course_id)
);

insert into user (username, password, fullname, role) values
("binh123", "howdareyou", "Võ Hạ Thanh Bình", "STUDENT"),
("caubengoknghek", "helpmepls", "Nguyễn Văn A", "STUDENT");

insert into class (class_name) values ("18CTT2");

insert into student (user_id, gender, class_id) values
(1, "MALE", 1),
(2, "MALE", 1);

insert into teacher (teacher_name) values ("Nguyen ABC");
insert into subject (subject_name, credit_amount) values ("Nhap mon CNPM", 5);
insert into semester (name, school_year, start_date, end_date) values ("HK1", "2021-2022", '2021-09-01', '2022-02-01');
insert into course (semester_id, subject_id, teacher_id, classroom, date_of_week, shift, max_slot)
values (1, 1, 1, "F101", "MON", "ONE", 60);

insert into subject (subject_name, credit_amount) VALUES
("Nhập môn Công nghệ phần mềm", 4),
("Phát triển ứng dụng web nâng cao", 6),
("Cơ sở dữ liệu", 4)