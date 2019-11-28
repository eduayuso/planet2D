package com.planet2d.editor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.planet2d.editor.Editor;
import com.planet2d.engine.config.Config;

public class FileUtils {
	
	public static void importImageFiles() {
		
		final JFileChooser fc = createImageFileChooser("png");
		
		openFilesDialog(fc, false);
	}

	public static void openFile(final Class classType) {
		
		final JFileChooser fc = createClassFileChooser(classType);
		
		openFilesDialog(fc, true);
	}
	
	private static JFileChooser createImageFileChooser(String extension) {

		return createFileChooser(null, extension);
	}

	private static JFileChooser createClassFileChooser(Class classType) {
		
		return createFileChooser(classType, "");
	}
	
	private static JFileChooser createFileChooser(Class classType, String ext) {
		
		JFileChooser fc = new JFileChooser();
		String defaultPath = "resources/" + Config.gamePath;
		String dialogTitle = "";
		String extension = ext;
		String fileTypeDescription = "";
		
		if (classType == null) {
			
			defaultPath += "/textures";
			dialogTitle = "Select image files to import";
			extension = ext;
			fileTypeDescription = "PNG Files";
			fc.setMultiSelectionEnabled(true);
		
		} else if (classType == Tree.class) {
			
			defaultPath += "/json/attributes/trees";
			dialogTitle = "Open a Tree file";
			extension = "tree";
			fileTypeDescription = "Jeviathor Tree File";
		}
		fc.setFileFilter(new FileNameExtensionFilter(fileTypeDescription, extension));
		fc.setDialogTitle(dialogTitle);
		fc.setCurrentDirectory(Gdx.files.local(defaultPath).file());
		
		return fc;
	}
	
	public static void openProjectFileDialog(final JFileChooser fc) {

		openFilesDialog(fc, true);
	}
	
	public static void openFilesDialog(final JFileChooser fc, final boolean project) {
		
		Gdx.app.postRunnable(new Runnable() {
			
            public void run () {
            	
            	int returnVal = fc.showOpenDialog(null);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
            		
            		if (project) {
            			FileHandle fileHandle = new FileHandle(fc.getSelectedFile());
            			loadFile(fileHandle);
            		} else {
            			importImages(fc.getSelectedFiles());
            		}
            	}
            }
		});
	}
	
	
	protected static void importImages(File[] files) {

		Editor.currentPage.importImages(files);
	}

	private static void loadFile(FileHandle file) {
		
		Editor.currentPage.editorPanel.loadProject(file);
	}
}
