package com.sodirea.scuttleup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sodirea.scuttleup.Scuttleup;

import java.util.ArrayList;
import java.util.Random;

import static com.sodirea.scuttleup.screens.PlayScreen.Walker.MAX_WALKER_NUM;
import static com.sodirea.scuttleup.screens.PlayScreen.Walker.WALKER_CHANGE_DIR_CHANCE;
import static com.sodirea.scuttleup.screens.PlayScreen.Walker.WALKER_DESPAWN_CHANCE;
import static com.sodirea.scuttleup.screens.PlayScreen.Walker.WALKER_SPAWN_CHANCE;

public class PlayScreen extends ScreenAdapter {

    public static final Vector2 MAP_DIM = new Vector2(7500, 7500);
    public static final Vector2 UNIT_DIM = new Vector2(150, 150);
    public static final Vector2 MAP_NUM_UNITS = new Vector2(50, 50);
    public static final int MIN_NUM_TILES = 300;
    public static final int MAX_NUM_TILES = 500;
    public static final float PIXELS_TO_METERS = 0.01f;
    public static final float TIME_STEP = 1 / 300f;

    Scuttleup game;

    private Texture bg;
    private Texture wall;
    private Texture floor;

    private boolean map[][];

    private World world;

    public PlayScreen(Scuttleup game) {
        this.game = game;

        bg = new Texture("catbg.png");
        wall = new Texture("wall.png");
        floor = new Texture("floor.png");

        world = new World(new Vector2(0, 0), true);

        map = new boolean[(int) MAP_NUM_UNITS.x][(int) MAP_NUM_UNITS.y];
        int trueCount = 0;
        while (trueCount < MIN_NUM_TILES || trueCount > MAX_NUM_TILES) {       // while the number of floors are less than min or greater than max, keep generating the map until we get something in between
            generateMap();
            trueCount = 0;
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j]) {
                        trueCount++;
                    }
                }
            }
            System.out.println(trueCount);
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // if this pair of coordinates is false, then it is a wall. render it into our game world
                // but if it is false, it does not necessarily mean it is a wall in the physics world;
                // only walls that are adjacent to floor (i.e. "true" in the map) will have a physics body
                if (!map[i][j]) {
                    // check if there is an adjacent floor (true in our map) to this wall (false in our map)
                    if (i > 0 && map[i - 1][j]
                            || i < map.length - 1 && map[i + 1][j]
                            || j > 0 && map[i][j - 1]
                            || j < map[i].length - 1 && map[i][j + 1]) { // if there is, then add a physics body to our world for this wall
                        BodyDef wallBodyDef;
                        Body wallBody;
                        PolygonShape wallBox;
                        FixtureDef wallFixtureDef;
                        Fixture wallFixture;
                        wallBodyDef = new BodyDef();
                        wallBodyDef.position.set((i * UNIT_DIM.x + wall.getWidth() / 2) * PIXELS_TO_METERS, (j * UNIT_DIM.y + wall.getHeight() / 2) * PIXELS_TO_METERS);
                        wallBody = world.createBody(wallBodyDef);
                        wallBox = new PolygonShape();
                        wallBox.setAsBox(wall.getWidth() / 2 * PIXELS_TO_METERS, wall.getHeight() / 2 * PIXELS_TO_METERS);
                        wallFixtureDef = new FixtureDef();
                        wallFixtureDef.shape = wallBox;
                        wallFixtureDef.density = 0.0f;
                        wallFixtureDef.friction = 0.0f;
                        wallFixture = wallBody.createFixture(wallFixtureDef);
                    }
                }
            }
        }
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SHIFT_LEFT || keyCode == Input.Keys.SPACE) {

                } else if (keyCode == Input.Keys.W || keyCode == Input.Keys.DPAD_UP) {
                    game.cam.position.y += 50;
                } else if (keyCode == Input.Keys.A || keyCode == Input.Keys.DPAD_LEFT) {
                    game.cam.position.x -= 50;
                } else if (keyCode == Input.Keys.S || keyCode == Input.Keys.DPAD_DOWN) {
                    game.cam.position.y -= 50;
                } else if (keyCode == Input.Keys.D || keyCode == Input.Keys.DPAD_RIGHT) {
                    game.cam.position.x += 50;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // LOGIC CHANGES
        game.cam.update();

        world.step(TIME_STEP, 6, 2);


        // UI CHANGES

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setTransformMatrix(game.cam.view);
        game.sb.setProjectionMatrix(game.cam.projection);

        game.sb.begin();

        game.sb.draw(bg, game.cam.position.x-game.cam.viewportWidth/2, game.cam.position.y-game.cam.viewportHeight/2);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // if this pair of coordinates is false, then it is a wall. render it into our game world
                // but if it is false, it does not necessarily mean it is a wall in the physics world;
                // only walls that are adjacent to floor (i.e. "true" in the map) will have a physics body
                if (!map[i][j]) {
                    game.sb.draw(wall, i * UNIT_DIM.x, j * UNIT_DIM.y);
                } else {
                    game.sb.draw(floor, i * UNIT_DIM.x, j * UNIT_DIM.y); // else it is floor, so add texture for floor
                }
            }
        }

        game.sb.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        bg.dispose();
    }


    // Walker class is used to help generate the map using the Drunkard Walk algorithm with multiple walkers
    protected class Walker {
        public static final int MAX_WALKER_NUM = 10;
        public static final float WALKER_SPAWN_CHANCE = 0.05f;
        public static final float WALKER_DESPAWN_CHANCE = 0.05f;
        public static final float WALKER_CHANGE_DIR_CHANCE = 0.7f;

        private Vector2 pos;            // position of walker in terms of our map indices
        private Vector2 dir;            // each coordinate is either -1 or 0 or 1; -1 means move left for x, or move down for y.

        private Walker(Vector2 pos, Vector2 dir) {
            this.pos = pos;
            this.dir = dir;
        }

        // walk the Walker 1 index in its direction. if it hits a dead-end, change its direction
        // a dead-end is 1 index before the bounds of the array. so [1 or map.length-2][1 or map.length-2]. we reserve the bounds of the array (0 and map.length-1) for walls
        void walk() {
            if (pos.x + dir.x < 1) { // check if the walker will walk out of bounds.
                dir.x = 0;                     // note that if it does, we don't want to simply reverse the direction (since it would be meaningless to re-walk the same path).
                dir.y = -1;                    // so make it walk perpendicular
                walk();                        // re-walk the Walker with the new direction
            } else if (pos.x + dir.x > map.length-2) {
                dir.x = 0;
                dir.y = 1;
                walk();
            } else if (pos.y + dir.y < 1) {
                dir.y = 0;
                dir.x = 1;
                walk();
            } else if (pos.y + dir.y > map[(int) pos.x].length-2) {
                dir.y = 0;
                dir.x = -1;
                walk();
            } else {
                pos.x += dir.x;
                pos.y += dir.y;
            }
        }

        void changeDir() {
            dir = generateNewDirection();
        }

        Vector2 getPos() {
            return pos;
        }
    }

    private Vector2 generateNewDirection() {
        Random rng = new Random();
        Vector2 direction = new Vector2(0, 0);
        if (rng.nextBoolean()) {                                        // if true, the new walker will walk x. if false, it will walk y
            if (rng.nextBoolean()) {                                    // if true, it will walk in +x
                direction.x = 1;
            } else {                                                    // else it will walk -x
                direction.x = -1;
            }
        } else {
            if (rng.nextBoolean()) {
                direction.y = 1;
            } else {
                direction.y = -1;
            }
        }
        return direction;
    }

    // generates the map
    private void generateMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = false;                                       // reset the map
            }
        }
        ArrayList<Walker> walkers = new ArrayList<Walker>();
        // get random x, y coordinate to start the walkers at
        Random rng = new Random();
        Vector2 initialCoord = new Vector2(rng.nextInt(map.length-2)+1, rng.nextInt(map.length-2)+1);   // initial coordinate of where our walkers start. this is also the coordinate of where the player will spawn. chooses a coordinate anywhere on the map, except on the maps bounds; i.e. [1-map.length-2][1-map.length-2]
        map[(int) initialCoord.x][(int) initialCoord.y] = true;
        // init the list with 4 walkers: one for each direction
        walkers.add(new Walker(initialCoord, new Vector2(-1,0)));
        walkers.add(new Walker(initialCoord, new Vector2(1,0)));
        walkers.add(new Walker(initialCoord, new Vector2(0,-1)));
        walkers.add(new Walker(initialCoord, new Vector2(0,1)));

        while (walkers.size() > 0) {
            for (int i = 0; i < walkers.size(); i++) {
                Walker walker = walkers.get(i);
                walker.walk();                                                          // walk the walkers
                map[(int) walker.getPos().x][(int) walker.getPos().y] = true;           // set the walker's position to walkable terrain
                if (walkers.size() < MAX_WALKER_NUM) {
                    // try spawning a walker
                    if (rng.nextFloat() < WALKER_SPAWN_CHANCE) {
                        Vector2 direction = generateNewDirection(); // generate a direction for the new walker
                        walkers.add(new Walker(walker.getPos(), direction));
                    }
                }
                // generate random num to possibly de-spawn walker
                if (rng.nextFloat() < WALKER_DESPAWN_CHANCE) {
                    walkers.remove(walker);
                }
                // generate random num to possibly change the walker's direction
                if (rng.nextFloat() < WALKER_CHANGE_DIR_CHANCE) {
                    walker.changeDir();
                }
            }
        }

//        player.setPosition(initialCoord.x * UNIT_DIM.x, initialCoord.y * UNIT_DIM.y);         // set coordinates of the player to the initial coordinate (which is guaranteed to be walkable)
//        cam.position.x = player.getPosition().x;
//        cam.position.y = player.getPosition().y;
        game.cam.position.x = initialCoord.x * UNIT_DIM.x;
        game.cam.position.y = initialCoord.y * UNIT_DIM.y;
        game.cam.update();
    }
}