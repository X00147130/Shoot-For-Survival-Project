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
import com.mygdx.sfs.Sprites.Enemies.Enemy;
import com.mygdx.sfs.Sprites.Items.Item;
import com.mygdx.sfs.Sprites.Items.ItemDef;
import com.mygdx.sfs.Sprites.Items.KeyCard;
import com.mygdx.sfs.Sprites.Items.HealthCrate;
import com.mygdx.sfs.Sprites.Items.Rifles;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.Sprites.Items.Bullets;
import com.mygdx.sfs.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.sfs.Tools.B2WorldCreator;
import com.mygdx.sfs.Tools.Controller;
import com.mygdx.sfs.Tools.WorldContactListener;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private shootForSurvival game;
    public AssetManager manager;

    //basic variables
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //tiled map variables
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
    private boolean shot = false;

    //Bullet Variable
    private ArrayList<Bullets> bullets;

    //Sprite Variable
    private Array<Item> items;
    public LinkedBlockingQueue<ItemDef> itemToSpawn;
    private int coins;
    private int keys;

    //finish level variable
    public boolean complete = false;

    //level variable
    private int level = 1;

    //controller creation
    public Controller controller;


    public PlayScreen(shootForSurvival g, int level) {

        //game management inits
        this.game = g;
        this.level = level;
        this.manager = game.getManager();

        //view of the game
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(shootForSurvival.MAP_WIDTH / shootForSurvival.PPM, shootForSurvival.MAP_HEIGHT / shootForSurvival.PPM, gamecam);
        hud = new Hud(game.batch, game,game.getScreen(),this);

        //controller
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            controller = new Controller(game);
        }

        //bullet init
        bullets = new ArrayList<Bullets>();

        //render/map setup
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Maps/Industry/Map/Lvl1-1.tmx");
        //map = mapLoader.load("levels/Level"+level+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / shootForSurvival.PPM);

        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 1.5f, gamePort.getWorldHeight() / 1.5f, 0);

        world = new World(new Vector2(0, -10), true);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(game,this);

        //Player creation
        player = new Player(this,game);
        world.setContactListener(new WorldContactListener());

        game.loadMusic("audio/music/yoitrax - Diamonds.mp3");
        if(game.getVolume() != 0) {
            game.music.play();
            game.music.setVolume(game.getVolume());
        }
        game.setSoundVolume(game.getSoundVolume());


        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
        coins = 0;
        game.setWorld(world);
    }

    public void spawnItem(ItemDef idef) {
        itemToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDef idef = itemToSpawn.poll();
            if (idef.type == HealthCrate.class) {
                items.add(new HealthCrate(game,this, idef.position.x, idef.position.y));
            }
            if(idef.type == KeyCard.class){
                items.add(new KeyCard(game,this,idef.position.x, idef.position.y));
            }
            if(idef.type == Rifles.class){
                items.add(new Rifles(game,this,idef.position.x, idef.position.y));
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
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

    public void handleInput(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (player.currentState != Player.State.DEAD) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && game.jumpCounter < 2 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3.6f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

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

                }else if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.currentState == Player.State.DOUBLEJUMP)) && game.doubleJumped == true) {
                        player.b2body.applyLinearImpulse(new Vector2(0f, 0f), player.b2body.getWorldCenter(), false);
                        Gdx.app.log("double", " jumped");
                }


                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.currentState != Player.State.COMPLETE) {
                        bullets.add(new Bullets(game, this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                   if(player.isRifle() == false) {
                       game.loadSound("audio/sounds/pistol shot.mp3");
                   }
                   else{
                       game.loadSound("audio/sounds/rifle shot.mp3");
                   }
                    long id = game.sound.play();
                    if (game.getSoundVolume() != 0)
                        game.sound.setVolume(id, game.getSoundVolume());
                    else {
                        game.sound.setVolume(id, 0);
                    }
                }


                if(Gdx.input.isKeyJustPressed(Input.Keys.D) && player.currentState != Player.State.COMPLETE){
                    player.setDash(true);
                    player.dash();
                }else if(player.isDash() == true && player.currentState == Player.State.JUMPING){
                    player.setDash(false);
                    player.b2body.applyLinearImpulse(new Vector2(0f,0f),player.b2body.getWorldCenter(), false);
                }


                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)&& player.currentState != Player.State.COMPLETE) {
                    game.setScreen(new PauseScreen(game));
                    }


                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.5 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(0.5f, 0), player.b2body.getWorldCenter(), true);
                }


                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1.5 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.5f, 0), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }




        }
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            if (player.currentState != Player.State.DEAD) {
                if (controller.isUpPressed() && game.jumpCounter < 2 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3.8f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

                    game.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play(game.getSoundVolume());

                    if(game.jumpCounter == 2){
                        game.doubleJumped = true;
                    }
                }else if ((controller.isUpPressed() == true && (player.currentState == Player.State.JUMPING)) && game.doubleJumped == true) {
                    player.b2body.applyLinearImpulse(new Vector2(0f,0f),player.b2body.getWorldCenter(), false);
                    Gdx.app.log("double"," jumped");

                }


                if(controller.isDownPressed() == true && player.currentState != Player.State.COMPLETE){
                    player.setDash(true);
                    player.dash();
                }else if(player.isDash() == true && player.currentState == Player.State.JUMPING){
                    player.b2body.applyLinearImpulse(new Vector2(0f,0f),player.b2body.getWorldCenter(), false);
                }


                if (controller.isSpacePressed() == true && player.currentState != Player.State.COMPLETE) {
                    bullets.add(new Bullets(game,this,player.b2body.getPosition().x,player.b2body.getPosition().y));
                    if(player.isRifle() == false)
                        game.manager.get("audio/sounds/pistol shot.mp3", Sound.class).play(game.getSoundVolume());
                    else
                        game.manager.get("audio/sounds/rifle shot.mp3", Sound.class).play(game.getSoundVolume());
                }


                if (controller.isRightPressed() == true && player.b2body.getLinearVelocity().x <= 1.3 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(0.7f, 0f), player.b2body.getWorldCenter(), true);
                }


                if (controller.isLeftPressed() == true && player.b2body.getLinearVelocity().x >= -1.3 && player.currentState != Player.State.COMPLETE) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.7f, 0f), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0f, 0f));
            }
        }
    }

    public void update(float dt) {
        if( Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(controller.stage);
        }

        handleInput(dt);
        handleSpawningItems();

        world.step(1 / 60f, 6, 2);


        player.update(dt);


        for (Enemy enemy : creator.getWorkers()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 424 / shootForSurvival.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : creator.getCoins())
            item.update(dt);

        for (Item item : creator.getVials())
            item.update(dt);

        for (Item item : creator.getKeys())
            item.update(dt);

        for (Item item : creator.getRifles())
            item.update(dt);

        creator.getScanner().destroyBody();

        if(creator.getScanner().isDestroyed() == true){
            creator.door.draw();
        }


        hud.update(dt);
        game.setHud(hud);

        for(Bullets bullet: bullets){
            bullet.update(dt);
            bullet.bulletBody.setActive(true);
        }

        if (player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y + 0.4f;
        }

        gamecam.update();
        renderer.setView(gamecam);
        game.setMoney(coins);
        game.setStatetimer(player.getStateTimer());
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
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);


        game.batch.begin();
        player.draw(game.batch);



        for (Enemy enemy : creator.getWorkers())
            enemy.draw(game.batch);

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

        creator.door.draw();
        for(Bullets bullet: bullets)
            bullet.render(game.batch);


        //Set to draw what hud sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.draw();
        }

        hud.stage.draw();
        hud.draw(game.batch, delta);


        if (gameOver()) {
            game.music.stop();
            game.setScreen(new GameOverScreen(game, level));
            dispose();
        }

        if (complete == true) {
            if (player.currentState == Player.State.COMPLETE && player.getStateTimer() > 1.5) {
                if(level < 10){
                    game.setScreen(new LevelComplete(game, level));
                }else{
                    game.setScreen(new Credits(game));
                }
                game.setPowerLVL(0);
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
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            controller.resize(width, height);
        }
    }

    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 4) {
            return true;
        }else {
                return false;
            }

    }


    public void setLevelComplete(boolean level) {
        complete = level;
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


    public void coins (){
        if(creator.getCoins().size > 0){
            coins = creator.getCoins().size;
        }
    }
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr.dispose();

        hud.dispose();
        world.dispose();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.dispose();
    }
}
