package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.TileCell;
import com.planet2d.engine.actors.CompositeActor;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.ShapeActor;

public class TileMapPhysics extends CompositeActor<ShapeActor> {

	private TileMapGrid tileMapGrid;
	private Array<ShapeActor> shapes;
	private int topPadding;
	private int rightPadding;
	private int bottomPadding;
	private int leftPadding;
	
	public TileMapPhysics(TileMapGrid tileMapGrid) {
		
		super("tileMapPhysics");
		this.tileMapGrid = tileMapGrid;
		this.setPosition(this.tileMapGrid.getX(), this.tileMapGrid.getY());
		this.shapes = new Array<ShapeActor>();
		String padding = this.tileMapGrid.tileSetConfig.physicsPadding;
		this.topPadding = Integer.parseInt(padding.split("-")[0]);
		this.rightPadding = Integer.parseInt(padding.split("-")[1]);
		this.bottomPadding = Integer.parseInt(padding.split("-")[2]);
		this.leftPadding = Integer.parseInt(padding.split("-")[3]);
	}

	public void generate() {

		this.clear();
		this.container.clear();
		this.shapes.clear();
		if (this.tileMapGrid.tileSetConfig.physicsShape.equals("polygon")) {
			try {
				this.createPolygons();
			} catch(Exception e) {
				Gdx.app.error("Generate physics", e.toString());
			}
		}
	}

	private void createPolygons() throws Exception {
		
		this.shapes = new Array<ShapeActor>();
		
		/*
		 * OBTENEMOS PRIMERA Y ULTIMA FILA Y COLUMNA
		 */
		int firstRow = 0;
		int lastRow  = 0;
		int firstCol = 0;
		int lastCol  = 0;
		
		for (TileCell cell: this.tileMapGrid.cells.values) {
			
			if (cell == null || cell.isEmpty()) continue;
			
			if (cell.row < firstRow) firstRow = cell.row;
			if (cell.row > lastRow)  lastRow  = cell.row;
			if (cell.col < firstCol) firstCol = cell.col;
			if (cell.col > lastCol)  lastCol  = cell.col;
		}
		
		/*
		 * ITERAMOS SOBRE LAS FILAS Y COLUMNAS
		 */
		for (int r=firstRow; r<=lastRow; r++) {
			
			Float recX = null;
			Float recY = null;
			Float recWidth = null;
			Float recHeight = null;
			
			for (int c=firstCol; c<=lastCol; c++) {
				
				TileCell refCell = this.tileMapGrid.cells.get(new Vector2(r, c));
				
				if (refCell != null && !refCell.isEmpty()) {
				
					if (recWidth == null) {
						
						recX = refCell.getX();
						recY = refCell.getY();
						recWidth  = refCell.getWidth();
						recHeight = refCell.getHeight();
					
					} else {
						
						recWidth += refCell.getWidth();
					}
					
				} else if (recWidth != null) {
					
					ShapeActor shape = this.createPhysicPolygon(recX, recY, recWidth, recHeight);
					if (shape != null && refCell != null) {
						recX = null;
						recY = null;
						recWidth = null;
						recHeight = null;
					}
				}
			}
			
			this.createPhysicPolygon(recX, recY, recWidth, recHeight);
		}
		
		/*
		 * OPTIMIZAMOS LOS RECTANGULOS, UNIENDO LOS QUE TIENEN POSICI�N Y CORRELATIVA, IGUAL X, E IGUAL ANCHURA
		 */
		
		this.optimizeRectangles();
		
		/*
		 * APLICAR PADDING
		 */
		this.applyPadding(this.topPadding, this.rightPadding, this.leftPadding, this.bottomPadding);
	}
	
