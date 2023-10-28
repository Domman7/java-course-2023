package edu.project1;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Random;

public class Hangman {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;

    public Hangman(String answer) {
        if (isValidAnswer(answer)) {
            maxAttempts = 5;
            attempts = 0;
            this.answer = answer;
            userAnswer = new char[answer.length()];
            Arrays.fill(userAnswer, '*');
        } else {
            throw new IllegalArgumentException("This answer is not valid");
        }
    }

    @NotNull
    public HangmanState guess(char guess) {
        boolean isCorrect = false;

        if (attempts <= maxAttempts) {
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == guess) {
                    isCorrect = true;
                    userAnswer[i] = guess;
                }
            }
        }

        if (isCorrect) {
            if (answer.equals(new String(userAnswer))) {

                return new HangmanState.Win(userAnswer, attempts, maxAttempts,
                    "You won!\nThe correct answer is: " + answer
                );
            } else {

                return new HangmanState.SuccessfulGuess(userAnswer, attempts, maxAttempts,
                    "Hit!\n" + new String(userAnswer)
                );
            }
        } else {
            attempts++;
            if (attempts > maxAttempts) {

                return new HangmanState.Defeat(answer.toCharArray(), attempts, maxAttempts, "You lost!");
            } else {

                return new HangmanState.FailedGuess(userAnswer, attempts, maxAttempts,
                    "Missed, mistake " + attempts + " out of " + maxAttempts
                        + ".\n" + new String(userAnswer)
                );
            }
        }
    }

    @NotNull
    public HangmanState giveUp() {

        return new HangmanState.Defeat(answer.toCharArray(), attempts, maxAttempts, "You gave up!");
    }

    boolean isValidAnswer(String word) {

        return word.length() != 0;
    }
}
