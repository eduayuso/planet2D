package com.planet2d.engine.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.engine.Engine;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.graphics.Shader;

public class Layer extends Group {

	public final static int BACKGROUND_LAYER = 0;
	public final static int GROUND_LAYER = 1;
	public final static int FOREGROUND_LAYER = 2;
	
	private int type;
	private int index;
	private char loop;
	private boolean fixedX;
	private boolean fixedY;
	private float parallaxFactor = 1f;
	
	private SpriteCache cache;
	private int cacheId;
	private int numCachableObjects;
	private Shader shader;
	
	public Layer() { }
	
	public void addGameActor(GameActor actor) {
		
		this.addActor(actor);
		actor.setZIndex(this.index + actor.layerIndex);
		
		this.numCachableObjects += actor.getCacheableChildrenNum();
	}
	
	@Override
	public Object clone() {
		
		Layer layer = new Layer();
		layer.type = this.type;
		layer.index = this.index;
		layer.loop  = this.loop;
		layer.setPosition(this.getX(), this.getY());
		layer.setSize(this.getWidth(), this.getHeight());
		
		for (Actor actor: this.getChildren()) {
			
			GameActor cloned = null;
			
			if (actor instanceof SpriteActor) {
				
				SpriteActor so = (SpriteActor)actor;
			//	cloned = (SpriteActor)so.clone();
			}
			
			if (cloned != null) {
				layer.addGameActor(cloned);
			}
		}
		
		return layer;
	}
	
	public void updateParallaxPosition(float diffX, float diffY) {

		float parallaxX = this.getParallaxValue(this.getX(), diffX, true);
		float parallaxY = this.getParallaxValue(this.getY(), diffY, false);
		this.setPosition(parallaxX, parallaxY);
	}

	private float getParallaxValue(float value, float diff, boolean horizontal) {

		if (this.type == BACKGROUND_LAYER) {
			
			float indexFactor = 0.05f*this.parallaxFactor;
			float totalFactor = 2f;
			if (horizontal) {
				indexFactor = 0.2f;
				totalFactor = 6f;
			}
			
			return value+diff*(1f-(float)(1f+this.getIndex()*indexFactor)/totalFactor);
			
		} else if (this.type == FOREGROUND_LAYER) {
			
			return value+diff*(1f-(float)(7f+this.getIndex()*0.8f)/6f);
		
		} else {
			
			return value;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		if (this.fixedX) {
			float fixedX = Engine.getCamera().getX() - Engine.getCamera().getWidth()/2f;
			this.setPosition(fixedX, this.getY());
		}
		
		if (this.fixedY) {
			
			float fixedY = Engine.getCamera().getY() - Engine.getCamera().getHeight()/2f;
			this.setPosition(this.getX(), fixedY);
		}
		if (this.shader != null) this.shader.update();
		super.draw(batch, parentAlpha);
		batch.setShader(null);
	}
	
	public void configCache() {
		
		this.cache = new SpriteCache(this.numCachableObjects, true);
		this.cache.beginCache();
		
		for (Actor actor: this.getChildren()) {
			GameActor gactor = ((GameActor)actor);
			if (gactor.isCacheable()) gactor.toCache(this.cache);
		}
		
		this.cacheId = this.cache.endCache();
	}
	
	public void drawCache() {
		
		if (this.cache != null && Engine.gameLayout != null && Engine.gameLayout.getCamera() != null) {
			this.cache.setProjectionMatrix(Engine.gameLayout.getCamera().combined);
			
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			// FIXME: if (Game.mustShowStageShader()) this.cache.setShader(Game.stage.storm.getDarkShader());
			this.cache.begin();
			this.cache.draw(this.cacheId);
			this.cache.end();
			this.cache.setShader(null);
		}
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public char getLoop() {
		return loop;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isFixedX() {
		return fixedX;
	}

	public void setFixedX(boolean fixed) {
		this.fixedX = fixed;
	}
	
	public boolean isFixedY() {
		return fixedY;
	}

	public void setFixedY(boolean fixed) {
		this.fixedY = fixed;
	}
	
	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public void setLoop(char loop) {
		this.loop = loop;
	}
}
