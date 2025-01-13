package com.mygdx.sfs.Scenes.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.Scenes.Hud;
import com.mygdx.sfs.Sprites.Breadcrumbs;
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Items.Item;
import com.mygdx.sfs.Sprites.Items.ItemDef;
import com.mygdx.sfs.Sprites.Items.KeyCard;
import com.mygdx.sfs.Sprites.Items.HealthCrate;
import com.mygdx.sfs.Sprites.Items.Rifles;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.Sprites.Items.Bullets;
import com.mygdx.sfs.Tools.B2WorldCreator;
import com.mygdx.sfs.Tools.Controller;
import com.mygdx.sfs.Tools.WorldContactListener;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private shootForSurvival game;
    public AssetManager manager;

    //basic variables
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //tiled map variables
    private int area = 1;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    public World world;
    private Box2DDebugRenderer b2dr;
    public B2WorldCreator creator;

    //Player variable
    private Player player;
    private float statetimer;

    //Bullet Variable
    private ArrayList<Bullets> bullets;
    private double bulletDamage = 0;

    //Sprite Variable
    private Array<Item> items;
    public LinkedBlockingQueue<ItemDef> itemToSpawn;
    private int coins;
    private int keys;

    //scanner Variables
    private boolean interact = false;
    private Vector2 scannerPostion = new Vector2 (0,0);

    //finish level variable
    public boolean complete = false;
    private boolean scannerJustTouched = false;

    //level variable
    private int level;

    //controller creation
    public Controller controller;

    //Bread Crumbs Variable
    private ArrayList<Breadcrumbs> crumbs;
    private Breadcrumbs bread;
    private Vector2 crumbpos;
    private Vector2 bossPos;
    private float breadcrumbTimer = 0f;
    private static final float BREADCRUMB_INTERVAL = 5.0f; // seconds
    private static final int MAX_BREADCRUMBS = 4;

    public PlayScreen(shootForSurvival g, int location, int level) {

        //game management inits
        this.game = g;
        this.level = level;
        this.manager = game.getManager();

        //view of the game
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(shootForSurvival.MAP_WIDTH / shootForSurvival.PPM, shootForSurvival.MAP_HEIGHT / shootForSurvival.PPM, gamecam);
        hud = new Hud(game.batch, game, game.getScreen(), this);

        //controller
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller = new Controller(game);
        }

        //bullet init
        bullets = new ArrayList<Bullets>();


        //render/map setup
        area = location;
        mapLoader = new TmxMapLoader();
        if(area == 1 ) {
            map = mapLoader.load("Maps/Industry/Map/Lvl1-" + level + ".tmx");
        }
        else if(area == 2) {
            map = mapLoader.load("Maps/Residential/Map/Lvl2-" + level + ".tmx");

        }
        renderer = new OrthogonalTiledMapRenderer(map, 1 / shootForSurvival.PPM);


        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 1.5f, gamePort.getWorldHeight() / 1.5f, 0);

        world = new World(new Vector2(0, -10), true);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(game, this);

        //Player creation
        player = new Player(this, game);
        world.setContactListener(new WorldContactListener());

        game.loadMusic("audio/music/yoitrax - Diamonds.mp3");
        if (game.getVolume() != 0) {
            game.music.play();
            game.music.setVolume(game.getVolume());
        }
        game.setSoundVolume(game.getSoundVolume());


        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
        coins = game.getMoney();
        System.out.println("playscreen bitch" + coins);


        game.setWorld(world);


        //breadcrumbs init
        crumbs = new ArrayList<Breadcrumbs>(MAX_BREADCRUMBS);
        if(level == 10) {
            addBreadcrumb(); // Start with one breadcrumb
        }
    }

    public void spawnItem(ItemDef idef) {
        itemToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDef idef = itemToSpawn.poll();
            if (idef.type == HealthCrate.class) {
                items.add(new HealthCrate(game, this, idef.position.x, idef.position.y));
            }
            if (idef.type == KeyCard.class) {
                items.add(new KeyCard(game, this, idef.position.x, idef.position.y));
            }
            if (idef.type == Rifles.class) {
                items.add(new Rifles(game, this, idef.position.x, idef.position.y));
            }
        }
    }


    public void handleInput(float dt) {

//PC
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (player.currentState != Player.State.DEAD) {
//jump / double jump
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && game.jumpCounter < 2 && player.currentState != Player.State.COMPLETE && player.currentState != Player.State.INTERACT) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3.6f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

                    if(level == 10) {
                        if (crumbpos != null) {
                            if (player.getX() == crumbpos.x + (5 / game.PPM)) {
                                crumbs.add(new Breadcrumbs(this, game));
                                Gdx.app.log("BreadCrumb", "Added");
                            }
                        }
                    }

                    game.loadSound("audio/sounds/soundnimja-jump.wav");
                    long id = game.sound.play();
                    if (game.getSoundVolume() != 0)
                        game.sound.setVolume(id, game.getSoundVolume());
                    else {
                        game.sound.setVolume(id, 0);
                    }

                    if (game.jumpCounter == 1) {
                        player.currentState = Player.State.DOUBLEJUMP;
                        Gdx.app.log("dJump", "Set");
                    }

                    if (game.jumpCounter == 2) {
                        game.doubleJumped = true;
                    }

                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.currentState == Player.State.DOUBLEJUMP)) && game.doubleJumped && player.currentState != Player.State.INTERACT) {
                    player.b2body.applyLinearImpulse(new Vector2(0f, 0f), player.b2body.getWorldCenter(), false);

                    if(level == 10) {
                        if(crumbpos != null) {
                            if (player.getY() == crumbpos.y + (5 / game.PPM)) {
                                crumbs.add(new Breadcrumbs(this, game));
                                Gdx.app.log("BreadCrumb", "Added");
                            }
                        }
                    }
                    Gdx.app.log("double", " jumped");
                }

