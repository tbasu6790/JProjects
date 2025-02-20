//"Guess the Number" game, random numbers from 1 to 500.

import java.util.Scanner;
import java.util.Random;

class Game {
    private int number;  // Random number to be guessed
    private int noOfGuesses;   // Number of attempts

    // Constructor to generate a random number
    public Game() {
        Random rand = new Random();
        this.number = rand.nextInt(500) + 1; // Random number between 1 and 100
        this.noOfGuesses = 0;
    }

    // Getter and setter for noOfGuesses
    public int getNoOfGuesses() {
        return noOfGuesses;
    }

    public void setNoOfGuesses(int noOfGuesses) {
        this.noOfGuesses = noOfGuesses;
    }

    // Method to take user input
    public int takeUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your guess: ");
        return sc.nextInt();
    }

    // Method to check if the guess is correct
    public boolean isCorrectNumber(int userGuess) {
        noOfGuesses++; // Increment the number of attempts

        if (userGuess == number) {
            System.out.println("Congratulations! You guessed the correct number in " + noOfGuesses + " attempts.");
            return true;
        } else if (userGuess < number) {
            System.out.println("Too low! Try again.");
        } else {
            System.out.println("Too high! Try again.");
        }
        return false;
    }
}

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean playAgain = true;

        System.out.println("Welcome to the Guess the Number game!");

        // Main game loop
        while (playAgain) {
            Game game = new Game();
            boolean hasWon = false;

            // Inner loop to play the game
            while (!hasWon) {
                int userGuess = game.takeUserInput();
                hasWon = game.isCorrectNumber(userGuess);
            }

            // Ask the user if they want to play again
            System.out.print("Do you want to play again? (yes/no): ");
            String response = sc.next().toLowerCase();

            if (!response.equals("yes")) {
                playAgain = false;
                System.out.println("Thanks for playing! Goodbye.");
            }
        }
        sc.close();
    }
}