package com.planet2d.editor.pages.stageEditor.actorsPanel.choosers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.config.EditorTypes.ActorType;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.actors.SpriteActor;

public class ChooseItem extends Group {
	
	private final static float ICON_WIDTH = 46;
	private final static float ICON_HEIGHT = 46;
	private final static float ICON_MARGIN = 12;

	protected ChooseScroll choosePanel;
	public Panel backgroundHover;
	public Panel backgroundSelected;
	private SpriteActor icon;
	public ActorType type;
	public String path;
	
	public ChooseItem(ChooseScroll choosePanel, ActorType type, String path, String iconName, String title) {
		
		super();

		this.setName(title);
		this.choosePanel = choosePanel;
		this.type = type;
		this.path = path;
		this.icon = new SpriteActor("editor", "ui/" + iconName + ".png");
		this.setSize(134, this.icon.getHeight()*1.75f);
		
		this.icon.setPosition(this.getWidth()/2f - this.icon.getWidth()/2f, this.icon.getHeight()*0.5f);

		this.backgroundHover = new Panel("panel-gray2", this.getWidth(), this.getHeight());
		this.backgroundSelected = new Panel("panel-white", this.getWidth(), this.getHeight());
		this.backgroundHover.setVisible(false);
		this.backgroundSelected.setVisible(false);
		
		Label label = new Label(title);
		label.setPosition(this.icon.getX() + this.icon.getWidth()/2f - label.getWidth()/2f, this.icon.getY() - label.getHeight() - 8);
		label.setTouchable(Touchable.disabled);
		this.icon.setTouchable(Touchable.disabled);
		
		this.addActor(this.backgroundHover);
		this.addActor(this.backgroundSelected);
		this.addActor(this.icon);
		this.addActor(label);
		
		this.addListener(new InputListener() {
					
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				
				backgroundHover.setVisible(true);
			}
			
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				
				backgroundHover.setVisible(false);
			}
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				backgroundSelected.setVisible(true);
				backgroundHover.setVisible(false);
				
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				
				backgroundSelected.setVisible(false);
				backgroundHover.setVisible(true);
				ChooseItem.this.choosePanel.selectItem(ChooseItem.this);
			}
		});
	}
}