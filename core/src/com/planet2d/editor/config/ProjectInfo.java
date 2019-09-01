package com.planet2d.editor.config;

import com.badlogic.gdx.utils.JsonValue;

public class ProjectInfo {

	public String id;
	public String title;
	public String genre;
	public String developer;
	public String synopsys;
	
	public ProjectInfo(JsonValue projJson) {
		
		this.id = projJson.getString("id");
		this.title = projJson.getString("title");
		this.genre = projJson.getString("genre");
		this.developer = projJson.getString("developer");
		this.synopsys = projJson.getString("synopsys");
	}

	public ProjectInfo(String title, String pathName) {

		this.id = pathName;
		this.title = title;
	}
}
