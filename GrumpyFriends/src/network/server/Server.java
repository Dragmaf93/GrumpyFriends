package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import network.InfoMatch;
import network.StatusMatch;
import world.GameWorldBuilder;
import world.World;
import world.WorldDirector;

public class Server {

	private static int contId = 0;
	private int lastPort;
	private HashMap<Integer, InfoMatch> matchesList;
	private HashMap<String, MiniServer> miniServerList;
	private HashMap<Integer, MatchServer> matchServers;

	private Lock lock = new ReentrantLock();

	public Server() {
		lastPort = 2001;
		matchesList = new HashMap<Integer, InfoMatch>();
		miniServerList = new HashMap<String, MiniServer>();
		matchServers = new HashMap<Integer, MatchServer>();
	}

	public void addMiniServer(Socket socket) {

		MiniServer mini = new MiniServer(socket, this);
		miniServerList.put(socket.getInetAddress().getHostAddress(), mini);
		mini.start();
	}

	public int addMatchServers(int idInfoMatch) {

		MatchServer matchServer = new MatchServer(lastPort,matchesList.get(idInfoMatch));
		matchServers.put(idInfoMatch, matchServer);

		matchServer.start();
		lastPort++;

		return matchServer.getPort();
	}

	public void addMatch(InfoMatch match, String addressCreator) {
		match.setId(contId);
		matchesList.put(contId++, match);
	}

	public int getMatchServerPort(int i) {
		return matchServers.get(i).getPort();
	}

	public List<InfoMatch> getMatchesList() {
		return new ArrayList<InfoMatch>(matchesList.values());
	}

	public InfoMatch getInfoMatch(int id) {
		return matchesList.get(id);
	}

	public void removeMiniServer(String ipClient) {
		miniServerList.remove(ipClient);
	}

	public void takeLock() {
		lock.lock();
	}

	public void releaseLock() {
		lock.unlock();
	}

	public static void main(String[] args) throws IOException {

		Server server = new Server();

		server.addMatch(new InfoMatch("a", "a", 2, true, "a"), null);
		server.addMatch(new InfoMatch("b", "b", 2, false, "b"), null);
		server.addMatch(new InfoMatch("c", "c", 2, true, "c"), null);

		for (InfoMatch info : server.getMatchesList()) {
			info.setStatusMatch(StatusMatch.WAITING);
		}

		ServerSocket serverSocket = null;

		boolean listeningSocket = true;

		try {
			serverSocket = new ServerSocket(2000);

		} catch (IOException e) {
			System.err.println("Could not listen on port: 2343");
		}

		while (listeningSocket) {
			Socket clientSocket = serverSocket.accept();
			server.addMiniServer(clientSocket);
		}
		serverSocket.close();
	}
	


}
