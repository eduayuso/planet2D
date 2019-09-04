package com.planet2d.engine.config;

public abstract class Config {
	
	public static int VIRTUAL_SCREEN_WIDTH = 1366;
	public static int VIRTUAL_SCREEN_HEIGHT = 760;
	public static final boolean FULL_SCREEN = false;
	public static boolean showUnselectedTranslucid = false;
	
	public static float soundVolume;
	public static float musicVolume;
	public static float timeFactor;
	public static String gamePath;
	public static String stagePath;

    public enum AppType {
        EDITOR,
        GAME
    }

    public static AppType appType = AppType.EDITOR;

	public abstract void load();
}
