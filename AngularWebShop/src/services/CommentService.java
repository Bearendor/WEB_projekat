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
import beans.Tema;

@Path("/komentari")
public class CommentService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getJustComments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Komentar> getJustKomentar() {
		return getComments().getValues();
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/addComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addComment(Object o) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/komentari.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;
		JSONObject jsonOut;
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(o));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray sviKomentari = (JSONArray) ob;
			//sviKomentari.add(json);
			System.out.println(json);
			//dodavanje u kolekciju svih podforuma
			Komentar kom = new Komentar();
			int maxId = getComments().getMaxID();
			maxId++;
			
			kom.setAutor(json.get("autor").toString());
			kom.setId(maxId);
			
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
    		Date date = new Date();
    		String timeStamp = dateFormat.format(date);
			kom.setDatumKomentara(timeStamp);
			kom.setMenjan("N");
			ArrayList<String> negGlas = new ArrayList<String>();
			kom.setNegativniGlasovi(negGlas);
			ArrayList<String> pozGlas = new ArrayList<String>();
			kom.setPozitivniGlasovi(pozGlas);
			ArrayList<String> pod = new ArrayList<String>();
			kom.setPodkomentari(pod);
			System.out.println("Tema: "+json.get("pripadaTemi").toString());
			kom.setPripadaTemi(json.get("pripadaTemi").toString());
			kom.setRoditeljskiKomentar("");
			kom.setTekstKomentara(json.get("tekstKomentara").toString());
			System.out.println("Novi komentar: "+kom);
			getComments().addCommToList(kom);
			
			jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(kom));
			sviKomentari.add(jsonOut);
			/////////////////////////////////////////
			
			//pisanje u fajl novog komentara
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			Iterator i = sviKomentari.iterator();
			
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
			
			System.out.println("OBJECT: "+json);
			System.out.println("SVI: "+sviKomentari);
		} catch (ParseException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "OK";
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/replyComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String reply(Object data) {
		
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/komentari.txt");

		JSONParser parser = new JSONParser();
		JSONObject jsonData;
		JSONObject jsonParent;
		JSONObject jsonChild;
		JSONObject jsonOut;
		try {
			jsonData = (JSONObject) parser.parse(mapper.writeValueAsString(data));
			jsonParent =  (JSONObject) jsonData.get("parent");
			jsonChild = (JSONObject) jsonData.get("child");
			System.out.println("ALL DATA: "+ jsonData);
			System.out.println("PARENT: "+ jsonParent);
			System.out.println("CHILD: "+ jsonChild);
			
			Object ob = parser.parse(new FileReader(fil));
			JSONArray sviKomentari = (JSONArray) ob;
			System.out.println(jsonParent);
			//dodavanje u kolekciju svih komentara
			Komentar kom = new Komentar();
			int maxId = getComments().getMaxID();
			maxId++;
			
			kom.setAutor(jsonChild.get("autor").toString());
			kom.setId(maxId);
			
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
    		Date date = new Date();
    		String timeStamp = dateFormat.format(date);
			kom.setDatumKomentara(timeStamp);
			kom.setMenjan("N");
			ArrayList<String> negGlas = new ArrayList<String>();
			kom.setNegativniGlasovi(negGlas);
			ArrayList<String> pozGlas = new ArrayList<String>();
			kom.setPozitivniGlasovi(pozGlas);
			ArrayList<String> pod = new ArrayList<String>();
			kom.setPodkomentari(pod);
			System.out.println("Tema: "+jsonParent.get("pripadaTemi").toString());
			kom.setPripadaTemi(jsonParent.get("pripadaTemi").toString());
			kom.setRoditeljskiKomentar(jsonParent.get("id").toString());
			kom.setTekstKomentara(jsonChild.get("tekstKomentara").toString());
			System.out.println("Novi komentar: "+kom);
			getComments().addCommToList(kom);
			
			jsonOut = (JSONObject) parser.parse(mapper.writeValueAsString(kom));
			sviKomentari.add(jsonOut);
			/////////////////////////////////////////
			
			//pisanje u fajl novog komentara
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
			
			Iterator i = sviKomentari.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jskom = (JSONObject) i.next();
				
				if(jsonParent.get("id").toString().trim().equals(jskom.get("id").toString().trim())) {
					System.out.println("Usao sam");
					//jskom.put("tekstKomentara", json.get("tekstKomentara").toString());
					JSONArray test = (JSONArray) jskom.get("podkomentari");
					test.add(jsonOut.get("id").toString());
					System.out.println("PODKOMENTARI: "+test);
					
					jskom.put("podkomentari", test);
					System.out.print("JSKOM: "+jskom);
					
					Komentar changedOne = JsonToComment(jskom);
					getComments().changeComment(changedOne);
				} 
				
				out.println(jskom.toJSONString());
				if(i.hasNext())
					out.println(",");
				else
					out.println("");
				
			}
			out.println("]");
			
			out.close();
			
			System.out.println("OBJECT: "+jsonChild);
			System.out.println("SVI: "+sviKomentari);
			
			
		} catch (ParseException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "OK";
		
	}
	
	@POST
	@Path("/deleteComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteComment(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/komentari.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allComments = (JSONArray) ob;
			
			if(json.get("roditeljskiKomentar").toString().isEmpty()) {
				JSONArray pod = (JSONArray) json.get("podkomentari");
		    	Iterator p = pod.iterator();
		    	while(p.hasNext()) {
		    		getComments().izbrisiKomentar(p.next().toString());
		    	}
				String commID = json.get("id").toString();
				getComments().izbrisiKomentar(commID);
				
				Komentari ks = getComments();
				
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
			} else {
				String parentID = json.get("roditeljskiKomentar").toString();
				System.out.println("Parent ID: "+parentID);
				getComments().izbrisiKomentar(json.get("id").toString());
				
				Komentari ks = getComments();
				Iterator i = ks.getValues().iterator();
				while(i.hasNext()) {
					Komentar k = (Komentar) i.next();
					
					if(Integer.toString(k.getId()).trim().equals(parentID.trim())) {
						int index = k.getPodkomentari().indexOf(json.get("id").toString());
						k.getPodkomentari().remove(index);
						
						System.out.println("Found ID: "+Integer.toString(k.getId()));
						getComments().changeComment(k);
					}
				}
				
				Komentari ks1 = getComments();
				
				FileWriter fw = new FileWriter(fil);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw);		    
			    
			    
				Iterator i1 = ks1.getValues().iterator();
				
				out.println("[");
				while(i1.hasNext()) {
					JSONObject jay = (JSONObject) parser.parse(mapper.writeValueAsString(i1.next()));
						out.println(jay.toJSONString());
						if(i1.hasNext())
							out.println(",");
						else
							out.println("");
					
				}
				out.println("]");
				
				out.close();
			}
			
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
			
		
		return "OK";
	}
	
	
	@POST
	@Path("/editComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String edit(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/komentari.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allComments = (JSONArray) ob;
			//pisanje u fajl 
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    
			Iterator i = allComments.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
				
				if(json.get("id").toString().trim().equals(jsus.get("id").toString().trim())) {
					System.out.println("Usao sam");
					jsus.put("tekstKomentara", json.get("tekstKomentara").toString());
					jsus.put("menjan", "Y");
					Komentar changedOne = JsonToComment(jsus);
					getComments().changeComment(changedOne);
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
	@Path("/promeniGlas")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String promeniGlas(Object u) {
		ObjectMapper mapper = new ObjectMapper();

		String f = ctx.getRealPath("");
		File fil = new File(f+"/komentari.txt");

		JSONParser parser = new JSONParser();
		JSONObject json;	
		try {
			json = (JSONObject) parser.parse(mapper.writeValueAsString(u));
			Object ob = parser.parse(new FileReader(fil));
			JSONArray allComments = (JSONArray) ob;
			//pisanje u fajl 
			FileWriter fw = new FileWriter(fil);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw);
		    
			Iterator i = allComments.iterator();
			
			out.println("[");
			while(i.hasNext()) {
				
				JSONObject jsus = (JSONObject) i.next();
				String compare1 = json.get("id").toString().trim();
				String compare2 = jsus.get("id").toString().trim();
				
				if(compare1.equals(compare2)) {
					System.out.println("Usao sam u komentar");					
					jsus.putAll(json);
					Komentar changedOne = JsonToComment(jsus);
					getComments().changeComment(changedOne);
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
	
	
	
	private Komentar JsonToComment(JSONObject json) {
		Komentar comm = new Komentar();
		
		comm.setId(Integer.parseInt(json.get("id").toString()));
		comm.setAutor(json.get("autor").toString());
		comm.setTekstKomentara(json.get("tekstKomentara").toString());
		comm.setDatumKomentara(json.get("datumKomentara").toString());
		comm.setMenjan(json.get("menjan").toString());
		comm.setRoditeljskiKomentar(json.get("roditeljskiKomentar").toString());
		comm.setPripadaTemi(json.get("pripadaTemi").toString());

		JSONArray pod = (JSONArray) json.get("podkomentari");
    	Iterator p = pod.iterator();
    	ArrayList<String> pomPOD = new ArrayList<String>();
    	while(p.hasNext()) {
    		pomPOD.add(p.next().toString());
    	}
    	comm.setPodkomentari(pomPOD);
    	
    	JSONArray pg = (JSONArray) json.get("pozitivniGlasovi");
    	Iterator ppt = pg.iterator();
    	ArrayList<String> pomPG = new ArrayList<String>();
    	while(ppt.hasNext()) {
    		pomPG.add(ppt.next().toString());
    	}
    	comm.setPozitivniGlasovi(pomPG);
    	
    	JSONArray ng = (JSONArray) json.get("negativniGlasovi");
    	Iterator ppc = ng.iterator();
    	ArrayList<String> pomNG = new ArrayList<String>();
    	while(ppc.hasNext()) {
    		pomNG.add(ppc.next().toString());
    	}
    	comm.setNegativniGlasovi(pomNG);
		
		return comm;
	}
	
	
	private Komentari getComments() {
		Komentari k = (Komentari) ctx.getAttribute("komentari");
		if (k == null) {
			k = new Komentari(ctx.getRealPath(""));
			ctx.setAttribute("komentari", k);
		} 
		return k;
	}
}
