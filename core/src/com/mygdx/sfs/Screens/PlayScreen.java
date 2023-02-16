package com.mygdx.sfs.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mygdx.sfs.Sprites.Items.health;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.Sprites.entities.Bullets;
import com.mygdx.sfs.Tools.B2WorldCreator;
import com.mygdx.sfs.Tools.Controller;
import com.mygdx.sfs.Tools.WorldContactListener;
import com.mygdx.sfs.shootForSurvival;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private shootForSurvival game;
    private TextureAtlas atlas;
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
    private B2WorldCreator creator;

    //Player variable
    private Player player;
    private float statetimer;
    private boolean shot = false;


    //Sprite Variable
    private Array<Item> items;
    public LinkedBlockingQueue<ItemDef> itemToSpawn;
    private int coins;

    //finish level variable
    public boolean complete = false;

    //level variable
    private int level = 1;

    //controller creation
    public Controller controller;


    public PlayScreen(shootForSurvival g, int level) {

        //admin
        atlas = new TextureAtlas("sprites/ryu_and_enemies.pack");

        this.game = g;
        this.level = level;
        this.manager = game.getManager();
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(shootForSurvival.V_WIDTH / shootForSurvival.PPM, shootForSurvival.V_HEIGHT / shootForSurvival.PPM, gamecam);
        hud = new Hud(game.batch, game,game.getScreen(),this);
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            controller = new Controller(game);
        }


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("levels/Level"+level+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / shootForSurvival.PPM);

        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(game,this);

        //Player creation
        player = new Player(this,game);

        player.setBullet(new Bullets(game,this,player.getX(),player.getY()));
        world.setContactListener(new WorldContactListener());

        game.loadMusic("audio/music/yoitrax - Fuji.mp3");
        if(game.getVolume() != 0) {
            game.music.play();
            game.music.setVolume(game.getVolume());
        }
        game.setSoundVolume(game.getSoundVolume());


        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
        coins = 0;
    }

    public void spawnItem(ItemDef idef) {
        itemToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDef idef = itemToSpawn.poll();
            if (idef.type == health.class) {
                items.add(new health(game,this, idef.position.x, idef.position.y));
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Viewport getGamePort() {
        return gamePort;
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

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (player.currentState != Player.State.DEAD) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && game.jumpCounter < 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

                    game.loadSound("audio/sounds/soundnimja-jump.wav");
                    long id = game.sound.play();
                    if(game.getSoundVolume() != 0)
                    game.sound.setVolume(id, game.getSoundVolume());
                    else{
                        game.sound.setVolume(id,0);
                    }

                    if(game.jumpCounter == 2){
                        game.doubleJumped = true;
                    }
                }else if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.currentState == Player.State.JUMPING)) && game.doubleJumped == true) {
                    player.b2body.applyLinearImpulse(new Vector2(0f,0f),player.b2body.getWorldCenter(), false);
                    Gdx.app.log("double"," jumped");
                }
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    player.attack();
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                    player.shoot();
                    shot = true;
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                    player.dash();
                }

                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    game.setScreen(new PauseScreen(game));
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.5) {
                    player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);
                }

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1.5) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            if (player.currentState != Player.State.DEAD) {
                if (controller.isUpPressed() && game.jumpCounter < 1) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
                    game.jumpCounter++;

                    game.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play(game.getSoundVolume());


                    if(game.jumpCounter == 2){
                        game.doubleJumped = true;
                    }
                }else if ((controller.isUpPressed() == true && (player.currentState == Player.State.JUMPING)) && game.doubleJumped == true) {
                    player.b2body.applyLinearImpulse(new Vector2(0f,0f),player.b2body.getWorldCenter(), false);
                    Gdx.app.log("double"," jumped");
                }
                if (controller.isDownPressed() == true) {
                    player.attack();
                }

                if (controller.isRightPressed() == true && player.b2body.getLinearVelocity().x <= 1.3) {
                    player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);
                }

                if (controller.isLeftPressed() == true && player.b2body.getLinearVelocity().x >= -1.3) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }
    }

    public shootForSurvival getGame() {
        return game;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void update(float dt) {
        if( Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(controller.stage);
        }

        handleInput(dt);
        handleSpawningItems();

        world.step(1 / 60f, 6, 2);


        player.update(dt);
        if(shot == true)
            player.getBullet().update(dt);
        for (Enemy enemy : creator.getNinjas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / shootForSurvival.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : creator.getCoins())
            item.update(dt);

        for (Item item : creator.getVials())
            item.update(dt);

        hud.update(dt);
        game.setHud(hud);


        if (player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
        game.setCoins(coins);
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
        for (Enemy enemy : creator.getNinjas())
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

        game.batch.end();

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
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 3) {
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
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.dispose();
        }
    }
}
