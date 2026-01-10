package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.MazeGame;
import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

/** An NPC computer terminal that hosts a number guessing minigame. Gives Code3 on victory. */
public class ComputerEntity extends CharacterEntity {

    private boolean gameWon = false;
    private Code code3Reward;

    /**
     * Creates a new Computer terminal NPC.
     * 
     * @param gridPosition the position to spawn at
     * @param code3 the Code object to reward on victory
     */
    public ComputerEntity(Vector2 gridPosition, Code code3) {
        super(gridPosition);
        image = new Texture("images/entities/Computer.png");
        isCollider = true;
        code3Reward = code3;
    }

    @Override
    public void update(float delta) { }

    /**
     * Marks the minigame as won and rewards the player with Code3.
     * 
     * @param answerIndex unused, kept for consistency with similar NPC interfaces
     */
    public void submitAnswer(int answerIndex) {
        if (gameWon) return;
        
        gameWon = true;
        code3Reward.collect();
        CodeManager.incrementCodesCollected();
        io.github.mazegame.GameStats.recordCodeCollected();
        io.github.mazegame.GameStats.recordPuzzleAchieved();
    }

    /**
     * {@return whether this NPC's minigame has already been won}
     */
    public boolean hasBeenAnswered() {
        return gameWon;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        // Display prompt when player is nearby
        if (position.dst2(PlayerEntity.instance.position) < 4 * Grid.GRID_SQUARE_SIZE * Grid.GRID_SQUARE_SIZE) {
            Vector2 drawPosition = getDrawPosition();

            if (!gameWon) {
                MazeGame.font.draw(batch, "Press E to play minigame", drawPosition.x, drawPosition.y - 0.5f);
            } else {
                MazeGame.font.draw(batch, "Game completed!", drawPosition.x, drawPosition.y - 0.5f);
            }
        }
    }
}
