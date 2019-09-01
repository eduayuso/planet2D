package com.planet2d.editor.pages.tileMapEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.planet2d.editor.Editor;
import com.planet2d.editor.pages.tileMapEditor.tileObjects.TileSetConfig;
import com.planet2d.editor.ui.Button;
import com.planet2d.editor.ui.Label;
import com.planet2d.editor.ui.Menu;
import com.planet2d.editor.ui.MenuItem;
import com.planet2d.editor.ui.Panel;
import com.planet2d.editor.ui.TextField;
import com.planet2d.engine.Engine;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;
import com.planet2d.engine.ui.Styles.FontType;

public class TileSetItem extends Group {

	private TileSetsPanel tileSetsPanel;
	public TileSetConfig tileSet;
	
	// header
	private Panel headerPanel;
	private Label headerLabel;
	private SpriteActor openDetailImage;
	private SpriteActor closeDetailImage;

	// options menu
	private TileSetItemOptionsMenu optionsMenu;
	
	// config panel
	private Table configPanel;
	private TextField nameTextField;
	private SelectBox<String> patternSelect;
	private SelectBox<String> physicsSelect;
	private SelectBox<String> deformationTopSelect;
	private SelectBox<String> deformationSidesSelect;
	private SelectBox<String> deformationBottomSelect;
	private Button testButton;
	
	public TileSetItem(TileSetsPanel tileSetsPanel, TileSetConfig tileSet) {
		
		super();
		this.tileSetsPanel = tileSetsPanel;
		this.tileSet = tileSet;
		
		this.setSize(tileSetsPanel.getWidth(), 312);
		
		this.headerPanel = this.createHeaderPanel(tileSetsPanel.getWidth());
		this.configPanel = this.createConfigPanel();
		
		this.optionsMenu = new TileSetItemOptionsMenu();
		Editor.canvas.addActor(this.optionsMenu);
		
		this.addActor(this.headerPanel);
		this.addActor(this.configPanel);
		this.close();
	}

	public void close() {

		this.configPanel.setVisible(false);
		this.headerPanel.setY(0);
		this.headerPanel.setSelected(false);
		this.setHeight(this.headerPanel.getHeight());
		this.tileSetsPanel.scroll.repositionItems();
		this.openDetailImage.setVisible(true);
		this.closeDetailImage.setVisible(false);
		
		this.tileSet.template.setVisible(false);
		this.tileSetsPanel.scroll.unselectItem(this);
	}
	
	public void open() {
		
		if (this.configPanel.isVisible()) {
			
			this.close();
		
		} else {

			this.configPanel.setVisible(true);
			this.headerPanel.setY(this.configPanel.getHeight());
			this.headerPanel.setSelected(true);
			this.setHeight(this.headerPanel.getHeight() + this.configPanel.getHeight());
			this.openDetailImage.setVisible(false);
			this.closeDetailImage.setVisible(true);
			
			this.tileSet.template.setVisible(true);
			this.tileSetsPanel.scroll.selectItem(this);
			
			for (TileSetItem item: this.tileSetsPanel.scroll.getItems()) {
				
				if (item != this) item.close();
			}
		}
		
		this.tileSetsPanel.scroll.repositionItems();
	}

