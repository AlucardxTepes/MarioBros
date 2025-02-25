package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
    private boolean setToDestroy;
    private boolean isDestroyed;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16 ));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 / Main.PPM,16 / Main.PPM);
        setToDestroy = false;
        isDestroyed = false;
    }

    @Override
    protected void defineEnemy() {
        // body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX()+1, getY());
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
        b2Body.createFixture(fixtureDef).setUserData(this); // setuserdata so that we can access it

        // Create head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-5, 8).scl(1/Main.PPM); // radius is 6 so this will go 2 above
        vertices[1] = new Vector2(5, 8).scl(1/Main.PPM);
        vertices[2] = new Vector2(-3, 3).scl(1/Main.PPM);
        vertices[3] = new Vector2(3, 3).scl(1/Main.PPM);
        head.set(vertices);

        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f; // 1 means Mario would bounce 10 pixels above
        fixtureDef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        b2Body.createFixture(fixtureDef).setUserData(this); // setuserdata so that we can access it

    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }

    public void update(float delta) {
        stateTime += delta;
        if (setToDestroy && !isDestroyed) {
            world.destroyBody(b2Body);
            isDestroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16 ));
            stateTime = 0;
            Main.manager.get("audio/sounds/stomp.wav", Sound.class).play();
        } else if (!isDestroyed) {
            b2Body.setLinearVelocity(velocity);
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }

    }

    @Override
    public void draw(Batch batch) {
        if (!isDestroyed || stateTime < 1) {
            super.draw(batch); // Textures are drawn only if NOT destroyed and stateTime < 1
        }
    }
}
