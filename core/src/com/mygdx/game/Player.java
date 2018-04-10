package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Player {
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    Player(){
        position.x = 100;
        position.y = 20;
        velocity.x = 5;
    }
}
