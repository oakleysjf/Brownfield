package io.github.mazegame.entities;

//#region Imports

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.EntityManager;
import io.github.mazegame.Grid;
import io.github.mazegame.effects.Effect;
import io.github.mazegame.items.*;
import io.github.mazegame.tiles.TileType;

//#endregion

/** The player character the player will control to navigate the world. */
public class PlayerEntity extends CharacterEntity {

    //#region Variables

    // ==================Self=================
    public static PlayerEntity instance;

    // ===============Movement================
    private final float MOVE_SPEED = 1.5f;
    private float moveMult = 1f;

    private boolean failMove = false;

    // =================Effects================
    private ArrayList<Effect> effects;

    //#endregion


    //#region Initialisation

    /** Creates a new player.
     * 
     * @param gridPosition the position on the grid
     */
    public PlayerEntity(Vector2 gridPosition) {
        super(gridPosition);
        effects = new ArrayList<Effect>();
        image = new Texture("images/entities/character.png");
        isCollider = true;

        // Makes sure only one player exits.
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Player already exists");
        }

        setHeldItem(Items.get(ItemID.RED_TEST_BOX));
    }

    //#endregion


    //#region Update

    /** Updates the player.
     *
     * @param delta the time since the last frame
     */
    public void update(float delta) {
        if (!isMoving) { 
            input();
        } else {
            move(delta);
        }
    }

    /** Sets the movement of the player based on if its moving and where it is on the grid.
    *
    * @param delta the time since the last frame
    */
    private void move(float delta) {
        // Put the player at its point on the world grid and check for inputs, unless its moving
        if (failMove){
            Vector2 nextPosition = new Vector2(nextGridPosition).scl(Grid.GRID_SQUARE_SIZE);
            setPosition(position.interpolate(nextPosition, MOVE_SPEED * moveMult * delta, Interpolation.exp10Out));
            
            // Moves the player to the destination if they are reasonable close enough
            if (position.dst2(nextPosition) < 40f * 40f){
                isMoving = false;
                failMove = false;
                setGridPosition(gridPosition);
            }
        }
        else {
            // Gets the target position in the world and the direction the player is moving in
            // and moves the player closer to the target position
            Vector2 nextPosition = new Vector2(nextGridPosition).scl(Grid.GRID_SQUARE_SIZE);
            setPosition(position.interpolate(nextPosition, MOVE_SPEED * moveMult * delta, Interpolation.exp10Out));
            
            // Moves the player to the destination if they are reasonable close enough
            if (position.dst2(nextPosition) < 1f){
                setGridPosition(nextGridPosition);
                isMoving = false;
                applyEffects();
            }   
        }
    }

    /** Loops through all the effects on the player, applies their effects and removes ones that have expired. */
    private void applyEffects() {
        // Sets the movement multiplier to 1, the default.
        moveMult = 1;
        ArrayList<Effect> removeEffects = new ArrayList<Effect>();
        // Applies the effect of each effect
        for (Effect effect : effects) {
            effect.applyEffect();
            if (effect.duration <= 0) {
                removeEffects.add(effect);
            }
        }
        // Removes all the expired buffs.
        for (Effect effect : removeEffects) {
            effects.remove(effect);
        }
    }

    /** Checks the inputs and sets values accordingly. */
    private void input() {
        // Checks if the player pressed any of movement keys and set it to move to the position.
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            nextGridPosition = new Vector2(gridPosition.x - 1, gridPosition.y);
            isMoving = true;
        } 
        else if (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextGridPosition = new Vector2(gridPosition.x + 1, gridPosition.y);
            isMoving = true;
        } 
        else if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            nextGridPosition = new Vector2(gridPosition.x, gridPosition.y + 1);
            isMoving = true;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            nextGridPosition = new Vector2(gridPosition.x, gridPosition.y - 1);
            isMoving = true;
        }

        // If the player has pressed a movement key, check to see if the player can move to that space or if
        // an entity is in that space.
        if (isMoving) {
            // Checks if the next tile is impassable and sets the move to fail if it is. 
            if (Grid.getTile(nextGridPosition).getTileType() == TileType.EXIT) {
                // Add code to win the game.
                
                failMove = false;
            }
            else if (!(Grid.getTile(nextGridPosition).getTileType() == TileType.PASSABLE)) {
                failMove = true;
            } else {
                failMove = false;
            }

            // Checks if there is an entity in the space player want to move to
            Entity collidingEntity = EntityManager.getCollider(nextGridPosition);
            // If the entity is a quest giver, then the player checks if they can trade.
            if (collidingEntity instanceof QuestGiverEntity) {
                ((QuestGiverEntity)collidingEntity).checkTrade();
                failMove = true;
            }
        }

        // If the space key is pressed, use the players current item, if one exists and can be used
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (getHeldItem() != null && getHeldItem().isUsable) {
                Effect newEffect = getHeldItem().use();
                // Makes sure the player doesn't the buff already.
                if (!effects.contains(newEffect)) {
                    newEffect.applyEffect();
                    effects.add(newEffect);
                    setHeldItem(null);
                }
            }
        }
    }

    //#endregion


    //#region Getters and Setters

    /** Sets the movement multiplier to the specified value.
     * 
     * @param value the value to set the movement multiplier to.
     */
    public void setMoveMult(float value) {
        moveMult = value;
    }

    /** Adds a value to the current movement multiplier.
     * 
     * @param value the value to add to the movement multiplier.
     */
    public void addMoveMult(float value) {
        moveMult += value;
    }

    public void multiplyMoveMult(float scalar) {
        moveMult *= scalar;
    }

    public void giveEffect(Effect effect) {
        effect.applyEffect();
        effects.add(effect);
    }

    //#endregion
}
