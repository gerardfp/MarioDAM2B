package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WelcomScreen extends MyGameScreen {
    SpriteBatch spriteBatch;
    Texture logotipo;
    float startGameTimer;

    public WelcomScreen(Game game){
        super(game);
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        logotipo = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        startGameTimer += delta;

        if(startGameTimer > 1){
            game.setScreen(new GameScreen(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(logotipo, 100, 100);
        spriteBatch.end();
    }
}
