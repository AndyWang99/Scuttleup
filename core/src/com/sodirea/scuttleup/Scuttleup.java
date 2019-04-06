package com.sodirea.scuttleup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sodirea.scuttleup.screens.MenuScreen;

public class Scuttleup extends Game {

	public SpriteBatch sb;
	public BitmapFont font;
	public OrthographicCamera cam;
	private Viewport viewport;

	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		sb = new SpriteBatch();
		font = new BitmapFont();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1280, 800);
		viewport = new FitViewport(1280, 800, cam);

		setScreen(new MenuScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.update();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
		font.dispose();
	}
}
