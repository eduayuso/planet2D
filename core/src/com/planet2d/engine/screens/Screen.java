package com.planet2d.engine.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.planet2d.engine.Engine;
import com.planet2d.engine.Resources;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.graphics.Camera;
import com.planet2d.engine.layouts.Layout;
import com.planet2d.editor.Editor;
import com.planet2d.editor.layouts.CanvasLayout;
import com.planet2d.editor.layouts.WindowLayout;

public abstract class Screen implements com.badlogic.gdx.Screen {

	protected Game app;
	protected Array<Layout> layouts;
	public static ShapeRenderer shapeRenderer;
	protected Camera camera;
	private boolean created;
	public SpriteBatch batch;

	public Screen(Game app) {
		
		this.app = app;
		this.batch = new SpriteBatch();
		Screen.shapeRenderer = new ShapeRenderer();
	}
	
	public void init() {
		
		this.createMainLayouts();
		this.setInput();
		this.load();
	}
	
	protected void createMainLayouts() {

		this.layouts = new Array<>();
		
		// Canvas layout
		Editor.canvas = new CanvasLayout(this);
		this.layouts.add(Editor.canvas);
		
		// Custom layout
		this.createExtraLayouts();
		
		// Window Frame
		Editor.window = new WindowLayout(this);
		this.layouts.add(Editor.window);
	}

	protected void createExtraLayouts() {}

	protected void setInput() {

		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		for (Layout layout: this.layouts) inputMultiplexer.addProcessor(layout);
		inputMultiplexer.addProcessor(Engine.userInputListener);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	protected abstract void load();
	protected abstract void create();
	
	@Override
	public void render(float delta) {
		
		this.clear();
		this.showLoadingProgress();
		
		if (this.created) {
			for (Layout layout: this.layouts) {
				layout.act(delta);
				layout.draw();
			}
		}
	}
	
	public void showLoadingProgress() {
		
		if (!Resources.loaded()) {
			this.showProgress();
		
		} else if (!this.created) {
			this.create();
			this.created = true;
		}
	}
	
	protected void showProgress() { }
	
	public void change(Screen newScreen) {

		this.app.setScreen(newScreen);
	}

	public void addToCanvasLayout(Actor actor) {

		Editor.canvas.add(actor);
	}
	
	public void addToWindowLayout(Actor actor) {

		Editor.window.add(actor);
	}
	
	public void clear() {
		
		this.clear(0,0,0);
	}
	
	public void clear(float r, float g, float b) {
		
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	
	public static float getWidth() {
		
		return Gdx.graphics.getWidth();
	}
	
	public static float getHeight() {
		
		return Gdx.graphics.getHeight();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void resize(int width, int height) {

		for (Layout layout: this.layouts) layout.resize(width, height);

		if (Editor.window != null && Editor.window.getMainToolBar() != null) {
           // Editor.window.getMainToolBar().setSize(width, Editor.window.getMainToolBar().getHeight());
        }
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

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
