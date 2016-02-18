package network;

import game.MatchManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import menu.GameBean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import character.Character;

public class Multiplayer {

	private Socket socket;
	private ServerSocket welcomeSocket;

	private Lock lock=new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	private BufferedReader inFromServer;
	private DataOutputStream outputStream;
	private MatchManager matchManager;

	private int portNumber = 6789;

	private String ipChooser;
	private String ipCreator;

	private ObjectMapper mapper;
	private List<GameBean> gameBeans;

	public Multiplayer(MatchManager matchManager) {

		this.matchManager = matchManager;
		this.mapper = new ObjectMapper();
		gameBeans = new ArrayList<GameBean>();
	}

	public Multiplayer() {	
		this.mapper = new ObjectMapper();
	gameBeans = new ArrayList<GameBean>();
	}

	public void setMatchManager(MatchManager matchManager) {
		this.matchManager = matchManager;
	}

	public void joinToMatch() {
		try {
			socket = new Socket(ipCreator, portNumber);
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

			sendOperationMessage(Message.SERVER_READY, null);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setIps(String chooser, String creator) {
		ipChooser = chooser;
		ipCreator = creator;
	}

	private void connectToChooser() {
		try {
			System.out.println(outputStream);
			socket = new Socket(ipChooser, portNumber);
			inFromServer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println(outputStream);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendOperationMessage(String ope, String proprieta) {
		try {
			System.out.println("Operazione"+ope+" "+proprieta+" "+outputStream);
			outputStream.writeBytes(ope + ";" + proprieta + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<GameBean> getGameBean() {
		return gameBeans;
	}
	
	public void readyToStart(){
		try{
			lock.lock();
			
			while (gameBeans.size()<3) {
				condition.await();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	private void doOperation(String operazione) {
		System.out.println(operazione);
		String[] op = operazione.split(";");

		switch (op[0]) {
		case Message.OP_MOVE_LEFT:
			System.out.println("SINISTRA");
			matchManager.getCurrentPlayer().move(Character.LEFT);
			break;
		case Message.OP_MOVE_RIGHT:
			System.out.println("DESTRA");
			matchManager.getCurrentPlayer().move(Character.RIGHT);
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
		case Message.SERVER_READY:
			connectToChooser();
			break;
		case Message.OP_SEND_INFO_TEAM:
		case Message.OP_SEND_INFO_WORLD:
			addBean(GameBean.jSonToGameBean(op[1]));
			break;
		default:
			break;

		}
	}
	private void addBean(GameBean bean){
		lock.lock();
		try{
			gameBeans.add(bean);
			if(gameBeans.size()>=3)
				condition.signal();
		}finally{
			lock.unlock();
		}
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

		try {
			System.out.println("ansasddddddddddddddddddddddddddddddddddd");
			welcomeSocket = new ServerSocket(portNumber);
			portNumber++;
			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			doOperation(inFromClient.readLine());

			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							if (inFromClient.ready())
								doOperation(inFromClient.readLine());
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
