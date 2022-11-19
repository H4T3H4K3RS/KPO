import java.util.ArrayList;
import java.util.List;

public class Task1 {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Амирханов Никита Русланович"));
        students.add(new Student("Артемов Никита Владиславович"));
        students.add(new Student("Барков Борис Геннадьевич"));
        students.add(new Student("Бесшапов Алексей Павлович"));
        students.add(new Student("Боссерт Александра Максимовна"));
        students.add(new Student("Гладких Иван Дмитриевич"));
        students.add(new Student("Гусев Андрей Вадимович"));
        students.add(new Student("Елушов Егор Валерьевич"));
        students.add(new Student("Жуковский Дмитрий Романович"));
        students.add(new Student("Калабай Михаил Иванович"));
        students.add(new Student("Клычкова Анастасия Дмитриевна"));
        students.add(new Student("Корякин Владимир Александрович"));
        students.add(new Student("Котовский Семён Олегович"));
        students.add(new Student("Кузнецов Максим Вадимович"));
        students.add(new Student("Лунькова Дарья Владимировна"));
        students.add(new Student("Назарова Екатерина Андреевна"));
        students.add(new Student("Нигай Александр Андреевич"));
        students.add(new Student("Овчинников Всеволод Александрович"));
        students.add(new Student("Олейник Юлия"));
        students.add(new Student("Осипова Елизавета Владимировна (староста)"));
        students.add(new Student("Порошин Илья Станиславович"));
        students.add(new Student("Порфирьев Антон Сергеевич"));
        students.add(new Student("Русанов Андрей Александрович"));
        students.add(new Student("Старцев Евгений Борисович"));
        students.add(new Student("Талалаев Геннадий Алексеевич"));
        students.add(new Student("Федотов Владислав Витальевич"));
        students.add(new Student("Фирсов Федор Александрович"));
        students.add(new Student("Черкасский Виталий Александрович"));
        students.add(new Student("Шарапов Егор Сергеевич"));
        students.add(new Student("Швецов Данил Игоревич"));
        students.add(new Student("Шиндяпкин Илья Дмитриевич"));
        Seminar seminar = new Seminar(students);
        seminar.start();
    }

}
