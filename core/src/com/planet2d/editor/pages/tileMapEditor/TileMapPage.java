package com.planet2d.editor.pages.tileMapEditor;

import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.planet2d.editor.Editor;
import com.planet2d.editor.actors.TileMapOptionsMenu;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.dialogs.DeleteTileSetDialog;
import com.planet2d.editor.listeners.CanvasListener;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileConfig;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileMapProject;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;
import com.planet2d.editor.pages.tileMapEditor.tilesTemplate.TileSetTemplate;
import com.planet2d.editor.ui.Menu;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.tileMaps.Tile;
import com.planet2d.engine.actors.tileMaps.TileMap;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Dialog;
import com.planet2d.engine.ui.Cursors.CursorType;

public class TileMapPage extends ProjectPage {
	
	public TileMapProject tileMapProject;
	private ArrayMap<String, TileSetTemplate> tileSetsTemplates;
	protected Dialog deleteTileSetDialog;
	public boolean testMode;
	public TileMapGrid tileMapGrid;

	public TileMapPage() {
		
		super(PageType.TILEMAP_EDITOR);
		this.createContent();
		this.tileSetsTemplates = new ArrayMap<String, TileSetTemplate>(String.class, TileSetTemplate.class);
		this.deleteTileSetDialog = new DeleteTileSetDialog();
	}
	
	@Override
	protected Menu createActorOptionsMenu() {
		
		return new TileMapOptionsMenu();
	}

	@Override
	protected float getSplit() {
		
		return 0.775f;
	}

	@Override
	public EditorPanel createEditorPanel() {
		
		return new TileMapPanel(this);
	}
	
	@Override
	public void createContent() {
		
		super.createContent();
	}
	
	public void addTileSetTemplate(TileSetConfig tileSetConfig) {
		
		TileSetTemplate tileSetTemplate = new TileSetTemplate(tileSetConfig);
		tileSetTemplate.setPosition(0, this.content.getHeight()-tileSetTemplate.getHeight());
		this.add(tileSetTemplate);
		this.tileSetsTemplates.put(tileSetConfig.name, tileSetTemplate);
	}

	@Override
	public void createNewProject(String newName) {
		
	/*	this.tileMap = new TileMap(newName);
		this.tileMap.setPosition(200, 200);
		this.tileMap.setTiles(new Array<Tile>());
		this.tileMap.setTouchable(Touchable.childrenOnly);
		this.add(this.tileMap);*/
	}

	@Override
	public void deleteProject() {
		
		this.clearContent();
		
		/*	FileHandle jsonFile = Gdx.files.local("resources/" + Config.gamePath + "/json/tileMaps/" + this.tileMap.getZone() + "/" + this.tileMap.getName() + ".tile");
		jsonFile.delete();
		
		FileHandle textureDir = Gdx.files.local("resources/" + Config.gamePath + "/textures/tileMaps/" + this.tileMap.getZone() + "/" + this.tileMap.getName());
		textureDir.deleteDirectory();
		
		this.editorPanel.clearContent();
		
		this.tileMap.remove();
		this.tileMap = null;*/
	}

