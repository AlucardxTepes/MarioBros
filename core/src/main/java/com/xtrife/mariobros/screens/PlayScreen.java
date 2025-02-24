package com.xtrife.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xtrife.mariobros.Main;
import com.xtrife.mariobros.scenes.Hud;
import com.xtrife.mariobros.sprites.Mario;
import com.xtrife.mariobros.tools.B2WorldCreator;

/**
 * Created by 9S on 2/24/2025.
 */
public class PlayScreen implements Screen {

  private final Main game;
  private TextureAtlas atlas;
  private OrthographicCamera gamecam;
  private Viewport viewport;
  private Hud hud;

  private TmxMapLoader mapLoader;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;

  private World world;
  private Box2DDebugRenderer b2dr;

  private Mario player;

  public PlayScreen(Main game) {
    atlas = new TextureAtlas("Mario_and_Enemies.atlas");
    this.game = game;
    // create camera that will eventually follow Mario
    gamecam = new OrthographicCamera();

    // maintain aspect ratio
    viewport = new FitViewport(Main.V_WIDTH / Main.PPM, Main.V_HEIGHT / Main.PPM, gamecam);
    // create HUD
    hud = new Hud(game.batch);

    // TiledMap level
    mapLoader = new TmxMapLoader();
    map = mapLoader.load("level0.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM);
    gamecam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

    world = new World(new Vector2(0, -10), true); // physics gravity
    b2dr = new Box2DDebugRenderer();

    new B2WorldCreator(world, map);

    player = new Mario(world, this);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    // separate update logic from render
    update(delta);

    // clear the game screen with black
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // render game map
    renderer.render();

    // render Box2DDebugLines
    b2dr.render(world, gamecam.combined);

    // setup camera
    game.batch.setProjectionMatrix(gamecam.combined);
    game.batch.begin();
    player.draw(game.batch);
    game.batch.end();

    // draw the hud
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
    map.dispose();
    renderer.dispose();
    world.dispose();
    b2dr.dispose();
    hud.dispose();
  }

  public void update(float delta) {
    handleInput(delta);

    // how often to update box2d physics
    world.step(1/60f, 6, 2);

    gamecam.position.x = player.b2Body.getPosition().x; // camera tracks only x movement

    player.update(delta);

    gamecam.update(); // always update cam
    renderer.setView(gamecam); // render only what cam can see
  }

  private void handleInput(float delta) {
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      // apply impulse to center of body to avoid particular torque
      player.b2Body.applyLinearImpulse(new Vector2(0, 4f), player.b2Body.getWorldCenter(), true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) // hold down key
      && player.b2Body.getLinearVelocity().x <= 2) { // prevent going too fast
      player.b2Body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2Body.getWorldCenter(), true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) // hold down key
      && player.b2Body.getLinearVelocity().x >= -2) { // prevent going too fast
      player.b2Body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2Body.getWorldCenter(), true);
    }

  }

  public TextureAtlas getAtlas() {
      return atlas;
  }
}
