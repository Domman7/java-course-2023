package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("1. Длина видео")
    @Test
    public void minutesToSecondsTest() {
        String[] cases = new String[] {"01:00", "13:56", "10:60", "1:-1", "-1:1", "123"};
        int[] expected = new int[] {60, 836, -1, -1, -1, -1};

        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], Main.minutesToSeconds(cases[i]));
        }
    }

    @DisplayName("2. Количество цифр")
    @Test
    public void testCountDigits() {
        int[] cases = new int[] {4666, 544, 0, -0, -1, -10, Integer.MIN_VALUE};
        int[] expected = new int[] {4, 3, 1, 1, 1, 2, 10};

        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], Main.countDigits(cases[i]));
        }
    }

    @DisplayName("3. Вложенный массив")
    @Test
    public void testIsNestable() {
        assertTrue(Main.isNestable(new int[] {1, 2, 3, 4}, new int[] {0, 6}));
        assertTrue(Main.isNestable(new int[] {3, 1}, new int[] {4, 0}));
        assertFalse(Main.isNestable(new int[] {9, 9, 8}, new int[] {8, 9}));
        assertFalse(Main.isNestable(new int[] {1, 2, 3, 4}, new int[] {2, 3}));
        assertFalse(Main.isNestable(new int[] {}, new int[] {2, 3}));
        assertFalse(Main.isNestable(new int[] {1, 2, 3, 4}, new int[] {}));
    }

    @DisplayName("4. Сломанная строка")
    @Test
    public void testFixString() {
        String[] cases = new String[] {"123456", "hTsii  s aimex dpus rtni.g", "badce", "a", ""};
        String[] expected = new String[] {"214365", "This is a mixed up string.", "abcde", "a", ""};

        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], Main.fixString(cases[i]));
        }
    }

    @DisplayName("5. Особый палиндром")
    @Test
    public void testIsPalindromeDescendant() {
        int[] cases = new int[] {11211230, 13001120, 23336014, 11, 10, 0, -10, -11211230};
        boolean[] expected =
            new boolean[] {true, true, true, true, false, false, false, false};

        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], Main.isPalindromeDescendant(cases[i]));
        }
    }

    @DisplayName("6. Постоянная Капрекара")
    @Test
    public void testCountK() {
        int[] cases = new int[] {3524, 6621, 6554, 1234, 1111};
        int[] expected = new int[] {3, 5, 4, 3, -1};

        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], Main.countK(cases[i]));
        }
    }

    @DisplayName("7. Циклический битовый сдвиг")
    @Test
    public void testRotate() {
        assertEquals(4, Main.rotateRight(8, 1));
        assertEquals(1, Main.rotateLeft(16, 1));
        assertEquals(6, Main.rotateLeft(17, 2));
        assertEquals(8, Main.rotateLeft(16, -1));
        assertEquals(Main.rotateRight(16, 1), Main.rotateLeft(16, -1));
    }

    @DisplayName("8. Кони на доске")
    @Test
    public void testKnightBoardCapture() {
        int[][] firstBoard = {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };
        assertTrue(Main.knightBoardCapture(firstBoard));

        int[][] secondBoard = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };
        assertFalse(Main.knightBoardCapture(secondBoard));

        int[][] thirdBoard = {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        };
        assertFalse(Main.knightBoardCapture(thirdBoard));
    }
}
