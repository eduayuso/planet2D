package com.planet2d.editor.pages.tileMapEditor.tilesTemplate;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.actors.EditorDebug;
import com.planet2d.editor.listeners.ActorListener;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.engine.actors.ShapeActor;
import com.planet2d.engine.actors.tileMaps.Tile;

public class TileShape extends ShapeActor {

	public TileType type;
	public int id;
	public Tile tile;
	private TileGroupTemplate tileGroupTemplate;
	public boolean selected;
	public float initX;
	public float initY;
	
	public TileShape(TileType type, TileGroupTemplate tileGroupTemplate, float x, float y, float width, float height) {
		
		super(x, y, width, height);
		this.type = type;
		this.id = tileGroupTemplate.id;
		this.tileGroupTemplate = tileGroupTemplate;
		this.addListener(new TileShapeListener(this.tileGroupTemplate.tileSetTemplate));
	}

	public void addTile(Tile tile) {
		
		this.addTile(tile, 0, 0);
	}
	
	public void addTile(Tile tile, float offsetX, float offsetY) {
		
		this.tile = tile;
		this.setTilePosition(offsetX, offsetY);
		this.tileGroupTemplate.addActor(this.tile);
		this.tile.editorDebug = new EditorDebug(this.tile);
		this.tile.addListener(new ActorListener(this.tile));
		this.tile.setTouchable(Touchable.enabled);
		this.shape = ShapeType.Line;
		
		if (this.type == TileType.CENTER) {
			if (this.tile.getWidth() != this.getWidth() || this.tile.getHeight() != this.getHeight()) {
				this.tileGroupTemplate.tileSetTemplate.resizeGroups(this.tile.getWidth(), this.tile.getHeight());
			}
		}
	}
	
	private void setTilePosition(float offsetX, float offsetY) {

		float x = this.getX();
		float y = this.getY();
		
		if (this.type == TileType.LEFT_TOP || this.type == TileType.LEFT || this.type == TileType.LEFT_BOTTOM) {
			x += this.getWidth() - this.tile.getWidth();
		}
		
		if (this.type == TileType.LEFT_BOTTOM || this.type == TileType.BOTTOM || this.type == TileType.RIGHT_BOTTOM) {
			y += this.getHeight() - this.tile.getHeight();
		}
		
		this.initX = x;
		this.initY = y;
		
		this.tile.setPosition(x + offsetX, y + offsetY);
	}

	public class TileShapeListener extends InputListener {

		public TileSetTemplate tileSetTemplate;
		
		public TileShapeListener(TileSetTemplate tileSetTemplate) {
			
			this.tileSetTemplate = tileSetTemplate;
			color = Color.WHITE;
		}

		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			
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

			if (tile != null) return;
			
			this.tileSetTemplate.shapeToDropTile = TileShape.this;
			if (this.tileSetTemplate.draggedTile != null) {
				shape = ShapeType.Filled;
			}
		}

		@Override
		public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {

			if (tile != null) return;
			
			tileSetTemplate.shapeToDropTile = null;
			if (this.tileSetTemplate.draggedTile != null) {
				shape = ShapeType.Line;
			}
		}
	}

	public void select(boolean set) {
		
		this.selected = set;
		if (this.selected) this.shape = ShapeType.Filled;
		else this.shape = ShapeType.Line;
	}
}