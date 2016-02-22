package network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.filter.KalmanFilter;

import sun.launcher.resources.launcher;
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

import element.weaponsManager.Launcher;
import game.MatchManager;

public class GameStatusSync {

	private ObjectMapper mapper;
	private JsonNodeFactory factory;

	public GameStatusSync() {
		mapper = new ObjectMapper();
		factory = JsonNodeFactory.instance;
	}

	public String characterStatusToJson(HashMap<String, Character> characters,
			Character currentCharacter, MatchManager matchManager) {
	    	ArrayNode root = new ArrayNode(factory);
		ArrayNode nodes = new ArrayNode(factory);
		
		try {
			List<Character> characterList = new ArrayList<Character>(
					characters.values());
			for (Character character : characterList) {
				ObjectNode jNode = mapper.createObjectNode();
				jNode.with("Character").put("Nome", character.getName());
				jNode.with("Character").put("x", character.getX());
				jNode.with("Character").put("y", character.getY());
				jNode.with("Character").put("moving", character.isMoving());
				jNode.with("Character").put("jumping", character.isJumping());
				jNode.with("Character").put("falling", character.isFalling());
				jNode.with("Character").put("grounded", character.isGrounded());
				jNode.with("Character").put("speedVectorX",
						character.getSpeedVector().x);
				jNode.with("Character").put("speedVectorY",
						character.getSpeedVector().y);
				jNode.with("Character").put("currentDirection",
						character.getCurrentDirection());

				nodes.add(jNode);
			}
			ObjectNode nodeCharacter = mapper.createObjectNode();

			Launcher launcher = currentCharacter.getLauncher();
			nodeCharacter.with("CurrentCharacter").put("activeLauncher",
					launcher.isActivated());
			nodeCharacter.with("CurrentCharacter").put("currentDirection",
				currentCharacter.getCurrentDirection());
			// jNode.with("Character").put("sufferedDamage",
			// character.sufferedDamage());
			// jNode.with("Character").put("lifePoints",
			// character.getLifePoints());
			// jNode.with("Character").put("lastDamagePoints",
			// character.getLastDamagePoints());

			if (currentCharacter.getLauncher().isActivated()) {
				nodeCharacter.with("CurrentCharacter").put("weapon",
						currentCharacter.getLauncher().getLoadedWeapon().getName());
				nodeCharacter.with("CurrentCharacter").put("aimDirection",
						currentCharacter.getAimDirection());
//				nodeCharacter.with("CurrentCharacter").put("attacked", currentCharacter.);

				
			}
			else if(currentCharacter.getLastEquippedWeapon()!=null
				&& currentCharacter.getLastEquippedWeapon().attacked()){
			    
			    nodeCharacter.with("CurrentCharacter").put("weaponAttack", true);
			    nodeCharacter.with("CurrentCharacter").put("weaponX",currentCharacter.getLastEquippedWeapon().getX());
			    nodeCharacter.with("CurrentCharacter").put("weaponY",currentCharacter.getLastEquippedWeapon().getY());
//			    System.out.println(currentCharacter.getLastEquippedWeapon().getX()+"    "+currentCharacter.getLastEquippedWeapon().getY());
			    nodeCharacter.with("CurrentCharacter").put("weaponAngle",currentCharacter.getLastEquippedWeapon().getAngle());
			    nodeCharacter.with("CurrentCharacter").put("exploded",currentCharacter.getLastEquippedWeapon().isExploded());
			    
			}
			
			ObjectNode nodeTime = mapper.createObjectNode();
			nodeTime.with("Time").put("Turn", matchManager.turnTimerLeft());
			nodeTime.with("Time").put("Match", matchManager.matchTimerLeft());
			
			root.add(nodeCharacter);
			root.add(nodes);
			root.add(nodeTime);
			
			return mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setCharactersStatus(HashMap<String, Character> characters, Character currentCharacter,MatchManager matchManager, String json) {

		try {
			JsonNode jsonRoot = mapper.readTree(json);
			JsonNode jsonCurrentCharacter = jsonRoot.get(0).get("CurrentCharacter");
			JsonNode jsonNodeArray = jsonRoot.get(1);
			JsonNode jsonTime = jsonRoot.get(2).get("Time");
			
			matchManager.setTurnTimerLeft(jsonTime.get("Turn").asText());
			matchManager.setMatchTimerLeft(jsonTime.get("Match").asText());
			
			
			currentCharacter.setActiveLauncher(jsonCurrentCharacter.get("activeLauncher")
				.asBoolean());
			currentCharacter.getLauncher().setDirection(
				jsonCurrentCharacter.get("currentDirection").asInt());
			if (jsonCurrentCharacter.get("activeLauncher").asBoolean()) {
			    currentCharacter.getLauncher().activate();
			} else
			    currentCharacter.getLauncher().disable();
			
			
			if (jsonCurrentCharacter.get("weapon") != null) {
			    currentCharacter.equipWeapon(jsonCurrentCharacter.get("weapon").asText());
			    currentCharacter.changeAim((float) jsonCurrentCharacter.get("aimDirection")
				    .asDouble());
//			    System.out.println((float) jsonCurrentCharacter.get("aimDirection")
//				    .asDouble());
			} 
//			else if(jsonCurrentCharacter.get("attacked") != null && jsonCurrentCharacter.get("attacked").asBoolean()){
//			    currentCharacter.setAttacked(true);
			    if(jsonCurrentCharacter.has("weaponAttack")
				    && jsonCurrentCharacter.get("weaponAttack").asBoolean()){
				currentCharacter.getLastEquippedWeapon().setX(jsonCurrentCharacter.get("weaponX").asDouble());
				currentCharacter.getLastEquippedWeapon().setY(jsonCurrentCharacter.get("weaponY").asDouble());
				currentCharacter.getLastEquippedWeapon().setAngle(jsonCurrentCharacter.get("weaponAngle").asDouble());
				currentCharacter.getLastEquippedWeapon().setExploded(jsonCurrentCharacter.get("exploded").asBoolean());
				
//				System.out.println(currentCharacter.getLastEquippedWeapon()+"    "+currentCharacter.getLastEquippedWeapon().getX()
//					+"  "+currentCharacter.getLastEquippedWeapon().getY());
				
			    }
//			}
			
			
			for (JsonNode jsonNode : jsonNodeArray) {
				JsonNode jsonCharacter = jsonNode.get("Character");
				Character c = characters
						.get(jsonCharacter.get("Nome").asText());

				c.setPosition(jsonCharacter.get("x").asDouble(), jsonCharacter
						.get("y").asDouble());
				c.setMoving(jsonCharacter.get("moving").asBoolean());
				c.setJumping(jsonCharacter.get("jumping").asBoolean());
				c.setGrounded(jsonCharacter.get("grounded").asBoolean());
//				c.setDied(jsonCharacter.get("died").asBoolean());
//				c.setOutWorld(jsonCharacter.get("outWorld").asBoolean());
//				c.setEndTurn(jsonCharacter.get("endTurn").asBoolean());
				c.setCurrentDirection(jsonCharacter.get("currentDirection")
						.asInt());
				c.setSpeedVector((float) jsonCharacter.get("speedVectorX")
						.asDouble(), (float) jsonCharacter.get("speedVectorY")
						.asDouble());

				// c.setSufferedDamage(jsonCharacter.get("sufferedDamage").asBoolean());
				// c.setLifePoints(jsonCharacter.get("lifePoints").asInt());
				// c.setDamagePoints(jsonCharacter.get("lastDamagePoints").asInt());

				// c.setLastEquippedWeaponName(jsonCharacter.get("lastEquippedWeaponName").asText());

			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
