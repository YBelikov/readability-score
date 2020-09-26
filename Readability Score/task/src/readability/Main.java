package readability;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    public static void main(String[] args)
    {
        ReadabilityScorer scorer = new ReadabilityScorer();
        scorer.loadText(args[0]);
        scorer.text();
        scorer.analyseText();
        scorer.printBasicTextCharacteristics();
        System.out.println("Enter score you want to calculate (AFI, FK, SMOG, CL, all): ");
        String option = new String();
        final Scanner sc = new Scanner(System.in);
        option = sc.nextLine();
        scorer.calculateScore(option);
        scorer.printScore();
        scorer.printAverageAge();
    }
}
