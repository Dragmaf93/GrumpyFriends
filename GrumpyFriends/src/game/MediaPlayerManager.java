package game;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerManager {

	private String PATH_MEDIA="media/";
	
	private Media mainSoundtrack;
	private MediaPlayer mediaPlayer;
	
	
	public MediaPlayerManager() {
		mainSoundtrack = new Media(new File(PATH_MEDIA+"mainSoundtrack.mp3").toURI().toString());
		mediaPlayer = new MediaPlayer(mainSoundtrack);
	    mediaPlayer.setCycleCount(-1);
	
	    
	}
	
	public void play(){
		mediaPlayer.play();
	}
	
	public void stop(){
		mediaPlayer.stop();
	}
	
	public void restart(){
		
	}
}
