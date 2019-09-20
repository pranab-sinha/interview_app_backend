/*
	Author: 		Pranab Sinha, Sripad
	Created Date: 	10th September 2019
	Modified Date: 	19th September 2019
	Description: 	All the functions to Save, Get, Delete and Update Candidate information is available in this class
*/

package evertz.evertz_interview_backend.application_api_calls;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import evertz.evertz_interview_backend.application_db_calls.DBConnect;

public class Candidate {
	
    private JSONArray JSONRequestMessage;

    public Candidate(JSONArray receivedMessage) {
    	
        JSONRequestMessage = receivedMessage;
        System.out.println("In Constructor Recived JSON message : " + JSONRequestMessage);
    }
    
    String registerNumber = "";
    String studentName = "";
    String dob = "";
    String collegeId = "";
    String genderString = "";
    String degreeId = "";
    String streamId = "";
    String yearOfPassing = "";
    String cgpa = "";
    String email = "";
    String mobile = "";
    String languageSelected = "";
    String response = "";

    @SuppressWarnings({})
	public String register() throws org.json.simple.parser.ParseException {
    	
        try {
            
        	JSONArray parameterList = JSONRequestMessage;
            JSONObject parameters = (JSONObject) parameterList.get(0);


            registerNumber = (String) parameters.get("RegisterNumber");
            studentName = (String) parameters.get("StudentName");
            dob = (String) parameters.get("DOB");
            collegeId = (String) parameters.get("College");
            genderString = (String) parameters.get("Gender");
            degreeId = (String) parameters.get("Degree");
            streamId = (String) parameters.get("Stream");
            yearOfPassing = (String) parameters.get("YearOfPassing");
            cgpa = (String) parameters.get("CGPA");
            email = (String) parameters.get("Email");
            mobile = (String) parameters.get("Mobile");
            languageSelected = (String) parameters.get("LanguageSelected");
            
            //Handling Gender to send bit to DB
            String gender = "";
            if (genderString == "Male") {
            	
                gender = "b'1'";
            } else {
            	
                gender = "b'0'";
            }

            //Query string to insert registration details
            String updateSQL = "INSERT INTO evertz_interview_app.candidate_details ( `REG_NO`, `NAME`, `DOB`, `GENDER`, `EMAIL`, `MOB_NO`, `COLLEGE_DETAILS_ID`, `DEGREE_ID`, `BRANCH_ID`, `GRAD_YEAR`, `CGPA`) VALUES ( '" + registerNumber + "', '" + studentName + "', '" + dob + "', " + gender + ", '" + email + "', '" + mobile + "', '" + collegeId + "', '" + degreeId + "', '" + streamId + "', '" + yearOfPassing + "', '" + cgpa + "');";
            System.out.println(updateSQL);
            int res = DBConnect.updateData(updateSQL);

            if (res == -1) {
            	
                response = registerationStatus("Failed+" + registerNumber);
                
            } else {
            	
                response = registerationStatus("Success+" + registerNumber);
            }

        } catch (Exception e) {
        	
            e.printStackTrace();
            response = registerationStatus("Failed+" + registerNumber);
        }
        return response;

    }
    
    @SuppressWarnings("unchecked")
    String registerationStatus(String status) {
    	
        String toSplitString = status;

        String finalJSON;
        String[] temp;
        String delimiter = "\\+";
        String st = "";

        temp = toSplitString.split(delimiter);
        String stringRegistrationStatus = temp[0];
        String registrationId = temp[1];

        JSONObject parameterList = new JSONObject();
        parameterList.put("RegisterNumber", registrationId);
        
        if(stringRegistrationStatus.equals("Success")) {

        	st = "true";
            parameterList.put("Registration", "Success");
            parameterList.put("Reason", "Student registration successful");
        } 
        
        else {
        	
        	st = "false";
        	
            parameterList.put("Registration", "Failed");
            parameterList.put("Reason", "Register number already exists, please contact the supervisor!");
        }
        
        JSONObject evertzinterviewapp = new JSONObject();
        evertzinterviewapp.put("Subsystem", "Candidate");
        evertzinterviewapp.put("Success", st);
        
        JSONObject parameterListObject = new JSONObject();
		parameterListObject.put("ParameterList", parameterList);
        
        evertzinterviewapp.put("Output", parameterListObject);
        
        evertzinterviewapp.put("Command", "Save");

        JSONObject jsonFinal = new JSONObject();
        jsonFinal.put("EvertzInterviewApp", evertzinterviewapp);

        JSONArray jsonOutput = new JSONArray();
        jsonOutput.add(jsonFinal);

        finalJSON = jsonOutput.toString();

        return finalJSON;

    }
}