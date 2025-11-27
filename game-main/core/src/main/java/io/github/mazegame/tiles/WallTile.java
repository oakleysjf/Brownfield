package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

public class WallTile extends Tile {

    public WallTile() {
        image = new Texture("images/tiles/wall.png");
        tileType = TileType.IMPASSABLE;
    }
}
