package com.planet2d.editor.pages.welcome.panels;

import com.planet2d.editor.config.ProjectInfo;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.ui.Styles.FontType;

public class ProjectInfoPanel extends Panel {
	
	private ProjectInfo project;
	private Label titleLabel;
	private Label genreLabel;
	private Label devLabel;
	private Label synopsysLabel;
	private SpriteActor icon;
	
	public ProjectInfoPanel(ProjectInfo project) {
		
		super("panel-gray", 424, 226);
		this.createContent(project);
	}

	public void createContent(ProjectInfo proj) {

		this.project = proj;
		
		/*
		 * ICON
		 */
		if (this.icon != null) this.removeActor(this.icon);
		this.icon = new SpriteActor("editor", "ui/" + this.project.id + "-icon.png");
		this.icon.setPosition(22, this.getHeight() - this.icon.getHeight() -22);
		this.addActor(this.icon);
		
		/*
		 * TITLE
		 */
		if (this.titleLabel != null) {
			this.titleLabel.setText(this.project.title);
			
		} else {
			this.titleLabel = new Label(this.project.title, FontType.MEDIUM);
			this.titleLabel.setPosition(this.icon.getX() + this.icon.getWidth() + 28, this.icon.getY() + this.icon.getHeight() - this.titleLabel.getHeight());
			this.addActor(this.titleLabel);
		}
		
		/*
		 * GENRE
		 */
		if (this.genreLabel != null) {
			this.genreLabel.setText("Genre: " + this.project.genre);
			
		} else {
			this.genreLabel = new Label("Genre: " + this.project.genre, FontType.SMALL);
			this.genreLabel.setPosition(this.titleLabel.getX(), this.titleLabel.getY() - this.titleLabel.getHeight());
			this.addActor(this.genreLabel);
		}
		
		/*
		 * DEVELOPER
		 */
		if (this.devLabel != null) {
			this.devLabel.setText("Developer: " + this.project.developer);
			
		} else {
			this.devLabel = new Label("Developer: " + this.project.developer, FontType.SMALL);
			this.devLabel.setPosition(this.titleLabel.getX(), this.genreLabel.getY() - this.genreLabel.getHeight() - 6);
			this.addActor(this.devLabel);
		}
		
		/*
		 * SYNOPSYS
		 */
		if (this.synopsysLabel != null) {
			this.synopsysLabel.setText(this.project.synopsys);
			
		} else {
			this.synopsysLabel = new Label(this.project.synopsys, FontType.SMALL);
			this.synopsysLabel.setWrap(true);
			this.synopsysLabel.setWidth(this.getWidth() - 28*2);
			this.synopsysLabel.setPosition(this.icon.getX(), this.icon.getY() - this.synopsysLabel.getHeight() - 28);
			this.addActor(this.synopsysLabel);
		}
	}
}
