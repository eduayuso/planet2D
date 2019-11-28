package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.planet2d.engine.Engine;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.screens.Screen;
import com.planet2d.editor.Editor;
import com.planet2d.editor.layouts.CanvasLayout;
import com.planet2d.editor.ui.Label;

public class StateBar extends Table {

	public Label actorNameLabel;
	public Label actorXLabel;
	public Label actorYLabel;
	public Label actorWidthLabel;
	public Label actorHeightLabel;
	public Label actorOriginXLabel;
	public Label actorOriginYLabel;
	public Label actorRotationLabel;
	
	public Label canvasSizeLabel;
	public Label zoomLabel;
	
	public StateBar(float width, float height) {
		
		super(Engine.skin);
		
		this.setBackground("gray");
		this.setSize(width-2, height);
		this.create();
	}

	private void create() {
		
		this.actorNameLabel 	= this.createLabel("actor name", 98, Align.left, false, false);
		this.actorXLabel 		= this.createLabel("x:0000", -1, Align.left, true, false);
		this.actorYLabel		= this.createLabel("y:0000", -1, Align.left, true, false);
		this.actorWidthLabel 	= this.createLabel("width:0000", -1, Align.left, true, false);
		this.actorHeightLabel 	= this.createLabel("height:0000", -1, Align.left, true, false);
		this.actorOriginXLabel	= this.createLabel("originX:0000", -1, Align.left, true, false);
		this.actorOriginYLabel	= this.createLabel("originY:0000", -1, Align.left, true, false);
		this.actorRotationLabel	= this.createLabel("angle:000", -1, Align.left, true, true);
		
		this.canvasSizeLabel 	= this.createLabel("1366 x 768", 100, Align.right, true, true);
		this.zoomLabel 			= this.createLabel("100%", -1, Align.right, true, false);
		this.getCell(this.zoomLabel).padRight(10);
	}
	
	private Label createLabel(String text, int ww, int a, boolean withBackground, boolean expandX) {
		
		Label label = new Label(text);
		label.setBackground(withBackground);
		label.setVisible(true, false);
		float w = ww;
		if (w == -1) w = label.getWidth();
		float p = 8;
		
		if (expandX) this.add(label).width(w).pad(p).expandX().align(a);
		else this.add(label).width(w).pad(p).align(a);
		
		return label;
	}

	public void showActorsInfo() {
		
		if (Editor.currentPage.getSelectedGameActors().size == 1) {
			
			GameActor actor = Editor.currentPage.getSelectedGameActors().get(0);
			this.updateLabels(actor.getName(), (int)actor.getX(), (int)actor.getY(), (int)actor.getWidth(), (int)actor.getHeight(),
							  (int) actor.getOriginX(), (int)actor.getOriginY(), (int)actor.getRotation());
		
		} else {
		
			int n = 0, x = 0, y = 0, w = 0, h = 0;
			for (GameActor actor: Editor.currentPage.getSelectedGameActors()) {
				if (x > actor.getX() || n==0) x = (int)actor.getX();
				if (y > actor.getY() || n==0) y = (int)actor.getY();
				n++;
			}
			this.updateLabels(n + " objects", x, y, w, h, 0, 0, 0);
		}
	}
	
	private void updateLabels(String n, int x, int y, int w, int h, int ox, int oy, int angle) {

		this.updateLabel(this.actorNameLabel, n);
		this.updateLabel(this.actorXLabel, "x:" + x);
		this.updateLabel(this.actorYLabel, "y:" + y);
		this.updateLabel(this.actorWidthLabel, "width:" + w);
		this.updateLabel(this.actorHeightLabel, "height:" + h);
		this.updateLabel(this.actorOriginXLabel, "originX:" + ox);
		this.updateLabel(this.actorOriginYLabel, "originY:" + oy);
		this.updateLabel(this.actorRotationLabel, "angle:" + angle);
	}

	public void updateCanvasInfo() {
		
		this.updateLabel(this.canvasSizeLabel, (int)Editor.currentPage.content.getWidth() + " x " + (int)Editor.currentPage.content.getHeight());
		this.updateLabel(this.zoomLabel, Editor.currentPage.scrollPane.getZoomScaled()+"%");
	}
	
	private void updateLabel(Label label, String text) {
		
		label.setText(text);
		if (text.isEmpty()) label.setVisible(true, false);
		else label.setVisible(true, true);
	}
}
