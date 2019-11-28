package com.planet2d.engine.ui;

import com.planet2d.engine.Engine;
import com.planet2d.engine.ui.Styles.FontType;

public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label {
	
	public Label(String text) {
		
		super(text, Engine.skin);
	}

	public Label(String text, String styleName) {
		
		super(text, Engine.skin, styleName);
	}
	
	public Label(String text, FontType fontStyle) {
		
		super(text, Styles.labelStyles.get(fontStyle));
	}
}
