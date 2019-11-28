package com.planet2d.editor.actors;

import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.MenuItem;

public class TileMapOptionsMenu extends Menu {
	
	public TileMapOptionsMenu() {
		
		super(
				new MenuItem("Tiles Grid", "tileMapGridEdit"),
				new MenuItem("Mesh Deformation", "tileMapMeshEdit"),
				new MenuItem("Physics", "tileMapPhysicsEdit"),
				new MenuItem("Delete", "deleteActor")
			);
	}
}