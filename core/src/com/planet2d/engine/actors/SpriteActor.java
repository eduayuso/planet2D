package com.planet2d.engine.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.planet2d.editor.config.Config;
import com.planet2d.engine.Resources;

public class SpriteActor extends GameActor {

	public String fileName;
	public Sprite sprite;
	
	public SpriteActor(String name) {
		super(name);
	}
	
	public SpriteActor(String path, String fileName, String name, boolean local) {
		
		this(name);
		this.fileName = fileName;
		if (local) this.create(new Sprite(Resources.getLocalTexture(Config.gamePath, path + "/"+ fileName)));
		else this.create(new Sprite(Resources.getTexture(path, fileName)));
	}
	
	public SpriteActor(String path, String name, boolean local) {
		
		this(path, name, name, local);
	}
	
	public SpriteActor(String projectName, String name) {
		this(projectName, name, false);
	}
	
	public void create(String fullPath) {
		
		this.create(new Sprite(Resources.getLocalTexture(fullPath)));
	}
	
	public void create(Sprite sprite) {
		
		this.sprite = sprite;
    	this.setWidth(this.sprite.getWidth());
    	this.setHeight(this.sprite.getHeight());
    	this.setBounds(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
    public void render(Batch batch, float parentAlpha) {
		
		batch.draw(this.sprite, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
}
