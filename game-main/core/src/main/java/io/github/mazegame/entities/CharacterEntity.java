package io.github.mazegame.entities;

//#region Imports

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.items.Item;

//#endregion

/** An abstract class for entities with an item. */
public abstract class CharacterEntity extends Entity {

    //#region Variables

    private Item heldItem;

    //#endregion


    //#region Initialisation

    /**
     * Creates a new character entity.
     * 
     * @param gridPosition the position to spawn at
     */
    public CharacterEntity(Vector2 gridPosition) {
        super(gridPosition);

    }

    //#endregion


    //#region Getters and Setters

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item item) {
        heldItem = item;
    }

    //#endregion


    //#region Drawing

    /** {@return the position where this entity should be rendered} */
    protected Vector2 getDrawPosition() {
        float offset = (Grid.GRID_SQUARE_SIZE - size.x) / 2f;
        return new Vector2(position).add(offset, offset);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Vector2 drawPosition = getDrawPosition();
        batch.draw(image, drawPosition.x, drawPosition.y, size.x, size.y);
        if (heldItem != null) batch.draw(heldItem.image, drawPosition.x + 0.5f, drawPosition.y - 0.5f);
    }

    //#endregion
}
