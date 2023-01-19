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
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Enemies.Ninja;
import com.mygdx.sfs.Sprites.Items.Coins;
import com.mygdx.sfs.Sprites.Items.health;
import com.mygdx.sfs.Sprites.TileObjects.Barrier;
import com.mygdx.sfs.Sprites.TileObjects.Finish;
import com.mygdx.sfs.Sprites.TileObjects.Platforms;
import com.mygdx.sfs.Sprites.TileObjects.Sky;
import com.mygdx.sfs.shootForSurvival;

public class B2WorldCreator {
        private shootForSurvival ninja;
        private Array<Ninja> ninjas;
        private Array<Coins> coins;
        private Array<health> vials;


        public B2WorldCreator(shootForSurvival ninja, PlayScreen screen) {
            this.ninja = ninja;

            World world = screen.getWorld();
            TiledMap map = screen.getMap();
            //temp body creation
            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;

            //create ground bodies fixtures
            for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / shootForSurvival.PPM, (rect.getY() + rect.getHeight() / 2) / shootForSurvival.PPM);

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2 / shootForSurvival.PPM, rect.getHeight() / 2 / shootForSurvival.PPM);
                fdef.shape = shape;
                body.createFixture(fdef);
            }

            //create ground fixtures
            for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / shootForSurvival.PPM, (rect.getY() + rect.getHeight() / 2) / shootForSurvival.PPM);

                body = world.createBody(bdef);

                shape.setAsBox(rect.getWidth() / 2 / shootForSurvival.PPM, rect.getHeight() / 2 / shootForSurvival.PPM);
                fdef.shape = shape;
                fdef.filter.categoryBits = shootForSurvival.BARRIER_BIT;
                body.createFixture(fdef);
            }

            //create Platforms fixtures
            for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

                // creation of Platform Objects
                new Platforms(screen, object);
            }

            //create finish fixtures
            for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

                // creation of end tree object
                new Finish(ninja, screen, object);
            }

            // create all ninjas e.g. multiple enemies
            ninjas = new Array<Ninja>();
            for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                ninjas.add(new Ninja(ninja,screen, rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
            }

            //create health fixtures
            vials = new Array<health>();
            for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                // creation of health vials objects
                vials.add(new health(ninja, screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));
            }

            //create Coins fixtures
            coins = new Array<Coins>();
            for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                // creation of coin objects
                coins.add(new Coins(ninja,screen,rect.x / shootForSurvival.PPM, rect.y / shootForSurvival.PPM));

            }

            //Create barriers
            for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
                new Barrier(screen,object);
            }

            //Sky limit
            for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
                new Sky(screen,object);
            }

        }

        public Array<Ninja> getNinjas() {
            return ninjas;
        }
        public Array<health> getVials(){return vials;}
        public Array<Coins> getCoins(){return coins;}

    }

