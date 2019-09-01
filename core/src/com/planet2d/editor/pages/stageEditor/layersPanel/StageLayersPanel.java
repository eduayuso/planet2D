package com.planet2d.editor.pages.stageEditor.layersPanel;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.planet2d.editor.pages.PanelTab;
import com.planet2d.editor.pages.layersPanel.LayersPanel;
import com.planet2d.editor.pages.stageEditor.StagePanel;

public class StageLayersPanel extends Table {
	
	private PanelTab layersTab;
	private PanelTab backgroundTab;
	private PanelTab groundTab;
	private PanelTab foregroundTab;
	
	private LayersPanel backgroundPanel;
	private LayersPanel groundPanel;
	private LayersPanel foregroundPanel;
	
	public StageLayersPanel(StagePanel stageEditorPanel) {

		super();
		
		this.layersTab = new PanelTab("Layers");
		this.backgroundTab = new PanelTab("Background");
		this.groundTab = new PanelTab("Ground");
		this.foregroundTab = new PanelTab("Foreground");
		this.backgroundTab.select(true);
		
		/*this.add(this.backgroundTab).align(Align.topLeft).padLeft(11).padTop(4);
		this.add(this.groundTab).align(Align.topLeft).padTop(4);
		this.add(this.foregroundTab).align(Align.topLeft).expandX().padTop(4);
		this.pack();
		
		this.setPosition(0, -this.getPrefHeight() - 26);
		
		this.backgroundPanel = this.createDraggablePanel(this.getWidth());
		this.groundPanel = this.createDraggablePanel(this.getWidth());
		this.foregroundPanel = this.createDraggablePanel(this.getWidth());*/
	}
}
