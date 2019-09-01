package com.planet2d.editor.pages.tileMapEditor.tileMapCreator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileConfig;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TileCell;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TilePoint;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TileShapeActor;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.physics.TileMapPhysics;
import com.planet2d.engine.actors.CompositeActor;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;

public class TileMapGrid extends CompositeActor<TileShapeActor> {

	public static float TILE_WIDTH;
	public static float TILE_HEIGHT;
	public TileSetConfig tileSetConfig;
	private Array<ArrayMap<TileType, TileConfig>> tileSets;
	public ArrayMap<Vector2, TileCell> cells;
	public ArrayMap<Vector2, TilePoint> points;
	public TileMapPhysics physics;
	public TileCell hoveredCell;
	public boolean blockCellCreation;
	public boolean viewPhysics;
	
	public TileMapGrid(TileSetConfig tileSetConfig) {
		
		super(tileSetConfig.name);
		TILE_WIDTH = tileSetConfig.BLOCK_WIDTH;
		TILE_HEIGHT = tileSetConfig.BLOCK_HEIGHT;
		
		this.tileSetConfig = tileSetConfig;
		this.tileSets = new Array<ArrayMap<TileType, TileConfig>>();
		this.configTileSets();
		this.cells = new ArrayMap<Vector2, TileCell>(Vector2.class, TileCell.class);
		this.points = new ArrayMap<Vector2, TilePoint>(Vector2.class, TilePoint.class);
		this.physics = new TileMapPhysics(this);
		this.physics.setVisible(false);
		this.create();
	}

	private void configTileSets() {

		for (int i=0; i<4; i++) this.tileSets.add(new ArrayMap<TileType, TileConfig>());
		
		for (TileConfig tileConfig: this.tileSetConfig.tiles) {
			
			this.tileSets.get(tileConfig.id-1).put(tileConfig.type, tileConfig);
		}
	}
	
	@Override
	public void create() {

		int firstCellNum = 1;
		int firstCellRow = 0;
		int firstCellCol = 0;
		TileCell cell = this.createCell(firstCellRow, firstCellCol, firstCellNum);
		cell.setPosition(TILE_WIDTH, TILE_WIDTH);
		this.addTile(cell, TileType.CENTER, firstCellNum);
		this.updateBounds();
	}
	
	public String getTileFileName(int num, TileType type) {
		
		return this.tileSets.get(num-1).get(type).file;
	}
	
	public Vector2 getTileOffset(int num, TileType type) {
		
		float x = this.tileSets.get(num-1).get(type).offsetX;
		float y = this.tileSets.get(num-1).get(type).offsetY;
		return new Vector2(x, y);
	}
	
	private TileCell createCell(int row, int col, int num) {
		
		TileCell cell = new TileCell(this, row, col, num);
		this.cells.put(new Vector2(row,col), cell);
		this.add(cell);
		
		return cell;
	}
	
	public void addTile(TileCell cell, TileType type, int num) {
		
		/*
		 * CHECK: DON'T PUT BORDER IN A CELL WHERE THERE IS A CENTER TILE
		 */
		if (type != TileType.CENTER && !this.isAllowedToAddBorder(cell, type)) return;
		
		/*
		 * CREATE TILE AND ADD TO CELL
		 */
		Tile tile = this.createTile(cell, num, type);
		if (tile != null) {
			cell.addTile(tile);
		}

		/*
		 * BORDERS AND POINTS
		 */
		if (tile != null && type == TileType.CENTER) {
			
			cell.incNeighboursTileConnections();
		
			/*
			 * CREATE NEIGHBOUR CELLS
			 */
			TileCell leftTopCell		= this.createNeighbourCell(cell, TileType.LEFT_TOP);
			TileCell topCell			= this.createNeighbourCell(cell, TileType.TOP);
			TileCell rightTopCell		= this.createNeighbourCell(cell, TileType.RIGHT_TOP);
			TileCell leftCell			= this.createNeighbourCell(cell, TileType.LEFT);
			TileCell rightCell 			= this.createNeighbourCell(cell, TileType.RIGHT);
			TileCell leftBottomCell 	= this.createNeighbourCell(cell, TileType.LEFT_BOTTOM);
			TileCell bottomCell			= this.createNeighbourCell(cell, TileType.BOTTOM);
			TileCell rightBottomCell 	= this.createNeighbourCell(cell, TileType.RIGHT_BOTTOM);
			
			/*
			 * ADD BORDER TILES
			 */
			
			this.addTile(leftTopCell, TileType.LEFT_TOP, cell.num);
			this.addTile(topCell, TileType.TOP, cell.num);
			this.addTile(rightTopCell, TileType.RIGHT_TOP, cell.num);
			this.addTile(leftCell, TileType.LEFT, cell.num);
			this.addTile(rightCell, TileType.RIGHT, cell.num);
			this.addTile(leftBottomCell, TileType.LEFT_BOTTOM, cell.num);
			this.addTile(bottomCell, TileType.BOTTOM, cell.num);
			this.addTile(rightBottomCell, TileType.RIGHT_BOTTOM, cell.num);
			
			/*
			 * CHECK CELL POINTS
			 */
			this.checkPointsAroundNewCell(cell, leftTopCell, topCell, rightTopCell, leftCell, rightCell, leftBottomCell, bottomCell, rightBottomCell);
		}
	}
	
