package com.planet2d.editor.screens;

import com.planet2d.editor.Editor;
import com.planet2d.editor.Loader;
import com.planet2d.engine.Engine;

public class InitScreen extends com.planet2d.engine.screens.InitScreen {

	public InitScreen(Engine engine) {
		super(engine);
	}
	
	@Override
	public void load() {
		
		Loader.loadUI();
	}
	
	@Override
	public void clear() {
		
		this.clear(0.36f, 0.36f, 0.36f);
	}
	
	@Override
	protected void create() {
		
		Editor.window.create();
	}
}
