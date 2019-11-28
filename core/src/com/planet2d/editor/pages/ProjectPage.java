package com.planet2d.editor.pages;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.planet2d.editor.Editor;
import com.planet2d.editor.actors.ActorOptionsMenu;
import com.planet2d.editor.actors.EditorDebug;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.dialogs.CloseProjectDialog;
import com.planet2d.editor.dialogs.DeleteProjectDialog;
import com.planet2d.editor.history.History;
import com.planet2d.editor.layouts.RectangleSelector;
import com.planet2d.editor.listeners.ActorListener;
import com.planet2d.editor.listeners.CanvasListener;
import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.StateBar;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.actors.trees.Branch;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Dialog;
import com.planet2d.engine.ui.Cursors.CursorType;

public abstract class ProjectPage extends Page {
	
	public RectangleSelector rectangleSelector;
	public Group splitPane;
	public PageScroll scrollPane;
	public EditorPanel editorPanel;
	public StateBar stateBar;
	public History history;
	public Group content;
	protected Dialog newProjectDialog;
	protected Dialog deleteProjectDialog;
	protected Dialog closeProjectDialog;
	protected Menu actorOptionsMenu;
	public float zoom = 0;
	
	public ProjectPage(PageType type) {
		
		super(type);
		
		this.history = new History();
		
		this.closeProjectDialog = new CloseProjectDialog();
		this.actorOptionsMenu = this.createActorOptionsMenu();
		this.deleteProjectDialog = new DeleteProjectDialog();
		Editor.canvas.addActor(this.actorOptionsMenu);
	}

	protected Menu createActorOptionsMenu() {

		return new ActorOptionsMenu();
	}

	public void createContent() {
		
		Editor.currentPage = this;
		
		Actor leftWidget = this.createLeftContent();
		Actor rightWidget = this.createRightContent();
		
		this.splitPane = new PageSplitPane(this.getSplit(), leftWidget, rightWidget);
		this.splitPane.setSize(this.getWidth(), this.getHeight());
		this.addActor(this.splitPane);
	}
	
	public void addContent(GameActor actor) {
		
		this.add(actor);
	}

	protected abstract float getSplit();

	private Actor createLeftContent() {
		
		Group group = new Group();
		
		// Bottom state bar
		this.stateBar = new StateBar(Screen.getWidth(), 20);
		group.addActor(this.stateBar);
		
		// Main content (Game actors, etc.)
		this.content = new Group();
		this.content.setSize(Screen.getWidth()*20f, Screen.getHeight()*10f);
		this.content.addListener(new CanvasListener());
		
		// Scroll pane
		float barsHeight = Editor.window.getMainToolBar().getHeight() + Editor.window.getPagesTabs().getHeight() + Editor.currentPage.stateBar.getHeight();
		float width = Editor.window.getMainToolBar().getWidth() - 4;
		float height = Screen.getHeight()-barsHeight-4;
		this.scrollPane = new PageScroll(this.content, width, height);
		this.scrollPane.container.setPosition(2, this.stateBar.getHeight());
		group.addActor(this.scrollPane.container);
		
		// Rectangle for select actors
		this.rectangleSelector = new RectangleSelector();
		this.content.addActor(this.rectangleSelector);
		
		group.setSize(this.scrollPane.getWidth(), this.scrollPane.getHeight());
		
		return group;
	}

	// Edition panel at the right
	private Actor createRightContent() {
		
		this.editorPanel = this.createEditorPanel();
		float x = Screen.getWidth()-this.editorPanel.getWidth();
		float y = 0;
		this.editorPanel.setPosition(x, 0);
		
		return this.editorPanel;
	}

	public abstract EditorPanel createEditorPanel();

	public void zoom(int amount) {

		float newZoom = this.zoom + amount;
		if (this.scrollPane.zoom(newZoom)) {
			this.zoom = newZoom;
			this.stateBar.updateCanvasInfo();
		}
	}
	
	public void clearContent() {
		
		this.scrollPane.clearContent();
		this.editorPanel.clearContent();
	}
	
	public void add(Actor actor) {
		
		this.content.addActor(actor);
		
		if (actor instanceof GameActor) {
			this.addGameActor((GameActor)actor);
		}
		
		this.history.saveSnapshot();
	}
	
	public void addGameActor(GameActor actor) {
		
		this.getGameActors().add(actor);
		actor.editorDebug = new EditorDebug(actor);
		this.content.addActor(actor.editorDebug.originIndicator);
		actor.addListener(new ActorListener(actor));
	}
	
	public Array<GameActor> getGameActors() {

		return this.scrollPane.getItems();
	}
	
	public Array<GameActor> getSelectedGameActors() {

		return this.scrollPane.getSelectedItems();
	}

	public void setScrollTouchable(boolean touchable) {
		
		if (this.scrollPane != null) {
			if (touchable) this.scrollPane.setTouchable(Touchable.enabled);
			else this.scrollPane.setTouchable(Touchable.disabled);
		}
	}
	
	public void selectActor(GameActor actor) {
		
		this.scrollPane.selectItem(actor);
	}
	
	public void selectActorByFileName(String fileName) {
		
		for (GameActor actor: this.getGameActors()) {
			
			if (actor instanceof SpriteActor) {
		
				if (((SpriteActor)actor).fileName.equals(fileName)) {
					this.selectActor(actor);
				}
			}
		}
	}
	
	public void unselectActor(GameActor actor) {
		
		this.scrollPane.unselectItem(actor);
	}
	
