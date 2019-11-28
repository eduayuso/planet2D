package com.planet2d.editor.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.planet2d.engine.ui.Cursors;
import com.planet2d.engine.ui.Cursors.CursorType;
import com.planet2d.editor.Editor;
import com.planet2d.editor.actors.OriginActor;
import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.TileMapGrid;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.tileMaps.Tile;
import com.planet2d.engine.config.Config;

public class ActorListener extends ClickListener {
	
	private GameActor actor;
	public static boolean touchOutsideActors;
	public static Integer dragStartX;
	public static Integer dragStartY;
	
	public ActorListener(GameActor actor) {
		this.actor = actor;
		touchOutsideActors = true;
	}

	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
		if (Editor.currentPage.isOptionsMenuVisible()) return true;
		
		super.touchDown(event, x, y, pointer, button);
		
		this.touchDownActor(x, y);
		
		if (button == Buttons.RIGHT && this.actor != null && !this.actor.isEditorEdited()) {
			this.openActorPopup();
		}

		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

		if (Editor.currentPage.isOptionsMenuVisible()) return;

		super.touchUp(event, x, y, pointer, button);
		if (button == Buttons.LEFT) this.touchUpActor(x, y);
	}
	
	public void touchDownActor(float x, float y) {

		ActorListener.touchOutsideActors = false; // Next canvas touch events will know at least one actor was touched
		
		if (Editor.currentPage.isEditingActor()) return; 
		
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			
			if (this.actor.isEditorSelected()) {
				Editor.currentPage.unselectActor(this.actor);
			} else {
				Editor.currentPage.selectActor(this.actor);
			}
			
		} else if (!this.actor.isEditorSelected()) {
			
			int numSelected = Editor.currentPage.getSelectedGameActors().size;
			if (numSelected <= 2) Editor.currentPage.unselectAllActors();
			Editor.currentPage.selectActor(this.actor);
		}
	}

	private void touchUpActor(float x, float y) {
		
		if (Editor.currentPage.isEditingActor()) return; 

		// Reset drag flags
		if (dragStartX != null) {
		//	History.saveSnapshot();
		}
		dragStartX = null;
		dragStartY = null;
		
		Editor.currentPage.stateBar.showActorsInfo();
		
		if (this.getTapCount() == 2) {
			actor.setEditorEdited(true);
			Config.showUnselectedTranslucid = true;
		} else if (!this.actor.isEditorEdited()) {
			Config.showUnselectedTranslucid = false;
		}
	}
	
	private void openActorPopup() {
		
		Editor.currentPage.showActorOptionsMenu();
	}

	@Override
	public void touchDragged (InputEvent event, float x, float y, int pointer) {
		
		if (!Gdx.input.isButtonPressed(Buttons.LEFT)) return;
		
		if (Editor.currentPage.isEditingActor()) return; 
		
		if (!this.actor.isEditorEdited()) {
		
			if (dragStartX == null) dragStartX = (int) x;
			if (dragStartY == null) dragStartY = (int) y;
			Editor.currentPage.dragActors(x - dragStartX, y - dragStartY);
			Config.showUnselectedTranslucid = true;
			Editor.currentPage.stateBar.showActorsInfo();
		}
	}

	@Override
	public boolean mouseMoved (InputEvent event, float x, float y) {
		return false;
	}

	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
		
		if (!Editor.currentPage.rectangleSelector.active && !Config.showUnselectedTranslucid) {
			this.actor.setEditorHovered(true);
		}
		
		if (this.actor.isEditorEdited()) {
			if (!ignoreEnterExit(fromActor)) Cursors.set(CursorType.DEFAULT);
		}
	}

	@Override
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
		
		if (this.actor.isEditorEdited()) {
			if (!ignoreEnterExit(toActor) && !ignoreCursorRotate()) Cursors.set(CursorType.ROTATE);
			
		} else {
			this.actor.setEditorHovered(false);
		}
	}
	
	private boolean ignoreCursorRotate() {

		return (this.actor instanceof Tile) || (this.actor instanceof TileMapGrid);
	}

	private boolean ignoreEnterExit(Actor actor) {
		
		return actor != null && actor.getName() != null && (actor.isDescendantOf(this.actor) || (actor instanceof OriginActor));
	}

	@Override
	public boolean scrolled (InputEvent event, float x, float y, int amount) {
		return false;
	}

	@Override
	public boolean keyDown (InputEvent event, int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (InputEvent event, int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (InputEvent event, char character) {
		return false;
	}
}
