package com.mygdx.sfs.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Hud implements Disposable {

        public Stage stage;
        private Viewport viewport;

        private int walletAmount;
        private Label walletLabel;

        private int keys;
        private Label keyAcquired;



        //health bar
        private Texture healthbar;

        //Labels
        Label healthLabel;
        Label wallet;
        Label key;

        static private boolean projectionMatrixSet;


        private shootForSurvival gameplay;
        public final Screen play;
        private PlayScreen playScreen;


        public Hud(SpriteBatch sb, final shootForSurvival game, final Screen paused, final PlayScreen playScreen){
            gameplay = game;
            play = paused;

            this.playScreen = playScreen;

            walletAmount = game.getMoney();

            keys = playScreen.getKeys();

            viewport = new FitViewport(shootForSurvival.V_WIDTH,shootForSurvival.V_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, sb);

            Table table = new Table();
            table.top();
            table.setFillParent(true);

            Table table2 = new Table();
            table2.top();
            table2.setFillParent(true);

            walletLabel = new Label(String.format("%01d", walletAmount), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.valueOf("ff0a7f")));
            walletLabel.setFontScale(0.6f,0.5f);
            wallet = new Label("MONEY:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.CYAN));
            wallet.setFontScale(0.6f,0.5f);

            healthLabel = new Label("HEALTH:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.GREEN));
            healthLabel.setFontScale(0.6f,0.4f);

            keyAcquired = new Label(String.format("%01d", keys), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.valueOf("ff0a7f")));
            keyAcquired.setFontScale(0.4f,0.5f);
            key = new Label("KEYS:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), Color.CYAN));
            key.setFontScale(0.4f,0.5f);


            //group for health label scaling

            table.add(healthLabel).expandX().left().padLeft(5).top().padTop(2);
            table.add(wallet).right().top();
            table.add(walletLabel).padRight(2).right().padLeft(20).top();

            table.row();
            table.row();


            table2.add(key).spaceLeft(20).padLeft(20).right();
            table2.add(keyAcquired).padRight(63).right();

            stage.addActor(table);
            stage.addActor(table2);



            // health bar initialisation
            healthbar = new Texture("sprites/HUD/fullHealth.png");


        }

        public void update(float dt) {
            walletAmount = gameplay.getMoney();
            walletLabel.setText(String.format("%01d", walletAmount));

            keys = playScreen.getKeys();
            keyAcquired.setText(String.format("%01d", keys));

            if(Player.getHitCounter() == 0)
                healthbar = new Texture("sprites/HUD/fullHealth.png");

            else if(Player.getHitCounter() == 1)
                healthbar = new Texture("sprites/HUD/1hit.png");

            else if(Player.getHitCounter() == 2)
                healthbar = new Texture("sprites/HUD/2hits.png");

            else if(Player.getHitCounter() == 3)
                healthbar = new Texture("sprites/HUD/noHealth.png");
        }


        public void draw(SpriteBatch batch, float alpha){
            batch.begin();
            batch.draw(healthbar,5,185,101,8);
            batch.end();
        }
        public Screen getPlayScreen(){
            return play;
        }


        @Override
        public void dispose() {
            stage.dispose();
        }
    }

