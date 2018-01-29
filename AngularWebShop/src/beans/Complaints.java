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

public class Complaints {
	private HashMap<String, Complaint> zalbe = new HashMap<String, Complaint>();
	private ArrayList<Complaint> listaZalbi = new ArrayList<Complaint>();
	
	public Complaints() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Complaints(String path) {		
		BufferedReader in = null;
		try {
			File file = new File(path + "/zalbe.txt");
			System.out.println("OVO MI JE PUTANJA: "+file.getCanonicalPath());
			citajZalbe(file);
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
	
	public void citajZalbe(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray sveZalbe = (JSONArray) obj;

			Iterator i = sveZalbe.iterator();
            while(i.hasNext()) {
            	JSONObject jst = (JSONObject) i.next();
            	Complaint com = new Complaint();
            	com.setId(Integer.parseInt(jst.get("id").toString()));
            	com.setDatumZalbe(jst.get("datumZalbe").toString());
            	com.setEntitetZalbe(jst.get("entitetZalbe").toString());
            	com.setIdEntiteta(jst.get("idEntiteta").toString());
            	com.setPodnosilacZalbe(jst.get("podnosilacZalbe").toString());
            	com.setTekstZalbe(jst.get("tekstZalbe").toString());
            	com.setZalPodforum(jst.get("zalPodforum").toString());
            	
            	zalbe.put(Integer.toString(com.getId()), com);
            	listaZalbi.add(com);   
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public int getMaxID() {
		int maxID = 0;
		for(Complaint k : listaZalbi) {
			if(k.getId() > maxID)
				maxID = k.getId();
		}
		return maxID;
	}
	
	public void deleteComplaint(String k) {
		zalbe.remove(k);
		Iterator<Complaint> iter = listaZalbi.iterator();
		while(iter.hasNext()){
			Complaint com = iter.next();
			if(Integer.toString(com.getId()).equals(k.trim()))
				iter.remove();
		}
	}
	
	public void addComplaintToList(Complaint k) {
		zalbe.put(Integer.toString(k.getId()), k);
		listaZalbi.add(k);
	}

	public Collection<Complaint> getValues() {
		// TODO Auto-generated method stub
		return zalbe.values();
	}
	
	
	
}
