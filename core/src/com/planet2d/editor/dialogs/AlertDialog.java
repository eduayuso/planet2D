package com.planet2d.editor.dialogs;

import com.planet2d.editor.ui.Label;
import com.planet2d.engine.ui.Dialog;

public class AlertDialog extends Dialog {

	public AlertDialog(String title, String msg) {
		
		super(title);
		
		Label label = new Label(msg);
		label.setWrap(true);
		
		this.getContentTable().add(label).pad(20).width(180);
		this.button("Ok", false).padBottom(20);
	}
}
