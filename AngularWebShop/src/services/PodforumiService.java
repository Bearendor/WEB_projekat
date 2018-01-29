package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import beans.Podforum;
import beans.Podforumi;
import beans.User;


@Path("/podforumi")
public class PodforumiService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustPodforumi")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Podforum> getJustPodforumi() {
		return getPodforumi().getValues();
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/dodajPodforum")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String dodajPodforum(Object o) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/podforumi.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(o));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray sviPodforumi = (JSONArray) ob;
			sviPodforumi.add(json);
			
			//dodavanje u kolekciju svih podforuma
			Podforum pf = new Podforum();
        	pf.setNaziv(json.get("naziv").toString());
        	pf.setOpis(json.get("opis").toString());
        	pf.setIkonica(json.get("ikonica").toString());
        	pf.setOdgovorniModerator(json.get("odgovorniModerator").toString());
        	
        	JSONArray pravila = (JSONArray) json.get("spisakPravila");
        	Iterator p = pravila.iterator();
        	ArrayList<String> pom = new ArrayList<String>();
        	while(p.hasNext()) {
        		pom.add(p.next().toString());
        	}
        	pf.setSpisakPravila(pom);
        	
        	JSONArray moderatori = (JSONArray) json.get("spisakModeratora");
        	Iterator q = moderatori.iterator();
        	ArrayList<String> pomMod = new ArrayList<String>();
        	while(q.hasNext()) {
        		pomMod.add(q.next().toString());
        	}
			pf.setSpisakModeratora(pomMod);
			
			getPodforumi().dodajPodforumUListu(pf);
			/////////////////////////////////////////
			
			//pisanje u fajl novog podforuma
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			Iterator i = sviPodforumi.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jspf = (JSONObject) i.next();
					out.println(jspf.toJSONString());
					if(i.hasNext())
						out.println(",");
					else
						out.println("");
				
			}
			out.println("]");
			
			out.close();
			
			System.out.println("OBJECT: "+json);
			System.out.println("SVI: "+sviPodforumi);
		} catch (ParseException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Podforumi alpf = getPodforumi();
		
		
		return "OK";
		
	}
	
	@POST
	@Path("/obrisiPodforum")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String obrisiPodforum(String naz) {
		getPodforumi().izbrisiPodforum(naz);
		Podforumi pfs = getPodforumi();
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/podforumi.txt");

		JSONParser parser = new JSONParser();
		try {
			//pisanje u fajl novog podforuma
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);		    
		    
		    
			Iterator i = pfs.getValues().iterator();
			
			out.println("[");
			while(i.hasNext()) {
				JSONObject json = (JSONObject) parser.parse(mapper.writeValueAsString(i.next()));
					out.println(json.toJSONString());
					if(i.hasNext())
						out.println(",");
					else
						out.println("");
				
			}
			out.println("]");
			
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "OK";
	}
	
	
	private Podforumi getPodforumi() {
		Podforumi pod = (Podforumi) ctx.getAttribute("podforumi");
		if (pod == null) {
			pod = new Podforumi(ctx.getRealPath(""));
			ctx.setAttribute("podforumi", pod);
		} 
		return pod;
	}
}
