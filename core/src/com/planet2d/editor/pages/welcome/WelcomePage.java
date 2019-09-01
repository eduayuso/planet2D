package com.planet2d.editor.pages.welcome;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.dialogs.CreateProjectDialog;
import com.planet2d.editor.pages.Page;
import com.planet2d.editor.pages.welcome.panels.PagesPanel;
import com.planet2d.editor.pages.welcome.panels.FilesPanel;
import com.planet2d.editor.pages.welcome.panels.ItemInfo;
import com.planet2d.editor.pages.welcome.panels.ItemsPanel;
import com.planet2d.editor.pages.welcome.panels.ProjectInfoPanel;
import com.planet2d.editor.pages.welcome.panels.ProjectsItemsPanel;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.ui.Dialog;

public class WelcomePage extends Page {
	
	private Panel mainPanel1;
	private ItemsPanel projectsPanel;
	private FilesPanel filesPanel1;
	
	private Panel mainPanel2;
	private ProjectInfoPanel gamePanel;
	private ItemsPanel pagesPanel;
	private FilesPanel filesPanel2;
	
	private Dialog newProjectDialog;
	
	public WelcomePage() {
		
		super(PageType.WELCOME);
		this.createContent();
	}
	
	public void createContent() {
		
		this.mainPanel1 = this.createMainPanel1();
		this.centerActor(this.mainPanel1);
		this.mainPanel1.setPosition(this.mainPanel1.getX(), this.mainPanel1.getY());
		this.addActor(this.mainPanel1);
		this.newProjectDialog = new CreateProjectDialog();
	}

	private Panel createMainPanel1() {
		
		Panel panel = new Panel("panel-white", 634, 492);
		
		SpriteActor logo1 = new SpriteActor("editor", "ui/logo.png");
		SpriteActor logo2 = new SpriteActor("editor", "ui/logo2.png");
		this.projectsPanel = new ProjectsItemsPanel(this, "My projects", 302, 375);
		this.filesPanel1 = this.createFilesPanel1();

		logo1.setPosition(panel.getWidth()/2f - logo1.getWidth()/2f, panel.getHeight() - logo1.getHeight() - 18);
		logo2.setPosition(panel.getWidth() - logo2.getWidth() - 18, panel.getHeight() - logo2.getHeight() - 18);
		this.projectsPanel.setPosition(10, 10);
		this.filesPanel1.setPosition(this.projectsPanel.getX() + this.projectsPanel.getWidth() + 10, 10);
		
		panel.addActor(this.projectsPanel);
		panel.addActor(this.filesPanel1);
		panel.addActor(logo1);
		panel.addActor(logo2);
		
		return panel;
	}
	
	private Panel createMainPanel2(ItemInfo info) {
		
		Panel panel = new Panel("panel-white", 756, 596);
		
		SpriteActor logo1 = new SpriteActor("editor", "ui/logo.png");
		SpriteActor logo2 = new SpriteActor("editor", "ui/logo2.png");
		SpriteActor arrow = new SpriteActor("editor", "ui/back-arrow.png");
		this.gamePanel = new ProjectInfoPanel(Config.projects.get(info.id));
		this.pagesPanel = new PagesPanel("", 424, 248);
		this.filesPanel2 = this.createFilesPanel2();
		
		logo1.setPosition(panel.getWidth()/2f - logo1.getWidth()/2f, panel.getHeight() - logo1.getHeight() - 18);
		logo2.setPosition(panel.getWidth() - logo2.getWidth() - 18, panel.getHeight() - logo2.getHeight() - 18);
		arrow.setPosition(18, panel.getHeight()-arrow.getHeight()-18);
		this.pagesPanel.setPosition(10, 10);
		this.gamePanel.setPosition(10, this.pagesPanel.getY() + this.pagesPanel.getHeight() + 10);
		this.filesPanel2.setPosition(this.gamePanel.getX() + this.gamePanel.getWidth() + 10, 10);
		
		arrow.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				togglePanels(mainPanel2, mainPanel1);
				return false;
			}
		});
		
		panel.addActor(logo1);
		panel.addActor(logo2);
		panel.addActor(arrow);
		panel.addActor(this.gamePanel);
		panel.addActor(this.pagesPanel);
		panel.addActor(this.filesPanel2);
		
		return panel;
	}
	
	protected void togglePanels(Panel panel1, final Panel panel2) {

		panel1.addAction(
			Actions.sequence(
				Actions.fadeOut(0.25f),
				Actions.visible(false)
			)
		);
		
		panel2.addAction(
			Actions.sequence(
				Actions.delay(0.25f),
				Actions.fadeOut(0f),
				Actions.visible(true),
				Actions.fadeIn(0.5f)
			)
		);
	}
	
	private FilesPanel createFilesPanel2() {
		
		FilesPanel panel = new FilesPanel(301, 484);
		
		return panel;
	}

	private FilesPanel createFilesPanel1() {
		
		FilesPanel panel = new FilesPanel(302, 375);
		
		return panel;
	}
	
	public void openProjectInfoPanel(ItemInfo info) {
		
		if (this.newProjectDialog.open) return;
		
		if (info.id.equals("new-project")) {
			
			this.newProjectDialog.show();
		
		} else {
		
			if (this.mainPanel2 == null) {
				
				this.mainPanel2 = this.createMainPanel2(info);
				this.centerActor(this.mainPanel2);
				this.mainPanel2.setPosition(this.mainPanel2.getX(), this.mainPanel2.getY());
				this.mainPanel2.setVisible(false);
				this.addActor(this.mainPanel2);
				
			} else {
				
				this.gamePanel.createContent(Config.projects.get(info.id));
			}
	
			this.togglePanels(this.mainPanel1, this.mainPanel2);
		}
	}
}
