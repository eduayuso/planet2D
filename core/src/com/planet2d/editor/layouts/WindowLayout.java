package com.planet2d.editor.layouts;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.PageTabs;
import com.planet2d.engine.layouts.Layout;
import com.planet2d.engine.screens.Screen;
import com.planet2d.editor.ui.ToolBar;

public class WindowLayout extends Layout {

	public ToolBar mainToolBar;
	public PageTabs pagesTabs;

	public WindowLayout(Screen screen) {
		
		super(screen);
	}
	
	@Override
	protected void init() {
		
		super.init();
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				unselectActors();
				return true;
			}
		});
	}
	
	public void unselectActors() {

		if (this.mainToolBar != null) this.mainToolBar.unselectButtons();
	}

	public void create() {
		
		this.mainToolBar = new ToolBar("mainToolBar");
		this.mainToolBar.setPosition(0, Screen.getHeight()-this.mainToolBar.getHeight());
		
		this.pagesTabs = new PageTabs();
		this.pagesTabs.addTab(PageType.WELCOME);
		this.pagesTabs.setPosition(0, this.mainToolBar.getY() - this.pagesTabs.getHeight() - 2);
		
		this.addActor(this.pagesTabs);
		this.addActor(this.mainToolBar);
	}
}
