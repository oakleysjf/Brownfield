package io.github.mazegame;

//#region Imports

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.mazegame.entities.GlueEntity;
import io.github.mazegame.entities.PlayerEntity;
import io.github.mazegame.entities.QuestGiverEntity;
import io.github.mazegame.items.ItemID;
import io.github.mazegame.items.Items;

//#endregion

/** The screen the main game uses to host gameplay */
public class GameScreen implements Screen {

    //#region varaiables
    
    // ================The Game===============
    final MazeGame GAME;
    public Grid grid;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private boolean isPaused = false;
    private float elapsedTime = 0f;
    private static final float GAME_TIME_LIMIT = 300f; // 5 minutes in seconds
    
    // Static reference for player to access game screen for quiz interaction
    public static GameScreen currentGameScreen;

    // ===================UI==================
    PauseMenu pauseMenu;

    // ================Textures===============
    Texture playerTexture;

    //#endregion

    //#region Initialisation 

    /** Creates the game screen */
    public GameScreen(final MazeGame game){
        this.GAME = game;
        GameScreen.currentGameScreen = this; // Set static reference for quiz interaction
        EntityManager.clear(); // Clear all entities from previous game
        PlayerEntity.instance = null; // Reset player singleton
        PlayerEntity.hasWon = false; // Reset win flag
        CodeManager.resetCodes(); // Reset code collection state
        GameStats.reset(); // Reset stats for new game
        grid = new Grid("levels/level3.txt");
        EntityManager.add(new QuestGiverEntity(new Vector2(1,1), Items.get(ItemID.RED_TEST_BOX), Items.get(ItemID.TEST_BOX)));
        EntityManager.add(new GlueEntity(new Vector2(5,3)));
        EntityManager.add(new io.github.mazegame.entities.EnergyDrinkEntity(new Vector2(10, 9)));
        
        // Add Turing Machine NPC that will give Code4 on correct answer
        EntityManager.add(new io.github.mazegame.entities.TuringMachineNPCEntity(new Vector2(18, 5)));
        
        // Add code collectibles to reachable areas in the maze (based on level3.txt layout)
        io.github.mazegame.Code code1 = new io.github.mazegame.Code();
        io.github.mazegame.Code code2 = new io.github.mazegame.Code();
        io.github.mazegame.Code code5 = new io.github.mazegame.Code();
        
        // Place codes in reachable floor areas of the maze (Code3 and Code4 removed - obtained via NPC trades)
        EntityManager.add(new io.github.mazegame.entities.CollectibleEntity(new Vector2(2, 1), code1, "Code1"));
        EntityManager.add(new io.github.mazegame.entities.CollectibleEntity(new Vector2(13, 1), code2, "Code2"));
        EntityManager.add(new io.github.mazegame.entities.CollectibleEntity(new Vector2(20, 11), code5, "Code5"));
        
        // Register codes with CodeManager
        io.github.mazegame.CodeManager.registerCode(0, code1);
        io.github.mazegame.CodeManager.registerCode(1, code2);
        io.github.mazegame.CodeManager.registerCode(4, code5);
        
        // Makes the camera and sets the viewport to use the camera.
        camera = new OrthographicCamera(800, 500);
        GAME.viewport.setCamera(camera);
        
        // Create UI camera for fixed screen overlay
        uiCamera = new OrthographicCamera(800, 500);
        uiCamera.position.set(400, 250, 0);
        uiCamera.update();

        // Creates the pause menu.
        pauseMenu = new PauseMenu(this);
	}

    //#endregion


    //#region Update

    @Override
    public void render(float delta) {
        logic(delta);
        input();
        NotificationManager.instance.update(delta);
        if (!isPaused) {
            elapsedTime += delta;
        }
        if (elapsedTime >= GAME_TIME_LIMIT) {
            // Time's up - player caught by dean
            GAME.setScreen(new LoseScreen(GAME));
            return;
        }
        if (PlayerEntity.hasWon) {
            // Transition to win screen
            GAME.setScreen(new WinScreen(GAME));
            return;
        }
        draw();

    }

