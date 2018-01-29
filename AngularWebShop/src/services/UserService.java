package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.User;
import beans.Users;

@Path("/korisnici")
public class UserService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getJustUsers() {
		System.out.println("SVI PROCITANI "+"\n"+getUsers().getValues());
		return getUsers().getValues();
		
	}
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser(Object u) {
		
		System.out.println("OVOG REGISTRUJEMO: "+u);
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/users1.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		JSONObject jsonOut;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allUsers = (JSONArray) ob;
			
			//dodavanje u kolekciju svih podforuma
			User pf = new User();
        	pf.setUsername(json.get("username").toString());
        	pf.setPassword(json.get("password").toString());
        	pf.setFirstName(json.get("firstname").toString());
        	pf.setLastName(json.get("lastname").toString());
        	pf.setEmail(json.get("email").toString());
        	pf.setPhone(json.get("phone").toString());
        	pf.setRole("K");
        	
        	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
    		Date date = new Date();
    		String timeStamp = dateFormat.format(date);
        	        	
        	pf.setRegisterDate(timeStamp);

        	ArrayList<String> pom = new ArrayList<String>();
        	pf.setSubscribedForums(pom);

        	ArrayList<String> pomPT = new ArrayList<String>();
        	pf.setPostedTopics(pomPT);

        	ArrayList<String> pomPC = new ArrayList<String>();
        	pf.setPostedComments(pomPC);
			
        	getUsers().addUserToList(pf);
			/////////////////////////////////////////
			
			//pisanje u fajl novog korisnika
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
		    jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(pf));
		    
		    allUsers.add(jsonOut);
		    
			Iterator i = allUsers.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
					out.println(jsus.toJSONString());
					if(i.hasNext())
						out.println(",");
					else
						out.println("");
				
			}
			out.println("]");
			
			out.close();

		} catch (ParseException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "OK";
		
	}
	
	@POST
	@Path("/promeniUlogu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String promeniUlogu(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/users1.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allUsers = (JSONArray) ob;
			//pisanje u fajl 
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    
			Iterator i = allUsers.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
				
				if(json.get("username").toString().trim().equals(jsus.get("username").toString().trim())) {
					System.out.println("Usao sam");
					if(json.get("role").toString().trim().equals("K")) {
						jsus.put("role", "M");
					} else {						
						jsus.put("role", "K");
					}
					User changedOne = JsonToUser(jsus);
					getUsers().changeUser(changedOne);
				} 
				
				out.println(jsus.toJSONString());
				if(i.hasNext())
					out.println(",");
				else
					out.println("");
				
			}
			out.println("]");
			out.close();
			
			
			
		} catch (ParseException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return "OK";
	}
	
	private User JsonToUser(JSONObject json) {
		User user = new User();
		
		user.setUsername(json.get("username").toString());
		user.setPassword(json.get("password").toString());
		user.setFirstName(json.get("firstName").toString());
		user.setLastName(json.get("lastName").toString());
		user.setEmail(json.get("email").toString());
		user.setPhone(json.get("phone").toString());
		user.setRole(json.get("role").toString());
		user.setRegisterDate(json.get("registerDate").toString());

		JSONArray subFors = (JSONArray) json.get("subscribedForums");
    	Iterator p = subFors.iterator();
    	ArrayList<String> pomSF = new ArrayList<String>();
    	while(p.hasNext()) {
    		pomSF.add(p.next().toString());
    	}
    	user.setSubscribedForums(pomSF);
    	
    	JSONArray pTops = (JSONArray) json.get("postedTopics");
    	Iterator ppt = pTops.iterator();
    	ArrayList<String> pomPT = new ArrayList<String>();
    	while(ppt.hasNext()) {
    		pomPT.add(ppt.next().toString());
    	}
    	user.setSubscribedForums(pomPT);
    	
    	JSONArray pComs = (JSONArray) json.get("postedComments");
    	Iterator ppc = pComs.iterator();
    	ArrayList<String> pomPC = new ArrayList<String>();
    	while(ppc.hasNext()) {
    		pomPC.add(ppc.next().toString());
    	}
    	user.setSubscribedForums(pomPC);
		
		return user;
	}
	
	
	private Users getUsers() {
		Users users = (Users) ctx.getAttribute("users");
		if (users == null) {
			users = new Users(ctx.getRealPath(""));
			ctx.setAttribute("users", users);
		} 
		return users;
	}
	
}
