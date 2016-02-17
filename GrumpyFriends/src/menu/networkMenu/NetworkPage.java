package menu.networkMenu;

import gui.Popup;

import java.util.List;

import network.InfoMatch;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import menu.AbstractMenuPage;
import menu.GameBean;
import menu.MenuButton;
import menu.MenuManager;

public class NetworkPage extends AbstractMenuPage {

	private UserName userName;
	private MatchList matchList;
	private CreateMatchPane createMatchPane;
	private DetailMatch detailMatch;
	private Popup messagePopup;

	public NetworkPage() {

		userName = new UserName(this);
		matchList = new MatchList(this);
		createMatchPane = new CreateMatchPane(this);
		detailMatch = new DetailMatch(this);

		createMatchPane.relocate(
				root.getPrefWidth() - createMatchPane.getWidthComponent(),
				createMatchPane.getLayoutY());

		matchList.relocate(matchList.getLayoutX(), root.getPrefHeight()
				- matchList.getHeightComponent());

		MenuButton backButton = new MenuButton(root.getPrefWidth() / 5, 50,
				"Back");
		// backButton.relocate(root.getPrefWidth()-backButton.getLayoutBounds().getWidth(),
		// root.getPrefHeight()-backButton.getLayoutBounds().getHeight());
		backButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					MenuManager.getInstance().previousPage();
					matchList.reset();
					createMatchPane.reset();
					userName.reset();
				}
			}
		});

		MenuButton createMatch = new MenuButton(root.getPrefWidth() / 5, 50,
				"Create Match");
		// createMatch.relocate(createMatchPane.getLayoutX(),
		// backButton.getLayoutY());
		createMatch.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (isAllInsert()) {
						MenuManager.getInstance().setClientType(false);
						NetworkPage.this.nextPage();
					}
				}
			}

		});

		HBox button = new HBox(10, createMatch, backButton);
		button.relocate(root.getPrefWidth()
				- (backButton.getLayoutBounds().getWidth() + createMatch
						.getLayoutBounds().getWidth()), root.getPrefHeight()
				- backButton.getLayoutBounds().getHeight());

		root.getChildren().addAll(userName, matchList, createMatchPane, button,
				detailMatch);
		detailMatch.setVisible(false);

	}

	private boolean isAllInsert() {

		if (!createMatchPane.isAllInsert()) {
			messagePopup = new Popup(300, 200, "Missing Value", "",
					"Ok");
			messagePopup.changeColorForNetwork();
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(
				new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						root.getChildren().remove(messagePopup);
					}
				});
		}
		else if (!userName.isInsertUserName()) {
			insertPopupUsernameMissing();
		}
		else  {
			return true;
		}
		return false;
	}
	
	public void insertPopupUsernameMissing() {
		messagePopup = new Popup(350, 200, "Missing Username", "",
				"Ok");
		messagePopup.changeColorForNetwork();
		root.getChildren().add(messagePopup);
		messagePopup.getRightButton().setOnMouseReleased(
			new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					root.getChildren().remove(messagePopup);
				}
			});
	}

	public boolean isUsernameInsert() {
		if (!userName.isInsertUserName())
			return false;
		return true;
	}

	public void setList(List<InfoMatch> i) {
		matchList.setInfoMatchesList(i);
	}

	@Override
	public void nextPage() {
		MenuManager.getInstance().nextPage();
	}

	public DetailMatch getDetailMatch() {
		return detailMatch;
	}

	@Override
	public void backPage() {
		MenuManager.getInstance().previousPage();
	}

	@Override
	public void reset() {

	}

	@Override
	public GameBean getGameBean() {
		GameBean bean = new GameBean("Network");

		bean.addData("MatchName", createMatchPane.getValues()[0]);
		bean.addData("NumberPlayer", createMatchPane.getValues()[1]);
		bean.addData("Private", createMatchPane.getValues()[2]);
		bean.addData("Password", createMatchPane.getValues()[3]);
		bean.addData("UserName", userName.getValues()[0]);
		return bean;
	}

	@Override
	public void update() {

	}

}
