package io.github.mazegame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.github.mazegame.effects.Effect;

/**
 * Unit tests for the CodeManager class.
 * Tests code registration, collection tracking, and state management.
 */
public class CodeManagerTest {

    @Before
    public void setUp() {
        CodeManager.resetCodes();
    }

    @Test
    public void testInitialCodeState() {
        CodeManager.resetCodes();
        assertEquals(0, CodeManager.getCodesCollected());
        assertFalse(CodeManager.allCodesCollected());
    }

    @Test
    public void testRegisterCode() {
        Code code = new Code();
        CodeManager.registerCode(0, code);
        // Registration should not affect collection count
        assertEquals(0, CodeManager.getCodesCollected());
    }

    @Test
    public void testRegisterMultipleCodes() {
        for (int i = 0; i < 5; i++) {
            Code code = new Code();
            CodeManager.registerCode(i, code);
        }
        assertEquals(0, CodeManager.getCodesCollected());
    }

    @Test
    public void testIncrementCodesCollected() {
        assertEquals(0, CodeManager.getCodesCollected());
        CodeManager.incrementCodesCollected();
        assertEquals(1, CodeManager.getCodesCollected());
    }

    @Test
    public void testMultipleIncrementsCodesCollected() {
        for (int i = 0; i < 5; i++) {
            CodeManager.incrementCodesCollected();
        }
        assertEquals(5, CodeManager.getCodesCollected());
    }

    @Test
    public void testAllCodesCollectedWhenFive() {
        for (int i = 0; i < 5; i++) {
            CodeManager.incrementCodesCollected();
        }
        assertTrue(CodeManager.allCodesCollected());
    }

    @Test
    public void testAllCodesNotCollectedWhenLessThanFive() {
        for (int i = 0; i < 4; i++) {
            CodeManager.incrementCodesCollected();
        }
        assertFalse(CodeManager.allCodesCollected());
    }

    @Test
    public void testResetCodes() {
        for (int i = 0; i < 5; i++) {
            CodeManager.incrementCodesCollected();
        }
        assertEquals(5, CodeManager.getCodesCollected());
        
        CodeManager.resetCodes();
        assertEquals(0, CodeManager.getCodesCollected());
    }

    @Test
    public void testCollectCodeEffect() {
        Code code = new Code();
        assertFalse(code.getCollected());
        
        Effect collectEffect = CodeManager.collectCode(code);
        assertNotNull(collectEffect);
    }

    @Test
    public void testCollectCodeEffectApplication() {
        Code code = new Code();
        assertFalse(code.getCollected());
        
        Effect collectEffect = CodeManager.collectCode(code);
        collectEffect.applyEffect();
        
        assertTrue(code.getCollected());
    }

    @Test
    public void testCollectSameCodeTwice() {
        Code code = new Code();
        
        Effect effect1 = CodeManager.collectCode(code);
        effect1.applyEffect();
        
        int codesAfterFirst = CodeManager.getCodesCollected();
        
        Effect effect2 = CodeManager.collectCode(code);
        effect2.applyEffect();
        
        // Second collection should not increment (already collected)
        assertEquals(codesAfterFirst, CodeManager.getCodesCollected());
    }

    @Test
    public void testRegisterCodeOutOfBounds() {
        Code code = new Code();
        // Should not throw exception
        CodeManager.registerCode(-1, code);
        CodeManager.registerCode(5, code);
        CodeManager.registerCode(100, code);
    }

    @Test
    public void testCollectMultipleDistinctCodes() {
        Code[] codes = new Code[5];
        for (int i = 0; i < 5; i++) {
            codes[i] = new Code();
        }
        
        for (int i = 0; i < 5; i++) {
            Effect effect = CodeManager.collectCode(codes[i]);
            effect.applyEffect();
        }
        
        assertEquals(5, CodeManager.getCodesCollected());
        assertTrue(CodeManager.allCodesCollected());
    }

    @Test
    public void testCodeManagerStateIndependence() {
        CodeManager.incrementCodesCollected();
        CodeManager.incrementCodesCollected();
        assertEquals(2, CodeManager.getCodesCollected());
        
        CodeManager.resetCodes();
        assertEquals(0, CodeManager.getCodesCollected());
        
        CodeManager.incrementCodesCollected();
        assertEquals(1, CodeManager.getCodesCollected());
    }

    @Test
    public void testPartialCodeCollection() {
        Code code1 = new Code();
        Code code2 = new Code();
        Code code3 = new Code();
        
        CodeManager.collectCode(code1).applyEffect();
        CodeManager.collectCode(code2).applyEffect();
        CodeManager.collectCode(code3).applyEffect();
        
        assertEquals(3, CodeManager.getCodesCollected());
        assertFalse(CodeManager.allCodesCollected());
    }
}
