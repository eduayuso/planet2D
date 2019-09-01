package com.planet2d.engine.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.planet2d.engine.Resources;

public class NinePatchDrawable extends com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable {
	
	public NinePatchDrawable(String fileName) {
		
		this(fileName, 2, 2, 2, 2);
	}

	public NinePatchDrawable(String fileName, int left, int right, int top, int bottom) {
		
		super(new NinePatch(Resources.getTexture("editor", "ui/"+fileName+".png"), left, right, top, bottom));
	}
}
