package io.github.mazegame.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

/**
 * Unit tests for the Entity abstract class.
 * Tests position management, grid positioning, and movement.
 */
public class EntityTest {

    private Entity testEntity;

    @Before
    public void setUp() {
        // Create a concrete implementation for testing
        testEntity = new Entity(new Vector2(5, 5)) {
            @Override
            public void update(float delta) {
                // No-op for testing
            }
        };
    }

    @Test
    public void testEntityInitialization() {
        assertNotNull(testEntity);
        assertEquals(5, testEntity.getGridPosition().x, 0.01f);
        assertEquals(5, testEntity.getGridPosition().y, 0.01f);
    }

    @Test
    public void testSetGridPosition() {
        testEntity.setGridPosition(new Vector2(10, 10));
        assertEquals(10, testEntity.getGridPosition().x, 0.01f);
        assertEquals(10, testEntity.getGridPosition().y, 0.01f);
    }

    @Test
    public void testSetGridPositionByCoordinates() {
        testEntity.setGridPosition(3, 7);
        assertEquals(3, testEntity.getGridPosition().x, 0.01f);
        assertEquals(7, testEntity.getGridPosition().y, 0.01f);
    }

    @Test
    public void testSetWorldPosition() {
        testEntity.setPosition(100, 200);
        assertEquals(100, testEntity.getPosition().x, 0.01f);
        assertEquals(200, testEntity.getPosition().y, 0.01f);
    }

    @Test
    public void testSetWorldPositionByVector() {
        Vector2 newPos = new Vector2(150, 250);
        testEntity.setPosition(newPos);
        assertEquals(150, testEntity.getPosition().x, 0.01f);
        assertEquals(250, testEntity.getPosition().y, 0.01f);
    }

    @Test
    public void testIsCollider() {
        assertFalse(testEntity.isCollider());
    }

    @Test
    public void testIsExpired() {
        assertFalse(testEntity.isExpired());
    }

    @Test
    public void testGetOrigin() {
        testEntity.setGridPosition(0, 0);
        Vector2 origin = testEntity.getOrigin();
        assertNotNull(origin);
        // Origin should be center of grid square
        assertEquals(25, origin.x, 0.01f);
        assertEquals(25, origin.y, 0.01f);
    }
}
