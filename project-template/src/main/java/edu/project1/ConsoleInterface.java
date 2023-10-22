package edu.project1;

import java.util.Scanner;

public class ConsoleInterface {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Hangman session = new Hangman();

        while (!session.isLose()) {
            System.out.println("Guess a letter:");
            var input = scanner.nextLine();

            if (input.length() == 1) {
                if (session.isRightGuess(input.charAt(0))) {
                    System.out.println("Hit!");
                    System.out.println(session.getState());
                } else {
                    System.out.println("Missed, mistake " + session.getAttempts()
                        + " out of " + session.getMaxAttempts() + " .");
                }
            }

            if (input.equals("exit")) {
                System.out.println("You gave up!");
                return;
            }

            if (session.isWin()) {
                System.out.println("You win!");
                return;
            }
        }

        System.out.println("You lost!");
    }

    private void printState(Hangman session) {

        session.getState();
    }
}
