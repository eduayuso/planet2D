package com.planet2d.editor.dialogs;

import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.tileMapEditor.TileMapPage;
import com.planet2d.editor.pages.tileMapEditor.TileMapPanel;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.TextField;
import com.planet2d.engine.ui.Dialog;

public class NewTileSetDialog extends Dialog {

	private TextField setNameTextField;
	
	public NewTileSetDialog() {
		
		super("Create new tile set");
		
		Label label = new Label("Name: ");
		this.setNameTextField = new TextField("");
		
		this.getContentTable().add(label).padTop(20).padLeft(20);
		this.getContentTable().add(this.setNameTextField).padTop(20).padRight(20).padBottom(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
	}

	@Override
	protected void result (Object object) {
		
		boolean res = (Boolean)object;
		
		if (res) {
			((TileMapPanel)((TileMapPage)Editor.currentPage).editorPanel).newTileSetDialogResult(this.setNameTextField.getText());
		}
	}
}