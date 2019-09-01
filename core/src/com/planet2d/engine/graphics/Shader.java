package com.planet2d.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shader extends ShaderProgram {
	
	protected float elapsedTime;
	protected boolean multipleTextures;
	protected TextureRegion mainTexture;
	protected TextureRegion textureMask;
	protected ColorFilter colorFilter;
	
	public Shader(String shaderPath) {
		
		super(vertexShader(shaderPath), fragmentShader(shaderPath));
		Shader.pedantic = false;
		if (!this.getLog().isEmpty()) Gdx.app.log(shaderPath, this.getLog());
		this.colorFilter = new ColorFilter();
	}

	private static String vertexShader(String shaderPath) {
		
		return Gdx.files.internal("shaders/" + shaderPath + "/vertex.vs").readString();
	}
	
	private static String fragmentShader(String shaderPath) {

		return Gdx.files.internal("shaders/" + shaderPath + "/fragment.fs").readString();
	}
	
	public void update() {
		
		this.elapsedTime += Gdx.graphics.getDeltaTime();
			
		this.begin();
		this.setUniformf("deltaTime", this.elapsedTime*10);
		if (multipleTextures) {
			
			Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE1);
			textureMask.getTexture().bind(1);
			this.setUniformi("u_texture1", 1);
			Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);
			mainTexture.getTexture().bind(0);
			this.setUniformi("u_texture", 0);
		}
		
		if (this.colorFilter != null) {
			this.setUniformf("hue", this.colorFilter.hue/100f);
			this.setUniformf("saturation", this.colorFilter.saturation/100f);
			this.setUniformf("lightness", this.colorFilter.lightness/100f);
			this.setUniformi("colorize", this.colorFilter.colorize);
			this.setUniformf("brightness", this.colorFilter.brightness/100f);
			this.setUniformf("contrast", this.colorFilter.contrast/100f);
		}
		
		this.end();
	}
	
	public ColorFilter getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(ColorFilter colorFilter) {
		this.colorFilter = colorFilter;
	}

	public void remove() {
		
		if (this.mainTexture != null) {
			if (this.mainTexture.getTexture() != null) this.mainTexture.getTexture().dispose();
			this.mainTexture.setTexture(null);
		}
		this.mainTexture = null;
		if (this.textureMask != null) {
			if (this.textureMask.getTexture() != null) this.textureMask.getTexture().dispose();
			this.textureMask.setTexture(null);
		}
		this.textureMask = null;
		this.colorFilter = null;
	}
}
