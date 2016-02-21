package network.client;

import game.MatchManager;
import gui.Popup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;













import network.Message;
import network.server.GameStatusSync;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import menu.GameBean;
import menu.MenuManager;
import character.Character;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.glass.ui.Menu;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class Multiplayer {

	private Socket socket;
//	private ServerSocket welcomeSocket;

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	private BufferedReader inFromServer;
	private DataOutputStream outToServer;
	
	private MatchManager matchManager;
	
	private Popup exception;
	
	private int portNumber;

	private String ipMatchServer;

	private List<GameBean> gameBeans;

	private boolean chooser;
	private GameStatusSync gameStatusSync;

	private HashMap<String, Character> characters;
	public Multiplayer(MatchManager matchManager) {
		gameStatusSync = new GameStatusSync();
		this.matchManager = matchManager;
		gameBeans = new ArrayList<GameBean>();
	}

	public Multiplayer() {
		characters = new HashMap<String, Character>();
		gameBeans = new ArrayList<GameBean>();
		gameStatusSync = new GameStatusSync();
		exception = new Popup(400, 180, "Connection refused", null, "OK");
		exception.getRightButton().setOnMouseReleased(
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							MenuManager.getInstance().goToMainMenu();
							MenuManager.getInstance().removeExceptionPopup(
									exception);
						}
					}
				});
	}

	public void setMatchManager(MatchManager matchManager) {
		this.matchManager = matchManager;
		List<Character> list=matchManager.getBattlefield().getAllCharacters();
		for (Character character : list) {
			characters.put(character.getName(), character);
		}
	}
	
	public boolean isAChooser(){
		return chooser;
	}
	
	public void joinToMatch(String ipMatchServer, int portNumber) throws UnknownHostException, IOException {
			
			socket = new Socket(ipMatchServer,portNumber);
			inFromServer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outToServer = new DataOutputStream(socket.getOutputStream());

			chooser=true;
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {


						while (true) {
							if (inFromServer.ready())
								doOperation(inFromServer.readLine());
						}
					} catch (IOException e) {
						matchManager.pauseMatch();
						MenuManager.getInstance().addExceptionPopup(exception);
					}
				}
			}).start();

	}

	public void setIpPort(String chooser,int port) {
		ipMatchServer = chooser;
		portNumber = port;
	}

//	private void connectToChooser() throws UnknownHostException, IOException {
//			socket = new Socket(ipMatchServer, portNumber);
//			inFromServer = new BufferedReader(new InputStreamReader(
//					socket.getInputStream()));
//			outToServer = new DataOutputStream(socket.getOutputStream());
//			System.out.println(outToServer);
//	}

	public void sendOperationMessage(String ope, String proprieta){
		
			try {
				outToServer.writeBytes(ope + ";" + proprieta + '\n');
			} catch (IOException e) {
				matchManager.pauseMatch();
				MenuManager.getInstance().addExceptionPopup(exception);
			}

	}

	public List<GameBean> getGameBean() {
		return gameBeans;
	}

	public void readyToStart() {
		try {
			lock.lock();

			while (gameBeans.size() < 3) {
				condition.await();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	private void doOperation(String operazione) throws UnknownHostException, IOException {
		String[] op = operazione.split(";");

		switch (op[0]) {
		case Message.WORLD_STATUS:
			System.out.println("POSITION 1 : "+matchManager.getCurrentPlayer().getX()+"   "+matchManager.getCurrentPlayer().getY());
			gameStatusSync.setCharactersStatus(characters, op[1]);
			System.out.println("POSITION 2 : "+matchManager.getCurrentPlayer().getX()+"   "+matchManager.getCurrentPlayer().getY());
			break;
		case Message.OP_SEND_INFO_TEAM_TO_CHOOSER:
		case Message.OP_SEND_INFO_WORLD:
		case Message.OP_SEND_INFO_TEAM_TO_CREATOR:
			addBean(GameBean.jSonToGameBean(op[1]));
			break;
		default:
			break;

		}
	}

	private void addBean(GameBean bean) {
		lock.lock();
		try {
			gameBeans.add(bean);
			if (gameBeans.size() >= 3)
				condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public void closeConnection() throws IOException {
			socket.close();
//			s.close();
	}
	
	

	public void createMatch(String ipMatchServer, int portNumber) throws IOException {

	
//			welcomeSocket = new ServerSocket(portNumber);
//			portNumber++;
//			Socket connectionSocket = welcomeSocket.accept();

//			BufferedReader inFromClient = new BufferedReader(
//					new InputStreamReader(connectionSocket.getInputStream()));
//			doOperation(inFromClient.readLine());
		
			socket = new Socket(ipMatchServer, portNumber);
			inFromServer = new BufferedReader(new InputStreamReader(
			socket.getInputStream()));
			outToServer = new DataOutputStream(socket.getOutputStream());
		
			chooser=false;
			
//			
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							if (inFromServer.ready())
								doOperation(inFromServer.readLine());
						} catch (IOException e) {
							matchManager.pauseMatch();
							MenuManager.getInstance().addExceptionPopup(exception);
						}

					}
				}
			}).start();
//		

	}
}
