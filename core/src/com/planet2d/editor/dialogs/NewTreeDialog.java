package com.planet2d.editor.dialogs;

import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.TextField;
import com.planet2d.engine.ui.Dialog;

public class NewTreeDialog extends Dialog {

	private TextField treeNameTextField;
	
	public NewTreeDialog() {
		
		super("Create new tree");
		
		Label label = new Label("Name: ");
		this.treeNameTextField = new TextField("");
		
		this.getContentTable().add(label).padTop(20).padLeft(20);
		this.getContentTable().add(this.treeNameTextField).padTop(20).padRight(20).padBottom(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
	}

	@Override
	protected void result (Object object) {
		
		boolean res = (Boolean)object;
		
		if (res) {
			
			Editor.currentPage.newProjectDialogResult(this.treeNameTextField.getText());
		}
	}
}
