package com.planet2d.editor;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.graphics.Pixmap;
import com.planet2d.engine.Engine;
import com.planet2d.editor.graphics.ActorRenderer;
import com.planet2d.editor.history.History;
import com.planet2d.editor.layouts.CanvasLayout;
import com.planet2d.editor.layouts.WindowLayout;
import com.planet2d.editor.listeners.UserInputListener;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.ui.Styles;
import com.planet2d.engine.ui.Cursors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Editor extends Engine {

	public static WindowLayout window;
	public static CanvasLayout canvas;
	public static ProjectPage currentPage;
	private Cursors cursors;
	private History history;
	public static Lwjgl3Window mainWindow;
	
	@Override
	public void create() {
		
		super.create();
		this.createCursors();
		this.createHistory();
        this.configWindow();
    }

    private void configWindow() {

        Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
        // Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
        // Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        mainWindow = ((Lwjgl3Graphics)Gdx.graphics).getWindow();
        Pixmap pixmap16 = new Pixmap(Gdx.files.internal("editor/textures/ui/app-icon-16.png"));
        Pixmap pixmap32 = new Pixmap(Gdx.files.internal("editor/textures/ui/app-icon-32.png"));
        Pixmap pixmap128 = new Pixmap(Gdx.files.internal("editor/textures/ui/app-icon-128.png"));
        mainWindow.setIcon(pixmap16, pixmap32);
        // mainWindow.setPosition(0,0);
        //  mainWindow.maximizeWindow();
        Config.VIRTUAL_SCREEN_WIDTH = Gdx.graphics.getWidth();
        Config.VIRTUAL_SCREEN_HEIGHT = Gdx.graphics.getHeight();
    }

    @Override
	protected void createSkin() {

		Engine.skin = new Skin(Gdx.files.internal("editor/skin/metal-ui.json"));
		Styles.create();
	}
	
	@Override
	protected void createRenderer() {

		renderer = new ActorRenderer();
	}
	
	private void createHistory() {
		this.history = new History();	
	}

	private void createCursors() {
		this.cursors = new Cursors();
	}

	@Override
	protected void createListeners() {

		Engine.userInputListener = new UserInputListener();
	}

	public static void undo() {
		
		//History.undo();
	}
	
	public static void redo() {
		
		//History.redo();
	}
}