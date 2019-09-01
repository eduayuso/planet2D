package com.planet2d.editor.pages.welcome.panels;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.ui.Label;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.ui.Styles.FontType;

public class PanelButton extends Group {
	
	public PanelButton(final ItemsPanel panel, final ItemInfo info) {
		
		super();

		SpriteActor iconBack = new SpriteActor("editor", "ui/project-icon-background.png");
		
		Label nameLabel = new Label(info.title, FontType.VERY_SMALL);
		nameLabel.setWidth(78);
		nameLabel.setPosition(iconBack.getWidth()/2f - nameLabel.getWidth()/2f, -nameLabel.getHeight()-4);
		nameLabel.setWrap(true);
		nameLabel.setAlignment(Align.top);
		
		SpriteActor icon = new SpriteActor("editor", "ui/" + info.id + "-icon.png");
		icon.setSize(58, 58);
		icon.setPosition(2, 2);
		
		this.setSize(iconBack.getWidth() + 32, iconBack.getHeight() + 38);
		
		this.addActor(iconBack);
		this.addActor(icon);
		this.addActor(nameLabel);
		
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				panel.buttonAction(info);
				return false;
			}
		});
	}
}
