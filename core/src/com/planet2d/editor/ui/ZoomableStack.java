package com.planet2d.editor.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.planet2d.engine.screens.Screen;

public class ZoomableStack extends Stack {

	public Table imageTable;

	public ZoomableStack(Table table) {
		
		this.imageTable = table;

	    this.add(this.imageTable);

	    this.layout();

	    this.imageTable.setTransform(true);
	}
	
	@Override
	public float getPrefWidth() {
	    return this.imageTable.getWidth() * this.imageTable.getScaleX();
	}

	@Override
	public float getPrefHeight() {
	    return this.imageTable.getHeight() * this.imageTable.getScaleY();
	}
	
	@Override
	public void layout() {
		
	    float width = this.getWidth();
	    float height = this.getHeight();

	    this.imageTable.setSize(this.imageTable.getPrefWidth(), this.imageTable.getPrefHeight());

	    float tableWidth = this.imageTable.getWidth();
	    float tableHeight = this.imageTable.getHeight();

	    this.imageTable.setOrigin(tableWidth / 2, tableHeight / 2);
	    this.imageTable.setPosition((width / 2 - (tableWidth / 2)), (height / 2 - (tableHeight / 2)));
	}
	
	@Override
	public void setCullingArea(Rectangle cullingArea) {

	}
	
	public boolean zoom(float s) {
		
		if (s <= 1.0 && this.imageTable.getWidth()*s >= Screen.getWidth() && this.imageTable.getHeight()*s >= Screen.getHeight()) {
			this.imageTable.setScale(s);
			invalidateHierarchy();
			return true;
		} else {
			return false;
		}
	}
	
	public int getZoomScaled() {
		
		return (int) (this.imageTable.getScaleX()*100);
	}
}
