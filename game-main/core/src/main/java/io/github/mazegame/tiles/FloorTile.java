package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

public class FloorTile extends Tile {
    public FloorTile() {
        image = new Texture("images/tiles/floor.png");
        tileType = TileType.PASSABLE;
    }
}
