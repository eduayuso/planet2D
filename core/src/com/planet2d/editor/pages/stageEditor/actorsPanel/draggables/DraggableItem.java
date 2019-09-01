package com.planet2d.editor.pages.stageEditor.actorsPanel.draggables;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.actors.SpriteActor;

public class DraggableItem extends Group {
	
	private final static float ICON_WIDTH = 120;
	private final static float ICON_HEIGHT = 120;

	protected ActorDraggablePanel dragPanel;
	public Panel backgroundHover;
	public Panel backgroundSelected;
	private SpriteActor icon;
	public String path;
	
	public DraggableItem(ActorDraggablePanel dragPanel, String path, String name) {
		
		super();

		this.setName(name);
		this.dragPanel = dragPanel;
		this.path = path;
		this.icon = new SpriteActor(path, name, true);
		this.setSize(134, this.icon.getHeight()*1.75f);
		
		this.icon.setPosition(this.getWidth()/2f - this.icon.getWidth()/2f, this.icon.getHeight()*0.5f);
		this.icon.setTouchable(Touchable.disabled);

		this.backgroundHover = new Panel("panel-gray2", this.getWidth(), this.getHeight());
		this.backgroundSelected = new Panel("panel-white", this.getWidth(), this.getHeight());
		this.backgroundHover.setVisible(false);
		this.backgroundSelected.setVisible(false);
		
		this.addActor(this.backgroundHover);
		this.addActor(this.backgroundSelected);
		this.addActor(this.icon);
		
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
				DraggableItem.this.dragPanel.dragItem(DraggableItem.this);
			}
		});
	}
}