package com.planet2d.editor.listeners;

import com.badlogic.gdx.Input.Keys;
import com.planet2d.editor.Editor;
import com.planet2d.editor.history.History;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public class UserInputListener extends com.planet2d.engine.input.UserInputListener {
	
	@Override
	public boolean keyDown(int keycode) {

		this.setKeyPressed(keycode, true);
		
		if (keycode == Keys.FORWARD_DEL) Editor.currentPage.deleteSelectedActors();
		else if (keycode == Keys.LEFT) Editor.currentPage.moveActor(-1,0);
		else if (keycode == Keys.RIGHT) Editor.currentPage.moveActor(1,0);
		else if (keycode == Keys.DOWN) Editor.currentPage.moveActor(0,-1);
		else if (keycode == Keys.UP) Editor.currentPage.moveActor(0,1);
		else if (keycode == Keys.CONTROL_LEFT) Editor.currentPage.controlLeftOption(true);
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.CONTROL_LEFT) {
			Editor.currentPage.controlLeftOption(false);
			return true;
		}

		this.setKeyPressed(keycode, false);
		return false;
	}
	
	public void setKeyPressed(int keycode, boolean set) {
		
	}

	@Override
	public boolean keyTyped(char character) {
		
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		return false;
	}
}