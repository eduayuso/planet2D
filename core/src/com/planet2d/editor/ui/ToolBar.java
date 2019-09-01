package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.Editor;
import com.planet2d.engine.Engine;
import com.planet2d.engine.Json;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;

public class ToolBar extends Table {

	private ButtonGroup<Button> buttonGroup;
	
	public ToolBar(String name) {
		
		super(Engine.skin);
		this.setName(name);
		this.create();
		this.setListener();
	}
	
	private void create() {
		
		JsonValue toolBarJson = Json.parseLocal("editor", "json/toolBars.json").get(this.getName());
		
		this.left();
		this.setSize(toolBarJson);
		this.setItems(toolBarJson);
		this.setBackground("white");
	}

	private void setSize(JsonValue toolBarJson) {

		float width = 0;
		float height = 0;
		
		String widthStr  = toolBarJson.getString("width");
		String heightStr = toolBarJson.getString("height");
		
		if (widthStr.equals("fill")) width = Screen.getWidth();
		else width = Integer.parseInt(widthStr);
		
		if (heightStr.equals("fill")) height = Screen.getWidth();
		else height = Integer.parseInt(heightStr);
		
		this.setSize(width, height);
	}
	
	private void setItems(JsonValue toolBarJson) {

		JsonValue itemsJson = toolBarJson.get("items");
		this.buttonGroup = new ButtonGroup<Button>();
		
		for (JsonValue itemJson: itemsJson) {
			this.addItem(itemJson);
		}
		
		this.buttonGroup.uncheckAll();
	}

	private void addItem(JsonValue itemJson) {

		// CREATE ACTOR TO INSERT
		
		Actor actor = null;
		String classType = itemJson.getString("class");
		
		if (classType.equals("ImageActor")) {
			actor = new SpriteActor("editor", itemJson.getString("fileName"));
			
		} else if (classType.equals("Button")) {
			String style = "toolBar";
			if (itemJson.get("style") != null) style = itemJson.getString("style");
			actor = new Button(itemJson, style);
			this.buttonGroup.add((Button) actor);
		}
		
		// ATTRIBUTES OF NEW CELL
		
		int padBottom=0, padLeft=0, padRight=0, padTop=0;
		if (itemJson.get("padLeft") != null) padLeft = itemJson.getInt("padLeft");
		if (itemJson.get("padRight") != null) padRight = itemJson.getInt("padRight");
		
		int align = 0;
		boolean toAlign = false;
		if (itemJson.get("align") != null) {
			toAlign = true;
			if (itemJson.getString("align").equals("right")) align = Align.right;
			else align = Align.left;
		}
		boolean expandX = false;
		if (itemJson.get("expandX") != null) expandX = itemJson.getBoolean("expandX");
		
		// ADD ACTOR TO CELL
		
		Cell<Actor> newCell = this.add(actor);
		if ((actor instanceof Button) && !((Button)actor).getText().toString().isEmpty()) {
			newCell.width(((Button)actor).getWidth()+10);
		}
		newCell.pad(padTop, padLeft, padBottom, padRight);
		if (expandX) newCell.expandX();
		if (toAlign) newCell.align(align);
	}

	public void unselectButtons() {

		this.buttonGroup.uncheckAll();
	}
	
	private void setListener() {

		this.addListener(new InputListener() {
			
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (Editor.currentPage != null) Editor.currentPage.setScrollTouchable(false);
			}

			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (Editor.currentPage != null) Editor.currentPage.setScrollTouchable(true);
			}
		});
	}
}