	public Tile createTile(TileCell cell, int id, TileType type) {

		String fileName = this.getTileFileName(id, type);
		String path = this.getTileSetPath() + "/"+ fileName;
		
		if (cell.tile != null && cell.tile.getName().equals(fileName)) return null;
		
		Tile tile = new Tile(fileName, type, id);
		tile.create(path);
		
		return tile;
	}
	
	public void checkPointsAroundNewCell(TileCell cell, TileCell leftTopCell, TileCell topCell, TileCell rightTopCell, TileCell leftCell, TileCell rightCell, TileCell leftBottomCell, TileCell bottomCell, TileCell rightBottomCell) {
		
		if (topCell.hasTile(TileType.TOP)) {
			if (leftTopCell.hasTile(TileType.TOP)) this.createPoint(topCell, leftTopCell, topCell, null, null);
			if (rightTopCell.hasTile(TileType.TOP)) this.createPoint(rightTopCell, topCell, rightTopCell, null, null);
		
		} else if (topCell.hasTile(TileType.CENTER)) {
			if (leftTopCell.hasTile(TileType.CENTER)) this.removePoint(topCell.row, topCell.col);
			if (rightTopCell.hasTile(TileType.CENTER)) this.removePoint(topCell.row, topCell.col+1);
		}
		
		if (bottomCell.hasTile(TileType.BOTTOM)) {
			if (leftBottomCell.hasTile(TileType.BOTTOM)) this.createPoint(cell, leftBottomCell, bottomCell, null, null);
			if (rightBottomCell.hasTile(TileType.BOTTOM)) this.createPoint(rightCell, bottomCell, rightBottomCell, null, null);
		
		} else if (bottomCell.hasTile(TileType.CENTER)) {
			if (leftBottomCell.hasTile(TileType.CENTER)) this.removePoint(cell.row, bottomCell.col);
			if (rightBottomCell.hasTile(TileType.CENTER)) this.removePoint(cell.row, bottomCell.col+1);
		}
		
		if (leftCell.hasTile(TileType.LEFT)) {
			if (leftTopCell.hasTile(TileType.LEFT)) this.createPoint(topCell, null, null, leftTopCell, leftCell);
			if (leftBottomCell.hasTile(TileType.LEFT)) this.createPoint(cell, null, null, leftCell, leftBottomCell);
		
		} else if (leftCell.hasTile(TileType.CENTER)) {
			if (leftTopCell.hasTile(TileType.CENTER)) this.removePoint(cell.row+1, cell.col);
			if (leftBottomCell.hasTile(TileType.CENTER)) this.removePoint(cell.row, cell.col);
		}
		
		if (rightCell.hasTile(TileType.RIGHT)) {
			if (rightTopCell.hasTile(TileType.RIGHT)) this.createPoint(rightTopCell, null, null, rightTopCell, rightCell);
			if (rightBottomCell.hasTile(TileType.RIGHT)) this.createPoint(rightCell, null, null, rightCell, rightBottomCell);
		
		} else if (rightCell.hasTile(TileType.CENTER)) {
			if (rightTopCell.hasTile(TileType.CENTER)) this.removePoint(rightCell.row+1, rightCell.col);
			if (rightBottomCell.hasTile(TileType.CENTER)) this.removePoint(rightCell.row, rightCell.col);
		}
	}
	
