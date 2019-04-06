package com.sodirea.scuttleup.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

    Texture bg;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam.setToOrtho(false, w, h);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        bg = new Texture("catbg.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(bg, 0, 0);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
    }
}
