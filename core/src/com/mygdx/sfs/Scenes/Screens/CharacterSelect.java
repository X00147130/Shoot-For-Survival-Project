package com.mygdx.sfs.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.RED;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private Texture background;
    private int location;
    private int map;

/* Arrays To Loop through for Selection */
    private ArrayList<TextureAtlas.AtlasRegion> characterSprites;
    private ArrayList<Label> characterNames;
    private int i = 0;

//Selection Variables
    private TextureAtlas selected;
    private TextureAtlas selected2;
    private TextureAtlas selected3;
    private TextureAtlas selected4;
    private TextureAtlas selected5;
    private TextureAtlas selected6;
    private TextureAtlas selected7;
    private TextureAtlas selected8;
    private TextureAtlas selected9;
    private TextureAtlas selected10;
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

//Image variables
    private Image left, right;

//Stage Variables
    private Table table;
    private Stage stage;

//Button Style Variables
    private TextButton.TextButtonStyle textStyle;
    private BitmapFont buttonFont;

//Label Variables
    private Label punkLabel;
    private Label bikerLabel;
    private Label cyborgLabel;
    private Label arrows;
    private Label enter;
    private Label choose;



//Class Constructors
    public CharacterSelect(shootForSurvival game, int area, int level) {
//Variable initalisation
        this.sfs = game;
        viewport = new FitViewport(sfs.V_WIDTH, sfs.V_HEIGHT, new OrthographicCamera());

        location = area;
        map = level;
        if (area == 1) {
            background = sfs.manager.get("backgrounds/Background.png", Texture.class);
        }
        else if(area == 2){
            background = sfs.manager.get("backgrounds/ResidentialBackround.png", Texture.class);
        }

//initialising and instantiating of animatimation arrays
        characterSprites = new ArrayList<TextureAtlas.AtlasRegion>(3);

        characterSprites.add(sfs.getBikerAtlas().findRegion("idle1"));

        characterSprites.add(sfs.getPunkAtlas().findRegion("idle1"));

        characterSprites.add(sfs.getCyborgAtlas().findRegion("idle1"));


//Label Style Initalisations
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-title-export.fnt")), MAGENTA);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/CyberpunkCraftpixFont.fnt")), MAGENTA);

//Text Button Style Initalisations
        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = MAGENTA;

//Labels Setup
        arrows = new Label("Arrows To Swap Character",font2);
        enter = new Label("Enter to Select",font2);
        arrows.setFontScale(0.4f, 0.5f);
        enter.setFontScale(0.4f, 0.5f);

//Intiatialising and Names of characters added to the arraylist
        characterNames = new ArrayList<Label>(3);

        bikerLabel = new Label("Clyde", font);
        punkLabel = new Label("Chad", font);
        cyborgLabel = new Label("X01F", font);

        characterNames.add(0,bikerLabel);
        characterNames.add(1,punkLabel);
        characterNames.add(2,cyborgLabel);


//Using Texture
        Texture button1 = new Texture("controller/rightBtn.png");
        Texture button2 = new Texture("controller/leftBtn.png");

//Right Button Setup
        right = new Image(button1);
        right.setSize(3 / sfs.PPM, 3 / sfs.PPM);

//Left Button Setup
        left = new Image(button2);
        left.setSize(3 / sfs.PPM, 3 / sfs.PPM);

//Selection Button Setup
        choose = new Label("Time To Kill", font2);
        choose.setFontScale(0.4f,0.5f);

//Setup for android
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
//Initialising stage and table
            stage = new Stage(viewport, sfs.batch);
            table = new Table();
            table.setFillParent(true);
            table.center();

//Filling the Table
            table.add(left).left().size(button2.getWidth() / 10, button2.getHeight() / 10).padRight(60).padTop(20);
            table.add(right).right().size(button1.getWidth() / 10, button1.getHeight() / 10).padLeft(60).padTop(20);
            table.row();
            table.row();
            table.add(choose).center().padLeft(130).padTop(30);

//Passing the table to the Stage and passing the Stage in as the location we want to read inputs from
            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);

//Adding a click Listener for the right arrow button
            right.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 1;
                        characterNames.get(i).setColor(RED);
                        choose.setColor(RED);
                        characterNames.get(i);
                        characterSprites.get(i);

                    } else if (i == 1) {
                        i = 2;
                        characterNames.get(i).setColor(CYAN);
                        choose.setColor(CYAN);
                        characterNames.get(i);
                        characterSprites.get(i);

                    } else if (i == 2) {
                        i = 0;
                        characterNames.get(i).setColor(MAGENTA);
                        choose.setColor(MAGENTA);
                        characterNames.get(i);
                        characterSprites.get(i);
                    }
                }
            });

//Adding a click Listener for the Left arrow button
            left.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 2;
                        characterNames.get(i).setColor(CYAN);
                        choose.setColor(CYAN);
                        characterSprites.get(i);
                        characterNames.get(i);

                    } else if (i == 1) {
                        i = 0;
                        characterNames.get(i).setColor(MAGENTA);
                        choose.setColor(MAGENTA);
                        characterSprites.get(i);
                        characterNames.get(i);
                    } else if (i == 2) {
                        i = 1;
                        characterNames.get(i).setColor(RED);
                        choose.setColor(RED);
                        characterSprites.get(i);
                        characterNames.get(i);
                    }
                }
            });

