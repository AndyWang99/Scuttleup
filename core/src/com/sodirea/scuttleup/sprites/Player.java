package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.sodirea.scuttleup.screens.PlayScreen.PIXELS_TO_METERS;

public class Player {

    private Texture sprite;
    private Vector2 position;

    private BodyDef playerBodyDef;
    private Body playerBody;
    private CircleShape playerCircle;
    private FixtureDef playerFixtureDef;
    private Fixture playerFixture;

    public Player(float x, float y, World world) {
        sprite = new Texture("player.png");
        position = new Vector2(x, y);

        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set((position.x+sprite.getWidth()/2) * PIXELS_TO_METERS, (position.y+sprite.getWidth()/2) * PIXELS_TO_METERS); // convert render coordinates to physics body coodinates
        playerBody = world.createBody(playerBodyDef);
        playerCircle = new CircleShape();
        playerCircle.setRadius((sprite.getWidth()/2) * PIXELS_TO_METERS);
        playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = playerCircle;
        playerFixtureDef.density = 500f;
        playerFixtureDef.friction = 0f;
        playerFixture = playerBody.createFixture(playerFixtureDef);
        playerBody.setUserData(this);
    }

    public void update() {
        position.set(playerBody.getPosition().x/PIXELS_TO_METERS-sprite.getWidth()/2, playerBody.getPosition().y/PIXELS_TO_METERS-sprite.getWidth()/2); // convert physics body coordinates back to render coordinates. this ensures that the rendering position is always in sync with the physics body's position
    }

    public void render(SpriteBatch sb) {
        sb.draw(sprite, position.x, position.y);
    }

    public void dispose() {
        sprite.dispose();
        playerCircle.dispose();
    }

    public Vector2 getBodyLinearVelocity() {
        return playerBody.getLinearVelocity();
    }

    public void setBodyLinearVelocity(float x, float y) {
        playerBody.setLinearVelocity(x, y);
    }
}
