package com.mygdx.game.characters;

import com.badlogic.gdx.math.Vector2;

public class Ball {
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public float[] reboundSpeeds = {12, 11, 10, 9, 8};
    public int type;

    public Ball(float px, float py, float vx, float vy, int type){
        position.x = px;
        position.y = py;
        velocity.x = vx;
        velocity.y = vy;
        this.type = type;
    }
}
