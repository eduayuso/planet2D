package com.planet2d.editor.pages.stageEditor.actorsPanel.draggables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;
import com.planet2d.editor.ui.Panel;
import com.planet2d.editor.ui.ScrollPane;
import com.planet2d.engine.Resources;
import com.planet2d.engine.config.Config;

public class ActorDraggablePanel extends ScrollPane {
	
	private ActorsPanel actorsPanel;
	private Panel content;
	private Array<FileHandle> itemFiles;
	private Array<DraggableItem> items;
	private String path;
	public boolean loaded;
	
	public ActorDraggablePanel(Group content, ActorsPanel actorsPanel) {
		
		this(content, actorsPanel, "");
	}

	public ActorDraggablePanel(Group content, ActorsPanel actorsPanel, String path) {
		
		super(content, actorsPanel.getWidth()+22, 542, "editorPanel");
		
		this.itemFiles = new Array<FileHandle>();
		this.path = path;
		this.actorsPanel = actorsPanel;
		
		this.content = new Panel("panel-gray", content.getWidth(), content.getHeight());
		content.addActor(this.content);
		
		this.items = new Array<DraggableItem>();
		
		this.setScrollingDisabled(true, false);
		this.cornerImage.setVisible(false);
		
		this.setPosition(0, -42);
	}
	
	public void load() {

		this.actorsPanel.loadingImage.setVisible(true);
		
		FileHandle fileHandle = Gdx.files.local("resources/"+Config.gamePath+"/textures/"+this.path);
		for (FileHandle file: fileHandle.list()) {
			Resources.loadLocalTextures(file.path());
			this.itemFiles.add(file);
		}
	}
	
	// Images just loaded
	public void addLoadedContent() {

		this.loaded = true;
		this.setVisible(true);
		
		for (FileHandle itemFile: itemFiles) {
			
			DraggableItem item = new DraggableItem(this, itemFile.path(), itemFile.nameWithoutExtension());
			this.addItem(item, itemFiles.size);
		}
	}
	
	private void addItem(DraggableItem item, int itemSize) {
		
		float y = this.content.getHeight()-itemSize*item.getHeight();
		if (this.items.size>0) {
			Actor lastItem = this.items.get(this.items.size-1);
			y = lastItem.getY() + item.getHeight();
		}
		this.items.add(item);
		
		item.setPosition(-4, y);
		
		this.content.addActor(item);
		this.stack.imageTable.setHeight(this.content.getHeight());
		this.stack.invalidateHierarchy();
	}
	
	public void dragItem(DraggableItem draggableItem) {

		
	}
}
