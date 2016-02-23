package network.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import utils.ReaderFileProperties;
import network.InfoMatch;
import network.Message;
import network.StatusMatch;
import character.Team;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import menu.GameBean;

public class Client {

	private final static String IP_SERVER = ReaderFileProperties.getPropValues("server_ip");
	private final static int PORT = Integer.parseInt(ReaderFileProperties.getPropValues("client_port"));
	// private final static String IP_SERVER = "192.168.43.148";

	private Socket socket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private ObjectMapper mapper;

	private String matchServerIp;
	private int serverPortNumber;

	private boolean imAChooser;

	private boolean connected;

	public Client() {
		mapper = new ObjectMapper();
	}

	public void connectToServer() throws UnknownHostException, IOException {
		socket = new Socket(IP_SERVER, PORT);

		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		connected = true;

	}

	public List<InfoMatch> requestMatchList() throws IOException {
		// System.out.println("HO RICHIESTO LA LISTA MATCH");
		outToServer.writeBytes(Message.OP_REQUEST_LIST + '\n');

		List<InfoMatch> listMatch = new ArrayList<InfoMatch>();
		String matches = inFromServer.readLine();

		JsonNode arrayMatch = mapper.readTree(matches);
		for (JsonNode jsonNode : arrayMatch)
			listMatch.add(InfoMatch.jsonToInfoMatch(jsonNode));

		return listMatch;
	}

	public boolean isConnected() {
		return connected;
	}

	public InfoMatch requestInfoMatch(InfoMatch matchChoosed)
			throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_MATCH);
		return null;
	}

	public String getMatchServerIp() {
		return matchServerIp;
	}

	public int getServerPortNumber() {
		return serverPortNumber;
	}

	public boolean imAChooser() {
		return imAChooser;
	}

	public boolean chooseMatch(InfoMatch matchChoosed) throws IOException {
		outToServer.writeBytes(Message.OP_SELECT_MATCH + ";"
				+ matchChoosed.getId() + "\n");

		String response = inFromServer.readLine();
		String[] resp = response.split(";");

		if (resp[0].equals(Message.OP_CONFIRM)) {
			matchServerIp = resp[1];
			serverPortNumber = Integer.parseInt(resp[2]);
			closeConnection();
			return true;
		}
		return false;
	}

	public void setImAChooser(boolean imAChooser) {
		this.imAChooser = imAChooser;
	}

	// public void sendInfoTeam(GameBean gameBean) throws IOException {
	// outToServer.writeBytes(Message.OP_SEND_INFO_TEAM + ";"
	// + gameBean.toJSON() + "\n");
	// }

	public GameBean requestInfoTeam() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_TEAM);
		return null;
	}

	public GameBean requestInfoWorld() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_WORLD);
		return null;
	}

	public void closeConnection() throws IOException {
		outToServer.writeBytes(Message.CLOSE + '\n');
		if (inFromServer.readLine().equals(Message.CLOSE)) {
			socket.close();
			connected = false;
		}
	}

	public boolean createMatch(InfoMatch match) throws IOException {
		String matchJson = mapper.writeValueAsString(match);
		outToServer
				.writeBytes(Message.OP_CREATE_MATCH + ";" + matchJson + '\n');

		String response = inFromServer.readLine();
		String[] resp = response.split(";");

		if (resp[0].equals(Message.OP_CONFIRM)) {
			matchServerIp = resp[1];
			serverPortNumber = Integer.parseInt(resp[2]);
			closeConnection();
			return true;
		}
		return false;
	}

//	public static void main(String[] args) throws IOException {
//		Client client = new Client();
//
//		InfoMatch match = new InfoMatch("d", "D", 2, true, "d");
//		match.setStatusMatch(StatusMatch.WAITING);
//
//		List<InfoMatch> list = client.requestMatchList();
//		for (InfoMatch infoMatch : list) {
//
//		}
//
//	}

}