//shoot / interact
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.currentState != Player.State.COMPLETE) {
                    if(!scannerJustTouched) {
                        bullets.add(new Bullets(game, this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                        if (!player.isRifle())
                            game.loadSound("audio/sounds/414888__matrixxx__retro_laser_shot_04(Pistol).wav");
                        else {
                            game.loadSound("audio/sounds/214990__peridactyloptrix__laser-blast-(Rifle).wav");
                        }

                        long id = game.sound.play();
                        if (game.getSoundVolume() != 0)
                            game.sound.setVolume(id, game.getSoundVolume());
                        else {
                            game.sound.setVolume(id, 0);
                        }
                    }

                    else if(scannerJustTouched && player.currentState != Player.State.INTERACT){
                        player.setScannerTouched(true);
                        creator.getScanner().interacted();
                    }

                }


                if(Gdx.input.isButtonJustPressed(Input.Keys.ENTER)&& player.currentState != Player.State.COMPLETE && player.currentState != Player.State.INTERACT){
                    player.setHealthCrate(true);
                }
//dash
                if (Gdx.input.isKeyJustPressed(Input.Keys.D) && player.currentState != Player.State.COMPLETE && player.currentState != Player.State.INTERACT) {
                    player.setDash(true);
                    player.dash();
                } else if (player.isDash() && player.currentState == Player.State.JUMPING) {
                    player.setDash(false);
                    player.b2body.applyLinearImpulse(new Vector2(0f, 0f), player.b2body.getWorldCenter(), false);
                }

//pause
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && player.currentState != Player.State.COMPLETE) {
                    game.setScreen(new PauseScreen(game));
                }

//move right
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.5 && player.currentState != Player.State.COMPLETE && player.currentState != Player.State.INTERACT) {
                    player.b2body.applyLinearImpulse(new Vector2(0.5f, 0), player.b2body.getWorldCenter(), true);

                    if(level == 10) {
                        if (crumbpos != null) {
                            if (player.getX() == crumbpos.x + (5 / game.PPM)) {
                                crumbs.add(new Breadcrumbs(this, game));
                                Gdx.app.log("BreadCrumb", "Added");
                            }
                        }
                    }
                }

//move left
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1.5 && player.currentState != Player.State.COMPLETE && player.currentState != Player.State.INTERACT) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.5f, 0), player.b2body.getWorldCenter(), true);
                    if(level == 10) {
                        if (crumbpos != null) {
                            if (player.getX() == crumbpos.x + (5 / game.PPM)) {
                                crumbs.add(new Breadcrumbs(this, game));
                                Gdx.app.log("BreadCrumb", "Added");
                            }
                        }
                    }
                }

            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }

