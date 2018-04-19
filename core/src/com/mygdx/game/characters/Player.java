package com.mygdx.game.characters;

import com.badlogic.gdx.math.Vector2;

public class Player {
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 size = new Vector2();

    public float stateTime;

    public Harpoon harpoon = new Harpoon();
    public boolean isShooting;

    public Player(){
        size.x = 64;
        size.y = 64;
        position.x = 256;
        position.y = 10;
        velocity.x = 5;
    }
}
