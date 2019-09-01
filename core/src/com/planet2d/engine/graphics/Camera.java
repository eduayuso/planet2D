package com.planet2d.engine.graphics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.layouts.Layout;

public class Camera extends Actor {
	
	private final static float MIN_ZOOM = 0.5f;
	private final static float MAX_ZOOM = 6f;
	private Layout layout;
	private com.badlogic.gdx.graphics.OrthographicCamera gdxCamera;
	private boolean automaticMode;
	private Float automaticMovementX;
	public boolean changingTarget; // Cambiando de objetivo, la transicion se hace con una accion, para mostrar un movimiento fluido hacia el nuevo objetivo
	public GameActor target1;
	public GameActor target2;

}
