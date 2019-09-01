package com.planet2d.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.screens.InitScreen;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.graphics.ActorRenderer;
import com.planet2d.engine.graphics.Camera;
import com.planet2d.engine.input.UserInputListener;
import com.planet2d.engine.layouts.GameLayout;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.stage.Stage;
import com.planet2d.engine.stage.StageJson;

public abstract class Engine extends Game {

	public static UserInputListener userInputListener;
	public static Screen previousScreen;
	public static Screen currentScreen;
	public static GameLayout gameLayout;
	public static Skin skin;
	public static Config config;
	public static Stage stage;
	public static ActorRenderer renderer;
	//private FPSLogger fpsLogger;
	
	@Override
	public void create () {

		this.loadConfig();
		this.createSkin();
		this.createRenderer();
		this.createListeners();
		this.setScreen(new InitScreen(this));
		//this.fpsLogger = new FPSLogger();
	}

	protected void loadConfig() {

		config = new Config();
		config.load();
	}

	protected abstract void createSkin();
	
	protected abstract void createRenderer();
	
	@Override
	public void render() {
		
		super.render();
		Resources.update();
		//if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Engine.exit();
	//	this.fpsLogger.log();
	}
	
	/*
	 * PARSE JSON STAGE
	 */
	public void createStage() {
		
		Json json = new StageJson();
		Engine.stage = json.fromJson(Stage.class, Gdx.files.internal("json/levels/"+Config.stagePath+".json"));
		Engine.stage.create();
		
		long mem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024l*1024l);
		Gdx.app.log("total mem", mem+"MB");
	}
	
	public static void exit() {
		
		Engine.unload();
		Resources.clear();
		Gdx.app.exit();
	}

	private static void unload() {

		Engine.currentScreen.dispose();
		Engine.stage = null;
	}

	protected void createListeners() {

		Engine.userInputListener = new UserInputListener();
	}
	
	@Override
	public void setScreen(com.badlogic.gdx.Screen screen) {
		
		((Screen)screen).init();
		super.setScreen(screen);
		currentScreen = (Screen) screen;
	}
	
	public static Camera getCamera() {
		
		return currentScreen.getCamera();
	}
	
	public static boolean isDesktop() {

		return Gdx.app.getType() == ApplicationType.Desktop;
	}
	
	public static boolean isMobile() {
		
		return Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS;
	}

	public static void render(GameActor gameActor, Batch batch, float parentAlpha) {

		renderer.render(gameActor, batch, parentAlpha);
	}
}
