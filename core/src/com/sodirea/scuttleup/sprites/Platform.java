package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Platform {

    public abstract void update(); // might delete this as diff platforms may require different params

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();

    public abstract Vector2 getPosition();

    public abstract Texture getTexture();
}
