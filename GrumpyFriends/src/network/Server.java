package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
	
	private static int contId = 0;
	private HashMap<Integer, InfoMatch> matchesList;
	private HashMap<Integer, String> ipCreator;
	private HashMap<String, MiniServer> miniServerList;
	
	private Lock lock = new ReentrantLock();
	
	public Server() {
		matchesList = new HashMap<Integer, InfoMatch>();
		ipCreator = new HashMap<Integer, String>();
		miniServerList = new HashMap<String, MiniServer>();
	}
	
	public void addMiniServer(Socket socket){
		
		MiniServer mini = new MiniServer(socket, this);
		miniServerList.put(socket.getInetAddress().getHostAddress(), mini);
		mini.start();
	}
	
	public void addMatch(InfoMatch match,String addressCreator) {
		match.setId(contId);
		ipCreator.put(contId, addressCreator);
		matchesList.put(contId++, match);
	}
	
	public String getIpCreator(int id){
		return ipCreator.get(id);
	}
	
	public MiniServer getMiniServer(String ip){
		return miniServerList.get(ip);
	}
	
	public List<InfoMatch> getMatchesList() {
		return new ArrayList<InfoMatch>(matchesList.values());
	}
	
	public InfoMatch getInfoMatch(int id) {
		return matchesList.get(id);
	}
	public void removeMiniServer(String ipClient){
		
		if(ipCreator.containsValue(ipClient)){
			Set<Integer> keySet = ipCreator.keySet();
			for (Integer integer : keySet) {
				if(ipClient.equals(ipCreator.get(integer))){
					matchesList.remove(integer);
					break;
				}
			}
			miniServerList.remove(ipClient);
		}
	}
	public void takeLock() {
		lock.lock();
	}
	
	public void releaseLock() {
		lock.unlock();
	}
	
	public static void main(String[] args) throws IOException {
		
		Server server = new Server();
		
		server.addMatch(new InfoMatch("a", "a", 2, true, "a"),null);
		server.addMatch(new InfoMatch("b", "b", 2, false, "b"),null);
		server.addMatch(new InfoMatch("c", "c", 2, true, "c"),null);

		
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
			server.addMiniServer(clientSocket);
		}
		serverSocket.close();
	}
	
}
