package com.sodirea.scuttleup.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sodirea.scuttleup.Scuttleup;

import static com.sodirea.scuttleup.Scuttleup.SCREEN_HEIGHT;
import static com.sodirea.scuttleup.Scuttleup.SCREEN_WIDTH;
import static com.sodirea.scuttleup.Scuttleup.TITLE;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TITLE;
		config.width = SCREEN_WIDTH;
		config.height = SCREEN_HEIGHT;
		new LwjglApplication(new Scuttleup(), config);
	}
}
