import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();
        double p = (a + b + c) / 2;

        double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));

        System.out.println(area);
    }
}