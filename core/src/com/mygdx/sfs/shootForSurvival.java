package com.mygdx.sfs;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Scenes.Hud;
import com.mygdx.sfs.Scenes.Screens.LogoScreen;

public class shootForSurvival extends Game {
    //constants
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final int MAP_WIDTH = 900;
    public static final int MAP_HEIGHT = 508;
    public static final float PPM = 150;
    public static final float MAX_VOL = 100;
    public static final float MIN_VOL = 0;

    //Filter initializations
    public static final short BOSS_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short SCREEN_BIT = 4;
    public static final short FINISH_BIT = 8;
    public static final short BARRIER_BIT = 16;
    public static final short ENEMY_BIT = 32;
    public static final short ITEM_BIT = 64;
    public static final short PLAYER_HEAD_BIT = 128;
    public static final short BULLET_BIT = 256;
    public static final short MONEY_BIT = 512;
    public static final short SKY_BIT = 1024;
    public static final short KEY_BIT = 2048;
    public static final short DEATH_BIT = 4096;


    //variables
    public SpriteBatch batch;
    public World world;
    public float volume = 0.5f;
    public float soundVolume = 0.5f;
    public boolean mutedM = false;
    public boolean mutedS = false;
    public Music music;
    public Sound sound;
    private int money = 0;
    private Hud hud;
    public int jumpCounter = 0;
    public int justTouched = 0;
    public boolean doubleJumped = false;
    public float statetimer = 0;
    public boolean musicIsChecked = false;
    public boolean soundIsChecked = false;

    private TextureAtlas punkAtlas;
    private TextureAtlas bikerAtlas;
    private TextureAtlas cyborgAtlas;


    private TextureAtlas bikerRifle1;
    private TextureAtlas punkRifle1;
    private TextureAtlas cyborgRifle1;

    private TextureAtlas pistolBullets;
    private TextureAtlas rifleBullets;

    private TextureAtlas worker1Atlas;

    private TextureAtlas doorAtlas;
    private TextureAtlas keycardAtlas;
    private TextureAtlas moneyAtlas;
    private TextureAtlas healthAtlas;
    private TextureAtlas screenAtlas;

    public TextureAtlas playersChoice;

    public static AssetManager manager;

    public Hud getHud() {
        return hud;
    }
    public void setHud(Hud hud) {
        this.hud = hud;
    }