	private ShapeActor createPhysicPolygon(Float recX, Float recY, Float recWidth, Float recHeight) {
		
		ShapeActor newShape = null;
		
		if (recX != null && recY != null && recWidth != null && recHeight != null) {

			newShape = new ShapeActor(recX, recY, recWidth, recHeight);
			newShape.color = Color.GREEN;
			this.shapes.add(newShape);
			this.add(newShape);
			
			newShape.addListener(new PhysicShapeListener(newShape, this));
		}
		
		return newShape;
	}

	private void optimizeRectangles() {

		boolean checkRects = false;
		
		do {
			
			checkRects = false;
			
			for (int a=0; a<this.shapes.size; a++) {
				
				ShapeActor rectA = this.shapes.get(a);
				
				for (int b=0; b<this.shapes.size; b++) {
					
					ShapeActor rectB = this.shapes.get(b);
					
					if ((rectA != rectB) &&
						(rectA.getX() == rectB.getX()) && 
						(rectA.getY()+rectA.getHeight() == rectB.getY()) &&
						(rectA.getWidth() == rectB.getWidth())) {
						this.createPhysicPolygon(rectA.getX(), rectA.getY(), rectA.getWidth(), rectA.getHeight() + rectB.getHeight());
						this.remove(rectA);
						this.remove(rectB);
						this.shapes.removeValue(rectA, true);
						this.shapes.removeValue(rectB, true);
						
						checkRects = true;
						break;
					}
				}
				
				if (checkRects) break;
			}
			
		} while(checkRects);
	}

