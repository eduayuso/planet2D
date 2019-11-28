package com.planet2d.editor.dialogs;

import com.planet2d.editor.Editor;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.ui.Dialog;

public class DeleteFileDialog extends Dialog {

	public DeleteFileDialog() {
		
		super("Delete image");
		
		Label label = new Label("The actors using this image will be also deleted.\nAre you sure?");
		
		this.getContentTable().add(label).pad(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
	}

	@Override
	protected void result (Object object) {
		
		boolean res = (Boolean)object;
		
		if (res) {
			
			Editor.currentPage.editorPanel.deleteFile();
		}
	}
}
