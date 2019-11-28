package com.planet2d.engine.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.planet2d.engine.Engine;
import com.planet2d.engine.screens.Screen;

public abstract class Layout extends com.badlogic.gdx.scenes.scene2d.Stage {
	
	protected OrthographicCamera camera;
	protected Screen screen;
	
	public Layout(Screen screen) {
		
		super(new ScreenViewport(new OrthographicCamera()), screen.batch);
		this.screen = screen;
		this.init();
	}
	
	protected void init() {
		this.camera = (OrthographicCamera)this.getViewport().getCamera();
	}
	
	public void add(Actor actor) {
		
		this.addActor(actor);
	}

    public void resize(int width, int height) {

        this.getViewport().update(width, height, false);
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
