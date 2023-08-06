import java.util.Scanner;
import java.io.PrintWriter;

public class diamond {
    public static void main(String[] args) {
        PrintWriter output = new PrintWriter(System.out, false);
        Scanner scan = new Scanner(System.in);
        int k = scan.nextInt();
        int n = scan.nextInt();

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n + i; j++) {
                if ((j >= n - i && j < n - i + k) || (j > n + i - k && j >= n)) output.print("*");
                else output.print(" ");
            }
            output.println();
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= n + i; j++) {
                if ((j >= n - i && j < n - i + k) || (j > n + i - k && j >= n)) output.print("*");
                else output.print(" ");
            }
            output.println();
        }
        output.close();
    }
}
