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

public class Komentari {
	private HashMap<String, Komentar> komentari = new HashMap<String, Komentar>();
	private ArrayList<Komentar> listaKomentara = new ArrayList<Komentar>();
	
	public Komentari() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Komentari(String path) {		
		BufferedReader in = null;
		try {
			File file = new File(path + "/komentari.txt");
			System.out.println("OVO MI JE PUTANJA: "+file.getCanonicalPath());
			citajKomentare(file);
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
	
	public void citajKomentare(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray sviKomentari = (JSONArray) obj;
			
			Iterator i = sviKomentari.iterator();
            while(i.hasNext()) {
            	JSONObject jst = (JSONObject) i.next();
            	Komentar kom = new Komentar();
            	kom.setId(Integer.parseInt(jst.get("id").toString()));
            	kom.setPripadaTemi(jst.get("pripadaTemi").toString());
            	kom.setAutor(jst.get("autor").toString());
            	kom.setDatumKomentara(jst.get("datumKomentara").toString());
            	kom.setRoditeljskiKomentar(jst.get("roditeljskiKomentar").toString());
            	kom.setTekstKomentara(jst.get("tekstKomentara").toString());
            	kom.setMenjan(jst.get("menjan").toString());
            	
            	JSONArray pozitivni = (JSONArray) jst.get("pozitivniGlasovi");
            	Iterator p = pozitivni.iterator();
            	ArrayList<String> pom = new ArrayList<String>();
            	while(p.hasNext()) {
            		pom.add(p.next().toString());
            	}
            	kom.setPozitivniGlasovi(pom);
            	
            	JSONArray negativni = (JSONArray) jst.get("negativniGlasovi");
            	Iterator pn = negativni.iterator();
            	ArrayList<String> pomN = new ArrayList<String>();
            	while(pn.hasNext()) {
            		pomN.add(pn.next().toString());
            	}
            	kom.setNegativniGlasovi(pomN);
            	
            	JSONArray podk = (JSONArray) jst.get("podkomentari");
            	Iterator pk = podk.iterator();
            	ArrayList<String> pomPK = new ArrayList<String>();
            	while(pk.hasNext()) {
            		pomPK.add(pk.next().toString());
            	}
            	kom.setPodkomentari(pomPK);
            	
            	komentari.put(Integer.toString(kom.getId()), kom);
            	listaKomentara.add(kom);
            	
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public int getMaxID() {
		int maxID = 0;
		for(Komentar k : listaKomentara) {
			if(k.getId() > maxID)
				maxID = k.getId();
		}
		return maxID;
	}
	
	public void changeComment(Komentar comm) {
		komentari.put(Integer.toString(comm.getId()), comm);
		int index= 0;
		for(Komentar k : listaKomentara) {
			if(Integer.toString(k.getId()).trim().equals(Integer.toString(comm.getId()).trim()))
				index = listaKomentara.indexOf(k);
		}
		listaKomentara.set(index, comm);
	}
	
	public void izbrisiKomentar(String k) {
		komentari.remove(k);
		Iterator<Komentar> iter = listaKomentara.iterator();
		while(iter.hasNext()){
			Komentar com = iter.next();
			if(Integer.toString(com.getId()).equals(k.trim()))
				iter.remove();
		}
	}
	
	public void addCommToList(Komentar k) {
		komentari.put(Integer.toString(k.getId()), k);
		listaKomentara.add(k);
	}

	public Collection<Komentar> getValues() {
		// TODO Auto-generated method stub
		return komentari.values();
	}
}
