package com.mygdx.game.characters;

import com.badlogic.gdx.math.Vector2;

public class Player {
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public float stateTime;

    public Harpoon harpoon = new Harpoon();
    public boolean isShooting;

    public Player(float px, float py){
        position.x = px;
        position.y = py;
        velocity.x = 5;
    }
}
