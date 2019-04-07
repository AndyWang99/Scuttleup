package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sodirea.scuttleup.Scuttleup;
import com.sodirea.scuttleup.sprites.Checkpoint;

import java.util.ArrayList;

public class PlayScreen extends ScreenAdapter {

    public static final float PIXELS_TO_METERS = 0.01f; // default is 0.01f
    public static final int GRAVITY = -500;
    public static final float TIME_STEP = 1 / 300f;
    public static final boolean DEBUGGING = false; // don't forget to set PIXELS_TO_METERS to 1f for this to be useful (i.e. see real-sized physics bodies)
    
    Scuttleup game;

    private Texture bg;

    private Checkpoint checkpoint; // this will just move up by CHECKPOINT_INTERVALS after it goes out of screen.

    private World world;
    private Box2DDebugRenderer debugRenderer;

    public PlayScreen(Scuttleup game) {
        this.game = game;
        bg = new Texture("catbg.png");

        checkpoint = new Checkpoint(0, 0);

        world = new World(new Vector2(0, GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SHIFT_LEFT || keyCode == Input.Keys.SPACE) {

                } else if (keyCode == Input.Keys.W || keyCode == Input.Keys.DPAD_UP) {

                } else if (keyCode == Input.Keys.A || keyCode == Input.Keys.DPAD_LEFT) {

                } else if (keyCode == Input.Keys.S || keyCode == Input.Keys.DPAD_DOWN) {

                } else if (keyCode == Input.Keys.D || keyCode == Input.Keys.DPAD_RIGHT) {

                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // LOGIC UPDATES
        checkpoint.update(game.cam.position.y - game.cam.viewportHeight / 2);

        game.cam.update();

        world.step(TIME_STEP, 6, 2);


        // UI UPDATES

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setTransformMatrix(game.cam.view);
        game.sb.setProjectionMatrix(game.cam.projection);

        game.sb.begin();
        if (DEBUGGING) {
            debugRenderer.render(world, game.cam.combined);
        }
        game.sb.draw(bg, 0, 0);
        checkpoint.render(game.sb);
        game.sb.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        bg.dispose();
        checkpoint.dispose();
    }
}