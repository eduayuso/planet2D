package com.planet2d.editor.pages.welcome.panels;

import com.planet2d.editor.config.EditorTypes.PageType;

public class ItemInfo {

	public String title;
	public PageType type;
	public String id;
	
	public ItemInfo(String title, PageType type) {
		
		this.title = title;
		this.type = type;
		this.id = this.pageTypeToString();
	}
	
	private String pageTypeToString() {
		
		return type.toString();
	}

	public ItemInfo(String title, String id) {
		
		this.title = title;
		this.id = id;
	}

	public String getId() {
		return null;
	}
}
