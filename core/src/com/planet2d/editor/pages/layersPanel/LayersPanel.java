package com.planet2d.editor.pages.layersPanel;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.pages.PanelHeader;
import com.planet2d.engine.actors.GameActor;

public class LayersPanel extends Table {

	private PanelHeader header;
	public LayersScroll scroll;

	public LayersPanel(String title, float width, float height) {
		
		super();
		this.setSize(width, height);
		
		this.header = new PanelHeader(this, title, width, 32, "panel-white");
		this.add(this.header).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expandX();
		this.row();
		
		this.scroll = new LayersScroll(this);
		this.add(this.scroll).align(Align.topLeft).padLeft(0).padTop(0).width(this.getWidth()-2).expand();
		
		this.pack();
	}
	
	public void updateHeader(String title) {
		
		this.header.titleLabel.setText(title);
	}
	
	public LayerItem findItem(GameActor actor) {
		
		return this.scroll.findItem(actor);
	}
	
	public void selectItem(LayerItem item) {
		
		this.scroll.selectItem(item);
	}
	
	public void unselectItem(LayerItem item) {
		
		this.scroll.unselectItem(item);
	}
	
	public void deleteItems(String[] fileNames) {
		
		for (String fileName: fileNames) {
			LayerItem item = this.scroll.findItemByFile(fileName);
			this.scroll.deleteItem(item);
		}
		
		this.scroll.unselectAllItems();
	}

	public void deleteSelectedItems() {
		
		this.scroll.deleteSelectedItems();
	}
}
