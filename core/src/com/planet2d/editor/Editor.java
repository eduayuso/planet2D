package com.planet2d.editor;

import com.planet2d.engine.Engine;
import com.planet2d.editor.graphics.ActorRenderer;
import com.planet2d.editor.history.History;
import com.planet2d.editor.layouts.CanvasLayout;
import com.planet2d.editor.layouts.WindowLayout;
import com.planet2d.editor.listeners.UserInputListener;
import com.planet2d.editor.pages.ProjectPage;
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
	
	@Override
	public void create() {
		
		super.create();
		this.createCursors();
		this.createHistory();
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