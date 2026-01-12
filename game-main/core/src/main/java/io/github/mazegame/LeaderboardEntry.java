package io.github.mazegame;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single entry on the leaderboard.
 * Contains player name, score, time, win status, and timestamp.
 */
public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry> {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String playerName;
    private int score;
    private long timeMillis; // Time taken in milliseconds
    private boolean won;
    private LocalDateTime timestamp;

    /**
     * Creates a new leaderboard entry.
     *
     * @param playerName the name of the player
     * @param score the player's score
     * @param timeMillis the time taken in milliseconds
     * @param won whether the player won
     */
    public LeaderboardEntry(String playerName, int score, long timeMillis, boolean won) {
        this.playerName = playerName;
        this.score = score;
        this.timeMillis = timeMillis;
        this.won = won;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public boolean isWon() {
        return won;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Formats the time in a human-readable way (MM:SS).
     *
     * @return formatted time string
     */
    public String getFormattedTime() {
        long seconds = timeMillis / 1000;
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    /**
     * Gets a human-readable timestamp.
     *
     * @return formatted timestamp string
     */
    public String getFormattedTimestamp() {
        return timestamp.format(DATE_FORMATTER);
    }

    @Override
    public int compareTo(LeaderboardEntry other) {
        // Sort by score descending
        if (this.score != other.score) {
            return Integer.compare(other.score, this.score);
        }
        // If scores are equal, sort by time ascending (faster is better)
        return Long.compare(this.timeMillis, other.timeMillis);
    }

    @Override
    public String toString() {
        return playerName + " - Score: " + score + " - Time: " + getFormattedTime() + " - " + 
               (won ? "WON" : "LOST") + " - " + getFormattedTimestamp();
    }
}
