package com.planet2d.editor.pages.treeEditor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.editor.pages.filesPanel.FileItem;
import com.planet2d.editor.pages.filesPanel.FilesPanel;
import com.planet2d.engine.actors.trees.Branch;
import com.planet2d.engine.actors.trees.Tree;

public class TreeFiles extends FilesPanel {
	
	private TreePanel treePanel;

	public TreeFiles(TreePanel treePanel) {
		
		super("textures/trees", treePanel.getWidth()/2f, treePanel.getHeight());
		this.treePanel = treePanel;
	}

	@Override
	public void addFileToProject(FileItem item) {
		
		super.addFileToProject(item);
		
		this.treePanel.loadImage(item.file);
	}

	@Override
	public void openFile(FileHandle file) {

		this.treePanel.loadProject(file);
	}
	
	@Override
	protected void checkFilesStates() {
		
		Tree tree = this.treePanel.getTree();
		if (tree == null) return;
		for (FileItem item: this.scrollPanel.getItems()) {
			if (!item.file.isDirectory()) {
				item.removeFromProject();
				for (Actor branch: tree.getChildren()) {
					if (item.file.name().equals(((Branch)branch).fileName)) {
						item.addToProject();
						break;
					}
				}
			}
		}
	}
}
