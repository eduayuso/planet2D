package com.planet2d.editor.pages.layersPanel;

import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.MenuItem;

public class LayerItemOptionsMenu extends Menu {

	public LayerItemOptionsMenu() {
		
		super(
				new MenuItem("Rename", "renameLayer"),
				new MenuItem("Delete", "deleteLayer")
			);
	}
}