package edu.project1;

import org.jetbrains.annotations.NotNull;

sealed interface HangmanState {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();

    record Defeat(char[] state, int attempt, int maxAttempts, String message) implements HangmanState {
    }

    record Win(char[] state, int attempt, int maxAttempts, String message) implements HangmanState {
    }

    record SuccessfulGuess(char[] state, int attempt, int maxAttempts, String message) implements HangmanState {
    }

    record FailedGuess(char[] state, int attempt, int maxAttempts, String message) implements HangmanState {
    }
}
