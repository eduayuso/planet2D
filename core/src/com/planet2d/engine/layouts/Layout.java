package com.planet2d.engine.layouts;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.planet2d.engine.Engine;
import com.planet2d.engine.screens.Screen;

public abstract class Layout extends com.badlogic.gdx.scenes.scene2d.Stage {
	
	protected Camera camera;
	protected Screen screen;
	
	public Layout(Screen screen) {
		
		super(new ScalingViewport(Scaling.none, Screen.getWidth(), Screen.getHeight(), new OrthographicCamera()), screen.batch);
		this.screen = screen;
		this.init();
	}
	
	protected void init() {
		this.camera = this.getViewport().getCamera();
	}
	
	public void add(Actor actor) {
		
		this.addActor(actor);
	}
}
