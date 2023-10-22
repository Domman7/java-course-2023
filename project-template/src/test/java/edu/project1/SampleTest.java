package edu.project1;

import edu.hw1.EvenArrayUtils;
import edu.hw2.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.Main.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SampleTest {
    @DisplayName("Тест победы")
    @Test
    void hangmanWinTest() {
        String[] wordsBank = new String[]{"hello"};
        Hangman session = new Hangman(wordsBank);
        session.isRightGuess('h');
        session.isRightGuess('e');
        session.isRightGuess('l');
        session.isRightGuess('o');

        assertEquals(true, session.isWin());
    }

    @DisplayName("Тест поражения")
    @Test
    void hangmanLoseTest() {
        String[] wordsBank = new String[]{"hello"};
        Hangman session = new Hangman(wordsBank);
        session.isRightGuess('i');
        session.isRightGuess('i');
        session.isRightGuess('i');
        session.isRightGuess('i');
        session.isRightGuess('i');

        assertEquals(true, session.isLose());
    }

    @DisplayName("Тест банка слов")
    @Test
    void hangmanWordsBankTest() {
        String[] wordsBank = new String[]{"hello", ""};
        Throwable exception = assertThrows(RuntimeException.class, () -> new Hangman(wordsBank));
    }
}
