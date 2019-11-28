package com.planet2d.editor.layouts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.engine.ui.NinePatchDrawable;

public class RectangleSelector extends Actor {

	private NinePatchDrawable drawable;
	public boolean active;
	
	public RectangleSelector() {
		
		super();
		this.setPosition(0, 0);
		this.setSize(1, 1);
		this.drawable = new NinePatchDrawable("rectangle-selector");
		this.setVisible(false);
	}
	
	public void start(float x, float y) {
		
		this.setPosition(x, y);
		this.active = true;
	}
	
	public void update(float x, float y) {
		
		this.setSize(x - this.getX(), y - this.getY());
		
		if (!this.isVisible()) this.setVisible(true);
	}
	
	public void end() {
		
		this.active = false;
		this.setVisible(false);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		this.drawable.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}

	public boolean overlaps(float ax, float ay, float aw, float ah) {

		float rx = this.getX();
		float ry = this.getY();
		float rw = this.getWidth();
		float rh = this.getHeight();
		
		if (rw < 0) {
			rw = -rw;
			rx -= rw;
		}
		if (rh < 0) {
			rh = -rh;
			ry -= rh;
		}
		
		return rx < ax && rx + rw > ax + aw && ry < ay && ry + rh > ay + ah;
	}
}
