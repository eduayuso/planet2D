package com.planet2d.engine.graphics;

import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.planet2d.engine.actors.SpriteActor;

public class SpriteTransform {

	private SpriteActor actor;
	private float[] verticesSrc;
	private float[] verticesDst;
	
	public SpriteTransform(SpriteActor actor) {
		
		this.actor = actor;
		this.verticesSrc = this.actor.sprite.getVertices();
		this.verticesDst = Arrays.copyOf(this.actor.sprite.getVertices(), this.actor.sprite.getVertices().length);
		this.compute(0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public float[] getVertices() {
		
		return this.verticesDst;
	}
	
	public void stretchBottomLeft(float incX, float incY) {
		
		this.compute(incX, incY, 0, 0, 0, 0, 0, 0);
	}

	public void stretchTopLeft(float incX, float incY) {
		
		this.compute(0, 0, incX, incY, 0, 0, 0, 0);
	}
	
	public void stretchTopRight(float incX, float incY) {
		
		this.compute(0, 0, 0, 0, incX, incY, 0, 0);
	}
	
	public void stretchBottomRight(float incX, float incY) {
		
		this.compute(0, 0, 0, 0, 0, 0, incX, incY);
	}
	
	public void skewRightVertical(float incY) {
		
		this.compute(0, 0, 0, 0, 0, incY, 0, incY);
	}
	
	public void skewLeftVertical(float incY) {
		
		this.compute(0, incY, 0, incY, 0, 0, 0, 0);
	}
	
	public void skewBottomHorizontal(float incX) {
		
		this.compute(incX, 0, 0, 0, 0, 0, incX, 0);
	}

	public void skewTopHorizontal(float incX) {
	
		this.compute(0, 0, incX, 0, incX, 0, 0, 0);
	}


	
	/*
	 * V1: bottomLeft
	 * V2: topLeft
	 * V3: topRight
	 * V4: bottomRight
	 */
	private void compute(float bottomLeftX, float bottomLeftY, float topLeftX, float topLeftY,
						 float topRightX, float topRightY, float bottomRightX, float bottomRightY) {
		
		this.verticesSrc[SpriteBatch.X1] += bottomLeftX;
		this.verticesSrc[SpriteBatch.Y1] += bottomLeftY;
		this.verticesSrc[SpriteBatch.X2] += topLeftX;
		this.verticesSrc[SpriteBatch.Y2] += topLeftY;
		this.verticesSrc[SpriteBatch.X3] += topRightX;
		this.verticesSrc[SpriteBatch.Y3] += topRightY;
		this.verticesSrc[SpriteBatch.X4] += bottomRightX;
		this.verticesSrc[SpriteBatch.Y4] += bottomRightY;
		
		float actorX = this.actor.getParent().getX() + this.actor.getX();
		float actorY = this.actor.getParent().getY() + this.actor.getY();
		
		this.verticesDst[SpriteBatch.X1] = this.verticesSrc[SpriteBatch.X1] + actorX;
		this.verticesDst[SpriteBatch.Y1] = this.verticesSrc[SpriteBatch.Y1] + actorY;
		this.verticesDst[SpriteBatch.X2] = this.verticesSrc[SpriteBatch.X2] + actorX;
		this.verticesDst[SpriteBatch.Y2] = this.verticesSrc[SpriteBatch.Y2] + actorY;
		this.verticesDst[SpriteBatch.X3] = this.verticesSrc[SpriteBatch.X3] + actorX;
		this.verticesDst[SpriteBatch.Y3] = this.verticesSrc[SpriteBatch.Y3] + actorY;
		this.verticesDst[SpriteBatch.X4] = this.verticesSrc[SpriteBatch.X4] + actorX;
		this.verticesDst[SpriteBatch.Y4] = this.verticesSrc[SpriteBatch.Y4] + actorY;
	}
	
	public void updateVertices() {
		
		this.compute(0, 0, 0, 0, 0, 0, 0, 0);
	}
}
