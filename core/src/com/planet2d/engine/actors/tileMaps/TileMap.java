package com.planet2d.engine.actors.tileMaps;

import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.actors.CompositeActor;

public class TileMap extends CompositeActor<Tile> {

	public TileMap(String name) {
		super(name);
	}
	
	public TileMap(String name, JsonValue json) {
		super(name, json);
	}
	
	@Override
	public void create() {
		this.create(false);
	}

	@Override
	public void create(boolean local) {
		
		this.setSize(this.json.getInt("width"), this.json.getInt("height"));
		
		int i = 0;
		for (JsonValue tileJson: this.json.get("tiles")) {
			Tile tile = new Tile(tileJson, this, i++, local);
			this.add(tile);
		}		
	}
}
