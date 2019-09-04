package com.planet2d.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;

public class Resources {
	
	private static AssetManager internalResourcesManager = new AssetManager();
	private static AssetManager localResourcesManager = new AssetManager(new LocalFileHandleResolver());
	public static AssetManager assetManager = internalResourcesManager;
	private static String projectName;
	
	private static void useInternal() {
		
		assetManager = internalResourcesManager;
	}
	
	public static void useLocal() {
		
		assetManager = localResourcesManager;
	}

	private static void load(String folder, String[] fileNames, String extension, Class<?> classType) {
		
		Resources.useInternal();
		for (String fileName : fileNames) {
			String path = folder + "/" + fileName + "." + extension;
			if (!assetManager.isLoaded(path, classType)) {
			//	Gdx.app.log("loading...", path);
				assetManager.load(path, classType);
			} else {
				//Gdx.app.log("already loaded!", path);
			}
		}
	}
	
	private static void unload(String folder, String[] fileNames, String extension, Class<?> classType) {
		
		Resources.useInternal();
		for (String fileName : fileNames) {
			String path = folder + "/" + fileName + "." + extension;
			if (assetManager.isLoaded(path, classType)) {
				assetManager.unload(path);
				//Gdx.app.log("unloading...", path);
			}
		}
	}
	
	public static void load3DModels(String[] fileNames) {
		
		load("3D", fileNames, "g3db", Model.class);
	}
	
	public static void unload3DModels(String[] fileNames) {
		
		unload("3D", fileNames, "g3db", Model.class);
	}
	
	public static void loadLocalTextures(String... filePaths) {
		
		Resources.useLocal();
		
		for (String filePath: filePaths) {
			
			if (!assetManager.isLoaded(filePath, Texture.class)) {
				//Gdx.app.log("loading...", filePath);
				assetManager.load(filePath, Texture.class);
			}
		}
	}
	
	public static void loadLocalTexture(String filePath) {
		
		useLocal();
		localResourcesManager.load("resources/"+filePath, Texture.class);
	}
	
	public static void loadPNGTextures(String... fileNames) {
		
		load(projectName + "/textures", fileNames, "png", Texture.class);
	}
	
	public static void unloadPNGTextures(String[] fileNames) {
		
		unload(projectName + "/textures", fileNames, "png", Texture.class);
	}
	
	public static void loadJPGTextures(String... fileNames) {
		
		load(projectName + "/textures", fileNames, "jpg", Texture.class);
	}
	
	public static void unloadJPGTextures(String[] fileNames) {
		
		unload(projectName + "/textures", fileNames, "jpg", Texture.class);
	}
	
