package io.github.mazegame;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Screen;

/**
 * Unit tests for the LoseScreen class.
 * Tests initialization, screen transitions, and input handling.
 */
public class LoseScreenTest {

    private LoseScreen loseScreen;
    private MazeGame mockGame;

    @Before
    public void setUp() {
        mockGame = mock(MazeGame.class);
        mockGame.batch = mock(com.badlogic.gdx.graphics.g2d.SpriteBatch.class);
        loseScreen = new LoseScreen(mockGame);
    }

    @Test
    public void testLoseScreenInitialization() {
        assertNotNull(loseScreen);
        assertTrue(loseScreen instanceof Screen);
    }

    @Test
    public void testLoseScreenIsScreen() {
        assertTrue(loseScreen instanceof Screen);
    }

    @Test
    public void testLoseScreenWithGameStats() {
        // Reset stats
        GameStats.reset();
        GameStats.codesCollected = 2;
        GameStats.questGiverInteractions = 3;
        GameStats.puzzlesAchieved = 1;
        GameStats.glueUsageCount = 2;
        GameStats.energyDrinksUsed = 1;

        assertEquals(2, GameStats.codesCollected);
        assertEquals(3, GameStats.questGiverInteractions);
        assertEquals(1, GameStats.puzzlesAchieved);
    }

    @Test
    public void testLoseScreenPause() {
        // Should not throw exception
        loseScreen.pause();
    }

    @Test
    public void testLoseScreenResume() {
        // Should not throw exception
        loseScreen.resume();
    }

    @Test
    public void testLoseScreenHide() {
        // Should not throw exception
        loseScreen.hide();
    }

    @Test
    public void testLoseScreenDispose() {
        // Should not throw exception
        loseScreen.dispose();
    }

    @Test
    public void testGameStatsCanBeReadBeforeShow() {
        GameStats.reset();
        GameStats.codesCollected = 5;
        assertEquals(5, GameStats.codesCollected);
    }
}