	@Override
	public void saveProject() {
		
		for (TileSetTemplate template: this.tileSetsTemplates.values) {
			if (template != null) template.updateTilesOffsetInfo();
		}
	
		FileHandle jsonFile = Gdx.files.local("resources/" + Config.gamePath + "/json/tileMaps/" + this.tileMapProject.name + ".tile");
		
		StringWriter jsonText = new StringWriter();
		JsonWriter json = new JsonWriter(jsonText);
		
		try {
			json.object();
				json.array(this.tileMapProject.name);
					for (TileSetConfig tileSet: this.tileMapProject.sets) {
						json.object()
							.set("name", tileSet.name)
							.set("pattern", tileSet.pattern)
							.set("physicsShape", tileSet.physicsShape)
							.set("physicsPadding", tileSet.physicsPadding)
							.set("meshDeformation", tileSet.meshDeformation[0]+"-"+tileSet.meshDeformation[1]+"-"+tileSet.meshDeformation[2])
							.array("tiles");
								for (TileConfig tile: tileSet.tiles) {
									json.object()
										.set("type", tile.type)
										.set("id", tile.id)
										.set("file", tile.file)
										.set("offsetX", tile.offsetX)
										.set("offsetY", tile.offsetY);
									json.pop();
								}
							json.pop();
						json.pop();
					}
				json.pop();
			json.pop();
			
			json.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		Json jsonOutput = new Json();
		jsonOutput.setOutputType(OutputType.json);
		
		jsonFile.writeString(jsonOutput.prettyPrint(jsonText.toString()), false);
	}
	
	@Override
	public void addGameActor(GameActor actor) {
		
		if (actor instanceof TileMap) {
			
			TileMap tileMap = (TileMap)actor;
			for (Actor tile: tileMap.getChildren()) {
				super.addGameActor((Tile)tile);
			}
			
		} else {
			super.addGameActor(actor);
		}
	}
	
	@Override
	public void selectActorByFileName(String fileName) {
		
		for (Tile tile: this.getSelectedTemplate().tiles) {
			if (tile.fileName.equals(fileName)) {
				this.selectActor(tile);
			}
		}
	}
	
	private TileSetTemplate getSelectedTemplate() {

		TileSetItem selectedTileSetItem = ((TileMapPanel)this.editorPanel).setsPanel.scroll.getSelectedItem();
		if (selectedTileSetItem != null) return this.tileSetsTemplates.get(selectedTileSetItem.tileSet.name);
		else return null;
	}

	@Override
	public void selectActor(GameActor actor) {
		
		super.selectActor(actor);
		if (actor instanceof Tile) {
			this.getSelectedTemplate().selectTileShape((Tile)actor);
			((TileMapPanel)this.editorPanel).selectItemByActor(actor);
		}
	}
	
	@Override
	public void unselectActor(GameActor actor) {
		
		super.unselectActor(actor);
		this.getSelectedTemplate().unselectTileShape((Tile)actor);
		((TileMapPanel)this.editorPanel).unselectItemByActor(actor);
	}
	
	@Override
	public void unselectAllActors() {
		
		this.scrollPane.unselectAllItems();
		
		if (this.getSelectedTemplate() == null) return;
		
		for (Tile tile: this.getSelectedTemplate().tiles) {
			tile.setEditorSelected(false);
			this.getSelectedTemplate().unselectAllTileShapes();
		}
		Config.showUnselectedTranslucid = false;
		
		((TileMapPanel)this.editorPanel).unselectAllItems();
	}
	
	@Override
	public void addContent(GameActor actor) {
		
		/*if (actor instanceof TileMap) {
			this.tileMap = (TileMap) actor;
		}*/
		
		super.addContent(actor);
	}
	
	@Override
	public void deleteSelectedActors() {
		
		this.getSelectedTemplate().clearSelectedTileShapes();
		super.deleteSelectedActors();
	}
	
	@Override
	public void startRectangleSelection(float x, float y) {
		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public boolean touchDown(CanvasListener canvasListener, float x, float y) {

		return super.touchDown(canvasListener, x, y);
	}
	
	@Override
	public void touchUp(CanvasListener canvasListener) {
		
		super.touchUp(canvasListener);
	}
	
	@Override
	public void touchDragged(CanvasListener canvasListener, float x, float y) {
		
		
	}
	
	public void setTileMap(TileMapProject tileMapProject) {

		this.tileMapProject = tileMapProject;
	}

	public void showDeleteTileSetDialog() {

		this.deleteTileSetDialog.show();
	}

	public void deleteTileSet() {
		
		TileSetItem currentTileSetItem = ((TileMapPanel)this.editorPanel).setsPanel.scroll.getSelectedItem();
		String tileSetName = currentTileSetItem.tileSet.name;
		
		currentTileSetItem.tileSet.tileMapProject.sets.removeValue(currentTileSetItem.tileSet, true);
		((TileMapPanel)this.editorPanel).setsPanel.deleteSelectedTileSet();
		this.tileSetsTemplates.get(tileSetName).remove();
		this.tileSetsTemplates.removeKey(tileSetName);
	}

	public void runTestMode() {

		this.testMode = !this.testMode;
		
		if (this.testMode) {
			
			TileSetItem tileSetItem = ((TileMapPanel)this.editorPanel).setsPanel.scroll.getSelectedItem();
			this.tileMapGrid = new TileMapGrid(tileSetItem.tileSet);
			this.tileMapGrid.setPosition(0, this.content.getHeight()-tileMapGrid.getHeight());
			this.add(this.tileMapGrid);
			this.tileMapGrid.setEditorEdited(true);
			this.hideTemplate();
			
		} else {
			
			this.tileMapGrid.remove();
			this.content.removeActor(this.tileMapGrid);
			this.tileMapGrid = null;
			this.showTemplate();
		}
	}

	private void showTemplate() {

		TileSetItem tileSetItem = ((TileMapPanel)this.editorPanel).setsPanel.scroll.getSelectedItem();
		tileSetItem.tileSet.template.setVisible(true);
	}
	
	private void hideTemplate() {
		
		TileSetItem tileSetItem = ((TileMapPanel)this.editorPanel).setsPanel.scroll.getSelectedItem();
		tileSetItem.tileSet.template.setVisible(false);
	}
	
	@Override
	protected void dragActor(GameActor actor, float x, float y) {
		
		if ((actor instanceof Tile) || ((actor instanceof TileMapGrid))) {
			super.dragActor(actor, x, y);
		}
	}
	
	@Override
	public void controlLeftOption(boolean pressed) {
		
		this.tileMapGrid.physics.setVisible(pressed);
	}
}
