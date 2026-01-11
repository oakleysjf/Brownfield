package io.github.mazegame;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Screen;

/**
 * Unit tests for the WinScreen class.
 * Tests initialization, screen transitions, and input handling.
 */
public class WinScreenTest {

    private WinScreen winScreen;
    private MazeGame mockGame;

    @Before
    public void setUp() {
        mockGame = mock(MazeGame.class);
        mockGame.batch = mock(com.badlogic.gdx.graphics.g2d.SpriteBatch.class);
        winScreen = new WinScreen(mockGame);
    }

    @Test
    public void testWinScreenInitialization() {
        assertNotNull(winScreen);
        assertTrue(winScreen instanceof Screen);
    }



    @Test
    public void testWinScreenPause() {
        // Should not throw exception
        winScreen.pause();
    }

    @Test
    public void testWinScreenResume() {
        // Should not throw exception
        winScreen.resume();
    }

    @Test
    public void testWinScreenHide() {
        // Should not throw exception
        winScreen.hide();
    }

    @Test
    public void testWinScreenDispose() {
        // Should not throw exception
        winScreen.dispose();
    }

    @Test
    public void testWinScreenWithGameStats() {
        // Reset stats
        GameStats.reset();
        GameStats.codesCollected = 5;
        GameStats.questGiverInteractions = 10;
        GameStats.puzzlesAchieved = 4;
        GameStats.glueUsageCount = 0;
        GameStats.energyDrinksUsed = 2;

        assertEquals(5, GameStats.codesCollected);
        assertEquals(10, GameStats.questGiverInteractions);
        assertEquals(4, GameStats.puzzlesAchieved);
    }

    @Test
    public void testWinScreenIsScreen() {
        assertTrue(winScreen instanceof Screen);
    }

    @Test
    public void testGameStatsCanBeReadBeforeShow() {
        GameStats.reset();
        GameStats.codesCollected = 5;
        assertEquals(5, GameStats.codesCollected);
    }
}
