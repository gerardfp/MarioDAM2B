package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.WelcomeScreen;


/**
 * Mejora1: en lugar de poner "3 vidas" dibujo tres muñequitos
 * Mejora2: he implementado un ViewPort para el HUD
 */
public class MyGdxGame extends Game {
	public void create(){

	    setScreen(new WelcomeScreen(this));
    }
}
