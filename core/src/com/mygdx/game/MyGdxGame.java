package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.WelcomScreen;

public class MyGdxGame extends Game {
	public void create(){
	    setScreen(new WelcomScreen(this));
    }
}
