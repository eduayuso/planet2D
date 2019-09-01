package com.planet2d.engine.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.actors.EditorDebug;
import com.planet2d.engine.Engine;
import com.planet2d.engine.graphics.ActorRenderer;
import com.planet2d.engine.graphics.ColorFilter;
import com.planet2d.engine.graphics.Shader;
import com.planet2d.engine.physics.Physics;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public abstract class GameActor extends Actor implements Json.Serializable {

	public int layerType;
	public int layerIndex;
	private int direction; // 1 (right), -1 (left)
	private String zone;
	private boolean active;
	private boolean cacheable;
	
	protected JsonValue json; //optional json with creating info
	
	protected Physics physics;
	protected Shader shader;
	protected ColorFilter colorFilter;
	
	public EditorDebug editorDebug;
	
	public GameActor(String name) {
		super();
		this.setName(name);
	}

	public GameActor(String name, JsonValue json) {
		this(name);
		this.json = json;
	}
	
	public void activate() {
		
		this.setActive(true);
	}

	public void create() {
		this.create(false);
	}
	
	public void create(boolean local) {
	}
	
	public void updateShader(Batch batch) {
		
	}
	
	public void toCache(SpriteCache cache) {
		
	}
	
	public float getBodyCenterX() {

		return 0;
	}

	public float getBodyCenterY() {
		return 0;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public int getCacheableChildrenNum() {
		return 0;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Override
	public void write(Json json) {
		
		json.writeValue("name", this.getName());
		json.writeValue("x", this.getX());
		json.writeValue("y", this.getY());
		json.writeValue("width", this.getWidth());
		json.writeValue("height", this.getHeight());
		json.writeValue("direction", this.direction);
		json.writeValue("layerType", this.layerType);
		json.writeValue("layerIndex", this.layerIndex);
		json.writeValue("zone", this.zone);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		
	}
	
	public boolean isEditorSelected() {

		return this.editorDebug.selected;
	}
	
	public boolean isEditorHovered() {
		
		return this.editorDebug.hovered;
	}

	public boolean isEditorEdited() {

		return this.editorDebug.edited;
	}

	public void setEditorSelected(boolean b) {
		
		if (this.editorDebug == null) return;
		this.editorDebug.selected = b;
		if (!b) this.setEditorEdited(false);
	}
	
	public void setEditorHovered(boolean b) {

		this.editorDebug.hovered = b;
	}

	public void setEditorEdited(boolean b) {

		this.editorDebug.edited = b;
		this.editorDebug.setOriginVisible(b);
		if (!b) Cursors.set(CursorType.DEFAULT);
	}
	
	public void checkPositionChanged() {
		
		this.positionChanged();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		Engine.render(this, batch, parentAlpha);
	}

	public abstract void render(Batch batch, float parentAlpha);
}
