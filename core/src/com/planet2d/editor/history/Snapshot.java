package com.planet2d.editor.history;

import com.planet2d.editor.Editor;
import com.planet2d.engine.actors.GameActor;

public class Snapshot {
	
	private State[] states;

	public Snapshot() {

		this.states = new State[Editor.currentPage.getGameActors().size];
		int i=0;
		for (GameActor actor: Editor.currentPage.getGameActors()) {
			this.states[i++] = new State(actor);
		}
	}
	
	public void restore() {
		
		for (int i=0; i<this.states.length; i++) {
			this.states[i].restore();
		}
	}
}