	public void checkPointsAroundDeletedCell(TileCell cell) {

		this.removePoint(cell.row, cell.col);
		this.removePoint(cell.row+1, cell.col);
		this.removePoint(cell.row+1, cell.col+1);
		this.removePoint(cell.row, cell.col+1);
		
		TileCell leftTopCell		= this.cells.get(new Vector2(cell.row+1, cell.col-1));
		TileCell topCell			= this.cells.get(new Vector2(cell.row+1, cell.col));
		TileCell rightTopCell		= this.cells.get(new Vector2(cell.row+1, cell.col+1));
		TileCell leftCell			= this.cells.get(new Vector2(cell.row, cell.col-1));
		TileCell rightCell 			= this.cells.get(new Vector2(cell.row, cell.col+1));
		TileCell leftBottomCell 	= this.cells.get(new Vector2(cell.row-1, cell.col-1));
		TileCell bottomCell			= this.cells.get(new Vector2(cell.row-1, cell.col));
		TileCell rightBottomCell 	= this.cells.get(new Vector2(cell.row-1, cell.col+1));
		
		if (TileCell.isCenter(topCell)) {
			if (TileCell.isCenter(leftTopCell) && !TileCell.isCenter(leftCell)) {
				this.createPoint(topCell, leftCell, cell, null, null);
			}
			if (TileCell.isCenter(rightTopCell) && !TileCell.isCenter(rightCell)) {
				this.createPoint(rightTopCell, cell, rightCell, null, null);
			}
		}
		
		if (TileCell.isCenter(bottomCell)) {
			if (TileCell.isCenter(leftBottomCell) && !TileCell.isCenter(leftCell)) {
				this.createPoint(cell, leftCell, cell, null, null);
			}
			if (TileCell.isCenter(rightBottomCell) && !TileCell.isCenter(rightCell)) {
				this.createPoint(rightCell, cell, rightCell, null, null);
			}
		}
		
		if (TileCell.isCenter(leftCell)) {
			if (TileCell.isCenter(leftTopCell) && !TileCell.isCenter(topCell)) {
				this.createPoint(topCell, null, null, leftTopCell, leftCell);
			}
			if (TileCell.isCenter(leftBottomCell) && !TileCell.isCenter(bottomCell)) {
				this.createPoint(cell, null, null, leftCell, leftBottomCell);
			}
		}
		
		if (TileCell.isCenter(rightCell)) {
			if (TileCell.isCenter(rightTopCell) && !TileCell.isCenter(topCell)) {
				this.createPoint(rightTopCell, null, null, rightTopCell, rightCell);
			}
			if (TileCell.isCenter(rightBottomCell) && !TileCell.isCenter(bottomCell)) {
				this.createPoint(rightCell, null, null, rightCell, rightBottomCell);
			}
		}
	}

	private void createPoint(TileCell refCell, TileCell leftCell, TileCell rightCell, TileCell topCell, TileCell bottomCell) {

		Vector2 vector = new Vector2(refCell.row, refCell.col);
		if (this.points.get(vector) != null) {
			this.removePoint(refCell.row, refCell.col);
		}
		TilePoint point = new TilePoint(this, refCell.row, refCell.col, leftCell, rightCell, topCell, bottomCell);
		point.setPosition(refCell.getX() - point.getWidth()/2f, refCell.getY() - point.getHeight()/2f);
		this.points.put(vector, point);
		this.add(point);
	}
	
	public String getTileSetPath() {

		return Config.gamePath + "/textures/tileMaps/" + this.tileSetConfig.tileMapProject.name;
	}

	private boolean isAllowedToAddBorder(TileCell cell, TileType type) {

		boolean allow = false;
		
		if (cell.tile == null) {
			
			allow = true;
		
		} else if (!Tile.isCorner(type)) {
		
			if (cell.tile.type != TileType.CENTER) allow = true;
		}
		
		return allow;
	}

	private TileCell createNeighbourCell(TileCell refCell, TileType type) {
		
		int num = TileLoopLogic.getCellNum(this.tileSetConfig.pattern, refCell.num, type);
		
		Vector2 coordOffset = this.getCoordOffset(type);
		float incRow = coordOffset.x;
		float incCol = coordOffset.y;
		float newRow = refCell.row+incRow;
		float newCol = refCell.col+incCol;
		
		TileCell neighbour = this.cells.get(new Vector2(newRow, newCol));
		
		if (neighbour == null) {
			neighbour = this.createCell((int)newRow, (int)newCol, num);
			float x = refCell.getX()+refCell.getWidth()*incCol;
			float y = refCell.getY()+refCell.getHeight()*incRow;
			neighbour.setPosition(x, y);
		}
		
		refCell.addNeighbour(type, neighbour);
		
		return neighbour;
	}
	
	private Vector2 getCoordOffset(TileType type) {

		int incRow = 0;
		int incCol = 0;
		
		if (type == TileType.LEFT_TOP) {
			incRow = 1; incCol = -1;
		} else if (type == TileType.TOP) {
			incRow = 1;
		} else if (type == TileType.RIGHT_TOP) {
			incRow = 1; incCol = 1;
		} else if (type == TileType.LEFT) {
			incCol = -1;
		} else if (type == TileType.RIGHT) {
			incCol = 1;
		} else if (type == TileType.LEFT_BOTTOM) {
			incRow = -1; incCol = -1;
		} else if (type == TileType.BOTTOM) {
			incRow = -1;
		} else if (type == TileType.RIGHT_BOTTOM) {
			incRow = -1; incCol = 1;
		}
		
		return new Vector2(incRow, incCol);
	}

