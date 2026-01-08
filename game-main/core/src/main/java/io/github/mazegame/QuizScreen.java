package io.github.mazegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.mazegame.entities.TuringMachineNPCEntity;

/**
 * A quiz screen overlay that appears when the player interacts with the Turing machine NPC.
 */
public class QuizScreen implements Screen {

    private final MazeGame game;
    private final GameScreen gameScreen;
    private final TuringMachineNPCEntity npc;
    private OrthographicCamera camera;
    private int selectedAnswer = 0;
    private boolean answered = false;
    private float feedbackTime = 0f;
    private static final float FEEDBACK_DURATION = 2f;
    private Texture whitePixel;

    public QuizScreen(MazeGame game, GameScreen gameScreen, TuringMachineNPCEntity npc) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.npc = npc;
        
        camera = new OrthographicCamera(800, 500);
        camera.position.set(400, 250, 0);
        camera.update();
        
        // Create a simple 1x1 white texture for drawing backgrounds
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        whitePixel = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Keep rendering the game in background
        gameScreen.render(delta);

        // Draw quiz overlay
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();

        // Draw semi-transparent dark background
        game.batch.setColor(0, 0, 0, 0.7f);
        game.batch.draw(whitePixel, 0, 0, 800, 500);
        game.batch.setColor(1, 1, 1, 1);

        // Draw question box background
        game.batch.setColor(0.2f, 0.2f, 0.3f, 0.9f);
        game.batch.draw(whitePixel, 40, 320, 720, 140);
        game.batch.setColor(1, 1, 1, 1);

        // Scale font up for better readability
        MazeGame.font.setColor(Color.WHITE);
        MazeGame.font.getData().setScale(2.5f);

        // Draw question
        float questionY = 430;
        MazeGame.font.draw(game.batch, TuringMachineQuiz.QUESTION, 60, questionY);

        // Draw answer options
        float answerStartY = 260;
        float answerSpacing = 80;
        MazeGame.font.getData().setScale(2.0f);

        for (int i = 0; i < TuringMachineQuiz.ANSWERS.length; i++) {
            float y = answerStartY - (i * answerSpacing);
            
            // Draw answer background
            if (i == selectedAnswer && !answered) {
                game.batch.setColor(1, 1, 0, 0.3f);
                game.batch.draw(whitePixel, 50, y - 35, 700, 60);
                game.batch.setColor(1, 1, 1, 1);
            }

            // Check if correct answer
            if (answered && i == TuringMachineQuiz.CORRECT_ANSWER) {
                MazeGame.font.setColor(Color.GREEN);
            } else if (answered && i == selectedAnswer && i != TuringMachineQuiz.CORRECT_ANSWER) {
                MazeGame.font.setColor(Color.RED);
            } else {
                MazeGame.font.setColor(Color.WHITE);
            }

            MazeGame.font.draw(game.batch, (i + 1) + ". " + TuringMachineQuiz.ANSWERS[i], 70, y);
        }

        MazeGame.font.setColor(Color.WHITE);

        // Draw feedback
        if (answered) {
            feedbackTime += delta;
            game.batch.setColor(0.2f, 0.2f, 0.3f, 0.9f);
            game.batch.draw(whitePixel, 80, 20, 640, 80);
            game.batch.setColor(1, 1, 1, 1);
            
            MazeGame.font.getData().setScale(2.0f);
            if (TuringMachineQuiz.isCorrect(selectedAnswer)) {
                MazeGame.font.setColor(Color.GREEN);
                MazeGame.font.draw(game.batch, "Correct! You earned Code4!", 110, 85);
                MazeGame.font.setColor(Color.WHITE);
            } else {
                MazeGame.font.setColor(Color.RED);
                MazeGame.font.draw(game.batch, "Incorrect! Answer: " + TuringMachineQuiz.ANSWERS[TuringMachineQuiz.CORRECT_ANSWER], 90, 85);
                MazeGame.font.setColor(Color.WHITE);
            }

            if (feedbackTime >= FEEDBACK_DURATION) {
                closeQuiz();
            }
        } else {
            game.batch.setColor(0.2f, 0.2f, 0.3f, 0.9f);
            game.batch.draw(whitePixel, 60, 10, 680, 60);
            game.batch.setColor(1, 1, 1, 1);
            MazeGame.font.getData().setScale(1.8f);
            MazeGame.font.draw(game.batch, "1-3 to select | ENTER to submit | ESC to cancel", 80, 60);
        }

        // Reset font scale
        MazeGame.font.getData().setScale(1.0f);
        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (answered) return;

        // Navigate answers
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
            selectedAnswer = (selectedAnswer - 1 + TuringMachineQuiz.ANSWERS.length) % TuringMachineQuiz.ANSWERS.length;
        }
        if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            selectedAnswer = (selectedAnswer + 1) % TuringMachineQuiz.ANSWERS.length;
        }

        // Number keys for direct selection
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) selectedAnswer = 0;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) selectedAnswer = 1;
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) selectedAnswer = 2;

        // Submit answer
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            answered = true;
            npc.submitAnswer(selectedAnswer);
            feedbackTime = 0f;
        }

        // Cancel quiz
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            closeQuiz();
        }
    }

    private void closeQuiz() {
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
