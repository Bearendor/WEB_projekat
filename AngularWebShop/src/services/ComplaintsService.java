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

import beans.Complaint;
import beans.Complaints;
import beans.Komentar;
import beans.Komentari;
import beans.Podforumi;

@Path("/zalbe")
public class ComplaintsService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustComplaints")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Complaint> getJustComplaints() {
		return getComplaints().getValues();
		
	}
	
	@POST
	@Path("/addComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addComplaint(Object o) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/zalbe.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		JSONObject jsonOut;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(o));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray sveZalbe = (JSONArray) ob;
			//sviKomentari.add(json);
			System.out.println("JSON Z: "+json);
			//dodavanje u kolekciju svih zalbi
			Complaint com = new Complaint();
			int maxId = getComplaints().getMaxID();
			maxId++;
			
			com.setId(maxId);
			
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
    		Date date = new Date();
    		String timeStamp = dateFormat.format(date);
			com.setDatumZalbe(timeStamp);
			com.setEntitetZalbe(json.get("entitetZalbe").toString());
			com.setIdEntiteta(json.get("idEntiteta").toString());
			com.setPodnosilacZalbe(json.get("podnosilacZalbe").toString());
			com.setTekstZalbe(json.get("tekstZalbe").toString());
			com.setZalPodforum(json.get("zalPodforum").toString());
			
			getComplaints().addComplaintToList(com);
			
			jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(com));
			sveZalbe.add(jsonOut);
			/////////////////////////////////////////
			
			//pisanje u fajl novog komentara
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			Iterator i = sveZalbe.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jskom = (JSONObject) i.next();
					out.println(jskom.toJSONString());
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
	@Path("/deleteComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteComplaint(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/zalbe.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));			
			String zID = json.get("id").toString();
			getComplaints().deleteComplaint(zID);
			Complaints ks = getComplaints();
			
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);		    

			Iterator i = ks.getValues().iterator();
			
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
	
	
	
	private Complaints getComplaints() {
		Complaints k = (Complaints) ctx.getAttribute("zalbe");
		if (k == null) {
			k = new Complaints(ctx.getRealPath(""));
			ctx.setAttribute("zalbe", k);
		} 
		return k;
	}
}
