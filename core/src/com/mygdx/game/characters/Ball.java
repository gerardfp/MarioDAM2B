package com.mygdx.game.characters;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

public class Ball {
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public float[] reboundSpeeds = {6, 5.5f, 5.1f, 4.8f, 4.4f};
    public float[] newBallDelays = {20, 15, 10, 8, 4};
    public int type;

    public Ball(float px, float py, float vx, float vy, int type){
        position.x = px;
        position.y = py;
        velocity.x = vx;
        velocity.y = vy;
        this.type = type;
    }

    public Circle getCircle(){
        return new Circle(
                position.x+Assets.imgBalls[type].getRegionWidth()/2,
                position.y+Assets.imgBalls[type].getRegionHeight()/2,
                Assets.imgBalls[type].getRegionWidth()/2
        );
    }
}
