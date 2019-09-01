package com.planet2d.editor.pages.tileMapEditor.tilesTemplate;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileConfig;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;
import com.planet2d.engine.actors.tileMaps.Tile;

public class TileSetTemplate extends Group {
	
	public TileSetConfig tileSet;
	public TileGroupTemplate blocksGroup1;
	public TileGroupTemplate blocksGroup2;
	public TileGroupTemplate blocksGroup3;
	public TileGroupTemplate blocksGroup4;
	
	/*public TileGroupTemplateL blocksGroup1L;
	public TileGroupTemplateL blocksGroup2L;
	public TileGroupTemplateL blocksGroup3L;
	public TileGroupTemplateL blocksGroup4L;*/
	
	private final static int INIT_X = 98;
	private float group1X;
	private float group2X;
	private float group3X;
	private float group4X;
	
	public Array<Tile> tiles;
	public Tile draggedTile;
	public TileShape shapeToDropTile;
	
	public TileSetTemplate(TileSetConfig tileSet) {
		
		super();
		
		this.setSize(Editor.currentPage.getWidth()*0.8f, 768);
		this.tileSet = tileSet;
		this.tileSet.template = this;
		this.tiles = new Array<Tile>();
		
		this.blocksGroup1 = this.createBlocksGroup(1);
		this.blocksGroup2 = this.createBlocksGroup(2);
		this.blocksGroup3 = this.createBlocksGroup(3);
		this.blocksGroup4 = this.createBlocksGroup(4);
		
	/*	this.blocksGroup1L = this.createBlocksGroupL(1);
		this.blocksGroup2L = this.createBlocksGroupL(2);
		this.blocksGroup3L = this.createBlocksGroupL(3);
		this.blocksGroup4L = this.createBlocksGroupL(4);*/
		
		this.setGroupPositions();
		
		this.blocksGroup1.setVisible(false);
		this.blocksGroup2.setVisible(false);
		this.blocksGroup3.setVisible(false);
		this.blocksGroup4.setVisible(false);
		
		/*this.blocksGroup1L.setVisible(false);
		this.blocksGroup2L.setVisible(false);
		this.blocksGroup3L.setVisible(false);
		this.blocksGroup4L.setVisible(false);*/
		
		this.addActor(this.blocksGroup1);
		this.addActor(this.blocksGroup2);
		this.addActor(this.blocksGroup3);
		this.addActor(this.blocksGroup4);
		
	/*	this.addActor(this.blocksGroup1L);
		this.addActor(this.blocksGroup2L);
		this.addActor(this.blocksGroup3L);
		this.addActor(this.blocksGroup4L);*/
		
		this.create();
	}
	
	private void setGroupPositions() {

		float y = this.getHeight() - this.blocksGroup1.getHeight() - 58;
		this.blocksGroup1.setPosition(INIT_X, y);
		this.blocksGroup2.setPosition(INIT_X + this.blocksGroup1.getX() + this.blocksGroup1.getWidth(), y);
		this.blocksGroup3.setPosition(INIT_X + this.blocksGroup2.getX() + this.blocksGroup1.getWidth(), y);
		this.blocksGroup4.setPosition(INIT_X + this.blocksGroup3.getX() + this.blocksGroup1.getWidth(), y);
		this.group1X = this.blocksGroup1.getX();
		this.group2X = this.blocksGroup2.getX();
		this.group3X = this.blocksGroup3.getX();
		this.group4X = this.blocksGroup4.getX();
		
	/*	float ly = this.blocksGroup1.getY() - this.blocksGroup1L.getHeight() - 128;
		float ly2 = ly - this.blocksGroup1L.getHeight() - 128;
		this.blocksGroup1L.setPosition((this.blocksGroup1.getX() + this.blocksGroup2.getX())/2f, ly);
		this.blocksGroup2L.setPosition((this.blocksGroup3.getX() + this.blocksGroup4.getX())/2f, ly);
		this.blocksGroup3L.setPosition(this.blocksGroup1L.getX(), ly2);
		this.blocksGroup4L.setPosition(this.blocksGroup2L.getX(), ly2);*/
	}

	private TileGroupTemplate createBlocksGroup(int i) {

		TileGroupTemplate group = new TileGroupTemplate(this, i);
		return group;
	}
	
	private TileGroupTemplateL createBlocksGroupL(int i) {

		TileGroupTemplateL group = new TileGroupTemplateL(this, i);
		return group;
	}

	private void create() {
		
		this.showPatternGroups();
		
		this.blocksGroup1.addTiles();
		this.blocksGroup2.addTiles();
		this.blocksGroup3.addTiles();
		this.blocksGroup4.addTiles();
		
		/*this.blocksGroup1L.addTiles();
		this.blocksGroup2L.addTiles();
		this.blocksGroup3L.addTiles();
		this.blocksGroup4L.addTiles();*/
	}

