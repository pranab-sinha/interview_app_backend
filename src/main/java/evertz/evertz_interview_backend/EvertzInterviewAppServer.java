package evertz.evertz_interview_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.json.simple.parser.ParseException;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class EvertzInterviewAppServer {
	
	public static String message;
	
	public static void main(String args[]) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		HttpContext context = server.createContext("/api");
		context.setHandler(EvertzInterviewAppServer::handleRequest);
		server.start();
	}
	
	 private static void handleRequest(HttpExchange exchange) throws IOException {
		 
		 exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		 exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		 exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Origin, Authorization, Accept, Client-Security-Token, Accept-Encoding, X-Auth-Token, content-type, Authentication");
		 
		 printRequestInfo(exchange);
		 String response = message;
		 exchange.sendResponseHeaders(200, response.getBytes().length);
		 OutputStream os = exchange.getResponseBody();
		 os.write(response.getBytes());
		 os.close();
	 }

	private static void printRequestInfo(HttpExchange exchange) throws IOException {
		InputStreamReader isr;
		isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
		BufferedReader br = new BufferedReader(isr);
		String value = br.readLine();
		System.out.println("Request Body");
		System.out.println("------------");
		String responseString = "";
		try {
			responseString = JSONMessage.JSONParse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Response string - " + responseString);
		message = responseString;
	}
}
