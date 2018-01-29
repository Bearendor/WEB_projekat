package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Users {

	private HashMap<String, User> users = new HashMap<String, User>();
	private ArrayList<User> userList = new ArrayList<User>();
	
	public Users() {
		this("D:\\Nastava\\Web programiranje\\workspace\\WebShop\\WebContent");
	}
	
	public Users(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/users1.txt");
			in = new BufferedReader(new FileReader(file));
			readUsers(file);
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
	
	public void readUsers(File file) {
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONArray sviKorisnici = (JSONArray) obj;
			
			Iterator i = sviKorisnici.iterator();
            while(i.hasNext()) {
            	JSONObject u = (JSONObject) i.next();
            	User pf = new User();
            	pf.setUsername(u.get("username").toString());
            	pf.setPassword(u.get("password").toString());
            	pf.setFirstName(u.get("firstName").toString());
            	pf.setLastName(u.get("lastName").toString());
            	pf.setEmail(u.get("email").toString());
            	pf.setPhone(u.get("phone").toString());
            	pf.setRegisterDate(u.get("registerDate").toString());
            	pf.setRole(u.get("role").toString());
            	
            	JSONArray subForums = (JSONArray) u.get("subscribedForums");
            	Iterator sf = subForums.iterator();
            	ArrayList<String> pom = new ArrayList<String>();
            	while(sf.hasNext()) {
            		pom.add(sf.next().toString());
            	}
            	pf.setSubscribedForums(pom);
            	
            	JSONArray postedTopics = (JSONArray) u.get("postedTopics");
            	Iterator pt = postedTopics.iterator();
            	ArrayList<String> pomPT = new ArrayList<String>();
            	while(pt.hasNext()) {
            		pomPT.add(pt.next().toString());
            	}
            	pf.setPostedTopics(pomPT);
            	
            	JSONArray postedComments = (JSONArray) u.get("postedComments");
            	Iterator pc = postedComments.iterator();
            	ArrayList<String> pomPC = new ArrayList<String>();
            	while(pc.hasNext()) {
            		pomPC.add(pc.next().toString());
            	}
            	pf.setPostedComments(pomPC);
            	
            	users.put(pf.getUsername(), pf);
            	userList.add(pf);
            		
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	
	/** Vraca kolekciju proizvoda. */
	public Collection<User> values() {
		return users.values();
	}

	/** Vraca kolekciju proizvoda. */
	public Collection<User> getValues() {
		return users.values();
	}
	
	public void addUserToList(User user) {
		users.put(user.getUsername(), user);
		userList.add(user);
		System.out.print(users);
	}
	
	public void changeUser(User user) {
		users.put(user.getUsername(), user);
		int index= -1;
		for(User u : userList) {
			if(u.getUsername().trim().equals(user.getUsername().trim()))
				index = userList.indexOf(u);
		}
		userList.set(index, user);
	}
	

	/** Vraca proizvod na osnovu njegovog id-a. */
	public User getUser(String user) {
		return users.get(user);
	}

	/** Vraca listu proizvoda. */
	public ArrayList<User> getUserList() {
		return userList;
	}
}
