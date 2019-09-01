package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TilePoint;

public class TilePointListener extends InputListener {
	
	private TilePoint tilePoint;
	private boolean horizontal;
	private Float initDragX;
	private Float initDragY;
	
	public TilePointListener(TilePoint tilePoint) {

		this.tilePoint = tilePoint;
		this.horizontal = this.tilePoint.topTile != null;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
		this.tilePoint.tileMapGrid.blockCellCreation = true;
		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		this.initDragX = null;
		this.initDragY = null;
		this.tilePoint.tileMapGrid.blockCellCreation = false;
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		
		if (this.initDragX == null) this.initDragX = x;
		if (this.initDragY == null) this.initDragY = y;
		
		if (this.horizontal) this.tilePoint.dragH(x);
		else this.tilePoint.dragV(y);
	}

	@Override
	public boolean mouseMoved (InputEvent event, float x, float y) {
		
		return true;
	}
	
	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {

	}

	@Override
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
		
	}
}