	public void unselectActorByFileName(String fileName) {
		
		/*for (GameActor actor: this.getGameActors()) {
		
			if (actor.fileName.equals(fileName)) {
				this.unselectActor(actor);
			}
		}*/
	}
	
	public void unselectAllActors() {
		
		this.scrollPane.unselectAllItems();
		Config.showUnselectedTranslucid = false;
	}
	
	public void dragActors(float x, float y) {

		for (GameActor actor: this.getSelectedGameActors()) {
			this.dragActor(actor, x, y);
		}
	}
	
	protected void dragActor(GameActor actor, float x, float y) {
		
		actor.setPosition(actor.getX() + x, actor.getY() + y);
	}
	
	public void rotateActor(GameActor actor, float x, float y) {
		
		if (!actor.editorDebug.movingOrigin) {
			float radians = (x+y)/100;
			actor.setRotation(MathUtils.radiansToDegrees*radians);
			if (actor instanceof Branch) ((Branch)actor).removeRotateAction();
			this.stateBar.showActorsInfo();
		}
	}
	
	public void startRectangleSelection(float x, float y) {

		this.rectangleSelector.toFront();
		this.rectangleSelector.start(x, y);
	}
	
	public void updateRectangleSelector(float x, float y) {
		
		if (this.rectangleSelector.active) {
			this.rectangleSelector.update(x, y);
		}
	}

	public void endRectangleSelection() {
		
		for (Actor actor: this.content.getChildren()) {
			if (actor instanceof GameActor) {
				this.checkIfSelectedByRectangle((GameActor)actor);
			}
		}
	}
	
	protected void checkIfSelectedByRectangle(GameActor actor) {
		
		if (this.rectangleSelector.overlaps(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight())) {
			this.selectActor(actor);
		}
	}
	
	public boolean isEditingActor() {
		
		return this.getSelectedGameActors().size == 1 && this.getSelectedGameActors().get(0).isEditorEdited() || this.actorOptionsMenu.isVisible();
	}
	
	public boolean isOptionsMenuVisible() {
		
		return this.actorOptionsMenu.isVisible();
	}
	
	public void newProjectDialogResult(Object result) {

		String newName = (String)result;
		this.newProjectDialog.hide();
		this.clearContent();
		this.createNewProject(newName);
		this.editorPanel.createNewProject(newName);
	}

	public abstract void createNewProject(String newName);
	
	public abstract void deleteProject();
	
	public abstract void saveProject();

	public abstract void dispose();
	
	public void showNewProjectDialog() {

		this.newProjectDialog.show();
	}
	
	public void showDeleteProjectDialog() {

		this.deleteProjectDialog.show();
	}
	
	public void showCloseProjectDialog() {
		
		this.closeProjectDialog.show();
	}
	
	public void showActorOptionsMenu() {

		this.actorOptionsMenu.show(Gdx.input.getX() - 10, Screen.getHeight() - Gdx.input.getY() + 10);
	}
	
	public void hideActorOptionsMenu() {
	
		this.actorOptionsMenu.hide();
	}
	
	public void importImages(File[] files) {
		this.editorPanel.importImages(files);
	}

	public void deleteSelectedActors() {
		
		this.scrollPane.deleteSelectedItems();
	}

	public void moveActor(int x, int y) {
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			x *= 10;
			y *= 10;
		}
		
		for (GameActor actor: this.getSelectedGameActors()) {
			actor.setPosition(actor.getX()+x, actor.getY()+y);
		}
		this.stateBar.showActorsInfo();
	}

	public boolean touchDown(CanvasListener canvasListener, float x, float y) {

		if (this.isOptionsMenuVisible()) return true;
		
		this.scrollPane.setFlickScroll(false);
		
		if (ActorListener.touchOutsideActors) {
			if (canvasListener.leftButtonPressed) {
				this.startRectangleSelection(x,y);
			} else {
				this.scrollPane.setFlickScroll(true);
			}
		}
		
		return true;
	}

	public void touchUp(CanvasListener canvasListener) {

		this.scrollPane.setFlickScroll(false);
		
		if (this.isOptionsMenuVisible()) return;
		
		if (Editor.currentPage.isEditingActor() && canvasListener.dragInitX != null) {

			canvasListener.dragInitX = null;
			canvasListener.dragInitY = null;

		} else {
		
			// If touch outside actors, unselect all actors
			if (ActorListener.touchOutsideActors) {
				this.unselectAllActors();
			}
			
			if (this.rectangleSelector.isVisible()) {
				this.endRectangleSelection();
			}
			this.rectangleSelector.end();
	
			// This is the last event (actor events fire before this): restart flag
			ActorListener.touchOutsideActors = true;
		}
	}

	public void touchDragged(CanvasListener canvasListener, float x, float y) {

		if (this.isOptionsMenuVisible()) return;
		if (!canvasListener.leftButtonPressed) return;
		
		if (this.isEditingActor()) {
				
			if (canvasListener.dragInitX == null) {
				canvasListener.dragInitX = (int) x;
				canvasListener.dragInitY = (int) y;
			}
			float rx = canvasListener.dragInitX - x;
			float ry = canvasListener.dragInitY - y;
			this.rotateActor(this.getSelectedGameActors().get(0), rx, ry);
			
		} else {
		
			this.updateRectangleSelector(x, y);
		}
	}

	public void controlLeftOption(boolean pressed) {
		
	}
}
