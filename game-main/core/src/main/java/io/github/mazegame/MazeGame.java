package io.github.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** The game script that initializes the game screens. */
public class MazeGame extends Game{

    public SpriteBatch batch;
    static public BitmapFont font;
    public ExtendViewport viewport;

    /** Creates the game */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new ExtendViewport(800, 500);

        font.setUseIntegerPositions(true);
        font.getData().setScale(1);

        this.setScreen(new GameScreen(this));
    }

    public void render() {
        super.render(); // Important!!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
