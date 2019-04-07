package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sodirea.scuttleup.Scuttleup;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    Scuttleup game;

    private Texture bg;
    private ArrayList<String> menuItems;
    private int menuIndex;

    public MenuScreen(Scuttleup game) {
        this.game = game;
        bg = new Texture("catbg.png");
        menuItems = new ArrayList<String>();
        menuItems.add("Play");
        menuItems.add("Exit");
        menuIndex = 0;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SHIFT_LEFT || keyCode == Input.Keys.SPACE) {
                    if (menuIndex == 0) {
                        game.setScreen(new PlayScreen(game));
                    } else if (menuIndex == 1) {
                        System.exit(0);
                    }
                } else if (menuIndex > 0 && (keyCode == Input.Keys.W || keyCode == Input.Keys.DPAD_UP)) { // go up on menu
                    menuIndex--;
                } else if (menuIndex < menuItems.size()-1 && (keyCode == Input.Keys.S || keyCode == Input.Keys.DPAD_DOWN)) {
                    menuIndex++;
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
        for (int i = 0; i < menuItems.size(); i++) {
            String menuItem = menuItems.get(i);
            if (menuIndex == i) {
                game.font.setColor(1, 1, 1, 1f);
            }
            game.font.draw(game.sb, menuItem, game.cam.viewportWidth * .25f, game.cam.viewportHeight * (1f - ((i+1) * .2f)));
            if (menuIndex == i) {
                game.font.setColor(1, 1, 1, 0.4f);
            }
        }
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