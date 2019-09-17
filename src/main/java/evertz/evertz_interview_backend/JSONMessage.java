package evertz.evertz_interview_backend;

import java.util.Iterator;
import java.sql.*;
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
				JSONObject parameterList = (JSONObject) studentObject.get("ParameterList");
				System.out.println(parameterList.toString());
				String registerNumber = (String)parameterList.get("RegisterNumber");
				String studentName = (String)parameterList.get("StudentName");
				String dob = (String)parameterList.get("DOB");
				String college = (String)parameterList.get("College");
				String gender = (String)parameterList.get("Gender");
				String degree = (String)parameterList.get("Degree");
				String stream = (String)parameterList.get("Stream");
				String yearofpassing = (String)parameterList.get("YearOfPassing");
				String cgpa = (String)parameterList.get("CGPA");
				String email = (String)parameterList.get("Email");
				String Mobile = (String)parameterList.get("Mobile");
				String LanguageSelected = (String)parameterList.get("LanguageSelected");
				System.out.println("Testing ---");
				String Gender = "";
				if(gender == "Male") {
					Gender = "b'1'";
				}
				else {
					Gender = "b'0'";
				}
				System.out.println(Gender);
				String fgc = dob.toString();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dobStr = formatter.format(fgc);
				System.out.println(dobStr);
				//String updateSQL="INSERT INTO evertz_interview_app.candidate_details ( `REG_NO`, `NAME`, `DOB`, `GENDER`, `EMAIL`, `MOB_NO`, `COLLEGE_DETAILS_ID`, `DEGREE_ID`, `BRANCH_ID`, `GRAD_YEAR`, `CGPA`) VALUES ( '" + registerNumber + "', '" + studentName+ "', '" + dobStr + "', '" + Gender + "', '"+ email + "', '"+ Mobile + "', '1', '1', '1', '"+ yearofpassing +"', '"+cgpa +"');";


				//System.out.println(updateSQL);
				//DAOLayer.updateData(updateSQL);




			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "SUCCESSFULLY STORED IN DB";	
	}
}