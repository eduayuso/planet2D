package com.planet2d.editor.actors;

import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.MenuItem;

public class ActorOptionsMenu extends Menu {
	
	public ActorOptionsMenu() {
		
		super(
				new MenuItem("Flip X", "actorFlipHorizontal"),
				new MenuItem("Flip Y", "actorFlipVertical"),
				new MenuItem("Scale", "actorScale"),
				new MenuItem("Rotate", "actorRotate"),
				new MenuItem("Move origin", "actorMoveOrigin"),
				new MenuItem("Color filter", "actorColor"),
				new MenuItem("Delete", "deleteActor")
			);
	}
}
