package com.planet2d.editor.pages.filesPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.editor.Editor;
import com.planet2d.editor.ui.PanelItem;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public class FileItem extends PanelItem {
	
	protected FilesScroll filesScroll;
	protected SpriteActor iconDisabled;
	protected SpriteActor iconSelected;
	public FileHandle file;
	public boolean addedToProject;
	private boolean ignoreDisabled;
	public boolean open;
	
	public FileItem(FilesScroll filesScroll, FileHandle file) {
		
		this(filesScroll, file, filesScroll.getWidth(), 24);
	}
	
	public FileItem(FilesScroll filesScroll, FileHandle file, float width, float height) {
		
		super(file.nameWithoutExtension(), width, height);
		
		this.filesScroll = filesScroll;
		this.file = file;
		
		this.create();
	}
		
	@Override
	protected void create() {
		
		super.create();
		if (this.iconDisabled != null) this.addActor(this.iconDisabled);
		if (this.iconSelected != null) this.addActor(this.iconSelected);
	}
	
	@Override
	protected Actor createIcon() {
		
		SpriteActor icon = null;
		
		if (file.isDirectory()) {
			
			icon = new SpriteActor("editor", "ui/folder-close.png");
			icon.setPosition(18, 4);
			
			this.iconSelected = new SpriteActor("editor", "ui/folder-open.png");
			this.iconSelected.setPosition(icon.getX(), icon.getY());
			this.iconSelected.setVisible(false);
			
		} else {
			
			icon = new SpriteActor("editor", "ui/file-icon.png");
			icon.setPosition(24, 4);
			icon.setVisible(false);
			
			this.iconDisabled = new SpriteActor("editor", "ui/file-icon-disabled.png");
			this.iconDisabled.setPosition(icon.getX(), icon.getY());
		}
		
		return icon;
	}
	
	@Override
	public void enter(Actor fromActor) {
		
		if (this.filesScroll.fileOptionsMenu.isVisible()) backgroundHover.setVisible(false);
	}

	@Override
	public void exit(Actor toActor) {
	}

	@Override
	protected void touchDownLeftButton() {
		
		if (this.filesScroll.fileOptionsMenu.isVisible()) this.filesScroll.fileOptionsMenu.hide();
		
		if (!Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) Editor.currentPage.unselectAllActors();

		this.select();
	}
	
	@Override
	protected void touchDownRightButton() {
		
		this.filesScroll.fileOptionsMenu.show(Gdx.input.getX(), Screen.getHeight() - Gdx.input.getY());
		
		int numSelected = this.filesScroll.getSelectedItems().size;
		if (numSelected < 2) Editor.currentPage.unselectAllActors();
		
		this.select();
	}

	private void select() {

		if (!this.file.isDirectory()) {
			Editor.currentPage.selectActorByFileName(this.file.name());
		}
		this.filesScroll.selectItem(this);
	}

	@Override
	protected void touchUp() {

		if (this.filesScroll.draggedItem != null) {
			this.filesScroll.dropDraggedItem();
		}
		Cursors.set(CursorType.DEFAULT);
		this.dragging = false;
	}
	
	@Override
	public void touchDragged() {
		
		if (!this.file.isDirectory()) {
			this.filesScroll.dragItem(this);
		}
	}

	public void open() {

		this.open = true;
		this.icon.setVisible(false);
		this.iconSelected.setVisible(true);
	}
	
	public void addToProject() {
		
		this.addedToProject = true;
		this.icon.setVisible(true);
		if (this.iconDisabled != null) this.iconDisabled.setVisible(false);
	}
	
	public void removeFromProject() {
		
		if (!this.ignoreDisabled) {
			this.addedToProject = false;
			this.icon.setVisible(false);
			this.iconDisabled.setVisible(true);
		}
	}
	
	public void ignoreDisable(boolean set) {
		
		this.ignoreDisabled = set;
		this.addToProject();
	}
}