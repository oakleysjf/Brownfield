package io.github.mazegame.entities;

//#region imports

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;

//#endregion

/** A class for handling objects that should be drawn and can move.*/
public abstract class Entity {

    //#region varibles 

    // ================Drawing================
    protected Texture image;
    protected Vector2 size;
    
    // ===============Positions===============
    protected Vector2 position;
    protected Vector2 gridPosition;
    
    // ================Movement===============
    protected Vector2 nextGridPosition;
    protected boolean isMoving = false;

    // ================Collision==============
    protected boolean isCollider;

    // ===============Expiration==============
    protected boolean isExpired;

    //#endregion

    //#region Initialisation

    public Entity() {}

    /**
     * Creates a new entity.
     * 
     * @param gridPosition the position to spawn at
     */
    public Entity(Vector2 gridPosition) {
        setGridPosition(gridPosition);
        nextGridPosition = gridPosition;
        size = new Vector2(1,1).scl(Grid.GRID_SQUARE_SIZE * 0.75f);
    }

    //#endregion

    
    //#region update

    /** Updates the entity, called every frame.
     * 
     * @param delta the time since the last frame
     */
    public abstract void update(float delta);

    /** Uses interpolation to smoothly move an entity to the next cell.
     * 
     * @param delta time since the last frame
     * @param speed how quickly to move the entity
     */
    protected void move (float delta, float speed) {
            // Gets the target position in the world and the direction the player is moving in
            // and moves the player closer to the target position
            Vector2 nextPosition = new Vector2(nextGridPosition).scl(Grid.GRID_SQUARE_SIZE);
            setPosition(position.interpolate(nextPosition, speed * delta, Interpolation.exp10Out));
            
            // Moves the player to the destination if they are reasonable close enough
            if (position.dst2(nextPosition) < 1f){
                setGridPosition(nextGridPosition);
                isMoving = false;
            } 
    }

    //#endregion

    //#region Getters & Setters

    /** Returns the players current position.
     * 
     * @return the players position
     */
    public Vector2 getPosition() {
        return position;
    }

    /** Sets the players world position.
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setPosition(float x, float y) {
        position = new Vector2(x, y);
    }

    /** Sets the players world positon
     * 
     * @param vector the xy-coordinate
     */
    public void setPosition(Vector2 vector) {
        position = vector;
    }

    /** Gets the center of the enity */
    public Vector2 getOrigin() {
        return new Vector2(position.x, position.y).add(Grid.GRID_SQUARE_SIZE/2, Grid.GRID_SQUARE_SIZE/2);
    }

    /** Gets the players current grid positon.
     * 
     * @return the players grid postion
     */
    public Vector2 getGridPosition() {
        return gridPosition;
    }

    /** Sets the players position on the grid and moves the players world position to that point
     * 
     * @param vector the xy-coordinate on the grid
     */
    public void setGridPosition(Vector2 vector) {
        gridPosition = vector;
        setPosition(new Vector2(vector).scl(Grid.GRID_SQUARE_SIZE));
    }

    /** Sets the players position on the grid and moves the players world position to that point
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
      */
    public void setGridPosition(int x, int y) {
        setGridPosition(new Vector2(x, y));
    }

    public boolean isCollider() {
        return isCollider;
    }

    public boolean isExpired() {
        return isExpired;
    }

    //#endregion


    //#region drawing 

    /** Draws the entity in the center of its grid cell.
     * 
     * @param batch the {@code SpriteBatch} used for drawing
     */
    public void draw(SpriteBatch batch){
        Vector2 drawPosition = new Vector2(position.x, position.y).mulAdd(new Vector2(1,1), (Grid.GRID_SQUARE_SIZE - size.x) / 2f);
        batch.draw(image, drawPosition.x, drawPosition.y, size.x, size.y);
    }

    //#endregion
}
