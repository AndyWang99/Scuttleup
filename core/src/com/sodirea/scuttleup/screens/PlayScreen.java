package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sodirea.scuttleup.Scuttleup;

import java.util.ArrayList;

public class PlayScreen extends ScreenAdapter {

    Scuttleup game;

    private Texture bg;

    public PlayScreen(Scuttleup game) {
        this.game = game;
        bg = new Texture("catbg.png");
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
        game.cam.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setTransformMatrix(game.cam.view);
        game.sb.setProjectionMatrix(game.cam.projection);

        game.sb.begin();
        game.sb.draw(bg, 0, 0);
        game.sb.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}