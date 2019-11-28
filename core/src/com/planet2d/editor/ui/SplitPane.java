package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.planet2d.editor.Editor;
import com.planet2d.engine.Engine;

public class SplitPane extends com.badlogic.gdx.scenes.scene2d.ui.SplitPane {
	
	private Actor leftWidget;
	private Actor rightWidget;

	public SplitPane(Actor leftWidget, Actor rightWidget, float initialSplitPos) {
		
		super(leftWidget, rightWidget, false, Engine.skin);
		this.leftWidget = leftWidget;
		this.rightWidget = rightWidget;
		
		this.setSplitAmount(initialSplitPos);
		this.setMinSplitAmount(initialSplitPos-0.1f);
		this.setMaxSplitAmount(initialSplitPos+0.15f);
	}
	
	@Override
	public void invalidate() {
		
		super.invalidate();
		if (this.leftWidget != null) {
			this.leftWidget.setWidth(this.getWidth()*this.getSplitAmount());
			Editor.currentPage.stateBar.setWidth(this.leftWidget.getWidth());
		}
	}
}
