package com.planet2d.editor.pages.filesPanel;

import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.MenuItem;

public class FileOptionsMenu extends Menu {

	public FileOptionsMenu() {
		
		super(
				new MenuItem("Rename", "renameFile"),
				new MenuItem("Delete", "deleteFile")
			);
	}
}
