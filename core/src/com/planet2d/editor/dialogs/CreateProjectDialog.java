package com.planet2d.editor.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.ui.Dialog;
import com.planet2d.editor.ui.TextField;

public class CreateProjectDialog extends Dialog {

	private TextField projectTitleTextField;
	private Label projectNameTextField;
	private boolean firstTouchDown;
	
	public CreateProjectDialog() {
		
		super("Create New Project");

		Label label1 = new Label("Title: ");
		this.projectTitleTextField = new TextField("");
		Label label2 = new Label("Project id: ");
		this.projectNameTextField = new Label("");
		this.projectNameTextField.setAlignment(Align.left);
		this.projectNameTextField.setWidth(150);
		
		this.getContentTable().add(label1).padTop(20).padLeft(20);
		this.getContentTable().add(this.projectTitleTextField).padTop(20).padRight(20);
		this.getContentTable().row();
		this.getContentTable().add(label2).padTop(20).padLeft(20).padBottom(20);
		this.getContentTable().add(this.projectNameTextField).left().padLeft(4).padTop(20).padRight(20).padBottom(20);
		this.button("Accept", true).padBottom(20);
		this.button("Cancel", false).padBottom(20);
		
		this.projectTitleTextField.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (!firstTouchDown) {
					firstTouchDown = true;
					projectTitleTextField.setText("");
					projectNameTextField.setText("");
				}
				return true;
			}
			@Override
			public boolean keyTyped (InputEvent event, char character) {
				if (character != ' ') {
					String name = projectTitleTextField.getText().replace(" ", "");
					projectNameTextField.setText(name);
					projectNameTextField.setAlignment(Align.left);
				}
				return false;
			}
		});
	}

	@Override
	protected void result (Object object) {
	
		boolean res = (Boolean)object;
			
		if (res) {
			
			Config.createProject(this.projectTitleTextField.getText(), this.projectNameTextField.getText().toString());
		}
	}
}
