package edu.project1;

import org.jetbrains.annotations.NotNull;
import java.util.Random;

public class HangmanWordsBank implements WordsBank {
    private final String[] wordsBank;

    public HangmanWordsBank(String[] wordsBank) {
        this.wordsBank = wordsBank;
    }

    @NotNull public String randomWord() {
        Random random = new Random();
        var len = wordsBank.length;

        if (len == 1) {

            return wordsBank[0];
        } else {

            return wordsBank[random.nextInt(wordsBank.length - 1)];
        }
    }
}
