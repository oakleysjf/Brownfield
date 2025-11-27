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
    private boolean isPaused = false;

    // ===================UI==================
    PauseMenu pauseMenu;

    // ================Textures===============
    Texture playerTexture;

    //#endregion

    //#region Initialisation 

    /** Creates the game screen */
    public GameScreen(final MazeGame game){
        this.GAME = game;
        grid = new Grid("levels/level2.txt");
        EntityManager.add(new QuestGiverEntity(new Vector2(1,1), Items.get(ItemID.RED_TEST_BOX), Items.get(ItemID.TEST_BOX)));
        EntityManager.add(new GlueEntity(new Vector2(5,3)));
        
        // Makes the camera and sets the viewport to use the camera.
        camera = new OrthographicCamera(800, 500);
        GAME.viewport.setCamera(camera);

        // Creates the pause menu.
        pauseMenu = new PauseMenu(this);
	}

    //#endregion


    //#region Update

    @Override
    public void render(float delta) {
        logic(delta);
        input();
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
}
