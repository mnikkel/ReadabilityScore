package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String text = readFileAsString(args[0]);

            String[] words = splitWords(text);
            String[] sentences = splitSentences(text);
            int characterCount = countCharacters(text);
            double score = calculateReadability(characterCount, words.length, sentences.length);
            String age = ages((int) Math.ceil(score));

            System.out.println("The text is:");
            System.out.println("");
            System.out.println("Words: " + words.length);
            System.out.println("Sentences: " + sentences.length);
            System.out.println("Characters: " + characterCount);
            System.out.println("The score is: " + score);
            System.out.println("This text should be understood by " + age + " year olds.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String ages(int score) {
        switch (score) {
            case 1:
                return "5-6";
            case 2:
                return "6-7";
            case 3:
                return "7-9";
            case 4:
                return "9-10";
            case 5:
                return "10-11";
            case 6:
                return "11-12";
            case 7:
                return "12-13";
            case 8:
                return "13-14";
            case 9:
                return "14-15";
            case 10:
                return "15-16";
            case 11:
                return "16-17";
            case 12:
                return "17-18";
            case 13:
                return "18-24";
            case 14:
                return "24+";
            default:
                return "not defined";
        }
    }

    public static double calculateReadability(double chars, double words, double sentences) {
        return (4.71 * (chars / words)) + (0.5 * (words / sentences)) - 21.43;
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static int countCharacters(String text) {
        return text.replaceAll("\\s", "").length();
    }

    public static String[] splitWords(String text) {
        return text.split("\\s+");
    }

    public static String[] splitSentences(String text) {
        return text.split("[.?!]");
    }
}
