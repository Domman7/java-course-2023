package edu.project1;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Random;

public class Hangman {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;
    private final String[] wordsBank;

    public char[] getUserAnswer() {

        return userAnswer;
    }

    public int getAttempts() {

        return attempts;
    }

    public int getMaxAttempts() {

        return maxAttempts;
    }

    public Hangman() {
        maxAttempts = 5;
        attempts = 0;
        wordsBank = new String[] {"hello", "aircraft", "array", "cafe", "sunrise"};
        answer = RandomWord();
        userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, '*');
    }

    public Hangman(String[] wordsBank) {
        maxAttempts = 5;
        attempts = 0;
        this.wordsBank = wordsBank.clone();
        if(isValidWordsBank()) {
            answer = RandomWord();
            userAnswer = new char[answer.length()];
            Arrays.fill(userAnswer, '*');
        } else {
            throw new RuntimeException("Bank of words is not valid!");
        }
    }

    boolean isRightGuess(char guess) {
        boolean res = false;

        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == guess) {
                res = true;
                userAnswer[i] = guess;
            }
        }

        if (!res) {
            attempts++;
        }

        return res;
    }

    String getState() {

        return new String(userAnswer);
    }

    boolean isLose() {

        return maxAttempts == attempts;
    }

    boolean isWin() {

        return answer.equals(new String(userAnswer));
    }

    boolean isValidWordsBank() {
        for (String word:
             wordsBank) {
            if(word.length() == 0) {
                return false;
            }
        }

        return true;
    }

    @NotNull String RandomWord() {
        Random random = new Random();
        var len = wordsBank.length;
        String word = null;

        if (len == 1) {

            return wordsBank[0];
        } else {

            return wordsBank[random.nextInt(wordsBank.length - 1)];
        }
    }
}
