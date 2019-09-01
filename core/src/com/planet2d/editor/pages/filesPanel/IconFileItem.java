package com.planet2d.editor.pages.filesPanel;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.SpriteActor;

public class IconFileItem extends FileItem {
	
	private String imageIconPath;
	private Sprite iconSprite;
	private Sprite loadingSprite;
	private boolean loading;
	
	public IconFileItem(FilesScroll filesScroll, FileHandle file) {
		
		super(filesScroll, file, setWidth(filesScroll, file), setHeight(file));
	}

	private static float setWidth(FilesScroll filesScroll, FileHandle file) {
		
		if (file.isDirectory()) return filesScroll.getWidth();
		else return 36;
	}
	
	private static float setHeight(FileHandle file) {
		
		if (file.isDirectory()) return 24;
		else return 36;
	}
	
	@Override
	public void create() {
		
		super.create();
		this.removeActor(this.label);
	}
	
	@Override
	protected void setDefaultWidth() {
		
	}

	@Override
	protected Actor createIcon() {
		
		SpriteActor icon = null;
		
		if (this.file.isDirectory()) {
			
			super.createIcon();
			
		} else {
			
			icon = new SpriteActor(this.file.name());
			this.imageIconPath = this.file.path().replaceAll("resources/", "");
			Sprite sprite = null;
			if (Resources.isLocalTextureLoaded(this.imageIconPath)) {
				this.iconSprite = new Sprite(Resources.getLocalTexture(this.imageIconPath));
				sprite = this.iconSprite;
			} else {
				this.loading = true;
				Resources.loadLocalTexture(this.imageIconPath);
				this.loadingSprite = new Sprite(Resources.getTexture("editor", "ui/loading.png"));
				sprite = this.loadingSprite;
			}
			icon.create(sprite);
			icon.setPosition(0, 0);
			icon.setVisible(false);
			
			float width = this.getWidth();
			float height = this.getHeight();
			if (icon.getWidth() > icon.getHeight()) {
				height = icon.getHeight()/icon.getWidth()*height;
			} else {
				width = icon.getWidth()/icon.getHeight()*width;
			}
			icon.setSize(width, height);
			
			this.iconDisabled = new SpriteActor(this.file.name());
			this.iconDisabled.create(sprite);
			this.iconDisabled.addAction(Actions.alpha(0.5f));
			this.iconDisabled.setPosition(0, 0);
			this.iconDisabled.setSize(icon.getWidth(), icon.getHeight());
		}
		
		return icon;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		if (this.loading && Resources.isLocalTextureLoaded(this.imageIconPath)) {
			
			this.loading = false;
			this.iconSprite = new Sprite(Resources.getLocalTexture(this.imageIconPath));
			((SpriteActor)this.icon).sprite = this.iconSprite;
			this.iconDisabled.sprite = this.iconSprite;
			float width = this.getWidth();
			float height = this.getHeight();
			if (this.iconSprite.getWidth() > this.iconSprite.getHeight()) {
				height = this.iconSprite.getHeight()/this.iconSprite.getWidth()*height;
			} else {
				width = this.iconSprite.getWidth()/this.iconSprite.getHeight()*width;
			}
			this.iconDisabled.setSize(width, height);
		}
		
		super.draw(batch, parentAlpha);
	}
}
