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

				String website = (String) studentObject.get("Subsystem");   
				System.out.println(website);
				String firstName = (String) studentObject.get("Command");   
				System.out.println(firstName);
				JSONArray parameterList = (JSONArray) studentObject.get("ParameterList");
				JSONObject job = (JSONObject) parameterList.get(0);
				System.out.println("New ab - " +job);
		
				String registerNumber = (String)job.get("RegisterNumber");
				String studentName = (String)job.get("StudentName");
				String dob = (String)job.get("DOB");
				String college = (String)job.get("College");
				String gender = (String)job.get("Gender");
				String degree = (String)job.get("Degree");
				String stream = (String)job.get("Stream");
				String yearofpassing = (String)job.get("YearOfPassing");
				String cgpa = (String)job.get("CGPA");
				String email = (String)job.get("Email");
				String Mobile = (String)job.get("Mobile");
				String LanguageSelected = (String)job.get("LanguageSelected");
				System.out.println("Testing ---");
				String Gender = "";
				if(gender == "Male") {
					Gender = "b'1'";
				}
				else {
					Gender = "b'0'";
				}
				System.out.println(Gender);
				
				DateFormat formatter = new SimpleDateFormat("MM-DD-yyyy"); 
				Date date = (Date)formatter.parse(dob);
				SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateOfBirth = newFormat.format(date).toString();
				
				String updateSQL="INSERT INTO evertz_interview_app.candidate_details ( `REG_NO`, `NAME`, `DOB`, `GENDER`, `EMAIL`, `MOB_NO`, `COLLEGE_DETAILS_ID`, `DEGREE_ID`, `BRANCH_ID`, `GRAD_YEAR`, `CGPA`) VALUES ( '" + registerNumber + "', '" + studentName+ "', '" + dateOfBirth + "', " + Gender + ", '"+ email + "', '"+ Mobile + "', '1', '1', '1', '"+ yearofpassing +"', '"+cgpa +"');";


				System.out.println(updateSQL);
				DAOLayer.updateData(updateSQL);




			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{\r\n" + 
				"	\"EvertzInterviewApp\" : {\r\n" + 
				"		\"Subsystem\" : \"StudentRegistration\",\r\n" + 
				"		\"Command\" : \"Status\",\r\n" + 
				"		\"Registartion\" : \"Success\"\r\n" + 
				"	}\r\n" + 
				"}";	
	}
}