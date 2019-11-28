package com.planet2d.editor.pages.stageEditor.actorsPanel.choosers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.config.EditorTypes.ActorType;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;

public class CharacterTypeChoosePanel extends ChooseScroll {

	public CharacterTypeChoosePanel(Group content, ActorsPanel actorsPanel) {
		
		super(content, actorsPanel);
		
		this.addItems(
				
			new ChooseItem(this, ActorType.CHARACTER, "npc", "npc-icon", "NPCs"),
			new ChooseItem(this, ActorType.CHARACTER, "spawners", "spawner-icon", "Spawners"),
			new ChooseItem(this, ActorType.CHARACTER, "bosses", "boss-icon", "Bosses"),
			new ChooseItem(this, ActorType.CHARACTER, "subbosses", "subboss-icon", "SubBosses"),
			new ChooseItem(this, ActorType.CHARACTER, "enemies", "enemy-icon", "Enemies"),
			new ChooseItem(this, ActorType.CHARACTER, "players", "player-icon", "Players")
		);
	}
}
