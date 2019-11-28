package com.planet2d.editor.history;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class State {

	private Actor actor;
	private float x;
	private float y;
	private int z;
	private float width;
	private float height;
	private float angle;
	
	public State(Actor actor) {
		
		this.actor = actor;
		this.x = actor.getX();
		this.y = actor.getY();
		this.z = actor.getZIndex();
		this.width = actor.getWidth();
		this.height = actor.getHeight();
		this.angle = actor.getRotation();
	}
	
	public void restore() {
		
		this.actor.setPosition(this.x, this.y);
		this.actor.setSize(this.width, this.height);
		this.actor.setRotation(this.angle);
	}
}
