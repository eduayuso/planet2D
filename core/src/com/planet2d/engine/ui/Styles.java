package com.planet2d.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Styles {
	
	public static ArrayMap<FontType, LabelStyle> labelStyles;
	
	public static enum FontType {
		
		VERY_SMALL,
		SMALL,
		MEDIUM,
		LARGE
	}

	private static int verySmallFontSize = 14;
	private static int smallFontSize = 18;
	private static int mediumFontSize = 24;
	private static int largeFontSize = 36;

	public static void create() {
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("editor/fonts/Calibri.ttf"));
		
		Styles.labelStyles = new ArrayMap<FontType, LabelStyle>();
		createLabelStyles(generator);
		
		generator.dispose();
	}
		
	private static void createLabelStyles(FreeTypeFontGenerator generator) {
		
		/*
		 * VERY SMALL LABEL STYLE BLACK
		 */
		FreeTypeFontParameter parameterVSB = new FreeTypeFontParameter();
		parameterVSB.size = verySmallFontSize;
		LabelStyle labelStyleVSB = new LabelStyle();
		labelStyleVSB.font = generator.generateFont(parameterVSB);
		labelStyleVSB.fontColor = Color.BLACK;
		Styles.labelStyles.put(FontType.VERY_SMALL, labelStyleVSB);
		
		/*
		 * SMALL LABEL STYLE BLACK
		 */
		FreeTypeFontParameter parameterSB = new FreeTypeFontParameter();
		parameterSB.size = smallFontSize;
		LabelStyle labelStyleSB = new LabelStyle();
		labelStyleSB.font = generator.generateFont(parameterSB);
		labelStyleSB.fontColor = Color.BLACK;
		Styles.labelStyles.put(FontType.SMALL, labelStyleSB);
		
		/*
		 * MEDIUM LABEL STYLE
		 */
		
		FreeTypeFontParameter parameterM = new FreeTypeFontParameter();
		parameterM.size = mediumFontSize;
		LabelStyle labelStyleM = new LabelStyle();
		labelStyleM.font = generator.generateFont(parameterM);
		labelStyleM.fontColor = Color.BLACK;
		Styles.labelStyles.put(FontType.MEDIUM, labelStyleM);
		
		/*
		 * LARGE LABEL STYLE
		 */
		FreeTypeFontParameter parameterL = new FreeTypeFontParameter();
		parameterL.size = largeFontSize;
		LabelStyle labelStyleL = new LabelStyle();
		labelStyleL.font = generator.generateFont(parameterL);
		labelStyleL.fontColor = Color.BLACK;
		Styles.labelStyles.put(FontType.LARGE, labelStyleL);
	}
}