//Android Phone
        else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (player.currentState != Player.State.DEAD) {
                if (controller.isUpPressed() && game.jumpCounter < 2 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3.8f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

                    game.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play(game.getSoundVolume());

                    if (game.jumpCounter == 2) {
                        game.doubleJumped = true;
                    }
                } else if ((controller.isUpPressed() && (player.currentState == Player.State.JUMPING)) && game.doubleJumped) {
                    player.b2body.applyLinearImpulse(new Vector2(0f, 0f), player.b2body.getWorldCenter(), false);
                    Gdx.app.log("double", " jumped");

                }


                if (controller.isDownPressed() && player.currentState != Player.State.COMPLETE) {
                    player.setDash(true);
                    player.dash();
                } else if (player.isDash() && player.currentState == Player.State.JUMPING) {
                    player.b2body.applyLinearImpulse(new Vector2(0f, 0f), player.b2body.getWorldCenter(), false);
                }


                if (controller.isSpacePressed() && player.currentState != Player.State.COMPLETE) {
                    if (!scannerJustTouched) {
                        bullets.add(new Bullets(game, this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                        if (!player.isRifle())
                            game.manager.get("audio/sounds/414888__matrixxx__retro_laser_shot_04(Pistol).wav", Sound.class).play(game.getSoundVolume());
                        else
                            game.manager.get("audio/sounds/214990__peridactyloptrix__laser-blast-(Rifle).wav", Sound.class).play(game.getSoundVolume());
                    } else if (scannerJustTouched) {
                        player.setScannerTouched(true);
                        creator.getScanner().interacted();
                    }
                }

                    if (controller.isRightPressed()  && player.b2body.getLinearVelocity().x <= 1.3 && player.currentState != Player.State.COMPLETE ) {
                        player.b2body.applyLinearImpulse(new Vector2(0.7f, 0f), player.b2body.getWorldCenter(), true);
                    }


                    if (controller.isLeftPressed()  && player.b2body.getLinearVelocity().x >= -1.3 && player.currentState != Player.State.COMPLETE) {
                        player.b2body.applyLinearImpulse(new Vector2(-0.7f, 0f), player.b2body.getWorldCenter(), true);
                    }
                } else {
                    player.b2body.setLinearVelocity(new Vector2(0f, 0f));
                }
            }
    }

    public void update(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(controller.stage);
        }

        handleInput(dt);
        handleSpawningItems();
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        if(level == 10) {
            // Handle breadcrumb spawning based on timer
            breadcrumbTimer += dt;
            if (breadcrumbTimer >= BREADCRUMB_INTERVAL) {
                breadcrumbTimer -= BREADCRUMB_INTERVAL; // Reset timer
                addBreadcrumb();
            }

            // Iterate through breadcrumbs to check for deletion
            Iterator<Breadcrumbs> iterator = crumbs.iterator();
            while(iterator.hasNext()) {
                Breadcrumbs breadcrumb = iterator.next();
                // If breadcrumb is marked for deletion (e.g., collision with boss)
                if(breadcrumb.isMarkedForDeletion()) {
                    breadcrumb.delete();
                    iterator.remove();
                    System.out.println("Breadcrumb deleted upon boss collision. Total breadcrumbs: " + crumbs.size());
                }
            }
        }


        scannerPostion = creator.getScanner().getScannerPos();


        for (Enemy enemy : creator.getWorkers()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 424 / shootForSurvival.PPM) {
                enemy.enemyBody.setActive(true);
            }
        }


        for (Enemy boss : creator.getScalpers()) {
            boss.update(dt);
            bossPos = new Vector2(boss.getX(), boss.getY());
            if (boss.getX() < player.getX() + 42 / shootForSurvival.PPM) {
                boss.enemyBody.setActive(true);
            }
        }

        for (Bullets bullet : bullets) {
            bullet.bulletBody.setActive(true);
            bullet.update(dt);
            bulletDamage = bullet.getDamage();
        }

        for (Item item : creator.getCoins()) {
            item.update(dt);
        }

        for (Item item : creator.getVials()) {
            for (int i = 0; i < creator.getVials().size; i++) {
                if (player.isHealthCrate()) {
                    player.setHealthCrate(false);
                }
            }
            item.update(dt);
        }

        for (Item item : creator.getKeys())
            item.update(dt);

        for (Item item : creator.getRifles())
            item.update(dt);


        creator.door.objectStateTimer += dt;

        if (creator.getScanner().isDestroyed()) {
            creator.door.unlock(game.statetimer);
            if (statetimer < 1.3f) {
                interact = creator.getScanner().isInteracted();
                game.setInteract(interact);
            } else
                interact = false;
            player.setScannerTouched(false);
        }


        hud.update(dt);
        game.setHud(hud);

        gamecam.update();

        if (player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y + 0.4f;
        }

        renderer.setView(gamecam);
        game.setMoney(coins);
        game.setStatetimer(player.getStateTimer());
    }

    private void addBreadcrumb() {
        Breadcrumbs breadcrumb = new Breadcrumbs(this, game);
        crumbs.add(breadcrumb);
        System.out.println("Added breadcrumb. Total breadcrumbs: " + crumbs.size());

        // Ensure maximum number of breadcrumbs is not exceeded
        if (crumbs.size() > MAX_BREADCRUMBS) {
            Breadcrumbs oldestBreadcrumb = crumbs.remove(0); // Remove the oldest breadcrumb
            oldestBreadcrumb.delete();
            System.out.println("Removed oldest breadcrumb to maintain max limit. Total breadcrumbs: " + crumbs.size());
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear Game Screen With Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game map
        renderer.render();

        //box2d debug lines
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            b2dr.render(world, gamecam.combined);
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        creator.door.draw(game.batch);


        player.draw(game.batch);

        for (Enemy enemy : creator.getWorkers())
            enemy.draw(game.batch);

        for (Enemy enemy2 : creator.getScalpers()) {
            enemy2.draw(game.batch);
        }

        for (Bullets bullet : bullets)
            if (!bullet.destroy) {
                bullet.render(game.batch);
            }

        for (Item item : items)
            item.draw(game.batch);

        if (creator.getCoins().notEmpty()) {
            for (Item item : creator.getCoins()) {
                item.draw(game.batch);
            }
        }

        if (creator.getVials().notEmpty()) {
            for (Item item : creator.getVials()) {
                item.draw(game.batch);
            }
        }

        if (creator.getKeys().notEmpty()) {
            for (Item item : creator.getKeys()) {
                item.draw(game.batch);
            }
        }

        if (creator.getRifles().notEmpty()) {
            for (Item item : creator.getRifles()) {
                item.draw(game.batch);
            }
        }

        game.batch.end();


        //Set to draw what hud sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.draw();
        }

        hud.stage.draw();
        hud.draw(game.batch, delta);


        if (gameOver()) {
            game.music.stop();
            game.setScreen(new GameOverScreen(game, area, level));
            dispose();
        }

        if (complete) {
            if (player.currentState == Player.State.COMPLETE && player.getStateTimer() > 1.2) {
                if (level < 10) {
                    game.setScreen(new LevelComplete(game, area, level));
                } else {
                    game.setScreen(new LevelSelect(game));
                }
                game.setPowerLVL(game.getPowerLVL());
                dispose();
            }
        }
    }

    public float getStatetimer() {
        return statetimer;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.resize(width, height);
        }
    }

    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 3) {
            coins = game.getStartMoney();
            game.setMoney(game.getStartMoney());
            System.out.println("gameover fail" + coins);
            return true;
        } else {
            return false;
        }

    }

    public void setScannerJustTouched(boolean scannerJustTouched) {
        this.scannerJustTouched = scannerJustTouched;
    }

    public void setLevelComplete(boolean level) {
        complete = level;
    }

    public boolean isInteract() {
        return interact;
    }

    public Breadcrumbs getBread() {
        return bread;
    }
    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
    }

    public int getArea() {
        return area;
    }

    public int getLevel() {
        return level;
    }

    public World getWorld() {
        return world;
    }

    public boolean isComplete() {
        return complete;
    }

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public shootForSurvival getGame() {
        return game;
    }

    public double getBulletDamage() {
        return bulletDamage;
    }

    public int getCoins() {
        return coins;
    }

    public void setMoney(int coins) {
        this.coins = coins;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();


        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr.dispose();

        hud.dispose();
        world.dispose();
        creator.dispose();
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            controller.dispose();

        System.gc();
    }
}


