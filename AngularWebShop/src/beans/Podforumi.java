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


public class Podforumi {
	
	private HashMap<String, Podforum> podforumi = new HashMap<String, Podforum>();
	private ArrayList<Podforum> podforumLista = new ArrayList<Podforum>();
	
	
	public Podforumi() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Podforumi(String path) {
		
		BufferedReader in = null;
		try {
			File file = new File(path + "/podforumi.txt");
			System.out.println("OVO MI JE PUTANJA: "+file.getCanonicalPath());
			citajPodforume(file);
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
	
	
	public void citajPodforume(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray sviPodforumi = (JSONArray) obj;
			
			Iterator i = sviPodforumi.iterator();
            while(i.hasNext()) {
            	JSONObject jspf = (JSONObject) i.next();
            	Podforum pf = new Podforum();
            	pf.setNaziv(jspf.get("naziv").toString());
            	pf.setOpis(jspf.get("opis").toString());
            	pf.setIkonica(jspf.get("ikonica").toString());
            	pf.setOdgovorniModerator(jspf.get("odgovorniModerator").toString());
            	
            	JSONArray pravila = (JSONArray) jspf.get("spisakPravila");
            	Iterator p = pravila.iterator();
            	ArrayList<String> pom = new ArrayList<String>();
            	while(p.hasNext()) {
            		pom.add(p.next().toString());
            	}
            	pf.setSpisakPravila(pom);
            	
            	JSONArray moderatori = (JSONArray) jspf.get("spisakModeratora");
            	Iterator q = moderatori.iterator();
            	ArrayList<String> pomMod = new ArrayList<String>();
            	while(q.hasNext()) {
            		pomMod.add(q.next().toString());
            	}
            	pf.setSpisakModeratora(pomMod);
            	podforumi.put(pf.getNaziv(), pf);
            	podforumLista.add(pf);
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public void dodajPodforumUListu(Podforum p) {
		podforumi.put(p.getNaziv(), p);
		podforumLista.add(p);
	}
	
	public void izbrisiPodforum(String k) {
		podforumi.remove(k);
		Iterator<Podforum> iter = podforumLista.iterator();
		while(iter.hasNext()){
			Podforum pf = iter.next();
			if(pf.getNaziv().equals(k.trim()))
				iter.remove();
		}
	}
	
	public int size() {
		int i = podforumi.size();
		return i;
	}
	
	public Collection<Podforum> getValues() {
		return podforumi.values();
	}
	
}
