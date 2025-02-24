package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.xtrife.mariobros.Main;

/**
 * Created by 9S on 2/24/2025 - 3:46 AM.
 */
public class Brick extends InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "onHeadHit");
        setCategoryFilter(Main.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
