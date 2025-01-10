package com.mygdx.sfs.Tools;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Bosses.Scalper;
import com.mygdx.sfs.Sprites.Enemies.Grunts.Grunt;
import com.mygdx.sfs.Sprites.Items.Money;
import com.mygdx.sfs.Sprites.Items.HealthCrate;
import com.mygdx.sfs.Sprites.Items.KeyCard;
import com.mygdx.sfs.Sprites.Items.Rifles;
import com.mygdx.sfs.Sprites.TileObjects.Barrier;
import com.mygdx.sfs.Sprites.TileObjects.Death;
import com.mygdx.sfs.Sprites.TileObjects.Door;
import com.mygdx.sfs.Sprites.TileObjects.Scanner;
import com.mygdx.sfs.Sprites.TileObjects.Sky;
import com.mygdx.sfs.shootForSurvival;

public class B2WorldCreator {
    private shootForSurvival sfs;
    private Array<Grunt> workers;
    private Array<Scalper> scalper;
    private Array<Money> coins;
    private Array<HealthCrate> vials;
    private Array<KeyCard> keys;
    private Array<Rifles> rifles;
    public Door door;
    private Scanner scanner;


    public B2WorldCreator(shootForSurvival game, PlayScreen screen) {
        this.sfs = game;

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
//temp body creation
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

//create ground bodies fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / shootForSurvival.PPM, (rect.getY() + rect.getHeight() / 2) / shootForSurvival.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / shootForSurvival.PPM, rect.getHeight() / 2 / shootForSurvival.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


//create wall fixtures
        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / shootForSurvival.PPM, (rect.getY() + rect.getHeight() / 2) / shootForSurvival.PPM);

            body = world.createBody(bdef);


            shape.setAsBox(rect.getWidth() / 2 / shootForSurvival.PPM, rect.getHeight() / 2 / shootForSurvival.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = shootForSurvival.WALL_BIT;
            body.createFixture(fdef);
        }

//create Coins fixtures
        coins = new Array<Money>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
// creation of coin objects
            coins.add(new Money(sfs,screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));

        }


//create finish fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
 // creation of finish/ door object
            door = new Door(sfs,screen,object);
        }

//create Scanner Fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            scanner = new Scanner(sfs,screen,object);
        }


// create workers enemies
        workers = new Array<Grunt>();
        scalper = new Array<Scalper>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            if(screen.getLevel() == 10){
                scalper.add(new Scalper(sfs,screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
            }else{
            workers.add(new Grunt(sfs,screen, rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
            }
        }


// create hammers enemies
     /*   hammers = new Array<Hammer>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            hammers.add(new Hammer(sfs,screen, rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
        }*/


//Create barriers
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            new Barrier(screen,object);
        }


//create health fixtures
        vials = new Array<HealthCrate>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

// creation of health vials objects
            vials.add(new HealthCrate(sfs, screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
        }


//create KeyCard fixtures
        keys = new Array<KeyCard>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
// creation of Key Card objects
            keys.add(new KeyCard(sfs, screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
        }


//Sky limit
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            new Sky(screen,object);
        }


//Death fixtures
        for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
            new Death(screen,object);
        }

//create Rifle fixtures
        rifles = new Array<Rifles>();
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
// creation of Rifle objects
            rifles.add(new Rifles(sfs, screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
        }


    }

    public Array<Grunt> getWorkers() {
        return workers;
    }
    public Array<Scalper> getScalpers() {
        return scalper;
    }
    public Array<HealthCrate> getVials(){return vials;}
    public Array<Money> getCoins(){return coins;}
    public Array<KeyCard> getKeys(){return keys;}
    public Array<Rifles> getRifles(){return rifles;}
    public Scanner getScanner() {
        return scanner;
    }


    public void dispose(){

    }
}

