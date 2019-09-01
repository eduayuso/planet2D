package com.planet2d.engine.actors.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.planet2d.engine.actors.GameActor;

public class Effect extends GameActor {

	protected ParticleEffect particleEffect;
	protected ParticleEmitter particleEmitter;
	
	public Effect(String name) {
		super(name);
	}

	@Override
	public void render(Batch batch, float parentAlpha) {
		
	}
}
