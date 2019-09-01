package com.planet2d.editor.pages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.pages.layersPanel.LayersPanel;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.actors.SpriteActor;

public class PanelHeader extends Group {
	
	public Label titleLabel;
	private SpriteActor background;

	public PanelHeader(Actor parentPanel, String title, float width, int height, String backgroundImage) {

		super();
		
		this.setSize(width, height);
		
		this.background = new SpriteActor("editor", "ui/"+backgroundImage+".png");
		this.background.setSize(this.getWidth(), this.getHeight());
		
		this.titleLabel = new Label(title);
		this.titleLabel.setPosition(12, this.background.getHeight()/2f - this.titleLabel.getHeight()/2f - 2);
		
		this.addActor(this.background);
		this.addActor(this.titleLabel);
	}
}
