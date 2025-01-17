package com.mygdx.sfs.Sprites.Items;

import static com.mygdx.sfs.shootForSurvival.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sfs.Scenes.Screens.PlayScreen;
import com.mygdx.sfs.Sprites.Player;
import com.mygdx.sfs.shootForSurvival;

public class Money extends Item {
    private static int count = 0;
    private static int startCount = 0;
    public shootForSurvival sfs;
    private Animation<TextureRegion> money;


    public Money(shootForSurvival sfs, PlayScreen screen, float  x, float y) {
        super(screen, x, y);
        this.sfs = sfs;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i <= sfs.getMoneyAtlas().getRegions().size; i++) {
            frames.add(sfs.getMoneyAtlas().findRegion("money" + i ));
        }

        money = new Animation<TextureRegion>(0.2f, frames);
        setBounds(0, 0, 25 / PPM, 35 / PPM);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 35 / PPM, 45 / PPM);
        }
        frames.clear();
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(9 / shootForSurvival.PPM);
        fdef.filter.categoryBits = shootForSurvival.ITEM_BIT;
        fdef.filter.maskBits = shootForSurvival.PLAYER_BIT |
                shootForSurvival.GROUND_BIT |
                shootForSurvival.DOOR_BIT;

        fdef.shape = shape;
        body.setGravityScale(0);
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Player player) {
        destroy();
        count += 100;
        screen.setMoney(count);
        Gdx.app.log("Coin", "destroyed" + count);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sfs.loadSound("audio/sounds/coin sound.wav");
            long id = sfs.sound.play();
            if (sfs.getSoundVolume() != 0)
                sfs.sound.setVolume(id, sfs.getSoundVolume());
            else {
                sfs.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            sfs.manager.get("audio/sounds/coin sound.wav", Sound.class).play(sfs.getSoundVolume());
        }

    }
    @Override
    public void update(float dt) {
        super.update(dt);

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        setRegion(money.getKeyFrame(sfs.statetimer,true));
        if(screen.getPlayer().currentState == Player.State.DEAD && screen.getPlayer().getStateTimer() > 0.01){
            count = startCount;
        }
        else if(screen.isComplete()){
            count = screen.getCoins();
            startCount = screen.getCoins();
            sfs.setStartMoney(startCount);
            System.out.println("Bitch failed" + count);
            sfs.setMoney(startCount);
            Gdx.app.log("Faggot",  "fails" + count);
        }

    }


    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose(){
        screen.dispose();
    }
}
