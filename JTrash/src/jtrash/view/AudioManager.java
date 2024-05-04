package jtrash.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
	
	Clip clip;
	private static AudioManager instance;
	private List<Clip> clips;



	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	private AudioManager() {

	}

	public void play(String filename) {

		try {
			File file = new File(filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			clip = AudioSystem.getClip();
			clips = new ArrayList<Clip>();
			clips.add(clip);
			clip.open(audioIn);
			clip.start(); 
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public void stopAll() {
		for (Clip clip : clips) {
			if (clip != null && clip.isRunning()) clip.stop();
		}
	}
}
