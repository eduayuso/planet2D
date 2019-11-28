package com.planet2d.editor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.planet2d.engine.Resources;

public class Panel extends Group {

	private NinePatchDrawable background;
	private NinePatchDrawable backgroundSelected;
	private boolean selected;
	
	public Panel(String backgroundName, float width, float height) {
		
		this(backgroundName, (int)width, (int)height);
	}
	
	public Panel(String backgroundName, int width, int height) {
		
		super();
		this.setSize(width, height);
		this.background = new NinePatchDrawable(new NinePatch(Resources.getTexture("editor", "ui/"+backgroundName+".png"), 4, 4, 4, 4));
	}
	
	public void setBackgroundSelected(String backgroundName) {
		
		this.backgroundSelected = new NinePatchDrawable(new NinePatch(Resources.getTexture("editor", "ui/"+backgroundName+".png"), 4, 4, 4, 4));
	}
	
	public void setSelected(boolean set) {
		
		this.selected = set;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		Color col = this.getColor();
		batch.setColor(col.r, col.g, col.b, parentAlpha*col.a);
		if (!this.selected && this.background != null) {
			this.background.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginX(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		}
		if (this.selected && this.backgroundSelected != null) {
			this.backgroundSelected.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginX(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		}
		super.draw(batch, parentAlpha);
		batch.setColor(col.r, col.g, col.b, 1);
	}
}
