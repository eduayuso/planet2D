package com.planet2d.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

public class ColorFilter {
	
	public float hue;
	public float saturation;
	public float lightness;
	public float brightness;
	public float contrast;
	public int colorize; // [0:false, 1:true]
	
	public ColorFilter() {
	}
	
	public ColorFilter(JsonValue jsonColor) {

		this.hue = jsonColor.getFloat("hue");
		this.saturation = jsonColor.getFloat("sat");
		this.lightness = jsonColor.getFloat("lig");
		this.brightness = jsonColor.getFloat("bri");
		this.contrast = jsonColor.getFloat("con");
		this.colorize = jsonColor.getInt("col");
	}

	public void printInfo() {
		
		Gdx.app.log("HUE",this.hue+"");
		Gdx.app.log("SAT",this.saturation+"");
		Gdx.app.log("LIG",this.lightness+"");
		Gdx.app.log("BRI",this.brightness+"");
		Gdx.app.log("CON",this.contrast+"");
		Gdx.app.log("COL",this.colorize+"");
		Gdx.app.log("-------","-------");
	}
}
