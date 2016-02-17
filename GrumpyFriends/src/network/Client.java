package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import menu.GameBean;
import character.Team;

public class Client {

	private final static String IP_SERVER = "192.168.43.148";

	private Socket socket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private ObjectMapper mapper;

	private String ipCreator;
	private String ipChooser;

	private boolean imAChooser;

	public Client() {
		mapper = new ObjectMapper();
	}

	public void connectToServer() throws UnknownHostException, IOException {
		socket = new Socket(IP_SERVER, 2343);

		outToServer = new DataOutputStream(socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

	}

	public List<InfoMatch> requestMatchList() throws IOException {
		System.out.println("HO RICHIESTO LA LISTA MATCH");
		outToServer.writeBytes(Message.OP_REQUEST_LIST + '\n');

		List<InfoMatch> listMatch = new ArrayList<InfoMatch>();
		String matches = inFromServer.readLine();

		JsonNode arrayMatch = mapper.readTree(matches);
		for (JsonNode jsonNode : arrayMatch)
			listMatch.add(InfoMatch.jsonToInfoMatch(jsonNode));

		return listMatch;
	}

	public InfoMatch requestInfoMatch(InfoMatch matchChoosed)
			throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_MATCH);
		return null;
	}

	public String getIpChooser() {
		return ipChooser;
	}

	public String getIpCreator() {
		return ipCreator;
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
			ipCreator = resp[1];
//			socket.close();
			return true;
		}
		return false;
	}

	public void setImAChooser(boolean imAChooser) {
		this.imAChooser = imAChooser;
	}

//	public void sendInfoTeam(GameBean gameBean) throws IOException {
//		outToServer.writeBytes(Message.OP_SEND_INFO_TEAM + ";"
//				+ gameBean.toJSON() + "\n");
//	}

	public GameBean requestInfoTeam() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_TEAM);
		return null;
	}

	public GameBean requestInfoWorld() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_WORLD);
		return null;
	}

	public void closeConnection() throws IOException {
		socket.close();
	}

	public boolean createMatch(InfoMatch match) throws IOException {
		String matchJson = mapper.writeValueAsString(match);
		outToServer
				.writeBytes(Message.OP_CREATE_MATCH + ";" + matchJson + '\n');

		String response = inFromServer.readLine();

		if (response.equals(Message.OP_CONFIRM)) {

			response = inFromServer.readLine();
			String[] resp = response.split(";");
			if (resp[0].equals(Message.OP_SEND_PLAYER_FOUND)) {
				ipChooser = resp[1];
//				socket.close();
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client();

		InfoMatch match = new InfoMatch("d", "D", 2, true, "d");
		match.setStatusMatch(StatusMatch.WAITING);
		System.out.println(client.createMatch(match));

		List<InfoMatch> list = client.requestMatchList();
		for (InfoMatch infoMatch : list) {
			System.out.println(infoMatch.getMatchName());

		}

	}

}
