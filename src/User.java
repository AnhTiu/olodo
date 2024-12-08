import java.util.ArrayList;
import java.util.List;

public class User {
    private List<Student> students;
    private List<Teacher> teachers;
    StudentDataHandler dataHandler;
    private RegisterCourse registerCourse;

    public User() {
        registerCourse = new RegisterCourse();
        dataHandler = new StudentDataHandler(registerCourse.getCourses());
        students = dataHandler.readStudentsFromFile(); // Đọc dữ liệu từ file khi khởi tạo
        teachers = new ArrayList<>();
        teachers.add(new Teacher("Cô Liên", "ADMIN"));
    }

    public boolean registerStudent(String name, String id, String email, String password) {
        if(name.equals("") ||email.equals("") || password.equals("")){
            return false;
        }
        if(id.contains(" ") || email.contains(" ") || password.contains(" ")){
            return false;
        }
        for (Student e : students) {
            if (e.getId().equals(id) || e.getEmail().equals(email)) {
                return false;
            }
        }
        Student student = new Student(name, id, email, password);
        students.add(student);
        dataHandler.writeStudentsToFile(students); // Lưu dữ liệu vào file
        return true;
    }

    public boolean loginStudent(String id, String password) {
        for (Student e : students) {
            if (e.getId().equals(id) && e.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean registerTeacher(String name, String id) {
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) {
                return false;
            }
        }
        Teacher teacher = new Teacher(name, id);
        teachers.add(teacher);
        return true;
    }

    public boolean loginTeacher(String id) {
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Teacher getTeacher(String id) {
        for (Teacher t : teachers) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void removeStudent(String studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
        dataHandler.writeStudentsToFile(students); // Lưu dữ liệu vào file sau khi xóa
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void removeStudentCourse(Student student, String courseId) {
        Course courseToRemove = student.getMonHoc().stream()
            .filter(course -> course.getId().equals(courseId))
            .findFirst()
            .orElse(null);

        if (courseToRemove != null) {
            student.removeMonHoc(courseToRemove);
            dataHandler.writeStudentsToFile(students); // Lưu dữ liệu vào file sau khi xóa môn học
        }
    }

    public String changePassword(String id, String newPassword) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                s.setPassword(newPassword);
                dataHandler.writeStudentsToFile(students); // Lưu dữ liệu vào file sau khi đổi mật khẩu
                return "Mật khẩu đã được thay đổi thành công cho sinh viên có ID: " + id;
            }
        }
        return "Không tìm thấy sinh viên với ID: " + id;
    }
    public void writeStudentsToFile() {
        dataHandler.writeStudentsToFile(students); // Ghi dữ liệu vào file
    }
    
}
