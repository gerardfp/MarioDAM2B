package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets;
import com.mygdx.game.Controls;
import com.mygdx.game.characters.Ball;
import com.mygdx.game.characters.Player;

import java.util.Iterator;
import java.util.Random;

public class GameScreen extends MyGameScreen {
    boolean DEBUG = true;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    float gravity = -.1f;

    Array<Ball> balls = new Array<Ball>();
    Player player;


    float newBallTimer;
    float newBallDelay;
    FitViewport viewport;
    OrthographicCamera camera;
    int VIEW_PORT_WIDTH; //PPM
    int VIEW_PORT_HEIGHT;

    int WORLD_MARGIN = 8;

    Random random = new Random();

    GameScreen(Game game){
        super(game);
    }


    @Override
    public void show () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Assets.load();

        VIEW_PORT_WIDTH = Assets.imgBackground.getRegionWidth();
        VIEW_PORT_HEIGHT = Assets.imgBackground.getRegionHeight();

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIEW_PORT_WIDTH, VIEW_PORT_HEIGHT, camera);
        camera.position.x = VIEW_PORT_WIDTH /2;
        camera.position.y = VIEW_PORT_HEIGHT /2;
        camera.update();

        player = new Player(VIEW_PORT_WIDTH/2, WORLD_MARGIN);
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


        if(DEBUG){
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0,1,0,1);
            for(Ball ball: balls) {
                Circle circle = ball.getCircle();
                shapeRenderer.circle(circle.x, circle.y, circle.radius);
            }
            Rectangle rectangle = player.harpoon.getRectangle();
            shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            shapeRenderer.end();

        }
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
        if(newBallTimer > newBallDelay){
            Ball ball = new Ball(random.nextInt(VIEW_PORT_WIDTH),VIEW_PORT_HEIGHT,1,0, random.nextInt(5));
            balls.add(ball);
            newBallDelay = ball.newBallDelays[ball.type];

            newBallTimer = 0;
        }
    }

    void updateBalls(){

        Iterator<Ball> ballIterator = balls.iterator();

        while(ballIterator.hasNext()) {
            Ball ball = ballIterator.next();

            ball.velocity.y += gravity;

            ball.position.y += ball.velocity.y;
            ball.position.x += ball.velocity.x;

            if (ball.position.y < 0) {
                ball.velocity.y = ball.reboundSpeeds[ball.type];
            }

            if (ball.position.x < 0 || ball.position.x > VIEW_PORT_WIDTH - Assets.imgBalls[ball.type].getRegionWidth()) {
                ball.velocity.x *= -1;
            }

            if(player.isShooting && Intersector.overlaps(ball.getCircle(), player.harpoon.getRectangle())){
                ballIterator.remove();
                if(ball.type != 4) {
                    balls.add(new Ball(ball.position.x, ball.position.y, 1, 1.2f, ball.type+1));
                    balls.add(new Ball(ball.position.x, ball.position.y, -1, 1.2f, ball.type+1));
                }
                player.isShooting = false;
                player.harpoon.height = 0;
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
