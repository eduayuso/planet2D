package com.planet2d.editor.dialogs;

import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.tileMapEditor.TileMapPage;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.ui.Dialog;

public class DeleteTileSetDialog extends Dialog {

	public DeleteTileSetDialog() {
		
		super("Delete Tile Set");
		
		Label label = new Label("Are you sure?");
		
		this.getContentTable().add(label).pad(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
	}

	@Override
	protected void result (Object object) {
		
		boolean res = (Boolean)object;
		
		if (res) {
			((TileMapPage)Editor.currentPage).deleteTileSet();
		}
	}
}