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

import beans.Komentar;
import beans.Komentari;
import beans.Message;
import beans.Messages;

@Path("/poruke")
public class MessagesService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Message> getJustMessages() {
		return getMessages().getValues();
		
	}
	
	@POST
	@Path("/addMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessage(Object m) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/poruke.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		JSONObject jsonOut;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(m));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray svePoruke = (JSONArray) ob;
			//dodavanje u kolekciju svih poruka
			Message msg = new Message();
			int maxId = getMessages().getMaxID();
			maxId++;
			
			msg.setID(Integer.toString(maxId));
			msg.setNaslov(json.get("naslov").toString());
			msg.setPosiljalac(json.get("posiljalac").toString());
			msg.setPrimalac(json.get("primalac").toString());
			msg.setProcitana(json.get("procitana").toString());
			msg.setSadrzaj(json.get("sadrzaj").toString());
			
			System.out.println(msg);
			
			jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(msg));
			svePoruke.add(jsonOut);

			//pisanje u fajl novog komentara
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			Iterator i = svePoruke.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsmsg = (JSONObject) i.next();
					out.println(jsmsg.toJSONString());
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
	@Path("/changeMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String edit(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/poruke.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			System.out.println("JSON "+json);
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allMessages = (JSONArray) ob;
			//pisanje u fajl 
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    
			Iterator i = allMessages.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
				System.out.println("JSUS "+jsus);
				if(json.get("id").toString().equals(jsus.get("id").toString())) {
					System.out.println("Usao sam u poruku");
					jsus.put("procitana", "Y");
					//Message changedOne = JsonToMessage(jsus);
					//getMessages();
					//getMessages().changeMessage(changedOne);
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
	
	
	private Message JsonToMessage(JSONObject json) {
		Message mess = new Message();
		
		mess.setID(json.get("id").toString());
		mess.setNaslov(json.get("naslov").toString());
		mess.setPosiljalac(json.get("posiljalac").toString());
		mess.setPrimalac(json.get("primalac").toString());
		mess.setProcitana(json.get("procitana").toString());
		mess.setSadrzaj(json.get("sadrzaj").toString());
		
		return mess;
	}
	
	private Messages getMessages() {
		//Messages m = (Messages) ctx.getAttribute("poruke");
		Messages m = null;
		//System.out.println("Messages: "+m.getValues().toString());
		if (m == null) {
			m = new Messages(ctx.getRealPath(""));
			ctx.setAttribute("poruke", m);
		} 
		return m;
	}
	
}
