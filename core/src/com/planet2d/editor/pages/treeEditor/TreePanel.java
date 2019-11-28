package com.planet2d.editor.pages.treeEditor;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.Loader;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.engine.Json;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.trees.Branch;
import com.planet2d.engine.actors.trees.Tree;

public class TreePanel extends EditorPanel {
	
	public TreeFiles filesPanel;
	public TreeLayers layersPanel;

	public TreePanel(ProjectPage page) {
		
		super(PageType.TREE_EDITOR, page);
	}
	
	public Tree getTree() {
		
		return ((TreePage)this.page).tree;
	}

	@Override
	public Table createContent() {
		
		Table content = new Table();
		
		this.filesPanel = new TreeFiles(this);
		this.layersPanel = new TreeLayers(this);
		
		content.add(this.filesPanel).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()/2f);
		content.add(this.layersPanel).align(Align.topLeft).padLeft(0).padTop(0).expand();
		
		return content;
	}

	@Override
	protected void addLoadedProject() {
		
		this.page.clearContent();
		
		Tree tree = new Tree(this.projectName, this.projectJson);
		tree.setPosition(200, 200);
		tree.create(true);
		tree.setTouchable(Touchable.childrenOnly);
				
		this.page.addContent(tree);
		this.filesPanel.checkFilesStates();
		this.layersPanel.addTree(tree);
	}
	
	@Override
	protected void addLoadedImage() {
		
		Tree tree = ((TreePage)this.page).tree;
		
		int numSameFile = 0;
		for (Actor actor: tree.getChildren()) {
			if (((Branch)actor).fileName.equals(this.projectImageName+".png")) numSameFile++;
		}
		String name = this.projectImageName;
		if (numSameFile > 0) name += "-" + numSameFile;
		
		Branch branch = new Branch(tree, this.projectImageName + ".png", name);
		tree.add(branch);
		this.page.addGameActor(branch);
		
		this.layersPanel.addBranch(branch);
	}
	
	@Override
	public void createNewProject(String newName) {
		
		// Create new dir in /textures/trees
		FileHandle newDir = Gdx.files.local("resources/" + Config.gamePath + "/textures/trees/" + newName);
		newDir.mkdirs();
		
		// Create new json file in /json/trees
		FileHandle newFile = Gdx.files.local("resources/" + Config.gamePath + "/json/trees/" + newName + ".tree");
		newFile.writeString("{"+newName+": {width:0,height:0,branches:[]}}", false);
		
		this.projectName = newName;
		this.layersPanel.updateHeader(newName);
		this.filesPanel.addNewFolder(newDir);
	}
	
	@Override
	public void loadProject(FileHandle file) {
		
		this.projectName = null;
		this.projectJson = null;
		this.projectImageName = null;
		
		if (file.isDirectory() || file.extension().equals("tree")) {
			
			this.projectName = file.nameWithoutExtension();
		
			JsonValue json = Json.parseLocal(Config.gamePath, "json/trees/"+this.projectName+".tree");
			this.showLoading();
			
			if (json != null) {
				this.projectJson = json.get(this.projectName);
				Loader.loadTree(this.projectName);
			}
		}
	}
	
	@Override
	public void loadImage(FileHandle file) {
		
		this.showLoading();
		
		this.projectJson = null;
		Resources.loadLocalTextures("resources/" + Config.gamePath + "/textures/trees/" + this.projectName + "/" + file.name());
		this.projectImageName = file.nameWithoutExtension();
	}
	
	@Override
	protected void showLoading() {

		this.loadingContent = true;
		this.loadingImage.setVisible(true);
		this.loadingImage.setX(this.layersPanel.getX() + this.layersPanel.getWidth()/2f - this.loadingImage.getWidth()/2f);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		this.separator.draw(batch, this.getX()+this.getWidth()/2f, 0, 2, this.getHeight()-this.header.getHeight()-4);
	}

	@Override
	public void clearContent() {

		this.layersPanel.scroll.clearContent();
		this.filesPanel.relistFiles();
	}

	@Override
	public void importImages(File[] files) {

		this.filesPanel.addFiles(files);
	}

	@Override
	public void deleteFile() {

		String[] deletedFileNames = this.filesPanel.deleteSelectedFiles();
		this.layersPanel.deleteItems(deletedFileNames);
	}

	public void selectItemByActor(GameActor actor) {

		this.layersPanel.selectItemByActor(actor);
		if (actor instanceof Branch) this.filesPanel.selectItemByFileName(((Branch)actor).fileName);
	}

	public void unselectItemByActor(GameActor actor) {

		this.layersPanel.unselectItemByActor(actor);
		if (actor instanceof Branch) this.filesPanel.unselectItemByFileName(((Branch)actor).fileName);
	}

	public void unselectAllItems() {

		this.layersPanel.unselectAllItems();
		this.filesPanel.unselectAllItems();
	}
	
	@Override
	public void unselectAssociatedFiles() {
		
		this.filesPanel.unselectAllItems();
	}
}
