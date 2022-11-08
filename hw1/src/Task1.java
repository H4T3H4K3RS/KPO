public class Task1 {
    public static void main(String[] args) {
        int n = 10;
        int[] array = new int[n];
        int sm = 0;
        System.out.print("Array:");
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * 100);
            sm += array[i];
            System.out.print(" ");
            System.out.print(array[i]);
        }
        System.out.print("\n");
        int mx = array[0];
        int mn = array[0];
        for (int i = 0; i < n; i++) {
            if (array[i] > mx) {
                mx = array[i];
            }
            if (array[i] < mn) {
                mn = array[i];
            }
        }
        System.out.print("Avg: ");
        System.out.println(sm / n);
        System.out.print("Max: ");
        System.out.println(mx);
        System.out.print("Min: ");
        System.out.println(mn);
    }
}
