package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.effects.SlowEffect;

public class GlueEntity extends Entity{

    /** Creates a new glue entity.
     * 
     * @param gridPosition the position to spawn at.
     */
    public GlueEntity(Vector2 gridPosition) {
        setGridPosition(gridPosition);
        size = new Vector2(1,1).scl(Grid.GRID_SQUARE_SIZE);
        image = new Texture("images/entities/Glue.png");
        isCollider = false;
    }

    public void update (float delta) {
        // Checks if the player is on the same grid space as this entity
        // If they are aplly the slow effect to the player and seet this entity to be deleted.
        if (PlayerEntity.instance.getGridPosition().equals(gridPosition)) {
            PlayerEntity.instance.giveEffect(new SlowEffect(5));
            System.out.println(isExpired);
            isExpired = true;
        }
    }


}
