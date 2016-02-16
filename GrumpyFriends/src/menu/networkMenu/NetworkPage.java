package menu.networkMenu;

import menu.AbstractMenuPage;
import menu.GameBean;

public class NetworkPage extends AbstractMenuPage {

	private UserName userName;
	private MatchList matchList;
	private CreateMatchPane createMatchPane;
	
	public NetworkPage() {
		
		userName = new UserName(this);
		matchList = new MatchList(this);
		createMatchPane = new CreateMatchPane(this);
		
		createMatchPane.relocate(root.getPrefWidth()-createMatchPane.getWidthComponent(), 
				createMatchPane.getLayoutY());
		
		matchList.relocate(matchList.getLayoutX(),
				root.getPrefHeight()-matchList.getHeightComponent());
		
		root.getChildren().addAll(userName, matchList, createMatchPane);
		
	}
	
	@Override
	public void nextPage() {
		
	}

	@Override
	public void backPage() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public GameBean getGameBeans() {
		return null;
	}

	@Override
	public void update() {
		
	}

}
