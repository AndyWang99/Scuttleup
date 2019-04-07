package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.sodirea.scuttleup.screens.PlayScreen.CHECKPOINT_INTERVALS;
import static com.sodirea.scuttleup.screens.PlayScreen.PIXELS_TO_METERS;

public class Checkpoint {

    private Texture tile;
    private Vector2 position;

    private BodyDef checkpointBodyDef;
    private Body checkpointBody;
    private PolygonShape checkpointBox;

    public Checkpoint(float y, World world) {
        tile = new Texture("checkpoint.png");
        position = new Vector2(0, y);

        checkpointBodyDef = new BodyDef();
        checkpointBodyDef.position.set((position.x+tile.getWidth()/2)*PIXELS_TO_METERS, (position.y+tile.getHeight()/2)*PIXELS_TO_METERS);
        checkpointBody = world.createBody(checkpointBodyDef);
        checkpointBox = new PolygonShape();
        checkpointBox.setAsBox(tile.getWidth() / 2 * PIXELS_TO_METERS, tile.getHeight() / 2 * PIXELS_TO_METERS);
        checkpointBody.createFixture(checkpointBox, 0.0f);
        checkpointBody.setUserData(this);
    }

    public void update(float bottomOfScreenY) {
        // check if the checkpoint has gone out of screen here. if it has, then increase Y position.
    }

    public void render(SpriteBatch sb) {
        sb.draw(tile, position.x, position.y);
    }

    public void dispose() {
        tile.dispose();
        checkpointBox.dispose();
    }
}
