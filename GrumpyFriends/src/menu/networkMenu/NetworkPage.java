package menu.networkMenu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import menu.AbstractMenuPage;
import menu.GameBean;
import menu.MenuButton;
import menu.MenuManager;

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
		
		MenuButton backButton = new MenuButton(200,50, "Back");
		backButton.relocate(root.getPrefWidth()-backButton.getLayoutBounds().getWidth(), 
				root.getPrefHeight()-backButton.getLayoutBounds().getHeight());
		backButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.PRIMARY){
					MenuManager.getInstance().previousPage();
				}
			}
		});
		root.getChildren().addAll(userName, matchList, createMatchPane,backButton);
		
		
		
	}
	
	@Override
	public void nextPage() {
		MenuManager.getInstance().nextPage();
	}

	@Override
	public void backPage() {
		MenuManager.getInstance().previousPage();
	}

	@Override
	public void reset() {
		
	}

	@Override
	public GameBean getGameBeans() {
		GameBean bean = new GameBean("Network");
		
		bean.addData("MatchName",createMatchPane.getValues()[0]);
		bean.addData("NumberPlayer",createMatchPane.getValues()[1]);
		bean.addData("Private",createMatchPane.getValues()[2]);
		bean.addData("Password",createMatchPane.getValues()[3]);
		bean.addData("UserName",userName.getValues()[0]);
		return bean;
	}

	@Override
	public void update() {
		
	}

}
