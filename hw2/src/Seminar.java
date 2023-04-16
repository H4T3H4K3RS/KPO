import java.sql.*;
import java.util.*;

public class Seminar {
    List<Student> students = new ArrayList<>();
    List<Student> respondents = new ArrayList<>();
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/students";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "3x4mpl3passw0rd";


    public void initializeDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "absent BOOLEAN," +
                    "mark INT" +
                    ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudentsToDB(List<Student> students) {
        System.out.print("Adding students to database. Those who already are in db will be reset.\n");
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO students (name, absent, mark) VALUES (?, ?, ?)"
            );
            for (Student student : students) {
                String query = "SELECT COUNT(*) FROM students WHERE name = ?";
                PreparedStatement countStmt = conn.prepareStatement(query);
                countStmt.setString(1, student.name);
                ResultSet resultSet = countStmt.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                if (count > 0) {
                    student.absent = false;
                    student.mark = 0;
                    this.updateStudent(student);
                    continue;
                }
                pstmt.setString(1, student.name);
                pstmt.setBoolean(2, student.absent);
                pstmt.setInt(3, student.mark);
                pstmt.executeUpdate();
            }
            System.out.print("Students added to database. Those who already were in db were reset.\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void retrieveStudentsFromDB() {
        this.students = new ArrayList<Student>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT name, absent, mark FROM students";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                boolean absent = resultSet.getBoolean("absent");
                int mark = resultSet.getInt("mark");
                Student student = new Student(name);
                student.absent = absent;
                student.mark = mark;
                this.students.add(student);
            }
            System.out.println("Students retrieved from DB [" + students.size() + "]");
        } catch (SQLException e) {
            System.out.println("Error retrieving students from DB: " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE students SET mark = ?, absent = ? WHERE name = ?"
            );
            pstmt.setInt(1, student.mark);
            pstmt.setBoolean(2, student.absent);
            pstmt.setString(3, student.name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Seminar(List<Student> students) {
        this.initializeDB();
        this.retrieveStudentsFromDB();
        this.addStudentsToDB(students);
    }


    public boolean chooseStudent() {
        boolean choice = true;
        while (choice) {
            if (this.respondents.size() == this.students.size())
                return false;
            boolean run = true;
            Random rand = new Random();
            int place = rand.nextInt(this.students.size());
            Student randomStudent = this.students.get(place);
            while (run) {
                place = rand.nextInt(this.students.size());
                randomStudent = this.students.get(place);
                boolean found = true;
                for (Student respondent : this.respondents) {
                    if (randomStudent.name.equals(respondent.name)) {
                        found = false;
                        break;
                    }
                }
                run = !found;
            }
            this.respondents.add(randomStudent);
            Scanner scanner = new Scanner(System.in);
            System.out.printf("> Student %s is responding\n", randomStudent.name);
            System.out.println("> Is he absent? (y/n)");
            boolean isAbsent = scanner.nextLine().equals("y");
            randomStudent.absent = isAbsent;
            randomStudent.mark = 0;
            if (isAbsent) {
                this.students.set(place, randomStudent);
                this.updateStudent(randomStudent);
                continue;
            }
            System.out.println("> Student is answering....");
            System.out.println("> What mark did the student get? (0-10)");
            while (true) {
                System.out.print("> ");
                int mark = Integer.parseInt(scanner.nextLine());
                if (randomStudent.setMark(mark)) {
                    break;
                }
                System.out.println("> Incorrect mark. Try again. (0-10)");
            }
            System.out.printf("> Student %s has received %d. Saved.\n", randomStudent.name, randomStudent.mark);
            this.students.set(place, randomStudent);
            this.updateStudent(randomStudent);
            choice = false;
        }
        return true;
    }

    public boolean generateList() {
        this.retrieveStudentsFromDB();
        if (this.students.size() == 0)
            return false;
        System.out.format("%-45s%-10s%s%n", "Name", "Absent", "Mark");
        System.out.format("%-45s%-10s%s%n", "------------------------------", "---------", "---");
        for (Student student : this.students) {
            System.out.format("%-45s%-10s%s%n", student.name, student.absent ? "y" : "n", student.mark);
        }
        return true;
    }


    public boolean handler() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String command = scanner.nextLine();
        if (command.equals("/e")) {
            System.out.println("");
            return false;
        }
        switch (command) {
            case "/h" -> {
                System.out.println(
                        "1. /r - choose random student\n" +
                                "2. /l - list of student with grades\n" +
                                "3. /e - Exit\n" +
                                "4. /h - Show this message"
                );
            }
            case "/r" -> {
                boolean status = this.chooseStudent();
                if (!status)
                    System.out.println("All students already responded!");
            }
            case "/l" -> {
                boolean status = this.generateList();
                if (!status)
                    System.out.println("No students chosen!");
            }
            default -> {
                System.out.println("Incorrect command!");
            }
        }
        return true;
    }

    public void start() {
        System.out.println(
                """
                        1. /r - choose random student
                        2. /l - list of student with grades
                        3. /e - Exit
                        4. /h - Show this message"""
        );
        boolean run = true;
        while (run) {
            run = handler();
        }
        System.out.println("See ya!");
    }
}
