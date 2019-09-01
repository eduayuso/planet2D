package com.planet2d.editor.pages.layersPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.planet2d.editor.ui.ActorIcon;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Panel;
import com.planet2d.editor.ui.PanelItem;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;
import com.planet2d.engine.ui.Styles.FontType;

public class LayerItem extends PanelItem {
	
	protected final static float ICON_WIDTH = 46;
	protected final static float ICON_HEIGHT = 46;
	protected final static float ICON_MARGIN = 12;
	
	protected LayersScroll layersScroll;
	public GameActor actor;
	public Panel dropItemPanel;
	private SpriteActor eye;
	private SpriteActor eyeBack;
	
	public LayerItem(LayersScroll layersScroll, GameActor actor) {
		
		super(actor.getName(), layersScroll.getWidth(), ICON_HEIGHT+ICON_MARGIN*2);

		this.layersScroll = layersScroll;
		this.actor = actor;
		
		this.create();
	}
	
	@Override
	protected void create() {
		
		this.eyeBack = new SpriteActor("editor", "ui/eye-background.png");
		this.eyeBack.setPosition(12, ICON_MARGIN);
		this.eye = new SpriteActor("editor", "ui/eye.png");
		float eyeX = this.eyeBack.getX()+this.eyeBack.getWidth()/2f-this.eye.getWidth()/2f;
		float eyeY = this.eyeBack.getY()+this.eyeBack.getHeight()/2f-this.eye.getHeight()/2f;
		this.eye.setPosition(eyeX, eyeY);
		this.eye.setTouchable(Touchable.disabled);
		this.eyeBack.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				eyeAction();
				return true;
			}
		});
		
		super.create();
		
		this.dropItemPanel = new Panel("panel-gray3", this.getWidth(), 6);
		this.dropItemPanel.setVisible(false);
		
		this.addActor(this.eyeBack);
		this.addActor(this.eye);
		this.addActor(this.dropItemPanel);
	}
	
	@Override
	protected void createBackgrounds() {

		this.createBackgrounds("panel-gray4", "panel-gray3");
	}
		
	@Override
	protected Label createLabel(String name) {

		Label label = new Label(this.getName(), FontType.VERY_SMALL);
		
		label.setPosition(this.icon.getX() + this.icon.getWidth() + 6, this.getHeight()/2f - label.getHeight()/2f);
		label.setTouchable(Touchable.disabled);
		
		return label;
	}
		
	@Override
	protected Actor createIcon() {
		
		Group iconGroup = new Group();
		
		Panel iconBackground = new Panel("panel-white", (int)ICON_WIDTH, (int)ICON_HEIGHT);
		iconGroup.addActor(iconBackground);
		
		SpriteActor icon = null;
		
		if (this.actor instanceof SpriteActor) {
			SpriteActor gactor = (SpriteActor) this.actor;
			icon = new ActorIcon(gactor, ICON_WIDTH, ICON_HEIGHT);
			icon.setPosition(iconBackground.getX(), iconBackground.getY());
			
			iconGroup.addActor(icon);
		}
		
		iconGroup.setSize(iconBackground.getWidth(), iconBackground.getHeight());
		iconGroup.setPosition(this.eyeBack.getX()+this.eyeBack.getWidth(), ICON_MARGIN);
		
		return iconGroup;
	}
	
	protected void eyeAction() {

		boolean visible = this.eye.isVisible();
		this.eye.setVisible(!visible);
		this.actor.setVisible(!visible);
	}
	
	@Override
	public void enter(Actor fromActor) {
		
		if (this.layersScroll.itemOptionsMenu.isVisible()) {
			this.backgroundHover.setVisible(false);
		}
		
		if (layersScroll.draggedItem != null) {
			layersScroll.toDropItem = this;
		}
	}

	@Override
	public void exit(Actor toActor) {
		
		if (layersScroll.draggedItem != null) {
			dropItemPanel.setVisible(false);
			
		} else if (toActor != eyeBack) {
			backgroundHover.setVisible(false);
		}
	}

	@Override
	protected void touchDownLeftButton() {
		
		if (this.layersScroll.itemOptionsMenu.isVisible()) this.layersScroll.itemOptionsMenu.hide();
			
		if (!Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) this.layersScroll.unselectAllItems();
		this.layersScroll.selectItem(this);
	}

	@Override
	protected void touchDownRightButton() {
		
		this.layersScroll.itemOptionsMenu.show(Gdx.input.getX(), Screen.getHeight() - Gdx.input.getY());
		
		int numSelected = this.layersScroll.getSelectedItems().size;
		if (numSelected < 2) this.layersScroll.unselectAllItems();
		
		this.layersScroll.selectItem(this);
	}

	@Override
	protected void touchUp() {
		
		if (layersScroll.draggedItem != null) {
			layersScroll.dropDraggedItem();
		}
		Cursors.set(CursorType.DEFAULT);
		dragging = false;
	}

	@Override
	public void touchDragged() {
		
		layersScroll.dragItem(this);
	}
	
	public String getActorFileName() {
		
		if (this.actor instanceof SpriteActor) return ((SpriteActor)this.actor).fileName;
		return null;
	}
}
