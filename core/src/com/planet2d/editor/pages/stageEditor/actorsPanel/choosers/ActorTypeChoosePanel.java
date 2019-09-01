package com.planet2d.editor.pages.stageEditor.actorsPanel.choosers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.config.EditorTypes.ActorType;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;

public class ActorTypeChoosePanel extends ChooseScroll {

	public ActorTypeChoosePanel(Group content, ActorsPanel actorsPanel) {
		
		super(content, actorsPanel);
		
		this.addItems(
			new ChooseItem(this, ActorType.PARTICLE, null, "particles-icon", "Particles"),
			new ChooseItem(this, ActorType.ITEM, null, "items-icon", "Items"),
			new ChooseItem(this, ActorType.TREE, null, "tree-icon", "Trees"),
			new ChooseItem(this, ActorType.INTERACTIVE, null, "interactive-icon", "Interactive Actors"),
			new ChooseItem(this, ActorType.CHARACTER, null, "character-icon", "Characters"),
			new ChooseItem(this, ActorType.TILEMAP, null, "tilemap-icon", "Tile Maps"),
			new ChooseItem(this, ActorType.SCENE, null, "scene-icon", "Scene Actors")
		);
	}
}
