package com.planet2d.editor.pages;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.Editor;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.dialogs.DeleteFileDialog;
import com.planet2d.editor.ui.Button;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.Styles.FontType;
import com.planet2d.engine.ui.NinePatchDrawable;

public abstract class EditorPanel extends Group {
	
	public ProjectPage page;
	private Panel backPanel;
	private Panel frontPanel;
	protected NinePatchDrawable separator;
	public Table header;
	protected Table content;
	protected Button newButton;
	protected Button openButton;
	protected Button saveButton;
	protected Button deleteButton;
	protected SpriteActor loadingImage;
	protected boolean loadingContent;
	
	public String projectName;
	public String projectImageName;
	public JsonValue projectJson;
	
	public DeleteFileDialog confirmDeleteFile;

	public EditorPanel(PageType type, ProjectPage page) {
		
		super();
		this.setName(Page.typeToString(type));
		this.page = page;
		int width = (int) (Screen.getWidth() * (1-this.page.getSplit()));
		int height = (int) (Screen.getHeight()-Editor.window.mainToolBar.getHeight()-Editor.window.pagesTabs.getHeight());
		
		this.backPanel = new Panel("panel-white", width, height);
		this.frontPanel = new Panel("panel-gray", width*2, height);
		this.backPanel.setPosition(0, 0);
		this.frontPanel.setPosition(2, 0);
		
		this.addActor(this.backPanel);
		this.addActor(this.frontPanel);
		
		this.setSize(this.backPanel.getWidth(), this.backPanel.getHeight());
		
		this.createHeader();
		
		this.content = this.createContent();
		this.content.setSize(this.frontPanel.getWidth(), this.frontPanel.getHeight() - this.header.getHeight() - 6);
		this.content.setPosition(this.frontPanel.getX(), this.frontPanel.getY());
		this.addActor(this.content);
		
		this.loadingImage = new SpriteActor("editor", "ui/loading.png");
		this.loadingImage.setPosition(this.getWidth()/2f - this.loadingImage.getWidth()/2f, this.getHeight()/2f - this.loadingImage.getHeight()/2f);
		this.loadingImage.setOrigin(this.loadingImage.getWidth()/2f, this.loadingImage.getHeight()/2f);
		this.loadingImage.addAction(Actions.forever(Actions.rotateBy(-360, 0.5f)));
		this.loadingImage.setVisible(false);
		this.addActor(this.loadingImage);
		
		this.confirmDeleteFile = new DeleteFileDialog();
	}
	
	public abstract Table createContent();

	private void createHeader() {

		this.separator = new NinePatchDrawable("page-frame-border");
		
		this.header = new Table();
		SpriteActor icon = new SpriteActor("editor", "ui/"+this.page.type.toString() + "-icon.png");
		icon.setSize(36, 36);
		
		String title = Page.typeToString(this.page.type) + " Editor";
		Label label = new Label(title, FontType.SMALL);
		
		HorizontalGroup buttons = this.createButtons();
		
		this.header.setPosition(this.frontPanel.getX(), this.getY() + this.getHeight() - icon.getHeight()-4);
		this.header.setSize(Screen.getWidth()*(1-this.page.getSplit()), 36);
		
		this.header.add(icon).align(Align.left);
		this.header.add(label).align(Align.left).padLeft(12);
		this.header.add(buttons).expandX().align(Align.right).padRight(12);
		
		this.addActor(this.header);
	}

	private HorizontalGroup createButtons() {
		
		HorizontalGroup group = new HorizontalGroup();
		
		this.newButton = new Button("", "new-project");
		this.openButton = new Button("", "open-project");
		this.saveButton = new Button("", "save-project");
		this.deleteButton = new Button("", "delete-project");
		
		float margin = 10;
		this.newButton.padLeft(margin);
		this.openButton.padLeft(margin);
		this.saveButton.padLeft(margin);
		this.deleteButton.padLeft(this.saveButton.getWidth()+margin);
		this.deleteButton.setVisible(false);
		
		group.addActor(this.newButton);
		group.addActor(this.openButton);
		group.addActor(this.saveButton);
		group.addActor(this.deleteButton);
		group.setSize((this.newButton.getWidth()+margin)*4, 36);
		
		group.setPosition(group.getWidth() + margin*2, 0);
		
		this.newButton.setAction("newProjectDialog");
		this.openButton.setAction("openProject");
		this.saveButton.setAction("saveProject");
		this.deleteButton.setAction("deleteProject");

		return group;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		if (this.isContentLoaded()) {
		
			this.loadingContent = false;
			this.loadingImage.setVisible(false);
			this.content.setVisible(true);
			this.page.scrollPane.setMaxScrollY();
			this.deleteButton.setVisible(true);
			
			if (this.projectJson != null) {
				this.addLoadedProject();
				
			} else {
				this.addLoadedImage();
			}
		}
		
		super.draw(batch, parentAlpha);
		this.separator.draw(batch, this.getX(), this.header.getY()-2, this.getWidth(), 2); 
	}

	private boolean isContentLoaded() {

		return Resources.loaded() && this.loadingContent;
	}
	
	public void checkTabs(PanelTab panelTab) { }

	protected abstract void addLoadedProject();
	
	protected abstract void addLoadedImage();

	public abstract void loadProject(FileHandle file);
	
	public abstract void loadImage(FileHandle file);

	public abstract void createNewProject(String newName);

	public abstract void clearContent();

	public abstract void importImages(File[] files);

	public abstract void deleteFile();
	
	public abstract void unselectAssociatedFiles();
	
	protected abstract void showLoading();
}
