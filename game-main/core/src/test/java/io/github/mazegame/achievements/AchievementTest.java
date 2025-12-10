package io.github.mazegame.achievements;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Achievement abstract class.
 * Tests achievement locking/unlocking and properties.
 */
public class AchievementTest {

    private Achievement testAchievement;

    @Before
    public void setUp() {
        testAchievement = new Achievement(
            AchievementID.GIVE_RED_BOX,
            "Test Achievement",
            "This is a test achievement"
        ) {
        };
    }

    @Test
    public void testAchievementInitialization() {
        assertNotNull(testAchievement);
        assertFalse(testAchievement.isUnlocked());
    }

    @Test
    public void testGetAchievementId() {
        assertEquals(AchievementID.GIVE_RED_BOX, testAchievement.getId());
    }

    @Test
    public void testGetAchievementTitle() {
        assertEquals("Test Achievement", testAchievement.getTitle());
    }

    @Test
    public void testGetAchievementDescription() {
        assertEquals("This is a test achievement", testAchievement.getDescription());
    }

    @Test
    public void testUnlockAchievement() {
        assertFalse(testAchievement.isUnlocked());
        testAchievement.unlock();
        assertTrue(testAchievement.isUnlocked());
    }

    @Test
    public void testDoubleUnlockAchievement() {
        testAchievement.unlock();
        assertTrue(testAchievement.isUnlocked());
        
        testAchievement.unlock();
        assertTrue(testAchievement.isUnlocked());
    }

    @Test
    public void testAchievementProperties() {
        testAchievement.unlock();
        
        assertNotNull(testAchievement.getId());
        assertNotNull(testAchievement.getTitle());
        assertNotNull(testAchievement.getDescription());
        assertTrue(testAchievement.isUnlocked());
    }
}
