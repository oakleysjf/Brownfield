package io.github.mazegame.tiles;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the TileType enum.
 * Tests tile type values and comparison.
 */
public class TileTypeTest {

    @Test
    public void testTileTypeValues() {
        assertNotNull(TileType.PASSABLE);
        assertNotNull(TileType.IMPASSABLE);
        assertNotNull(TileType.EXIT);
    }

    @Test
    public void testTileTypeComparison() {
        TileType type1 = TileType.PASSABLE;
        TileType type2 = TileType.PASSABLE;
        TileType type3 = TileType.IMPASSABLE;

        assertEquals(type1, type2);
        assertNotEquals(type1, type3);
    }

    @Test
    public void testTileTypeEquality() {
        assertTrue(TileType.PASSABLE == TileType.PASSABLE);
        assertFalse(TileType.PASSABLE == TileType.IMPASSABLE);
    }

    @Test
    public void testTileTypeOrdinal() {
        assertTrue(TileType.PASSABLE.ordinal() >= 0);
        assertTrue(TileType.IMPASSABLE.ordinal() >= 0);
        assertTrue(TileType.EXIT.ordinal() >= 0);
    }

    @Test
    public void testTileTypeNames() {
        assertNotNull(TileType.PASSABLE.name());
        assertNotNull(TileType.IMPASSABLE.name());
        assertNotNull(TileType.EXIT.name());
    }
}
