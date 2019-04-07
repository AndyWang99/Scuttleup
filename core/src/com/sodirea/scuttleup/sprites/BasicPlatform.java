package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sodirea.scuttleup.Scuttleup;

import java.util.Random;

import static com.sodirea.scuttleup.screens.PlayScreen.PIXELS_TO_METERS;

public class BasicPlatform extends Platform {

    private Texture tile;
    private Vector2 position;
    private Random randomXGen;

    private BodyDef platformBodyDef;
    private Body platformBody;
    private PolygonShape platformBox;

    public BasicPlatform(float y, World world) {
        tile = new Texture("basic-platform.png");
        randomXGen = new Random();
        // random X position between 0 and width of app - tile width
        position = new Vector2((randomXGen.nextFloat() * Scuttleup.SCREEN_WIDTH) - tile.getWidth(), y);

        platformBodyDef = new BodyDef();
        platformBodyDef.position.set((position.x+tile.getWidth()/2)*PIXELS_TO_METERS, (position.y+tile.getHeight()/2)*PIXELS_TO_METERS);
        platformBody = world.createBody(platformBodyDef);
        platformBox = new PolygonShape();
        platformBox.setAsBox(tile.getWidth() / 2 * PIXELS_TO_METERS, tile.getHeight() / 2 * PIXELS_TO_METERS);
        platformBody.createFixture(platformBox, 0.0f);
        platformBody.setUserData(this);
    }

    public void update() {
    }

    public void render(SpriteBatch sb) {
        sb.draw(tile, position.x, position.y);
    }

    public void dispose() {
        tile.dispose();
        platformBox.dispose();
    }
}
