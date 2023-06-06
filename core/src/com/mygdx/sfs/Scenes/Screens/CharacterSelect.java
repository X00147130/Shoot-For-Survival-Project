package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.MAGENTA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.sfs.shootForSurvival;

import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private shootForSurvival sfs;
    private Viewport viewport;
    private Animation preview;
    private Texture background;

    /* Arrays To Loop through for Selection */
    private ArrayList<TextureAtlas.AtlasRegion> characterSprites;
    private ArrayList<Label> characterNames;
    private int i = 0;

    //Selection, Labels and buttons
    private TextureAtlas selected;
    private TextureAtlas selectedRifle;
    private TextureAtlas selectedRifle2;
    private TextureAtlas selectedRifle3;
    private TextureAtlas selectedRifle4;
    private TextureAtlas selectedRifle5;
    private TextureAtlas selectedRifle6;
    private TextureAtlas selectedRifle7;
    private TextureAtlas selectedRifle8;
    private TextureAtlas selectedRifle9;
    private TextureAtlas selectedRifle10;


    private Image left, right;
    private TextButton choose;
    private Table table;
    private Stage stage;
    private TextButton.TextButtonStyle textStyle;
    private BitmapFont buttonFont;
    private Label punkLabel;
    private Label bikerLabel;
    private Label cyborgLabel;
    private Label arrows;
    private Label enter;




    public CharacterSelect(shootForSurvival game) {
        this.sfs = game;
        viewport = new FitViewport(sfs.V_WIDTH, sfs.V_HEIGHT, new OrthographicCamera());

        background = sfs.manager.get("backgrounds/Background.png", Texture.class);

        /*initialising and instantiating of animatimation arrays*/
        characterSprites = new ArrayList<TextureAtlas.AtlasRegion>(3);

        characterSprites.add(sfs.getBikerAtlas().findRegion("idle1"));

        characterSprites.add(sfs.getPunkAtlas().findRegion("idle1"));

        characterSprites.add(sfs.getCyborgAtlas().findRegion("idle1"));


        //Setup of Screen
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), MAGENTA);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt")), MAGENTA);

        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = MAGENTA;

        arrows = new Label("Arrows To Swap Character",font2);
        enter = new Label("Enter to Select",font2);

        /*Intiatialising and Names of characters added to the arraylist*/
        characterNames = new ArrayList<Label>(3);


        bikerLabel = new Label("Clyde", font);
        punkLabel = new Label("Chad", font);
        cyborgLabel = new Label("X01F", font);


        characterNames.add(0,bikerLabel);
        characterNames.add(1,punkLabel);
        characterNames.add(2,cyborgLabel);


        Texture button1 = new Texture("controller/rightBtn.png");
        Texture button2 = new Texture("controller/leftBtn.png");


        right = new Image(button1);
        right.setSize(3 / sfs.PPM, 3 / sfs.PPM);


        left = new Image(button2);
        left.setSize(3 / sfs.PPM, 3 / sfs.PPM);


        choose = new TextButton("Select", textStyle);


        if (Gdx.app.getType() == Application.ApplicationType.Android) {

            stage = new Stage(viewport, sfs.batch);
            table = new Table();
            table.setFillParent(true);
            table.center();


            table.add(left).left().size(button2.getWidth() / 10, button2.getHeight() / 10).padRight(60).padTop(20);
            table.add(right).right().size(button1.getWidth() / 10, button1.getHeight() / 10).padLeft(60).padTop(20);
            table.row();
            table.row();
            table.add(choose).center().padLeft(130).padTop(30);

            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);

            right.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 1;
                        characterNames.get(i);

                        characterSprites.get(i);

                    } else if (i == 1) {
                        i = 2;
                        characterNames.get(i);

                        characterSprites.get(i);

                    } else if (i == 2) {
                        i = 0;
                        characterNames.get(i);

                        characterSprites.get(i);
                    }
                }
            });


            left.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 2;
                        characterSprites.get(i);

                        characterNames.get(i);

                    } else if (i == 1) {
                        i = 0;
                        characterSprites.get(i);

                        characterNames.get(i);
                    } else if (i == 2) {
                        i = 1;
                        characterSprites.get(i);

                        characterNames.get(i);
                    }
                }
            });


            choose.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    switch (i) {
                        case 0:
                            selected = sfs.getBikerAtlas();
                            selectedRifle = sfs.getBikerRifle1();
                            selectedRifle2 = sfs.getBikerRifle2();
                            selectedRifle3 = sfs.getBikerRifle3();
                            selectedRifle4 = sfs.getBikerRifle4();
                            selectedRifle5 = sfs.getBikerRifle5();
                            selectedRifle6 = sfs.getBikerRifle6();
                            selectedRifle7 = sfs.getBikerRifle7();
                            selectedRifle8 = sfs.getBikerRifle8();
                            selectedRifle9 = sfs.getBikerRifle9();
                            selectedRifle10 = sfs.getBikerRifle10();
                            break;

                        case 1:
                            selected = sfs.getPunkAtlas();
                            selectedRifle = sfs.getPunkRifle1();
                            selectedRifle2 = sfs.getPunkRifle2();
                            selectedRifle3 = sfs.getPunkRifle3();
                            selectedRifle4 = sfs.getPunkRifle4();
                            selectedRifle5 = sfs.getPunkRifle5();
                            selectedRifle6 = sfs.getPunkRifle6();
                            selectedRifle7 = sfs.getPunkRifle7();
                            selectedRifle8 = sfs.getPunkRifle8();
                            selectedRifle9 = sfs.getPunkRifle9();
                            selectedRifle10 = sfs.getPunkRifle10();
                            break;

                        case 2:
                            selected = sfs.getCyborgAtlas();
                            selectedRifle = sfs.getCyborgRifle1();
                            selectedRifle2 = sfs.getCyborgRifle2();
                            selectedRifle3 = sfs.getCyborgRifle3();
                            selectedRifle4 = sfs.getCyborgRifle4();
                            selectedRifle5 = sfs.getCyborgRifle5();
                            selectedRifle6 = sfs.getCyborgRifle6();
                            selectedRifle7 = sfs.getCyborgRifle7();
                            selectedRifle8 = sfs.getCyborgRifle8();
                            selectedRifle9 = sfs.getCyborgRifle9();
                            selectedRifle10 = sfs.getCyborgRifle10();
                            break;

                        default:
                    }
                    sfs.setPlayersChoice(selected);
                    sfs.setRifleChoice(selectedRifle);
                    sfs.setRifleChoice2(selectedRifle2);
                    sfs.setRifleChoice3(selectedRifle3);
                    sfs.setRifleChoice4(selectedRifle4);
                    sfs.setRifleChoice5(selectedRifle5);
                    sfs.setRifleChoice6(selectedRifle6);
                    sfs.setRifleChoice7(selectedRifle7);
                    sfs.setRifleChoice8(selectedRifle8);
                    sfs.setRifleChoice9(selectedRifle9);
                    sfs.setRifleChoice10(selectedRifle10);

                    sfs.setScreen(new PlayScreen(sfs, 1));
                }
            });
        }

    }
        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            update(delta);




        sfs.batch.begin();

        sfs.batch.draw(background,0,0,500,200);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            arrows.setBounds(20, 155, 70, 70);
            arrows.draw(sfs.batch,1);

            enter.setBounds(95, 5, 70, 70);
            enter.draw(sfs.batch,1);
        }

        sfs.batch.draw(characterSprites.get(i),185,105);

        characterNames.get(0).setBounds(115, 55, 70, 70);
        characterNames.get(1).setBounds(135, 55, 70, 70);
        characterNames.get(2).setBounds(135, 55, 70, 70);
        characterNames.get(i).draw(sfs.batch,1);

        sfs.batch.end();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            stage.draw();
        }

    public int getI() {
        return i;
    }

    public void handleInput(float dt){
        /*Move Left*/
        if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if (i == 0) {
                    i = 2;
                    characterSprites.get(i);

                    characterNames.get(i);

                } else if (i == 1) {
                    i = 0;
                    characterSprites.get(i);

                    characterNames.get(i);
                } else if (i == 2) {
                    i = 1;
                    characterSprites.get(i);

                    characterNames.get(i);
                }
            }

            /*Move Right*/
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                if (i == 0) {
                    i = 1;
                    characterSprites.get(i);

                    characterNames.get(i);

                } else if (i == 1) {
                    i = 2;
                    characterSprites.get(i);

                    characterNames.get(i);
                } else if (i == 2) {
                    i = 0;
                    characterSprites.get(i);

                    characterNames.get(i);
                }
            }

            /*Selection*/
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    sfs.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = sfs.sound.play();
                    if (sfs.getSoundVolume() != 0)
                        sfs.sound.setVolume(id, sfs.getSoundVolume());
                    else {
                        sfs.sound.setVolume(id, 0);
                    }
                }
                switch (i) {
                    case 0:
                        selected = sfs.getBikerAtlas();
                        selectedRifle = sfs.getBikerRifle1();
                        selectedRifle2 = sfs.getBikerRifle2();
                        selectedRifle3 = sfs.getBikerRifle3();
                        selectedRifle4 = sfs.getBikerRifle4();
                        selectedRifle5 = sfs.getBikerRifle5();
                        selectedRifle6 = sfs.getBikerRifle6();
                        selectedRifle7 = sfs.getBikerRifle7();
                        selectedRifle8 = sfs.getBikerRifle8();
                        selectedRifle9 = sfs.getBikerRifle9();
                        selectedRifle10 = sfs.getBikerRifle10();
                        break;

                    case 1:
                        selected = sfs.getPunkAtlas();
                        selectedRifle = sfs.getPunkRifle1();
                        selectedRifle2 = sfs.getPunkRifle2();
                        selectedRifle3 = sfs.getPunkRifle3();
                        selectedRifle4 = sfs.getPunkRifle4();
                        selectedRifle5 = sfs.getPunkRifle5();
                        selectedRifle6 = sfs.getPunkRifle6();
                        selectedRifle7 = sfs.getPunkRifle7();
                        selectedRifle8 = sfs.getPunkRifle8();
                        selectedRifle9 = sfs.getPunkRifle9();
                        selectedRifle10 = sfs.getPunkRifle10();
                        break;

                    case 2:
                        selected = sfs.getCyborgAtlas();
                        selectedRifle = sfs.getCyborgRifle1();
                        selectedRifle2 = sfs.getCyborgRifle2();
                        selectedRifle3 = sfs.getCyborgRifle3();
                        selectedRifle4 = sfs.getCyborgRifle4();
                        selectedRifle5 = sfs.getCyborgRifle5();
                        selectedRifle6 = sfs.getCyborgRifle6();
                        selectedRifle7 = sfs.getCyborgRifle7();
                        selectedRifle8 = sfs.getCyborgRifle8();
                        selectedRifle9 = sfs.getCyborgRifle9();
                        selectedRifle10 = sfs.getCyborgRifle10();
                        break;

                    default:
                }
                sfs.setPlayersChoice(selected);
                sfs.setRifleChoice(selectedRifle);
                sfs.setRifleChoice2(selectedRifle2);
                sfs.setRifleChoice3(selectedRifle3);
                sfs.setRifleChoice4(selectedRifle4);
                sfs.setRifleChoice5(selectedRifle5);
                sfs.setRifleChoice6(selectedRifle6);
                sfs.setRifleChoice7(selectedRifle7);
                sfs.setRifleChoice8(selectedRifle8);
                sfs.setRifleChoice9(selectedRifle9);
                sfs.setRifleChoice10(selectedRifle10);
                sfs.setScreen(new PlayScreen(sfs, 1));
            }
        }
    }

    public void update(float dt){
        handleInput(dt);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
