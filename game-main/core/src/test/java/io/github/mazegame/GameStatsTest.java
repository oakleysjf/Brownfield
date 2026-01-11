package io.github.mazegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the GameStats class.
 * Tests all statistics tracking, recording, and reset functionality.
 */
public class GameStatsTest {

    @Before
    public void setUp() {
        GameStats.reset();
    }

    @Test
    public void testInitialStats() {
        GameStats.reset();
        assertEquals(0, GameStats.questGiverInteractions);
        assertEquals(0, GameStats.glueUsageCount);
        assertEquals(0, GameStats.codesCollected);
        assertEquals(0, GameStats.puzzlesAchieved);
        assertEquals(0, GameStats.energyDrinksUsed);
    }

    @Test
    public void testRecordQuestGiverInteraction() {
        assertEquals(0, GameStats.questGiverInteractions);
        GameStats.recordQuestGiverInteraction();
        assertEquals(1, GameStats.questGiverInteractions);
    }

    @Test
    public void testMultipleQuestGiverInteractions() {
        for (int i = 0; i < 5; i++) {
            GameStats.recordQuestGiverInteraction();
        }
        assertEquals(5, GameStats.questGiverInteractions);
    }

    @Test
    public void testRecordGlueUsage() {
        assertEquals(0, GameStats.glueUsageCount);
        GameStats.recordGlueUsage();
        assertEquals(1, GameStats.glueUsageCount);
    }

    @Test
    public void testMultipleGlueUsage() {
        for (int i = 0; i < 3; i++) {
            GameStats.recordGlueUsage();
        }
        assertEquals(3, GameStats.glueUsageCount);
    }

    @Test
    public void testRecordCodeCollected() {
        assertEquals(0, GameStats.codesCollected);
        GameStats.recordCodeCollected();
        assertEquals(1, GameStats.codesCollected);
    }

    @Test
    public void testRecordAllCodesCollected() {
        for (int i = 0; i < 5; i++) {
            GameStats.recordCodeCollected();
        }
        assertEquals(5, GameStats.codesCollected);
    }

    @Test
    public void testRecordPuzzleAchieved() {
        assertEquals(0, GameStats.puzzlesAchieved);
        GameStats.recordPuzzleAchieved();
        assertEquals(1, GameStats.puzzlesAchieved);
    }

    @Test
    public void testMultiplePuzzlesAchieved() {
        for (int i = 0; i < 4; i++) {
            GameStats.recordPuzzleAchieved();
        }
        assertEquals(4, GameStats.puzzlesAchieved);
    }

    @Test
    public void testRecordEnergyDrinkUsed() {
        assertEquals(0, GameStats.energyDrinksUsed);
        GameStats.recordEnergyDrinkUsed();
        assertEquals(1, GameStats.energyDrinksUsed);
    }

    @Test
    public void testMultipleEnergyDrinksUsed() {
        for (int i = 0; i < 2; i++) {
            GameStats.recordEnergyDrinkUsed();
        }
        assertEquals(2, GameStats.energyDrinksUsed);
    }

    @Test
    public void testResetAllStats() {
        GameStats.recordQuestGiverInteraction();
        GameStats.recordGlueUsage();
        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();
        GameStats.recordEnergyDrinkUsed();

        GameStats.reset();

        assertEquals(0, GameStats.questGiverInteractions);
        assertEquals(0, GameStats.glueUsageCount);
        assertEquals(0, GameStats.codesCollected);
        assertEquals(0, GameStats.puzzlesAchieved);
        assertEquals(0, GameStats.energyDrinksUsed);
    }

    @Test
    public void testMixedStatisticsRecording() {
        GameStats.recordQuestGiverInteraction();
        GameStats.recordQuestGiverInteraction();
        GameStats.recordGlueUsage();
        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();
        GameStats.recordEnergyDrinkUsed();

        assertEquals(2, GameStats.questGiverInteractions);
        assertEquals(1, GameStats.glueUsageCount);
        assertEquals(1, GameStats.codesCollected);
        assertEquals(1, GameStats.puzzlesAchieved);
        assertEquals(1, GameStats.energyDrinksUsed);
    }

