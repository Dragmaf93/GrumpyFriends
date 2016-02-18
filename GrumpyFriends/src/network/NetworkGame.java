package network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.sun.glass.ui.Menu;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import character.Character;
import menu.GameBean;
import menu.MenuManager;
import menu.MenuPage;
import menu.SequencePage;
import menu.networkMenu.NetworkPage;
import menu.networkMenu.WaitingPage;
import menu.teamMenu.TeamPage;
import menu.worldMenu.WorldPage;
import game.AbtractGame;
import game.GameTask;
import game.LocalMatchManager;
import game.MatchManager;

public class NetworkGame extends AbtractGame {

	private Client client;
	private SequencePage creatorSequence;
	private SequencePage chooserSequence;
	private MenuPage networkPage;
	private TeamPage teamPage;
	
	private StateNetworkGame state;

	public NetworkGame() {
		client = new Client();

		teamPage = new TeamPage();
		networkPage = new NetworkPage();

		creatorSequence = new SequencePage(networkPage, new WorldPage(),
				teamPage);
		chooserSequence = new SequencePage(networkPage, teamPage);

		sequencePages = chooserSequence;
		state = StateNetworkGame.DISCONNECTED;
	}

	@Override
	public void startGame() {
		Multiplayer multiplayer = new Multiplayer();
		multiplayer.setIps(client.getIpChooser(), client.getIpCreator());
		List<GameBean> gamebeans=null;
		if(client.imAChooser()){
			multiplayer.joinToMatch();
			GameBean teamInfo = teamPage.getGameBean();
			System.out.println("CHOOSER "+teamInfo.toJSON());
			multiplayer.sendOperationMessage(Message.OP_SEND_INFO_TEAM, teamInfo.toJSON());
			
			gamebeans = multiplayer.getGameBean();
			gamebeans.add(teamInfo);
			
		}else{
			multiplayer.createMatch();

			GameBean worldInfo = creatorSequence.getMenuPages().get(1).getGameBean();
			multiplayer.sendOperationMessage(Message.OP_SEND_INFO_WORLD, worldInfo.toJSON());
			
			GameBean teamInfo = teamPage.getGameBean();
			System.out.println("CREATOR "+teamInfo.toJSON());
			multiplayer.sendOperationMessage(Message.OP_SEND_INFO_TEAM, teamInfo.toJSON());
			
			gamebeans = multiplayer.getGameBean();
			gamebeans.add(worldInfo);
			gamebeans.add(teamInfo);
			
		}
		
		multiplayer.readyToStart();
		
		for (int i = 0; i< gamebeans.size();i++) {
			extractData(gamebeans.get(i));
		}
		
		matchManager = new NetworkMatchManager(battlefield);
		
		multiplayer.setMatchManager(matchManager);
		((NetworkMatchManager)matchManager).setMultiplayer(multiplayer);
		teams.get(0).setMatchManager(matchManager);
		teams.get(0).setColorTeam(Color.CRIMSON);
		teams.get(1).setMatchManager(matchManager);
		teams.get(1).setColorTeam(Color.STEELBLUE);
	
		matchManager.setTeamA(teams.get(0));
		matchManager.setTeamB(teams.get(1));
		
		for (Character character :characters) {
			character.setWorld(battlefield);
			battlefield.addCharacter(character);
		}
		
		
		positionCharacter();
		
		matchManager.startMatch();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpGame() {

		if (!client.imAChooser()) {
			
			List<MenuPage> menuPages = sequencePages.getMenuPages();
			InfoMatch match = beanToInfoMatch(menuPages.get(0).getGameBean());
			match.setWorldType(menuPages.get(1).getGameBean()
					.getFirstValue("WorldType"));

			try {
				client.createMatch(match);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
						.getDetailMatch().getInfoMatch()))
					flag = true;

			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sequencePages != chooserSequence) {
				sequencePages = chooserSequence;
				sequencePages.nextPage();
			}

			if (flag)
				MenuManager.getInstance().nextPage();

		} else if (!chooser && sequencePages != creatorSequence) {
			sequencePages = creatorSequence;
			sequencePages.nextPage();
		}

	}

	@Override
	public MenuPage nextPage() {

		if (state == StateNetworkGame.DISCONNECTED) {
			GameTask task = new GameTask() {
				
				@Override
				protected void work() {
					try {
						client.connectToServer();
						List<InfoMatch> matches;
						try {
							matches = client.requestMatchList();
							((NetworkPage) networkPage).setList(matches);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				protected void afterWork() {
					MenuManager.getInstance().hideLoadingPane();
					state=StateNetworkGame.CONNECT;
				}
			};
			task.startToWork();

		MenuManager.getInstance().getWaitingPage().setText("Caricamento");
		return MenuManager.getInstance().getWaitingPage();
		}
		return sequencePages.nextPage();
	}

	@Override
	public MenuPage prevPage() {
		MenuPage page = sequencePages.currentPage();
		if(state == StateNetworkGame.CONNECT && page==networkPage){
			try {
				client.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
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
	
	public void updateListMatch() {
		List<InfoMatch> matches;
		try {
			matches = client.requestMatchList();
			((NetworkPage) networkPage).setList(matches);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
