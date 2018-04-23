package com.mygdx.game.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Assets;

public class Harpoon {
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 size = new Vector2();

    public float stateTime;

    public float height;

    public Harpoon(){
        size.x = 64;
        size.y = 64;
        position.x = 500;
        position.y = 20;
        velocity.x = 5;
    }

    public Rectangle getRectangle(){
        return new Rectangle(
            (int) position.x,
            (int) position.y,
            Assets.harpoon.getKeyFrame(0).getRegionWidth(),
            Assets.harpoon.getKeyFrame(0).getRegionHeight());
    }
}