	public void createPhysicalLines() {
		
		/*if (this.moving == false) {
		
			this.physicalLines = new ArrayList<PhysicalLine>();
			
			for (Block b: this.blockMap.values()) {
				
				if ((this.isFluid() || this.isBackPlatform()) && (b.upper && !b.getName().contains("up-left") && !b.getName().contains("up-right"))) {
					
					Block refBlock = this.getBlock(b.row+1, b.column);
					
					int x0 = refBlock.getX();
					int y0 = refBlock.getY();
					int x1 = refBlock.getX() + refBlock.getWidth();
					int y1 = y0;
					
					int offsetL = 0;
					
					if (b.getZone().contains("jungle5")) {
						offsetL = -198;
					} else if (b.getZone().contains("temple-of-light3")) {
						offsetL = -8;
					} else if (b.getZone().contains("temple-of-light4")) {
						offsetL = -100;
					} else if (b.getZone().contains("temple-of-light5")) {
						offsetL = -0;
					} else if (b.getZone().contains("underground")) {
						offsetL = 24;
					} else if (b.getZone().equals("dark-fortress")) {
						offsetL = -12;
					} else if (b.getZone().equals("dark-fortress2")) {
						offsetL = 48;
					} else if (b.getZone().contains("norris-elevator")|| b.getZone().contains("colisseum2")) {
						offsetL = -36;
					} else if (b.getZone().contains("final") || b.getZone().contains("valley-junction") || b.getZone().contains("norris-tower3") || b.getZone().contains("cave-of-ice") || b.getZone().contains("ice-castle")|| b.getZone().contains("norris-exterior2")) {
						offsetL = 0;
					} else if (!b.getZone().contains("final") && !b.getZone().contains("valley-junction") && !b.getZone().contains("norris-exterior2") && !b.getZone().contains("norris-elevator")&& !b.getZone().contains("colisseum2") && !b.getZone().contains("sky-land")) {
						offsetL = 56;
					}
					
					if (b.getName().contains("up") && b.getName().contains("left")) {
						x1 = x0 + offsetL; 
					} else if (b.getName().contains("up") && b.getName().contains("right")) {
						x0 = x1 - offsetL;
					}
					
					if (this.getZone().equals("river")) {
						y0 -= 98;
						y1 = y0;
					}
					
					this.physicalLines.add(new PhysicalLine(x0, y0, x1, y1));
				
				} else if (this.isNormalPlatform() && b.inner) {
					
					int x0 = b.getX();
					int y0 = b.getY();
					int x1 = b.getX() + b.getWidth();
					
					if (this.getZone().equals("hive")) {
						y0 += b.getHeight()*3/4;
					} else if (this.getZone().equals("dungeon")|| this.getZone().contains("cave-of-fire3") || this.getZone().contains("colisseum4")) {
						y0 += 60;
					} else if (this.getZone().equals("dark-fortress")) {
						y0 += 28;
					} else if (this.getZone().equals("dark-fortress2")) {
						y0 += 33;
					} else if (this.getZone().contains("cave-of-ice") || this.getZone().contains("ground") || 
							   this.getZone().equals("north-land") || this.getZone().equals("cave-of-fire") || this.getZone().equals("island") ) {
						y0 += 56;
					} else if (this.getZone().equals("forest") || this.getZone().equals("norris-tower")) {
						y0 += 56;
					} else if (this.getZone().equals("colisseum") || this.getZone().equals("forest2")|| this.getZone().equals("sky-land3")|| this.getZone().equals("temple-of-light6")) {
						y0 += 38;
					} else if (this.getZone().equals("final") || this.getZone().equals("valley-junction") || this.getZone().contains("norris-tower3") || this.getZone().equals("ice-castle") || this.getZone().contains("norris-exterior2") || this.getZone().contains("norris-elevator") || this.getZone().contains("colisseum2")) {
						if (this.getZone().contains("norris-elevator") || this.getZone().contains("colisseum2")) y0 += this.getOffsetYPhysics() + 3;
						else y0 += b.getHeight();
					} else if (this.getZone().equals("sky-land2") || this.getZone().equals("volcanic-island2") || 
							   this.getZone().equals("temple-of-light2") || this.getZone().equals("temple-of-night2") || 
							   this.getZone().equals("north-land2") || this.getZone().equals("dark-castle")) {
						y0 += 89;
					} else if (this.getZone().equals("temple-of-light3") || this.getZone().equals("temple-of-light4")) {
						y0 += 189;
					} else if (this.getZone().equals("ice-castle5")) {
						y0 += 89;
					}
					int y1 = y0;
					
					this.physicalLines.add(new PhysicalLine(x0, y0, x1, y1));
				}
			}
		
			boolean checkLines = false;
			
			do {
				
				checkLines = false;
			
				for (PhysicalLine pl1: this.physicalLines) {
					
					for (PhysicalLine pl2: this.physicalLines) {
						
						if (pl1 != null && pl2 != null && pl1 != pl2 && pl1.getX1() == pl2.getX0() && pl1.getY() == pl2.getY()) {
							
							int	x0 = pl1.getX0();
							int	x1 = pl2.getX1();
							int y  = pl1.getY();
						
							this.physicalLines.add(new PhysicalLine(x0, y, x1, y));
							this.physicalLines.remove(pl1);
							this.physicalLines.remove(pl2);
							pl1.remove();
							pl2.remove();
							
							checkLines = true;
							break;
						}
					}
					
					if (checkLines) break;
				}
				
			} while(checkLines);
			
			this.adjustPhysicalLines();
		}
	}*/
	}
	
	private void applyPadding(int top, int right, int left, int bottom) {

		for (ShapeActor shape: this.shapes) {
			
			float x 	 = shape.getX();
			float y  	 = shape.getY();
			float width  = shape.getWidth();
			float height = shape.getHeight();
			
			x -= left;
			width += left + right;
			y -= bottom; 
			height += bottom + top;
			
			shape.setPosition(x, y);
			shape.setSize(width, height);
		}
	}
	
	public void addPadding(int top, int right, int left, int bottom) {
		
		this.topPadding += top;
		this.rightPadding += right;
		this.leftPadding += left;
		this.bottomPadding += bottom;
		this.tileMapGrid.tileSetConfig.physicsPadding = this.topPadding+"-"+this.rightPadding+"-"+this.leftPadding+"-"+this.bottomPadding;
		
		this.applyPadding(top, right, left, bottom);
	}
}
