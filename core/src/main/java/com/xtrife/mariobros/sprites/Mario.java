package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.screens.PlayScreen;

/**
 * Created by 9S on 2/24/2025 - 3:18 AM.
 */
public class Mario extends Sprite {

    public World world;
    public Body b2Body;
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / Main.PPM, 16 / Main.PPM);
        setRegion(marioStand); // associate texture region with sprite
    }

    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Main.PPM, 32 / Main.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);
    }

    public void update(float delta) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

}