	private Panel createHeaderPanel(float width) {

		Panel panel = new Panel("panel-gray2", width-2, 24);
		panel.setBackgroundSelected("panel-gray4");
		panel.setPosition(0, super.getHeight()-panel.getHeight());
		this.headerLabel = new Label(this.tileSet.name, FontType.VERY_SMALL);
		this.headerLabel.setPosition(42, panel.getHeight()/2f - this.headerLabel.getHeight()/2f);
		
		this.openDetailImage = new SpriteActor("editor", "ui/open-detail.png");
		this.openDetailImage.setPosition(18, 4);
		this.closeDetailImage = new SpriteActor("editor", "ui/close-detail.png");
		this.closeDetailImage.setPosition(this.openDetailImage.getX(), this.openDetailImage.getY());
		this.closeDetailImage.setVisible(false);
		
		panel.addActor(this.headerLabel);
		panel.addActor(this.openDetailImage);
		panel.addActor(this.closeDetailImage);
		
		panel.addListener(new InputListener() {
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				headerPanel.setSelected(true);
			}
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (!configPanel.isVisible()) headerPanel.setSelected(false);
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (!configPanel.isVisible()) open();
				if (button == Input.Buttons.RIGHT) {
					optionsMenu.show(Gdx.input.getX(), Screen.getHeight() - Gdx.input.getY());
				}
				return false;
			}
		});
		
		return panel;
	}

	private Table createConfigPanel() {

		Table table = new Table(Engine.skin);
		table.setSize(this.getWidth(), super.getHeight()-this.headerPanel.getHeight());
		table.setBackground("white");
		
		/*
		 * OPTIONS LABEL
		 */
		Label configLabel = new Label("config");
		table.add(configLabel).pad(8).padLeft(18).padTop(10).align(Align.top).colspan(2);
		table.row();
		
		/*  
		 * NAME
		 */
		Label nameLabel = new Label("name", FontType.VERY_SMALL);
		this.nameTextField = new TextField(this.tileSet.name);
		
		table.add(nameLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.nameTextField).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		/*  
		 * PATTERN
		 */
		Label patternLabel = new Label("pattern", FontType.VERY_SMALL);
		this.patternSelect = new SelectBox<String>(Engine.skin);
		this.patternSelect.setItems("1x1", "1x2", "2x1", "2x2");
		this.patternSelect.setSelected(this.tileSet.pattern);
		this.patternSelect.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tileSet.pattern = patternSelect.getSelected();
				tileSet.template.showPatternGroups();
			}
		});
		
		table.add(patternLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.patternSelect).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		/*  
		 * PHYSICS
		 */
		Label shapeLabel = new Label("physics", FontType.VERY_SMALL);
		this.physicsSelect = new SelectBox<String>(Engine.skin);
		this.physicsSelect.setItems("polygon", "line", "none");
		this.physicsSelect.setSelected(this.tileSet.physicsShape);
		
		table.add(shapeLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.physicsSelect).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		/*
		 * DEFORMATION
		 */
		Label deformationLabel = new Label("mesh deformation");
		table.add(deformationLabel).pad(8).padLeft(18).padTop(10).align(Align.top).colspan(2);
		table.row();
		
		Label defTopLabel = new Label("top", FontType.VERY_SMALL);
		this.deformationTopSelect = new SelectBox<String>(Engine.skin);
		this.deformationTopSelect.setItems("stretch", "loop");
		this.deformationTopSelect.setSelected(this.tileSet.meshDeformation[0]);
		table.add(defTopLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.deformationTopSelect).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		Label defSidesLabel = new Label("sides", FontType.VERY_SMALL);
		this.deformationSidesSelect = new SelectBox<String>(Engine.skin);
		this.deformationSidesSelect.setItems("stretch", "loop");
		this.deformationSidesSelect.setSelected(this.tileSet.meshDeformation[1]);
		table.add(defSidesLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.deformationSidesSelect).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		Label defBottomLabel = new Label("bottom", FontType.VERY_SMALL);
		this.deformationBottomSelect = new SelectBox<String>(Engine.skin);
		this.deformationBottomSelect.setItems("stretch", "loop");
		this.deformationBottomSelect.setSelected(this.tileSet.meshDeformation[2]);
		table.add(defBottomLabel).pad(4).padLeft(18).padTop(4).align(Align.topLeft);
		table.add(this.deformationBottomSelect).width(table.getWidth()/2f).pad(4).padTop(0).align(Align.topRight);
		table.row();
		
		/*
		 * TEST AND DESIGN BUTTON
		 */
		
		this.testButton = new Button("Test") {
			@Override
			public void runAction() {
				com.planet2d.editor.Actions.saveProject();
				TileMapPage page = (TileMapPage)Editor.currentPage;
				if (!page.testMode) {
					testButton.setText("Design");
				} else {
					testButton.setText("Test");
				}
				page.runTestMode();
			}
		};
		
		table.add(this.testButton).width(64).align(Align.center).padBottom(8).colspan(2).expand();
		
		
		return table;
	}
	
	public class TileSetItemOptionsMenu extends Menu {

		public TileSetItemOptionsMenu() {
			
			super(
					new MenuItem("Delete", "deleteTileSet")
				);
		}
	}
}
