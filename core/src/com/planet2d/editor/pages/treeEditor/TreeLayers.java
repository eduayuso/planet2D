package com.planet2d.editor.pages.treeEditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.editor.pages.layersPanel.LayerItem;
import com.planet2d.editor.pages.layersPanel.LayersPanel;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.trees.Branch;
import com.planet2d.engine.actors.trees.Tree;

public class TreeLayers extends LayersPanel {
	
	public TreeLayers(TreePanel treePanel) {
		
		super("Select a tree", treePanel.getWidth()/2f, treePanel.getHeight());
	}

	public void addTree(Tree tree) {
		
		this.updateHeader(tree.getName());
		
		LayerItem[] items = new LayerItem[tree.getChildren().size];

		for (Actor branch: tree.getChildren()) {
			items[branch.getZIndex()] = new LayerItem(this.scroll, (Branch)branch);
		}
		
		for (int i=items.length-1; i>=0; i--) {
			this.scroll.addItem(items[i]);
		}
		
		this.scroll.content.getChildren().reverse();
	}
	
	public void addBranch(Branch branch) {
		
		LayerItem item = new LayerItem(this.scroll, branch);

		this.scroll.addItem(item);
		item.toBack();
		item.actor.setZIndex(item.getZIndex());
	}

	public void selectItemByActor(GameActor actor) {

		LayerItem item = this.findItem(actor);
		this.selectItem(item);
	}
	
	public void unselectItemByActor(GameActor actor) {

		LayerItem item = this.findItem(actor);
		this.unselectItem(item);
	}
	
	public void unselectAllItems() {
		
		this.scroll.unselectAllItems();
	}
}
