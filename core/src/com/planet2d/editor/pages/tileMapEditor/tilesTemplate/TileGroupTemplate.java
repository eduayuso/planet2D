package com.planet2d.editor.pages.tileMapEditor.tilesTemplate;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.ArrayMap;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileConfig;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.actors.tileMaps.Tile;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.ui.Styles.FontType;

public class TileGroupTemplate extends Group {

	protected ArrayMap<TileType, TileShape> tileShapes;
	
	public TileSetTemplate tileSetTemplate;
	protected Label numLabel;
	public int id;
	
	public TileGroupTemplate(TileSetTemplate tileSetTemplate, int id) {
		
		super();
		
		this.id = id;
		this.tileSetTemplate = tileSetTemplate;
		this.tileShapes = new ArrayMap<TileType, TileShape>(TileType.class, TileShape.class);
		
		this.create();
	}

	protected void create() {

		float width = this.tileSetTemplate.tileSet.BLOCK_WIDTH;
		float height = this.tileSetTemplate.tileSet.BLOCK_HEIGHT;
		
		this.createTileShape(TileType.LEFT_TOP, width/3, height/3);
		this.createTileShape(TileType.TOP, width, height/3);
		this.createTileShape(TileType.RIGHT_TOP, width/3, height/3);

		this.createTileShape(TileType.LEFT, width/3, height);
		this.createTileShape(TileType.CENTER, width, height);
		this.createTileShape(TileType.RIGHT, width/3, height);
		
		this.createTileShape(TileType.LEFT_BOTTOM, width/3, height/3);
		this.createTileShape(TileType.BOTTOM, width, height/3);
		this.createTileShape(TileType.RIGHT_BOTTOM, width/3, height/3);
		
		this.numLabel = new Label(this.id+"", FontType.LARGE);
		this.numLabel.setTouchable(Touchable.disabled);
		
		this.setShapesPositions();
		
		this.setSize(this.tileShapes.get(TileType.LEFT_TOP).getWidth()*2 + this.tileShapes.get(TileType.TOP).getWidth(), 
					 this.tileShapes.get(TileType.LEFT_TOP).getHeight()*2 + this.tileShapes.get(TileType.CENTER).getHeight());
		
		for (TileShape shape: this.tileShapes.values) {
			if (shape != null) this.addActor(shape);
		}
		
		this.addActor(this.numLabel);
	}

	protected void createTileShape(TileType type, float width, float height) {

		this.tileShapes.put(type, new TileShape(type, this, 0, 0, width, height));		
	}

	public void addTiles() {
		
		for (TileConfig tileConfig: this.tileSetTemplate.tileSet.tiles) {

			if (tileConfig.id == this.id) {
				
				String path = Config.gamePath + "/textures/tileMaps/" + this.tileSetTemplate.tileSet.tileMapProject.name + "/"+ tileConfig.file;
				Tile tile = new Tile(tileConfig.file);
				tile.create(path);
				tileConfig.tile = tile;

				this.tileSetTemplate.tiles.add(tile);
				if (this.tileShapes.get(tileConfig.type) != null) {
					this.tileShapes.get(tileConfig.type).addTile(tile, tileConfig.offsetX, tileConfig.offsetY);
				}
			}
		}
	}

	public void setShapesPositions() {

		float topY = this.tileShapes.get(TileType.CENTER).getHeight();
		this.tileShapes.get(TileType.LEFT_TOP).setPosition(- this.tileShapes.get(TileType.LEFT_TOP).getWidth(), topY);
		this.tileShapes.get(TileType.TOP).setPosition(0, topY);
		this.tileShapes.get(TileType.RIGHT_TOP).setPosition(tileShapes.get(TileType.CENTER).getWidth(), topY);
		
		this.tileShapes.get(TileType.LEFT).setPosition(-this.tileShapes.get(TileType.LEFT).getWidth(), 0);
		this.tileShapes.get(TileType.RIGHT).setPosition(this.tileShapes.get(TileType.CENTER).getWidth(), 0);
		
		this.tileShapes.get(TileType.LEFT_BOTTOM).setPosition(-this.tileShapes.get(TileType.LEFT_BOTTOM).getWidth(), -this.tileShapes.get(TileType.LEFT_BOTTOM).getHeight());
		this.tileShapes.get(TileType.BOTTOM).setPosition(0, -this.tileShapes.get(TileType.BOTTOM).getHeight());
		this.tileShapes.get(TileType.RIGHT_BOTTOM).setPosition(this.tileShapes.get(TileType.CENTER).getWidth(), -this.tileShapes.get(TileType.RIGHT_BOTTOM).getHeight());
		
		this.numLabel.setPosition(this.tileShapes.get(TileType.CENTER).getX() + this.tileShapes.get(TileType.CENTER).getWidth()/2f - this.numLabel.getWidth()/2f,
								  this.tileShapes.get(TileType.CENTER).getY() + this.tileShapes.get(TileType.CENTER).getHeight()/2f - this.numLabel.getHeight()/2f);
		
	}

	public void resize() {
		
		float width = this.tileSetTemplate.tileSet.BLOCK_WIDTH;
		float height = this.tileSetTemplate.tileSet.BLOCK_HEIGHT;
		
		this.tileShapes.get(TileType.LEFT_TOP).setSize(width/3, height/3);
		this.tileShapes.get(TileType.TOP).setSize(width, height/3);
		this.tileShapes.get(TileType.RIGHT_TOP).setSize(width/3, height/3);

		this.tileShapes.get(TileType.LEFT).setSize(width/3, height);
		this.tileShapes.get(TileType.CENTER).setSize(width, height);
		this.tileShapes.get(TileType.RIGHT).setSize(width/3, height);
		
		this.tileShapes.get(TileType.LEFT_BOTTOM).setSize(width/3, height/3);
		this.tileShapes.get(TileType.BOTTOM).setSize(width, height/3);
		this.tileShapes.get(TileType.RIGHT_BOTTOM).setSize(width/3, height/3);
		
		this.setShapesPositions();
		
		this.setSize(this.tileShapes.get(TileType.LEFT_TOP).getWidth()*2 + this.tileShapes.get(TileType.TOP).getWidth(), 
				 	 this.tileShapes.get(TileType.LEFT_TOP).getHeight()*2 + this.tileShapes.get(TileType.CENTER).getHeight());
	}

	public void clearSelectedTileShapes() {

		for (TileShape shape: this.tileShapes.values) {
				
			if (shape != null && shape.selected) {
			
				this.tileSetTemplate.tileSet.removeTile(shape.tile);
				shape.select(false);
				shape.tile = null;
			}
		}
	}

	public void updateTilesOffsetInfo() {
		
		for (TileConfig tileConfig: this.tileSetTemplate.tileSet.tiles) {
			
			for (TileShape shape: this.tileShapes.values) {
				
				if (shape != null && shape.tile != null && shape.tile == tileConfig.tile) {
					
					tileConfig.offsetX = shape.tile.getX() - shape.initX;
					tileConfig.offsetY = shape.tile.getY() - shape.initY;
				}
			}
		}
	}
}