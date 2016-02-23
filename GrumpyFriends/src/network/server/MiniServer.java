package network.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import network.InfoMatch;
import network.Message;
import network.StatusMatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MiniServer extends Thread {

	private Socket socket = null;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private String message;
	
	private Server server;
	private String ipClient;
	
	private boolean closed;
	private long time0;
	private long maxTime=5000;
	
	ObjectMapper mapper;

    public MiniServer(Socket socket, Server server) {

        super("MiniServer");
        this.socket = socket;
        this.server = server;

        try {
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream()); 
			ipClient = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
        } 
        
        mapper = new ObjectMapper();
    }

    public void run() {
    	  
        while(!socket.isClosed()) { 
        		try {
        			
        		if (inFromClient.ready())
        			doOperation(inFromClient.readLine());

        		if(closed && System.currentTimeMillis() > time0+maxTime){
        			socket.close();
        		}
        		
			} catch (IOException e) {
			}
        } 
        server.removeMiniServer(ipClient);
    	
    }

	private void doOperation(String message) throws IOException {
		String [] operation = message.split(";");
		
		switch (operation[0]) {
		case Message.OP_REQUEST_LIST:
			String matches = toJSON(server.getMatchesList());
			outToClient.writeBytes(matches + '\n');
			break;
		case Message.OP_SELECT_MATCH:
			try {
				server.takeLock();
				InfoMatch match = server.getInfoMatch(Integer.parseInt(operation[1]));
				if (match.getStatusMatch() == StatusMatch.WAITING) {
					int port = server.getMatchServerPort((match.getId()));
					outToClient.writeBytes(Message.OP_CONFIRM+";"
							+socket.getLocalAddress().getHostAddress()+";"+port+'\n');
					match.setStatusMatch(StatusMatch.BUILDING);
				}
				else
					outToClient.writeBytes(Message.OP_ERROR+";null\n");
			}
			finally {
				server.releaseLock();
			}
			break;
		case Message.OP_CREATE_MATCH:
			try {
				server.takeLock();
				JsonNode matchJson = mapper.readTree(operation[1]);
				InfoMatch match = InfoMatch.jsonToInfoMatch(matchJson);
				server.addMatch(match,socket.getInetAddress().getHostAddress());
				int port = server.addMatchServers(match.getId());
				outToClient.writeBytes(Message.OP_CONFIRM+";"
						+socket.getLocalAddress().getHostAddress()+";"+port+'\n');
			}
			finally {
				server.releaseLock();
			}
			break;
		case Message.CLOSE:
			outToClient.writeBytes(Message.CLOSE+'\n');
			closed=true;
			time0=System.currentTimeMillis();
		default:
			break;
		}
	}
	
	private String toJSON(Object matches) {
		try {
			return mapper.writeValueAsString(matches);
		} catch (JsonProcessingException e) {
		}
		return null;
	}
	
}
