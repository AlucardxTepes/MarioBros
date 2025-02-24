package com.xtrife.mariobros.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.scenes.Hud;

/**
 * Created by 9S on 2/24/2025 - 3:46 AM.
 */
public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28; // ID in tiled is 27. TILEd is zero based, libgdx isnt

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "onHeadHit");
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
