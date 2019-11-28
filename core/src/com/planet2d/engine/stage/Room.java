package com.planet2d.engine.stage;

import com.badlogic.gdx.utils.Array;
import com.planet2d.engine.Engine;
import com.planet2d.engine.actors.characters.CharacterDialog;
import com.planet2d.engine.actors.characters.enemies.Enemy;
import com.planet2d.engine.actors.traps.Trap;
import com.planet2d.engine.actors.GameActor;
import com.planet2d.engine.actors.characters.Character;

public class Room {

	private int id;
	private int x, y, width, height;
	private boolean death;
	private boolean exit;
	private boolean battle;
	private boolean battle2;
	private boolean visited;
	private boolean disabled;
	private boolean elevator;
	private boolean lava;
	private boolean finalBoss;
	private String song;
	public String specialAction;
	private float zoom;
	private Array<Enemy> enemies;
	private Array<Trap> traps;
	public boolean showGoIndicator;
	public boolean rainDisabled;
	
	public Room() {}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public void setEnemies(Array<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void activateEnemies() {
		
		if (this.enemies != null) {
			for (Enemy enemy: this.enemies) {
				enemy.activate();
			}
		}
	}
	
	/**
	 * SOLO ACTIVAMOS LOS AGUILAS (CHAPUCILLA MENOR)
	 */
	public void activateTraps() {
		
		if (this.traps != null) {
			for (Trap trap: this.traps) {
			//FIXME:	if (trap instanceof Eagle) trap.activate();
			}
		}
	}
	
	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public Character findCharacterForDialog(CharacterDialog dialog) {
		
		Character res = null;
		
		for (Character character: Engine.stage.getCharacters()) {
			
			float charX = character.getBodyCenterX();
			float charY = character.getBodyCenterY();
			
			if (charX < this.getX() + this.getWidth() && charX > this.getX() &&
				charY < this.getY() + this.getHeight() && charY > this.getY()) {
				 
				res = character;
				break;
			}
		}
		
		return res;
	}
	
	public boolean isInside(GameActor obj) {
		
		boolean inside = false;
		
		float charX = obj.getX() + obj.getWidth()/2f;
		float charY = obj.getY() + obj.getHeight()/2f;
		
		if (charX < this.getX() + this.getWidth() && charX > this.getX() &&
			charY < this.getY() + this.getHeight() && charY > this.getY()) {
			inside = true;
		}
		
		return inside;
	}

	public boolean hasBoss() {

		return this.song.contains("boss");
	}

	public void removeEnemy(Enemy enemy) {
		
		this.getEnemies().removeValue(enemy, true);
		if (this.getEnemies().size == 0) {
			Engine.stage.checkPlatformsDoors();
			this.checkSpecialActions();
		}
		// FIME: this.checkTricemastodonStage();
	}
	
	public void checkSpecialActions() {
		
		if (this.specialAction == null || this.specialAction.isEmpty()) return; 
		
		if (this.specialAction.equals("minorodonte-bridge")) {
			// FIME: this.specialActionMinorodonteBridge();
		} else if (this.specialAction.equals("players-hurt-fall")) {
			// FIME: this.specialActionPlayersHurtFall();
		}
	}

	public void removeTrap(Trap trap) {

		this.traps.removeValue(trap, true);
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public boolean isBattle() {
		return battle;
	}

	public void setBattle(boolean battle) {
		this.battle = battle;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Array<Trap> getTraps() {
		return traps;
	}

	public void setTraps(Array<Trap> traps) {
		this.traps = traps;
	}

	public boolean isElevator() {
		return elevator;
	}

	public void setElevator(boolean elevator) {
		this.elevator = elevator;
	}

	public boolean isFinalBoss() {
		return finalBoss;
	}

	public void setFinalBoss(boolean finalBoss) {
		this.finalBoss = finalBoss;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBattle2() {
		return battle2;
	}

	public void setBattle2(boolean battle2) {
		this.battle2 = battle2;
	}
	
	public boolean isLava() {
		return lava;
	}

	public void setLava(boolean lava) {
		this.lava = lava;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public void remove() {
		
		this.song = null;
		this.specialAction = null;
		if (this.enemies != null) {
			this.enemies.clear();
			this.enemies = null;
		}
		if (this.traps != null) {
			this.traps.clear();
			this.traps = null;
		}
	}
}
