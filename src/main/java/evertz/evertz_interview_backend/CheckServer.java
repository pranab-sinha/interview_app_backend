package evertz.evertz_interview_backend;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckServer {
	@SuppressWarnings("unchecked")
	public static String getServerIp(String JSONRequestMessage) throws org.json.simple.parser.ParseException{
		String reqIp = "";
        String status = "";
		String result = "";
		//Get IP and host name of the current server..
		InetAddress ip;
        String hostname;
        String onlyip = "";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            onlyip = ip.getHostAddress();
            System.out.println("Only IP Address:- " + onlyip);
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }

		//JSON parser object to parse read the response
				if(JSONRequestMessage == null) {
					return "NULL MESSAGE RECEIVED";
				}
				JSONParser jsonParser = new JSONParser();
				String message = JSONRequestMessage;
				System.out.println(message);
				Object obj = jsonParser.parse(message);
				System.out.println("Inside function" + obj.toString());
				JSONArray checkServer = (JSONArray) obj;
				System.out.println("checkServer: " + checkServer);
				Iterator i = checkServer.iterator();
				try {
					while (i.hasNext()) {
						JSONObject slide = (JSONObject) i.next();
						System.out.println("i: " + slide);
						JSONObject serverObject = (JSONObject) slide.get("EvertzInterviewApp");
						System.out.println("Hello :- " + serverObject);
						JSONObject parameterList = (JSONObject) serverObject.get("ParameterList");
						//System.out.println("ParameterList: " + parameterList);
						JSONObject getServerIp = (JSONObject) parameterList.get("ServerIp");
						//System.out.println("getServerIp: " + getServerIp);
						reqIp = (String) getServerIp.get("serverIp");   
						System.out.println("IP: " + reqIp);
						
					}
					
					if (onlyip.contentEquals(reqIp)) {
						status = "Connected";
						
					}
					else {
						status = "Not Connected";
					}
					
					JSONObject parameterList = new JSONObject();
					parameterList.put("ServerIp",onlyip);
					parameterList.put("Status",status);
					//parameterList.put(keyName, dataElement);
					
					JSONObject evertzinterviewapp= new JSONObject();
			    	evertzinterviewapp.put("Subsystem", "Server"); 
			    	
			    	evertzinterviewapp.put("Command","ServerStatus");
			    	evertzinterviewapp.put("ParameterList", parameterList);
			    	JSONObject jsonFinal = new JSONObject(); 
			    	jsonFinal.put("EvertzInterviewApp",evertzinterviewapp);

			    	JSONArray jsonoutput = new JSONArray();
			    	jsonoutput.add(jsonFinal);
		        
			    	result = jsonoutput.toString(); 
			    	
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return result;

}
}