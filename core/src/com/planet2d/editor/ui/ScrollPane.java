package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.planet2d.engine.Engine;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;

public abstract class ScrollPane<T extends Actor> extends com.badlogic.gdx.scenes.scene2d.ui.ScrollPane {
	
	public Panel content;
	private float initHeight;
	public Table container;
	protected ZoomableStack stack;
	protected SpriteActor cornerImage;
	protected Array<T> items;
	protected Array<T> selectedItems;
	
	public PanelItem draggedItem;
	
	public ScrollPane(Group content, float width, float height) {
		
		this(content, width, height, "default");
	}
	
	public ScrollPane(Group content, float width, float height, String style) {
		
		this(content, width, height, "default", "panel-gray");
	}

	public ScrollPane(Group content, float width, float height, String style, String backgroundImage) {
		
		super(createWidget(content), Engine.skin, style);
		
		this.stack = (ZoomableStack) this.getWidget();
		this.container = new Table();
		
		this.container.setSize(width, height);
		this.container.add(this);
		this.setSize(width, height);
		
		// Image to cover empty corner
		this.cornerImage = new SpriteActor("editor", "ui/frame-corner.png");
		this.cornerImage.setPosition(width-this.cornerImage.getWidth()-2, -1);
		this.container.addActor(this.cornerImage);
		
		// Content to fill
		this.content = new Panel(backgroundImage, content.getWidth(), content.getHeight());
		this.initHeight = content.getHeight();
		content.addActor(this.content);
		
		this.items = new Array<T>();
		this.selectedItems = new Array<T>();
		
		this.config();
	}
	
	protected void config() {
		
		this.setFadeScrollBars(false);
		this.setSmoothScrolling(true);
		
		this.removeListener(this.getListeners().get(0));
		
		this.addListener(new InputListener() {
			
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				getStage().setScrollFocus(ScrollPane.this);
			}
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
	}
	
	private static Actor createWidget(Group content) {

		Table table = new Table();
		table.add(content);
		
		ZoomableStack stack = new ZoomableStack(table);
		
		return stack;
	}
	
	@Override
	public void invalidate() {
		
		super.invalidate();
		if (this.cornerImage != null) this.cornerImage.setPosition(this.getWidth()-this.cornerImage.getWidth(), -1);
	}

	public void setMaxScrollY() {

		this.setSmoothScrolling(false);
		this.setScrollY(this.getMaxY());
	}
	
	public void addItems(T... items) {
		
		for (T item: items) {
			
			this.addItem(item);
		}
	}
	
	public void addItem(T item) {
		
		float y = this.content.getHeight();
		if (this.items.size>0) {
			T lastItem = this.items.get(this.items.size-1);
			y = lastItem.getY();
		}
		y -= item.getHeight();
		this.items.add(item);
		
		item.setPosition(-4, y);
		
		this.content.addActor(item);
		this.stack.imageTable.setHeight(this.content.getHeight());
		this.stack.invalidateHierarchy();
	}
	
	public void clearContent() {

		for (T actor: this.items) {
			this.content.removeActor(actor);
			actor.remove();
		}
		this.content.clear();
		this.items.clear();
		this.selectedItems.clear();
	}
	
	public Array<T> getItems() {
		
		return this.items;
	}
	
	public void selectItem(T item) {
		
		if (!this.selectedItems.contains(item, true)) {
			this.selectedItems.add(item);
		}
	}
	
	public void unselectItem(T item) {
		
		this.selectedItems.removeValue(item, true);
	}
	
	public void unselectAllItems() {
		
		this.selectedItems.clear();
	}
	
	public void deleteSelectedItems() {
		
		for (T item: this.selectedItems) {
			this.deleteItem(item);
		}
		this.selectedItems.clear();
	}
	
	public void deleteItem(T item) {
		
		this.content.removeActor(item);
		item.remove();
		this.items.removeValue(item, true);
		this.repositionItems();
		this.stack.imageTable.setHeight(this.content.getHeight());
		this.stack.invalidateHierarchy();
	}
	
	public void repositionItems() {
		
		if (this.content.getChildren().size == 0) return;

		float y = this.content.getHeight() - this.content.getChildren().get(0).getHeight()*this.items.size;
		Actor prevItem = null;
		for (Actor item: this.content.getChildren()) {
			if (prevItem != null) {
				y = prevItem.getY() + item.getHeight();
			}
			item.setY(y);
			prevItem = item;
		}
	}

	public T getSelectedItem() {
		
		if (this.selectedItems == null || this.selectedItems.size == 0) return null;
		else return this.selectedItems.get(0);
	}
	
	public Array<T> getSelectedItems() {
		
		return this.selectedItems;
	}
	
	public void dragItem(PanelItem item) {

		item.dragging = true;
		this.draggedItem = item;
		Cursors.set(CursorType.DRAG_ITEM);
	}
}
