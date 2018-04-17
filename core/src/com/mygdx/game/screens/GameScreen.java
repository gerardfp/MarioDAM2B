package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets;
import com.mygdx.game.Controls;
import com.mygdx.game.characters.Ball;
import com.mygdx.game.characters.Player;

import java.util.Random;

public class GameScreen extends MyGameScreen {
    SpriteBatch batch;

    float gravity = -.2f;

    Array<Ball> balls = new Array<Ball>();
    Player player;


    float newBallTimer;
    FitViewport viewport;
    OrthographicCamera camera;
    int VIEW_PORT_WIDTH = 1024; //PPM
    int VIEW_PORT_HEIGHT = 512;

    Random random = new Random();

    GameScreen(Game game){
        super(game);
    }


    @Override
    public void show () {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT, camera);
        camera.position.x = VIEW_PORT_WIDTH /2;
        camera.position.y = VIEW_PORT_HEIGHT /2;
        camera.update();

        Assets.load();

        player = new Player();

        balls.add(new Ball(10,10,1,12, random.nextInt(5)));
        balls.add(new Ball(100,10,2,6, random.nextInt(5)));
        balls.add(new Ball(10,100,3,14, random.nextInt(5)));
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(Assets.imgBackground, 0, 0, VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT);
        for(Ball ball: balls) {
            batch.draw(Assets.imgBalls[ball.type], ball.position.x, ball.position.y, Assets.imgBalls[ball.type].getRegionWidth()*2, Assets.imgBalls[ball.type].getRegionHeight()*2);
        }
        batch.draw(Assets.walkAnimation.getKeyFrame(player.stateTime, true), player.position.x, player.position.y, player.size.x, player.size.y);
        if(player.isShooting) {
            TextureRegion tr = Assets.harpoon.getKeyFrame(player.stateTime, true);
            tr.setRegionHeight((int) player.harpoon.altura);

            batch.draw(tr, player.harpoon.position.x, player.harpoon.position.y);
        }
        batch.end();
    }

    void update(float delta){
        updateTimers(delta);
        addNewBall();
        updateBalls();
        updatePlayer(delta);
    }

    void updateTimers(float delta){
        newBallTimer += delta;
    }

    void addNewBall(){
        if(newBallTimer > 1f){
            balls.add(new Ball(10, 100, 3, 14, random.nextInt(5)));
            newBallTimer = 0;
        }
    }

    void updateBalls(){
        for(Ball ball: balls) {
            ball.velocity.y += gravity;

            ball.position.y += ball.velocity.y;
            ball.position.x += ball.velocity.x;

            if (ball.position.y < 0) {
                ball.velocity.y = ball.reboundSpeeds[ball.type];
            }

            if (ball.position.x < 0 || ball.position.x > VIEW_PORT_WIDTH - Assets.imgBalls[ball.type].getRegionWidth()) {
                ball.velocity.x *= -1;
            }
        }
    }

    void updatePlayer(float delta){
        player.stateTime += delta;
        player.harpoon.altura += delta*100;

        if(Controls.isLeftPressed()){
            player.position.x -= player.velocity.x;
        }

        if(Controls.isRightPressed()){
            player.position.x += player.velocity.x;
        }

        if(Controls.isShootPressed()){
            player.isShooting = true;
            player.harpoon.position.set(player.position);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
