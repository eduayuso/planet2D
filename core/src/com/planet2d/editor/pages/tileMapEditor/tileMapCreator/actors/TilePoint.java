package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.listeners.TilePointListener;

public class TilePoint extends TileShapeActor {
	
	private static float POINT_WIDTH = 10;
	
	public Float initX;
	public Float initY;
	
	public Tile leftTile;
	public Tile rightTile;
	public Tile topTile;
	public Tile bottomTile;
	
	public Tile leftTile2;
	public Tile rightTile2;
	public Tile topTile2;
	public Tile bottomTile2;

	public TilePoint(TileMapGrid tileMapGrid, int row, int col, TileCell leftCell, TileCell rightCell, TileCell topCell, TileCell bottomCell) {
		
		super(tileMapGrid, row, col, 0, 0, POINT_WIDTH, POINT_WIDTH);
		this.shape = ShapeType.Filled;
		if (leftCell != null) {
			this.leftTile = leftCell.tile;
		}
		if (rightCell != null) {
			this.rightTile = rightCell.tile;
		}
		if (topCell != null) {
			this.topTile = topCell.tile;
		}
		if (bottomCell != null) {
			this.bottomTile = bottomCell.tile;
		}
		
		this.addListener(new TilePointListener(this));
	}
	
	public void dragH(float x) {

		if (this.initX == null) this.initX = this.getX();
		
		this.moveBy(x, 0);
		
		if (this.topTile.type == TileType.RIGHT) {
			
			if (this.getX() < this.initX) {
				this.setX(this.initX);
				return;
			}
			
			if (this.isSidesStretchDeform()) {
				this.topTile.stretchBottomRight(x, 0);
				this.bottomTile.stretchTopRight(x, 0);
			} else {
				this.topTile.skewBottomHorizontal(x);
				this.bottomTile.skewTopHorizontal(x);
			}
			
		} else if (this.topTile.type == TileType.LEFT) {
			
			if (this.getX() > this.initX) {
				this.setX(this.initX);
				return;
			}
			
			if (this.isSidesStretchDeform()) {
				this.topTile.stretchBottomLeft(x, 0);
				this.bottomTile.stretchTopLeft(x, 0);
			} else {
				this.topTile.skewBottomHorizontal(x);
				this.bottomTile.skewTopHorizontal(x);
			}
		}
	}
	
	public void dragV(float y) {

		if (this.initY == null) this.initY = this.getY();
		
		this.moveBy(0, y);
		
		if (this.leftTile.type == TileType.TOP) {
			
			if (this.getY() < this.initY) {
				this.setY(this.initY);
				return;
			}
			
			if (this.isTopStretchDeform()) {
				this.leftTile.stretchTopRight(0, y);
				this.rightTile.stretchTopLeft(0, y);
			} else {
				this.leftTile.skewRightVertical(y);
				this.rightTile.skewLeftVertical(y);
			}
			
		} else if (this.leftTile.type == TileType.BOTTOM) {
			
			if (this.getY() > this.initY) {
				this.setY(this.initY);
				return;
			}
			if (this.isBottomStretchDeform()) {
				this.leftTile.stretchBottomRight(0, y);
				this.rightTile.stretchBottomLeft(0, y);
			} else {
				this.leftTile.skewRightVertical(y);
				this.rightTile.skewLeftVertical(y);
			}
		}
	}
	
	@Override
	public boolean remove() {
		
		return super.remove();
	}
	
	private boolean isTopStretchDeform() {

		return this.tileMapGrid.tileSetConfig.getTopMeshDeformType().equals("stretch");
	}
	
	private boolean isBottomStretchDeform() {

		return this.tileMapGrid.tileSetConfig.getBottomMeshDeformType().equals("stretch");
	}
	
	private boolean isSidesStretchDeform() {

		return this.tileMapGrid.tileSetConfig.getSidesMeshDeformType().equals("stretch");
	}
}
