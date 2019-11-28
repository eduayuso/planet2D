package com.planet2d.engine.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

public class CompositeActor<T extends GameActor> extends GameActor {
	
	protected Group container;

	public CompositeActor(String name) {
		super(name);
		this.container = new Group();
	}

	public CompositeActor(String name, JsonValue json) {
		
		super(name, json);
		this.container = new Group();
	}
	
	public void add(T actor) {
		
		this.container.addActor(actor);
	}
	
	public void remove(T actor) {
		
		this.container.removeActor(actor);
		actor.remove();
	}
	
	public Array<Actor> getChildren() {
		
		return this.container.getChildren();
	}
	
	public void normalize() {
		
		this.normalizeSize();
		this.normalizePosition();
	}
	
	private void normalizeSize() {

		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		
		for (Actor actor: this.getChildren()) {
			
			if (actor.getX() < minX) minX = actor.getX();
			else if (actor.getX()+actor.getWidth() > maxX) maxX = actor.getX()+actor.getWidth();
			
			if (actor.getY() < minY) minY = actor.getY();
			else if (actor.getY()+actor.getHeight() > maxY) maxY = actor.getY()+actor.getHeight();
		}
		
		this.setSize(maxX - minX, maxY - minY);

		for (Actor actor: this.getChildren()) {
			
			if (minX < 0) actor.setX(actor.getX() - minX);
			if (minY < 0) actor.setY(actor.getY() - minY);
		}
	}
	
	private void normalizePosition() {

		Float minX = null;
		Float maxX = null;
		Float minY = null;
		Float maxY = null;
		
		for (Actor actor: this.getChildren()) {
			
			if (minX == null) {
				minX = actor.getX();
				minY = actor.getY();

			} else {
				
				if (actor.getX() < minX) minX = actor.getX();
				if (actor.getY() < minY) minY = actor.getY();
			}
		}
		
		for (Actor actor: this.getChildren()) {
			
			actor.setPosition(actor.getX() - minX, actor.getY() - minY);
		}
	}
	
	@Override
	protected void positionChanged() {
		
		for (Actor actor: this.getChildren()) {
			T gactor = (T)actor;
			gactor.checkPositionChanged();
		}
	}
	
	@Override
	public void setVisible (boolean visible) {
		
		super.setVisible(visible);
		this.container.setVisible(visible);
	}

	@Override
	public boolean remove() {
		
		this.container.remove();
		return super.remove();
	}
	
	@Override
	public void render(Batch batch, float parentAlpha) {
		
		this.getParent().addActor(this.container);
		this.container.setPosition(this.getX(), this.getY());
		this.container.setSize(this.getWidth(), this.getHeight());
		this.container.setTransform(false);
		this.container.setTouchable(Touchable.childrenOnly);
		this.container.draw(batch, parentAlpha);
	}
}
