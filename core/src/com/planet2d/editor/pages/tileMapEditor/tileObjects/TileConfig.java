package com.planet2d.editor.pages.tileMapEditor.tileObjects;

import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;
import com.planet2d.engine.actors.tileMaps.Tile;

public class TileConfig {

	public Tile tile;
	public TileType type;
	public int id;
	public String file;
	public float offsetX;
	public float offsetY;

	public TileConfig(JsonValue tileJson) {
		
		this(TileType.valueOf(tileJson.getString("type")), tileJson.getInt("id"), tileJson.getString("file"),
			 tileJson.getFloat("offsetX"), tileJson.getFloat("offsetY"));
	}
	
	public TileConfig(TileType type, int id, String file, float offsetX, float offsetY) {
		
		this.type = type;
		this.id = id;
		this.file = file;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}
