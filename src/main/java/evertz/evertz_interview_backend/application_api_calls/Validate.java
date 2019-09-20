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

import evertz.evertz_interview_backend.application_db_calls.DBConnect;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;

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
		
		String testConnectionQuery;
		String connectionStatus;
		try {
			
			testConnectionQuery = "select * from degree";
			ResultSet queryResult = DBConnect.selectData(testConnectionQuery);
			if(queryResult.next() == false) {
				connectionStatus = "Not Connected";
			}
			connectionStatus = "Connection to DB is successful";
			
			
		} catch (Exception e) {
			
			connectionStatus = "Failed to connect to Database Server";
			e.printStackTrace();
		}
		
		return connectionStatus;
	}
	
	@SuppressWarnings("unchecked")
	public String serverResponseJSON () {
		
		String reason;
		String statusMessage = "";
		
		if(connectionStatus.equalsIgnoreCase("Not Connected") || connectionStatus.contains("Failed to connect to Database Server")) {
			reason = "Database server not connected";
			statusMessage = "false";
		}
		else {
			reason = "Database server connected";
			statusMessage = "true";
		}
		
		JSONObject responseParameterList = new JSONObject();
		responseParameterList.put("ServerIp", requestedIpFromJSONArray);
		responseParameterList.put("Status", connectionStatus);
		responseParameterList.put("Reason", reason);

		JSONObject evertzinterviewapp= new JSONObject();
		evertzinterviewapp.put("Subsystem", "Server"); 
		
		JSONObject parameterListObject = new JSONObject();
		parameterListObject.put("ParameterList", responseParameterList);
		System.out.println("Status received after if condition - " + statusMessage);
		evertzinterviewapp.put("Command", "ValidateServer");
		evertzinterviewapp.put("Success", statusMessage);
		evertzinterviewapp.put("Output", parameterListObject);
		
		JSONObject finalJSONObject = new JSONObject(); 
		finalJSONObject.put("EvertzInterviewApp", evertzinterviewapp);

		JSONArray responseJSONOutput = new JSONArray();
		responseJSONOutput.add(finalJSONObject);

		responseJSONFormat = responseJSONOutput.toString(); 
		
		return responseJSONFormat;
	}
}