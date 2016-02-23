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
import java.util.Map;

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
    
    private Server server;

    
    private MatchManager matchManager;
    
    private GameStatusSync gameStatusSync;

    private Map<String, Character> characters;

    private int contStartNextTurn;
    boolean closeMatchUpdate = false;

    public MatchServer(Server server, int port, InfoMatch match) {
	this.port = port;
	this.match = match;
	this.server = server;
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


	    chooserSocket = serverSocket.accept();
	    inFromChooser = new BufferedReader(new InputStreamReader(
		    chooserSocket.getInputStream()));
	    outToChooser = new DataOutputStream(chooserSocket.getOutputStream());

	    outToCreator.writeBytes(Message.OP_SEND_PLAYER_FOUND + '\n');


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

	    inFromChooser.readLine();
	    inFromCreator.readLine();

	    matchManager.startMatch();

	    new Thread(new Runnable() {

		@Override
		public void run() {
		    while (!closeMatchUpdate) {

			matchManager.update();
			if (matchManager.isMatchFinished()) {
			    closeMatchUpdate = true;
			    listeningSocket = false;
			} else {
			    sendStatusWorld();
			}
		    }
		}
	    }).start();

	    while (listeningSocket) {

		String mex = inFromCurrentClient.readLine();
		doOperation(mex);
	    }

	} catch (IOException e) {
	    closeMatchUpdate = true;
	    listeningSocket = false;
	}

	try {
	    creatorSocket.close();
	    chooserSocket.close();

	} catch (IOException e) {
	}

	server.removeMatchServer(match);
    }

    private void sendStatusWorld() {

	try {
	    String json = gameStatusSync.characterStatusToJson(characters,
		    matchManager.getCurrentPlayer(), matchManager);

	    outToCreator.writeBytes(Message.WORLD_STATUS + ";" + json + '\n');
	    outToChooser.writeBytes(Message.WORLD_STATUS + ";" + json + '\n');
	} catch (IOException e) {
	    closeMatchUpdate = true;
	    listeningSocket = false;
	}

    }

    private void doOperation(String operazione) throws UnknownHostException,
	    IOException {
	String[] op = operazione.split(";");
	switch (op[0]) {
	case Message.OP_MOVE_LEFT:
	    matchManager.getCurrentPlayer().move(Character.LEFT);
	    break;
	case Message.OP_MOVE_RIGHT:
	    matchManager.getCurrentPlayer().move(Character.RIGHT);
	    break;
	case Message.OP_JUMP:
	    matchManager.getCurrentPlayer().jump();
	    break;
	case Message.OP_STOP_MOVE:
	    matchManager.getCurrentPlayer().stopToMove();
	    break;
	case Message.OP_EQUIP_WEAPON:
	    matchManager.getCurrentPlayer().equipWeapon(op[1]);
	    break;
	case Message.OP_ATTACK:
	    matchManager.getCurrentPlayer().attack(Float.parseFloat(op[1]));
	    break;
	case Message.OP_INCREASE_AIM:
	    matchManager.getCurrentPlayer().changeAim(Character.INCREASE_AIM);
	    break;
	case Message.OP_DECREASE_AIM:
	    matchManager.getCurrentPlayer().changeAim(Character.DECREASE_AIM);
	    break;
	case Message.OP_STOP_AIM:
	    matchManager.getCurrentPlayer().changeAim(Character.STOP);
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
	case Message.SET_STATER_PHASE:

	    contStartNextTurn++;
	    if (contStartNextTurn >= 2) {
		contStartNextTurn = 0;
		matchManager.setCanStartNextTurn(true);
	    }
	    break;
	case Message.CHANGE_TURN:
	    if (inFromCurrentClient == inFromChooser) {
		inFromCurrentClient = inFromCreator;
		outToCurrentClient = outToCreator;
	    } else {
		inFromCurrentClient = inFromChooser;
		outToCurrentClient = outToChooser;
	    }
	    break;
	case Message.SET_DAMAGE_PHASE:
	    matchManager.setCanClearDamageCharacter(true);
	    break;
	case Message.SET_DEATH_PHASE:
	    matchManager.setCanRemoveDeathCharacter(true);
	    break;
	default:
	    break;

	}
    }

    public void sendMessage(String message, String parameter) {
	try {
	    outToCreator.writeBytes(message + ";" + parameter + '\n');
	    outToChooser.writeBytes(message + ";" + parameter + '\n');
	} catch (IOException e) {
	    closeMatchUpdate = true;
	    listeningSocket = false;
	}
    }

}
