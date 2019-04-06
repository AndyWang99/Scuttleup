package com.sodirea.scuttleup;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sodirea.scuttleup.states.GameStateManager;
import com.sodirea.scuttleup.states.MenuState;
import com.sodirea.scuttleup.states.PlayState;

public class Scuttleup extends ApplicationAdapter {
	private SpriteBatch sb;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new PlayState(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
	
	@Override
	public void dispose () {
		sb.dispose();

	}
}
