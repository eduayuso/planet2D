package com.planet2d.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.Engine;

public class Menu extends Table {

	private Array<MenuItem> items;
	private boolean hideOnExit;
	
	public Menu(MenuItem... items) {
		
		super(Engine.skin);
		
		this.items = new Array<MenuItem>();
		for (int i=0; i<items.length; i++) {
			this.items.add(this.addItem(items[i]));
			items[i].menu = this;
		}
		this.config();
	}
	
	public Menu(Button button, JsonValue json) {
		
		super(Engine.skin);

		this.items = new Array<MenuItem>();
		for (JsonValue itemJson: json) {
			this.items.add(this.addItem(itemJson));
		}
		
		this.config();
	}
	
	public void config() {
		
		this.setBackground("white");
		this.setPosition(this.getX(), this.getY()-this.getHeight());
		this.setItemsWidth();
		this.setSize(this.getPrefWidth(), this.getPrefHeight());
		this.setPosition(this.getX(), this.getY()-this.getHeight());
		this.hide();
		
		this.addListener(new InputListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				
				if (fromActor != null) hideOnExit = true;
				else hideOnExit = false;
			}
			
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				
				if (toActor != null && !toActor.isDescendantOf(Menu.this) && hideOnExit) hide();
				hideOnExit = false;
			}
		});
	}

	private void setItemsWidth() {
		
		for (MenuItem it: this.items) {
			it.setWidth(this.getPrefWidth());
		}
	}

	protected MenuItem addItem(JsonValue itemJson) {
		
		return this.addItem(new MenuItem(itemJson, this));
	}
	
	protected MenuItem addItem(MenuItem menuItem) {
		
		this.add(menuItem).pad(0, 0, 0, 0).align(Align.left).expandX();
		this.row();
		return menuItem;
	}

	public void show() {

		this.setVisible(true);
	}
	
	public void show(int x, float y) {

		this.show();
		this.setPosition(x, y-this.getHeight());
		this.toFront();
	}
	
	public void hide() {
		
		this.setVisible(false);
	}
}
