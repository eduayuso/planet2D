package com.planet2d.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.ProjectInfo;
import com.planet2d.engine.Resources;

public class Loader {

	public static void loadUI() {

		String[] projectIcons = new String[Config.projects.size];
		int p=0;
		for (ProjectInfo pinfo: Config.projects.values()) {
			projectIcons[p++] = "ui/"+pinfo.id+"-icon";
		}
		
		Resources.setProject("editor");
		Resources.loadPNGTextures(
			"ui/open-detail",
			"ui/close-detail",
			"ui/app-icon-mini",
			"ui/logo",
			"ui/logo2",
			"ui/actor-origin",
			"ui/frame-corner",
			"ui/label-border",
			"ui/rectangle-selector",
			"ui/add-file-icon",
			"ui/tab-selected",
			"ui/tab-unselected",
			"ui/tab2-selected",
			"ui/tab2-unselected",
			"ui/panel-white",
			"ui/panel-gray",
			"ui/panel-gray2",
			"ui/panel-gray3",
			"ui/panel-gray4",
			"ui/project-icon-background",
			"ui/new-project-icon",
			"ui/SCREEN_EDITOR-icon",
			"ui/STAGE_EDITOR-icon",
			"ui/CHARACTER_EDITOR-icon",
			"ui/TILEMAP_EDITOR-icon",
			"ui/TREE_EDITOR-icon",
			"ui/MISSION_EDITOR-icon",
			"ui/PARTICLE_EDITOR-icon",
			"ui/ASSETS_PAGE-icon",
			"ui/scene-icon",
			"ui/character-icon",
			"ui/tilemap-icon",
			"ui/interactive-icon",
			"ui/tree-icon",
			"ui/items-icon",
			"ui/particles-icon",
			"ui/images-icon",
			"ui/audio-icon",
			"ui/more-icon",
			"ui/back-arrow",
			"ui/file-icon",
			"ui/file-icon-disabled",
			"ui/folder-open",
			"ui/folder-close",
			"ui/page-frame-border",
			"ui/loading",
			"ui/eye-background",
			"ui/eye",
			"ui/player-icon",
			"ui/enemy-icon",
			"ui/boss-icon",
			"ui/subboss-icon",
			"ui/npc-icon",
			"ui/spawner-icon",
			"ui/menuItem_new-project",
			"ui/menuItem_open-project",
			"ui/menuItem_save-project",
			"ui/menuItem_close-project",
			"ui/menuItem_close",
			"ui/menuItem_undo",
			"ui/menuItem_redo",
			"ui/menuItem_empty",
			"ui/file-mode",
			"ui/icon-mode"
		);
		
		Resources.loadPNGTextures(projectIcons);
	}

	public static void loadTree(String treeName) {
		
		String directory = "resources/"+Config.gamePath+"/textures/trees/"+treeName;

		FileHandle fileHandle = Gdx.files.local(directory);
		for (FileHandle file: fileHandle.list()) {
			if (file.extension().equals("png")) Resources.loadLocalTextures(file.path());
		}
	}

	public static void loadTileMap(String treeName) {
		
		String directory = "resources/"+Config.gamePath+"/textures/tileMaps/"+treeName;

		FileHandle fileHandle = Gdx.files.local(directory);
		for (FileHandle file: fileHandle.list()) {
			if (file.extension().equals("png")) Resources.loadLocalTextures(file.path());
		}
	}
}
