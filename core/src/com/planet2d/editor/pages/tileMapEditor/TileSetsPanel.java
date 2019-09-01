package com.planet2d.editor.pages.tileMapEditor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.dialogs.NewTileSetDialog;
import com.planet2d.editor.dialogs.NewTreeDialog;
import com.planet2d.editor.pages.PanelHeader;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileMapProject;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;
import com.planet2d.engine.actors.SpriteActor;

public class TileSetsPanel extends Table {
	
	private TileMapPanel tileMapPanel;
	private PanelHeader header;
	public TileSetsScroll scroll;
	private NewTileSetDialog newTileSetDialog;
	
	public TileSetsPanel(TileMapPanel tileMapPanel) {
		
		super();
		this.setSize(tileMapPanel.getWidth()/2f, tileMapPanel.getHeight()/2f);
		
		this.tileMapPanel = tileMapPanel;
		this.createHeader();
		this.add(this.header).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expandX();
		this.row();
		
		this.scroll = new TileSetsScroll(this);
		this.add(this.scroll).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expand();
		
		this.pack();
		
		this.newTileSetDialog = new NewTileSetDialog();
	}
	
	private void createHeader() {

		this.header = new PanelHeader(this, "Select a tile map", tileMapPanel.getWidth()/2f, 32, "panel-white");
		SpriteActor addSetIcon = new SpriteActor("editor", "ui/add-file-icon.png");
		addSetIcon.setPosition(this.header.getWidth() - addSetIcon.getWidth() - 12, this.header.getHeight()/2f - addSetIcon.getHeight()/2f);
		this.header.addActor(addSetIcon);
		
		addSetIcon.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				newTileSetDialog.show();
				return true;
			}
		});
	}

	public void addTileMap(TileMapProject tileMapProject) {
		
		this.updateHeader(tileMapProject.name);
		
		TileSetItem[] items = new TileSetItem[tileMapProject.sets.size];

		int n=0;
		for (TileSetConfig tileSet: tileMapProject.sets) {
			((TileMapPage)this.tileMapPanel.page).addTileSetTemplate(tileSet);
			items[n++] = new TileSetItem(this, tileSet);
		}
		
		for (int i=0; i<items.length; i++) {
			this.scroll.addItem(items[i]);
		}
	}
	
	public void updateHeader(String title) {
		
		this.header.titleLabel.setText(title);
	}
	
	public void addTileSet(TileSetConfig tileSet) {
		
		((TileMapPage)this.tileMapPanel.page).addTileSetTemplate(tileSet);
		TileSetItem newItem = new TileSetItem(this, tileSet);
		this.scroll.addItem(newItem);
	}
	
	public void deleteSelectedTileSet() {
		
		TileSetItem item = this.scroll.getSelectedItem();
		this.unselectAllItems();
		this.scroll.deleteItem(item);
	}

	public void deleteItems(String[] fileNames) {
		
		/*for (String fileName: fileNames) {
			LayerItem item = this.scroll.findItemByFile(fileName);
			this.scroll.deleteItem(item);
		}
		
		this.scroll.unselectAllItems();*/
	}
	
	public void unselectAllItems() {
		
		this.scroll.unselectAllItems();
	}
}