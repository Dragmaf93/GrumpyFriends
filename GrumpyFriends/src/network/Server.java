package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
	
	private static int contId = 0;
	private HashMap<Integer, InfoMatch> matchesList;
	
	private Lock lock = new ReentrantLock();
	
	public Server() {
		matchesList = new HashMap<Integer, InfoMatch>();
	}
	
	public void addMatch(InfoMatch match) {
		match.setId(contId);
		matchesList.put(contId++, match);
	}
	
	public List<InfoMatch> getMatchesList() {
		return new ArrayList<InfoMatch>(matchesList.values());
	}
	
	public InfoMatch getInfoMatch(int id) {
		return matchesList.get(id);
	}
	
	public void takeLock() {
		lock.lock();
	}
	
	public void releaseLock() {
		lock.unlock();
	}
	
	public static void main(String[] args) throws IOException {
		
		Server server = new Server();
		
		server.addMatch(new InfoMatch("a", "a", 2, true, "a"));
		server.addMatch(new InfoMatch("b", "b", 2, true, "b"));
		server.addMatch(new InfoMatch("c", "c", 2, true, "c"));

		
		for (InfoMatch info : server.getMatchesList()) {
			info.setStatusMatch(StatusMatch.WAITING);
		}
		
		ServerSocket serverSocket = null;
		
		boolean listeningSocket = true;
		
		try {
			serverSocket = new ServerSocket(2343);
		
		} catch (IOException e) {
			System.err.println("Could not listen on port: 2343");
		}
		
		while(listeningSocket) {
			Socket clientSocket = serverSocket.accept();
			MiniServer mini = new MiniServer(clientSocket, server);
			mini.start();
		}
		serverSocket.close();
	}
	
}
