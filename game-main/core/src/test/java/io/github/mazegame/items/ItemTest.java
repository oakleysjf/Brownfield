package io.github.mazegame.items;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Item abstract class.
 * Tests item usage and equality.
 */
public class ItemTest {

    private Item usableItem;
    private Item nonUsableItem;

    @Before
    public void setUp() {
        // Create concrete implementations for testing
        usableItem = new Item() {
            {
                name = ItemID.RED_TEST_BOX;
                isUsable = true;
                effect = null;
            }
        };

        nonUsableItem = new Item() {
            {
                name = ItemID.TEST_BOX;
                isUsable = false;
                effect = null;
            }
        };
    }

    @Test
    public void testUsableItem() {
        assertNotNull(usableItem);
        assertTrue(usableItem.isUsable);
    }

    @Test
    public void testNonUsableItem() {
        assertNotNull(nonUsableItem);
        assertFalse(nonUsableItem.isUsable);
    }

    @Test
    public void testUseNonUsableItemReturnsNull() {
        assertNull(nonUsableItem.use());
    }

    @Test
    public void testItemEquality() {
        Item item1 = new Item() {
            { name = ItemID.RED_TEST_BOX; }
        };
        Item item2 = new Item() {
            { name = ItemID.RED_TEST_BOX; }
        };
        
        assertEquals(item1, item2);
    }

    @Test
    public void testItemInequality() {
        Item item1 = new Item() {
            { name = ItemID.RED_TEST_BOX; }
        };
        Item item2 = new Item() {
            { name = ItemID.TEST_BOX; }
        };
        
        assertNotEquals(item1, item2);
    }

    @Test
    public void testItemNotEqualsToOtherObject() {
        Item item = new Item() {
            { name = ItemID.RED_TEST_BOX; }
        };
        
        assertFalse(item.equals("not an item"));
    }
}
