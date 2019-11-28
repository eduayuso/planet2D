package com.planet2d.engine.stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.IntMap;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.actors.characters.Character;
import com.planet2d.engine.actors.effects.Effect;
import com.planet2d.engine.actors.interactive.InteractiveActor;
import com.planet2d.engine.actors.items.Item;
import com.planet2d.engine.actors.tileMaps.TileMap;
import com.planet2d.engine.actors.trees.Tree;
import com.planet2d.engine.stage.Room;

public class Stage {
	
	private Array<Layer> layers;
	private IntMap<Array<Layer>> layerMap;
	private ArrayMap<String, Layer> loopLayers;
	
	private Array<Room> rooms;
	private Room currentRoom;
	private Room startingRoom;
	private String title;
	private String zone;
	
	private Array<SpriteActor> spriteActors; // Backgrounds, etc..
	private Array<TileMap> tileMaps;
	private Array<InteractiveActor> interactiveActors;
	private Array<Tree> trees;
	private Array<Item> items;
	private Array<Effect> effects;
	private Array<Character> characters;
	private String sky;
	private String song;
	
	public Vector2 playerStartPosition;
	public boolean active;
	public boolean clear;
	public boolean mustShowGoIndicators;
	
	public Stage() {}
	
	public void create() {
		
		this.createLayers();
		
		this.createGameActors(this.spriteActors);
		this.createGameActors(this.tileMaps);
		this.createGameActors(this.interactiveActors);
		this.createGameActors(this.trees);
		this.createGameActors(this.items);
		this.createGameActors(this.effects);
		this.createGameActors(this.characters);
	}

	private void createLayers() {

		this.layerMap = new IntMap<Array<Layer>>();
		this.layerMap.put(0, new Array<Layer>());
		this.layerMap.put(1, new Array<Layer>());
		this.layerMap.put(2, new Array<Layer>());
		
		for (Layer layer: this.layers) {
			
			this.layerMap.get(layer.getType()).add(layer);
		}
		
		/*
		 * CREATE EXTRA LAYER FOR PLAYERS
		 */
		Layer extraLayer = new Layer();
		extraLayer.setType(1);
		extraLayer.setIndex(this.layerMap.get(1).size);
		extraLayer.setLoop('-');
		this.layerMap.get(1).add(extraLayer);
	}
	
	private void createGameActors(Array<? extends GameActor> objectsArray) {

		if (objectsArray == null) return;
		int size = objectsArray.size;
		for (int i=0; i<size; i++) this.createGameActor(objectsArray.get(i));
	}
	
	private void createGameActor(GameActor object) {
		
		boolean cancelCreate = false;
		
		if (!cancelCreate) {
			object.create();
			this.addGameActor(object); 
		}
	}

	public void addGameActor(GameActor object) {
		
		Array<Layer> layerList = this.layerMap.get(object.layerType);
		layerList.get(object.layerIndex).addGameActor(object);
	}

	public Array<Character> getCharacters() {
		return null;
	}

	public void checkPlatformsDoors() {
		
	}

}
