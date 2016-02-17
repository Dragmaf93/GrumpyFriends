package menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameBean {

	private static final ObjectMapper MAPPER= new ObjectMapper();

	private HashMap<String, String> data;
	private String beanName;
	
	private List<String> names;
	private List<String> values;
	
	private int ptr;
	
	public GameBean(String beanName) {
		this.beanName = beanName;
		data = new HashMap<String, String>();
		ptr=-1;
		names = new ArrayList<String>();
		values = new ArrayList<String>();
	}

	public String getNameBean() {
		return beanName;
	}
	
	public boolean hasNext(){
		if(ptr+1>=values.size())
			return false;
		ptr++;
		return true;
	}
	
	public String getNextNameData(){
		return names.get(ptr);
	}
	
	public String getNextValue(){
		return values.get(ptr);
	}
	
	public void addData(String name, String value) {
//		data.put(name, value);
		
		names.add(name);
		values.add(value);
	}
	
	public String getFirstValue(String name){
		for (int i=0;i<names.size();i++){
			if(names.get(i).equals(name))
				return values.get(i);
		}
		return null;
	}
	
	public void firtData(){
		ptr=-1;
	}
	
	public String toJSON() {
		String jSon = "[{\"Name\":\""+beanName+"\",";
		
		for (int i=0;i<names.size();i++){
			jSon+="\""+names.get(i)+"\":\""+values.get(i)+"\",";
		}
		jSon = jSon.substring(0,jSon.length()-1);
		jSon+="}]";
		return jSon;
	}

	public HashMap<String, String> getData() {
		return data;
	}
	
	public static GameBean jSonToGameBean(String jSon){
		try {
			JsonNode node=MAPPER.readTree(jSon).get(0);
			Iterator<Entry<String, JsonNode>> i = node.fields();
			GameBean gameBean= new GameBean(node.get(i.next().getKey()).asText());
			while(i.hasNext()) {
				Entry<String, JsonNode> fieldName=i.next();
				gameBean.addData(fieldName.getKey(), fieldName.getValue().asText());
			}
			
			return gameBean;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public static void main(String[] args) {
		GameBean gameBean=new GameBean("Team");
		gameBean.addData("CIA","CAIO");
		gameBean.addData("CIA","CAO");
		gameBean.addData("CIA","CAIO");
		gameBean.addData("CIA","CAIO");
		gameBean.addData("CIA","CAIO");
		GameBean game = GameBean.jSonToGameBean(gameBean.toJSON());
		
		System.out.println(game.toJSON());
		
	}
}
