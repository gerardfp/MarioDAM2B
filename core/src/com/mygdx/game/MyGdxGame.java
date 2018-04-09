package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion imgBackground;
	TextureRegion imgBall;

	float x=100, y=800;
	float gravity = -.2f;
	float velocidadX = 2f;
	float velocidadY = 2f;
	float reboundSpeed = 12;

	@Override
	public void create () {
		batch = new SpriteBatch();
		TextureAtlas textureAtlas = new TextureAtlas("superpang.txt");
		imgBall = textureAtlas.findRegion("ball0");
		imgBackground = textureAtlas.findRegion("background");

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		velocidadY += gravity;

		y += velocidadY;
		x += velocidadX;

		if (y < 0){
		    velocidadY = reboundSpeed;
        }
        if(x < 0 || x > Gdx.graphics.getWidth()-imgBall.getRegionWidth()){
			velocidadX *= -1;
		}

		batch.begin();
		batch.draw(imgBackground,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(imgBall, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
