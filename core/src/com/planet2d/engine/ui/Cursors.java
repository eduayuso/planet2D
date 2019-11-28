package com.planet2d.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.utils.ObjectMap;

public class Cursors {
	
	public static boolean ignore;
	
	public static enum CursorType {
		
		DEFAULT,
		ROTATE,
		CROSSHAIR,
		DRAG_ITEM,
		TEXT,
		HORIZONTAL_RESIZE,
		VERTICAL_RESIZE
	}

	private static ObjectMap<CursorType, Cursor> cursors;
	private static CursorType currentCursor;
	
	public Cursors() {
		
		cursors = new ObjectMap<CursorType, Cursor>();
		createCursor(CursorType.ROTATE);
		createCursor(CursorType.HORIZONTAL_RESIZE);
		createCursor(CursorType.VERTICAL_RESIZE);
		createCursor(CursorType.TEXT);
		createCursor(CursorType.DRAG_ITEM);
		currentCursor = CursorType.DEFAULT;
	}

	private static void createCursor(CursorType cursorType) {
		
		String cursorFile = "cursor-"+ cursorType.toString().toLowerCase();
		Pixmap pixmap = new Pixmap(Gdx.files.internal("editor/textures/ui/"+cursorFile+".png"));
		int hy = 16;
		if (cursorType == CursorType.DRAG_ITEM) hy = 0;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, 16, hy);
		pixmap.dispose();
		
		cursors.put(cursorType, cursor);
	}
	
	public static void setAndLock(CursorType cursorType) {
		
		ignore = false;
		set(cursorType);
		ignore = true;
	}

	public static void set(CursorType cursorType) {
		
		if (ignore) return;
		
		if (!currentCursor.equals(cursorType)) {
			if (cursorType.equals(CursorType.DEFAULT)) Gdx.graphics.setSystemCursor(SystemCursor.Hand);
			else Gdx.graphics.setCursor(cursors.get(cursorType));
			currentCursor = cursorType;
		}
	}
}