    @Override
    public void create() {
        batch = new SpriteBatch();

        manager = new AssetManager();

        playersChoice = new TextureAtlas();

        /*Sound Loading*/
        manager.load("audio/sounds/coin.mp3", Sound.class);
        manager.load("audio/sounds/getting-hit.mp3", Sound.class);
        manager.load("audio/sounds/health drink.mp3", Sound.class);
        manager.load("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class);
        manager.load("audio/sounds/mixkit-fast-sword-whoosh-2792.wav", Sound.class);
        manager.load("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class);
        manager.load("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class);
        manager.load("audio/sounds/soundnimja-jump.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/death.wav", Sound.class);
        manager.load("audio/sounds/pistol shot.mp3", Sound.class);



        /*Music Loading*/
        manager.load("audio/music/yoitrax - Fuji.mp3", Music.class);
        manager.load("audio/music/yoitrax - Jade Dragon.mp3", Music.class);
        manager.load("audio/music/yoitrax - Diamonds.mp3", Music.class);
        manager.load("audio/music/jantrax - ai.mp3", Music.class);

        /*Texture Loading*/
        manager.load("backgrounds/menubg.png", Texture.class); // main menu
        manager.load("backgrounds/Background.png",Texture.class); //Character select background
        manager.load("backgrounds/lvlselectbg.png", Texture.class); // level select
        manager.load("backgrounds/lvlcompletebg.png", Texture.class); // level complete
        manager.load("backgrounds/settingsbg.png", Texture.class); // settings
        manager.load("backgrounds/controls.png", Texture.class); // controls Background
        manager.load("backgrounds/pausebg.png", Texture.class); // pause
        manager.load("backgrounds/deadbg.png", Texture.class); // game over
        manager.load("sprites/bullet.png", Texture.class); // bullet

        punkAtlas = new TextureAtlas("sprites/Characters/punkPistol1.pack");//Punk Character
        bikerAtlas = new TextureAtlas("sprites/Characters/bikerPistol1.pack");//Biker Character
        cyborgAtlas = new TextureAtlas("sprites/Characters/cyborgPistol1.pack");//Cyborg Character

        bikerRifle1 = new TextureAtlas("sprites/Characters/bikerRifle1.pack");//Biker Character with rifle 1
        punkRifle1 = new TextureAtlas("sprites/Characters/punkRifle1.pack");//Punk Character with rifle 1
        cyborgRifle1 = new TextureAtlas("sprites/Characters/cyborgRifle1.pack");//Cyborg Character with rifle 1

        pistolBullets = new TextureAtlas("sprites/Bullets/pistolBullets.pack"); //Pistol bullets
        /*rifleBullets = new TextureAtlas("sprites/Bullets/bullets.pack"); //Rifle bullets*/


        worker1Atlas = new TextureAtlas("sprites/Enemies/worker1.pack");//Worker1 (Enemy) Character

        doorAtlas = new TextureAtlas("sprites/Objects/door.pack"); //End Level Door
        keycardAtlas = new TextureAtlas("sprites/Objects/keycard.pack"); //Key Card
        healthAtlas = new TextureAtlas("sprites/Objects/HealthCrate.pack"); //Health Crate
        moneyAtlas = new TextureAtlas("sprites/Objects/money.pack"); //Money
        screenAtlas = new TextureAtlas("sprites/Objects/screen.pack"); //Screen At Door


        manager.finishLoading();
        setScreen(new LogoScreen(this));
    }

    public static AssetManager getManager() {
        return manager;
    }

    public void loadMusic(String path) {
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public void loadSound(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setStatetimer(float statetimer) {
        this.statetimer = statetimer;
    }

    public void setMutedM(boolean muted){
        mutedM = muted;
    }

    public void setMutedS(boolean mute){
        mutedS = mute;
    }

    public void setMusicIsChecked(boolean musicIsChecked) {
        this.musicIsChecked = musicIsChecked;
    }

    public void setSoundIsChecked(boolean soundIsChecked) {
        this.soundIsChecked = soundIsChecked;
    }

    public boolean isMusicIsChecked() {
        return musicIsChecked;
    }

    public boolean isSoundIsChecked() {
        return soundIsChecked;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TextureAtlas getPunkAtlas() {
        return punkAtlas;
    }
    public TextureAtlas getBikerAtlas() {
        return bikerAtlas;
    }
    public TextureAtlas getCyborgAtlas() {
        return cyborgAtlas;
    }
    public TextureAtlas getPlayersChoice() {
        return playersChoice;
    }

    public TextureAtlas getbikerRifle1() {
        return bikerRifle1;
    }
    public TextureAtlas getpunkRifle1() {
        return punkRifle1;
    }
    public TextureAtlas getcyborgRifle1() {
        return cyborgRifle1;
    }

    public TextureAtlas getPistolBullets() {
        return pistolBullets;
    }
    public TextureAtlas getRifleBullets() {
        return rifleBullets;
    }

    public TextureAtlas getWorker1Atlas() {
        return worker1Atlas;
    }


    public TextureAtlas getDoorAtlas() {
        return doorAtlas;
    }
    public TextureAtlas getKeycardAtlas() {
        return keycardAtlas;
    }
    public TextureAtlas getMoneyAtlas() {
        return moneyAtlas;
    }
    public TextureAtlas getHealthAtlas() {
        return healthAtlas;
    }


    public void setPlayersChoice(TextureAtlas playersChoice) {
        this.playersChoice = playersChoice;
    }
}

