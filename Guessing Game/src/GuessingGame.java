/*
About: Guessing Country Game
Author: Muhtasim Fuad Chowdhury
Reference:  https://gist.github.com/SedaKunda/79e1d9ddc798aec3a366919f0c14a078,
            https://www.quora.com/How-do-I-pick-up-a-random-string-from-an-array-of-strings-in-Java
*/

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    private static final String[] countries = {"UNITED STATES", "CHINA", "UNITED KINGDOM", "GERMANY", "FRANCE", "RUSSIA", "JAPAN", "UNITED ARAB EMIRATES", "SAUDI ARABIA", "ITALY"};
    private static final int LIVES = 3;
    private static int incorrectGuesses = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean exit = false;

    static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        while (!exit) {
            String randCountry = generateRandomCountry();
            String wordCount = generateMaskedCountry(randCountry);
            displayGameRules();
            playGame(wordCount, randCountry);
            exit = continueGame(exit);
        }
        scanner.close();
    }

    private static String generateRandomCountry() {
        Random random = new Random();
        int index = random.nextInt(countries.length);
        return countries[index];
    }

    private static String generateMaskedCountry(String country) {
        String word = new String(new char[country.length()]);
        return word.replace("\0", "_");
    }

    private static void displayGameRules() {
        System.out.println("Country Guessing Game!!!");
        System.out.println("___________________________________________________");
        System.out.println("Guess a random country name");
        System.out.println("If it is incorrect, you lose a life");
        System.out.println("You have 3 lives");
        System.out.println("Good Luck! ;)");
        System.out.println("___________________________________________________");
        System.out.println("BEGIN!!!");
    }

    private static void playGame(String maskedCountry, String randCountry) {
        while (incorrectGuesses < LIVES) {
            System.out.println("Guess a country name: ");
            System.out.println(maskedCountry);
            String guess = scanner.nextLine();

            if (Objects.equals(guess.toUpperCase(), randCountry)) {
                System.out.println("The country was indeed " + randCountry);
                System.out.println("Congratulations!!! You win!!!");
                break;
            }
            else {
                incorrectGuesses++;
                if (incorrectGuesses < LIVES) {
                    System.out.println("WRONG! Try again");
                    System.out.println("You have " + (LIVES - incorrectGuesses) + " Lives");
                }
                else {
                    System.out.println("WRONG! You ran out of Lives");
                    System.out.println("The correct country was " + randCountry);
                    System.out.println("You lose :(");
                }
            }
        }
    }

    public static boolean continueGame(boolean exit) {
        while (true) {
            System.out.println("Would you like to play again? (Y or N): " );
            String playAgain = scanner.nextLine().toUpperCase();
            try {
                if (playAgain.equals("Y")) {
                    incorrectGuesses = 0;
                    return false;
                }
                else if (playAgain.equals("N")) {
                    return true;
                }
                else {
                    throw new InvalidInputException("Invalid Input! Please Enter Y or N");
                }
            }
            catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}