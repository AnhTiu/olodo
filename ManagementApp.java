import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ManagementApp extends Application {
    private User userManagement = new User();
    private RegisterCourse registerCourse = new RegisterCourse();
    private Student currentStudent;
    private Teacher currentTeacher;
    private Label notificationLabel = new Label();
    private BorderPane mainPane = new BorderPane();
    private Scene mainScene, loginScene, registerScene, studentScene, teacherScene;
    private Stage primaryStage;
    private static ManagementApp instance;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Student and Course Management System");

        showMainScreen();
        showLoginScreen();
        showRegisterScreen();

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showMainScreen() {
        Pane mainPane = new Pane();
    
        // Thêm hình nền
        Image backgroundImage = new Image("test/icon.png"); // Sử dụng đường dẫn chính xác
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        backgroundView.setPreserveRatio(true);
    
        VBox buttonBox = new VBox(20);
        buttonBox.setAlignment(Pos.CENTER); // Đặt các nút vào giữa màn hình
        buttonBox.setPadding(new Insets(20, 20, 20, 20));
        buttonBox.setSpacing(20);
        buttonBox.setLayoutX(300); // Đặt vị trí của VBox để các nút nằm cân đối
        buttonBox.setLayoutY(400);
    
        Button loginButton = new Button("Đăng nhập");
        styleButton(loginButton);
        loginButton.setOnAction(event -> primaryStage.setScene(loginScene));
    
        Button registerButton = new Button("Đăng ký");
        styleButton(registerButton);
        registerButton.setOnAction(event -> primaryStage.setScene(registerScene));
    
        buttonBox.getChildren().addAll(loginButton, registerButton);
        mainPane.getChildren().addAll(backgroundView, buttonBox);
    
        mainScene = new Scene(mainPane, 800, 600); // Đặt kích thước giao diện chính theo hình ảnh logo
    }
    
    private void styleButton(Button button) {
        button.setStyle(
            "-fx-font-size: 18px; " +
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-border-radius: 5px; " +
            "-fx-background-radius: 5px;"
        );
        button.setPrefSize(150, 50); // Kích thước nút phù hợp với hình ảnh logo
    }
    

    private void showLoginScreen() {
        Pane loginPane = new Pane();
    
        // Thêm hình nền
        Image backgroundImage = new Image("test/icon.png"); // Sử dụng đường dẫn chính xác
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        backgroundView.setPreserveRatio(true);
    
        VBox loginBox = new VBox(15);
        loginBox.setAlignment(Pos.CENTER); // Đặt các nút và trường nhập liệu vào giữa màn hình
        loginBox.setPadding(new Insets(20, 20, 20, 20));
        loginBox.setSpacing(15);
    
        Label loginTitle = new Label("Đăng nhập");
        loginTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
    
        TextField loginIdField = new TextField();
        loginIdField.setPromptText("Mã số");
        styleTextField(loginIdField);
    
        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("Mật khẩu");
        styleTextField(loginPasswordField);
    
        Button loginStudentButton = new Button("Đăng nhập Sinh viên");
        styleButton(loginStudentButton);
        loginStudentButton.setPrefSize(250, 50); // Đảm bảo đủ kích thước để hiển thị văn bản
        loginStudentButton.setOnAction(event -> handleStudentLogin(loginIdField, loginPasswordField));
    
        Button loginTeacherButton = new Button("Đăng nhập Giáo viên");
        styleButton(loginTeacherButton);
        loginTeacherButton.setPrefSize(250, 50); // Đảm bảo đủ kích thước để hiển thị văn bản
        loginTeacherButton.setOnAction(event -> handleTeacherLogin(loginIdField));
    
        Button backButton = new Button("Trở về");
        styleButton(backButton);
        backButton.setPrefSize(200, 50); // Đảm bảo đủ kích thước để hiển thị văn bản
        backButton.setOnAction(event -> primaryStage.setScene(mainScene));
    
        loginBox.getChildren().addAll(loginTitle, loginIdField, loginPasswordField, loginStudentButton, loginTeacherButton, backButton);
        
        // Tính toán vị trí của loginBox để căn giữa màn hình và thấp hơn một chút
        double layoutX = (800 - 250) / 2.0; // 250 là chiều rộng của loginBox
        double layoutY = (600 - (50 * 6) - (15 * 5)) / 2.0 + 50; // 50 là chiều cao của nút, 6 là số lượng phần tử, 15 là khoảng cách giữa các phần tử, 50 là giá trị thêm để di chuyển xuống dưới
        loginBox.setLayoutX(layoutX);
        loginBox.setLayoutY(layoutY);
    
        loginPane.getChildren().addAll(backgroundView, loginBox);
    
        loginScene = new Scene(loginPane, 800, 600); // Đặt kích thước giao diện đăng nhập theo hình ảnh logo
    }
    
    private void styleTextField(TextField textField) {
        textField.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-padding: 10px; " +
            "-fx-border-radius: 5px; " +
            "-fx-background-radius: 5px;"
        );
        textField.setPrefSize(250, 40); // Kích thước trường nhập liệu phù hợp
    }
    
    private void showRegisterScreen() {
        Pane registerPane = new Pane();
    
        // Thêm hình nền
        Image backgroundImage = new Image("test/icon.png"); // Sử dụng đường dẫn chính xác
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        backgroundView.setPreserveRatio(true);
    
        VBox registerBox = new VBox(15);
        registerBox.setAlignment(Pos.CENTER); // Đặt các nút và trường nhập liệu vào giữa màn hình
        registerBox.setPadding(new Insets(20, 20, 20, 20));
        registerBox.setSpacing(15);
    
        Label registerTitle = new Label("Đăng ký Tài khoản Mới");
        registerTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        Label registerIdField = new Label();
        TextField registerNameField = new TextField();
        registerNameField.setPromptText("Tên");
        styleTextField(registerNameField);
    
        
        
    
        TextField registerEmailField = new TextField();
        registerEmailField.setPromptText("Email");
        styleTextField(registerEmailField);
    
        PasswordField registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Mật khẩu");
        styleTextField(registerPasswordField);
    
        Button registerStudentButton = new Button("Đăng ký Sinh viên");
        styleButton(registerStudentButton);
        registerStudentButton.setPrefSize(250, 50); // Đảm bảo đủ kích thước để hiển thị văn bản
        registerStudentButton.setOnAction(event -> {
            String id = registerIdField.getText();
            String name = registerNameField.getText();  
            String email = registerEmailField.getText();
            String password = registerPasswordField.getText();
            boolean isRegistered = userManagement.registerStudent(name, id, email, password);
            if (isRegistered) {
                showAlert("Đăng ký thành công", "Đăng ký sinh viên thành công: " + name);
                primaryStage.setScene(mainScene);
            } else {
                showAlert("Đăng ký thất bại", "Đăng kí thất bại.");
            }
        });
    
        Button backButton = new Button("Trở về");
        styleButton(backButton);
        backButton.setPrefSize(250, 50); // Đảm bảo đủ kích thước để hiển thị văn bản
        backButton.setOnAction(event -> primaryStage.setScene(mainScene));
    
        registerBox.getChildren().addAll(registerTitle, registerNameField, registerIdField, registerEmailField, registerPasswordField, registerStudentButton, backButton);
        
        // Tính toán vị trí của registerBox để căn giữa màn hình và thấp hơn một chút
        double layoutX = (800 - 250) / 2.0; // 250 là chiều rộng của registerBox
        double layoutY = (600 - (50 * 7) - (15 * 6)) / 2.0 + 80; // 50 là chiều cao của nút, 7 là số lượng phần tử, 15 là khoảng cách giữa các phần tử, 80 là giá trị thêm để di chuyển xuống dưới
        registerBox.setLayoutX(layoutX);
        registerBox.setLayoutY(layoutY);
    
        registerPane.getChildren().addAll(backgroundView, registerBox);
    
        registerScene = new Scene(registerPane, 800, 600); // Đặt kích thước giao diện đăng ký theo hình ảnh logo
    }
    
    
    

    private void handleStudentLogin(TextField loginIdField, PasswordField loginPasswordField) {
        String id = loginIdField.getText();
        String password = loginPasswordField.getText();
        boolean isLoggedIn = userManagement.loginStudent(id, password);
        if (isLoggedIn) {
            currentStudent = userManagement.getStudentById(id);
            notificationLabel.setText("");
            showStudentDashboard();
        } else {
            showAlert("Đăng nhập thất bại", "Mã số hoặc mật khẩu không đúng.");
        }
    }

    private void handleTeacherLogin(TextField loginIdField) {
        String id = loginIdField.getText();
        boolean isLoggedIn = userManagement.loginTeacher(id);
        if (isLoggedIn) {
            currentTeacher = userManagement.getTeacher(id);
            notificationLabel.setText("");
            showTeacherDashboard();
        } else {
            showAlert("Đăng nhập thất bại", "Mã số không đúng.");
        }
    }

    private void showStudentDashboard() {
        VBox studentDashboard = new VBox();
        studentDashboard.setPadding(new Insets(10, 10, 10, 10));
        studentDashboard.setSpacing(10);
        studentDashboard.setStyle("-fx-background-color: #e0f2f1; -fx-border-color: #004d40; -fx-border-width: 2px;");

        Label studentTitleLabel = new Label("Bảng điều khiển của sinh viên");
        studentTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button manageCoursesButton = new Button("Quản lý khóa học");
        manageCoursesButton.setOnAction(event -> showCourseManagement());

        Button viewInfoButton = new Button("Xem thông tin");
        viewInfoButton.setOnAction(event -> showStudentInformation(currentStudent.getId()));

        Button logoutButton = new Button("Đăng xuất");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        logoutButton.setOnAction(event -> primaryStage.setScene(mainScene));

        studentDashboard.getChildren().addAll(studentTitleLabel, manageCoursesButton, viewInfoButton, logoutButton);

        studentScene = new Scene(studentDashboard, 800, 600);
        primaryStage.setScene(studentScene);
    }

    public class StudentCourseInfo {
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty studentId;
    private final SimpleStringProperty courses;

    public StudentCourseInfo(String studentName, String studentId, String courses) {
        this.studentName = new SimpleStringProperty(studentName);
        this.studentId = new SimpleStringProperty(studentId);
        this.courses = new SimpleStringProperty(courses);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    public String getCourses() {
        return courses.get();
    }

    public void setCourses(String courses) {
        this.courses.set(courses);
    }
}
public static ManagementApp getInstance() { 
    return instance; 
}

private void showTeacherDashboard() {
    VBox teacherDashboard = new VBox();
    teacherDashboard.setPadding(new Insets(10, 10, 10, 10));
    teacherDashboard.setSpacing(10);
    teacherDashboard.setStyle("-fx-background-color: #ffe0b2; -fx-border-color: #ff9800; -fx-border-width: 2px;");

    Label teacherTitleLabel = new Label("Bảng điều khiển của giáo viên");
    teacherTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    Button manageStudentsButton = new Button("Quản lý sinh viên");
    manageStudentsButton.setOnAction(event -> showTeacherManagement());

    Button viewStudentsByCourseButton = new Button("Các khóa học");
    viewStudentsByCourseButton.setOnAction(event -> showStudentsByCourse());

    Button logoutButton = new Button("Đăng xuất");
    logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
    logoutButton.setOnAction(event -> primaryStage.setScene(mainScene));

    teacherDashboard.getChildren().addAll(teacherTitleLabel, manageStudentsButton, viewStudentsByCourseButton, logoutButton);

    // Thêm TableView vào bên phải của teacherDashboard
    TableView<StudentCourseInfo> studentTable = new TableView<>();
    ObservableList<StudentCourseInfo> data = FXCollections.observableArrayList();

    TableColumn<StudentCourseInfo, String> nameCol = new TableColumn<>("Tên sinh viên");
    nameCol.setMinWidth(200);
    nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));

    TableColumn<StudentCourseInfo, String> idCol = new TableColumn<>("Mã sinh viên");
    idCol.setMinWidth(100);
    idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

    TableColumn<StudentCourseInfo, String> coursesCol = new TableColumn<>("Môn học đăng ký");
    coursesCol.setMinWidth(300);
    coursesCol.setCellValueFactory(new PropertyValueFactory<>("courses"));

    studentTable.setItems(data);
    studentTable.getColumns().addAll(nameCol, idCol, coursesCol);

    // Thêm dữ liệu vào bảng
    for (Student student : userManagement.getStudents()) {
        String courses = student.getMonHoc().stream()
            .map(Course::getName)
            .collect(Collectors.joining(", "));
        data.add(new StudentCourseInfo(student.getName(), student.getId(), courses));
    }

    // Thiết lập BorderPane
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(teacherDashboard);
    borderPane.setRight(studentTable);

    teacherScene = new Scene(borderPane, 800, 600);
    primaryStage.setScene(teacherScene);
    
}


    private void showCourseManagement() {
        VBox courseBox = new VBox();
        courseBox.setPadding(new Insets(10, 10, 10, 10));
        courseBox.setSpacing(10);
        courseBox.setStyle("-fx-background-color: #e0f2f1; -fx-border-color: #004d40; -fx-border-width: 2px;");

        Label courseTitleLabel = new Label("Quản lý khóa học");
        courseTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(
                registerCourse.getCourses().stream().map(Course::getName).toArray(String[]::new)));
        courseComboBox.setPromptText("Chọn khóa học");

        Button registerCourseButton = new Button("Đăng ký khóa học");
        registerCourseButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        registerCourseButton.setOnAction(event -> {
            if (currentStudent != null) {
                handleCourseSelection(courseComboBox.getValue(), true);
            } else {
                showAlert("Đăng nhập yêu cầu", "Vui lòng đăng nhập trước.");
            }
        });

        Button dropCourseButton = new Button("Hủy khóa học");
        dropCourseButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        dropCourseButton.setOnAction(event -> {
            if (currentStudent != null) {
                handleCourseSelection(courseComboBox.getValue(), false);
            } else {
                showAlert("Đăng nhập yêu cầu", "Vui lòng đăng nhập trước.");
            }
        });

        Button viewStudentInfoButton = new Button("Xem thông tin sinh viên");
        viewStudentInfoButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        viewStudentInfoButton.setOnAction(event -> {
            if (currentStudent != null) {
                showStudentInformation(currentStudent.getId());
            } else {
                showAlert("Đăng nhập yêu cầu", "Vui lòng đăng nhập trước.");
            }
        });

        Button backButton = new Button("Trở về");
        backButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
        backButton.setOnAction(event -> primaryStage.setScene(studentScene)); // Trở về màn hình sinh viên

        courseBox.getChildren().addAll(courseTitleLabel, courseComboBox, registerCourseButton, dropCourseButton,
                viewStudentInfoButton, backButton);

        Scene courseScene = new Scene(courseBox, 800, 600);
        primaryStage.setScene(courseScene);
    }

    private void showTeacherManagement() {
        VBox teacherBox = new VBox();
        teacherBox.setPadding(new Insets(10, 10, 10, 10));
        teacherBox.setSpacing(10);
        teacherBox.setStyle("-fx-background-color: #ffe0b2; -fx-border-color: #ff9800; -fx-border-width: 2px;");

        Label teacherTitleLabel = new Label("Quản lý giáo viên");
        teacherTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ComboBox<String> studentComboBox = new ComboBox<>(FXCollections.observableArrayList(
                userManagement.getStudents().stream().map(Student::getId).toArray(String[]::new)));
        studentComboBox.setPromptText("Chọn sinh viên");

        Button viewStudentCoursesButton = new Button("Xem môn học của sinh viên");
        viewStudentCoursesButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        viewStudentCoursesButton.setOnAction(event -> showStudentCourses(studentComboBox.getValue()));

        Button viewStudentsByCourseButton = new Button("Các khóa học");
        viewStudentsByCourseButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        viewStudentsByCourseButton.setOnAction(event -> showStudentsByCourse());

        Button removeStudentBySelectButton = new Button("Xóa sinh viên khỏi khóa học");
        removeStudentBySelectButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        removeStudentBySelectButton.setOnAction(event -> {
            String studentId = studentComboBox.getValue();
            if (studentId != null && !studentId.isEmpty()) {
                showRemoveStudentFromCourse(studentId);
            } else {
                showAlert("Lỗi chọn sinh viên", "Vui lòng chọn một sinh viên.");
            }
        });

            Button backButton = new Button("Trở về");
            backButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
            backButton.setOnAction(event -> primaryStage.setScene(teacherScene)); // Trở về màn hình giáo viên
            
            teacherBox.getChildren().addAll(teacherTitleLabel, studentComboBox, viewStudentCoursesButton,
                    viewStudentsByCourseButton, removeStudentBySelectButton, backButton);

            Scene teacherScene = new Scene(teacherBox, 800, 600);
            primaryStage.setScene(teacherScene);
        }

        private void showRemoveStudentFromCourse(String studentId) {
            Student student = userManagement.getStudentById(studentId);
            if (student != null) {
                VBox courseBox = new VBox();
                courseBox.setPadding(new Insets(10, 10, 10, 10));
                courseBox.setSpacing(10);
                courseBox.setStyle("-fx-background-color: #f3e5f5; -fx-border-color: #8e24aa; -fx-border-width: 2px;");

                Label titleLabel = new Label("Chọn môn học để hủy cho sinh viên " + student.getName());
                titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                ComboBox<String> courseComboBox = new ComboBox<>(FXCollections.observableArrayList(
                        student.getMonHoc().stream().map(Course::getName).toArray(String[]::new)));
                courseComboBox.setPromptText("Chọn môn học");

                Button removeCourseButton = new Button("Hủy môn học");
                removeCourseButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                removeCourseButton.setOnAction(event -> {
                    String courseName = courseComboBox.getValue();
                    if (courseName != null) {
                        Course selectedCourse = student.getMonHoc().stream()
                                .filter(course -> course.getName().equals(courseName))
                                .findFirst()
                                .orElse(null);
                        if (selectedCourse != null) {
                            System.out.println("Selected Course ID: " + selectedCourse.getId()); // Debugging
                            String courseId = selectedCourse.getId();
                            if (courseId != null) {
                                boolean success = registerCourse.studentDropCourse(student, courseId);
                                if (success) {
                                    userManagement.removeStudentCourse(student, courseId); // Cập nhật file
                                    userManagement.writeStudentsToFile(); // Ghi dữ liệu vào file
                                    System.out.println("File đã được cập nhật sau khi xóa môn học.");
                                    showAlert("Thành công",
                                            "Đã hủy môn học " + courseName + " cho sinh viên " + student.getName());
                                    primaryStage.setScene(teacherScene); // Trở về màn hình giáo viên
                                } else {
                                    showAlert("Thất bại",
                                            "Không thể hủy môn học " + courseName + " cho sinh viên " + student.getName());
                                }
                            } else {
                                showAlert("Lỗi", "Mã môn học không hợp lệ.");
                            }
                        } else {
                            showAlert("Thất bại",
                                    "Không tìm thấy môn học " + courseName + " cho sinh viên " + student.getName());
                        }
                    } else {
                        showAlert("Lỗi chọn môn học", "Vui lòng chọn một môn học.");
                    }
                });

                Button backButton = new Button("Trở về");
                backButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
                backButton.setOnAction(event -> primaryStage.setScene(teacherScene)); // Trở về màn hình giáo viên

                courseBox.getChildren().addAll(titleLabel, courseComboBox, removeCourseButton, backButton);

                Scene courseScene = new Scene(courseBox, 800, 600);
                primaryStage.setScene(courseScene);
            } else {
                showAlert("Lỗi", "Không tìm thấy sinh viên với mã số: " + studentId);
            }
        }

        private void showStudentCourses(String studentId) {
            Student student = userManagement.getStudentById(studentId);
            if (student != null) {
                VBox coursesBox = new VBox();
                coursesBox.setPadding(new Insets(10, 10, 10, 10));
                coursesBox.setSpacing(10);
                coursesBox.setStyle("-fx-background-color: #f3e5f5; -fx-border-color: #8e24aa; -fx-border-width: 2px;");

                Label studentCoursesTitle = new Label("Các môn học của sinh viên");
                studentCoursesTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                for (Course course : student.getMonHoc()) {
                    Label courseLabel = new Label(course.getName() + " (" + course.getId() + ")");
                    coursesBox.getChildren().add(courseLabel);
                }

                Button backButton = new Button("Trở về");
                backButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
                backButton.setOnAction(event -> primaryStage.setScene(teacherScene)); // Trở về màn hình giáo viên

                coursesBox.getChildren().add(backButton);

                Scene coursesScene = new Scene(coursesBox, 800, 600);
                primaryStage.setScene(coursesScene);
            } else {
                showAlert("Lỗi", "Không tìm thấy sinh viên với mã số: " + studentId);
            }
        }

       private void handleCourseSelection(String courseName, boolean isRegistration) {
    if (courseName != null) {
        Course selectedCourse = registerCourse.getCourses().stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .orElse(null);
        if (selectedCourse != null) {
            boolean success = isRegistration
                    ? registerCourse.studentRegisterCourse(currentStudent, selectedCourse.getId())
                    : registerCourse.studentDropCourse(currentStudent, selectedCourse.getId()); 
            if (success) {
                userManagement.dataHandler.writeStudentsToFile(userManagement.getStudents()); // Lưu dữ liệu sau khi cập nhật
                ManagementApp.getInstance().refreshTeacherDashboard(); // Làm mới dashboard sau khi thay đổi
                showAlert("Thành công",
                        (isRegistration ? "Đăng ký" : "Hủy đăng ký") + " khóa học thành công cho: " + courseName);
            } else {
                showAlert("Thất bại",
                        "Bạn đã" + (isRegistration ? " đăng ký" : " chưa đăng ký") + " khóa học này.");
            }
        } else {
            showAlert("Thất bại", "Không tìm thấy khóa học.");
        }
    } else {
        showAlert("Lỗi chọn khóa học", "Vui lòng chọn một khóa học.");
    }
}


    private void showStudentsByCourse() {
        VBox coursesBox = new VBox();
        coursesBox.setPadding(new Insets(10, 10, 10, 10));
        coursesBox.setSpacing(10);
        coursesBox.setStyle("-fx-background-color: #f3e5f5; -fx-border-color: #8e24aa; -fx-border-width: 2px;");

        Label studentCoursesTitle = new Label("Danh sách sinh viên theo khóa học");
        studentCoursesTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        for (Course course : registerCourse.getCourses()) {
            Label courseLabel = new Label("Khóa học: " + course.getName() + " (" + course.getId() + ")");
            coursesBox.getChildren().add(courseLabel);

            List<Student> students = currentTeacher.getStudentsByCourseId(course.getId());
            for (Student student : students) {
                Label studentLabel = new Label("  - " + student.getName() + " (" + student.getId() + ")");
                coursesBox.getChildren().add(studentLabel);
            }
        }

        Button backButton = new Button("Trở về");
        backButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
        backButton.setOnAction((event) -> {primaryStage.setScene(teacherScene);}); // Trở về màn hình giáo viên

        coursesBox.getChildren().add(backButton);

        Scene coursesScene = new Scene(coursesBox, 800, 600);
        primaryStage.setScene(coursesScene);
    }

    private void showStudentInformation(String studentId) {
        Student student = userManagement.getStudentById(studentId);
        if (student != null) {
            Stage studentStage = new Stage();
            studentStage.setTitle("Thông tin sinh viên");

            VBox studentBox = new VBox();
            studentBox.setPadding(new Insets(10, 10, 10, 10));
            studentBox.setSpacing(10);
            studentBox.setStyle("-fx-background-color: #f3e5f5; -fx-border-color: #8e24aa; -fx-border-width: 2px;");

            Label studentTitleLabel = new Label("Thông tin sinh viên");
            studentTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label studentNameLabel = new Label("Tên: " + student.getName());
            Label studentIdLabel = new Label("Mã số: " + student.getId());
            Label studentEmailLabel = new Label("Email: " + student.getEmail());
            Label studentCoursesLabel = new Label("Các môn học:");

            VBox coursesBox = new VBox();
            for (Course course : student.getMonHoc()) {
                Label courseLabel = new Label(course.getName() + " (" + course.getId() + ")");
                coursesBox.getChildren().add(courseLabel);
            }

            Button closeButton = new Button("Đóng");
            closeButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;");
            closeButton.setOnAction(event -> studentStage.close());

            studentBox.getChildren().addAll(studentTitleLabel, studentNameLabel, studentIdLabel, studentEmailLabel,
                    studentCoursesLabel, coursesBox, closeButton);

            Scene studentScene = new Scene(studentBox, 500, 500);
            studentStage.setScene(studentScene);
            studentStage.show();
        } else {
            showAlert("Lỗi", "Không tìm thấy sinh viên với mã số: " + studentId);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void refreshTeacherDashboard() {
        showTeacherDashboard();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
