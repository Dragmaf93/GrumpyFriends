package menu;

import gui.UpdatablePane;

public interface MenuPage extends UpdatablePane{

	public void nextPage();
	public void backPage();
	public void reset();
	
	public GameBean getGameBean();
}
