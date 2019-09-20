package evertz.evertz_interview_backend.application_api_calls;

/*
	Author: 	Sinjini
Created Date: 	19th September 2019
Modified Date: 	19th September 2019
Description: 	Used to send proper JSON message once connection is successful.
				This class accept JSON Array as arguments and return JSON structure as string.
*/

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Validate {
	private String requestedIpFromJSONArray;
	private String connectionStatus;
	private String responseJSONFormat;
	private JSONArray validateServerIPJSONArray;
	
	public Validate(JSONArray JSONArrayRequest) {
		
		validateServerIPJSONArray = JSONArrayRequest;
		System.out.println("JSON Received - " + validateServerIPJSONArray);
		requestedIpFromJSONArray = getServerIP(validateServerIPJSONArray);
		connectionStatus = validateConnection(requestedIpFromJSONArray);
	}
	
	private String getServerIP(JSONArray ServerIPArray) {
		
		JSONObject serverIPParameterValue = (JSONObject) ServerIPArray.get(0);
		JSONObject server = (JSONObject) serverIPParameterValue.get("ServerIp");
		System.out.println(server);
		String serverIP = (String) server.get("serverIp");
		System.out.println("Server IP - " + serverIP);
		return serverIP;
	}
	
	private String validateConnection(String requestedIPString) {
		
		InetAddress address;
		String localIPAddress;
		String connectionStatus;
		try {
			address = InetAddress.getLocalHost();
			localIPAddress = "10.42.10.46";
			
			if (localIPAddress.contentEquals(requestedIPString)) {
				connectionStatus = "Connected";

			}
			else {
				connectionStatus = "Not Connected";
			}
			
		} catch (UnknownHostException e) {
			
			connectionStatus = "Some Error Occured";
			e.printStackTrace();
		}
		
		return connectionStatus;
	}
	
	@SuppressWarnings("unchecked")
	public String serverResponseJSON () {
		
		JSONObject responseParameterList = new JSONObject();
		responseParameterList.put("ServerIp", requestedIpFromJSONArray);
		responseParameterList.put("Status", connectionStatus);

		JSONObject evertzinterviewapp= new JSONObject();
		evertzinterviewapp.put("Subsystem", "Server"); 

		evertzinterviewapp.put("Command", "ServerStatus");
		evertzinterviewapp.put("ParameterList", responseParameterList);
		JSONObject finalJSONObject = new JSONObject(); 
		finalJSONObject.put("EvertzInterviewApp", evertzinterviewapp);

		JSONArray responseJSONOutput = new JSONArray();
		responseJSONOutput.add(finalJSONObject);

		responseJSONFormat = responseJSONOutput.toString(); 
		
		return responseJSONFormat;
	}
}