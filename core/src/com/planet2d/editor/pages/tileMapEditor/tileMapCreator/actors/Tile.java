package com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.engine.graphics.SpriteTransform;

public class Tile extends com.planet2d.engine.actors.tileMaps.Tile {
	
	public Tile auxTile;

	public enum TileType {
		
		LEFT_TOP,
		TOP,
		RIGHT_TOP,
		LEFT,
		CENTER,
		RIGHT,
		LEFT_BOTTOM,
		BOTTOM,
		RIGHT_BOTTOM,
		
		L_RIGHT_BOTTOM,
		L_LEFT_BOTTOM,
		L_RIGHT_TOP,
		L_LEFT_TOP
	}
	
	public TileType type;
	public int id;
	
	public Tile(String name, TileType type, int id) {
		super(name);
		this.type = type;
		this.id = id;
	}
	
	public static boolean isCorner(TileType type) {
		
		return type == TileType.LEFT_TOP || type == TileType.LEFT_BOTTOM || type == TileType.RIGHT_TOP || type == TileType.RIGHT_BOTTOM;
	}
	
	public static boolean isTop(TileType type) {
		
		return type == TileType.LEFT_TOP || type == TileType.TOP || type == TileType.RIGHT_TOP;
	}
	
	public static boolean isBottom(TileType type) {
		
		return type == TileType.LEFT_BOTTOM || type == TileType.BOTTOM || type == TileType.RIGHT_BOTTOM;
	}
	
	public static boolean isLeft(TileType type) {
		
		return type == TileType.LEFT_BOTTOM || type == TileType.LEFT || type == TileType.LEFT_TOP;
	}
	
	public static boolean isRight(TileType type) {
		
		return type == TileType.RIGHT_TOP || type == TileType.RIGHT || type == TileType.RIGHT_BOTTOM;
	}
	
	public void fussionWith(Tile tile2) {

		this.auxTile = tile2;
	}

	public Vector2 getRelativePositionInCell(TileCell cell) {

		float x = 0;
		float y = 0;
		
		if (this.type == TileType.LEFT_TOP || this.type == TileType.LEFT || this.type == TileType.LEFT_BOTTOM) {
			x += cell.getWidth() - this.getWidth();
		}
		
		if (this.type == TileType.LEFT_BOTTOM || this.type == TileType.BOTTOM || this.type == TileType.RIGHT_BOTTOM) {
			y += cell.getHeight() - this.getHeight();
		}
		
		return new Vector2(x, y);
	}
	
	@Override
	public boolean remove () {
		
		if (this.auxTile != null) {
			this.auxTile.remove();
			this.auxTile = null;
		}
		return super.remove();
	}

	public void addToParent(Group parent) {

		parent.addActor(this);
		if (this.auxTile != null) parent.addActor(this.auxTile);
	}

	public void correctPosition(float x, float y) {

		this.setPosition(this.getX() + x, this.getY() + y);
		if (this.auxTile != null) this.auxTile.setPosition(this.auxTile.getX() + x, this.auxTile.getY() + y); 
	}

	public boolean isL() {

		return this.auxTile != null;
	}

	public void stretchTopRight(float incX, float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.stretchTopRight(incX, incY);
	}
	
	public void stretchTopLeft(float incX, float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.stretchTopLeft(incX, incY);
	}
	
	public void stretchBottomRight(float incX, float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.stretchBottomRight(incX, incY);
	}
	
	public void stretchBottomLeft(float incX, float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.stretchBottomLeft(incX, incY);
	}
	
	public void skewRightVertical(float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.skewRightVertical(incY);
	}
	
	public void skewLeftVertical(float incY) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.skewLeftVertical(incY);
	}
	
	public void skewBottomHorizontal(float incX) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.skewBottomHorizontal(incX);
	}
	
	public void skewTopHorizontal(float incX) {

		if (this.transform == null) this.transform = new SpriteTransform(this);
		this.transform.skewTopHorizontal(incX);
	}

	public void resetTransform() {

		this.transform = null;
	}
}
