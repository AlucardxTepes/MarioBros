package com.xtrife.mariobros.tools;

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
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.screens.PlayScreen;
import com.xtrife.mariobros.sprites.Brick;
import com.xtrife.mariobros.sprites.Coin;
import com.xtrife.mariobros.sprites.Goomba;

/**
 * Created by 9S on 2/24/2025 - 3:43 AM.
 */
public class B2WorldCreator {

    private Array<Goomba> goombas;

    private final TiledMap map;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        this.map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // create ground bodies / fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Main.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Main.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth() / 2) / Main.PPM, (rectangle.getHeight() / 2) / Main.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
        // create pipes fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Main.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Main.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rectangle.getWidth() / 2 / Main.PPM, rectangle.getHeight() / 2 / Main.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Main.OBJECT_BIT;
            body.createFixture(fixtureDef);
        }
        // create coins fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rectangle);
        }
        // create bricks fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rectangle);
        }

        // create all goombas
        goombas = new Array<>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rectangle.getX() / Main.PPM, rectangle.getY() / Main.PPM));
        }
    }

    public Array<Goomba> getGoombas() {
        return this.goombas;
    }
}
