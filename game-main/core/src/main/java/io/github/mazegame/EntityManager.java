package io.github.mazegame;

//#region imports

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.entities.Entity;

//#endregion

/** Stores, updates and handles collision between all entities. */
public final class EntityManager {
    
    static ArrayList<Entity> entities = new ArrayList<Entity>();

    static ArrayList<Entity> addedEntities = new ArrayList<Entity>();

    static boolean isUpdating = false;

    /** Adds an entity to the entity manager.
     * 
     * @param entity the entity to add
     */
    public static void add(Entity entity){
        // Delays adding an entity if the manager is updating
        if (!isUpdating){
            entities.add(entity);
        } else {
            addedEntities.add(entity);
        }
    }

    /** Updates all entities.
     * 
     * @param delta the time since the last frame
     */
    public static void update(float delta) {
        isUpdating = true;
        ArrayList<Entity> removedEntities = new ArrayList<Entity>();
        for (Entity entity : entities) {
            entity.update(delta);

            if (entity.isExpired()) {
                removedEntities.add(entity);
            }
        }
        isUpdating = false;

        // Adds all entities that where attempted to be added while updating.
        for (Entity entity : addedEntities) {
            entities.add(entity);
        }

        // Removes all expired entities
        for (Entity entity : removedEntities) {
            entities.remove(entity);
        }
    }

    /** Draws all the entities.
     * 
     * @param batch the {@code SpriteBatch} used for drawing 
     */
    public static void draw(SpriteBatch batch){
        for (Entity entity : entities){
            entity.draw(batch);
        }
    }

    /** Checks if an entity is in the specified position.
     * 
     * @param gridPosition the position on the grid to check for entities
     * @return the entity in that position, or null if there is none
     */
    public static Entity getCollider(Vector2 gridPosition){
        for (Entity entity : entities) {
            if (entity.getGridPosition().equals(gridPosition) && entity.isCollider()) {
                return entity;
            }
        }
        return null;
    }
}
