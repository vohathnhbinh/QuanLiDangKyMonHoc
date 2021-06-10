# QuanLiDangKyMonHoc
Một phần mềm quản lí hệ thống đăng ký học phần đơn giản
# Hạn chế
Phần mềm không lưu danh sách đăng ký học phần của sinh viên theo học kì, vì thế khi đăng ký đến học phần thứ 8 (giới hạn đăng ký - magic number :))) ) trong một học kì này, qua học kì sau sinh viên sẽ phải tự hủy các học phần của học kì trước để đăng ký tiếp hoặc do người quản lí hệ thống xóa các record đăng ký học phần.
# Giải pháp
Tạo thêm một table mục đích lưu tất cả học phần sinh viên đã đăng ký (archive), kể cả những học kì trước đó, trước trước đó, và sau này; table danh sách đăng ký học phần chỉ để lưu những đăng ký học phần của học kì hiện tại, nó sẽ được wipe mỗi khi bước sang học kì mới (nếu rảnh mới làm :( ).
# Công nghệ
- Java
- MySQL với ORM Hibernate
- Java Swing với IntelliJ IDEA GUI Designer
# Thư viện bên ngoài
- ButtonColumn.java: [Table Button Column](https://tips4java.wordpress.com/2009/07/12/table-button-column/)
# Khác
- Họ tên generator: [Fake name generator](https://fauxid.com/fake-name-generator/vietnam)
