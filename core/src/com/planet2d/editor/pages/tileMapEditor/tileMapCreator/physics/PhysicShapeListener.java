package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.physics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.engine.actors.ShapeActor;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public class PhysicShapeListener extends InputListener {
	
	private static final int BORDER_WIDTH = 6;
	private TileMapPhysics tileMapPhysics;
	private ShapeActor shape;
	private Float initDragX;
	private Float initDragY;
	
	public PhysicShapeListener(ShapeActor shape, TileMapPhysics tileMapPhysics) {
	
		this.shape = shape;
		this.tileMapPhysics = tileMapPhysics;
	}
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

		this.initDragX = null;
		this.initDragY = null;
		Cursors.ignore = false;
		Cursors.set(CursorType.DEFAULT);
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		
		if (this.initDragX == null) this.initDragX = x;
		else {
			int incX = (int) (x - this.initDragX);
			if (x < BORDER_WIDTH) {
				this.tileMapPhysics.addPadding(0, 0, -incX, 0);
			} else if (x > this.shape.getWidth() - BORDER_WIDTH) {
				this.tileMapPhysics.addPadding(0, incX, 0, 0);
			}
			this.initDragX = x;
		}
		
		if (this.initDragY == null) this.initDragY = y;
		else {
			int incY = (int) (y - this.initDragY);
			if (y < BORDER_WIDTH) {
				this.tileMapPhysics.addPadding(0, 0, 0, -incY);
			} else if (y > this.shape.getHeight() - BORDER_WIDTH) {
				this.tileMapPhysics.addPadding(incY, 0, 0, 0);
			}
			this.initDragY = y;
		}
		
	}

	@Override
	public boolean mouseMoved (InputEvent event, float x, float y) {
		
		if ((x < BORDER_WIDTH) || (x > this.shape.getWidth() - BORDER_WIDTH)) {
			Cursors.setAndLock(CursorType.HORIZONTAL_RESIZE);
		
		} else if ((y < BORDER_WIDTH) || (y > this.shape.getHeight() - BORDER_WIDTH)) {
			Cursors.setAndLock(CursorType.VERTICAL_RESIZE);
		
		} else {
			Cursors.ignore = false;
			Cursors.set(CursorType.DEFAULT);
		}
		
		return true;
	}
	
	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {

	}

	@Override
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {

		Cursors.ignore = false;
		Cursors.set(CursorType.DEFAULT);
	}
}