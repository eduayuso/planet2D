package com.planet2d.editor.pages.tileMapEditor.tileObjects;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

public class TileMapProject {

	public String name;
	public Array<TileSetConfig> sets;
	
	public TileMapProject(String name, JsonValue json) {
		
		this.name = name;
		this.sets = new Array<TileSetConfig>();
		for (JsonValue setJson: json) {
			this.sets.add(new TileSetConfig(this, setJson));
		}
	}
}
