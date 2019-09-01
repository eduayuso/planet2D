package com.planet2d.editor.pages.filesPanel;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.Actions;
import com.planet2d.editor.dialogs.AlertDialog;
import com.planet2d.editor.pages.PanelHeader;
import com.planet2d.editor.ui.PanelItem;
import com.planet2d.engine.actors.SpriteActor;

public abstract class FilesPanel extends Table {

	public String path;
	private PanelHeader header;
	protected FilesScroll scrollPanel;
	private SpriteActor fileModeIcon;
	private SpriteActor imageModeIcon;
	protected AlertDialog alertDialog;
	protected boolean iconMode;
	
	public FilesPanel(String path, float width, float height) {

		super();
		this.path = path;
		this.setSize(width, height);
		this.iconMode = true;
		
		this.header = this.createHeader("Files");
		this.add(this.header).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expandX();
		this.row();
		
		this.scrollPanel = new FilesScroll(this);
		this.add(this.scrollPanel).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expand();
		
		this.alertDialog = new AlertDialog("No project selected", "Select a project folder to import the images in");
		
		this.pack();
	}

	private PanelHeader createHeader(String name) {

		PanelHeader header = new PanelHeader(this, name, this.getWidth(), 32, "panel-gray2");
		
		// Files show mode icon
		
		this.fileModeIcon = new SpriteActor("editor", "ui/file-mode.png");
		this.fileModeIcon.setPosition(header.getWidth() - this.fileModeIcon.getWidth() - 32, header.titleLabel.getY());
		header.addActor(this.fileModeIcon);
		
		this.fileModeIcon.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				setIconMode(false);
				return false;
			}
		});
		this.fileModeIcon.setVisible(this.iconMode);
		
		this.imageModeIcon = new SpriteActor("editor", "ui/icon-mode.png");
		this.imageModeIcon.setPosition(this.fileModeIcon.getX(), this.fileModeIcon.getY());
		header.addActor(this.imageModeIcon);
		
		this.imageModeIcon.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				setIconMode(true);
				return false;
			}
		});
		this.imageModeIcon.setVisible(!this.iconMode);
		
		
		// Add files icon
		
		SpriteActor addFileButton = new SpriteActor("editor", "ui/add-file-icon.png");
		addFileButton.setPosition(header.getWidth() - addFileButton.getWidth() - 12, header.titleLabel.getY());
		header.addActor(addFileButton);
		
		addFileButton.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				if (scrollPanel.getSelectedItem() == null) alertDialog.show();
				else Actions.importImageFiles();
				return false;
			}
		});
		
		return header;
	}
	
	protected void setIconMode(boolean b) {

		this.fileModeIcon.setVisible(b);
		this.imageModeIcon.setVisible(!b);
		this.iconMode = b;
		this.relistFiles();
	}

	public void addNewFolder(FileHandle newDir) {

		this.scrollPanel.folderToOpen = newDir;
		this.scrollPanel.relistFiles();
	}
	
	public void relistFiles() {

		this.scrollPanel.relistFiles();
		this.checkFilesStates();
	}

	protected abstract void checkFilesStates();

	public abstract void openFile(FileHandle file);

	public void addFiles(File[] files) {

		FileItem item = (FileItem) this.scrollPanel.getSelectedItem();
		
		for (File file: files) {
			new FileHandle(file).copyTo(item.file);
		}
		
		this.relistFiles();
	}

	public void addFileToProject(FileItem item) {

		item.addToProject();
	}
	
	public String[] deleteSelectedFiles() {
		
		String[] filesToDelete = new String[this.scrollPanel.getSelectedItems().size];

		int i=0;
		for (FileItem item: this.scrollPanel.getSelectedItems()) {
			
			filesToDelete[i++] = item.file.name();
			item.file.delete();
			this.scrollPanel.deleteItem(item);
		}
		
		return filesToDelete;
	}
	
	public void selectItemByFileName(String fileName) {

		this.scrollPanel.selectItemByFileName(fileName);
	}
	
	public void unselectItemByFileName(String fileName) {

		this.scrollPanel.unselectItemByFileName(fileName);
	}
	
	public void unselectAllItems() {
		
		this.scrollPanel.unselectAllItems();
	}

	public void showDraggedItem(FileItem item) {
		
	}
}
