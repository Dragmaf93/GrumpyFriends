package project;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class _TestJSON
{
    public static void main(final String[] args)
    {
        final JsonDBManager dbManager = JsonDBManager.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        System.out.println(dbManager.getMenuDaily(dateFormat.format(date)));
        
//    	try {
//			JsonNode arrNode = new ObjectMapper().readTree(dbManager.getMenuDaily(dateFormat.format(date)));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
