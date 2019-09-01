package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.editor.listeners.ActorListener;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TileCell;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public class TileCellListener extends InputListener {
	
	private TileCell tileCell;

	public TileCellListener(TileCell tileCell) {

		this.tileCell = tileCell;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		if (this.tileCell.tileMapGrid.blockCellCreation) return false;
		if (this.tileCell.toDeleteTile || this.tileCell.toGenerateTile) return false;
		this.interact();
		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
	}

	@Override
	public boolean mouseMoved (InputEvent event, float x, float y) {
		
		return true;
	}
	
	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {

		if (this.tileCell.tileMapGrid.blockCellCreation) return;
		if (this.tileCell.toDeleteTile || this.tileCell.toGenerateTile) return;
		this.tileCell.tileMapGrid.hoveredCell = this.tileCell;
		if (!Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			this.tileCell.shape = ShapeType.Filled;
		}
		
		if (pointer != 0) this.interact();
	}

	@Override
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
		
		if (pointer == -1) {
			this.tileCell.shape = ShapeType.Line;
			this.tileCell.tileMapGrid.hoveredCell = null;
		}
	}
	
	private void interact() {
		
		if (this.tileCell.toDeleteTile || this.tileCell.toGenerateTile) return;
		
		if (this.tileCell.tileMapGrid.isEditorEdited() && !Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			
			ActorListener.touchOutsideActors = false;
			
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				this.tileCell.toGenerateTile = true;
				
			} else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				this.tileCell.toDeleteTile = true;
			}
		}
	}
}
