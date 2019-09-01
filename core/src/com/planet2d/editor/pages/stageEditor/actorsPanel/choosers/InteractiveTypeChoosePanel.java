package com.planet2d.editor.pages.stageEditor.actorsPanel.choosers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.config.EditorTypes.ActorType;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;

public class InteractiveTypeChoosePanel extends ChooseScroll {

	public InteractiveTypeChoosePanel(Group content, ActorsPanel actorsPanel) {
		
		super(content, actorsPanel);
		
		this.addItems(
			new ChooseItem(this, ActorType.INTERACTIVE, null, "particles-icon", "Particles"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "items-icon", "Items"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "tree-icon", "Trees"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "interactive-icon", "Interactive Actors"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "character-icon", "Characters"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "tilemap-icon", "Tile Maps"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "scene-icon", "Scene Actors")
		);
	}

}
