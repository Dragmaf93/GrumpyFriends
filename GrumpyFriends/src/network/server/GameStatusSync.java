package network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.filter.KalmanFilter;

import character.BlackStormtrooper;
import character.Character;
import character.WhiteStormtrooper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GameStatusSync {

	private ObjectMapper mapper;
	private JsonNodeFactory factory;
	
	public GameStatusSync() {
		mapper = new ObjectMapper();
		factory = JsonNodeFactory.instance;
	}
	
	
	public String characterStatusToJson(HashMap<String,Character> characters){
		
		
		
		ArrayNode nodes = new ArrayNode(factory);
		try {
			List<Character> characterList = new ArrayList<Character>(characters.values());
			for (Character character : characterList) {
				ObjectNode jNode = mapper.createObjectNode();	
				jNode.with("Character").put("Nome", character.getName());
				jNode.with("Character").put("x", character.getX());
				jNode.with("Character").put("y", character.getY());
				nodes.add(jNode);
				
			}
			
			return mapper.writeValueAsString(nodes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setCharactersStatus(HashMap<String,Character> characters, String json){
		
		try {
			JsonNode jsonNodeArray= mapper.readTree(json);
			
			for (JsonNode jsonNode : jsonNodeArray) {
				JsonNode jsonCharacter = jsonNode.get("Character");
				Character c = characters.get(jsonCharacter.get("Nome").asText());
				System.out.println("Charadsa      "+ jsonCharacter.get("Nome").asText());
				c.setPosition(jsonCharacter.get("x").asDouble(), jsonCharacter.get("y").asDouble());
			}			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Character c1 = new WhiteStormtrooper("CAIO", null);
		Character c2 = new BlackStormtrooper("BILL", null);
		
	
		HashMap<String,Character> list = new HashMap<String, Character>();
		list.put(c1.getName(),c1);
		list.put(c2.getName(),c2);
		c1.setPosition(100.21, 12.2);
		c2.setPosition(1.21, 1112.2);
		
		GameStatusSync gameStatusSync = new GameStatusSync();
		
		String json= gameStatusSync.characterStatusToJson(list);
		System.out.println(json);
		c1.setPosition(0, 0);
		c2.setPosition(0, 0);
		System.out.println("Position c1 "+ c1.getX()+" "+c1.getY());
		System.out.println("Position c2 "+ c2.getX()+" "+c2.getY());
		
		gameStatusSync.setCharactersStatus(list,json);
		
		System.out.println("Position c1 "+ c1.getX()+" "+c1.getY());
		System.out.println("Position c2 "+ c2.getX()+" "+c2.getY());
		
		
		
	}
}
