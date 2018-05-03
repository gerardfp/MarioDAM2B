package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion imgBackground;
    public static TextureRegion[] imgBalls = new TextureRegion[5];
    public static Animation<TextureRegion> walkAnimation, idleAnimation, shootAnimation, deadAnimation;
    public static Animation<TextureRegion> harpoon;
    public static TextureRegion lives;

    public static void load(){
        TextureAtlas textureAtlas = new TextureAtlas("superpang.txt");

        walkAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("move"));
        idleAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("idle"));
        shootAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("shoot"));
        deadAnimation = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("dead"));

        harpoon = new Animation<TextureRegion>(0.1f, textureAtlas.findRegions("harpoon"));

        for (int i = 0; i < 5; i++) {
            imgBalls[i] = textureAtlas.findRegion("ball"+i);
        }

        imgBackground = textureAtlas.findRegion("background");

        lives = textureAtlas.findRegion("lives");
    }
}
