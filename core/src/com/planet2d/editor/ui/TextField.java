package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public class TextField extends com.planet2d.engine.ui.TextField {

	public TextField(String text) {
		
		super(text);
		this.addListener(new InputListener() {
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				Cursors.set(CursorType.TEXT);
			}
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				Cursors.set(CursorType.DEFAULT);
			}
		});
	}
}
