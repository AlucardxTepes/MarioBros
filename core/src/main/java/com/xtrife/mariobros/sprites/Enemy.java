package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
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


    public Enemy(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    public abstract void hitOnHead();
}
