package com.planet2d.editor.pages.welcome.panels;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.Editor;
import com.planet2d.editor.config.ProjectInfo;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Panel;
import com.planet2d.engine.ui.Styles.FontType;

public abstract class ItemsPanel extends Panel {
	
	private ItemInfo[] items;

	public ItemsPanel(String title, int width, int height) {
		
		this(title, "panel-gray", width, height);
	}

	public ItemsPanel(String title, String panelName, int width, int height) {
		
		super(panelName, width, height);
		
		Label titleLabel = new Label(title, FontType.MEDIUM);
		titleLabel.setPosition(18, this.getHeight() - titleLabel.getHeight() - 16);
		this.addActor(titleLabel);
	}

	public void createButtons(ItemInfo[] items, float x00, float y00) {
		
		this.items = items;

		Group button = null;
		
		int r=0;
		int c=0;
		float x0 = x00;
		float y0 = this.getHeight() - y00;
		
		for (ItemInfo item: this.items) {
			
			if (c==4) {
				c=0;
				r++;
			}
			button = new PanelButton(this, item);
			button.setPosition(x0 + button.getWidth()*c, y0 - button.getHeight()*r);
			this.addActor(button);
			c++;
		}
	}

	public abstract void buttonAction(ItemInfo info);
}
