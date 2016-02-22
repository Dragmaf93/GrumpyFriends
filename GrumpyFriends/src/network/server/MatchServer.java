package network.server;

import game.MatchManager;
import game.TurnPhaseType;
import gui.drawer.CharacterDrawer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import network.InfoMatch;
import network.Message;
import world.GameWorldBuilder;
import world.World;
import world.WorldDirector;
import character.Character;
import menu.GameBean;

public class MatchServer extends Thread {

	private ServerSocket serverSocket;

	private Socket creatorSocket;
	private Socket chooserSocket;

	private BufferedReader inFromChooser;
	private DataOutputStream outToChooser;

	private BufferedReader inFromCreator;
	private DataOutputStream outToCreator;

	private BufferedReader inFromCurrentClient;
	private DataOutputStream outToCurrentClient;

	private boolean listeningSocket;
	private int port;
	private InfoMatch match;

	private ServerGame game;

	private MatchManager matchManager;
	private GameStatusSync gameStatusSync;

	private HashMap<String, Character> characters;

	private int contStartNextTurn;

	public MatchServer(int port, InfoMatch match) {
		this.port = port;
		this.match = match;
		listeningSocket = true;
		game = new ServerGame();
		gameStatusSync = new GameStatusSync();
		characters = new HashMap<String, Character>();
	}

	public int getPort() {
		return port;
	}

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket(port);

			creatorSocket = serverSocket.accept();
			inFromCreator = new BufferedReader(new InputStreamReader(
					creatorSocket.getInputStream()));
			outToCreator = new DataOutputStream(creatorSocket.getOutputStream());

			System.out.println("CREATOR "
					+ creatorSocket.getInetAddress().getHostAddress());

			chooserSocket = serverSocket.accept();
			inFromChooser = new BufferedReader(new InputStreamReader(
					chooserSocket.getInputStream()));
			outToChooser = new DataOutputStream(chooserSocket.getOutputStream());

			outToCreator.writeBytes(Message.OP_SEND_PLAYER_FOUND + '\n');

			System.out.println("CHOOSER "
					+ chooserSocket.getInetAddress().getHostAddress());

			doOperation(inFromCreator.readLine());
			doOperation(inFromCreator.readLine());
			doOperation(inFromChooser.readLine());

			game.startGame();

			inFromCurrentClient = inFromCreator;
			outToCurrentClient = outToCreator;

			matchManager = game.getMatchManager();
			((ServerMatchManager) matchManager).setMatchServer(this);
			List<Character> list = matchManager.getBattlefield()
					.getAllCharacters();
			for (Character character : list) {
				characters.put(character.getName(), character);
			}

//			System.out.println(inFromChooser.readLine());
//			System.out.println(inFromCreator.readLine());

			matchManager.startMatch();

			new Thread(new Runnable() {

				@Override
				public void run() {
					boolean exit = false;
					while (!exit) {

						matchManager.update();

						try {
							sendStatusWorld();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}).start();

			while (listeningSocket) {

//				System.out.println("PRIMA DI CAMBIARE "+inFromCurrentClient);
				String mex = inFromCurrentClient.readLine();
				doOperation(mex);
//				System.out.println("DOPO DI CAMBIARE "+inFromCurrentClient);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sendStatusWorld() throws IOException {

		String json = gameStatusSync.characterStatusToJson(characters);
		outToCreator.writeBytes(Message.WORLD_STATUS + ";" + json + '\n');
		outToChooser.writeBytes(Message.WORLD_STATUS + ";" + json + '\n');

	}

	private void doOperation(String operazione) throws UnknownHostException,
			IOException {
		String[] op = operazione.split(";");
//		 System.out.println(op[0]);
		switch (op[0]) {
		case Message.OP_MOVE_LEFT:
			System.out.println("SINISTRA");
			matchManager.getCurrentPlayer().move(Character.LEFT);
			System.out.println("POSITION: "
					+ matchManager.getCurrentPlayer().getX() + "   "
					+ matchManager.getCurrentPlayer().getY());
			break;
		case Message.OP_MOVE_RIGHT:
			System.out.println("DESTRA");
			matchManager.getCurrentPlayer().move(Character.RIGHT);
			System.out.println("POSITION: "
					+ matchManager.getCurrentPlayer().getX() + "   "
					+ matchManager.getCurrentPlayer().getY());
			break;
		case Message.OP_JUMP:
			System.out.println("JUMP");
			matchManager.getCurrentPlayer().jump();
			break;
		case Message.OP_STOP_MOVE:
			System.out.println("STOP_MOVE");
			matchManager.getCurrentPlayer().stopToMove();
			break;
		case Message.OP_EQUIP_WEAPON:
			System.out.println("EQUIP_WEAPON");
			matchManager.getCurrentPlayer().equipWeapon(op[1]);
			break;
		case Message.OP_ATTACK:
			System.out.println("ATTACK");
			matchManager.getCurrentPlayer().attack(Float.parseFloat(op[1]));
			break;
		case Message.OP_INCREASE_AIM:
			System.out.println("INCREASE AIM");
			matchManager.getCurrentPlayer().changeAim(Character.INCREASE_AIM);
			break;
		case Message.OP_DECREASE_AIM:
			System.out.println("DECREASE AIM");
			matchManager.getCurrentPlayer().changeAim(Character.DECREASE_AIM);
			break;
		case Message.OP_SEND_INFO_TEAM_TO_CHOOSER:
			outToChooser.writeBytes(Message.OP_SEND_INFO_TEAM_TO_CHOOSER + ";"
					+ op[1] + '\n');
			game.addBean(GameBean.jSonToGameBean(op[1]));
			break;
		case Message.OP_SEND_INFO_TEAM_TO_CREATOR:
			outToCreator.writeBytes(Message.OP_SEND_INFO_TEAM_TO_CREATOR + ";"
					+ op[1] + '\n');
			game.addBean(GameBean.jSonToGameBean(op[1]));
			break;
		case Message.OP_SEND_INFO_WORLD:
			outToChooser.writeBytes(Message.OP_SEND_INFO_WORLD + ";" + op[1]
					+ '\n');
			game.addBean(GameBean.jSonToGameBean(op[1]));
			break;
		case Message.START_NEXT_TURN:

			contStartNextTurn++;
			System.out.println("CONT " + contStartNextTurn);
			if (contStartNextTurn >= 2) {
				contStartNextTurn = 0;
				matchManager.setCanStartNextTurn(true);
			}
			break;
		case Message.CHANGE_TURN:
			System.out.println("CHANGE_TURN");
			if (inFromCurrentClient == inFromChooser) {
				inFromCurrentClient = inFromCreator;
				outToCurrentClient = outToCreator;
			} else {
				inFromCurrentClient = inFromChooser;
				outToCurrentClient = outToChooser;
			}
		default:
			break;

		}
	}

	public void sendMessage(String message, String parameter) {
		try {
			outToCreator.writeBytes(message + ";" + parameter + '\n');
			outToChooser.writeBytes(message + ";" + parameter + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}