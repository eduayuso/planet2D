package com.planet2d.engine.ui;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.Editor;
import com.planet2d.engine.Engine;

public class Dialog extends com.badlogic.gdx.scenes.scene2d.ui.Dialog {
	
	public boolean open;

	public Dialog(String title) {
		
		super(title, Engine.skin);
	}

	public void show() {

		this.open = true;
		super.show(Editor.canvas);
	}
	
	@Override
	public void hide () {
		
		this.open = false;
		if (Editor.currentPage != null) Editor.currentPage.scrollPane.setTouchable(Touchable.enabled);
		super.hide();
	}
}
