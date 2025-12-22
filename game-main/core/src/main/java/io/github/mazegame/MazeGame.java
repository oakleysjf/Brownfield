package io.github.mazegame;

import io.github.mazegame.achievements.AchievementManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** The game script that initializes the game screens. */
public class MazeGame extends Game{

    public SpriteBatch batch;
    static public BitmapFont font;
    public ExtendViewport viewport;

    private AchievementManager achievementManager;

    Code code1 = new Code();
    Code code2 = new Code();
    Code code3 = new Code();
    Code code4 = new Code();
    Code code5 = new Code();
    
    /** Creates the game */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new ExtendViewport(800, 500);

        font.setUseIntegerPositions(true);
        font.getData().setScale(1);

        achievementManager = new AchievementManager();
        achievementManager.init();
        
        this.setScreen(new GameScreen(this));
    }

    /** Getters for the codes */
    public Code getCode1() {
        return code1;
    }

    public Code getCode2() {
        return code2;
    }

    public Code getCode3() {
        return code3;
    }

    public Code getCode4() {
        return code4;
    }

    public Code getCode5() {
        return code5;
    }

    public void render() {
        super.render(); // Important!!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public AchievementManager getAchievementManager() {
        return achievementManager;
    }
}
