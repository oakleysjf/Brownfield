package io.github.mazegame.tiles;

//#region imports

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

//#endregion

/** Used to make tiles that will be drawn on the grid. */
public abstract class Tile {
    Texture image;
    TileType tileType;

    /**
     * Draw this tile at the given position.
     * 
     * @param batch the {@link SpriteBatch} to draw to
     * @param position the position to draw the tile at
     * @param size the size to draw the tile at
     */
    public void drawTo(SpriteBatch batch, Vector2 position, float size) {
        batch.draw(image, position.x, position.y, size, size);
    }

    /** Dispose of any textures stored in this tile. */
    public void dispose() {
        image.dispose();
    }

    /** Returns the tile type of the tile.
     * 
     * @return the {@link TileType} of this tile.
    */
    public TileType getTileType() {
        return tileType;
    }
}
