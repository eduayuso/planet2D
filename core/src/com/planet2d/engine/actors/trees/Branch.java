package com.planet2d.engine.actors.trees;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.actors.SpriteActor;

public class Branch extends SpriteActor {
	
	private Action rotateAction;
	private Tree tree;
	
	public Branch(String path, String fileName, String name, boolean local) {

		super(path, fileName, name, local);
	}
	
	public Branch(Tree tree, String fileName, String name) {
		
		this("trees/"+tree.getName(), fileName, name, true);
		this.tree = tree;
	}

	public Branch(JsonValue json, Tree tree, int index, boolean local) {
		
		this("trees/"+tree.getName(), json.getString("file"), json.getString("name"), local);
		this.tree = tree;
		
		float x = json.getFloat("x");
		float y = json.getFloat("y");
		this.setPosition(x, y);
		this.setOrigin(json.getFloat("originX"), json.getFloat("originY"));
		this.addRotateAction(json.getFloat("rotation"));
	}
	
	public void addRotateAction(float degrees) {
		
		this.removeRotateAction();
		
		Action branchAction = Actions.sequence(
										Actions.rotateTo(degrees, 1.5f, Interpolation.sine),
										Actions.rotateTo(0f, 1.5f, Interpolation.sine)
									);
		this.rotateAction = Actions.forever(branchAction);
		this.addAction(this.rotateAction);
	}
	
	public void removeRotateAction() {
		
		if (this.rotateAction != null) {
			this.removeAction(this.rotateAction);
			this.rotateAction = null;
			this.setRotation(0);
		}
	}
}
