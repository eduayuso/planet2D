package com.planet2d.editor.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.planet2d.engine.Resources;
import com.planet2d.engine.ui.Styles.FontType;

public class Label extends com.planet2d.engine.ui.Label {

	private NinePatchDrawable background;
	private boolean backgroundVisible = true;
	private boolean textVisible = true;
	public int paddingLeft = 12;
	public int paddingRight = 12;
	public int paddingV = 8;
	
	public Label(String text) {
		super(text);
	}
	
	public Label(String text, FontType type) {
		
		super(text, type);
	}
	
	public void setBackground(boolean withBackground) {
		
		this.setBackground(withBackground, "label-border");
	}

	public void setBackground(boolean withBackground, String backgroundImageName) {
		
		if (withBackground && this.background == null) {
			this.background = new NinePatchDrawable(new NinePatch(Resources.getTexture("editor", "ui/"+backgroundImageName+".png"), 4, 4, 4, 4));
		} else {
			this.background = null;
		}
	}
	
	public void setVisible(boolean background, boolean text) {
		
		this.backgroundVisible = background;
		this.textVisible = text;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		if (this.background != null && this.backgroundVisible) {
			this.background.draw(batch, this.getX()-paddingLeft, this.getY()-paddingV, this.getOriginX(), this.getOriginY(), this.getWidth()+paddingLeft+paddingRight, this.getHeight()+paddingV*2, this.getScaleX(), this.getScaleY(), this.getRotation());
		}
		if (this.textVisible) super.draw(batch, parentAlpha);
	}
}
