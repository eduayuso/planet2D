package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.ArrayMap;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.listeners.TileCellListener;

public class TileCell extends TileShapeActor {

	public Tile tile;
	public int num;
	
	public ArrayMap<TileType, TileCell> neighboursCells;
	public int tilesConections; // if it is zero and this not contains tile, remove
	public boolean toGenerateTile;
	public boolean toDeleteTile;
	
	public TileCell(TileMapGrid tileMapGrid, int row, int col, int num) {
		
		super(tileMapGrid, row, col, 0, 0, TileMapGrid.TILE_WIDTH, TileMapGrid.TILE_HEIGHT);
		this.color = Color.WHITE;
		this.num = num;
		this.neighboursCells = new ArrayMap<TileType, TileCell>(TileType.class, TileCell.class);
		this.addListener(new TileCellListener(this));
	}

	public void addTile(Tile tile) {
		
		if (this.tile == null) {
			this.tile = tile;
			
		} else {
			
			this.tile.remove();
			Tile newTile = null;
			if (this.tilesFussionCondition(this.tile, tile)) {
				newTile = this.replaceOrFussion(this.tile, tile);
			} else {
				newTile = tile;
			}
			this.tile = newTile;
		}
		
		this.configTile();
	}
	
	private boolean tilesFussionCondition(Tile tile1, Tile tile2) {

		return tile2.type != TileType.CENTER && tile1.type != tile2.type && !Tile.isCorner(tile1.type);
	}

	private Tile replaceOrFussion(Tile tile1, Tile tile2) {

	/*	if (tile1.type == TileType.TOP) tile1 = this.createSideTopTile(tile1, tile2);
		else if (tile2.type == TileType.TOP) tile2 = this.createSideTopTile(tile2, tile1);*/
		
		tile1.fussionWith(tile2);
		return tile1;
	}
	
	@Override
	public boolean remove() {
		
	/*	for (TileCell cell: this.neighboursCells) cell.superRemove();
		this.neighboursCells.clear();*/
		return super.remove();
	}

	private Tile createSideTopTile(Tile tile1, Tile tile2) {

		TileType topType = null;
		if (tile2.type == TileType.LEFT) topType = TileType.RIGHT_TOP;
		else topType = TileType.LEFT_TOP;
		
		return this.tileMapGrid.createTile(this, tile1.id, topType);
	}
	
	private void configTile() {
		
		if (this.tile.getParent() == null) this.tile.addToParent(this.getParent());
		
		if (Tile.isTop(this.tile.type)) this.tile.toFront();
		else this.tile.toBack();
		this.tile.setTouchable(Touchable.disabled);
		this.toFront();
		
		if (this.tile.auxTile != null) {
			if (Tile.isTop(this.tile.auxTile.type)) this.tile.auxTile.toFront();
			else this.tile.auxTile.toBack();
			this.tile.auxTile.setTouchable(Touchable.disabled);
		}
		
		this.setTilePosition(this.tile);
	}

	private void setTilePosition(Tile tile) {
		
		Vector2 defaultPos = tile.getRelativePositionInCell(this);
		Vector2 offset = this.tileMapGrid.getTileOffset(tile.id, tile.type);
		float x = this.getX() + defaultPos.x + offset.x;
		float y = this.getY() + defaultPos.y + offset.y;
		tile.setPosition(x, y);

		if (tile.auxTile != null) {
			this.setTilePosition(tile.auxTile);
		}
	}

	private void generateTile() {
		
		if (this.isEmpty()) {
			
			this.tileMapGrid.addTile(this, TileType.CENTER, this.num);
		}
		
		this.toGenerateTile = false;
		this.tileMapGrid.updateBounds();
	}
	
	private void deleteTile() {
		
		if (this.tile != null && this.tile.type == TileType.CENTER) {

			/*
			 * DELETE CENTER TILE
			 */
			this.tile.remove();
			this.tile = null;
			
			/*
			 * CLEAR BORDER AROUND (IN NEIGHBOURS)
			 */
			for (TileCell neighbour: this.neighboursCells.values) {
				if (neighbour == null) continue;
				neighbour.tilesConections--;
				if (neighbour.tile != null && !neighbour.hasCenterTile() && !neighbour.isFalseCorner(this)) {
					if (neighbour.tile.isL()) {
						this.removeBorderTileL(neighbour);
					} else {
						neighbour.tile.remove();
						neighbour.tile = null;
					}
				}
			}
			
			/*
			 * NEIGHBOUR CENTER TILES REGENERATE BORDERS
			 */
			for (TileCell neighbour: this.neighboursCells.values) {
				if (neighbour == null) continue;
				if (neighbour.hasCenterTile()) {
					this.tileMapGrid.regenerateBorders(neighbour);
				}
			}
			
			/*
			 * REMOVE POINTS AROUND
			 */
			this.tileMapGrid.checkPointsAroundDeletedCell(this);
		}
		
		this.toDeleteTile = false;
		this.tileMapGrid.updateBounds();
	}
	
	// Remove tile or tileAux
	private void removeBorderTileL(TileCell neighbour) {

		if ((this.row == neighbour.row) && (neighbour.tile.getHeight() == this.getHeight()) ||
			(this.col == neighbour.col) && (neighbour.tile.getHeight() != this.getHeight())) {
			Tile mainTile = neighbour.tile;
			neighbour.tile = mainTile.auxTile;
			mainTile.auxTile = null;
			mainTile.remove();
		} else {
			neighbour.tile.auxTile.remove();
			neighbour.tile.auxTile = null;
		}
	}

	private boolean isFalseCorner(TileCell tileCell) {

		return !Tile.isCorner(this.tile.type) && tileCell.row != this.row && tileCell.col != this.col;
	}

	private boolean hasCenterTile() {

		return this.tile != null && this.tile.type == TileType.CENTER;
	}

	@Override
	public boolean isEmpty() {

		return this.tile == null || this.tile.type != TileType.CENTER;
	}
	
	public void incNeighboursTileConnections() {

		for (TileCell neighbour: this.neighboursCells.values) {
			if (neighbour == null) continue;
			neighbour.tilesConections++;
		}
	}
	
	public void addNeighbour(TileType type, TileCell cell) {

		this.neighboursCells.put(type, cell);
		cell.tilesConections++;
	}
	
	@Override
	public void act(float delta) {

		if (this.isEmpty() && this.tilesConections == 0) {
			this.tileMapGrid.removeCell(this);
		
		} else {
			if (this.toGenerateTile) this.generateTile();
			else if (this.toDeleteTile) this.deleteTile();
		}
	}
	
	@Override
	public void render(Batch batch, float parentAlpha) {
		
		if (!this.tileMapGrid.physics.isVisible()) super.render(batch, parentAlpha);
	}

	public boolean canRegenerate() {

		return this.tile == null || this.tile.auxTile == null;
	}

	public boolean hasTile(TileType type) {

		return this.tile != null && this.tile.type == type || this.tile.auxTile != null && this.tile.auxTile.type == type;
	}

	public static boolean isCenter(TileCell cell) {

		return cell != null && cell.tile != null && cell.tile.type == TileType.CENTER;
	}
}
