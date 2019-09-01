package com.planet2d.editor.pages.stageEditor.actorsPanel.choosers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.planet2d.editor.pages.stageEditor.actorsPanel.ActorsPanel;
import com.planet2d.editor.ui.Panel;
import com.planet2d.editor.ui.ScrollPane;

public class ChooseScroll extends ScrollPane<ChooseItem> {
	
	private ActorsPanel actorsPanel;
	private Panel content;

	public ChooseScroll(Group content, ActorsPanel actorsPanel) {

		super(content, actorsPanel.getWidth()+22, 542, "editorPanel");
		
		this.actorsPanel = actorsPanel;
		
		this.setScrollingDisabled(true, false);
		this.cornerImage.setVisible(false);
		
		this.setPosition(0, -42);
	}
	
	@Override
	public void selectItem(ChooseItem item) {
		
		super.selectItem(item);
		this.actorsPanel.chooseItem((ChooseItem) item);
	}
}
