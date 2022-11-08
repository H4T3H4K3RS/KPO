public class Task2 {
    public static boolean isSimple(int n) {
        int cnt = 0;
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                cnt += 1;
        }
        return cnt == 0;
    }

    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++) {
            System.out.print(isSimple(i) ? i + "\n" : "");
        }
    }

}
