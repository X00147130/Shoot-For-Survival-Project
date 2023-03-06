package com.mygdx.sfs.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.sfs.Screens.PlayScreen;
import com.mygdx.sfs.shootForSurvival;

public class Bullets {

    public static final float SPEED = 3f;
    private static Texture texture;
    private shootForSurvival sfs;
    private FixtureDef bulletDef;
    public Body bulletBody;
    private boolean destroyed;
    private World world;
    private PlayScreen screen;

    public float x,y;
    public boolean todestroy = false;

    public Bullets(shootForSurvival sfs,PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.screen = screen;
        this.sfs = sfs;
        this.y = y;
        this.x = x;

        texture =new Texture("sprites/bullet.png");

        destroyed = false;

        defineBullet();

        if (bulletBody.getPosition().x > screen.getGamePort().getScreenWidth()){
            todestroy = true;
        }
    }

    public Fixture defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);


        bulletBody.setBullet(true);
        if(!screen.getPlayer().isFlipX()) {
            bulletBody.setLinearVelocity(SPEED,0);
        }else{
            bulletBody.setLinearVelocity(-SPEED,0);
        }


        bulletDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f / shootForSurvival.PPM);
        bulletDef.filter.categoryBits = shootForSurvival.BULLET_BIT;
        bulletDef.filter.maskBits = shootForSurvival.ENEMY_BIT |
                shootForSurvival.GROUND_BIT;

        bulletDef.shape = shape;
        Fixture fix1 = bulletBody.createFixture(bulletDef);
        bulletBody.setGravityScale(0);
        Gdx.app.log("bullet", "shoot");

        return fix1;
    }

    public void destroy(){
        todestroy = true;
    }

    public void update(float dt){
       y = SPEED * dt;
        if(todestroy && !destroyed){
            world.destroyBody(bulletBody);
            destroyed = true;
        }
    }

    public void render(SpriteBatch sb){
        sb.draw(texture, bulletBody.getPosition().x, bulletBody.getPosition().y,texture.getWidth() / sfs.PPM,texture.getHeight() / sfs.PPM);
    }
    public void dispose(){
        texture.dispose();
    }
}
