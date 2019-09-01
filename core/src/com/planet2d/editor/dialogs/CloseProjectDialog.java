package com.planet2d.editor.dialogs;

import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.Engine;
import com.planet2d.engine.ui.Dialog;

public class CloseProjectDialog extends Dialog {

	public CloseProjectDialog() {
		
		super("Close Project");
		
		Label label = new Label("Your changes will be lost.\nAre you sure?");
		
		this.getContentTable().add(label).pad(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
	}

	@Override
	protected void result (Object object) {
		
		boolean res = (Boolean)object;
		
		if (res) {
			
			Editor.currentPage.dispose();
			Engine.exit();
		}
	}
}
