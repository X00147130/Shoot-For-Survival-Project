package com.mygdx.sfs.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

        /*Label timeLabel;*/

        //health bar
        private ShapeRenderer border;
        private ShapeRenderer background;
        private ShapeRenderer health;
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

            walletAmount = playScreen.getCoins();

            keys = playScreen.getKeys();

            viewport = new FitViewport(shootForSurvival.V_WIDTH,shootForSurvival.V_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, sb);

            Table table = new Table();
            table.top();
            table.setFillParent(true);

            Table table2 = new Table();
            table2.top();
            table2.setFillParent(true);


            walletLabel = new Label(String.format("%01d", walletAmount), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.valueOf("ff0a7f")));
            wallet = new Label("MONEY:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.CYAN));

            healthLabel = new Label("HEALTH:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.GREEN));

            keyAcquired = new Label(String.format("%01d", keys), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.valueOf("ff0a7f")));
            key = new Label("KEYS:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), Color.CYAN));


            //group for health label scaling

            table.add(healthLabel).expandX().left().padLeft(5).top();
            table.add(wallet).padRight(10).right().top();
            table.add(walletLabel).padRight(10).right().top().spaceRight(11);
            table.row();
            table.row();


            table2.add(key).right();
            table2.add(keyAcquired).padRight(30).right();

            stage.addActor(table);
            stage.addActor(table2);



            // health bar initialisation
            border = new ShapeRenderer();
            background = new ShapeRenderer();
            health = new ShapeRenderer();
            projectionMatrixSet = false;



        }

        public void update(float dt) {
            walletAmount = playScreen.getCoins();
            walletLabel.setText(String.format("%01d", walletAmount));

            keys = playScreen.getKeys();
            keyAcquired.setText(String.format("%01d", keys));
        }


        public void draw(SpriteBatch batch, float alpha){
            if(!projectionMatrixSet){
                border.setProjectionMatrix(batch.getProjectionMatrix());
                health.setProjectionMatrix(batch.getProjectionMatrix());
                background.setProjectionMatrix(batch.getProjectionMatrix());
            }
            border.begin(ShapeRenderer.ShapeType.Filled);
            border.setColor(Color.WHITE);
            border.rect(4,184,101,8);
            border.end();

            background.begin(ShapeRenderer.ShapeType.Filled);
            background.setColor(Color.BLACK);
            background.rect(5, 185, 99, 6);
            background.end();

            health.begin(ShapeRenderer.ShapeType.Filled);
            if(Player.getHitCounter() == 0) {
                health.rect(5, 185, 99, 6);
                health.setColor(Color.CYAN);
            }
            else if (Player.getHitCounter() == 1){
                health.rect(5,185,66,6);
                health.setColor(Color.YELLOW);
            }
            else if (Player.getHitCounter() == 2){
                health.rect(5,185,33,6);
                health.setColor(Color.RED);
            }
            else if (Player.getHitCounter() == 3){
                health.rect(5,185,0,6);
            }
            health.end();



        }
        public Screen getPlayScreen(){
            return play;
        }


        @Override
        public void dispose() {
            stage.dispose();
        }
    }

