package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion imgBackground;
    public static TextureRegion[] imgBalls = new TextureRegion[5];
    public static TextureRegion imgPlayer;
    public static Animation<TextureRegion> walkAnimation;

    public static void load(){
        TextureAtlas textureAtlas = new TextureAtlas("superpang.txt");

        imgPlayer = textureAtlas.findRegion("shoot");
        walkAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("move"));

        for (int i = 0; i < 5; i++) {
            imgBalls[i] = textureAtlas.findRegion("ball"+i);
        }

        imgBackground = textureAtlas.findRegion("background");
    }
}
