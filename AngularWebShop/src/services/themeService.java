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

import beans.Complaints;
import beans.Podforumi;
import beans.Tema;
import beans.Teme;
import beans.User;

@Path("/teme")
public class themeService {
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustThemes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Tema> getJustThemes() {
		return getTeme().getValues();
		
	}
	
	@POST
	@Path("/promeniGlas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String promeniGlas(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/teme.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allThemes = (JSONArray) ob;
			//pisanje u fajl 
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    
			Iterator i = allThemes.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
				String compare1 = json.get("naslov").toString().trim()+json.get("podforum").toString().trim();
				String compare2 = jsus.get("naslov").toString().trim()+jsus.get("podforum").toString().trim();
				
				if(compare1.equals(compare2)) {
					System.out.println("Usao sam");
					/*
					if(json.get("role").toString().trim().equals("K")) {
						jsus.put("role", "M");
					} else {						
						jsus.put("role", "K");
					}
					*/
					
					jsus.putAll(json);
					Tema changedOne = JsonToTheme(jsus);
					getTeme().changeTheme(changedOne);
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
	
	@POST
	@Path("/createTheme")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createTheme(Object u) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/teme.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		JSONObject jsonOut;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allThemes = (JSONArray) ob;
			
			//dodavanje u kolekciju svih tema
			Tema tema = new Tema();
			tema.setAutor(json.get("autor").toString());
			tema.setNaslov(json.get("naslov").toString());
			tema.setPodforum(json.get("podforum").toString());
			tema.setSadrzaj(json.get("sadrzaj").toString());
			tema.setTip(json.get("tip").toString());
        	
        	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
    		Date date = new Date();
    		String timeStamp = dateFormat.format(date);
        	tema.setDatumKreiranja(timeStamp);        	

        	ArrayList<String> pom = new ArrayList<String>();
        	tema.setNegativniGlasovi(pom);

        	ArrayList<String> pomPT = new ArrayList<String>();
        	tema.setPozitivniGlasovi(pomPT);
			
        	getTeme().addThemeToList(tema);
			/////////////////////////////////////////
			
			//pisanje u fajl novog korisnika
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
		    jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(tema));
		    
		    allThemes.add(jsonOut);
		    
			Iterator i = allThemes.iterator();
			
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
	@Path("/deleteTheme")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteTheme(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/teme.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));			
			String tID = json.get("naslov").toString()+json.get("podforum");
			getTeme().deleteTheme(tID);
			Teme ts = getTeme();
			
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);		    

			Iterator i = ts.getValues().iterator();
			
			out.println("[");
			while(i.hasNext()) {
				JSONObject jay = (JSONObject) parser.parse(mapper.writeValueAsString(i.next()));
					out.println(jay.toJSONString());
					if(i.hasNext())
						out.println(",");
					else
						out.println("");
				
			}
			out.println("]");
			
			out.close();
		

			} catch(Exception e) {
				e.printStackTrace();
			}
			
		return "OK";
	}
	
	
	private Tema JsonToTheme(JSONObject json) {
		Tema the = new Tema();
		
		the.setNaslov(json.get("naslov").toString());
		the.setAutor(json.get("autor").toString());
		the.setTip(json.get("tip").toString());
		the.setPodforum(json.get("podforum").toString());
		the.setSadrzaj(json.get("sadrzaj").toString());
		the.setDatumKreiranja(json.get("datumKreiranja").toString());

		JSONArray pozGl = (JSONArray) json.get("pozitivniGlasovi");
    	Iterator p = pozGl.iterator();
    	ArrayList<String> pomPG = new ArrayList<String>();
    	while(p.hasNext()) {
    		pomPG.add(p.next().toString());
    	}
    	the.setPozitivniGlasovi(pomPG);
    	
    	JSONArray negGl = (JSONArray) json.get("negativniGlasovi");
    	Iterator ppt = negGl.iterator();
    	ArrayList<String> pomNG = new ArrayList<String>();
    	while(ppt.hasNext()) {
    		pomNG.add(ppt.next().toString());
    	}
    	the.setNegativniGlasovi(pomNG);
		
		return the;
	}
	
	private Teme getTeme() {
		Teme t = (Teme) ctx.getAttribute("teme");
		if (t == null) {
			t = new Teme(ctx.getRealPath(""));
			ctx.setAttribute("teme", t);
		} 
		return t;
	}
	
}
