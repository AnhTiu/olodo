import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDataHandler {
    private static final String FILE_PATH = "test/nhap.txt";
    private List<Course> courses;

    public StudentDataHandler(List<Course> courses) {
        this.courses = courses;
    }

    // Đọc dữ liệu từ file
    public List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length >= 4) {
                    Student student = new Student(details[0], details[1], details[2], details[3]);
                    if (details.length > 4) {
                        String[] courseIds = details[4].split(" ");
                        for (String courseId : courseIds) {
                            Course course = findCourseById(courseId);
                            if (course != null) {
                                student.addMonHoc(course);
                            }
                        }
                    }
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Ghi dữ liệu vào file
    public void writeStudentsToFile(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                bw.write(student.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
