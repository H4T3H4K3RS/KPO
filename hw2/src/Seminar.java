import java.util.*;

public class Seminar {
    List<Student> students = new ArrayList<>();
    List<Student> respondents = new ArrayList<>();

    public Seminar(List<Student> students) {
        this.students.addAll(students);
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
            choice = false;
        }
        return true;
    }

    public boolean generateList() {
        if (this.students.size() == 0)
            return false;
        System.out.println("Name\tAbsent\tMark");
        for (Student student : this.students) {
            System.out.printf("%s\t%s\t%d\n", student.name, student.absent ? "y" : "n", student.mark);
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
                "1. /r - choose random student\n" +
                        "2. /l - list of student with grades\n" +
                        "3. /e - Exit\n" +
                        "4. /h - Show this message"
        );
        boolean run = true;
        while (run) {
            run = handler();
        }
        System.out.println("See ya!");
    }
}