	public static void loadTextureAtlases(String[] fileNames) {
		
		load(projectName + "/textures", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void unloadTextureAtlases(String[] fileNames) {
		
		unload(projectName + "/textures", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void loadSounds(String[] fileNames) {
		
		load("sounds", fileNames, "wav", Sound.class);
	}
	
	public static void unloadSounds(String[] fileNames) {
		
		unload("sounds", fileNames, "wav", Sound.class);
	}
	
	public static boolean isLoaded(String path, Class<?> classType) {
		
		return assetManager.isLoaded(path, classType);
	}
	
	public static void loadMusic(String[] fileNames) {
		
		load("music", fileNames, "mp3", Music.class);
	}
	
	public static void unloadMusic(String[] fileNames) {
		
		unload("music", fileNames, "mp3", Music.class);
	}
	
	public static void loadVideos(String[] fileNames) {
		
	//	load("videos", fileNames, "mp4", Video.class);
	}
	
	public static void loadCharactersTextures(String[] fileNames) {
		
		load("textures/characters", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void loadCharactersTextures(String[] fileNames, String type) {
		if (type.equals("atlas")) {
			load("textures/characters", fileNames, type, TextureAtlas.class);
		} else {
			load("textures/characters", fileNames, type, Texture.class);
		}
	}
	
	
	public static void unloadCharactersTextures(String[] fileNames) {
		
		unload("textures/characters", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void unloadCharactersTextures(String[] fileNames, String type) {
		if (type.equals("atlas")) {
			unload("textures/characters", fileNames, type, TextureAtlas.class);
		} else {
			unload("textures/characters", fileNames, type, Texture.class);
		}
	}
	
	public static void loadPlayerTextures() {
		
		load("textures/characters", new String[] { "hank" }, "atlas", TextureAtlas.class);
		load("textures/characters", new String[] { "hank-basic-level1" }, "atlas", TextureAtlas.class);
		/*for (Entry<String, Integer> entrySet: Config.getPlayerLevels("hank").entries()) {
			load("textures/characters", new String[] { "hank-" + entrySet.key + "-level1" }, "atlas", TextureAtlas.class);
		}*/
		
		load("textures/characters", new String[] { "vajra" }, "atlas", TextureAtlas.class);
		load("textures/characters", new String[] { "vajra-basic-level1" }, "atlas", TextureAtlas.class);
		/*for (Entry<String, Integer> entrySet: Config.getPlayerLevels("vajra").entries()) {
			load("textures/characters", new String[] { "vajra-" + entrySet.key + "-level1" }, "atlas", TextureAtlas.class);
		}*/
	}
	
	public static void loadInteractiveObjectsTextures(String[] fileNames, String type) {
		
		if (type.equals("atlas")) {
			load("textures/interactive", fileNames, type, TextureAtlas.class);
		} else {
			load("textures/interactive", fileNames, type, Texture.class);
		}
	}
	
	public static void loadTreesTextures(String[] fileNames) {
		
		load("textures/trees", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void unloadInteractiveObjectsTextures(String[] fileNames, String type) {
		
		if (type.equals("atlas")) {
			unload("textures/interactive", fileNames, type, TextureAtlas.class);
		} else {
			unload("textures/interactive", fileNames, type, Texture.class);
		}
	}
	
	public static void loadAnimatedSceneObjectsTextures(String[] fileNames) {
		
		load("textures/scene", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void loadSceneObjectsTexturesAtlases(String[] fileNames) {
		
		load("textures/scene", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void loadSceneObjectsTextures(String[] fileNames) {
		
		load("textures/scene", fileNames, "png", Texture.class);
	}
	
	public static void loadSceneObjectsTexturesJPG(String[] fileNames) {
		
		load("textures/scene", fileNames, "jpg", Texture.class);
	}
	
	public static void unloadSceneObjectsTextures(String[] fileNames) {
		
		unload("textures/scene", fileNames, "png", Texture.class);
	}
	
	public static void unloadAnimatedSceneObjectsTextures(String[] fileNames) {
		
		unload("textures/scene", fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void loadBlocksTextures(String[] fileNames, String type) {
		
		load("textures/" + type, fileNames, "atlas", TextureAtlas.class);
	}
	
	public static void unloadBlocksTextures(String[] fileNames, String type) {
		
		unload("textures/" + type, fileNames, "atlas", TextureAtlas.class);
	}
	
	public static Model get3DModel(String projectName, String fileName) {
		
		return assetManager.get(projectName + "/3D/" + fileName + ".g3db", Model.class);
	}
	
	public static Texture getLocalTexture(String filePath) {
		
		return localResourcesManager.get("resources/"+filePath, Texture.class);
	}
	
	public static Texture getLocalTexture(String projectPath, String filePath) {
		
		return localResourcesManager.get("resources/"+projectPath+"/textures/"+filePath, Texture.class);
	}
	
	public static Texture getTexture(String fileName) {
		
		return getTexture(Resources.projectName, fileName);
	}
	
	public static Texture getTexture(String projectName, String fileName) {
		
		return internalResourcesManager.get(projectName + "/textures/" + fileName, Texture.class);
	}
	
	public static TextureAtlas getTextureAtlas(String projectName, String atlasName) {
		
		return internalResourcesManager.get(projectName + "/textures/" + atlasName + ".atlas", TextureAtlas.class);
	}
	
	public static Array<Sound> getAllSounds() {
		Array<Sound> ret = new Array<Sound>();
		internalResourcesManager.getAll(Sound.class, ret);
		return ret;
	}
	
	public static Sound getSound(String projectName, String fileName) {
	
		return internalResourcesManager.get(projectName + "/sounds/" + fileName + ".wav", Sound.class);
	}
	
	public static Music getMusic(String projectName, String fileName) {
		Music ret = null;
		if (fileName != null && !("").equals(fileName)) {
			ret = internalResourcesManager.get(projectName + "/music/" + fileName + ".mp3", Music.class);
		}
		return ret;
	}
	
	public static boolean loaded() {
		
		return assetManager.getProgress() == 1;
	}
	
	public static boolean update() {
		
		return assetManager.update();
	}
	
	public static float getProgress() {
		
		return assetManager.getProgress();
	}
	
	public static boolean isLocalTextureLoaded(String fileName) {
		
		return localResourcesManager.isLoaded("resources/" + fileName, Texture.class);
	}
	
	public static boolean is3DLoaded(String fileName) {
		
		return assetManager.isLoaded("3D/" + fileName + ".g3db", Model.class);
	}
	
	public static void clear() {
	
		assetManager.dispose();
	}
	
	public static void finished() {
	
		assetManager.finishLoading();
	}

	public static void setProject(String project) {

		projectName = project;
	}
}
