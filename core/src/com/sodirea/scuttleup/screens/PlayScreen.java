package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.sodirea.scuttleup.Scuttleup;
import com.sodirea.scuttleup.sprites.BasicPlatform;
import com.sodirea.scuttleup.sprites.Checkpoint;
import com.sodirea.scuttleup.sprites.PistonPlatform;
import com.sodirea.scuttleup.sprites.Platform;
import com.sodirea.scuttleup.sprites.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.sodirea.scuttleup.Scuttleup.SCREEN_HEIGHT;

public class PlayScreen extends ScreenAdapter {

    public static final float PIXELS_TO_METERS = 0.01f; // default is 0.01f
    public static final int GRAVITY = -500;
    public static final float TIME_STEP = 1 / 300f;
    public static final boolean DEBUGGING = true; // don't forget to set PIXELS_TO_METERS to 1f for this to be useful (i.e. see real-sized physics bodies)

    public static final int CHECKPOINT_INTERVALS = 2000; // each checkpoint should give a new upgrade for character
    public static final int PLATFORM_INTERVALS = 200;
    public static final int NUM_PLATFORMS_IN_ARRAY = (int) Math.ceil(SCREEN_HEIGHT / PLATFORM_INTERVALS) + 1;

    Scuttleup game;

    private Texture bg;

    private Checkpoint checkpoint; // this will just move up by CHECKPOINT_INTERVALS after it goes out of screen.
    private ArrayList<Platform> platforms;
    private float highestPlatform;
    private Map<Double, String> platformChances;
    private Player player;
    private float moveCamUpToHere;
    private Random randomPlatformGen;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    public PlayScreen(Scuttleup game) {
        this.game = game;
        bg = new Texture("catbg.png");

        world = new World(new Vector2(0, GRAVITY), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() instanceof Player || contact.getFixtureB().getBody().getUserData() instanceof Player) {
                    player.addNumberOfFootContacts(); // add to the total number of contact points
                    Platform platform = null;
                    if (contact.getFixtureA().getBody().getUserData() instanceof Platform) {
                        platform = (Platform) contact.getFixtureA().getBody().getUserData();
                    } else if (contact.getFixtureB().getBody().getUserData() instanceof Platform) {
                        platform = (Platform) contact.getFixtureB().getBody().getUserData();
                    }

                    if (platform != null && player.getPosition().y > platform.getPosition().y) {
                        moveCamUpToHere = platform.getPosition().y + SCREEN_HEIGHT / 3;
                    }

                    if (platform != null && platform instanceof PistonPlatform) {
                        player.applyBodyLinearImpulse(0, 200);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA().getBody().getUserData() instanceof Player || contact.getFixtureB().getBody().getUserData() instanceof Player) {
                    player.lessNumberOfFootContacts();
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        debugRenderer = new Box2DDebugRenderer();

        checkpoint = new Checkpoint(0, world);
        highestPlatform = 0;
        platformChances = new HashMap<Double, String>();
        platformChances.put(0.7, "basic");
        platformChances.put(1.0, "piston");
        randomPlatformGen = new Random();

        platforms = new ArrayList<Platform>();

        for (int i = 0; i < NUM_PLATFORMS_IN_ARRAY; i++) {
            highestPlatform += PLATFORM_INTERVALS;
            double randomPlatform = randomPlatformGen.nextDouble();
            // TODO:: binary search to find where the random platform is in
            for (Map.Entry<Double, String> entry : platformChances.entrySet()) {
                if (randomPlatform < entry.getKey())
                {
                    if (entry.getValue().equals("basic")) {
                        platforms.add(new BasicPlatform(highestPlatform, world));
                        break;
                    } else if (entry.getValue().equals("piston")) {
                        platforms.add(new PistonPlatform(highestPlatform, world));
                        break;
                    }
                }
            }
        }
        player = new Player(game.cam.position.x, checkpoint.getTexture().getHeight(), world);
        moveCamUpToHere = game.cam.position.y;
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        // LOGIC UPDATES
        checkpoint.update(game.cam.position.y - game.cam.viewportHeight / 2);
        for (int i = 0; i < NUM_PLATFORMS_IN_ARRAY; i++) {
            Platform platform = platforms.get(i);
            if (platform.getPosition().y + platform.getTexture().getHeight() < game.cam.position.y - game.cam.viewportHeight / 2) {
                highestPlatform += PLATFORM_INTERVALS;
                double randomPlatform = randomPlatformGen.nextDouble();
                // TODO:: binary search to find where the random platform is in
                for (Map.Entry<Double, String> entry : platformChances.entrySet()) {
                    if (randomPlatform < entry.getKey())
                    {
                        if (entry.getValue().equals("basic")) {
                            platforms.set(i, new BasicPlatform(highestPlatform, world));
                            break;
                        } else if (entry.getValue().equals("piston")) {
                            platforms.set(i, new PistonPlatform(highestPlatform, world));
                            break;
                        }
                    }
                }
            }
            platform.update();
        }

        player.update(delta);

        game.cam.position.y += (moveCamUpToHere-game.cam.position.y) / 10;

        game.cam.update();

        world.step(TIME_STEP, 6, 2);


        // UI UPDATES

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setTransformMatrix(game.cam.view);
        game.sb.setProjectionMatrix(game.cam.projection);

        game.sb.begin();
        game.sb.draw(bg, game.cam.position.x - game.cam.viewportWidth / 2, game.cam.position.y - game.cam.viewportHeight / 2);
        checkpoint.render(game.sb);
        for (Platform platform : platforms) {
            platform.render(game.sb);
        }
        player.render(game.sb);
        game.sb.end();
        if (DEBUGGING) {
            debugRenderer.render(world, game.cam.combined);
        }
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose() {
        bg.dispose();
        checkpoint.dispose();
        for (Platform platform : platforms) {
            platform.dispose();
        }
        player.dispose();
    }
}