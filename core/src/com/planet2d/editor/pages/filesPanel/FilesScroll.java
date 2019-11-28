package com.planet2d.editor.pages.filesPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.Editor;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.PanelItem;
import com.planet2d.editor.ui.ScrollPane;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.ui.Styles.FontType;

public class FilesScroll extends ScrollPane<FileItem> {
	
	private FilesPanel filesPanel;
	private Label emptyListLabel;
	public FileHandle folderToOpen;
	public FileOptionsMenu fileOptionsMenu;
	
	public FilesScroll(FilesPanel panel) {
		
		super(createContent(panel), panel.getWidth(), panel.getHeight(), "editorPanel", "panel-white");
		
		this.filesPanel = panel;
		this.setScrollingDisabled(true, false);
		this.cornerImage.setVisible(false);
		this.emptyListLabel = new Label("No trees found", FontType.SMALL);
		this.emptyListLabel.setPosition(this.getWidth()/2f - this.emptyListLabel.getWidth()/2f, this.content.getHeight()-this.emptyListLabel.getHeight()*2f);
		
		this.fileOptionsMenu = new FileOptionsMenu();
		Editor.canvas.addActor(this.fileOptionsMenu);
		
		this.listFiles();
	}
	
	private static Group createContent(FilesPanel panel) {

		Group content = new Group();
		content.setSize(panel.getWidth()-2, panel.getHeight()*10);
		return content;
	}
	
	public void relistFiles() {
		
		this.content.clear();
		this.items.clear();
		this.listFiles();
	}

	public void listFiles() {

		FileHandle fileHandle = Gdx.files.local("resources/"+Config.gamePath+"/"+this.filesPanel.path);
		
		if (fileHandle.list().length == 0) {
			
			this.content.addActor(this.emptyListLabel);
			
		} else {
			
			for (FileHandle file: fileHandle.list()) {

				FileItem newItem = this.addFileItem(file);
				if (newItem != null && file.equals(this.folderToOpen)) { // Show files of selected folder
					newItem.open();
					for (FileHandle subFile: file.list()) {
						this.addFileItem(subFile);
					}
				} 
			}
		}
	}

	private FileItem addFileItem(FileHandle file) {

		FileItem fileItem = null;
		if (file.isDirectory() || file.extension().equals("png")) {
			
			if (file.extension().equals("png") && this.filesPanel.iconMode) {
				fileItem = new IconFileItem(this, file);
			} else {
				fileItem = new FileItem(this, file);
			}
			
			this.addItem(fileItem);
		}
		return fileItem;
	}
	
	public void selectItemByFileName(String fileName) {
	
		for (FileItem item: this.items) {
			if (item.file.name().equals(fileName)) {
				this.selectItem(item);
				break;
			}
		}
	}
	
	public void unselectItemByFileName(String fileName) {
		
		for (FileItem item: this.items) {
			if (item.file.name().equals(fileName)) {
				this.unselectItem(item);
				break;
			}
		}
	}

	@Override
	public void selectItem(FileItem item) {
		
		super.selectItem(item);
		item.backgroundSelected.setVisible(true);
		item.open = !item.open;
		
		if (item.file.isDirectory()) {
			if (item.open) this.folderToOpen = item.file;
			else this.folderToOpen = null;
			this.relistFiles();
			this.filesPanel.openFile(item.file);
		}
	}
	
	@Override
	public void unselectItem(FileItem item) {
		
		super.unselectItem(item);
		item.backgroundSelected.setVisible(false);
	}
	
	@Override
	public void unselectAllItems() {
		
		for (FileItem item: this.items) {
			item.backgroundSelected.setVisible(false);
		}
		super.unselectAllItems();
	}

	public void dropDraggedItem() {

		this.filesPanel.addFileToProject((FileItem) this.draggedItem);
		this.draggedItem = null;
	}
	
	@Override
	public void dragItem(PanelItem item) {
		
		super.dragItem(item);
		this.filesPanel.showDraggedItem((FileItem)item);
	}
	
	@Override
	public void addItem(FileItem item) {
		
		if (!this.filesPanel.iconMode || item.file.isDirectory()) {
			super.addItem(item);
			
		} else {
			
			float y = this.content.getHeight();
			float x = 12;
			int itemIndex = 0;
			boolean lastIsDirectory = false;
			if (this.items.size>0) {
				FileItem lastItem = this.items.get(this.items.size-1);
				itemIndex = this.items.indexOf(lastItem, true);
				y = lastItem.getY();
				lastIsDirectory = lastItem.file.isDirectory();
				if (itemIndex % 3 != 0 && !lastIsDirectory) x = lastItem.getX() + item.getWidth() + 6;
			}
			
			if (itemIndex % 3 == 0 || lastIsDirectory) y-= item.getHeight() + 6;
			
			this.items.add(item);
			
			item.setPosition(x, y);
			
			this.content.addActor(item);
			this.stack.imageTable.setHeight(this.content.getHeight());
			this.stack.invalidateHierarchy();
		}
	}
}
