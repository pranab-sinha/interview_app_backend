package evertz.evertz_interview_backend;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StudentRegistration {
	public static String Register(String JSONRequestMessage) throws org.json.simple.parser.ParseException {
		String registerNumber = "";
		String studentName = "";
		String dob = "";
		String college = "";
		String gender = "";
		String degree = "";
		String stream = "";
		String yearofpassing = "";
		String cgpa = "";
		String email = "";
		String Mobile = "";
		String LanguageSelected = "";
		String result = "";

		JSONParser jsonParser = new JSONParser();
		
		String message = JSONRequestMessage;
		System.out.println("JSON request message received : " + message);
		
		Object obj = jsonParser.parse(message);

		JSONArray studentList = (JSONArray) obj;
		Iterator i = studentList.iterator();
		
		try {
			while (i.hasNext()) {
				JSONObject slide = (JSONObject) i.next();

				JSONObject studentObject = (JSONObject) slide.get("EvertzInterviewApp");

				String subsystemName = (String) studentObject.get("Subsystem");   
				String CommandName = (String) studentObject.get("Command");   
				JSONArray parameterList = (JSONArray) studentObject.get("ParameterList");
				JSONObject parameters = (JSONObject) parameterList.get(0);


				registerNumber = (String)parameters.get("RegisterNumber");
				studentName = (String)parameters.get("StudentName");
				dob = (String)parameters.get("DOB");
				college = (String)parameters.get("College");
				gender = (String)parameters.get("Gender");
				degree = (String)parameters.get("Degree");
				stream = (String)parameters.get("Stream");
				yearofpassing = (String)parameters.get("YearOfPassing");
				cgpa = (String)parameters.get("CGPA");
				email = (String)parameters.get("Email");
				Mobile = (String)parameters.get("Mobile");
				LanguageSelected = (String)parameters.get("LanguageSelected");
			}
				//Handling Gender to send bit to DB
				String Gender = "";
				if(gender == "Male") {
					Gender = "b'1'";
				}
				else {
					Gender = "b'0'";
				}
				
				//Handling date format according to DB
				DateFormat formatter = new SimpleDateFormat("MM-DD-yyyy"); 
				Date date = (Date)formatter.parse(dob);
				SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateOfBirth = newFormat.format(date).toString();

				//Query string to insert registration details

				String updateSQL="INSERT INTO evertz_interview_app.candidate_details ( `REG_NO`, `NAME`, `DOB`, `GENDER`, `EMAIL`, `MOB_NO`, `COLLEGE_DETAILS_ID`, `DEGREE_ID`, `BRANCH_ID`, `GRAD_YEAR`, `CGPA`) VALUES ( '" + registerNumber + "', '" + studentName+ "', '" + dateOfBirth + "', " + Gender + ", '"+ email + "', '"+ Mobile + "', '1', '1', '1', '"+ yearofpassing +"', '"+cgpa +"');";
				int res = DAOLayer.updateData(updateSQL);
				
				if(res == -1) {
					result = registeration_status("Failed+"+registerNumber);
				}
				else {
				result = registeration_status("Success+"+registerNumber);
				}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = registeration_status("Failed+"+registerNumber);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static String registeration_status(String status) {
		String str = status;

		String finalJSON;
		String[] temp;
		String delimiter = "\\+";

		temp = str.split(delimiter);   
		String reg_status  = temp[0];   
		String reg_id = temp[1];
		
	    if (reg_status.equals("Success")){
	    	
	    	JSONObject parameterList= new JSONObject();
	    	parameterList.put("RegisterNumber", reg_id);
	    	parameterList.put("Registration", "Success");
	    	parameterList.put("Reason","Student registration successful");
	    	
	    	JSONObject evertzinterviewapp= new JSONObject();
	    	evertzinterviewapp.put("Subsystem", "StudentRegistration"); 
	    	
	    	evertzinterviewapp.put("Command","Status");
	    	evertzinterviewapp.put("ParameterList", parameterList);
	    	
	    	JSONObject jsonFinal = new JSONObject(); 
	    	jsonFinal.put("EvertzInterviewApp",evertzinterviewapp);

	    	JSONArray jsonoutput = new JSONArray();
	    	jsonoutput.add(jsonFinal);
        
	    	finalJSON = jsonoutput.toString(); 
	    	
	    	return finalJSON;
		}
		else {
			JSONObject parameterList= new JSONObject();
	    	parameterList.put("RegisterNumber", reg_id);
	    	parameterList.put("Registration", "Failed");
	    	parameterList.put("Reason","Register number already exists.Please contact the supervisor!");
	    	
	    	JSONObject evertzinterviewapp= new JSONObject();
	    	evertzinterviewapp.put("Subsystem", "StudentRegistration"); 
	    	
	    	evertzinterviewapp.put("Command","Status");
	    	evertzinterviewapp.put("ParameterList", parameterList);
	    	
	    	JSONObject jsonFinal = new JSONObject(); 
	    	jsonFinal.put("EvertzInterviewApp",evertzinterviewapp);
	    	
	    	JSONArray jsonOutput = new JSONArray();
	    	jsonOutput.add(jsonFinal);
	    	
	    	finalJSON = jsonOutput.toString(); 
	    	
	    	return finalJSON;
		}
	}
}
