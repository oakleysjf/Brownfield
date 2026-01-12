package io.github.mazegame;

/**
 * Manages the in-game scoreboard display.
 * Score decreases over time as a penalty for taking too long.
 */
public class Scoreboard {
    private int currentScore;
    private float timeSinceLastPenalty = 0f;
    private static final float PENALTY_INTERVAL = 5f; // Penalty every 5 seconds
    private static final int SCORE_PENALTY = 50; // Points lost per penalty interval
    
    /**
     * Creates a new scoreboard with the initial score.
     */
    public Scoreboard() {
        updateScore();
    }
    
    /**
     * Updates the scoreboard based on elapsed time.
     * Applies penalties every 5 seconds.
     *
     * @param delta the time elapsed since last frame
     */
    public void update(float delta) {
        timeSinceLastPenalty += delta;
        
        // Apply penalty every 5 seconds
        while (timeSinceLastPenalty >= PENALTY_INTERVAL) {
            currentScore = Math.max(0, currentScore - SCORE_PENALTY);
            timeSinceLastPenalty -= PENALTY_INTERVAL;
        }
    }
    
    /**
     * Gets the current score.
     *
     * @return the current score
     */
    public int getCurrentScore() {
        return currentScore;
    }
    
    /**
     * Recalculates the score based on current game stats.
     * This should be called periodically to ensure the score reflects
     * the latest game statistics.
     */
    public void updateScore() {
        currentScore = GameStats.calculateScore();
    }
    
    /**
     * Resets the scoreboard for a new game.
     */
    public void reset() {
        timeSinceLastPenalty = 0f;
        updateScore();
    }
    
    /**
     * Gets the time until the next penalty.
     *
     * @return time in seconds until next penalty
     */
    public float getTimeUntilNextPenalty() {
        return PENALTY_INTERVAL - timeSinceLastPenalty;
    }
    
    /**
     * Gets the penalty amount applied per interval.
     *
     * @return the score penalty amount
     */
    public int getPenaltyAmount() {
        return SCORE_PENALTY;
    }
}
