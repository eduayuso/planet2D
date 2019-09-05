package com.planet2d.editor.pages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.Editor;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.ui.Button;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.Resources;
import com.planet2d.engine.screens.Screen;

public class PageTabs extends HorizontalGroup {

	private NinePatchDrawable background;
	public Array<Tab> tabs;
	private Tab lastSelectedTab;
	
	public PageTabs() {
		
		super();
		this.create();
	}

	private void create() {
		
		this.setSize(Screen.getWidth(), 21);
		this.tabs = new Array<Tab>();
		this.background = new NinePatchDrawable(new NinePatch(Resources.getTexture("editor", "ui/tab-unselected.png"), 4, 4, 4, 4));
	}

	public void addTab(PageType type) {
		
		Tab tab = new Tab(Page.typeToString(type), this.tabs.size);
		this.addActor(tab);
		this.tabs.add(tab);
		
		tab.addPage(Editor.canvas.createPage(type));
		this.selectTab(tab);
	}
	
	protected void unselectTabs() {

		for (Tab tab: this.tabs) tab.select(false);
	}
	
	protected void selectTab(Tab tab) {

		this.unselectTabs();
		tab.select(true);
	}

	protected void closeTab(Tab tab) {

		if (this.lastSelectedTab != null && tab.selected) this.lastSelectedTab.select(true);
		
		tab.page.remove();
		this.removeActor(tab);
		this.tabs.removeValue(tab, true);
		tab.remove();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		Color color = batch.getColor();
		batch.setColor(color.r, color.g, color.b, 1);
		this.background.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginX(), this.getWidth()+2, this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		super.draw(batch, parentAlpha);
		batch.setColor(color.r, color.g, color.b, color.a*parentAlpha);
	}

    private class Tab extends Group {
		
		private Page page;
		private boolean selected;
		private int index;
		private Label labelSelected;
		private Label labelUnselected;
		private Button closeButton;
		private boolean closing;
		
		public Tab(String name, int index) {
			
			super();
			this.setName(name);
			this.index = index;
			this.labelSelected = new Label(name);
			this.labelSelected.setBackground(true, "tab-selected");
			this.labelSelected.paddingLeft = 12;
			this.labelSelected.paddingRight = 32;
			this.labelSelected.paddingV = 3;
			this.labelSelected.setAlignment(Align.center);
			this.labelUnselected = new Label(name);
			this.labelUnselected.setBackground(true, "tab-unselected");
			this.labelUnselected.setAlignment(Align.center);
			this.labelUnselected.paddingLeft = 12;
			this.labelUnselected.paddingRight = 32;
			this.labelUnselected.paddingV = 3;
			
			this.labelSelected.setPosition(this.labelSelected.paddingLeft, 0);
			this.labelUnselected.setPosition(this.labelSelected.paddingLeft, 0);
			this.labelSelected.setVisible(false);
			
			this.closeButton = new Button("", "tab-close");
			this.closeButton.setPosition(this.labelSelected.getX() + this.labelSelected.getWidth() + 6, -this.labelUnselected.paddingV);
			
			this.addActor(this.labelSelected);
			this.addActor(this.labelUnselected);
			this.addActor(this.closeButton);
			this.setSize(this.labelSelected.getWidth() + this.labelSelected.paddingLeft + this.labelSelected.paddingRight, this.labelSelected.getHeight());
			
			this.addListener(new InputListener() {
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					
					if (!Tab.this.closing) {
						PageTabs.this.selectTab(Tab.this);
					}
					return false;
				}
			});
			
			this.closeButton.addListener(new InputListener() {
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					
					Tab.this.closing = true;
					PageTabs.this.closeTab(Tab.this);
					return true;
				}
			});
		}

		public void addPage(Page page) {
			
			this.page = page;
		}

		protected void select(boolean b) {

			if (!b && this.selected) PageTabs.this.lastSelectedTab = this;
			this.selected = b;
			this.labelSelected.setVisible(this.selected);
			this.labelUnselected.setVisible(!this.selected);
			
			this.page.setVisible(b);
		}
	}
}
