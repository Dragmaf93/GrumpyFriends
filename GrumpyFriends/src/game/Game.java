package game;

import java.io.IOException;

import menu.GameBean;
import menu.MenuPage;
import menu.SequencePage;

public interface Game {
	
	static final String PATH_PACKAGE="character.";
	
	abstract public SequencePage getSequencePages();
	abstract public void startGame() throws IOException;
	abstract public void reset();
	
	abstract public void addBean(GameBean bean);
	
	public abstract MatchManager getMatchManager();
	public abstract MenuPage nextPage();
	public abstract MenuPage prevPage();
}
