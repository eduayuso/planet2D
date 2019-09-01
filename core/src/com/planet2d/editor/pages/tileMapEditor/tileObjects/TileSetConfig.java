package com.planet2d.editor.pages.tileMapEditor.tileObjects;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.editor.pages.tileMapEditor.tilesTemplate.TileSetTemplate;
import com.planet2d.engine.actors.tileMaps.Tile;

public class TileSetConfig {

	public float BLOCK_WIDTH = 148;
	public float BLOCK_HEIGHT = 148;
	public static final int DEFORM_TOP = 0;
	public static final int DEFORM_SIDES = 1;
	public static final int DEFORM_BOTTOM = 2;
	
	public String name;
	public String pattern;
	public String physicsShape;
	public String physicsPadding;
	public String[] meshDeformation;
	public Array<TileConfig> tiles;
	public TileMapProject tileMapProject;
	public TileSetTemplate template;
	
	public TileSetConfig(TileMapProject tileMapProject, JsonValue json) {
	
		this.tileMapProject = tileMapProject;
		this.name = json.getString("name");
		this.pattern = json.getString("pattern");
		this.physicsShape = json.getString("physicsShape");
		this.physicsPadding = json.getString("physicsPadding");
		this.meshDeformation = json.getString("meshDeformation").split("-");
		this.tiles = new Array<TileConfig>();
		for (JsonValue tileJson: json.get("tiles")) {
			this.tiles.add(new TileConfig(tileJson));
		}
	}
	
	public TileSetConfig(TileMapProject tileMapProject, String name) {
		
		this.tileMapProject = tileMapProject;
		this.name = name;
		this.pattern = "1x1";
		this.physicsShape = "polygon";
		this.physicsPadding = "0-0-0-0";
		this.meshDeformation = new String[]{"stretch", "stretch", "stretch"}; 
		this.tiles = new Array<TileConfig>();
	}

	public void removeTile(Tile tile) {

		for (TileConfig tileConfig: this.tiles) {
			
			if (tileConfig.tile == tile) {
				this.tiles.removeValue(tileConfig, true);
				break;
			}
		}
	}

	public String getTopMeshDeformType() {

		return this.meshDeformation[DEFORM_TOP];
	}
	
	public String getSidesMeshDeformType() {

		return this.meshDeformation[DEFORM_SIDES];
	}
	
	public String getBottomMeshDeformType() {

		return this.meshDeformation[DEFORM_BOTTOM];
	}
}
