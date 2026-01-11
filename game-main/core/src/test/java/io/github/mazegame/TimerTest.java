package io.github.mazegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the game timer and elapsed time management.
 * Tests time progression, limits, and status checks.
 */
public class TimerTest {

    private static final float GAME_TIME_LIMIT = 300f; // 5 minutes
    private float elapsedTime;
    private float timeRemaining;

    @Before
    public void setUp() {
        elapsedTime = 0f;
        timeRemaining = GAME_TIME_LIMIT;
    }

    @Test
    public void testInitialTime() {
        assertEquals(0f, elapsedTime, 0.01f);
        assertEquals(GAME_TIME_LIMIT, timeRemaining, 0.01f);
    }

    @Test
    public void testTimeProgression() {
        float delta = 0.016f; // ~60 FPS
        elapsedTime += delta;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(0.016f, elapsedTime, 0.01f);
        assertEquals(GAME_TIME_LIMIT - 0.016f, timeRemaining, 0.01f);
    }

    @Test
    public void testMultipleFrameUpdates() {
        float delta = 0.016f;
        for (int i = 0; i < 60; i++) {
            elapsedTime += delta;
        }
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(0.96f, elapsedTime, 0.01f);
        assertTrue(timeRemaining > 0);
    }

    @Test
    public void testTimeDoesNotExceedLimit() {
        elapsedTime = GAME_TIME_LIMIT + 10f;
        timeRemaining = Math.max(0, GAME_TIME_LIMIT - elapsedTime);

        assertEquals(0f, timeRemaining, 0.01f);
    }

    @Test
    public void testTimeRemainingCalculation() {
        elapsedTime = 100f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(100f, elapsedTime, 0.01f);
        assertEquals(200f, timeRemaining, 0.01f);
    }

    @Test
    public void testIsTimeExpired() {
        elapsedTime = GAME_TIME_LIMIT;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertTrue(timeRemaining <= 0);
    }

    @Test
    public void testTimeNotExpiredInitially() {
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;
        assertFalse(timeRemaining <= 0);
    }

    @Test
    public void testTimeExceededLimit() {
        elapsedTime = 350f;
        timeRemaining = Math.max(0, GAME_TIME_LIMIT - elapsedTime);

        assertEquals(0f, timeRemaining, 0.01f);
        assertTrue(elapsedTime > GAME_TIME_LIMIT);
    }

    @Test
    public void testPartialMinute() {
        elapsedTime = 30.5f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(30.5f, elapsedTime, 0.01f);
        assertEquals(269.5f, timeRemaining, 0.01f);
    }

    @Test
    public void testOneSecondPassed() {
        elapsedTime = 1f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(1f, elapsedTime, 0.01f);
        assertEquals(299f, timeRemaining, 0.01f);
    }

    @Test
    public void testHalfGameCompleted() {
        elapsedTime = 150f; // Half of 300
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(150f, timeRemaining, 0.01f);
    }

    @Test
    public void testGameTimeLimitIsCorrect() {
        assertEquals(300f, GAME_TIME_LIMIT, 0.01f);
    }

    @Test
    public void testQuarterGameTime() {
        elapsedTime = 75f; // Quarter of 300
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(75f, elapsedTime, 0.01f);
        assertEquals(225f, timeRemaining, 0.01f);
    }

    @Test
    public void testThreeQuartersGameTime() {
        elapsedTime = 225f; // Three quarters
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(225f, elapsedTime, 0.01f);
        assertEquals(75f, timeRemaining, 0.01f);
    }

    @Test
    public void testNearExpiration() {
        elapsedTime = 299.9f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(299.9f, elapsedTime, 0.01f);
        assertEquals(0.1f, timeRemaining, 0.01f);
        assertFalse(timeRemaining <= 0);
    }

    @Test
    public void testAtExpiration() {
        elapsedTime = 300f;
        timeRemaining = Math.max(0, GAME_TIME_LIMIT - elapsedTime);

        assertEquals(300f, elapsedTime, 0.01f);
        assertEquals(0f, timeRemaining, 0.01f);
        assertTrue(timeRemaining <= 0);
    }

    @Test
    public void testFractionalSeconds() {
        elapsedTime = 0.5f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(0.5f, elapsedTime, 0.01f);
        assertEquals(299.5f, timeRemaining, 0.01f);
    }

    @Test
    public void testOneMinutePassed() {
        elapsedTime = 60f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(60f, elapsedTime, 0.01f);
        assertEquals(240f, timeRemaining, 0.01f);
    }

    @Test
    public void testTwoMinutesPassed() {
        elapsedTime = 120f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(120f, elapsedTime, 0.01f);
        assertEquals(180f, timeRemaining, 0.01f);
    }

    @Test
    public void testFourMinutesPassed() {
        elapsedTime = 240f;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(240f, elapsedTime, 0.01f);
        assertEquals(60f, timeRemaining, 0.01f);
    }

    @Test
    public void testHighFrameRateAccumulation() {
        float delta = 1f / 120f; // 120 FPS
        for (int i = 0; i < 120; i++) {
            elapsedTime += delta;
        }
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(1f, elapsedTime, 0.01f);
        assertEquals(299f, timeRemaining, 0.01f);
    }

    @Test
    public void testLowFrameRateAccumulation() {
        float delta = 0.1f; // 10 FPS
        for (int i = 0; i < 10; i++) {
            elapsedTime += delta;
        }
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(1f, elapsedTime, 0.01f);
        assertEquals(299f, timeRemaining, 0.01f);
    }

    @Test
    public void testVerySmallTimestep() {
        float delta = 0.001f;
        elapsedTime += delta;
        timeRemaining = GAME_TIME_LIMIT - elapsedTime;

        assertEquals(0.001f, elapsedTime, 0.0001f);
        assertEquals(GAME_TIME_LIMIT - 0.001f, timeRemaining, 0.0001f);
    }

    @Test
    public void testTimeProgressionIsMonotonic() {
        float prev = GAME_TIME_LIMIT;
        for (int i = 0; i < 100; i++) {
            elapsedTime += 0.1f;
            timeRemaining = GAME_TIME_LIMIT - elapsedTime;
            assertTrue(timeRemaining <= prev);
            prev = timeRemaining;
        }
    }

    @Test
    public void testZeroTimeRemaining() {
        elapsedTime = 300f;
        timeRemaining = Math.max(0, GAME_TIME_LIMIT - elapsedTime);

        assertEquals(0f, timeRemaining, 0.01f);
    }

    @Test
    public void testNegativeTimeClamp() {
        elapsedTime = 350f;
        timeRemaining = Math.max(0, GAME_TIME_LIMIT - elapsedTime);

        assertEquals(0f, timeRemaining, 0.01f);
        assertFalse(timeRemaining < 0);
    }
}
