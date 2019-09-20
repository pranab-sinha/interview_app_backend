package evertz.evertz_interview_backend.application_api_calls;
/*
 	Author: 		Sauravk
	Created Date: 	19th September 2019
	Modified Date: 	19th September 2019
	Description: 	Any information the UI needs to show in the HTML page. Eg: College, Degree and Branch data from DB.
*/
import java.sql.ResultSet;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import evertz.evertz_interview_backend.application_db_calls.DBConnect;

public class UI {
	private JSONArray JSONRequestMessage;
   public UI(JSONArray receivedMessage) {
    	JSONRequestMessage = receivedMessage;
    }
    @SuppressWarnings("unchecked")
    public String getJsonList(JSONArray JSONRequestMessage) throws org.json.simple.parser.ParseException {
        String jsonParameterListDetails = "";
        String response = "";
        String dropDownDataToFetch = "";
        String dropDownKeyNameList = "";
        //JSON parser object to parse and print the object
        
        try {
            	
                JSONArray parameterList = JSONRequestMessage;
                JSONObject parameters = (JSONObject) parameterList.get(0);
                jsonParameterListDetails = (String) parameters.get("UIDropdownField");

            
            
            //Check condition and fetch the data and keyName
            if (jsonParameterListDetails.equals("College")) {
            	dropDownDataToFetch = "college_details";
            	dropDownKeyNameList = "CollegeList";
            } else if (jsonParameterListDetails.equals("Degree")) {
            	dropDownDataToFetch = "degree";
            	dropDownKeyNameList = "DegreeList";
            } else if (jsonParameterListDetails.equals("Branch")) {
            	dropDownDataToFetch = "branch";
            	dropDownKeyNameList = "BranchList";
            } else if (jsonParameterListDetails.equals("Modules")) {
            	dropDownDataToFetch = "modules";
            	dropDownKeyNameList = "ModuleList";
            }
            
            //query to fetch the details from the database
            String detailsDataFetch = "select * from " + dropDownDataToFetch + ";";
            System.out.println("SQL Query - " + detailsDataFetch);
            ResultSet rs = DBConnect.selectData(detailsDataFetch);
            
            //JSON Array object to add each json data 
            JSONArray dataElementObj = new JSONArray();
            while (rs.next()) {
                JSONObject eachData = new JSONObject();
                eachData.put("id", rs.getInt(1));
                eachData.put("name", rs.getString(2));
                dataElementObj.add(eachData);
            }
            
            //JSON parameter object to put the KeyName and dataElementObj
            JSONObject parameterList_obj = new JSONObject();
            parameterList_obj.put(dropDownKeyNameList, dataElementObj);
            
            //JSON evertzinterviewapp object to put all the subsystem,UI,Command and update
            JSONObject evertzinterviewappObj = new JSONObject();
            evertzinterviewappObj.put("Subsystem", "UI");
            evertzinterviewappObj.put("Command", "Get");
            evertzinterviewappObj.put("Success", "true");
            
            JSONObject parameterListObject = new JSONObject();
    		parameterListObject.put("ParameterList", parameterList_obj);
            
            evertzinterviewappObj.put("Output", parameterListObject);
            
            JSONObject jsonFinalObj = new JSONObject();
            jsonFinalObj.put("EvertzInterviewApp", evertzinterviewappObj);
            
            //JSON array object and output the final JSON
            JSONArray jsonOutputObj = new JSONArray();
            jsonOutputObj.add(jsonFinalObj);
            
            //Output the JSON Object to String
            response = jsonOutputObj.toString();
            
            //catch the exception error
        } catch (Exception e) {
            e.printStackTrace();
        } 
        //Finally return the response
        return response;
    }

}