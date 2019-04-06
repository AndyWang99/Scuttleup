package com.sodirea.scuttleup.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {

    public static final float WORLD_WIDTH = 10000;
    public static final float WORLD_HEIGHT = 10000;
    public static final float VIEW_WORLD_PERCENTAGE = 0.1f;

    private Texture bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        // Height is multiplied by aspect ratio
        cam.setToOrtho(false, WORLD_WIDTH * VIEW_WORLD_PERCENTAGE, WORLD_HEIGHT * VIEW_WORLD_PERCENTAGE * (h / w));
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
