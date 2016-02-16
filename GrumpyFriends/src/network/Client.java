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

	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private ObjectMapper mapper;

	public Client() {
		mapper = new ObjectMapper();
	}

	public void connectToServer() throws UnknownHostException, IOException {
		clientSocket = new Socket("127.0.0.1", 2343);

		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

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

	public boolean chooseMatch(InfoMatch matchChoosed) throws IOException {
		outToServer.writeBytes(Message.OP_SELECT_MATCH + ";"
				+ matchChoosed.getId() + "\n");

		String response = inFromServer.readLine();

		if (response.equals(Message.OP_CONFIRM))
			return true;
		return false;
	}

	public void sendInfoTeam(Team team) throws IOException {
		outToServer.writeBytes(Message.OP_SEND_INFO_TEAM);
	}

	public GameBean requestInfoTeam() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_TEAM);
		return null;
	}

	public GameBean requestInfoWorld() throws IOException {
		outToServer.writeBytes(Message.OP_REQUEST_INFO_WORLD);
		return null;
	}

	public void closeConnection() throws IOException {
		clientSocket.close();
	}

	public boolean createMatch(InfoMatch match) throws IOException {
		String matchJson = mapper.writeValueAsString(match);
		outToServer
				.writeBytes(Message.OP_CREATE_MATCH + ";" + matchJson + '\n');

		String response = inFromServer.readLine();

		if (response.equals(Message.OP_CONFIRM))
			return true;
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
