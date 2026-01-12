package io.github.mazegame;

import io.github.mazegame.achievements.AchievementID;
import io.github.mazegame.achievements.AchievementManager;

/** Tracks game statistics including quest giver interactions, glue usage, codes collected, puzzles solved, and energy drinks used. */
public class GameStats {
    public static int questGiverInteractions = 0;
    public static int glueUsageCount = 0;
    public static int codesCollected = 0;
    public static int puzzlesAchieved = 0;
    public static int energyDrinksUsed = 0;
    public static long gameStartTime = 0;
    
    /** Records that a quest giver interaction has occurred. */
    public static void recordQuestGiverInteraction() {
        questGiverInteractions++;
    }
    
    /** Records that the player has used glue. */
    public static void recordGlueUsage() {
        glueUsageCount++;
    }
    
    /** Records that a code collectible has been collected. */
    public static void recordCodeCollected() {
        codesCollected++;
    }
    
    /** Records that a puzzle has been achieved (minigame won or quiz completed). */
    public static void recordPuzzleAchieved() {
        puzzlesAchieved++;
    }
    
    /** Records that an energy drink has been used. */
    public static void recordEnergyDrinkUsed() {
        energyDrinksUsed++;
    }
    
    /**
     * Calculates the final score based on game statistics.
     * Score is calculated as:
     * - 500 points per code collected (max 2500)
     * - 200 points per quest giver interaction (max depends on content)
     * - 150 points per puzzle achieved
     * - Penalty of 50 points per glue used
     * - Penalty of 30 points per energy drink used
     *
     * @return the calculated score
     */
    public static int calculateScore() {
        int score = 0;
        
        // Base points for codes (500 per code)
        score += codesCollected * 500;
        
        // Points for quest interactions (200 per interaction)
        score += questGiverInteractions * 200;
        
        // Points for puzzles achieved (150 per puzzle)
        score += puzzlesAchieved * 150;
        
        // Penalties
        score -= glueUsageCount * 50;
        score -= energyDrinksUsed * 30;
        
        // Ensure score doesn't go negative
        return Math.max(0, score);
    }
    
    /**
     * Gets the elapsed game time in milliseconds.
     *
     * @return the elapsed time
     */
    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }
    
    /**
     * Checks and unlocks achievements based on current game statistics.
     * This should be called when the game ends (win or lose).
     */
    public static void checkAndUnlockAchievements() {
        // Hand In Assignment: Collect all 5 codes
        if (codesCollected >= 5) {
            unlockAchievement(AchievementID.HAND_IN_ASSIGNMENT);
        }
        
        // Interact With Everything: Need to determine the max number of quest givers
        // For now, using a reasonable threshold (e.g., 5+ interactions)
        if (questGiverInteractions >= 5) {
            unlockAchievement(AchievementID.INTERACT_WITH_EVERYTHING);
        }
        
        // Solve Both Puzzles: Achieve both puzzles (ComputerMinigame and TuringMachineQuiz)
        if (puzzlesAchieved >= 2) {
            unlockAchievement(AchievementID.SOLVE_BOTH_PUZZLES);
        }
    }
    
    /**
     * Unlocks an achievement if it hasn't been unlocked already.
     *
     * @param achievementID the ID of the achievement to unlock
     */
    private static void unlockAchievement(AchievementID achievementID) {
        io.github.mazegame.achievements.Achievement achievement = AchievementManager.get(achievementID);
        if (achievement != null && !achievement.isUnlocked()) {
            achievement.unlock();
        }
    }
    
    /**
     * Resets all game statistics to zero for a new game. */
    public static void reset() {
        questGiverInteractions = 0;
        glueUsageCount = 0;
        codesCollected = 0;
        puzzlesAchieved = 0;
        energyDrinksUsed = 0;
        gameStartTime = System.currentTimeMillis();
    }
}
