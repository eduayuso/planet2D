package com.planet2d.editor.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.ShapeActor;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.screens.Screen;

public class EditorDebug extends ShapeActor {

	public boolean hovered;
	public boolean selected;
	public boolean edited;
	public boolean movingOrigin;
	public OriginActor originIndicator;

	public EditorDebug(GameActor actor) {
		
		super();
		
		this.originIndicator = new OriginActor(actor);
		this.originIndicator.create();
		this.originIndicator.setVisible(false);
	}
	
	public void render(GameActor actor, Batch batch, float parentAlpha) {

		if (this.edited) this.drawEditorBox(actor, batch, Color.YELLOW);
		else if (this.selected) this.drawEditorBox(actor, batch, Color.BLUE);
		else if (this.hovered) this.drawEditorBox(actor, batch, Color.RED);
		
		float alpha = parentAlpha;
		if (Config.showUnselectedTranslucid && !this.selected) alpha = 0.5f; 
		else alpha = parentAlpha;
		
		actor.render(batch, alpha);
		if (this.edited) this.originIndicator.updatePosition();
	}
	
	private void drawEditorBox(GameActor actor, Batch batch, Color color) {
		
		batch.end();
		
		Screen.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		Screen.shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		Screen.shapeRenderer.begin(ShapeType.Line);
		Screen.shapeRenderer.setColor(color);
		Screen.shapeRenderer.rect(actor.getX(), actor.getY(), actor.getOriginX(), actor.getOriginY(), actor.getWidth(), actor.getHeight(), actor.getScaleX(), actor.getScaleY(), 0);
		Screen.shapeRenderer.end();
		
		batch.begin();
	}
	
	public void setOriginVisible(boolean b) {

		this.originIndicator.setVisible(b);
		this.originIndicator.toFront();
	}
}
