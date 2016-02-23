package network.server;

import java.io.IOException;
import java.net.DatagramSocket;
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
import utils.ReaderFileProperties;
import world.GameWorldBuilder;
import world.World;
import world.WorldDirector;

public class Server {

    private static final int MIN_PORT_NUMBER = 2000;
    private static final int MAX_PORT_NUMBER = 65000;
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

	while (!available(lastPort)) {
	    lastPort++;
	    if (lastPort >= 65000)
		lastPort = 2001;

	}
	
	MatchServer matchServer = new MatchServer(this, lastPort,
		matchesList.get(idInfoMatch));
	matchServers.put(idInfoMatch, matchServer);

	matchServer.start();

	return matchServer.getPort();
    }

    public void addMatch(InfoMatch match, String addressCreator) {
	match.setId(contId);
	matchesList.put(contId++, match);
    }

    public void removeMatchServer(InfoMatch infoMatch) {

	matchServers.remove(infoMatch.getId());
	matchesList.remove(infoMatch.getId());
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
	    serverSocket = new ServerSocket(Integer.parseInt(ReaderFileProperties.getPropValues("client_port")));

	} catch (IOException e) {
	    System.err.println("Could not listen on port: 2343");
	}

	while (listeningSocket) {
	    Socket clientSocket = serverSocket.accept();
	    server.addMiniServer(clientSocket);
	}
	serverSocket.close();
    }

    private static boolean available(int port) {
	if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
	    throw new IllegalArgumentException("Invalid start port: " + port);
	}

	ServerSocket ss = null;
	DatagramSocket ds = null;
	try {
	    ss = new ServerSocket(port);
	    ss.setReuseAddress(true);
	    ds = new DatagramSocket(port);
	    ds.setReuseAddress(true);
	    return true;
	} catch (IOException e) {
	} finally {
	    if (ds != null) {
		ds.close();
	    }

	    if (ss != null) {
		try {
		    ss.close();
		} catch (IOException e) {
		    /* should not be thrown */
		}
	    }
	}

	return false;
    }
}
