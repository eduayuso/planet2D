package com.planet2d.editor.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public abstract class PanelItem extends Group {
	
	protected final static float LABEL_MARGIN = 8;
	public Panel backgroundHover;
	public Panel backgroundSelected;
	public Actor icon;
	public Label label;
	
	public boolean dragging;

	public PanelItem(String name, float width , float height) {
		
		super();
		this.setName(name);
		this.setSize(width, height);
	}

	protected void create() {

		this.createBackgrounds();
		this.backgroundHover.setVisible(false);
		this.backgroundSelected.setVisible(false);
		
		this.icon = this.createIcon();
		if (this.icon != null) this.icon.setTouchable(Touchable.disabled);
		
		this.label = this.createLabel(this.getName());
		label.setTouchable(Touchable.disabled);
		
		if (this.backgroundHover != null) this.addActor(this.backgroundHover);
		if (this.backgroundSelected != null) this.addActor(this.backgroundSelected);
		if (this.icon != null) this.addActor(this.icon);
		if (this.label != null) this.addActor(this.label);
		
		this.setDefaultWidth();
		
		this.addListener(new PanelItemListener());
	}

	protected void setDefaultWidth() {

		float width = this.label.getWidth() + LABEL_MARGIN + 6;
		if (this.icon != null) width += this.icon.getX() + this.icon.getWidth();
		this.setWidth(width);
	}

	protected Label createLabel(String name) {

		Label label = new Label(name);
		float x = LABEL_MARGIN;
		float y = 4;
		if (this.icon != null) x += this.icon.getX() + this.icon.getWidth();
		label.setPosition(x, y);
		
		return label;
	}

	protected void createBackgrounds() {

		this.createBackgrounds("panel-gray2", "panel-gray");
	}
	
	protected void createBackgrounds(String hover, String selected) {
		
		this.backgroundHover = new Panel(hover, this.getWidth(), this.getHeight());
		this.backgroundSelected = new Panel(selected, this.getWidth(), this.getHeight());
	}
	
	protected abstract Actor createIcon();
	
	public abstract void enter(Actor fromActor);

	public abstract void exit(Actor toActor);
	
	protected abstract void touchDownLeftButton();
	
	protected abstract  void touchDownRightButton();
	
	protected abstract void touchUp();
	
	public abstract void touchDragged();
	
	public class PanelItemListener extends InputListener {
		
		@Override
		public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {

			backgroundHover.setVisible(true);
			PanelItem.this.enter(fromActor);
		}
		
		@Override
		public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
			
			backgroundHover.setVisible(false);
			PanelItem.this.exit(toActor);
		}
		
		@Override
		public boolean touchDown (InputEvent event, float x0, float y0, int pointer, int button) {

			if (button == Input.Buttons.LEFT) {
				PanelItem.this.touchDownLeftButton();
				return true;
			
			} else {
				PanelItem.this.touchDownRightButton();
				return false;
			}
		}
		
		@Override
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			
			PanelItem.this.touchUp();
		}
		
		@Override
		public void touchDragged (InputEvent event, float x, float y, int pointer) {
			
			PanelItem.this.touchDragged();
		}
	}
}
