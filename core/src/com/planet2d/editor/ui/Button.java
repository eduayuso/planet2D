package com.planet2d.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import com.planet2d.engine.Engine;
import com.planet2d.editor.Actions;

public class Button extends com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton {

	public String actionName;
	public Menu menu; // dropdown menu (optional)
	
	public Button(JsonValue json) {
		this(json, "default");
	}
	
	public Button(JsonValue json, String styleName) {
		super(json.getString("text"), Engine.skin, styleName);
		this.create(json);
	}
	
	public Button(String text, String styleName) {
		super(text, Engine.skin, styleName);
		this.create(null);
	}
	
	public Button(String text) {
		
		super(text, Engine.skin);
		this.create(null);
	}
	
	protected void create(JsonValue json) {
		
		if (json != null) {
		
			int padL = 10, padR = 10;
			if (json.get("padLeft") != null) padL = json.getInt("padLeft");
			if (json.get("padRight") != null) padR = json.getInt("padRight");
			this.pad(0, padL, 0, padR);
			
			if (json != null) {
				if (json.get("menu") != null) {
					this.menu = new Menu(this, json.get("menu"));
					this.addActor(this.menu);
				}
				if (json.get("action") != null) this.actionName = json.getString("action");
			}
		}
		
		this.addListener(new ButtonListener(this));
	}
	
	public class ButtonListener extends ChangeListener {
		
		private Button button;
		
		public ButtonListener(Button textButton) {
			this.button = textButton;
		}

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			
			if (this.button.isPressed()) {
				if (this.button.menu != null) {
					this.button.menu.show();
				} else {
					this.button.runAction();
				}
				
			} else {
				
				if (this.button.menu != null) this.button.menu.hide();
			}
		}
	}
	
	public void setAction(String actionName) {
		
		this.actionName = actionName;
	}

	public void runAction() {

		if (this.actionName != null) {
			Actions.invokeAction(this.actionName);
		}
	}
	
}
