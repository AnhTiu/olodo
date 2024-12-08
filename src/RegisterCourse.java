import java.util.ArrayList;

public class RegisterCourse {
    private ArrayList<Course> courses;

    public RegisterCourse() {
        courses = new ArrayList<>();
        courses.add(new Course("Lap trinh huong doi tuong", "PTIT001", 3));
        courses.add(new Course("Mang may tinh", "PTIT002", 3));
        courses.add(new Course("He dieu hanh", "PTIT003", 3));
        courses.add(new Course("Kinh te chinh tri Mac Lenin", "PTIT004", 2));
    }

    public boolean studentRegisterCourse(Student student, String id) {
        for (Course course : courses) {
            if (course.getId().equals(id)) {
                if (student.getMonHoc().contains(course)) {
                    System.out.println("Bạn đã đăng ký khóa học này");
                    return false;
                } else {
                    student.addMonHoc(course);
                    System.out.println("Đăng ký khóa học thành công");
                    return true;
                }
            }
        }
        System.out.println("Không có khóa học này");
        return false;
    }

    public boolean studentDropCourse(Student student, String id) {
        System.out.println(student.getId() +  ' ' +id);
        for (Course course : student.getMonHoc()) {
            if (course.getId().equals(id)) {
                if (student.getMonHoc().contains(course)) {
                    student.removeMonHoc(course);
                    System.out.println("Hủy đăng ký khóa học thành công");
                    return true;
                } else {
                    System.out.println("Bạn chưa đăng ký khóa học này");
                    return false;
                }
            }
        }
        System.out.println("Không có khóa học này");
        return false;
    }

    // Phương thức để giáo viên hủy đăng ký khóa học của sinh viên
    public boolean teacherDropStudentCourse(Student student, String courseId) {
        return studentDropCourse(student, courseId);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
