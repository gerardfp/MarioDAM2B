package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;

public class HUD {

    SpriteBatch batch;
    GameScreen gameScreen;
    BitmapFont bitmapFont;

    public HUD(SpriteBatch batch, GameScreen gameScreen){
        this.batch = batch;
        this.gameScreen = gameScreen;

        bitmapFont = new BitmapFont();
    }

    public void render(){
        batch.begin();
        bitmapFont.draw(batch, String.valueOf(gameScreen.player.lifes), 500, 450);
        bitmapFont.draw(batch, gameScreen.balls.size + " BALLS", 50, 450);
        batch.draw(Assets.lives, 550, 440);
        batch.end();
    }
}
