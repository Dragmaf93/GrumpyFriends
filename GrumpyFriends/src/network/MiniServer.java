package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Mi creo "+ipClient);
        this.socket = socket;
        this.server = server;

        try {
			inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToClient = new DataOutputStream(socket.getOutputStream()); 
			ipClient = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
        	e.printStackTrace();
        } 
        
        mapper = new ObjectMapper();
    }

    public void run() {
    	  
        while(!socket.isClosed()) { 
        	System.out.println("Attendo");

        		try {
        			
        		if (inFromClient.ready())
        			doOperation(inFromClient.readLine());

        		if(closed && System.currentTimeMillis() > time0+maxTime){
        			socket.close();
        		}
        		
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
        server.removeMiniServer(ipClient);
    	
    }

	private void doOperation(String message) throws IOException {
		System.out.println("MESSAGE ARRIVA"+message);
		String [] operation = message.split(";");
		
		switch (operation[0]) {
		case Message.OP_REQUEST_LIST:
			System.out.println("REQUEST LISTA MATCH");
			String matches = toJSON(server.getMatchesList());
			outToClient.writeBytes(matches + '\n');
			break;
		case Message.OP_REQUEST_INFO_MATCH:
			System.out.println("REQUEST INFO MATCH");
			break;
		case Message.OP_SELECT_MATCH:
			try {
				server.takeLock();
				System.out.println("SELECT MATCH");
				InfoMatch match = server.getInfoMatch(Integer.parseInt(operation[1]));
				if (match.getStatusMatch() == StatusMatch.WAITING) {
					outToClient.writeBytes(Message.OP_CONFIRM+";"+server.getIpCreator(match.getId())+'\n');
					match.setStatusMatch(StatusMatch.BUILDING);
					MiniServer miniServer = server.getMiniServer(server.getIpCreator(match.getId()));
					miniServer.sendMessagePlayerFound(socket.getInetAddress().getHostAddress());
				}
				else
					outToClient.writeBytes(Message.OP_ERROR+";null\n");
			}
			finally {
				server.releaseLock();
			}
			break;
		case Message.OP_SEND_INFO_TEAM:
			System.out.println("SEND INFO TEAM");
			break;
		case Message.OP_CREATE_MATCH:
			try {
				server.takeLock();
				System.out.println("CREATE MATCH");
				System.out.println(operation[1]);
				JsonNode matchJson = mapper.readTree(operation[1]);
				InfoMatch match = InfoMatch.jsonToInfoMatch(matchJson);
				server.addMatch(match,socket.getInetAddress().getHostAddress());
				
				
				outToClient.writeBytes(Message.OP_CONFIRM+'\n');
			}
			finally {
				server.releaseLock();
			}
			break;
		case Message.OP_REQUEST_INFO_TEAM:
			System.out.println("REQUEST INFO TEAM");
			break;
		case Message.OP_REQUEST_INFO_WORLD:
			System.out.println("REQUEST INFO WORLD");
			break;
		case Message.CLOSE:
			outToClient.writeBytes(Message.CLOSE+'\n');
			closed=true;
			time0=System.currentTimeMillis();
		default:
			break;
		}
	}
	
	public void sendMessagePlayerFound(String ip) throws IOException{
		outToClient.writeBytes(Message.OP_SEND_PLAYER_FOUND+";"+ip+'\n');
	}
	
	private String toJSON(Object matches) {
		try {
			return mapper.writeValueAsString(matches);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
