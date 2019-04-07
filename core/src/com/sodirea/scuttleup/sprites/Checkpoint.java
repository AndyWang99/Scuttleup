package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Checkpoint {

    public static final int CHECKPOINT_INTERVALS = 2000; // each checkpoint should give a new upgrade for character

    private Texture tile;
    private Vector2 position;

    public Checkpoint(float x, float y) {
        tile = new Texture("checkpoint.png");
        position = new Vector2(x, y);
    }

    public void update(float bottomOfScreenY) {
        // check if the checkpoint has gone out of screen here. if it has, then increase Y position.
    }

    public void render(SpriteBatch sb) {
        sb.draw(tile, position.x, position.y);
    }

    public void dispose() {
        tile.dispose();
    }
}
