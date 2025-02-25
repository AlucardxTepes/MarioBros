package com.xtrife.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.xtrife.mariobros.screens.PlayScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    public static AssetManager manager; // WARNING: you should not have a static AssetManager normally

    public static final short DEFAULT_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;



    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/music/mario_music.ogg", Music.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.finishLoading();

        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render(); // delegate to active screen (which was set using setScreen method)
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }
}
