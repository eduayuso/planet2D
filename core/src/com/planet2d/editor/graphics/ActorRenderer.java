package com.planet2d.editor.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.planet2d.engine.actors.GameActor;

public class ActorRenderer extends com.planet2d.engine.graphics.ActorRenderer {

	@Override
	public void render(GameActor actor, Batch batch, float parentAlpha) {
		
		if (actor.editorDebug == null) {
			
			super.render(actor, batch, parentAlpha);
			
		} else {
			
			actor.editorDebug.render(actor, batch, parentAlpha);
		}
	}
}
