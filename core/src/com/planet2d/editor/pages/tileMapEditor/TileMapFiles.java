package com.planet2d.editor.pages.tileMapEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.filesPanel.FileItem;
import com.planet2d.editor.pages.filesPanel.FilesPanel;
import com.planet2d.engine.actors.tileMaps.Tile;
import com.planet2d.engine.screens.Screen;

public class TileMapFiles extends FilesPanel {

	private TileMapPanel tileMapPanel;
	private Tile draggedTile;
	
	public TileMapFiles(TileMapPanel tileMapPanel) {
		
		super("textures/tileMaps", tileMapPanel.getWidth()/2f, tileMapPanel.getHeight());
		this.tileMapPanel = tileMapPanel;
	}

	@Override
	public void addFileToProject(FileItem item) {
		
		TileSetItem tileSetItem = this.tileMapPanel.setsPanel.scroll.getSelectedItem();
		if (tileSetItem == null) return;
		
		tileSetItem.tileSet.template.addTile();
		this.draggedTile = null;
	}

	@Override
	public void openFile(FileHandle file) {

		this.tileMapPanel.loadProject(file);
	}

	@Override
	protected void checkFilesStates() {
		
		for (FileItem item: this.scrollPanel.getItems()) item.ignoreDisable(true);
	}
	
	@Override
	public void showDraggedItem(FileItem item) {
		
		if (this.tileMapPanel.setsPanel.scroll.getSelectedItem() == null) return;
		
		if (this.draggedTile == null) {
			
			String path = item.file.path().replaceAll("resources/", "");
			this.draggedTile = new Tile(item.file.name());
			this.draggedTile.create(path);
			Editor.canvas.addActor(this.draggedTile);
			this.draggedTile.setTouchable(Touchable.disabled);
			
			TileSetItem tileSetItem = this.tileMapPanel.setsPanel.scroll.getSelectedItem();
			tileSetItem.tileSet.template.draggedTile = this.draggedTile;
			
			this.draggedTile.addAction(Actions.alpha(0.5f));
		}
		
		this.draggedTile.setPosition(Gdx.input.getX() - this.draggedTile.getWidth()/2f, Screen.getHeight()-Gdx.input.getY()-this.draggedTile.getHeight()/2f);
	}
}
