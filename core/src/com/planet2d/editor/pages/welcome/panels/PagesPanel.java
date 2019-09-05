package com.planet2d.editor.pages.welcome.panels;

import com.planet2d.editor.Editor;
import com.planet2d.editor.config.EditorTypes.PageType;

public class PagesPanel extends ItemsPanel {

	public PagesPanel(String title, int width, int height) {
		
		super(title, width, height);
		
		ItemInfo[] items = new ItemInfo[8];
		int i=0;
		items[i++] = new ItemInfo("Screens", PageType.SCREEN_EDITOR);
		items[i++] = new ItemInfo("Stage", PageType.STAGE_EDITOR);
		items[i++] = new ItemInfo("Characters", PageType.CHARACTER_EDITOR);
		items[i++] = new ItemInfo("Tile Maps", PageType.TILEMAP_EDITOR);
		items[i++] = new ItemInfo("Trees", PageType.TREE_EDITOR);
		items[i++] = new ItemInfo("Particles", PageType.PARTICLE_EDITOR);
		items[i++] = new ItemInfo("Missions", PageType.MISSION_EDITOR);
		items[i++] = new ItemInfo("Assets", PageType.ASSETS_PAGE);
		this.createButtons(items, 40, 98);
	}

	@Override
	public void buttonAction(ItemInfo info) {
		
		Editor.window.getPagesTabs().addTab(info.type);
	}
}
