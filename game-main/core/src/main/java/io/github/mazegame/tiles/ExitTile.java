package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;

public class ExitTile extends Tile {

    public ExitTile() {
        image = new Texture("images/tiles/floor.png");
        tileType = TileType.EXIT;
    }
}