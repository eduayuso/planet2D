package com.planet2d.editor.pages.tileMapEditor.tileMapCreator;

import com.planet2d.editor.pages.tileMapEditor.tileMapCreator.actors.Tile.TileType;

public class TileLoopLogic {

	public static int getCellNum(String pattern, int refNum, TileType type) {

		if (type == TileType.LEFT_TOP || type == TileType.RIGHT_TOP || type == TileType.LEFT_BOTTOM || type == TileType.RIGHT_BOTTOM) {
			return getCornerNum(pattern, refNum);
		
		} else if (type == TileType.TOP || type == TileType.BOTTOM) {
			return getVerticalNum(pattern, refNum);
			
		} else if (type == TileType.LEFT || type == TileType.RIGHT) {
			return getHorizontalNum(pattern, refNum);
		
		} else {
			return 0;
		}
	}

	private static int getCornerNum(String pattern, int refNum) {

		int num = 0;

		if (pattern.equals("1x2")) {
			if (refNum == 1) num = 2;
			else num = 1;
		} else if (pattern.equals("2x1")) {
			if (refNum == 1) num = 2;
			else num = 1;
		} else if (pattern.equals("2x2")) {
			if (refNum == 1) num = 4;
			else if (refNum == 2) num = 3;
			else if (refNum == 3) num = 2;
			else num = 1;
		} else {
			num = 1;
		}
		
		return num;
	}
	
	private static int getVerticalNum(String pattern, int refNum) {
		
		int num = 0;

		if (pattern.equals("1x2")) {
			if (refNum == 1) num = 2;
			else num = 1;
		} else if (pattern.equals("2x1")) {
			if (refNum == 1) num = 1;
			else num = 2;
		} else if (pattern.equals("2x2")) {
			if (refNum == 1) num = 3;
			else if (refNum == 2) num = 4;
			else if (refNum == 3) num = 1;
			else num = 2;
		} else {
			num = 1;
		}
		
		return num;
	}
	
	private static int getHorizontalNum(String pattern, int refNum) {

		int num = 0;

		if (pattern.equals("1x2")) {
			if (refNum == 1) num = 1;
			else num = 2;
		} else if (pattern.equals("2x1")) {
			if (refNum == 1) num = 2;
			else num = 2;
		} else if (pattern.equals("2x2")) {
			if (refNum == 1) num = 2;
			else if (refNum == 2) num = 1;
			else if (refNum == 3) num = 4;
			else num = 3;
		} else {
			num = 1;
		}
		
		return num;
	}
}
