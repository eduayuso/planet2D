package com.planet2d.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

public class Json {

	public static JsonValue parseInternal(String resourcesPath, String fileName) {
		
		return new JsonReader().parse(Gdx.files.internal(resourcesPath + "/" + fileName));
	}
	
	public static JsonValue parseLocal(String resourcesPath, String fileName) {

		return new JsonReader().parse(Gdx.files.local("resources/" + resourcesPath + "/" + fileName));
	}
}
