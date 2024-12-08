import java.util.ArrayList;
import java.util.stream.Collectors;

public class Student {
    public static int dem = 0;
    private String name;
    private String id;
    private String email;
    private String password;
    private ArrayList<Course> monHoc = new ArrayList<>();

    public Student(String name, String id, String email, String password) {
        this.name = name;
        this.id = xuLiID();
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getMonHoc() {
        return monHoc;
    }
    public int getDem() {
        return dem;
    }
    public void setDem(int dem) {
        this.dem = dem;
    }

    public void addMonHoc(Course mon) {
        monHoc.add(mon);
    }

    public void removeMonHoc(Course mon) {
        monHoc.remove(mon);

    }
    

    @Override
    public String toString() {
        String courseIds = monHoc.stream().map(Course::getId).collect(Collectors.joining(" "));
        return name + "," + id + "," + email + "," + password + "," + courseIds;
    }
    public String xuLiID(){
        dem++;
        return String.format("SV%03d", dem);
    }
}
