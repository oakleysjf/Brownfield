package io.github.mazegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.entities.ComputerEntity;

/**
 * A simple number guessing minigame played on the computer terminal.
 * Guess the random number between 1-10 to earn a code reward.
 */
public class ComputerMinigameScreen implements Screen {

    private final MazeGame game;
    private final GameScreen gameScreen;
    private final ComputerEntity computer;
    private OrthographicCamera camera;
    private Texture whitePixel;
    
    private int secretNumber;
    private int playerGuess = 5;
    private String feedback = "Guess a number 1-10";
    private boolean gameWon = false;
    private float winTime = 0f;
    private static final float WIN_DISPLAY_TIME = 2f;

    public ComputerMinigameScreen(MazeGame game, GameScreen gameScreen, ComputerEntity computer) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.computer = computer;
        
        camera = new OrthographicCamera(800, 500);
        camera.position.set(400, 250, 0);
        camera.update();
        
        // Create a simple 1x1 white texture for drawing backgrounds
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
        
        // Generate random secret number
        secretNumber = 1 + (int)(Math.random() * 10);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Keep rendering the game in background
        gameScreen.render(delta);

        // Draw minigame overlay
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();

        // Draw semi-transparent dark background
        game.batch.setColor(0, 0, 0, 0.7f);
        game.batch.draw(whitePixel, 0, 0, 800, 500);
        game.batch.setColor(1, 1, 1, 1);

        // Draw game box background
        game.batch.setColor(0.1f, 0.2f, 0.3f, 0.9f);
        game.batch.draw(whitePixel, 100, 150, 600, 300);
        game.batch.setColor(1, 1, 1, 1);

        // Draw title
        MazeGame.font.setColor(Color.CYAN);
        MazeGame.font.getData().setScale(2.5f);
        MazeGame.font.draw(game.batch, "NUMBER GUESSING GAME", 120, 420);

        // Draw instructions
        MazeGame.font.setColor(Color.WHITE);
        MazeGame.font.getData().setScale(2.0f);
        MazeGame.font.draw(game.batch, "Guess the secret number (1-10)", 130, 360);

        if (!gameWon) {
            // Draw current guess
            MazeGame.font.setColor(Color.YELLOW);
            MazeGame.font.getData().setScale(3.0f);
            MazeGame.font.draw(game.batch, "Your guess: " + playerGuess, 200, 280);

            // Draw feedback
            if (playerGuess < secretNumber) {
                MazeGame.font.setColor(Color.ORANGE);
                feedback = "Too low! Try higher";
            } else if (playerGuess > secretNumber) {
                MazeGame.font.setColor(Color.ORANGE);
                feedback = "Too high! Try lower";
            } else {
                MazeGame.font.setColor(Color.GREEN);
                feedback = "CORRECT!";
                gameWon = true;
            }

            MazeGame.font.getData().setScale(2.0f);
            MazeGame.font.draw(game.batch, feedback, 150, 200);

            // Draw controls
            MazeGame.font.setColor(Color.WHITE);
            MazeGame.font.getData().setScale(1.6f);
            MazeGame.font.draw(game.batch, "Q/E to adjust | ENTER to guess | ESC to quit", 110, 100);
        } else {
            // Draw victory message
            MazeGame.font.setColor(Color.GREEN);
            MazeGame.font.getData().setScale(2.5f);
            MazeGame.font.draw(game.batch, "YOU WIN! Code3 earned!", 140, 280);

            winTime += delta;
            if (winTime >= WIN_DISPLAY_TIME) {
                closeGame(true);
            }
        }

        // Reset font scale
        MazeGame.font.getData().setScale(1.0f);
        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (gameWon) return;

        // Adjust guess with Q/E keys
        if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            if (playerGuess > 1) playerGuess--;
        }
        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            if (playerGuess < 10) playerGuess++;
        }

        // Direct number input
        for (int i = 1; i <= 9; i++) {
            if (Gdx.input.isKeyJustPressed(Keys.valueOf("NUM_" + i))) {
                playerGuess = i;
            }
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_0)) {
            playerGuess = 10;
        }

        // Submit guess
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            if (playerGuess == secretNumber) {
                gameWon = true;
            }
        }

        // Cancel game
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            closeGame(false);
        }
    }

    private void closeGame(boolean won) {
        if (won) {
            computer.submitAnswer(0); // Mark as interacted and rewarded
        }
        game.setScreen(gameScreen);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        whitePixel.dispose();
    }
}
