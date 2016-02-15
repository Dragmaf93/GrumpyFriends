package network;

import com.fasterxml.jackson.databind.JsonNode;

public class InfoMatch {

	private int id;
	
	private String matchName;
	private String userName;
	
	private int characterTeamNumber;
	
	private boolean privateMatch;
	private String password;
	
	private String worldType;
	
	private StatusMatch statusMatch;
	
	
	public InfoMatch() {
		
	}

	public InfoMatch(String matchName, String userName,
			int characterTeamNumber, boolean privateMatch, String worldType) {
		
		this.matchName = matchName;
		this.userName = userName;
		this.characterTeamNumber = characterTeamNumber;
		this.privateMatch = privateMatch;
		this.worldType = worldType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCharacterTeamNumber() {
		return characterTeamNumber;
	}

	public void setCharacterTeamNumber(int characterTeamNumber) {
		this.characterTeamNumber = characterTeamNumber;
	}

	public boolean isPrivateMatch() {
		return privateMatch;
	}

	public void setPrivateMatch(boolean privateMatch) {
		this.privateMatch = privateMatch;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWorldType() {
		return worldType;
	}

	public void setWorldType(String worldType) {
		this.worldType = worldType;
	}

	public StatusMatch getStatusMatch() {
		return statusMatch;
	}

	public void setStatusMatch(StatusMatch statusMatch) {
		this.statusMatch = statusMatch;
	}
	
	
	public static InfoMatch jsonToInfoMatch(JsonNode json) {
		
		InfoMatch infoMatch = new InfoMatch();
		
		infoMatch.setId(json.get("id").asInt());
		infoMatch.setMatchName(json.get("matchName").asText());
		infoMatch.setUserName(json.get("userName").asText());
		infoMatch.setCharacterTeamNumber(json.get("characterTeamNumber").asInt());
		infoMatch.setPrivateMatch(json.get("privateMatch").asBoolean());
		if (infoMatch.isPrivateMatch())
			infoMatch.setPassword(json.get("password").asText());

		infoMatch.setWorldType(json.get("worldType").asText());
		infoMatch.setStatusMatch(StatusMatch.valueOf(json.get("statusMatch").asText()));
		
		return infoMatch;
	}
	
}
