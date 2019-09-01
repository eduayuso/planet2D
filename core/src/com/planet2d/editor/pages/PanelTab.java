package com.planet2d.editor.pages;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.ui.Label;

public class PanelTab extends Group {

	private boolean selected;
	public Label labelSelected;
	public Label labelUnselected;
	
	public PanelTab(String name) {
		
		this(name, null);
	}
	
	public PanelTab(String name, Integer width) {
		
		super();
		this.setName(name);
		
		this.labelSelected = new Label(name);
		this.labelSelected.setBackground(true, "tab-selected");
		this.labelSelected.setAlignment(Align.center);
		if (width != null) this.labelSelected.setWidth(width);
		
		this.labelUnselected = new Label(name);
		this.labelUnselected.setBackground(true, "tab-unselected");
		this.labelUnselected.setAlignment(Align.center);
		if (width != null) this.labelUnselected.setWidth(width);
		
		this.labelSelected.setPosition(this.labelSelected.paddingLeft, 0);
		this.labelUnselected.setPosition(this.labelSelected.paddingLeft, 0);
		this.labelSelected.setVisible(false);
		
		this.addActor(this.labelSelected);
		this.addActor(this.labelUnselected);
		this.setSize(this.labelSelected.getWidth() + this.labelSelected.paddingLeft + this.labelSelected.paddingRight, this.labelSelected.getHeight());
	}
	
	public void hover(boolean set) {
		
		if (!this.selected) {
			this.labelSelected.setVisible(set);
			this.labelUnselected.setVisible(!set);
		}
	}

	public void select(boolean set) {
		
		this.selected = set;
		this.labelSelected.setVisible(set);
		this.labelUnselected.setVisible(!set);
	}

	public void updateText(String name) {

		this.labelSelected.setText(name);
		this.labelUnselected.setText(name);
	}
}
