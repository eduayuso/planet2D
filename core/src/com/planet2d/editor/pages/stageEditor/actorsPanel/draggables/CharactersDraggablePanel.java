package com.planet2d.editor.pages.stageEditor.actorsPanel.draggables;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;

public class CharactersDraggablePanel  extends ActorDraggablePanel {

	public CharactersDraggablePanel(Group content, ActorsPanel actorsPanel, String path) {
		
		super(content, actorsPanel, "characters/"+path);
	}
	
	
}
