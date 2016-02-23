package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReaderFileProperties {

	private static String result;

	public ReaderFileProperties() {
	}
 
	public static String getPropValues(String parameterRequest){
		result = "";
 
		try {
			Properties prop = new Properties();
			String propFileName = "/resources/port.properties";
 
	        String dir = System.getProperty("user.dir");
	        InputStream in = new FileInputStream(dir + propFileName);
	        prop.load(in);
	        in.close();

	        result = prop.getProperty(parameterRequest);
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		ReaderFileProperties r = new ReaderFileProperties();
		System.out.println(r.getPropValues("server_port"));
	}
	
}
