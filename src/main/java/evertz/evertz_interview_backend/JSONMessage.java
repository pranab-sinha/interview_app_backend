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
		System.out.println(message);
		Object obj = jsonParser.parse(message);
		System.out.println("Inside function" + obj.toString());
		JSONArray studentList = (JSONArray) obj;
		Iterator i = studentList.iterator();
		System.out.println("Testing ---22");
		try {
			while (i.hasNext()) {
				System.out.println("Inside loop");
				JSONObject slide = (JSONObject) i.next();

				JSONObject studentObject = (JSONObject) slide.get("EvertzInterviewApp");
				System.out.println(studentObject);

				String subsystem = (String) studentObject.get("Subsystem");   
				System.out.println(subsystem);
				
				String command = (String) studentObject.get("Command");   
				System.out.println(command);
				
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
		return result;	
	}
}