    @Test
    public void testStatisticsIndependence() {
        GameStats.recordQuestGiverInteraction();
        assertEquals(1, GameStats.questGiverInteractions);
        assertEquals(0, GameStats.glueUsageCount);
        assertEquals(0, GameStats.codesCollected);

        GameStats.recordGlueUsage();
        assertEquals(1, GameStats.questGiverInteractions);
        assertEquals(1, GameStats.glueUsageCount);
        assertEquals(0, GameStats.codesCollected);
    }

    @Test
    public void testCompleteGameplay() {
        // Simulate a complete gameplay session
        for (int i = 0; i < 3; i++) {
            GameStats.recordQuestGiverInteraction();
        }

        for (int i = 0; i < 5; i++) {
            GameStats.recordCodeCollected();
        }

        for (int i = 0; i < 4; i++) {
            GameStats.recordPuzzleAchieved();
        }

        GameStats.recordGlueUsage();
        GameStats.recordEnergyDrinkUsed();

        assertEquals(3, GameStats.questGiverInteractions);
        assertEquals(5, GameStats.codesCollected);
        assertEquals(4, GameStats.puzzlesAchieved);
        assertEquals(1, GameStats.glueUsageCount);
        assertEquals(1, GameStats.energyDrinksUsed);
    }

    @Test
    public void testStatisticsCanBeDirectlyModified() {
        GameStats.codesCollected = 3;
        GameStats.questGiverInteractions = 2;
        GameStats.glueUsageCount = 1;

        assertEquals(3, GameStats.codesCollected);
        assertEquals(2, GameStats.questGiverInteractions);
        assertEquals(1, GameStats.glueUsageCount);
    }

    @Test
    public void testHighStatValues() {
        for (int i = 0; i < 100; i++) {
            GameStats.recordQuestGiverInteraction();
        }

        assertEquals(100, GameStats.questGiverInteractions);
    }

    @Test
    public void testResetMultipleTimes() {
        GameStats.recordCodeCollected();
        GameStats.reset();
        assertEquals(0, GameStats.codesCollected);

        GameStats.recordCodeCollected();
        GameStats.recordCodeCollected();
        GameStats.reset();
        assertEquals(0, GameStats.codesCollected);
    }

    @Test
    public void testQuestGiverInteractionPersistence() {
        GameStats.recordQuestGiverInteraction();
        int initial = GameStats.questGiverInteractions;

        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();

        assertEquals(initial, GameStats.questGiverInteractions);
    }

    @Test
    public void testCodesCollectedPersistence() {
        GameStats.recordCodeCollected();
        int initial = GameStats.codesCollected;

        GameStats.recordQuestGiverInteraction();
        GameStats.recordGlueUsage();

        assertEquals(initial, GameStats.codesCollected);
    }

    @Test
    public void testPuzzlesAchievedPersistence() {
        GameStats.recordPuzzleAchieved();
        int initial = GameStats.puzzlesAchieved;

        GameStats.recordEnergyDrinkUsed();

        assertEquals(initial, GameStats.puzzlesAchieved);
    }

    @Test
    public void testGlueUsagePersistence() {
        GameStats.recordGlueUsage();
        int initial = GameStats.glueUsageCount;

        GameStats.recordQuestGiverInteraction();
        GameStats.recordCodeCollected();

        assertEquals(initial, GameStats.glueUsageCount);
    }

    @Test
    public void testEnergyDrinkUsagePersistence() {
        GameStats.recordEnergyDrinkUsed();
        int initial = GameStats.energyDrinksUsed;

        GameStats.recordPuzzleAchieved();

        assertEquals(initial, GameStats.energyDrinksUsed);
    }

    @Test
    public void testMaxCodeLimitReached() {
        for (int i = 0; i < 5; i++) {
            GameStats.recordCodeCollected();
        }
        assertEquals(5, GameStats.codesCollected);

        // Exceeding the limit
        GameStats.recordCodeCollected();
        assertEquals(6, GameStats.codesCollected);
    }

    @Test
    public void testStatisticsAfterReset() {
        GameStats.codesCollected = 5;
        GameStats.questGiverInteractions = 10;

        GameStats.reset();

        GameStats.recordCodeCollected();
        assertEquals(1, GameStats.codesCollected);
        assertEquals(0, GameStats.questGiverInteractions);
    }
}
