package evertz.evertz_interview_backend;

import java.util.Iterator;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSONMessage {


	public static String JSONParse(String JSONRequestMessage) throws org.json.simple.parser.ParseException {
		//JSON parser object to parse read the response
		String result = "";
		if(JSONRequestMessage == null) {
			return "NULL MESSAGE RECEIVED";
		}
		JSONParser jsonParser = new JSONParser();
		String message = "[" + JSONRequestMessage + "]";
		System.out.println("JSON request message received : " + message);
		Object obj = jsonParser.parse(message);
		
		JSONArray studentList = (JSONArray) obj;
		Iterator i = studentList.iterator();

		try {
			while (i.hasNext()) {
				JSONObject slide = (JSONObject) i.next();

				JSONObject studentObject = (JSONObject) slide.get("EvertzInterviewApp");
				String subsystem = (String) studentObject.get("Subsystem");   
				String command = (String) studentObject.get("Command");   
				
				if((subsystem.equals("Server")) && (command.equals("ValidateServer"))) {
					result = CheckServer.getServerIp(message);
				}
				
				if((subsystem.equals("StudentRegistration")) && (command.equals("Save"))) {
					result = StudentRegistration.Register(message);
				}
				if((subsystem.equals("UI")) && (command.equals("Get"))) {
					result = UIDropDownList.getListJSON(message);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = result.substring(1, result.length()-1);
		return result;	
	}
}