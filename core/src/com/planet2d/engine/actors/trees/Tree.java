package com.planet2d.engine.actors.trees;

import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.actors.CompositeActor;

public class Tree extends CompositeActor<Branch> {
	
	public Tree(String name) {
		super(name);
	}
	
	public Tree(String name, JsonValue json) {
		super(name, json);
	}
	
	@Override
	public void create(boolean local) {
		
		this.setSize(this.json.getInt("width"), this.json.getInt("height"));
		
		int i = 0;
		for (JsonValue branchJson: this.json.get("branches")) {
			this.add(new Branch(branchJson, this, i++, local));
		}		
	}
}
