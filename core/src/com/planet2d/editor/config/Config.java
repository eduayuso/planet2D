package com.planet2d.editor.config;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.planet2d.editor.Editor;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.engine.Json;

public class Config extends com.planet2d.engine.config.Config {
	
	public static ObjectMap<String, ProjectInfo> projects;
	
	public void load() {
		
		projects = new ObjectMap<String, ProjectInfo>();
		JsonValue json = Json.parseLocal("editor", "json/config.json");
		JsonValue projectsJson = json.get("projects");
		
		for (JsonValue projJson: projectsJson) {
			
			projects.put(projJson.getString("id"), new ProjectInfo(projJson));
		}
	}
	
	public static void createProject(String projectName, String projectPath) {
		
		projects.put(projectName, new ProjectInfo(projectName, projectPath));

		ProjectManager.generateProject(projectPath);
	}
	
	public static boolean isTreeEditor() {
		
		return Editor.currentPage.getName().equals(PageType.TREE_EDITOR);
	}
}
