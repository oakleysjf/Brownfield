package io.github.mazegame;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages the game leaderboard, storing and retrieving player scores.
 * Leaderboard data is persisted to a file for persistence across game sessions.
 */
public class Leaderboard {
    private static final String LEADERBOARD_FILE = "leaderboard.dat";
    private static final int MAX_ENTRIES = 5;
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
        loadLeaderboard();
    }

    /**
     * Adds a new entry to the leaderboard if it qualifies.
     *
     * @param playerName the name of the player
     * @param score the player's score
     * @param time the time taken to complete the game
     * @param won whether the player won (true) or lost (false)
     * @return true if the entry was added to the leaderboard, false otherwise
     */
    public boolean addEntry(String playerName, int score, long time, boolean won) {
        LeaderboardEntry entry = new LeaderboardEntry(playerName, score, time, won);
        
        // Only add if it's in top 10 or leaderboard isn't full
        if (entries.size() < MAX_ENTRIES || score > entries.get(entries.size() - 1).getScore()) {
            entries.add(entry);
            entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore())); // Sort descending
            
            // Keep only top 10
            if (entries.size() > MAX_ENTRIES) {
                entries = entries.subList(0, MAX_ENTRIES);
            }
            
            saveLeaderboard();
            return true;
        }
        
        return false;
    }

    /**
     * Gets the current leaderboard entries.
     *
     * @return a list of leaderboard entries sorted by score
     */
    public List<LeaderboardEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * Gets a specific number of top entries.
     *
     * @param count the number of entries to retrieve
     * @return a list of the top entries
     */
    public List<LeaderboardEntry> getTopEntries(int count) {
        return entries.subList(0, Math.min(count, entries.size()));
    }

    /**
     * Clears all leaderboard entries and resets the file.
     */
    public void reset() {
        entries.clear();
        File file = new File(LEADERBOARD_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Loads the leaderboard from the persistent file.
     */
    private void loadLeaderboard() {
        File file = new File(LEADERBOARD_FILE);
        
        if (!file.exists()) {
            entries = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                entries = (List<LeaderboardEntry>) obj;
                entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
            entries = new ArrayList<>();
        }
    }

    /**
     * Saves the leaderboard to a persistent file.
     */
    private void saveLeaderboard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LEADERBOARD_FILE))) {
            oos.writeObject(entries);
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    /**
     * Gets the player's rank on the leaderboard, or -1 if not on it.
     *
     * @param score the player's score
     * @return the rank (1-indexed) or -1 if not on leaderboard
     */
    public int getRank(int score) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getScore() == score) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Checks if a score qualifies for the leaderboard.
     *
     * @param score the score to check
     * @return true if the score would be on the leaderboard
     */
    public boolean qualifiesForLeaderboard(int score) {
        return entries.size() < MAX_ENTRIES || score > entries.get(entries.size() - 1).getScore();
    }
}
