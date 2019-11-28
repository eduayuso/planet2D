package com.planet2d.engine.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedActor extends GameActor {
	
	protected Animation<Sprite> animation;
	protected float elapsedTime;

	public AnimatedActor(String name) {
		super(name);
	}

	@Override
	public void render(Batch batch, float parentAlpha) {

		
	}
}
