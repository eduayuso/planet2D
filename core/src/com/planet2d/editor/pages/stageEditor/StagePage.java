package com.planet2d.editor.pages.stageEditor;

import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.engine.actors.GameActor;

public class StagePage extends ProjectPage {

	public StagePage() {
		
		super(PageType.STAGE_EDITOR);
		this.createContent();
	}

	@Override
	protected float getSplit() {
		
		return 0.775f;
	}

	@Override
	public EditorPanel createEditorPanel() {
		
		return new StagePanel(this);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void createNewProject(String newName) {
		
	}

	@Override
	public void deleteProject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveProject() {
		// TODO Auto-generated method stub
		
	}
}
