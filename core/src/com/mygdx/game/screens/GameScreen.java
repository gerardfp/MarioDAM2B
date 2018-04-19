package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
    int VIEW_PORT_WIDTH = 512; //PPM
    int VIEW_PORT_HEIGHT = 256;

    int WORLD_MARGIN = 10;

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
        batch.draw(Assets.imgBackground, 0, 0);
        for(Ball ball: balls) {
            batch.draw(Assets.imgBalls[ball.type], ball.position.x, ball.position.y);
        }
        batch.draw(Assets.walkAnimation.getKeyFrame(player.stateTime, true), player.position.x, player.position.y);
        if(player.isShooting) {
            TextureRegion tr = Assets.harpoon.getKeyFrame(player.stateTime, true);
            tr.setRegionHeight((int) player.harpoon.height);

            batch.draw(tr, player.harpoon.position.x, player.harpoon.position.y);
        }
        batch.end();
    }

    void update(float delta){
        updateTimers(delta);
        addNewBall();
        updateBalls();
        updatePlayer(delta);
        updateHarpoon(delta);
    }

    void updateTimers(float delta){
        newBallTimer += delta;
    }

    void addNewBall(){
        if(newBallTimer > 3f){
            balls.add(new Ball(10, 100, 3, 14, random.nextInt(5)));
            newBallTimer = 0;
        }
    }

    void updateBalls(){
        Rectangle harpoonRectangle = new Rectangle(
                (int) player.harpoon.position.x,
                (int) player.harpoon.position.y,
                Assets.harpoon.getKeyFrame(0).getRegionWidth(),
                Assets.harpoon.getKeyFrame(0).getRegionHeight());

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

            Circle ballCircle = new Circle(
                    ball.position.x+Assets.imgBalls[ball.type].getRegionWidth()/2,
                    ball.position.y+Assets.imgBalls[ball.type].getRegionHeight()/2,
                    Assets.imgBalls[ball.type].getRegionWidth()/2
                    );

            if(player.isShooting && Intersector.overlaps(ballCircle, harpoonRectangle)){
                System.out.println("INTERSECCION!!!!");
                player.isShooting = false;
            }
        }
    }

    void updatePlayer(float delta){
        player.stateTime += delta;


        if(Controls.isLeftPressed()){
            player.position.x -= player.velocity.x;
        }

        if(Controls.isRightPressed()){
            player.position.x += player.velocity.x;
        }

        if(Controls.isShootPressed() && !player.isShooting){
            player.isShooting = true;
            player.harpoon.position.set(player.position);
            player.harpoon.height = 0;
        }
    }

    void updateHarpoon(float delta){
        player.harpoon.height += delta*100;

        if(player.harpoon.position.y + player.harpoon.height >= VIEW_PORT_HEIGHT-WORLD_MARGIN){
            player.isShooting = false;
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
