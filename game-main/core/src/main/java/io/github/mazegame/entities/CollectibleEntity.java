package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.mazegame.Code;
import io.github.mazegame.Grid;
import io.github.mazegame.MazeGame;

/** An entity that represents a collectible code digit in the game world. When the player reaches the collectible, it is collected and a notification is displayed. */
public class CollectibleEntity extends Entity {
    private Code code;
    private String label;
    private boolean collected = false;

    /** Creates a new collectible entity at the specified position.
     * 
     * @param gridPosition the grid position to spawn at
     * @param code the Code object to collect
     * @param label the label to display for this code (e.g., "Code1", "Code2")
     */
    public CollectibleEntity(Vector2 gridPosition, Code code, String label) {
        super(gridPosition);
        this.code = code;
        this.label = label;
        this.image = null; // We'll draw text instead
        this.isCollider = false;
        this.size = new Vector2(Grid.GRID_SQUARE_SIZE, Grid.GRID_SQUARE_SIZE);
    }

    @Override
    public void update(float delta) {
        // Check if player is at the same position
        if (PlayerEntity.instance != null && !collected) {
            if (PlayerEntity.instance.getGridPosition().equals(gridPosition)) {
                // Player has collected this code
                collected = true;
                if (!code.getCollected()) {
                    code.collect();
                    io.github.mazegame.CodeManager.incrementCodesCollected();
                    io.github.mazegame.GameStats.recordCodeCollected();
                    int total = io.github.mazegame.CodeManager.getCodesCollected();
                    io.github.mazegame.NotificationManager.instance.addNotification(label + " collected! (" + total + "/5)");
                }
                isExpired = true; // Mark for removal from EntityManager
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Don't draw if already collected
        if (collected) {
            return;
        }
        
        if (MazeGame.font != null) {
            batch.setColor(Color.WHITE);
            String text = label + ": " + code.getDigit();
            MazeGame.font.draw(batch, text, position.x, position.y + Grid.GRID_SQUARE_SIZE / 2);
            batch.setColor(Color.WHITE);
        }
    }

    public boolean isCollected() {
        return collected;
    }
}
