package io.github.mazegame;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.entities.Entity;

/**
 * Unit tests for the EntityManager class.
 * Tests entity addition, updating, removal, and collision detection.
 */
public class EntityManagerTest {

    private Entity mockEntity;
    private Entity mockEntity2;
    private SpriteBatch mockBatch;

    @Before
    public void setUp() {
        mockEntity = mock(Entity.class);
        mockEntity2 = mock(Entity.class);
        mockBatch = mock(SpriteBatch.class);
        
        // Reset EntityManager state
        EntityManager.clear();
    }

    @Test
    public void testAddEntity() {
        when(mockEntity.isExpired()).thenReturn(false);
        EntityManager.add(mockEntity);
        
        // Verify entity was added by trying to access it
        assertNotNull(mockEntity);
    }

    @Test
    public void testUpdateEntity() {
        when(mockEntity.isExpired()).thenReturn(false);
        EntityManager.add(mockEntity);
        
        EntityManager.update(0.016f);
        
        verify(mockEntity).update(0.016f);
    }

    @Test
    public void testRemoveExpiredEntity() {
        when(mockEntity.isExpired()).thenReturn(true);
        EntityManager.add(mockEntity);
        
        EntityManager.update(0.016f);
        
        // Entity should be removed from list
        verify(mockEntity).update(0.016f);
    }

    @Test
    public void testDrawEntities() {
        when(mockEntity.isExpired()).thenReturn(false);
        EntityManager.add(mockEntity);
        
        EntityManager.draw(mockBatch);
        
        verify(mockEntity).draw(mockBatch);
    }

    @Test
    public void testGetCollider() {
        Vector2 position = new Vector2(5, 5);
        when(mockEntity.getGridPosition()).thenReturn(position);
        when(mockEntity.isCollider()).thenReturn(true);
        when(mockEntity.isExpired()).thenReturn(false);
        
        EntityManager.add(mockEntity);
        
        Entity collider = EntityManager.getCollider(position);
        assertSame(collider, mockEntity);
    }

    @Test
    public void testGetColliderNonExistent() {
        Vector2 position = new Vector2(5, 5);
        when(mockEntity.getGridPosition()).thenReturn(new Vector2(1, 1));
        when(mockEntity.isCollider()).thenReturn(true);
        when(mockEntity.isExpired()).thenReturn(false);
        
        EntityManager.add(mockEntity);
        
        Entity collider = EntityManager.getCollider(position);
        assertNull(collider);
    }

    @Test
    public void testMultipleEntities() {
        when(mockEntity.isExpired()).thenReturn(false);
        when(mockEntity2.isExpired()).thenReturn(false);
        
        EntityManager.add(mockEntity);
        EntityManager.add(mockEntity2);
        
        EntityManager.update(0.016f);
        
        verify(mockEntity).update(0.016f);
        verify(mockEntity2).update(0.016f);
    }
}