	public void regenerateBorders(TileCell cell) {
		
		TileCell leftTopCell = this.getNeighbourCell(cell, TileType.LEFT_TOP);
		TileCell topCell = this.getNeighbourCell(cell, TileType.TOP);
		TileCell rightTopCell = this.getNeighbourCell(cell, TileType.RIGHT_TOP);
		TileCell leftCell = this.getNeighbourCell(cell, TileType.LEFT);
		TileCell rightCell = this.getNeighbourCell(cell, TileType.RIGHT);
		TileCell leftBottomCell = this.getNeighbourCell(cell, TileType.LEFT_BOTTOM);
		TileCell bottomCell = this.getNeighbourCell(cell, TileType.BOTTOM);
		TileCell rightBottomCell = this.getNeighbourCell(cell, TileType.RIGHT_BOTTOM);
		
		if (leftTopCell.canRegenerate()) this.addTile(leftTopCell, TileType.LEFT_TOP, cell.num);
		if (topCell.canRegenerate()) this.addTile(topCell, TileType.TOP, cell.num);
		if (rightTopCell.canRegenerate()) this.addTile(rightTopCell, TileType.RIGHT_TOP, cell.num);
		if (leftCell.canRegenerate()) this.addTile(leftCell, TileType.LEFT, cell.num);
		if (rightCell.canRegenerate()) this.addTile(rightCell, TileType.RIGHT, cell.num);
		if (leftBottomCell.canRegenerate()) this.addTile(leftBottomCell, TileType.LEFT_BOTTOM, cell.num);
		if (bottomCell.canRegenerate()) this.addTile(bottomCell, TileType.BOTTOM, cell.num);
		if (rightBottomCell.canRegenerate()) this.addTile(rightBottomCell, TileType.RIGHT_BOTTOM, cell.num);
	}
	
	private TileCell getNeighbourCell(TileCell cell, TileType type) {

		Vector2 coordOffset = this.getCoordOffset(type);
		return this.cells.get(new Vector2(cell.row + coordOffset.x, cell.col + coordOffset.y));
	}

	public void removeCell(TileCell tileCell) {
		
		this.cells.removeValue(tileCell, true);
		if (tileCell.tile != null) {
			tileCell.tile.setVisible(false);
			tileCell.tile.remove();
			tileCell.tile = null;
		}
				
		tileCell.remove();
	}

	private void removePoint(int row, int col) {
		
		TilePoint point = this.points.get(new Vector2(row, col));
		if (point != null) {
			point.remove();
			this.points.removeValue(point, true);
		}
	}

	public void updateBounds() {
		
		Float minX = null; 
		Float minY = null;
		Float maxX = null;
		Float maxY = null;
		
		for (TileCell cell: this.cells.values) {
			
			if (cell == null) continue;
			
			if (minX == null) minX = cell.getX();
			if (maxX == null) maxX = cell.getX()+cell.getWidth();
			if (minY == null) minY = cell.getY();
			if (maxY == null) maxY = cell.getY()+cell.getHeight();
			
			if (cell.getX() < minX) minX = cell.getX();
			else if (cell.getX()+cell.getWidth() > maxX) maxX = cell.getX()+cell.getWidth();
			if (cell.getY() < minY) minY = cell.getY();
			else if (cell.getY()+cell.getHeight() > maxY) maxY = cell.getY()+cell.getHeight();
		}
		
		this.setBounds(this.getX()+minX, this.getY()+minY, maxX - minX, maxY - minY);
		
		for (TileCell cell: this.cells.values) {
			if (cell == null) continue;
			cell.setPosition(cell.getX() - minX, cell.getY() - minY); 
			if (cell.tile != null) cell.tile.correctPosition(-minX, - minY);
		}
		
		for (TilePoint point: this.points.values) {
			if (point == null) continue;
			point.setPosition(point.getX() - minX, point.getY() - minY);
		}
		
		this.physics.generate();
		
		for (TilePoint p: this.points.values) {
			if (p == null) continue;
			p.toFront();
		}
	}
	
	@Override
	protected void positionChanged() {
		
		super.positionChanged();
		this.physics.setPosition(this.getX(), this.getY());
	}
	
	@Override
	public void render(Batch batch, float parentAlpha) {
		
		super.render(batch, parentAlpha);
		
		this.getParent().addActor(this.physics);
	}
	
	@Override
	public void setEditorEdited(boolean b) {
		
		this.editorDebug.edited = b;
		Cursors.set(CursorType.DEFAULT);
	}
}
