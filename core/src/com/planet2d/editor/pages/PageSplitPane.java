package com.planet2d.editor.pages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.planet2d.editor.Editor;
import com.planet2d.engine.Engine;

public class PageSplitPane extends SplitPane {

	public float initSplit = 0.78f;
	public Actor leftWidget;
	public Actor rightWidget;

	public PageSplitPane(float initSplit, Actor leftWidget, Actor rightWidget) {
		
		super(leftWidget, rightWidget, false, Engine.skin);
		this.initSplit = initSplit;
		this.leftWidget = leftWidget;
		this.rightWidget = rightWidget;
		
		this.setSplitAmount(initSplit);
		this.setMinSplitAmount(this.initSplit-0.01f);
		this.setMaxSplitAmount(this.initSplit+0.01f);
		
		this.getListeners().removeIndex(0);
	}
	
	@Override
	public void invalidate() {
		
		super.invalidate();
		if (this.leftWidget != null) {
			
			// Left widget resize
			this.leftWidget.setWidth(this.getWidth()*this.getSplitAmount());
			Editor.currentPage.stateBar.setWidth(this.leftWidget.getWidth()-2);
			Editor.currentPage.scrollPane.container.setWidth(this.leftWidget.getWidth()-2);
			
			// Right widget resize
			
		}
	}
}
