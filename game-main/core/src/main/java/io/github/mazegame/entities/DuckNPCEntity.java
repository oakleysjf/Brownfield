package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.effects.SlowEffect;

/** An NPC duck that walks in a vertical cycle and chases the player when nearby, applying SlowEffect on contact. */
public class DuckNPCEntity extends CharacterEntity {

    private static final float CHASE_DISTANCE = 4f * Grid.GRID_SQUARE_SIZE;
    private static final float CATCH_DISTANCE = 0.5f * Grid.GRID_SQUARE_SIZE;
    private static final float MOVE_SPEED = 0.8f;
    private static final int SLOW_EFFECT_DURATION = 3;

    private float walkTimer = 0f;
    private static final float WALK_CYCLE_DURATION = 2f;
    private boolean movingUp = true;

    private boolean isChaseBehaviourActive = false;
    private float chaseTimer = 0f;
    private static final float MAX_CHASE_DURATION = 3f;
    
    private Vector2 originalGridPosition;
    private boolean isReturningHome = false;
    private boolean hasAppliedEffect = false;

    /**
     * Creates a new Duck NPC.
     * 
     * @param gridPosition the position to spawn at
     */
    public DuckNPCEntity(Vector2 gridPosition) {
        super(gridPosition);
        originalGridPosition = new Vector2(gridPosition);
        image = new Texture("images/entities/duck.png");
        isCollider = true;
    }

    @Override
    public void update(float delta) {
        float distanceToPlayer = position.dst(PlayerEntity.instance.position);

        // If returning home, move back to original position
        if (isReturningHome) {
            returnToOriginalPosition(delta);
            if (gridPosition.equals(originalGridPosition)) {
                isReturningHome = false;
                chaseTimer = 0f;
                walkTimer = 0f;
                movingUp = true;
                hasAppliedEffect = false;
            }
        }
        // Check if player is within chase distance and chase time hasn't expired
        else if (distanceToPlayer < CHASE_DISTANCE && chaseTimer < MAX_CHASE_DURATION) {
            isChaseBehaviourActive = true;
            chaseTimer += delta;
            chasePlayer(delta);
        } 
        // Chase time has expired, return home
        else if (chaseTimer >= MAX_CHASE_DURATION) {
            isChaseBehaviourActive = false;
            isReturningHome = true;
        } 
        // Normal walk cycle
        else {
            isChaseBehaviourActive = false;
            verticalWalkCycle(delta);
        }

        // Check if duck has caught the player
        if (distanceToPlayer < CATCH_DISTANCE && !hasAppliedEffect) {
            PlayerEntity.instance.giveEffect(new SlowEffect(SLOW_EFFECT_DURATION));
            hasAppliedEffect = true;
        }
    }

    /**
     * Handles the vertical walk cycle behavior when player is not nearby.
     * 
     * @param delta the time since the last frame
     */
    private void verticalWalkCycle(float delta) {
        walkTimer += delta;

        if (walkTimer >= WALK_CYCLE_DURATION) {
            walkTimer = 0f;
            movingUp = !movingUp;
        }

        // Move vertically based on walk cycle
        if (!isMoving) {
            if (movingUp) {
                nextGridPosition = new Vector2(gridPosition.x, gridPosition.y + 1);
            } else {
                nextGridPosition = new Vector2(gridPosition.x, gridPosition.y - 1);
            }
            isMoving = true;
        } else {
            move(delta, MOVE_SPEED);
        }
    }

    /**
     * Handles the chase behavior when player is nearby.
     * 
     * @param delta the time since the last frame
     */
    private void chasePlayer(float delta) {
        if (!isMoving) {
            Vector2 playerGridPos = PlayerEntity.instance.gridPosition;
            Vector2 duckGridPos = gridPosition;

            // Calculate direction to player
            Vector2 direction = new Vector2(playerGridPos).sub(duckGridPos);

            // Move towards player (prioritize larger movement)
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                if (direction.x > 0) {
                    nextGridPosition = new Vector2(gridPosition.x + 1, gridPosition.y);
                } else {
                    nextGridPosition = new Vector2(gridPosition.x - 1, gridPosition.y);
                }
            } else {
                if (direction.y > 0) {
                    nextGridPosition = new Vector2(gridPosition.x, gridPosition.y + 1);
                } else {
                    nextGridPosition = new Vector2(gridPosition.x, gridPosition.y - 1);
                }
            }
            isMoving = true;
        } else {
            move(delta, MOVE_SPEED);
        }
    }

    /**
     * Returns the duck to its original starting position.
     * 
     * @param delta the time since the last frame
     */
    private void returnToOriginalPosition(float delta) {
        if (!isMoving) {
            Vector2 direction = new Vector2(originalGridPosition).sub(gridPosition);

            // Move towards original position (prioritize larger movement)
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                if (direction.x > 0) {
                    nextGridPosition = new Vector2(gridPosition.x + 1, gridPosition.y);
                } else {
                    nextGridPosition = new Vector2(gridPosition.x - 1, gridPosition.y);
                }
            } else {
                if (direction.y > 0) {
                    nextGridPosition = new Vector2(gridPosition.x, gridPosition.y + 1);
                } else {
                    nextGridPosition = new Vector2(gridPosition.x, gridPosition.y - 1);
                }
            }
            isMoving = true;
        } else {
            move(delta, MOVE_SPEED);
        }
    }
}
