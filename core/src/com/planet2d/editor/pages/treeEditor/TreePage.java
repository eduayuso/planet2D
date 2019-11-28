package com.planet2d.editor.pages.treeEditor;

import java.io.StringWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.planet2d.editor.config.Config;
import com.planet2d.editor.config.EditorTypes.PageType;
import com.planet2d.editor.dialogs.NewTreeDialog;
import com.planet2d.editor.listeners.CanvasListener;
import com.planet2d.editor.pages.EditorPanel;
import com.planet2d.editor.pages.ProjectPage;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.trees.Branch;
import com.planet2d.engine.actors.trees.Tree;

public class TreePage extends ProjectPage {

	public Tree tree;
	
	public TreePage() {
		
		super(PageType.TREE_EDITOR);
		this.createContent();
		
		this.newProjectDialog = new NewTreeDialog();
	}
	
	@Override
	public EditorPanel createEditorPanel() {
		
		return new TreePanel(this);
	}
	
	@Override
	protected float getSplit() {
		
		return 0.775f;
	}
	
	@Override
	public void createNewProject(String newName) {
		
		this.tree = new Tree(newName);
		this.tree.setPosition(0, 0);
		this.tree.setTouchable(Touchable.childrenOnly);
		this.add(this.tree);
	}
	
	@Override
	public void deleteProject() {
		
		this.clearContent();
		
		FileHandle jsonFile = Gdx.files.local("resources/" + Config.gamePath + "/json/trees/" + this.tree.getName() + ".tree");
		jsonFile.delete();
		
		FileHandle textureDir = Gdx.files.local("resources/" + Config.gamePath + "/textures/trees/" + this.tree.getName());
		textureDir.deleteDirectory();
		
		this.editorPanel.clearContent();
		
		this.tree.remove();
		this.tree = null;
	}
	
	@Override
	public void saveProject() {
		
		this.tree.normalize();

		FileHandle jsonFile = Gdx.files.local("resources/" + Config.gamePath + "/json/trees/" + this.tree.getName() + ".tree");
		
		StringWriter jsonText = new StringWriter();
		JsonWriter json = new JsonWriter(jsonText);
		
		try {
			json.object();
				json.object(this.tree.getName())
					.set("width", this.tree.getWidth())
					.set("height", this.tree.getHeight())
					.array("branches");
						for (Actor branch: this.tree.getChildren()) {
							json.object()
								.set("name", branch.getName())
								.set("file", ((Branch)branch).fileName)
								.set("x", branch.getX())
								.set("y", branch.getY())
								.set("originX", branch.getOriginX())
								.set("originY", branch.getOriginY())
								.set("rotation", branch.getRotation());
							json.pop();
						}
					json.pop();
				json.pop();
			json.pop();
			
			json.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		Json jsonOutput = new Json();
		jsonOutput.setOutputType(OutputType.json);
		
		jsonFile.writeString(jsonOutput.prettyPrint(jsonText.toString()), false);
	}
	
	@Override
	public void addGameActor(GameActor actor) {
		
		if (actor instanceof Tree) {
			
			Tree tree = (Tree)actor;
			for (Actor branch: tree.getChildren()) {
				super.addGameActor((Branch)branch);
			}
			
		} else {
			super.addGameActor(actor);
		}
	}
	
	@Override
	public void selectActor(GameActor actor) {
		
		super.selectActor(actor);
		((TreePanel)this.editorPanel).selectItemByActor(actor);
	}
	
	@Override
	public void unselectActor(GameActor actor) {
		
		super.unselectActor(actor);
		((TreePanel)this.editorPanel).unselectItemByActor(actor);
	}
	
	@Override
	public void unselectAllActors() {
		
		super.unselectAllActors();
		((TreePanel)this.editorPanel).unselectAllItems();
	}
	
	@Override
	public void endRectangleSelection() {
		
		for (Actor actor: this.content.getChildren()) {
			if (actor instanceof GameActor) {
				if (actor instanceof Tree) {
					for (Actor branch: ((Tree) actor).getChildren()) {
						this.checkIfSelectedByRectangle((Tree)actor, (Branch)branch);
					}
				} else {
					super.checkIfSelectedByRectangle((GameActor)actor);
				}
			}
		}
	}
	
	private void checkIfSelectedByRectangle(Tree tree, GameActor child) {

		if (this.rectangleSelector.overlaps(tree.getX()+child.getX(), tree.getY()+child.getY(), child.getWidth(), child.getHeight())) {
			this.selectActor(child);
		}
	}
	
	@Override
	public void addContent(GameActor actor) {
		
		if (actor instanceof Tree) {
			this.tree = (Tree) actor;
		}
		
		super.addContent(actor);
	}
	
	@Override
	public void deleteSelectedActors() {
		
		super.deleteSelectedActors();
		((TreePanel)this.editorPanel).layersPanel.deleteSelectedItems();
		((TreePanel)this.editorPanel).filesPanel.checkFilesStates();
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void touchUp(CanvasListener canvasListener) {
		
		super.touchUp(canvasListener);
		
		if (this.isEditingActor() && canvasListener.dragInitX != null) {
			
			GameActor actor = this.getSelectedGameActors().get(0);
			if (actor instanceof Branch) ((Branch)actor).addRotateAction(actor.getRotation());
		}
	}
}
