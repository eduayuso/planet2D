package com.planet2d.editor.pages.tileMapEditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.ui.ScrollPane;

public class TileSetsScroll extends ScrollPane<TileSetItem> {
	
	public TileSetsScroll(TileSetsPanel panel) {
		
		super(createContent(panel), panel.getWidth(), panel.getHeight(), "editorPanel", "panel-gray2");
		
		this.setScrollingDisabled(true, false);
		this.cornerImage.setVisible(false);
	}

	private static Group createContent(TileSetsPanel panel) {

		Group content = new Group();
		content.setSize(panel.getWidth()-2, panel.getHeight()*10);
		return content;
	}
	
	@Override
	public void repositionItems() {
		
		if (this.content.getChildren().size == 0) return;

		float y = this.content.getHeight();
		Actor prevItem = null;
		for (Actor item: this.content.getChildren()) {
			if (prevItem != null) {
				y = prevItem.getY();
			}
			item.setY(y - item.getHeight());
			prevItem = item;
		}
		
		this.stack.imageTable.setHeight(this.content.getHeight());
		this.stack.invalidateHierarchy();
	}
}
