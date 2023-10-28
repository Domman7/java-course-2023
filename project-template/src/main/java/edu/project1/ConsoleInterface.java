package edu.project1;

import java.util.Scanner;

public class ConsoleInterface {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        HangmanWordsBank wordsBank = new HangmanWordsBank(
            new String[] {"hello", "aircraft", "array", "cafe", "sunrise"}
        );
        Hangman session = new Hangman(wordsBank.randomWord());
        HangmanState state = null;

        while (!(state instanceof HangmanState.Win || state instanceof HangmanState.Defeat)) {
            System.out.println("Guess a letter:");
            var input = scanner.nextLine();

            if (input.equals("exit")) {
                state = session.giveUp();
                printState(state);
                break;
            } else if (input.length() == 1) {
                state = tryGuess(session, input.charAt(0));
                printState(state);
            }
        }
    }

    private static HangmanState tryGuess(Hangman session, char input) {

        return session.guess(input);
    }

    private static void printState(HangmanState guess) {

        System.out.println(guess.message());
    }
}

