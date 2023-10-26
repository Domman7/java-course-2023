package edu.project1;

import edu.hw1.EvenArrayUtils;
import edu.hw2.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.Main.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("Тест победы")
    @Test
    void hangmanWinTest() {
        HangmanWordsBank wordsBank = new HangmanWordsBank(
            new String[] {"hello"}
        );
        Hangman session = new Hangman(wordsBank.randomWord());
        HangmanState state = session.guess('h');
        state = session.guess('e');
        state = session.guess('l');
        state = session.guess('o');

        assertTrue(state instanceof HangmanState.Win);
    }

    @DisplayName("Тест поражения")
    @Test
    void hangmanLoseTest() {
        HangmanWordsBank wordsBank = new HangmanWordsBank(
            new String[] {"hello"}
        );
        Hangman session = new Hangman(wordsBank.randomWord());
        HangmanState state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');

        assertTrue(state instanceof HangmanState.Defeat);
    }

    @DisplayName("Тест банка слов")
    @Test
    void hangmanWordsBankTest() {
        HangmanWordsBank wordsBank = new HangmanWordsBank(
            new String[] {""}
        );
        Throwable exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Hangman(wordsBank.randomWord())
        );
    }

    @DisplayName("Тест состояний")
    @Test
    void hangmanStatesTest() {
        HangmanWordsBank wordsBank = new HangmanWordsBank(
            new String[] {"hello"}
        );
        Hangman session = new Hangman(wordsBank.randomWord());
        HangmanState state = session.guess('i');
        assertTrue(state instanceof HangmanState.FailedGuess);

        state = session.guess('h');
        assertTrue(state instanceof HangmanState.SuccessfulGuess);

        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        state = session.guess('i');
        assertTrue(state instanceof HangmanState.Defeat);

        state = session.guess('e');
        assertTrue(state instanceof HangmanState.Defeat);
    }
}
