package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.MazeGame;
import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

/** An NPC that poses a Turing machine trivia question and gives Code4 on correct answer. */
public class TuringMachineNPCEntity extends CharacterEntity {

    private boolean questionAsked = false;
    private boolean answered = false;
    private Code code4Reward;

    /**
     * Creates a new Turing machine NPC.
     * 
     * @param gridPosition the position to spawn at
     */
    public TuringMachineNPCEntity(Vector2 gridPosition) {
        super(gridPosition);
        image = new Texture("images/entities/character.png");
        isCollider = true;
        code4Reward = new Code();
    }

    @Override
    public void update(float delta) { }

    /**
     * Checks if the player's answer is correct and rewards them with Code4.
     * 
     * @param answerIndex the index of the answer selected (0, 1, or 2)
     */
    public void submitAnswer(int answerIndex) {
        if (answered) return;
        
        answered = true;
        
        // Correct answer is index 1 (Alan Turing)
        if (answerIndex == 1) {
            PlayerEntity.instance.collectCode(code4Reward);
            CodeManager.registerCode(3, code4Reward);
            CodeManager.incrementCodesCollected();
        }
    }

    /**
     * {@return whether this NPC has already been answered}
     */
    public boolean hasBeenAnswered() {
        return answered;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        // Display prompt when player is nearby
        if (position.dst2(PlayerEntity.instance.position) < 4 * Grid.GRID_SQUARE_SIZE * Grid.GRID_SQUARE_SIZE) {
            Vector2 drawPosition = getDrawPosition();

            if (!answered) {
                MazeGame.font.draw(batch, "Press E to answer my Turing machine question", drawPosition.x, drawPosition.y - 0.5f);
            } else {
                MazeGame.font.draw(batch, "Thanks for answering!", drawPosition.x, drawPosition.y - 0.5f);
            }
        }
    }
}
