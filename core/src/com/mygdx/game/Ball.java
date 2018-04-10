package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Ball {
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    float reboundSpeed = 12;

    Ball(){
        position.x = 0;
        position.y = 0;
        velocity.x = 1;
        velocity.y = 1;
    }

    Ball(float px, float py, float vx, float vy){
        position.x = px;
        position.y = py;
        velocity.x = vx;
        velocity.y = vy;
    }

    Ball(Vector2 position, Vector2 velocity){
        this.position = position;
        this.velocity = velocity;
    }
}
