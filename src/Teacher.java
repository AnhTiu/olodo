import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String name;
    private String id;
    private ArrayList<Student> students;

    public Teacher(String name, String id) {
        this.name = name;
        this.id = "admin";
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(String studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
    }

    public List<Student> getStudentsByCourseId(String courseId) {
        List<Student> enrolledStudents = new ArrayList<>();
        for (Student student : students) {
            for (Course course : student.getMonHoc()) {
                if (course.getId().equals(courseId)) {
                    enrolledStudents.add(student);
                    break;
                }
            }
        }
        return enrolledStudents;
    }
}
