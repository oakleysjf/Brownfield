package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

/** A solid wall tile that blocks movement and cannot be traversed. */
public class WallTile extends Tile {

    public WallTile() {
        image = new Texture("images/tiles/wall.png");
        tileType = TileType.IMPASSABLE;
    }
}
