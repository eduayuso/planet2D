package com.planet2d.editor.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.planet2d.engine.Engine;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.SpriteActor;

public class OriginActor extends SpriteActor {
	
	private GameActor parent;
	
	public OriginActor(GameActor p) {
		
		super("actor-origin");
		this.parent = p;
		this.create(new Sprite(Resources.getTexture("editor", "ui/"+this.getName()+".png")));
	}
	
	@Override
	public void create(Sprite sprite) {
		
		super.create(sprite);
		this.setOrigin(this.getWidth()/2f, this.getHeight()/2f);
		this.updatePosition();
		this.addListener(new OriginActorListener(this));
	}
	
	public void updatePosition() {

		this.setPosition(this.parent.getX() + this.parent.getOriginX() - this.getWidth()/2f,
						 this.parent.getY() + this.parent.getOriginY() - this.getHeight()/2f);
	}

	public class OriginActorListener extends InputListener {

		private OriginActor actor;
		
		public OriginActorListener(OriginActor actor) {
			this.actor = actor;
		}
		
		@Override
		public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
		}
		
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			
			return true;
		}
		
		@Override
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			
			this.actor.parent.editorDebug.movingOrigin = false;
		}
		
		@Override
		public void touchDragged (InputEvent event, float x, float y, int pointer) {
			
			this.actor.parent.editorDebug.movingOrigin = true;
			float newX = this.actor.parent.getOriginX() - this.actor.getWidth()/2f + x;
			float newY = this.actor.parent.getOriginY() - this.actor.getHeight()/2f  + y;
			this.actor.parent.setRotation(0);
			this.actor.parent.setOrigin(newX, newY);
			this.actor.updatePosition();
		}
	}
}
