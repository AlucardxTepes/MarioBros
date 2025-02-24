package com.xtrife.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.scenes.Hud;

/**
 * Created by 9S on 2/24/2025.
 */
public class PlayScreen implements Screen {

  private final Main game;
  private OrthographicCamera gamecam;
  private Viewport viewport;
  private Hud hud;

  private TmxMapLoader mapLoader;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;

  public PlayScreen(Main game) {
    this.game = game;
    // create camera that will eventually follow Mario
    gamecam = new OrthographicCamera();

    // maintain aspect ratio
    viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, gamecam);
    // create HUD
    hud = new Hud(game.batch);

    // level map
    mapLoader = new TmxMapLoader();
    map = mapLoader.load("level0.tmx");
    renderer = new OrthogonalTiledMapRenderer(map);
    gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    update(delta);
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    renderer.render();

    game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    hud.stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }

  public void update(float delta) {
    handleInput(delta);
    gamecam.update(); // always update cam
    renderer.setView(gamecam); // render only what cam can see
  }

  private void handleInput(float delta) {
    if (Gdx.input.isTouched()) {
      gamecam.position.x += 100 * delta;
    }

  }
}
