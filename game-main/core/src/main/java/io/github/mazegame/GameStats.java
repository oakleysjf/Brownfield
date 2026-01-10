package io.github.mazegame;

/** Tracks game statistics including quest giver interactions, glue usage, codes collected, puzzles solved, and energy drinks used. */
public class GameStats {
    public static int questGiverInteractions = 0;
    public static int glueUsageCount = 0;
    public static int codesCollected = 0;
    public static int puzzlesAchieved = 0;
    public static int energyDrinksUsed = 0;
    
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
    
    /** Resets all game statistics to zero for a new game. */
    public static void reset() {
        questGiverInteractions = 0;
        glueUsageCount = 0;
        codesCollected = 0;
        puzzlesAchieved = 0;
        energyDrinksUsed = 0;
    }
}