//Adding a click Listener for the Selection button
            choose.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        sfs.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(sfs.getSoundVolume());
                    }
                    switch (i) {
                        case 0:
                            selected = sfs.getBikerAtlas();
                            selected2 = sfs.getBikerAtlas2();
                            selected3 = sfs.getBikerAtlas3();
                            selected4 = sfs.getBikerAtlas4();
                            selected5 = sfs.getBikerAtlas5();
                            selected6 = sfs.getBikerAtlas6();
                            selected7 = sfs.getBikerAtlas7();
                            selected8 = sfs.getBikerAtlas8();
                            selected9 = sfs.getBikerAtlas9();
                            selected10 = sfs.getBikerAtlas10();
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
                            selected2 = sfs.getPunkAtlas2();
                            selected3 = sfs.getPunkAtlas3();
                            selected4 = sfs.getPunkAtlas4();
                            selected5 = sfs.getPunkAtlas5();
                            selected6 = sfs.getPunkAtlas6();
                            selected7 = sfs.getPunkAtlas7();
                            selected8 = sfs.getPunkAtlas8();
                            selected9 = sfs.getPunkAtlas9();
                            selected10 = sfs.getPunkAtlas10();
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
                            selected2 = sfs.getCyborgAtlas2();
                            selected3 = sfs.getCyborgAtlas3();
                            selected4 = sfs.getCyborgAtlas4();
                            selected5 = sfs.getCyborgAtlas5();
                            selected6 = sfs.getCyborgAtlas6();
                            selected7 = sfs.getCyborgAtlas7();
                            selected8 = sfs.getCyborgAtlas8();
                            selected9 = sfs.getCyborgAtlas9();
                            selected10 = sfs.getCyborgAtlas10();
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
                    sfs.setPlayersChoice2(selected2);
                    sfs.setPlayersChoice3(selected3);
                    sfs.setPlayersChoice4(selected4);
                    sfs.setPlayersChoice5(selected5);
                    sfs.setPlayersChoice6(selected6);
                    sfs.setPlayersChoice7(selected7);
                    sfs.setPlayersChoice8(selected8);
                    sfs.setPlayersChoice9(selected9);
                    sfs.setPlayersChoice10(selected10);
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

                    sfs.setScreen(new PlayScreen(sfs, location , map));
                }
            });
        }

    }

    //Render Method
        @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            update(delta);



//Open the Batch from the main page
        sfs.batch.begin();

        sfs.batch.draw(background,0,0,500,200);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            arrows.setBounds(80, 155, 70, 70);
            arrows.draw(sfs.batch,1);

            enter.setBounds(125, 5, 70, 70);
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

//A getter for retrieving i as i is the choice of Character the player makes
    public int getI() {
        return i;
    }

//Handling inputs for the Computer when testing for selection
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
                    characterNames.get(i).setColor(CYAN);
                    choose.setColor(CYAN);
                    characterSprites.get(i);
                    characterNames.get(i);

                } else if (i == 1) {
                    i = 0;
                    characterNames.get(i).setColor(MAGENTA);
                    choose.setColor(MAGENTA);
                    characterSprites.get(i);
                    characterNames.get(i);
                } else if (i == 2) {
                    i = 1;
                    characterNames.get(i).setColor(RED);
                    choose.setColor(RED);
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
                    characterNames.get(i).setColor(RED);
                    choose.setColor(RED);
                    characterSprites.get(i);
                    characterNames.get(i);

                } else if (i == 1) {
                    i = 2;
                    characterNames.get(i).setColor(CYAN);
                    choose.setColor(CYAN);
                    characterSprites.get(i);
                    characterNames.get(i);
                } else if (i == 2) {
                    i = 0;
                    characterNames.get(i).setColor(MAGENTA);
                    choose.setColor(MAGENTA);
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
                        selected2 = sfs.getBikerAtlas2();
                        selected3 = sfs.getBikerAtlas3();
                        selected4 = sfs.getBikerAtlas4();
                        selected5 = sfs.getBikerAtlas5();
                        selected6 = sfs.getBikerAtlas6();
                        selected7 = sfs.getBikerAtlas7();
                        selected8 = sfs.getBikerAtlas8();
                        selected9 = sfs.getBikerAtlas9();
                        selected10 = sfs.getBikerAtlas10();
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
                        selected2 = sfs.getPunkAtlas2();
                        selected3 = sfs.getPunkAtlas3();
                        selected4 = sfs.getPunkAtlas4();
                        selected5 = sfs.getPunkAtlas5();
                        selected6 = sfs.getPunkAtlas6();
                        selected7 = sfs.getPunkAtlas7();
                        selected8 = sfs.getPunkAtlas8();
                        selected9 = sfs.getPunkAtlas9();
                        selected10 = sfs.getPunkAtlas10();
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
                        selected2 = sfs.getCyborgAtlas2();
                        selected3 = sfs.getCyborgAtlas3();
                        selected4 = sfs.getCyborgAtlas4();
                        selected5 = sfs.getCyborgAtlas5();
                        selected6 = sfs.getCyborgAtlas6();
                        selected7 = sfs.getCyborgAtlas7();
                        selected8 = sfs.getCyborgAtlas8();
                        selected9 = sfs.getCyborgAtlas9();
                        selected10 = sfs.getCyborgAtlas10();
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
                sfs.setPlayersChoice2(selected2);
                sfs.setPlayersChoice3(selected3);
                sfs.setPlayersChoice4(selected4);
                sfs.setPlayersChoice5(selected5);
                sfs.setPlayersChoice6(selected6);
                sfs.setPlayersChoice7(selected7);
                sfs.setPlayersChoice8(selected8);
                sfs.setPlayersChoice9(selected9);
                sfs.setPlayersChoice10(selected10);
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
                sfs.setScreen(new PlayScreen(sfs, location, map));
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

//Trash disposing
    @Override
    public void dispose() {
        stage.dispose();
        System.gc();
    }
}
