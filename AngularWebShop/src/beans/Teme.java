package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Teme {
	private HashMap<String, Tema> teme = new HashMap<String, Tema>();
	private ArrayList<Tema> listaTema = new ArrayList<Tema>();
	
	
	public Teme() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Teme(String path) {
		
		BufferedReader in = null;
		try {
			File file = new File(path + "/teme.txt");
			System.out.println("OVO MI JE PUTANJA: "+file.getCanonicalPath());
			citajTeme(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
		
		
	}
	
	
	public void citajTeme(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray sveTeme = (JSONArray) obj;
			
			Iterator i = sveTeme.iterator();
            while(i.hasNext()) {
            	JSONObject jst = (JSONObject) i.next();
            	Tema tema = new Tema();
            	tema.setPodforum(jst.get("podforum").toString());
            	tema.setNaslov(jst.get("naslov").toString());
            	tema.setTip(jst.get("tip").toString());
            	tema.setAutor(jst.get("autor").toString());
            	tema.setSadrzaj(jst.get("sadrzaj").toString());
            	tema.setDatumKreiranja(jst.get("datumKreiranja").toString());
            	
            	JSONArray pozitivni = (JSONArray) jst.get("pozitivniGlasovi");
            	Iterator p = pozitivni.iterator();
            	ArrayList<String> pom = new ArrayList<String>();
            	while(p.hasNext()) {
            		pom.add(p.next().toString());
            	}
            	tema.setPozitivniGlasovi(pom);
            	
            	JSONArray negativni = (JSONArray) jst.get("negativniGlasovi");
            	Iterator pn = negativni.iterator();
            	ArrayList<String> pomN = new ArrayList<String>();
            	while(pn.hasNext()) {
            		pomN.add(pn.next().toString());
            	}
            	tema.setNegativniGlasovi(pomN);
            	teme.put(tema.getNaslov()+tema.getPodforum(), tema);
            	listaTema.add(tema);
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public void addThemeToList(Tema tema) {
		teme.put(tema.getNaslov()+tema.getPodforum(), tema);
		listaTema.add(tema);
		System.out.print(teme);
	}
	
	public void deleteTheme(String k) {
		teme.remove(k);
		Iterator<Tema> iter = listaTema.iterator();
		while(iter.hasNext()){
			Tema pf = iter.next();
			if((pf.getNaslov()+pf.getPodforum()).equals(k.trim()))
				iter.remove();
		}
	}
	
	public void changeTheme(Tema tema) {
		teme.put(tema.getNaslov()+tema.getPodforum(), tema);
		int index= -1;
		for(Tema t : listaTema) {
			if(t.getNaslov().trim().equals(tema.getNaslov().trim()))
				index = listaTema.indexOf(t);
		}
		listaTema.set(index, tema);
	}
	
	public Collection<Tema> getValues() {
		return teme.values();
	}
	
}
