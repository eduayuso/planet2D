package com.planet2d.editor.pages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.editor.Editor;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.editor.ui.ScrollPane;

public class PageScroll extends ScrollPane<GameActor> {

	public float scaleFactor;

	public PageScroll(Group content, float width, float height) {
		super(content, width, height);
	}

	@Override
	protected void config() {
		
		this.setFadeScrollBars(false);
		this.setSmoothScrolling(true);
		
		this.removeListener(this.getListeners().get(1));
		
		this.addListener(new InputListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				setSmoothScrolling(true);
				return true;
			}
			
			@Override
			public boolean scrolled (InputEvent event, float x, float y, int amount) {
				
				if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					setSmoothScrolling(false);
					Editor.currentPage.zoom(amount);
					return false;
				} else {
					setSmoothScrolling(true);
					scroll(amount);
					return true;
				}
			}
			
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				getStage().setScrollFocus(PageScroll.this);
			}
		});
		
		this.setFlickScroll(false);
		this.setFlingTime(0);
	}

	protected void scroll(int amount) {

		if (!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			setScrollY(getScrollY() + getMouseWheelY() * amount);
			
		} else {
			setScrollX(getScrollX() + getMouseWheelX() * amount);
		}
	}
	
	public boolean zoom(float zoom) {
		
		float oldWidth = this.stack.getPrefWidth();
		float oldHeight = this.stack.getPrefHeight();
		float scale = 1.0f - zoom/50f;
		
		float factorX = this.getMaxX() / this.getScrollX();
		float factorY = this.getMaxY() / this.getScrollY();
		
		this.scaleFactor = scale;
		
		if (this.stack.zoom(scale)) {
			
			float newWidth = this.stack.getPrefWidth();
			float newHeight = this.stack.getPrefHeight();
			
			float newX = this.getScrollX() - (oldWidth - newWidth)/factorX;
			float newY = this.getScrollY() - (oldHeight - newHeight)/factorY;
			
			if (newY >= this.getMaxY()) newY = this.getMaxY()-1;
			
			this.setScrollX(newX);
			this.setScrollY(newY);
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public int getZoomScaled() {
		
		return this.stack.getZoomScaled();
	}
	
	@Override
	public void selectItem(GameActor actor) {
		
		super.selectItem(actor);
		actor.setEditorSelected(true);
	}

	@Override
	public void unselectItem(GameActor actor) {

		actor.setEditorSelected(false);
		super.unselectItem(actor);
	}
	
	@Override
	public void unselectAllItems() {
		
		for (GameActor actor: this.items) {
			actor.setEditorSelected(false);
		}
		super.unselectAllItems();
	}
}
