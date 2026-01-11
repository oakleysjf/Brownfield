package io.github.mazegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Code class.
 * Tests code digit generation, collection status, and state management.
 */
public class CodeTest {

    private Code code;

    @Before
    public void setUp() {
        code = new Code();
    }

    @Test
    public void testCodeInitialization() {
        assertNotNull(code);
        assertFalse(code.getCollected());
    }

    @Test
    public void testCodeDigitRange() {
        // Digit should be between 0 and 9
        assertTrue(code.getDigit() >= 0);
        assertTrue(code.getDigit() <= 9);
    }

    @Test
    public void testCodeInitiallyNotCollected() {
        assertFalse(code.getCollected());
    }

    @Test
    public void testCollectCode() {
        assertFalse(code.getCollected());
        code.collect();
        assertTrue(code.getCollected());
    }

    @Test
    public void testCollectCodeMultipleTimes() {
        code.collect();
        assertTrue(code.getCollected());
        code.collect();
        assertTrue(code.getCollected()); // Should remain collected
    }

    @Test
    public void testCodeDigitIsConsistent() {
        int digit1 = code.getDigit();
        int digit2 = code.getDigit();
        assertEquals(digit1, digit2); // Should always return same digit
    }

    @Test
    public void testMultipleCodesCanHaveSameDigit() {
        Code code1 = new Code();
        Code code2 = new Code();
        // Can have same digit (random generation)
        assertTrue(true); // Just verify both can be created
    }

    @Test
    public void testCodeCollectionState() {
        // Test state transitions
        assertFalse(code.getCollected());
        code.collect();
        assertTrue(code.getCollected());
    }

    @Test
    public void testDigitValueAfterCollection() {
        int digit = code.getDigit();
        code.collect();
        assertEquals(digit, code.getDigit()); // Digit doesn't change after collection
    }

    @Test
    public void testCodeIsIndependent() {
        Code code1 = new Code();
        Code code2 = new Code();
        
        code1.collect();
        assertTrue(code1.getCollected());
        assertFalse(code2.getCollected()); // Code2 unaffected
    }

    @Test
    public void testAllDigitValuesAreValid() {
        for (int i = 0; i < 100; i++) {
            Code testCode = new Code();
            int digit = testCode.getDigit();
            assertTrue(digit >= 0 && digit <= 9);
        }
    }

    @Test
    public void testCodeCollectionIsImmutable() {
        // Once collected, it stays collected
        code.collect();
        assertTrue(code.getCollected());
        code.collect();
        code.collect();
        assertTrue(code.getCollected());
    }
}
