package io.github.mazegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

/** Display screen shown when the player is caught by the dean (time runs out), showing game over message. */
public class LoseScreen implements Screen {
    private final MazeGame game;
    private OrthographicCamera camera;
    
    /** Creates the lose screen with a reference to the main game.
     * 
     * @param game the MazeGame instance
     */
    public LoseScreen(MazeGame game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        camera = new OrthographicCamera(800, 500);
        camera.position.set(400, 250, 0);
        camera.update();
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        
        // Draw title
        MazeGame.font.getData().setScale(2f);
        MazeGame.font.draw(game.batch, "You were caught by the dean!", 150, 420);
        
        // Draw stats
        MazeGame.font.getData().setScale(1.5f);
        MazeGame.font.draw(game.batch, "GAME OVER", 300, 350);
        
        MazeGame.font.getData().setScale(1f);
        MazeGame.font.draw(game.batch, "Codes Collected: " + GameStats.codesCollected + "/5", 150, 300);
        MazeGame.font.draw(game.batch, "Quest Giver Interactions: " + GameStats.questGiverInteractions, 150, 260);
        MazeGame.font.draw(game.batch, "Puzzles Achieved: " + GameStats.puzzlesAchieved, 150, 220);
        MazeGame.font.draw(game.batch, "Glue Used: " + GameStats.glueUsageCount, 150, 180);
        MazeGame.font.draw(game.batch, "Energy Drinks Used: " + GameStats.energyDrinksUsed, 150, 140);
        
        // Draw instructions
        MazeGame.font.getData().setScale(0.8f);
        MazeGame.font.draw(game.batch, "Press SPACE to return to menu or ESC to exit", 100, 100);
        
        game.batch.end();
        
        // Handle input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Return to main menu (or restart)
            game.setScreen(new GameScreen(game));
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {}
}
