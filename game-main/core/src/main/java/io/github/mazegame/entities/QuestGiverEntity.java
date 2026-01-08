package io.github.mazegame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.Grid;
import io.github.mazegame.MazeGame;
import io.github.mazegame.items.Item;
import io.github.mazegame.Code;

/** An NPC entity that offers a quest to trade items with the player. */
public class QuestGiverEntity extends CharacterEntity {

    public Item wantedItem;
    public String requestText = "Hand me that assignment!";
    public String finishText = "Thanks, have your grade.";
    private Code code3Reward;

    /**
     * Creates a new quest giver.
     * 
     * @param gridPosition the position to spawn at
     * @param wantedItem the {@link Item} the quest giver wants from the player
     * @param heldItem the {@link Item} the quest giver will give to the player
     */
    public QuestGiverEntity(Vector2 gridPosition, Item wantedItem, Item heldItem) {
        super(gridPosition);
        this.wantedItem = wantedItem;
        image = new Texture("images/entities/character.png");
        isCollider = true;
        setHeldItem(heldItem);
        code3Reward = new Code();
    }

    /**
     * {@return whether the player can trade with this quest giver}
     * 
     * A trade is possible if the player is holding the wanted item, and the
     * quest giver is holding something to give them in return.
     */
    private boolean canTrade() {
        Item ourItem = getHeldItem();
        Item theirItem = PlayerEntity.instance.getHeldItem();

        return ourItem != null && theirItem != null && theirItem.equals(wantedItem);
    }

    @Override
    public void update(float delta) { }

    /** Used by the player to check if they have the correct item to trade, if they do replace their current item with
     *  the quest item and give Code3. */
    public void checkTrade() {
        if (canTrade()) {
            PlayerEntity.instance.setHeldItem(getHeldItem());
            setHeldItem(null);
            wantedItem = null;
            // Give Code3 to player
            PlayerEntity.instance.collectCode(code3Reward);
            io.github.mazegame.CodeManager.registerCode(2, code3Reward);
            io.github.mazegame.CodeManager.incrementCodesCollected();
            io.github.mazegame.GameStats.recordQuestGiverInteraction();
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);

        // Displays the quest givers speach text.
        if (position.dst2(PlayerEntity.instance.position) < 4 * Grid.GRID_SQUARE_SIZE * Grid.GRID_SQUARE_SIZE) {
            Vector2 drawPosition = getDrawPosition();

            // If the player has not traded, the quest giver displays the request text with the item they want,
            // and the item the player will receive, else they give a different text.
            if (wantedItem != null) {
                MazeGame.font.draw(batch, requestText, drawPosition.x, drawPosition.y - 0.5f);
            } else {
                MazeGame.font.draw(batch, finishText, drawPosition.x, drawPosition.y - 0.5f);
            }
        }
    }
    
}
