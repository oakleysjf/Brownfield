package io.github.mazegame.effects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Effect abstract class.
 * Tests effect duration and equality.
 */
public class EffectTest {

    private Effect testEffect;

    @Before
    public void setUp() {
        testEffect = new Effect(10) {
            @Override
            public void applyEffect() {
                duration--;
            }
        };
    }

    @Test
    public void testEffectInitialization() {
        assertNotNull(testEffect);
        assertEquals(10, testEffect.duration);
    }

    @Test
    public void testEffectDurationDecrease() {
        testEffect.applyEffect();
        assertEquals(9, testEffect.duration);
    }

    @Test
    public void testEffectExpiration() {
        for (int i = 0; i < 10; i++) {
            testEffect.applyEffect();
        }
        assertTrue(testEffect.duration <= 0);
    }

    @Test
    public void testEffectEquality() {
        // Create two effects from the same setUp
        Effect effect1 = new Effect(5) {
            @Override
            public void applyEffect() {}
        };
        
        // Test that equals works with same object
        assertTrue(effect1.equals(effect1));
    }

    @Test
    public void testEffectInequalityDifferentTypes() {
        // Effects created from testEffect in setUp
        Effect effect1 = testEffect;
        
        // Create a different effect class
        String notAnEffect = "not an effect";
        
        // Test that equals returns false for non-Effect objects
        assertFalse(effect1.equals(notAnEffect));
    }

    @Test
    public void testZeroDurationEffect() {
        Effect zeroEffect = new Effect(0) {
            @Override
            public void applyEffect() {}
        };
        
        assertEquals(0, zeroEffect.duration);
    }

    @Test
    public void testNegativeDurationEffect() {
        Effect negEffect = new Effect(-5) {
            @Override
            public void applyEffect() {}
        };
        
        assertEquals(-5, negEffect.duration);
    }
}
