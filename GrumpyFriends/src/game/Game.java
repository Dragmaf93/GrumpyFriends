package game;

import menu.SequencePage;

public interface Game {
	
	static final String PATH_PACKAGE="character.";
	
	abstract public SequencePage getSequencePages();
	abstract public void startGame();
	abstract public void reset();
	
	public abstract MatchManager getMatchManager();
}
