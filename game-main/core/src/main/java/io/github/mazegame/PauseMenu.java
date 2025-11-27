package io.github.mazegame;

//#region imports

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

//#endregion

/** Creates the pause menu. */
public class PauseMenu {
    final GameScreen game;
    Stage stage;

    /** Creates the UI section for the pause menu. */
    public PauseMenu(final GameScreen game){
        this.game = game;
        // Creates the stage and the UI skin.
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        // Creates the table all UI elements will use.
        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        table.center().pad(10);

        //The paused text at the top of the pause UI.
        Label pausedText = new Label("Paused", skin);

        // Creates the resume button and makes it resume the game when pressed.
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.togglePause();
                return false;
            }
        });

        // Creates the exit button and makes it close the game when pressed.
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.exit();
                System.exit(0);
                return false;
            }
        });

        // Adds all the UI elements to the table and adds the table to the stage, so it can be drawn.
        table.add(pausedText).row();
        table.add(resumeButton).fill().row();
        table.add(exitButton).fill().row();

        stage.addActor(table);
    }
}
