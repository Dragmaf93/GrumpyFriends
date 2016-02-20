package network.server;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameStatusSync {

	private ObjectMapper mapper;
	
	public GameStatusSync() {
		mapper = new ObjectMapper();
	}
	
	void setCharactersStatus(List<Character> characters, String json){
		
		try {
			JsonNode jsonNode = mapper.readTree(json);

			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
