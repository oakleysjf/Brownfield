package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

/** A tile that marks the exit point from the game level. */
public class ExitTile extends Tile {

    /** Creates a new exit tile. */
    public ExitTile() {
        image = new Texture("images/tiles/floor.png");
        tileType = TileType.EXIT;
    }
}