    /** Handles the logic of the main game.
     *  
     * @param delta the time since the last frame.
     */
    public void logic(float delta) {
        // Updates all the enities in the entity manager when the game isn't paused.
        if (!isPaused) {
            EntityManager.update(delta);
        }
    }

    /** Handles general inputs. */
    private void input() {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            togglePause();
        }
    }

    /** Toggles the game between the paused and unpaused state. */
    public void togglePause(){
        if (!isPaused){
            pause();
        } else {
            resume();
        }
    }

    public void onPlayerWin() {
        // Win logic is handled by PlayerEntity.hasWon flag
        // and GameScreen.render() transitions to WinScreen
        System.out.println("You win!");
    }

    //#endregion



    //#region Drawing

    /** Draws all the elements for the main game */
    public void draw() {
        // Clears the screen
        ScreenUtils.clear(Color.BLUE);

        // Moves the camera to the player and clamps it as to not see outside the maze.
        Vector2 halfViewport = new Vector2(camera.viewportWidth, camera.viewportHeight).scl(0.5f);
        Vector2 cameraPosition = Extensions.clamp(PlayerEntity.instance.getOrigin(), halfViewport, new Vector2(Grid.worldBounds.x, Grid.worldBounds.y).sub(halfViewport));
        camera.position.set(new Vector3(cameraPosition, 0));
        
        // Sets the camera and begins the spritebatch for drawing
        GAME.viewport.apply();
        GAME.batch.setProjectionMatrix(camera.combined);
        GAME.batch.begin();
        
        grid.draw(GAME.batch);
        EntityManager.draw(GAME.batch);

        GAME.batch.end();

        // Render notifications
        GAME.batch.setProjectionMatrix(GAME.viewport.getCamera().combined);
        GAME.batch.begin();
        NotificationManager.instance.draw(GAME.batch, GAME.viewport.getWorldWidth(), GAME.viewport.getWorldHeight());
        
        // Draw timer in top-middle
        float remainingTime = GAME_TIME_LIMIT - elapsedTime;
        int minutes = (int) remainingTime / 60;
        int seconds = (int) remainingTime % 60;
        String timeString = String.format("%d:%02d", minutes, seconds);
        
        MazeGame.font.getData().setScale(2f);
        com.badlogic.gdx.graphics.g2d.GlyphLayout timerLayout = new com.badlogic.gdx.graphics.g2d.GlyphLayout(MazeGame.font, timeString);
        float timerX = (GAME.viewport.getWorldWidth() - timerLayout.width) / 2f;
        float timerY = GAME.viewport.getWorldHeight() - 40;
        MazeGame.font.draw(GAME.batch, timeString, timerX, timerY);
        
        // Draw subtitle
        MazeGame.font.getData().setScale(0.8f);
        String subtitle = "Escape from Uni!";
        com.badlogic.gdx.graphics.g2d.GlyphLayout subtitleLayout = new com.badlogic.gdx.graphics.g2d.GlyphLayout(MazeGame.font, subtitle);
        float subtitleX = (GAME.viewport.getWorldWidth() - subtitleLayout.width) / 2f;
        float subtitleY = timerY - 30;
        MazeGame.font.draw(GAME.batch, subtitle, subtitleX, subtitleY);
        
        GAME.batch.end();

        // UI elements
        if (isPaused) {
            pauseMenu.stage.act(Gdx.graphics.getDeltaTime());
            pauseMenu.stage.draw();
        }
    }

    //#endregion


    //#region Screen Overrides

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        playerTexture.dispose();
        grid.dispose();
    }

    @Override
    public void resize(int width, int height) {
        GAME.viewport.update(width, height, true);
    }

    //#endregion
}
