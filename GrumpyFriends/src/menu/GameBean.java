package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameBean {

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
	
	public Object toJSON() {
		return null;
	}

	public HashMap<String, String> getData() {
		return data;
	}
}
