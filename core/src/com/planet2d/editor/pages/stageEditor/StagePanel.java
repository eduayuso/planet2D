package com.planet2d.editor.pages.stageEditor;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;
import com.planet2d.editor.pages.stageEditor.layersPanel.StageLayersPanel;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.TextField;

public class StagePanel extends EditorPanel {

	private TextField titleField;
	private TextField songField;
	
	private ActorsPanel actorsPanel;
	private StageLayersPanel layersPanel;

	public StagePanel(ProjectPage page) {
		
		super(PageType.STAGE_EDITOR, page);
		this.content.setPosition(0, 8);
	}
	
	@Override
	public Table createContent() {
		
		Table content = new Table();

		Label nameLabel = new Label("Title:");
		content.add(nameLabel).padLeft(16).padTop(0).align(Align.topLeft).width(130);
		
		Label songLabel = new Label("Song:");
		content.add(songLabel).padLeft(16).padTop(0).align(Align.topLeft).width(130);
		
		content.row();
		
		this.titleField = new TextField("");
		content.add(this.titleField).padLeft(16).padTop(4).align(Align.topLeft).width(130);
		
		this.songField = new TextField("");
		content.add(this.songField).padLeft(16).padTop(4).align(Align.topLeft).width(130);
		
		content.row();
		
		this.actorsPanel = new ActorsPanel(this);
		this.layersPanel = new StageLayersPanel(this);
		
		content.add(this.actorsPanel).align(Align.topLeft).padLeft(2).padTop(18).width(130);
		content.add(this.layersPanel).align(Align.topLeft).padLeft(2).padTop(18).expand();
		
		return content;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		this.separator.draw(batch, this.getX(), this.getHeight()-118, this.getWidth(), 2);
		
		this.separator.draw(batch, this.getX()+152, 0, 2, this.getHeight()-118);
	}

	@Override
	public void createNewProject(String newName) {
		
	}

	@Override
	public void loadProject(FileHandle file) {
		
	}
	
	@Override
	public void loadImage(FileHandle file) {
		
	}

	@Override
	public void clearContent() {
		
	}

	@Override
	public void importImages(File[] files) {
		
	}

	@Override
	protected void addLoadedProject() {
		
	}

	@Override
	protected void addLoadedImage() {
		
	}

	@Override
	public void deleteFile() {
		
	}

	@Override
	public void unselectAssociatedFiles() {
		
	}

	@Override
	protected void showLoading() {
		// TODO Auto-generated method stub
		
	}
}
