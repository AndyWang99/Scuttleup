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

	public static final String TITLE = "Scuttleup";
	// Game will start with this window size
	// Every player will always see this size on their screen (i.e. their viewport). Even upon resizing this is how much they will see
	// Resizing would just scale up or down the graphics, but still show them this size of information / the viewport
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 800;

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
		cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);

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
