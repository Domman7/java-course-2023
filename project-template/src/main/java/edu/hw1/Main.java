package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    public static void main(String[] args) {
        sayHello();
    }

    //0. Привет, мир!
    public static void sayHello() {
        LOGGER.info("Привет, мир!");
    }

    //1. Длина видео
    public static int minutesToSeconds(String videoLength) {
        var len = videoLength.split(":");

        if (len.length != 2) {

            return -1;
        }

        int min = Integer.parseInt(len[0]);
        int sec = Integer.parseInt(len[1]);

        if (sec >= 60 || sec < 0 || min < 0) {

            return -1;
        } else {

            return min * 60 + sec;
        }
    }

    //2. Количество цифр
    public static int countDigits(int number) {
        if (number == 0) {

            return 1;
        }

        int res = 0;

        if (number == Integer.MIN_VALUE) {
            number = Integer.MAX_VALUE;
        } else {
            number = Math.absExact(number);
        }

        while (number > 0) {
            res++;
            number /= 10;
        }

        return res;
    }

    //3. Вложенный массив
    public static boolean isNestable(int[] first, int[] second) {
        if (first.length == 0 || second.length == 0) {

            return false;
        }

        return Arrays.stream(first).min().getAsInt() > Arrays.stream(second).min().getAsInt()
            && Arrays.stream(first).max().getAsInt() < Arrays.stream(second).max().getAsInt();
    }

    //4. Сломанная строка
    public static String fixString(String str) {
        var charArray = str.toCharArray();

        for (int i = 0; i < charArray.length - 1; i += 2) {
            char temp = charArray[i];
            charArray[i] = charArray[i + 1];
            charArray[i + 1] = temp;
        }

        return String.valueOf(charArray);
    }

    //5. Особый палиндром
    public static boolean isPalindromeDescendant(int number) {
        while (number >= 10) {
            if (isPalindrome(number)) {

                return true;
            }
            number = updateNumber(number);
        }

        return false;
    }

    public static int updateNumber(int number) {
        StringBuilder res = new StringBuilder();
        String numberStr = Integer.toString(number);
        int strLength = numberStr.length();

        for (int i = 0; i < strLength - 1; i += 2) {
            res.append((numberStr.charAt(i) - '0') + (numberStr.charAt(i + 1) - '0'));
        }
        if (strLength % 2 != 0) {
            res.append(numberStr.charAt(strLength - 1));
        }

        return Integer.parseInt(res.toString());
    }

    public static boolean isPalindrome(int number) {
        String numberStr = String.valueOf(number);
        int strLength = numberStr.length();

        for (int i = 0; i < (strLength / 2); i++) {
            if ((numberStr.charAt(i) - '0') != (numberStr.charAt(strLength - i - 1) - '0')) {

                return false;
            }
        }

        return true;
    }

    //6. Постоянная Капрекара
    public static int countK(int number) {
        int[] numberAsc = numberToArray(number);
        Arrays.sort(numberAsc);
        int firstNumber = arrayToNumber(numberAsc, true);
        int secondNumber = arrayToNumber(numberAsc, false);

        if (firstNumber - secondNumber == 0) {

            return -1;
        }

        if (number != 6174) {

            return countK(firstNumber - secondNumber) + 1;
        } else {

            return 0;
        }
    }

    public static int[] numberToArray(int number) {
        int numberLength = String.valueOf(number).length();
        int[] numberArray = new int[numberLength];

        for (int i = 0; i < numberLength; i++) {
            numberArray[numberLength - i - 1] = number % 10;
            number = number / 10;
        }

        return numberArray;
    }

    public static int arrayToNumber(int[] array, boolean isReversed) {
        StringBuilder numberSb = new StringBuilder();

        for (int num : array) {
            numberSb.append((char) (num + '0'));
        }

        if (isReversed) {

            return Integer.parseInt(numberSb.reverse().toString());
        } else {

            return Integer.parseInt(numberSb.toString());
        }
    }

    //7. Циклический битовый сдвиг
    public static int rotateRight(int n, int shift) {
        if (shift < 0) {

            return rotateLeft(n, -shift);
        }

        var numberStr = Integer.toBinaryString(n);
        var strLength = numberStr.length();
        shift = shift % strLength;

        return Integer.parseInt(numberStr.substring(strLength - shift)
            + numberStr.substring(0, strLength - shift), 2);
    }

    public static int rotateLeft(int n, int shift) {
        if (shift < 0) {

            return rotateRight(n, -shift);
        }

        var numberStr = Integer.toBinaryString(n);
        shift = shift % numberStr.length();

        return Integer.parseInt(numberStr.substring(shift) + numberStr.substring(0, shift), 2);
    }

    //8. Кони на доске
    public static boolean knightBoardCapture(int[][] board) {
        var rows = board.length;
        var columns = board[0].length;
        int[][] possibleMoves = {
            {1, 2},
            {2, 1},
            {-1, 2},
            {2, -1},
            {1, -2},
            {-2, 1},
            {-1, -2},
            {-2, -1}
        };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == 1) {
                    for (int[] move : possibleMoves) {
                        int row = i + move[0];
                        int column = j + move[1];
                        if (isValid(row, column, rows, columns) && board[row][column] == 1) {

                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean isValid(int row, int column, int rows, int columns) {

        return row >= 0 && column >= 0 && row < rows && column < columns;
    }
}
