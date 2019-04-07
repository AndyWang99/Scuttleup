package com.sodirea.scuttleup.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Platform {

    public abstract void update(); // might delete this as diff platforms may require different params

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}
