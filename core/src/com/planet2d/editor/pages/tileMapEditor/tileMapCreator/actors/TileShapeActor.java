package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.engine.actors.ShapeActor;

public class TileShapeActor extends ShapeActor {
	
	public TileMapGrid tileMapGrid;
	public int row;
	public int col;

	public TileShapeActor(TileMapGrid tileMapGrid, int row, int col, float x, float y, float width, float height) {
		
		super(x, y, width, height);
		this.tileMapGrid = tileMapGrid;
		this.row = row;
		this.col = col;
	}
	
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public void render(Batch batch, float parentAlpha) {
		
		if (this.tileMapGrid.isEditorEdited()) {
			this.setTouchable(Touchable.enabled);
			if (this.isEmpty()) super.render(batch, parentAlpha);
		} else {
			this.setTouchable(Touchable.disabled);
		}
	}
}
