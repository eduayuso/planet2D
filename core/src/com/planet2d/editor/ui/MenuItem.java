package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.Actions;
import com.planet2d.engine.actors.SpriteActor;

public class MenuItem extends PanelItem {
	
	private final static float ITEM_HEIGHT = 24;
	private JsonValue json;
	private String actionName;
	public Menu menu;
	
	public MenuItem(JsonValue itemJson, Menu menu) {
		
		super(itemJson.getString("text"), 0, ITEM_HEIGHT);
		this.json = itemJson;
		this.menu = menu;
		this.create();
	}
	
	public MenuItem(String title, String action) {
		
		super(title, 0, ITEM_HEIGHT);
		this.actionName = action;
		this.create();
	}
	
	@Override
	public void setWidth(float width) {
		
		super.setWidth(width);
		this.backgroundHover.setWidth(width);
		this.backgroundSelected.setWidth(width);
	}
	
	@Override
	protected void create() {
		
		super.create();
		
		if (this.json != null) this.actionName = this.json.getString("action");
	}

	@Override
	protected Actor createIcon() {
		
		Actor icon = null;
		
		if (this.json != null && this.json.get("icon") != null) {
			
			icon = new SpriteActor("editor", "ui/menuItem_"+this.json.getString("icon")+".png");
			icon.setPosition(12, 4);
		}
		
		return icon;
	}

	@Override
	public void enter(Actor fromActor) {
		
	}

	@Override
	public void exit(Actor toActor) {
	}

	@Override
	protected void touchDownLeftButton() {

	}

	@Override
	protected void touchDownRightButton() {
	}

	@Override
	protected void touchUp() {
		
		if (this.actionName != null) Actions.invokeAction(this.actionName);
		this.backgroundSelected.setVisible(false);
		this.menu.hide();
	}

	@Override
	public void touchDragged() {
		
	}
}
