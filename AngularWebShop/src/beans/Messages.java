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

public class Messages {
	private HashMap<String, Message> poruke = new HashMap<String, Message>();
	private ArrayList<Message> listaPoruka = new ArrayList<Message>();
	
	public Messages() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Messages(String path) {		
		BufferedReader in = null;
		try {
			System.out.println("OVDE TREBA DA UDJE");
			File file = new File(path + "/poruke.txt");
			System.out.println("OVO MI JE PUTANJA: "+file.getCanonicalPath());
			citajPoruke(file);
		} catch (Exception e) {
			System.out.println("OVDE JE GRESKA");
			e.printStackTrace();
		}
		finally {
			System.out.println("I OVDE JE GRESKA");
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}	
	}
	
	public void citajPoruke(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray svePoruke = (JSONArray) obj;

			Iterator i = svePoruke.iterator();
            while(i.hasNext()) {
            	JSONObject jst = (JSONObject) i.next();
            	Message msg = new Message();
            	msg.setID(jst.get("id").toString());
            	msg.setPosiljalac(jst.get("posiljalac").toString());
            	msg.setPrimalac(jst.get("primalac").toString());
            	msg.setNaslov(jst.get("naslov").toString());
            	msg.setSadrzaj(jst.get("sadrzaj").toString());
            	msg.setProcitana(jst.get("procitana").toString());
            	
            	
            	poruke.put(msg.getID(), msg);
            	listaPoruka.add(msg);   
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public Integer getMaxID() {
		int maxID = 0;
		for(Message m : listaPoruka) {
			if(Integer.parseInt(m.getID()) > maxID)
				maxID = Integer.parseInt(m.getID());
		}
		return maxID;
	}
	
	public void changeMessage(Message msg) {
		poruke.put(msg.getID(), msg);
		
		int index= 0;
		for(Message m : listaPoruka) {
			if(m.getID().equals(msg.getID()))
				index = listaPoruka.indexOf(m);
		}
		listaPoruka.set(index, msg);
	}

	public Collection<Message> getValues() {
		// TODO Auto-generated method stub
		return poruke.values();
	}
}
