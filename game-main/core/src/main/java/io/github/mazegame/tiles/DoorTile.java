package io.github.mazegame.tiles;

import com.badlogic.gdx.graphics.Texture;
import io.github.mazegame.CodeManager;

/** A tile that represents a locked door which unlocks when all code collectibles are collected. */
public class DoorTile extends Tile {
    private boolean isLocked = true;

    public DoorTile() {
        image = new Texture("images/tiles/door.png");
        tileType = TileType.DOOR;
    }

    /** @return whether the door is locked (true if not all codes are collected) */
    public boolean isLocked() {
        // Door is locked until all codes are collected
        isLocked = !CodeManager.allCodesCollected();
        return isLocked;
    }

    /** Sets the locked state of this door.
     * 
     * @param locked whether the door should be locked
     */
    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }
}
