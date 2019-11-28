package com.planet2d.editor.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.planet2d.editor.Editor;

public class CanvasListener extends ClickListener {
	
	public Integer dragInitX;
	public Integer dragInitY;
	public boolean leftButtonPressed;
	
	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
		this.leftButtonPressed = button == Input.Buttons.LEFT;
		return Editor.currentPage.touchDown(this, x, y);
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		if (!leftButtonPressed) return;
		Editor.currentPage.touchUp(this);
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		
		Editor.currentPage.touchDragged(this, x, y);
	}

	@Override
	public boolean mouseMoved (InputEvent event, float x, float y) {
		return false;
	}
}
