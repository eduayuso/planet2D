package com.planet2d.editor.pages.welcome.panels;

import com.planet2d.editor.Editor;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.ProjectInfo;
import com.planet2d.editor.pages.welcome.WelcomePage;

public class ProjectsItemsPanel extends ItemsPanel {

	private WelcomePage page;
	
	public ProjectsItemsPanel(WelcomePage page, String title, int width, int height) {
		
		super(title, width, height);
		this.page = page;
		
		ItemInfo[] items = new ItemInfo[Config.projects.size+1];
		int p=0;
		items[p++] = new ItemInfo("New Project", "new-project");
		for (ProjectInfo proj: Config.projects.values()) {
			items[p++] = new ItemInfo(proj.title, proj.id);
		}
		this.createButtons(items, 26, 132);
	}

	@Override
	public void buttonAction(ItemInfo info) {
		
		Config.gamePath = info.id;
		this.page.openProjectInfoPanel(info);
	}

}
