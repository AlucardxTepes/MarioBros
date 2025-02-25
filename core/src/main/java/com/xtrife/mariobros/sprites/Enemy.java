package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.xtrife.mariobros.screens.PlayScreen;

/**
 * Created by 9S on 2/24/2025 - 7:08 PM.
 */
public abstract class Enemy extends Sprite {

    protected final World world;
    protected final PlayScreen screen;
    public Body b2Body;
    public Vector2 velocity;


    public Enemy(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0.1f, 0);
    }

    protected abstract void defineEnemy();

    public abstract void hitOnHead();

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
