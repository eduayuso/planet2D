package com.planet2d.editor.pages.tileMapEditor.tilesTemplate;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.ui.Styles.FontType;

public class TileGroupTemplateL extends TileGroupTemplate {
	
	public TileGroupTemplateL(TileSetTemplate tileSetTemplate, int id) {
		
		super(tileSetTemplate, id);
	}
	
	@Override
	protected void create() {

		float width = this.tileSetTemplate.tileSet.BLOCK_WIDTH;
		float height = this.tileSetTemplate.tileSet.BLOCK_HEIGHT;
		
		this.createTileShape(TileType.L_LEFT_TOP, width, height/3);
		this.createTileShape(TileType.L_RIGHT_TOP, width, height/3);

		this.createTileShape(TileType.L_LEFT_BOTTOM, width, height/3);
		this.createTileShape(TileType.L_RIGHT_BOTTOM, width, height/3);
		
		this.numLabel = new Label(this.id+"", FontType.LARGE);
		this.numLabel.setTouchable(Touchable.disabled);
		
		this.setShapesPositions();
		
		this.setSize(width*3, height*3);
		
		for (TileShape shape: this.tileShapes.values) {
			if (shape != null) this.addActor(shape);
		}
		
		this.addActor(this.numLabel);
	}
	
	@Override
	public void setShapesPositions() {
		
		float width = this.tileSetTemplate.tileSet.BLOCK_WIDTH;
		float height = this.tileSetTemplate.tileSet.BLOCK_HEIGHT;

		float topY = height;
		this.tileShapes.get(TileType.L_LEFT_TOP).setPosition(-width, height);
		this.tileShapes.get(TileType.L_RIGHT_TOP).setPosition(width, height);
		
		this.tileShapes.get(TileType.L_LEFT_BOTTOM).setPosition(-width, -height/3);
		this.tileShapes.get(TileType.L_RIGHT_BOTTOM).setPosition(width, -height/3);
		
		this.numLabel.setPosition(0, 0);
		this.numLabel.setVisible(false);
	}
	
	@Override
	public void resize() {
		
		float width = this.tileSetTemplate.tileSet.BLOCK_WIDTH;
		float height = this.tileSetTemplate.tileSet.BLOCK_HEIGHT;
		
		this.tileShapes.get(TileType.L_LEFT_TOP).setSize(width, height/3);
		this.tileShapes.get(TileType.L_RIGHT_TOP).setSize(width, height/3);

		this.tileShapes.get(TileType.L_LEFT_BOTTOM).setSize(width, height/3);
		this.tileShapes.get(TileType.L_RIGHT_BOTTOM).setSize(width, height/3);
		
		this.setShapesPositions();
		
		this.setSize(width*3, height*3);
	}
}