	public void showPatternGroups() {

		if (this.tileSet.pattern.equals("1x1")) {

			this.blocksGroup1.setVisible(true);
			this.blocksGroup2.setVisible(false);
			this.blocksGroup3.setVisible(false);
			this.blocksGroup4.setVisible(false);
		/*	this.blocksGroup1L.setVisible(true);
			this.blocksGroup2L.setVisible(false);
			this.blocksGroup3L.setVisible(false);
			this.blocksGroup4L.setVisible(false);*/
			
			this.blocksGroup1.setX(this.group2X + (this.group3X - this.group2X)/2);
			
		} else if (this.tileSet.pattern.equals("2x1")) {
			
			this.blocksGroup1.setVisible(true);
			this.blocksGroup2.setVisible(true);
			this.blocksGroup3.setVisible(false);
			this.blocksGroup4.setVisible(false);
			/*this.blocksGroup1L.setVisible(true);
			this.blocksGroup2L.setVisible(true);
			this.blocksGroup3L.setVisible(false);
			this.blocksGroup4L.setVisible(false);*/
			
			this.blocksGroup1.setX(this.group2X);
			this.blocksGroup2.setX(this.group3X);
			
		} else if (this.tileSet.pattern.equals("1x2")) {
			
			this.blocksGroup1.setVisible(true);
			this.blocksGroup2.setVisible(false);
			this.blocksGroup3.setVisible(true);
			this.blocksGroup4.setVisible(false);
		/*	this.blocksGroup1L.setVisible(true);
			this.blocksGroup2L.setVisible(false);
			this.blocksGroup3L.setVisible(true);
			this.blocksGroup4L.setVisible(false);*/
			
			this.blocksGroup1.setX(this.group2X);
			this.blocksGroup3.setX(this.group3X);
			
		} else if (this.tileSet.pattern.equals("2x2")) {
			
			this.blocksGroup1.setVisible(true);
			this.blocksGroup2.setVisible(true);
			this.blocksGroup3.setVisible(true);
			this.blocksGroup4.setVisible(true);
			/*this.blocksGroup1L.setVisible(true);
			this.blocksGroup2L.setVisible(true);
			this.blocksGroup3L.setVisible(true);
			this.blocksGroup4L.setVisible(true);*/
			
			this.blocksGroup1.setX(this.group1X);
			this.blocksGroup2.setX(this.group2X);
			this.blocksGroup3.setX(this.group3X);
			this.blocksGroup4.setX(this.group4X);
		}
	}

	public void addTile() {

		this.draggedTile.remove();
		
		if (this.shapeToDropTile == null) {
			
			this.draggedTile = null;
			
		} else {
		
			this.draggedTile.addAction(Actions.alpha(1f));
			this.tiles.add(this.draggedTile);
			this.shapeToDropTile.addTile(this.draggedTile);
			
			TileType type = this.shapeToDropTile.type;
			int id = this.shapeToDropTile.id;
			TileConfig tileConfig = new TileConfig(type, id, this.draggedTile.fileName, 0, 0);
			tileConfig.tile = this.draggedTile;
			this.tileSet.tiles.add(tileConfig);
			
			this.tileSet.template.draggedTile = null;
		}
	}
	
	public void removeTile(Tile draggedTile) {
		
		this.tiles.removeValue(draggedTile, true);
		this.removeActor(draggedTile);
	}

	public void selectTileShape(Tile tile) {

		this.selectTileShape(tile, true);
	}

	public void unselectTileShape(Tile tile) {
		
		this.selectTileShape(tile, false);
	}
	
	private void selectTileShape(Tile tile, boolean set) {

		for (Actor group: this.getChildren()) {
			if (group instanceof TileGroupTemplate) {
				for (Actor a: ((TileGroupTemplate)group).getChildren()) {
					if (a instanceof TileShape) {
						if (((TileShape)a).tile == tile) {
							((TileShape)a).select(set);
							break;
						}
					}
				}
			}
		}
	}

	public void unselectAllTileShapes() {

		for (Actor group: this.getChildren()) {
			if (group instanceof TileGroupTemplate) {
				for (Actor a: ((TileGroupTemplate)group).getChildren()) {
					if (a instanceof TileShape) {
						((TileShape)a).select(false);
					}
				}
			}
		}
	}
	
	public void resizeGroups(float width, float height) {
		
		this.tileSet.BLOCK_WIDTH = width;
		this.tileSet.BLOCK_HEIGHT = height;
		
		for (Actor group: this.getChildren()) {
			if (group instanceof TileGroupTemplate) {
				((TileGroupTemplate)group).resize();
			}
		}
		
		this.setGroupPositions();
	}

	public void clearSelectedTileShapes() {

		for (Actor group: this.getChildren()) {
			if (group instanceof TileGroupTemplate) {
				((TileGroupTemplate)group).clearSelectedTileShapes();
			}
		}
	}
	
	public void updateTilesOffsetInfo() {
		
		for (Actor group: this.getChildren()) {
			if (group instanceof TileGroupTemplate) {
				((TileGroupTemplate)group).updateTilesOffsetInfo();
			}
		}
	}
}
