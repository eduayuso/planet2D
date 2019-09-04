package com.planet2d.editor;

import java.lang.reflect.Method;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.planet2d.editor.FileUtils;
import com.planet2d.editor.pages.tileMapEditor.TileMapPage;
import com.planet2d.engine.Engine;

public class Actions {
	
	public static void invokeAction(String actionName) {
		
		Method method = null;
		String className = "com.planet2d.editor.Actions";
		String methodName = actionName;
		
		try {
			Class c = Class.forName(className);
			method = c.getMethod(methodName);
			method.invoke(null);
		} catch (Exception e) {
			Gdx.app.error("error invocando metodo " + actionName, e.toString());
		}
	}

	public static void newProject() {
        Editor.mainWindow.restoreWindow();
    }
	
	public static void newProjectDialog() {
		
		Editor.currentPage.showNewProjectDialog();
	}
	
	public static void openTreeFile() {
		
		FileUtils.openFile(Tree.class);
	}
	
	public static void importImageFiles() {

		FileUtils.importImageFiles();
	}

	public static void minimizeWindow() {

	    Editor.mainWindow.restoreWindow();
    }

    public static void maximizeWindow() {

        Editor.mainWindow.maximizeWindow();
    }

	public static void closeEditor() {
		
		if (Editor.currentPage != null) Editor.currentPage.showCloseProjectDialog();
		else Engine.exit();  
	}
	
	public static void deleteFile() {
		
		Editor.currentPage.editorPanel.confirmDeleteFile.show();
	}
	
	public static void deleteLayer() {
		
		Editor.currentPage.deleteSelectedActors();
	}
	
	public static void deleteActor() {
		
		Editor.currentPage.deleteSelectedActors();
	}
	
	public static void deleteProject() {
		
		Editor.currentPage.showDeleteProjectDialog();
	}
	
	public static void saveProject() {
		
		Editor.currentPage.saveProject();
	}
	
	public static void deleteTileSet() {
		
		((TileMapPage)Editor.currentPage).showDeleteTileSetDialog();
	}
	
	public static void undo() {
		
		//History.undo();
	}
	
	public static void redo() {
		
		//History.redo();
	}
}
