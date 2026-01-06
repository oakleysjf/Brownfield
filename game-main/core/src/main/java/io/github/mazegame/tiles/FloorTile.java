package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

/** A walkable floor tile that the player and other entities can traverse. */
public class FloorTile extends Tile {
    public FloorTile() {
        image = new Texture("images/tiles/floor.png");
        tileType = TileType.PASSABLE;
    }
}
