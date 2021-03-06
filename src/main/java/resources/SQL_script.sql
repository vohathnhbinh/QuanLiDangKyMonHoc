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

CREATE TABLE IF NOT EXISTS class (
    class_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(10),
    class_size INT DEFAULT 0,
    male_size INT DEFAULT 0,
    female_size INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS student (
	user_id INT NOT NULL PRIMARY KEY,
    student_number VARCHAR(10),
    gender ENUM('MALE', 'FEMALE'),
    class_id INT,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (class_id) REFERENCES class (class_id)
);

CREATE TRIGGER stu_num BEFORE INSERT ON student
FOR EACH ROW SET new.student_number = CONCAT("SV", LPAD(new.user_id, 3, "0"));

CREATE TABLE IF NOT EXISTS staff (
	user_id INT NOT NULL PRIMARY KEY,
    staff_number VARCHAR(10),
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TRIGGER staf_num BEFORE INSERT ON staff
FOR EACH ROW SET new.staff_number = CONCAT("GV", LPAD(new.user_id, 3, "0"));

CREATE TABLE IF NOT EXISTS subject (
	subject_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject_number VARCHAR(10),
    subject_name NVARCHAR(100),
    credit_amount INT
);

DELIMITER ;;
CREATE PROCEDURE subj_number_update ()
begin
		update subject s
		set s.subject_number = CONCAT("MH", LPAD(s.subject_id, 3, "0"));
end;
;;

CREATE TABLE IF NOT EXISTS semester (
    semester_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(3),
    school_year VARCHAR(10),
    start_date DATE,
    end_date DATE
);

CREATE TABLE IF NOT EXISTS teacher (
    teacher_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    teacher_name NVARCHAR(50)
);

CREATE TABLE IF NOT EXISTS course (
    course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    semester_id INT,
    subject_id INT,
    teacher_id INT,
    classroom VARCHAR(5),
    date_of_week ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
    shift ENUM('ONE', 'TWO', 'THREE', 'FOUR'),
    max_slot INT,
    FOREIGN KEY (semester_id)
        REFERENCES semester (semester_id),
    FOREIGN KEY (subject_id)
        REFERENCES subject (subject_id),
    FOREIGN KEY (teacher_id)
        REFERENCES teacher (teacher_id)
);

CREATE TABLE IF NOT EXISTS session (
    session_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    semester_id INT,
    begin_date DATETIME,
    expired_date DATETIME,
    FOREIGN KEY (semester_id)
        REFERENCES semester (semester_id)
);

CREATE TABLE IF NOT EXISTS student_course (
    user_id INT NOT NULL,
    course_id INT NOT NULL,
    create_on DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id , course_id),
    FOREIGN KEY (user_id)
        REFERENCES student (user_id),
    FOREIGN KEY (course_id)
        REFERENCES course (course_id)
);

insert into user (username, password, fullname, role) values
("binh123", "howdareyou", "V?? H??? Thanh B??nh", "STUDENT"),
("caubengoknghek", "helpmepls", "Nguy???n V??n A", "STUDENT"),
("student3", "aaaaa", "V?? H??ng Ho??n", "STUDENT"),
("student4", "aaaaa", "C???n Minh Uy??n", "STUDENT"),
("student5", "aaaaa", "T??n Thi???u Ho??i", "STUDENT"),
("student6", "aaaaa", "Giang An B??nh", "STUDENT"),
("student7", "aaaaa", "L??m Trang", "STUDENT"),
("student8", "aaaaa", "Ung Khoa Th??i", "STUDENT"),
("student9", "aaaaa", "M???ch Ki???u Sinh", "STUDENT"),
("student10", "aaaaa", "Trang Minh", "STUDENT"),
("student11", "aaaaa", "An Thi???u Mi", "STUDENT"),
("student12", "aaaaa", "Kh????ng B???i Ng???c", "STUDENT"),
("staff13", "bbbbb", "Ng???y T???", "STAFF"),
("staff14", "bbbbb", "T??ng Lam H???p", "STAFF"),
("staff15", "bbbbb", "Danh Kim", "STAFF"),
("staff16", "bbbbb", "H???ng B???ng", "STAFF"),
("staff17", "bbbbb", "La Qu??", "STAFF");

insert into class (class_name, class_size, male_size, female_size) values
("18CTT2", 8, 5, 3),
("18CTT1", 4, 1, 3);

insert into student (user_id, gender, class_id) values
(1, "MALE", 1),
(2, "MALE", 1),
(3, "MALE", 1),
(4, "FEMALE", 1),
(5, "MALE", 1),
(6, "FEMALE", 1),
(7, "FEMALE", 1),
(8, "MALE", 1),
(9, "FEMALE", 2),
(10, "MALE", 2),
(11, "FEMALE", 2),
(12, "FEMALE", 2);

insert into staff (user_id) values
(13), (14), (15), (16), (17);

insert into subject (subject_name, credit_amount) values
("Nh???p m??n l???p tr??nh", 4),
("Nh???p m??n C??ng ngh??? th??ng tin 1", 4),
("Nh???p m??n C??ng ngh??? th??ng tin 2", 4),
("K??? thu???t l???p tr??nh", 4),
("Ph????ng ph??p l???p tr??nh h?????ng ?????i t?????ng", 4),
("C???u tr??c d??? li???u v?? gi???i thu???t", 4),
("M???ng m??y t??nh", 4),
("Ki???n tr??c m??y t??nh v?? h???p ng???", 4),
("C?? s??? d??? li???u", 4),
("Nh???p m??n c??ng ngh??? ph???n m???m", 4),
("H??? ??i???u h??nh", 4),
("Thi???t k??? ph???n m???m", 4),
("Nh???p m??n thi???t k??? v?? ph??n t??ch gi???i thu???t", 4),
("L???p tr??nh ???ng d???ng Java", 4);
SET SQL_SAFE_UPDATES = 0;
call subj_number_update();
SET SQL_SAFE_UPDATES = 1;

insert into teacher (teacher_name) values
("La Ph?????c L??"),
("Ch??u Tr???ch"),
("L??? Vi"),
("Tr???nh D??n"),
("H??? Qu??"),
("Kh??u Tr??m V???ng"),
("L??? Mi"),
("C?? Khuy???n V???nh"),
("Kh???ng Sao Nh??n"),
("Ung H??ng");

insert into semester (name, school_year, start_date, end_date) values
("HK1", "2021-2022", '2021-09-01', '2022-02-01'),
("HK2", "2021-2022", '2021-03-01', '2022-06-01'),
("HK3", "2021-2022", '2021-07-01', '2022-08-01'),
("HK1", "2022-2023", '2022-09-01', '2023-02-01'),
("HK2", "2022-2023", '2022-03-01', '2023-06-01'),
("HK3", "2022-2023", '2022-07-01', '2023-08-01'),
("HK2", "TEST-TEST", '2021-06-01', '2021-12-01');

insert into session (semester_id, begin_date, expired_date) values
(1, '2021-08-10', '2021-09-01'),
(1, '2021-09-04', '2021-09-10'),
(7, '2021-06-01', '2021-06-12'),
(7, '2021-06-13', '2021-06-20');

insert into course (semester_id, subject_id, teacher_id, classroom, date_of_week, shift, max_slot) values
(1, 1, 1, "A101", "MON", "ONE", 10),
(1, 1, 10, "B101", "TUE", "TWO", 40),
(7, 4, 3, "A201", "MON", "FOUR", 30),
(7, 3, 4, "B301", "TUE", "ONE", 20),
(7, 2, 2, "A205", "WED", "TWO", 50),
(7, 5, 7, "B103", "THU", "THREE", 70),
(7, 1, 3, "A103", "FRI", "FOUR", 100),
(7, 6, 2, "A104", "MON", "ONE", 30),
(7, 14, 10, "B104", "SAT", "TWO", 10),
(7, 11, 8, "A105", "TUE", "THREE", 20),
(7, 3, 7, "A106", "WED", "FOUR", 40),
(7, 4, 4, "B105", "THU", "ONE", 10),
(7, 1, 8, "B301", "FRI", "TWO", 50),
(7, 10, 6, "A203", "SUN", "THREE", 60),
(7, 12, 3, "A204", "MON", "FOUR", 40),
(7, 2, 6, "B302", "TUE", "ONE", 30),
(7, 3, 2, "B304", "WED", "TWO", 20),
(7, 7, 1, "A201", "THU", "THREE", 70),
(7, 8, 5, "B306", "FRI", "FOUR", 80),
(7, 9, 4, "A205", "SAT", "ONE", 10);

insert into student_course (user_id, course_id) values
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 5), (2, 6), (2, 7), (2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 6), (3, 7),
(4, 2), (4, 3), (4, 4), (4, 5);