package network.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.omg.CORBA.Current;

import network.InfoMatch;
import network.Message;
import network.StatusMatch;
import network.server.StateNetworkGame;
import character.Character;

import com.sun.glass.ui.Menu;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import menu.GameBean;
import menu.MenuManager;
import menu.MenuPage;
import menu.SequencePage;
import menu.networkMenu.NetworkPage;
import menu.networkMenu.WaitingPage;
import menu.teamMenu.TeamPage;
import menu.worldMenu.WorldPage;
import game.AbstractGame;
import game.GameTask;
import game.LocalMatchManager;
import game.MatchManager;
import gui.Popup;

public class ClientGame extends AbstractGame {

    private final class ConnectionTask extends GameTask {
	@Override
	protected void work() throws IOException {

	    client.connectToServer();
	    List<InfoMatch> matches;
	    matches = client.requestMatchList();
	    ((NetworkPage) networkPage).setList(matches);
	}

	@Override
	protected void afterWork() {
	    state = StateNetworkGame.CONNECT;
	    MenuManager.getInstance().hideLoadingPane();
	}

	@Override
	protected void handleException() {
	    MenuManager.getInstance().addExceptionPopup(exceptionServer);
	    exceptionServer.getRightButton().setOnMouseReleased(
		    new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			    if (event.getButton() == MouseButton.PRIMARY) {
				MenuManager.getInstance().previousPage();
				MenuManager.getInstance().removeExceptionPopup(
					exceptionServer);
			    }
			}
		    });
	}
    }

    private final class CreateMatchTask extends GameTask {
	@Override
	protected void work() throws IOException {
	    List<MenuPage> menuPages = sequencePages.getMenuPages();
	    InfoMatch match = beanToInfoMatch(menuPages.get(0).getGameBean());
	    match.setWorldType(menuPages.get(1).getGameBean()
		    .getFirstValue("WorldType"));
	    client.createMatch(match);
	}

	@Override
	protected void afterWork() {
	    state = StateNetworkGame.WAITING_OPPONENT;
	    MenuManager.getInstance().hideLoadingPane();
	}

	@Override
	protected void handleException() {
	    MenuManager.getInstance().addExceptionPopup(exceptionServer);
	    exceptionServer.getRightButton().setOnMouseReleased(
		    new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			    if (event.getButton() == MouseButton.PRIMARY) {
				MenuManager.getInstance().previousPage();
				MenuManager.getInstance().previousPage();
				MenuManager.getInstance().previousPage();
				MenuManager.getInstance().removeExceptionPopup(
					exceptionServer);
			    }
			}
		    });
	}
    }

    private Client client;
    private SequencePage creatorSequence;
    private SequencePage chooserSequence;
    private MenuPage networkPage;
    private TeamPage teamPage;

    private List<GameBean> gamebeans;
    private StateNetworkGame state;

    private Popup exceptionServer;
    private Popup exceptionOpponentConnection;

    public ClientGame() {
	client = new Client();

	teamPage = new TeamPage();
	networkPage = new NetworkPage();

	creatorSequence = new SequencePage(networkPage, new WorldPage(),
		teamPage);
	chooserSequence = new SequencePage(networkPage, teamPage);

	sequencePages = chooserSequence;
	state = StateNetworkGame.DISCONNECTED;

	exceptionServer = new Popup(400, 180, "Server not reacheble", null,
		"Ok");
	exceptionOpponentConnection = new Popup(400, 180, "Connection refused",
		null, "OK");

    }

    @Override
    public void startGame() throws IOException {
	Multiplayer multiplayer = new Multiplayer();
	if (client.imAChooser()) {
	    multiplayer.joinToMatch(client.getMatchServerIp(),
		    client.getServerPortNumber());
	    GameBean teamInfo = teamPage.getGameBean();
	    multiplayer.sendOperationMessage(
		    Message.OP_SEND_INFO_TEAM_TO_CREATOR, teamInfo.toJSON());
	    gamebeans = multiplayer.getGameBean();
	    gamebeans.add(teamInfo);
	} else {
	    multiplayer.createMatch(client.getMatchServerIp(),
		    client.getServerPortNumber());

	    GameBean worldInfo = creatorSequence.getMenuPages().get(1)
		    .getGameBean();
	    multiplayer.sendOperationMessage(Message.OP_SEND_INFO_WORLD,
		    worldInfo.toJSON());

	    GameBean teamInfo = teamPage.getGameBean();
	    multiplayer.sendOperationMessage(
		    Message.OP_SEND_INFO_TEAM_TO_CHOOSER, teamInfo.toJSON());

	    gamebeans = multiplayer.getGameBean();
	    gamebeans.add(worldInfo);
	    gamebeans.add(teamInfo);

	}
	multiplayer.readyToStart();

	for (int i = 0; i < gamebeans.size(); i++) {
	    extractData(gamebeans.get(i));
	}

	matchManager = new ClientMatchManager(battlefield);

	for (Character character : characters) {
	    character.setWorld(battlefield);
	    battlefield.addCharacter(character);
	}
	multiplayer.setMatchManager(matchManager);
	((ClientMatchManager) matchManager).setMultiplayer(multiplayer);
	teams.get(0).setMatchManager(matchManager);
	teams.get(0).setColorTeam(Color.CRIMSON);
	teams.get(1).setMatchManager(matchManager);
	teams.get(1).setColorTeam(Color.STEELBLUE);

	matchManager.setTeamA(teams.get(0));
	matchManager.setTeamB(teams.get(1));

	positionCharacter();
	multiplayer.sendOperationMessage(Message.SERVER_READY, null);
	matchManager.startMatch();

    }

    @Override
    public void reset() {
    }

    private InfoMatch beanToInfoMatch(GameBean bean) {
	InfoMatch infoMatch = new InfoMatch();
	infoMatch.setMatchName(bean.getFirstValue("MatchName"));
	infoMatch.setCharacterTeamNumber(Integer.parseInt(bean
		.getFirstValue("NumberPlayer")));
	infoMatch.setPrivateMatch(Boolean.parseBoolean(bean
		.getFirstValue("Private")));

	if (infoMatch.isPrivateMatch())
	    infoMatch.setPassword(bean.getFirstValue("Password"));
	infoMatch.setStatusMatch(StatusMatch.WAITING);

	infoMatch.setUserName(bean.getFirstValue("UserName"));
	return infoMatch;
    }

    public void setClientType(boolean chooser) {
	client.setImAChooser(chooser);
	if (chooser) {
	    boolean flag = false;
	    try {

		if (client.chooseMatch(((NetworkPage) networkPage)
			.getDetailMatch().getInfoMatch())) {
		    flag = true;
		    state = StateNetworkGame.CHOOSED_MATCH;
		}
	    } catch (IOException e) {
		MenuManager.getInstance().addExceptionPopup(exceptionServer);
		exceptionServer.getRightButton().setOnMouseReleased(
			new EventHandler<MouseEvent>() {

			    @Override
			    public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
				    MenuManager.getInstance().previousPage();
				    MenuManager.getInstance()
					    .removeExceptionPopup(
						    exceptionServer);
				}
			    }
			});
	    }

	    if (sequencePages != chooserSequence) {
		sequencePages = chooserSequence;
		sequencePages.nextPage();
	    }

	    if (flag)
		MenuManager.getInstance().nextPage();

	} else if (!chooser && sequencePages != creatorSequence) {
	    state = StateNetworkGame.CREATED_MATCH;
	    sequencePages = creatorSequence;
	    sequencePages.nextPage();
	}
	
	if(sequencePages.currentPage()==teamPage && client.imAChooser())
	    teamPage.visibleButtonBack(false);
	else if (sequencePages.currentPage()==teamPage && !client.imAChooser())
		teamPage.visibleButtonBack(true);
    }

    @Override
    public MenuPage nextPage() {
	MenuPage page = sequencePages.currentPage();
	if (!client.isConnected() && page == null) {
	    GameTask task = new ConnectionTask();

	    task.startToWork();
	    MenuManager.getInstance().getWaitingPage().setText("Connection...");
	    return MenuManager.getInstance().getWaitingPage();
	} else if (state == StateNetworkGame.CREATED_MATCH && page == teamPage) {
	    GameTask task = new CreateMatchTask();

	    task.startToWork();

	    MenuManager.getInstance().getWaitingPage()
		    .setText("In attesa di un avversario...");
	    return MenuManager.getInstance().getWaitingPage();
	}

	return sequencePages.nextPage();
    }

    @Override
    public MenuPage prevPage() {
	MenuPage page = sequencePages.currentPage();
	if (state == StateNetworkGame.CONNECT && page == networkPage) {
	    try {
		client.closeConnection();
	    } catch (IOException e) {
	    }
	    state = StateNetworkGame.DISCONNECTED;
	}
	return sequencePages.prevPage();
    }

    public void connectToServer() throws UnknownHostException, IOException {
	client.connectToServer();
    }

    public void disconnetToServer() throws IOException {
	client.closeConnection();
    }

    @Override
    protected void positionCharacter() {
	int x = 10;
	for (Character character : characters) {
	    x += 10;
	    character.setStartedPosition(x, 100);
	}
	worldBuilder.lastSettings();
    }

    public void updateListMatch() {
	try {
	    List<InfoMatch> matches;
	    matches = client.requestMatchList();
	    ((NetworkPage) networkPage).setList(matches);
	} catch (IOException e) {
	    MenuManager.getInstance().addExceptionPopup(exceptionServer);
	    exceptionServer.getRightButton().setOnMouseReleased(
		    new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			    if (event.getButton() == MouseButton.PRIMARY) {
				MenuManager.getInstance().previousPage();
				MenuManager.getInstance().removeExceptionPopup(
					exceptionServer);
			    }
			}
		    });
	}
    }
}
