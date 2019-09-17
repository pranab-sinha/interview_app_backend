package evertz.evertz_interview_backend;

import java.sql.ResultSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UIDropDownList {
	@SuppressWarnings("unchecked")
	public static String getListJSON(String JSONRequestMessage) throws org.json.simple.parser.ParseException{
		String details = "";
		String result = "";
		String dataToFetch = "";
		String keyName = "";
		//JSON parser object to parse read the response
				if(JSONRequestMessage == null) {
					return "NULL MESSAGE RECEIVED";
				}
				JSONParser jsonParser = new JSONParser();
				String message = JSONRequestMessage;
				System.out.println(message);
				Object obj = jsonParser.parse(message);
				System.out.println("Inside function" + obj.toString());
				JSONArray studentList = (JSONArray) obj;
				Iterator i = studentList.iterator();
				try {
					while (i.hasNext()) {
						JSONObject slide = (JSONObject) i.next();
						JSONObject studentObject = (JSONObject) slide.get("EvertzInterviewApp");
						System.out.println("Hello :- " + studentObject);
						JSONArray parameterList = (JSONArray) studentObject.get("ParameterList");
						JSONObject parameters = (JSONObject) parameterList.get(0);
						details = (String)parameters.get("Details");
						
					}
					
					if(details.equals("College")) {
						dataToFetch = "college_details";
						keyName = "CollegeName";
					}
					else if(details.equals("Degree")) {
						dataToFetch = "degree";
						keyName = "DegreeName";
					}
					else if(details.equals("Branch")) {
						dataToFetch = "branch";
						keyName = "BranchName";
					}
					
					String fetchData = "select * from " + dataToFetch + ";";
					ResultSet rs = DAOLayer.selectData(fetchData);

					JSONArray dataElement = new JSONArray();
					
					while(rs.next()) {
						JSONObject eachData = new JSONObject();
						eachData.put("id", rs.getInt(1));
						eachData.put("name", rs.getString(2));
						dataElement.add(eachData);
					}
					JSONObject parameterList = new JSONObject();
					parameterList.put(keyName, dataElement);
					
					JSONObject evertzinterviewapp= new JSONObject();
			    	evertzinterviewapp.put("Subsystem", "UI"); 
			    	
			    	evertzinterviewapp.put("Command","Update");
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
