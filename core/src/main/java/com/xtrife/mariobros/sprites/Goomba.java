package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.screens.PlayScreen;

/**
 * Created by 9S on 2/24/2025 - 7:09 PM.
 */
public class Goomba extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16 ));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 / Main.PPM,16 / Main.PPM);
    }

    @Override
    protected void defineEnemy() {
        // body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Main.PPM, 32 / Main.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        // body fixture
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fixtureDef.filter.categoryBits = Main.ENEMY_BIT;
        fixtureDef.filter.maskBits = Main.GROUND_BIT |
            Main.BRICK_BIT | Main.COIN_BIT | Main.ENEMY_BIT | Main.OBJECT_BIT | Main.MARIO_BIT;

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);
    }

    public void update(float delta) {
        stateTime += delta;
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }
}
