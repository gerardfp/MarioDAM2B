package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kotcrab.vis.ui.VisUI;
import javafx.scene.control.Tab;

public class MenuScreen extends MyGameScreen {

    Stage stage;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        VisUI.load();
        stage = new Stage();

        TextButton button = new TextButton("START GAME", VisUI.getSkin());
        TextButton button2 = new TextButton("OPCIONES", VisUI.getSkin());
        Table table = new Table();
        table.add(button);
        table.setFillParent(true);
        table.row();
        table.add(button2);

        stage.addActor(table);

        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();
    }
}
