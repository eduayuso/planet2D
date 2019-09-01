package com.planet2d.editor.pages.layersPanel;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.Editor;
import com.planet2d.editor.ui.PanelItem;
import com.planet2d.editor.ui.ScrollPane;
import com.planet2d.engine.actors.GameActor;

public class LayersScroll extends ScrollPane<LayerItem> {

	protected LayersPanel layersPanel;
	public PanelItem toDropItem;
	public LayerItemOptionsMenu itemOptionsMenu;

	public LayersScroll(LayersPanel panel) {
		
		super(createContent(panel), panel.getWidth(), panel.getHeight(), "editorPanel", "panel-gray2");
		
		this.layersPanel = panel;
		this.setScrollingDisabled(true, false);
		this.cornerImage.setVisible(false);
		
		this.itemOptionsMenu = new LayerItemOptionsMenu();
		Editor.canvas.addActor(this.itemOptionsMenu);
	}
	
	private static Group createContent(LayersPanel panel) {

		Group content = new Group();
		content.setSize(panel.getWidth()-2, panel.getHeight()*10);
		return content;
	}

	public void dropDraggedItem() {
		
		if (this.draggedItem == this.toDropItem || this.toDropItem == null) return;
		
		((LayerItem)this.toDropItem).dropItemPanel.setVisible(false);
		
		int zF = this.toDropItem.getZIndex();
		
		this.draggedItem.setZIndex(zF);
		((LayerItem)this.draggedItem).actor.setZIndex(zF);
		
		/*
		 * REPLACE ITEMS
		 */
		this.repositionItems();
		
		this.draggedItem = null;
		this.toDropItem = null;
	}

	public LayerItem findItem(GameActor actor) {

		for (LayerItem item: this.getItems()) {
			if (item.actor == actor) {
				return item;
			}
		}
		return null;
	}
	
	public LayerItem findItemByFile(String fileName) {

		for (LayerItem item: this.getItems()) {
			if (fileName.equals(item.getActorFileName())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public void selectItem(LayerItem item) {
		
		if (item == null) return;
		
		super.selectItem(item);
		item.backgroundSelected.setVisible(true);
		
		if (!item.actor.isEditorSelected()) Editor.currentPage.selectActor(item.actor);
		
		Editor.currentPage.stateBar.showActorsInfo();
	}
	
	@Override
	public void unselectItem(LayerItem item) {
		
		super.unselectItem(item);
		item.backgroundSelected.setVisible(false);
		
		if (item.actor.isEditorSelected()) Editor.currentPage.unselectActor(item.actor);
		
		Editor.currentPage.stateBar.showActorsInfo();
	}
	
	@Override
	public void unselectAllItems() {
		
		for (LayerItem item: this.items) {
			item.backgroundSelected.setVisible(false);
			Editor.currentPage.scrollPane.unselectItem(item.actor);
			Editor.currentPage.editorPanel.unselectAssociatedFiles();
		}
		super.unselectAllItems();
	}
	
	@Override
	public void deleteItem(LayerItem item) {
		
		super.deleteItem(item);
		item.actor.remove();
	}
	
}