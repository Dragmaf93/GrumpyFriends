package multiplayer;

import game.MatchManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import character.Character;

public class Multiplayer {

	public final static String OP_MOVE_LEFT = "L";
	public final static String OP_MOVE_RIGHT = "R";
	public final static String OP_STOP_MOVE = "S";
	public final static String OP_JUMP = "J";
	private int left,right,stop;

	public final static String OP_EQUIP_WEAPON = "E";
	public final static String OP_INCREASE_AIM = "IA";
	public final static String OP_DECREASE_AIM = "DA";
	public final static String OP_ATTACK = "A";

	private final static String SERVER_READY = "SR";

	private Socket socket;
	private ServerSocket welcomeSocket;

	private BufferedReader inFromServer;
	private DataOutputStream outputStream;
	private MatchManager matchManager;

	private int portNumber = 6789;

	public Multiplayer(MatchManager matchManager) {

		this.matchManager = matchManager;
	}

	public void joinToMatch() {
		try {
			socket = new Socket("192.168.43.113", portNumber);
			portNumber++;
			inFromServer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						welcomeSocket = new ServerSocket(portNumber);
						Socket connectionSocket = welcomeSocket.accept();

						BufferedReader inFromClient = new BufferedReader(
								new InputStreamReader(
										connectionSocket.getInputStream()));

						while (true) {
							if (inFromClient.ready())
								doOperation(inFromClient.readLine());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			sendOperationMessage(SERVER_READY, null);
			matchManager.startMatch();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void connectToServer() {
		try {
			socket = new Socket("192.168.43.113", portNumber);
			inFromServer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
			matchManager.startMatch();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendOperationMessage(String ope, String proprieta) {
		try {
			outputStream.writeBytes(ope + "," + proprieta + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void doOperation(String operazione) {

		String[] op = operazione.split(",");

		switch (op[0]) {
		case OP_MOVE_LEFT:
			System.out.println("SINISTRA");
			matchManager.getCurrentPlayer().move(Character.LEFT);
			left++;
			break;
		case OP_MOVE_RIGHT:
			System.out.println("DESTRA");
			matchManager.getCurrentPlayer().move(Character.RIGHT);
			right++;
			break;
		case OP_JUMP:
			System.out.println("JUMP");
			matchManager.getCurrentPlayer().jump();
			break;
		case OP_STOP_MOVE:
			System.out.println("STOP_MOVE");
			matchManager.getCurrentPlayer().stopToMove();
			stop++;
			break;
		case OP_EQUIP_WEAPON:
			System.out.println("EQUIP_WEAPON");
			matchManager.getCurrentPlayer().equipWeapon(op[1]);
			break;
		case OP_ATTACK:
			System.out.println("ATTACK");
			matchManager.getCurrentPlayer().attack(Float.parseFloat(op[1]));
			break;
		case OP_INCREASE_AIM:
			System.out.println("INCREASE AIM");
			matchManager.getCurrentPlayer().changeAim(Character.INCREASE_AIM);
			break;
		case OP_DECREASE_AIM:
			System.out.println("DECREASE AIM");
			matchManager.getCurrentPlayer().changeAim(Character.DECREASE_AIM);
			break;
		case SERVER_READY:
			connectToServer();
			break;
		default:
			break;
			
		}
		System.out.println(left+"    "+ right+"      "+stop);
	}

	public void closeConnection() {
		try {
			socket.close();
			welcomeSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createMatch() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					welcomeSocket = new ServerSocket(portNumber);
					portNumber++;
					Socket connectionSocket = welcomeSocket.accept();

					BufferedReader inFromClient = new BufferedReader(
							new InputStreamReader(
									connectionSocket.getInputStream()));

					while (true) {
						if (inFromClient.ready())
							doOperation(inFromClient.readLine());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
