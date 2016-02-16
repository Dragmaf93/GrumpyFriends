package network;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import menu.GameBean;
import menu.MenuPage;
import menu.SequencePage;
import menu.networkMenu.NetworkPage;
import menu.teamMenu.TeamPage;
import menu.worldMenu.WorldPage;
import game.AbtractGame;

public class NetworkGame extends AbtractGame {

	private Client client;

	private SequencePage creatorSequence;
	private SequencePage chooserSequence;
	private MenuPage networkPage;
	private TeamPage teamPage;

	public NetworkGame() {
		client = new Client();
		
		teamPage=new TeamPage();
		networkPage = new NetworkPage();

		creatorSequence = new SequencePage(networkPage, new WorldPage(),
				teamPage);
		chooserSequence = new SequencePage(networkPage, teamPage);

		sequencePages = chooserSequence;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpGame() {
		List<MenuPage> menuPages = sequencePages.getMenuPages();
		InfoMatch match = beanToInfoMatch(menuPages.get(0).getGameBeans());
		match.setWorldType(menuPages.get(1).getGameBeans()
				.getFirstValue("WorldType"));

		try {
			client.createMatch(match);
		} catch (IOException e) {
			e.printStackTrace();
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
		if (chooser && sequencePages != chooserSequence) {
			sequencePages = chooserSequence;
			sequencePages.nextPage();
		} else if(!chooser && sequencePages!=creatorSequence){
			sequencePages = creatorSequence;
			sequencePages.nextPage();
		}

	}

	@Override
	public MenuPage nextPage() {
		MenuPage page = sequencePages.currentPage();
		
		if(page==null){
			List<InfoMatch> matches;
			try {
				matches = client.requestMatchList();
				for (InfoMatch infoMatch : matches) {
					System.out.println(infoMatch.getMatchName());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		return sequencePages.nextPage();
	}

	@Override
	public MenuPage prevPage() {
		MenuPage page = sequencePages.prevPage();
		return page;
	}

	public void connectToServer() throws UnknownHostException, IOException {
		client.connectToServer();
	}

	public void disconnetToServer() throws IOException {
		client.closeConnection();
	}
}
