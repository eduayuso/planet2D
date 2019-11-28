package com.planet2d.engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.planet2d.engine.actors.GameActor;

public class ActorRenderer {

	public void render(GameActor actor, Batch batch, float parentAlpha) {
		
		Color color = batch.getColor();
		batch.setColor(color.r, color.g, color.b, color.a*parentAlpha);
		
		actor.render(batch, parentAlpha);
		
		batch.setColor(color);
	}
}
