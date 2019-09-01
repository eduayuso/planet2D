package com.planet2d.engine.stage;

import com.badlogic.gdx.utils.Json;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.actors.effects.Effect;
import com.planet2d.engine.actors.interactive.InteractiveActor;
import com.planet2d.engine.actors.items.Item;
import com.planet2d.engine.actors.characters.Character;
import com.planet2d.engine.actors.characters.npc.NPC;
import com.planet2d.engine.actors.tileMaps.TileMap;
import com.planet2d.engine.actors.trees.Tree;

public class StageJson extends Json {

	public StageJson() {
		
		super();
		this.setElementType(Stage.class, "rooms", Room.class);
		this.setElementType(Stage.class, "sceneActors", SpriteActor.class);
		this.setElementType(Stage.class, "trees", Tree.class);
		this.setElementType(Stage.class, "effects", Effect.class);
		this.setElementType(Stage.class, "tileMaps", TileMap.class);
		this.setElementType(Stage.class, "items", Item.class);
		this.setElementType(Stage.class, "characters", Character.class);
		this.setElementType(Stage.class, "interactiveActors", InteractiveActor.class);
		
		// Players and NPCs
		this.addClassTag("npc", NPC.class);
	}
}
