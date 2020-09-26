package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadabilityScorer {
    private HashMap<Integer, Integer> scoreToAgeTable;
    //private static final Logger logger = Logger.getLogger(ReadabilityScorer.class.getName());
    private String text;
    private String option;
    private List<Integer> ages;
    private int words;
    private int sentences;
    private int characters;
    private int syllables;
    private int polysyllables;

    private double automatedReadabilityScore;
    private double fleshCincadeScore;
    private double smogScore;
    private double colemanLiauScore;

    public ReadabilityScorer() {
        this.scoreToAgeTable = new HashMap<>();
        this.scoreToAgeTable.put(1, 6);
        this.scoreToAgeTable.put(2, 7);
        this.scoreToAgeTable.put(3, 9);
        this.scoreToAgeTable.put(4, 10);
        this.scoreToAgeTable.put(5, 11);
        this.scoreToAgeTable.put(6, 12);
        this.scoreToAgeTable.put(7, 13);
        this.scoreToAgeTable.put(8, 14);
        this.scoreToAgeTable.put(9, 15);
        this.scoreToAgeTable.put(10, 16);
        this.scoreToAgeTable.put(11, 17);
        this.scoreToAgeTable.put(12, 18);
        this.scoreToAgeTable.put(13, 24);
        this.scoreToAgeTable.put(14, 25);
        this.ages = new ArrayList<>();
    }

    public void loadText(String path) {
        text = new String();
        File file = new File(path);
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                text = text + scanner.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find requested file!");
        }
    }

    public void calculateScore(String option) {
        this.option = option;
        switch(option) {
            case "ARI":
                this.automatedReadabilityScore = automatedReadabilityIndex();
                break;
            case "FK":
                this.fleshCincadeScore = calculateFleshCincadeScore();
                break;
            case "SMOG":
                this.smogScore = calculateSmogScore();
                break;
            case "CL":
                this.colemanLiauScore = calculateColemanLiauScore();
                break;
            case "all":
                this.automatedReadabilityScore = automatedReadabilityIndex();
                this.fleshCincadeScore = calculateFleshCincadeScore();
                this.smogScore = calculateSmogScore();
                this.colemanLiauScore = calculateColemanLiauScore();
                break;
            default:
                break;
        }
    }

    public void text() {
        System.out.println("The text is:");
        System.out.println(this.text);
    }
    public void analyseText() {
        basicCharacteristics();
    }

    public void printBasicTextCharacteristics() {
        System.out.println("Words: " + this.words);
        System.out.println("Sentences: " + this.sentences);
        System.out.println("Characters: " + this.characters);
        System.out.println("Syllables: " + this.syllables);
        System.out.println("Polysyllables: " + this.polysyllables);

    }

    private void basicCharacteristics() {
        var allWords = text.split("\\s");
        this.characters = text.replaceAll("\\s","").split("").length;
        this.words = allWords.length;
        this.sentences = text.split("[.!?]").length;
        this.syllables = calculateSyllables(allWords);
        this.polysyllables = calculatePolysyllables(allWords);
    }

    private double automatedReadabilityIndex() {
         double score = 4.71 * (double) this.characters / this.words + 0.5 * (double) this.words / this.sentences - 21.43;
         return score;
    }

    private double calculateFleshCincadeScore() {
        double score = 0.39 * this.words / this.sentences + 11.8 * this.syllables / this.words - 15.59;
        return score;
    }

    private int calculateSyllables(String[] words) {
        int totalNumberOfSyllables = 0;
        for (var word : words) {
            totalNumberOfSyllables += countSyllables(word);
        }

        return totalNumberOfSyllables;
    }


    private double calculateSmogScore() {

        return 1.043 * Math.sqrt(this.polysyllables * 30 / this.sentences) + 3.1291;
     }

    private int calculatePolysyllables(String[] words) {
        int totalNumberOfPolysyllables = 0;
        for (var word : words) {
            int syllables = countSyllables(word);
            if( syllables >= 3) {
                ++totalNumberOfPolysyllables;
            }
        }
        return totalNumberOfPolysyllables;
    }

    private double calculateColemanLiauScore() {
        double score = 0.0588 * (double)characters / words * 100 - 0.296 * (double)sentences / words * 100 - 15.8;
        return score;
    }

    protected int countSyllables(String word)
    {
        // TODO: Implement this method so that you can call it from the
        // getNumSyllables method in BasicDocument (module 1) and
        // EfficientDocument (module 2).
        int count = 0;
        word = word.toLowerCase();

        if (word.charAt(word.length()-1) == 'e') {
            if (silente(word)){
                String newword = word.substring(0, word.length()-1);
                count = count + countit(newword);
            } else {
                count++;
            }
        } else {
            count = count + countit(word);
        }
        return count;
    }

    private int countit(String word) {
        int count = 0;
        Pattern splitter = Pattern.compile("[^aeiouy]*[aeiouy]+");
        Matcher m = splitter.matcher(word);

        while (m.find()) {
            count++;
        }
        return count;
    }

    private boolean silente(String word) {
        word = word.substring(0, word.length()-1);

        Pattern yup = Pattern.compile("[aeiouy]");
        Matcher m = yup.matcher(word);

        if (m.find()) {
            return true;
        } else
            return false;
    }
    public void printScore() {
        switch(this.option) {
            case "ARI":
                printAutomatedReadabilityScore();
                break;
            case "FK":
                printFleshCincadeScore();
                break;
            case "SMOG":
                printSmogScore();
                break;
            case "CL":
                printColemanLiauScore();
                break;
            case "all":
                printAutomatedReadabilityScore();
                printFleshCincadeScore();
                printSmogScore();
                printColemanLiauScore();
                break;
            default:
                break;
        }
    }

    private void printAutomatedReadabilityScore() {
        Integer roundedScore = roundScore(this.automatedReadabilityScore);
        Integer age = scoreToAgeTable.get(roundedScore);
        ages.add(age);
        if (age.equals(25)) {
            System.out.println("Automated Readability Index: " + this.automatedReadabilityScore + " (about " + age + "+ " + "year " + "olds" + " )." );
        } else {
            System.out.println("Automated Readability Index: " + this.automatedReadabilityScore + " (about " + age + " year " + "olds" + " ).");
        }
    }

    private void printFleshCincadeScore() {
        int roundedScore = roundScore(this.fleshCincadeScore);
        Integer age = scoreToAgeTable.get(roundedScore);
        ages.add(age);
        if (age.equals(25)) {

            System.out.println("Flesch–Kincaid readability tests: " + this.fleshCincadeScore + " (about " + age + "+ "+ "year " + "olds" + " )." );
        } else {
            System.out.println("Flesch–Kincaid readability tests: " + this.fleshCincadeScore + " (about " + age + " year " + "olds" + " )." );
        }
    }

    private void printSmogScore() {
        Integer roundedScore = roundScore(this.smogScore);
        System.out.println(roundedScore);
        Integer age = scoreToAgeTable.get(roundedScore);
        ages.add(age);
        if (age.equals(25)) {
            System.out.println("Simple Measure of Gobbledygook: " + this.smogScore + " (about " + age + "+ " + "year " + "olds" + " )." );
        } else {
            System.out.println("Simple Measure of Gobbledygook: " + this.smogScore + " (about " + age + " year " + "olds" + " )." );
        }
    }

    private void printColemanLiauScore() {
        Integer roundedScore = roundScore(this.colemanLiauScore);
        Integer age = scoreToAgeTable.get(roundedScore);
        ages.add(age);
        if (age.equals(25)) {
            System.out.println("Coleman–Liau index: " + this.colemanLiauScore + " (about " + age + "+" +" year " + "olds" + " )." );
        } else {
            System.out.println("Coleman–Liau index: " + this.colemanLiauScore + " (about " + age + " year " + "olds" + " )." );
        }
    }

    private int roundScore(double score) {
        return (int)Math.round(score);
    }

    public void printAverageAge() {
        double sum = 0;
        for (var age : ages) {
            sum += age;
        }
        double averageAge = sum / ages.size();
        System.out.println("This text should be understood in average by " + averageAge + " years olds");
    }
}
