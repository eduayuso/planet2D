package com.planet2d.engine.actors.tileMaps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.graphics.SpriteTransform;

public class Tile extends SpriteActor {
	
	private TileMap tileMap;
	public SpriteTransform transform;
	private boolean toUpdateVertices;
	
	public Tile(String name) {
		
		super(name);
		this.fileName = name;
	}
	
	public Tile(String path, String fileName, String name, boolean local) {

		super(path, fileName, name, local);
	}

	public Tile(TileMap tileMap, String fileName, String name) {
		
		this("tileMaps/"+tileMap.getZone()+"/"+tileMap.getName(), fileName, name, true);
		this.tileMap = tileMap;
	}

	public Tile(JsonValue json, TileMap tileMap, int index, boolean local) {
		
		this("trees/"+tileMap.getZone()+"/"+tileMap.getName(), json.getString("file"), json.getString("name"), local);
		this.tileMap = tileMap;
		
		float x = json.getFloat("x");
		float y = json.getFloat("y");
		this.setPosition(x, y);
	}
	
	@Override
	protected void positionChanged () {
		
		if (this.transform != null) this.toUpdateVertices = true;
	}
	
	@Override
    public void render(Batch batch, float parentAlpha) {
		
		if (this.transform == null) {
			
			batch.draw(this.sprite, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
			
		} else {

			if (this.toUpdateVertices) {
				this.transform.updateVertices();
				this.toUpdateVertices = false;
			}
			batch.draw(this.sprite.getTexture(), this.transform.getVertices(), 0, this.transform.getVertices().length);
		}
	}
}
