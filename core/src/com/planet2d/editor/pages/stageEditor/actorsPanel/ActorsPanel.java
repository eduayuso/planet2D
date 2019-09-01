package com.planet2d.editor.pages.stageEditor.actorsPanel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.planet2d.editor.config.EditorTypes.ActorType;
import com.planet2d.editor.pages.PanelTab;
import com.planet2d.editor.pages.stageEditor.StagePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.choosers.ActorTypeChoosePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.choosers.CharacterTypeChoosePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.choosers.ChooseItem;
import com.planet2d.editor.pages.stageEditor.actorsPanel.choosers.InteractiveTypeChoosePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.choosers.SceneTypeChoosePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.ActorDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.CharactersDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.EffectsDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.InteractiveDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.ItemsDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.SceneDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.TileMapsDraggablePanel;
import com.planet2d.editor.pages.stageEditor.actorsPanel.draggables.TreesDraggablePanel;
import com.planet2d.engine.Resources;
import com.planet2d.engine.actors.SpriteActor;
import com.planet2d.engine.screens.Screen;

public class ActorsPanel extends Table {
	
	private static final float WIDTH = 128;
	
	// Tabs
	private Group tabGroup;
	private PanelTab actorsTab;
	private PanelTab sceneTab;
	private PanelTab charactersTab;
	private PanelTab interactiveTab;
	private PanelTab tileMapsTab;
	private PanelTab treesTab;
	private PanelTab itemsTab;
	private PanelTab particlesTab;

	// Sub tabs
	private Group subTabGroup;
	private PanelTab typeChooseTab;
	private PanelTab actorDragTab;
	
	// Panels
	private Group panelsGroup;
	
	// Chooser Panels
	private ActorTypeChoosePanel actorTypeChoosePanel;
	private CharacterTypeChoosePanel characterTypeChoosePanel;
	private SceneTypeChoosePanel sceneTypeChoosePanel;
	private InteractiveTypeChoosePanel interactiveTypeChoosePanel;
	
	// Draggable Panels
	private ObjectMap<String, CharactersDraggablePanel> charactersDraggablePanels; // 1 panel per character type
	private ObjectMap<String, SceneDraggablePanel> sceneDraggablePanels; // 1 panel per zone
	private ObjectMap<String, InteractiveDraggablePanel> interactiveDraggablePanel; // 1 panel per interactive actor type
	private TileMapsDraggablePanel tileMapsDraggablePanel;
	private TreesDraggablePanel treesDraggablePanel;
	private ItemsDraggablePanel itemsDraggablePanel;
	private EffectsDraggablePanel effectsDraggablePanel;
	private ActorDraggablePanel loadingDraggablePanel; 
	
	// Loading
	public SpriteActor loadingImage;
	
	public ActorsPanel(StagePanel stageEditorPanel) {
		
		super();
		this.setSize(WIDTH, Screen.getHeight());
		this.setPosition(Screen.getWidth()-this.getWidth(), 0);
		
		this.createTabs();
		this.createSubTabs();
		this.createChooserPanels();
		this.createDraggablePanels();
		this.createLoadingImage();
		
		this.add(this.tabGroup).align(Align.topLeft).padTop(7).expandX().width(WIDTH);
		this.row();
		
		this.add(this.subTabGroup).align(Align.topLeft).padTop(12).expandX().width(WIDTH);
		this.row();
		
		this.add(this.panelsGroup).expand().align(Align.topLeft).padLeft(0).padTop(8).width(WIDTH);
		
		this.pack();
		
		this.typeChooseTab.select(true);
		
		this.setListeners();
	}

	private void createTabs() {

		this.actorsTab = new PanelTab("Actors", (int) WIDTH);
		this.sceneTab = new PanelTab("Scene", (int) WIDTH);
		this.charactersTab = new PanelTab("Characters", (int) WIDTH);
		this.interactiveTab = new PanelTab("Interactive", (int) WIDTH);
		
		this.sceneTab.setVisible(false);
		this.charactersTab.setVisible(false);
		this.interactiveTab.setVisible(false);
		
		this.tabGroup = new Group();
		this.tabGroup.addActor(this.actorsTab);
		this.tabGroup.addActor(this.sceneTab);
		this.tabGroup.addActor(this.charactersTab);
		this.tabGroup.addActor(this.interactiveTab);
		this.tabGroup.setHeight(this.actorsTab.getHeight());
	}
	
	private void createSubTabs() {
		
		this.typeChooseTab = new PanelTab("Select a type", (int) WIDTH);
		this.actorDragTab = new PanelTab("aaaaaa", (int) WIDTH);
		
		this.subTabGroup = new Group();
		this.subTabGroup.setHeight(this.tabGroup.getHeight());
		this.subTabGroup.addActor(this.typeChooseTab);
		this.subTabGroup.addActor(this.actorDragTab);
		
		this.actorDragTab.setVisible(false);
	}
	
