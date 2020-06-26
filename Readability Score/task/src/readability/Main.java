package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Text text = new Text(args[0]);

        System.out.println("The text is:");
        System.out.println("");
        System.out.println("Words: " + text.words.length);
        System.out.println("Sentences: " + text.sentences.length);
        System.out.println("Characters: " + text.characterCount);
        System.out.println("Syllables: " + text.syllables);
        System.out.println("Polysyllables: " + text.polysyllables);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        try {
            var scan = new Scanner(System.in);
            String score = scan.nextLine();
            int age;
            boolean all = score.equals("all");
            if (score.equals("ARI") || all) {
                age = ages((int) Math.ceil(text.ari));
                System.out.printf("Automated Readability Index: %f (about %d year olds)%n", text.ari, age);
            }

            if (score.equals("FK") || all) {
                age = ages((int) Math.ceil(text.fk));
                System.out.printf("Flesch–Kincaid readability tests: %f (about %d year olds)%n", text.fk, age);
            }

            if (score.equals("SMOG") || all) {
                age = ages((int) Math.ceil(text.smog));
                System.out.printf("Simple Measure of Gobbledygook: %f (about %d year olds)%n", text.smog, age);
            }

            if (score.equals("CL") || all) {
                age = ages((int) Math.ceil(text.cl));
                System.out.printf("Coleman–Liau index: %f (about %d year olds)%n", text.cl, age);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static int ages(double score) {
        if (score < 13) {
            return (int) score + 6;
        } else if (score == 13) {
            return 24;
        } else {
            return 99;
        }
    }
}

class Text {
    String text;
    String[] words;
    String[] sentences;
    int characterCount;
    int syllables;
    int polysyllables;
    double ari;
    double fk;
    double smog;
    double cl;

    Text(String filename) {
        try {
            text = readFileAsString(filename);
            words = splitWords(text);
            sentences = splitSentences(text);
            characterCount = countCharacters(text);
            setSyllables();
            ari = calculateAri(characterCount, words.length, sentences.length);
            fk = calculateFk(words.length, sentences.length, syllables);
            smog = calculateSmog(polysyllables, sentences.length);
            cl = calculateCl(characterCount, words.length, sentences.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    double calculateCl(double characters, double words, double sentences) {
        double l = (characters / words) * 100;
        double s = (sentences / words) * 100;
        return 0.0588 * l - 0.296 * s - 15.8;
    }

    double calculateSmog(int polysyllables, double sentences) {
        return 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
    }

    String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    void setSyllables() {
        for (String word : words) {
            int count = countSyllables(word);
            syllables += count;
            if (count > 2) {
                polysyllables += 1;
            }
        }
    }

    int countSyllables(String word) {
        Pattern vowels = Pattern.compile("([aiouyAIOUY]|e\\B|E\\B)+");
        return (int) vowels.matcher(word).results().count();
    }

    String[] splitWords(String text) {
        return text.split("\\s+");
    }

    String[] splitSentences(String text) {
        return text.split("[.?!]");
    }

    int countCharacters(String text) {
        return text.replaceAll("\\s", "").length();
    }

    double calculateAri(double chars, double words, double sentences) {
        return (4.71 * (chars / words)) + (0.5 * (words / sentences)) - 21.43;
    }

    double calculateFk(double words, double sentences, double syllables) {
        return (0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59);
    }
}
