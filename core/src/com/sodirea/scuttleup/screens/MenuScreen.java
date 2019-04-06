package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sodirea.scuttleup.Scuttleup;

public class MenuScreen extends ScreenAdapter {

    Scuttleup game;

    private Texture bg;

    public MenuScreen(Scuttleup game) {
        this.game = game;
        bg = new Texture("catbg.png");
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
//                    game.setScreen(new GameScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setTransformMatrix(game.cam.view);
        game.sb.setProjectionMatrix(game.cam.projection);

        game.sb.begin();
        game.sb.draw(bg, 0, 0);
        game.font.draw(game.sb, "Play", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.sb, "PVP", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.sb.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}