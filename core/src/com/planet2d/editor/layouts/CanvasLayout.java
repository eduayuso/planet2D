package com.planet2d.editor.layouts;

import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.Page;
import com.planet2d.editor.pages.stageEditor.StagePage;
import com.planet2d.editor.pages.tileMapEditor.TileMapPage;
import com.planet2d.editor.pages.treeEditor.TreePage;
import com.planet2d.editor.pages.welcome.WelcomePage;
import com.planet2d.engine.layouts.Layout;
import com.planet2d.engine.screens.Screen;

public class CanvasLayout extends Layout {
	
	public Array<Page> projectPages;
	
	public CanvasLayout(Screen screen) {
		
		super(screen);
		this.projectPages = new Array<Page>();
	}

	public Page createPage(PageType type) {
		
		Page page = null;
		if (type == PageType.WELCOME) page = new WelcomePage();
		else if (type == PageType.TREE_EDITOR) page = new TreePage();
		else if (type == PageType.STAGE_EDITOR) page = new StagePage();
		else if (type == PageType.TILEMAP_EDITOR) page = new TileMapPage();
		
		if (page != null) {
			this.projectPages.add(page);
			this.add(page);
		}
		
		return page;
	}
}
