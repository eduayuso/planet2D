package com.planet2d.engine.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.planet2d.engine.config.Config;
import com.planet2d.engine.Resources;

public class Audio {

	public static Music currentSong;
	private static ArrayMap<String, Long> playingLoopSoundsIds;
	private static boolean fadingMusic;
	public static String currentSongName;
	private static String nextSongName;
	
	public Audio() {
		
		playingLoopSoundsIds = new ArrayMap<String, Long>();
	}
	
	public static void update() {
		
		if (fadingMusic && currentSong != null) {
			
			if (currentSong.getVolume() > 0.001f) {
	            float v = currentSong.getVolume() - Gdx.graphics.getDeltaTime();
	            if(v < 0.001f) v = 0.001f;
	            currentSong.setVolume(v);
			
			} else {
				fadingMusic = false;
				if (nextSongName != null) playSong(nextSongName);
			}
		}
	}

	/* ============================================
	 * SOUNDS
	 * ============================================
	 */
	
	public static void playSound(String soundName) {
		
		playSound(soundName, 1f);
	}
	
	public static void playSound(String soundName, float pitch) {
		
		if (soundName != null && !soundName.isEmpty()) {
			
			try {
				Sound sound = Resources.getSound(Config.gamePath, soundName);
				sound.play(Config.soundVolume/10f, pitch, 0f);
			} catch(Exception e) {
				Gdx.app.error("Warning", "Sound " + soundName + " not found");				
			}
		}
	}
	
	public static void loopSound(String soundName) {
		
		loopSound(soundName, 1);
	}
	
	public static void loopSound(String soundName, float pitch) {
		
		long loopId;
		if (!isPlayingLoopSound(soundName)) {
			loopId = Resources.getSound(Config.gamePath, soundName).loop(Config.soundVolume/10f, pitch, 0f);
			playingLoopSoundsIds.put(soundName, loopId);
		}
	}

	public static void playReverbSound(String soundName, final float pitch) {
		
		final Sound sound = Resources.getSound(Config.gamePath, soundName);
		sound.play(Config.soundVolume/10f, pitch, 0f);
		
		/*Player player = Game.player != null? Game.player : Game.player2;
		
		if (player != null) {
			
			player.addAction(
				Actions.sequence(
					Actions.delay(0.2f/Game.timeFactor),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							sound.play(Config.soundVolume/20f, pitch, 0f);
						}
					}),
					Actions.delay(0.2f/Game.timeFactor),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							sound.play(Config.soundVolume/30f, pitch, 0f);
						}
					})
				)
			);
		}*/
	}
	
	/** Detiene todos los sonidos, no borra ninguno del playingSounds (excepto 
	 * el sonido de la lluvia). */
	public static void stopAllSound() {
		//if (Game.stage.isRaining()) Audio.stopSound("rain");
		Array<Sound> allSounds = Resources.getAllSounds();
		for (Sound s : allSounds) {
			s.stop();
		}
	}
	
	public static void stopSoundLoop(String soundName) {
		
		if (isPlayingLoopSound(soundName)) {
			Long id = playingLoopSoundsIds.get(soundName);
			Resources.getSound(Config.gamePath, soundName).setLooping(id, false);
			playingLoopSoundsIds.removeKey(soundName);
		}
	}
	
	public static void stopSound(String soundName) {
		
		if (Resources.getSound(Config.gamePath, soundName) != null) {
			Resources.getSound(Config.gamePath, soundName).stop();
		}
	}

	/* ============================================
	 * MUSIC
	 * ============================================
	 */
	
	public static void playSong(String songName) {
		
		playSong(songName, Config.musicVolume/10f);
	}
	
	public static void playSong(String songName, boolean loop) {
		
		playSong(songName);
		Audio.currentSong.setLooping(loop);
	}
	
	public static void playSong(String songName, float volume) {
		
		if (Audio.currentSong != null && Audio.currentSong.isPlaying()) {
			
			Audio.currentSong.stop();
			Audio.currentSong.dispose();
		}
		Audio.fadingMusic = false;
		Audio.nextSongName = null;
		Audio.currentSong = Resources.getMusic(Config.gamePath, songName);
		Audio.currentSongName = songName;
		
		if (Audio.currentSong != null) {
			Audio.currentSong.setVolume(volume);
			final String loopSong = songName+"_LOOP";
			
			if (Resources.isLoaded("music/"+loopSong+".mp3", Music.class)) {
				
				Audio.currentSong.setLooping(false);
				Audio.currentSong.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(Music music) {
						playSong(loopSong);
					}
				});
			
			} else {
				Audio.currentSong.setLooping(!songName.equals("intro") && !songName.equals("score") && !songName.equals("game-over"));
			}
			
			Audio.currentSong.play();
		}
	}
	
	public static void stopMusic() {
		
		if (Audio.currentSong != null) {
			Audio.fadingMusic = false;
			Audio.nextSongName = null;
			Audio.currentSong.stop();
			Audio.currentSong.dispose();
		}
	}
	
	public static void pauseMusic() {
		
		Audio.currentSong.pause();
	}
	
	public static void restartMusic() {
		
		Audio.setMusicVolume(Config.musicVolume);
		Audio.currentSong.stop();
		Audio.currentSong.play();
	}
	
	public static void resumeMusic() {
		
		Audio.currentSong.play();
	}
	
	public static void setMusicVolume(float volume) {
		
		if (Audio.currentSong != null) Audio.currentSong.setVolume(volume);
	}
	
	public static void changeSong(String nextSong) {
		
		if (!nextSong.equals(currentSongName)) {
		
			fadingMusic = true;
			nextSongName = nextSong;
		}
	}

	public static void fadeOutMusic() {
		
		fadingMusic = true;
	}
	
	public static boolean isPlayingLoopSound(String soundName) {
		
		return playingLoopSoundsIds.get(soundName) != null;
	}
	
	public static boolean isPlayingSong(String songName) {
		
		return songName == currentSongName;
	}
}