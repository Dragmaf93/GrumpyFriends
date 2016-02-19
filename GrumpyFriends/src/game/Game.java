package game;

import java.io.IOException;

import menu.MenuPage;
import menu.SequencePage;

public interface Game {
	
	static final String PATH_PACKAGE="character.";
	
	abstract public SequencePage getSequencePages();
	abstract public void startGame() throws IOException;
	abstract public void reset();
	
	public abstract void setUpGame();
	
	public abstract MatchManager getMatchManager();
	public abstract MenuPage nextPage();
	public abstract MenuPage prevPage();
}
