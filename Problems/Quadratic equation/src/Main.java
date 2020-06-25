import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);

        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();

        double plusOrMinus = Math.sqrt(Math.pow(b, 2) - 4 * a * c);
        double x1 = (-b + plusOrMinus) / (2 * a);
        double x2 = (-b - plusOrMinus) / (2 * a);

        System.out.println(Math.min(x1, x2) + " " + Math.max(x1, x2));
    }
}