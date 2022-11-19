public class Student {
    String name;
    boolean absent;
    int mark;

    public Student(String name) {
        this.name = name;
        this.mark = 0;
        this.absent = false;
    }

    public boolean setMark(int mark) {
        if (mark < 0 || mark > 10)
            return false;
        this.mark = mark;
        return true;
    }
}