	private void createChooserPanels() {

		this.actorTypeChoosePanel = new ActorTypeChoosePanel(this.createScrollPanelContent(), this);
		this.characterTypeChoosePanel = new CharacterTypeChoosePanel(this.createScrollPanelContent(), this);
		this.sceneTypeChoosePanel = new SceneTypeChoosePanel(this.createScrollPanelContent(), this);
		this.interactiveTypeChoosePanel = new InteractiveTypeChoosePanel(this.createScrollPanelContent(), this);
		
		this.panelsGroup = new Group();
		this.panelsGroup.setSize((int) WIDTH, 500);
		
		this.characterTypeChoosePanel.setVisible(false);
		this.sceneTypeChoosePanel.setVisible(false);
		this.interactiveTypeChoosePanel.setVisible(false);
		
		this.panelsGroup.addActor(this.actorTypeChoosePanel);
		this.panelsGroup.addActor(this.characterTypeChoosePanel);
	}
	
	private void createDraggablePanels() {
		
		this.charactersDraggablePanels = new ObjectMap<String, CharactersDraggablePanel>();
		for (Actor actor: this.characterTypeChoosePanel.getItems()) {
			ChooseItem item = (ChooseItem)actor;
			CharactersDraggablePanel panel = new CharactersDraggablePanel(this.createScrollPanelContent(), this, item.path);
			this.charactersDraggablePanels.put(item.path, panel);
			this.panelsGroup.addActor(panel);
			panel.setVisible(false);
		}
		
		/*this.charactersDraggablePanels = new ObjectMap<String, CharactersDraggablePanel>();
		for (ChoosePanelItem item: this.characterTypeChoosePanel.getItems()) {
			CharactersDraggablePanel panel = new CharactersDraggablePanel(this.createScrollPanelContent(), this, item.path);
			this.charactersDraggablePanels.put(item.path, panel);
			this.panelsGroup.addActor(panel);
			panel.setVisible(false);
		}
		
		this.charactersDraggablePanels = new ObjectMap<String, CharactersDraggablePanel>();
		for (ChoosePanelItem item: this.characterTypeChoosePanel.getItems()) {
			CharactersDraggablePanel panel = new CharactersDraggablePanel(this.createScrollPanelContent(), this, item.path);
			this.charactersDraggablePanels.put(item.path, panel);
			this.panelsGroup.addActor(panel);
			panel.setVisible(false);
		}*/
	}

	private Group createScrollPanelContent() {
		
		Group content = new Group();
		content.setSize(WIDTH, 1768);
		return content;
	}
	
	private void createLoadingImage() {

		this.loadingImage = new SpriteActor("editor", "ui/loading.png");
		this.loadingImage.setPosition(this.getWidth()/2f - this.loadingImage.getWidth()/2f + 4, this.getHeight()/2f - this.loadingImage.getHeight()/2f);
		this.loadingImage.setOrigin(this.loadingImage.getWidth()/2f, this.loadingImage.getHeight()/2f);
		this.loadingImage.addAction(Actions.forever(Actions.rotateBy(-360, 0.5f)));
		this.loadingImage.setVisible(false);
		
		this.panelsGroup.addActor(this.loadingImage);
	}

	private void setListeners() {

		this.charactersTab.addListener(new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				charactersTab.select(true);
				typeChooseTab.select(false);
				actorDragTab.select(false);
				return true;
			}
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				charactersTab.select(false);
				selectTab(charactersTab);
			}
		});
	}

	protected void selectTab(PanelTab tab) {
		
		if (tab == this.charactersTab) {
			
			if (this.actorDragTab.isVisible()) {
			
				this.actorDragTab.setVisible(false);
				this.typeChooseTab.setVisible(true);
				this.typeChooseTab.select(true);
				this.characterTypeChoosePanel.setVisible(true);
				this.charactersDraggablePanels.get(this.characterTypeChoosePanel.getSelectedItem().path).setVisible(false);
				
			} else {
				
				this.charactersTab.setVisible(false);
				this.actorsTab.setVisible(true);
				this.typeChooseTab.select(true);
				this.actorTypeChoosePanel.setVisible(true);
				this.characterTypeChoosePanel.setVisible(false);
			}
		}
	}

	public void chooseItem(ChooseItem item) {
		
		if (item.path != null) {
			
			this.typeChooseTab.setVisible(false);
			this.actorDragTab.setVisible(true);
			this.actorDragTab.updateText(item.getName());
			this.actorDragTab.select(true);
		}
		
		if (item.type == ActorType.CHARACTER) {
			
			if (item.path == null) {
				
				this.actorsTab.setVisible(false);
				this.charactersTab.setVisible(true);
				
				this.actorTypeChoosePanel.setVisible(false);
				this.characterTypeChoosePanel.setVisible(true);
				
			} else {
				
				this.characterTypeChoosePanel.setVisible(false);
				this.loadingDraggablePanel = this.charactersDraggablePanels.get(item.path);
				if (this.loadingDraggablePanel.loaded) this.loadingDraggablePanel.setVisible(true);
				else this.loadingDraggablePanel.load();
			}
		}
	}
	
	private boolean isContentLoaded() {

		return Resources.loaded() && (this.loadingDraggablePanel == null || !this.loadingDraggablePanel.loaded);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
		
		if (this.isContentLoaded() && this.loadingImage.isVisible()) {
			this.loadingImage.setVisible(false);
			this.loadingDraggablePanel.addLoadedContent();
		}
	}
}
