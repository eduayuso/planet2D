package com.planet2d.editor.pages.tileMapEditor;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.editor.Loader;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileMapProject;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;
import com.planet2d.engine.Json;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.GameActor;

public class TileMapPanel extends EditorPanel {

	public TileMapFiles filesPanel;
	public TileSetsPanel setsPanel;

	public TileMapPanel(ProjectPage page) {
		
		super(PageType.TILEMAP_EDITOR, page);
	}

	@Override
	public Table createContent() {
		
		Table content = new Table();
		
		this.filesPanel = new TileMapFiles(this);
		this.setsPanel = new TileSetsPanel(this);
		
		content.add(this.filesPanel).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()/2f);
		content.add(this.setsPanel).align(Align.topLeft).padLeft(0).padTop(0).expand();
		
		return content;
	}

	@Override
	protected void addLoadedProject() {
		
		this.page.clearContent();
		
		TileMapProject tileMapConfig = new TileMapProject(this.projectName, this.projectJson);
		
		((TileMapPage)this.page).setTileMap(tileMapConfig);
		this.setsPanel.addTileMap(tileMapConfig);
		
		this.page.scrollPane.setScrollY(0);
	}

	@Override
	protected void addLoadedImage() {
		
		/*TileMap tileMap = ((TileMapPage)this.page).tileMap;
		
		int numSameFile = 0;
		for (Tile tile: tileMap.getTiles()) {
			if (tile.fileName.equals(this.projectImageName+".png")) numSameFile++;
		}
		String name = this.projectImageName;
		if (numSameFile > 0) name += "-" + numSameFile;
		
		Tile tile = new Tile(name, tileMap, this.projectImageName + ".png");
		tileMap.addTile(tile);
		this.page.addGameActor(tile);
		
		this.setsPanel.addTile(tile);*/
	}

	@Override
	public void loadProject(FileHandle file) {
		
		this.projectName = null;
		this.projectJson = null;
		this.projectImageName = null;
		
		if (file.isDirectory() || file.extension().equals("tile")) {
			
			this.projectName = file.nameWithoutExtension();
		
			JsonValue json = Json.parseLocal(Config.gamePath, "json/tileMaps/"+this.projectName+".tile");
			this.showLoading();
			
			if (json != null) {
				
				this.projectJson = json.get(this.projectName);
				Loader.loadTileMap(this.projectName);
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
		this.loadingImage.setX(this.setsPanel.getX() + this.setsPanel.getWidth()/2f - this.loadingImage.getWidth()/2f);
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
		this.setsPanel.updateHeader(newName);
		this.filesPanel.addNewFolder(newDir);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		this.separator.draw(batch, this.getX()+this.getWidth()/2f, 0, 2, this.getHeight()-this.header.getHeight()-4);
	}

	@Override
	public void clearContent() {
		
		this.setsPanel.scroll.clearContent();
		this.filesPanel.relistFiles();
	}

	@Override
	public void importImages(File[] files) {
		
		this.filesPanel.addFiles(files);
	}

	@Override
	public void deleteFile() {
		
		String[] deletedFileNames = this.filesPanel.deleteSelectedFiles();
		this.setsPanel.deleteItems(deletedFileNames);
	}
	
	public void selectItemByActor(GameActor actor) {

		if (actor instanceof Tile) this.filesPanel.selectItemByFileName(((Tile)actor).fileName);
	}

	public void unselectItemByActor(GameActor actor) {

		if (actor instanceof Tile) this.filesPanel.unselectItemByFileName(((Tile)actor).fileName);
	}

	public void unselectAllItems() {

		this.filesPanel.unselectAllItems();
	}
	
	@Override
	public void unselectAssociatedFiles() {
		
		this.filesPanel.unselectAllItems();
	}

	public void newTileSetDialogResult(String text) {

		TileMapProject tileMapProject = ((TileMapPage)this.page).tileMapProject;
		TileSetConfig tileSet = new TileSetConfig(tileMapProject, text);
		tileMapProject.sets.add(tileSet);
		
		this.setsPanel.addTileSet(tileSet);
	}
}
