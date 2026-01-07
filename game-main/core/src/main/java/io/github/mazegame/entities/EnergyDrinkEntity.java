package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.effects.SpeedEffect;

/** An entity that applies a speed effect to the player when collected, opposite of GlueEntity. */
public class EnergyDrinkEntity extends Entity {

    /** Creates a new energy drink entity.
     * 
     * @param gridPosition the position to spawn at.
     */
    public EnergyDrinkEntity(Vector2 gridPosition) {
        setGridPosition(gridPosition);
        size = new Vector2(1,1).scl(Grid.GRID_SQUARE_SIZE);
        image = new Texture("images/entities/EnergyDrink.png");
        isCollider = false;
    }

    /** Updates the energy drink entity and applies speed effect to player if they occupy the same position. */
    public void update(float delta) {
        // Checks if the player is on the same grid space as this entity
        // If they are apply the speed effect to the player and set this entity to be deleted.
        if (PlayerEntity.instance.getGridPosition().equals(gridPosition)) {
            PlayerEntity.instance.giveEffect(new SpeedEffect(7));
            isExpired = true;
        }
    }
}
