package com.planet2d.editor.ui;

import com.planet2d.engine.actors.SpriteActor;

public class ActorIcon extends SpriteActor {
	
	public ActorIcon(SpriteActor imageActor, float iconWidth, float iconHeight) {
		
		super(imageActor.getName());
		super.create(imageActor.sprite);
		
		float width = iconWidth-2;
		float height = iconHeight-2;
		float x = this.getX();
		float y = this.getY();
		
		if (this.getWidth() > this.getHeight()) {
			height = width * this.getHeight()/this.getWidth();
			y += (iconHeight-height)/2f;
		} else {
			width = height * this.getWidth()/this.getHeight();
			x += (iconWidth-width)/2f;
		}
		
		this.setSize(width, height);
		this.setPosition(x, y);
	}
}
