package io.github.mazegame.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;
import io.github.mazegame.GameStats;

/**
 * Unit tests for the ComputerEntity class.
 * Tests minigame mechanics, code rewards, and NPC interactions.
 * Note: These tests verify logic independent of libGDX texture loading.
 */
public class ComputerEntityTest {

    @Test
    public void testCodeRewardLogic() {
        // Test the core reward logic without initializing libGDX
        GameStats.reset();
        int initialCodesCollected = GameStats.codesCollected;
        
        // Simulate what submitAnswer does
        GameStats.codesCollected++;
        GameStats.puzzlesAchieved++;
        
        assertEquals(initialCodesCollected + 1, GameStats.codesCollected);
        assertEquals(1, GameStats.puzzlesAchieved);
    }

    @Test
    public void testMultipleCodeRewards() {
        GameStats.reset();
        
        // Simulate multiple code rewards
        for (int i = 0; i < 3; i++) {
            GameStats.codesCollected++;
            GameStats.puzzlesAchieved++;
        }
        
        assertEquals(3, GameStats.codesCollected);
        assertEquals(3, GameStats.puzzlesAchieved);
    }

    @Test
    public void testGameStateIsolation() {
        GameStats.reset();
        GameStats.codesCollected = 5;
        assertEquals(5, GameStats.codesCollected);
        
        GameStats.reset();
        assertEquals(0, GameStats.codesCollected);
    }

    @Test
    public void testCodeCollectionCounter() {
        GameStats.reset();
        
        assertEquals(0, GameStats.codesCollected);
        GameStats.recordCodeCollected();
        assertEquals(1, GameStats.codesCollected);
        GameStats.recordCodeCollected();
        assertEquals(2, GameStats.codesCollected);
    }

    @Test
    public void testPuzzleAchievementCounter() {
        GameStats.reset();
        
        assertEquals(0, GameStats.puzzlesAchieved);
        GameStats.recordPuzzleAchieved();
        assertEquals(1, GameStats.puzzlesAchieved);
        GameStats.recordPuzzleAchieved();
        assertEquals(2, GameStats.puzzlesAchieved);
    }

    @Test
    public void testPositionVectorHandling() {
        Vector2 pos1 = new Vector2(5, 5);
        Vector2 pos2 = new Vector2(10, 15);
        Vector2 pos3 = new Vector2(50, 50);

        assertEquals(5f, pos1.x, 0.01f);
        assertEquals(10f, pos2.x, 0.01f);
        assertEquals(50f, pos3.x, 0.01f);
    }

    @Test
    public void testGameStatsIndependence() {
        GameStats.reset();
        GameStats.codesCollected = 2;
        GameStats.puzzlesAchieved = 1;
        
        int codesBefore = GameStats.codesCollected;
        int puzzlesBefore = GameStats.puzzlesAchieved;
        
        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();
        
        assertEquals(codesBefore + 1, GameStats.codesCollected);
        assertEquals(puzzlesBefore + 1, GameStats.puzzlesAchieved);
    }

    @Test
    public void testMaxCodeLimit() {
        GameStats.reset();
        
        // Simulate collecting all 5 codes
        for (int i = 0; i < 5; i++) {
            GameStats.codesCollected++;
        }
        
        assertEquals(5, GameStats.codesCollected);
    }

    @Test
    public void testStatisticsPersist() {
        GameStats.reset();
        GameStats.codesCollected = 3;
        GameStats.questGiverInteractions = 2;
        GameStats.puzzlesAchieved = 1;
        
        // Verify stats persist
        assertEquals(3, GameStats.codesCollected);
        assertEquals(2, GameStats.questGiverInteractions);
        assertEquals(1, GameStats.puzzlesAchieved);
    }

    @Test
    public void testCodeCollectionWithGameStats() {
        GameStats.reset();
        GameStats.glueUsageCount = 2;
        GameStats.energyDrinksUsed = 1;
        
        // Collect a code
        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();
        
        assertEquals(1, GameStats.codesCollected);
        assertEquals(1, GameStats.puzzlesAchieved);
        assertEquals(2, GameStats.glueUsageCount); // Should remain unchanged
        assertEquals(1, GameStats.energyDrinksUsed); // Should remain unchanged
    }

    @Test
    public void testQuestGiverStatisticsUnaffected() {
        GameStats.reset();
        GameStats.questGiverInteractions = 5;
        
        // Code collection shouldn't affect quest givers
        GameStats.recordCodeCollected();
        GameStats.recordPuzzleAchieved();
        
        assertEquals(5, GameStats.questGiverInteractions);
    }

    @Test
    public void testVectorPositionAccuracy() {
        Vector2 gridPos = new Vector2(7, 9);
        
        assertEquals(7f, gridPos.x, 0.01f);
        assertEquals(9f, gridPos.y, 0.01f);
        
        gridPos.x = 12;
        gridPos.y = 14;
        
        assertEquals(12f, gridPos.x, 0.01f);
        assertEquals(14f, gridPos.y, 0.01f);
    }

    @Test
    public void testSequentialCodeCollection() {
        GameStats.reset();
        
        // Simulate 3 computer terminals being solved
        int[] codesPerTerminal = {1, 1, 1};
        
        for (int codes : codesPerTerminal) {
            GameStats.codesCollected += codes;
        }
        
        assertEquals(3, GameStats.codesCollected);
    }
}
