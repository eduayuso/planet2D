package com.planet2d.engine.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.planet2d.engine.screens.Screen;

public class ShapeActor extends GameActor {

	public Vector2[] points;
	public Color color;
	public ShapeType shape;
	private ShapeRenderer renderer;

	public ShapeActor(Vector2... points) {
		
		super("shape");
		this.points = points;
		this.color = Color.BLUE;
		this.shape = ShapeType.Line;
		this.renderer = Screen.shapeRenderer;
	}
	
	public ShapeActor(float x, float y, float width, float height) {
		
		this(new Vector2(x,y), new Vector2(x,y+height), new Vector2(x+width, y+height), new Vector2(x+width, y));
		this.setPosition(x, y);
		this.setSize(width, height);
	}
	
	public boolean isTriangle() {
		
		return this.points.length == 3;
	}
	
	public boolean isRectangle() {
		
		return this.points.length == 4;
	}
	
	@Override
	public void render(Batch batch, float parentAlpha) {
		
		if (parentAlpha < 0.1f) return;

		batch.end();
		
		this.renderer.setProjectionMatrix(batch.getProjectionMatrix());
		this.renderer.setTransformMatrix(batch.getTransformMatrix());
		
		this.renderer.begin(this.shape);
		this.renderer.setColor(this.color);
		
		if (this.isRectangle()) {
			
			float x = this.getX();
			float y = this.getY();
			float width = this.getWidth();
			float height = this.getHeight();
			
			this.renderer.rect(x, y, width, height);
			
		} else {
		
			float[] vertices = new float[2*this.points.length];
			int v = 0;
			for (int p=0; p<points.length; p++) {
				vertices[v++] = this.getX() + points[p].x; 
				vertices[v++] = this.getY() + points[p].y;
			}
			
			this.renderer.polygon(vertices);
		}
		
		this.renderer.end();
		
		batch.begin();
	}
}
