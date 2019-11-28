package com.planet2d.engine.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureMaskShader extends Shader {

	public TextureMaskShader(String shaderPath, TextureRegion mainTexture, TextureRegion textureMask) {
		super(shaderPath);
		this.mainTexture = mainTexture;
		this.textureMask = textureMask;
		this.multipleTextures = true;
	}
}
