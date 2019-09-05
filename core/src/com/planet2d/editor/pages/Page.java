package com.planet2d.editor.pages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.Editor;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.NinePatchDrawable;

public abstract class Page extends Group {
	
	public PageType type;
	private NinePatchDrawable frameBorder;

	public Page(PageType type) {
		
		super();
		this.type = type;
		this.setName(Page.typeToString(type));
		this.setSize(Screen.getWidth(), Screen.getHeight() - Editor.window.getMainToolBar().getHeight() - Editor.window.getPagesTabs().getHeight());
		
		this.frameBorder = new NinePatchDrawable("page-frame-border");
	}
	
	public abstract void createContent();
		
	public void centerActor(Actor actor) {
		
		actor.setPosition(this.getX() + this.getWidth()/2f - actor.getWidth()/2f, this.getY() + this.getHeight()/2f - actor.getHeight()/2f);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		// Top border
		this.frameBorder.draw(batch, this.getX(), 0 + this.getHeight() - 4, this.getWidth(), 2);
		
		// Left border
		this.frameBorder.draw(batch, this.getX(), 0, 2, this.getY() + this.getHeight() - 5);
	}
	
	public static String typeToString(PageType type) {

		if (type == PageType.WELCOME) return "Welcome b";
		if (type == PageType.STAGE_EDITOR) return "Stage";
		if (type == PageType.TILEMAP_EDITOR) return "Tilemap";
		if (type == PageType.CHARACTER_EDITOR) return "Character";
		if (type == PageType.TREE_EDITOR) return "Tree";
		return "";
	}
}
