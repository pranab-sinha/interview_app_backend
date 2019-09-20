package evertz.evertz_interview_backend.application_server;

/*
    Author: 		Shreelakshmi Bhatt
    Created Date: 	10th September 2019
    Modified Date: 	19th September 2019
    Description: 	This class would just get the Request from the ApplicationServer class and pass it on to the right Api Call classes 

*/

import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import evertz.evertz_interview_backend.application_api_calls.Candidate;
import evertz.evertz_interview_backend.application_api_calls.UI;
import evertz.evertz_interview_backend.application_api_calls.Validate;

public class DispatchToSubsystem {

    public static String JSONParse(String JSONRequestMessage) throws org.json.simple.parser.ParseException {

        //JSON parser object to parse read the response
        String response = "";
        if (JSONRequestMessage == null) {

            JSONObject errorMessage = new JSONObject();
            errorMessage.put("Error", "Null request Received.");

            JSONObject jsonFinal = new JSONObject();
            jsonFinal.put("EvertzInterviewApp", errorMessage);

            JSONArray jsonoutput = new JSONArray();
            jsonoutput.add(jsonFinal);

            response = jsonoutput.toString();

            return response;
        } else {

            try {

                JSONParser jsonParser = new JSONParser();
                String concatenatedJSONRequestMessage = "[" + JSONRequestMessage + "]";

                System.out.println("JSON request message received : " + concatenatedJSONRequestMessage);

                Object jsonRequestMessageObject = jsonParser.parse(concatenatedJSONRequestMessage);
                JSONArray jsonRequestMessageArray = (JSONArray) jsonRequestMessageObject;

                Iterator iterator = jsonRequestMessageArray.iterator();

                try {

                    while (iterator.hasNext()) {

                        JSONObject JSONObjectIterator = (JSONObject) iterator.next();
                        JSONObject evertzInterviewApp = (JSONObject) JSONObjectIterator.get("EvertzInterviewApp");

                        String subsystemName = (String) evertzInterviewApp.get("Subsystem");
                        String commandName = (String) evertzInterviewApp.get("Command");
                        JSONArray parameterList = (JSONArray) evertzInterviewApp.get("ParameterList");
                        System.out.println(parameterList);

                        if ((subsystemName.equals("Server")) && (commandName.equals("ValidateServer"))) {

                            Validate validate = new Validate(parameterList);
                            response = validate.serverResponseJSON();
                        }

                        if ((subsystemName.equals("StudentRegistration")) && (commandName.equals("Save"))) {

                            Candidate candidate = new Candidate(parameterList);
                            response = candidate.register();
                        }

                        if ((subsystemName.equals("UI")) && (commandName.equals("Get"))) {

                            UI ui = new UI(parameterList);
                            response = ui.getJsonList(parameterList);
                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

                response = response.substring(1, response.length() - 1);
                return response;

            } catch (Exception e) {
                e.printStackTrace();

                JSONObject errorMessage = new JSONObject();
                errorMessage.put("Error", "Null request Received.");

                JSONObject jsonFinal = new JSONObject();
                jsonFinal.put("EvertzInterviewApp", errorMessage);

                JSONArray jsonoutput = new JSONArray();
                jsonoutput.add(jsonFinal);

                response = jsonoutput.toString();

                return response;
            }
        }
    }
}