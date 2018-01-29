package beans;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
	private String phone;
	private String email;
	private String registerDate;
	private ArrayList<String> subscribedForums;
	private ArrayList<String> postedTopics;
	private ArrayList<String> postedComments;
	

	public User() {
		
	}
	
	public User(String user, String pass, String firstName, String lastName, String role, 
			String phoneNum, String email, String date, ArrayList<String> subFor, 
			ArrayList<String> postedTopics, ArrayList<String> postedComms) {
		this.username = user;
		this.password = pass;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.phone = phoneNum;
		this.email = email;
		this.registerDate = date;
		this.subscribedForums = subFor;
		this.postedTopics = postedTopics;
		this.postedComments = postedComms;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public ArrayList<String> getSubscribedForums() {
		return subscribedForums;
	}

	public void setSubscribedForums(ArrayList<String> subscribedForums) {
		this.subscribedForums = subscribedForums;
	}

	public ArrayList<String> getPostedTopics() {
		return postedTopics;
	}

	public void setPostedTopics(ArrayList<String> postedTopics) {
		this.postedTopics = postedTopics;
	}

	public ArrayList<String> getPostedComments() {
		return postedComments;
	}

	public void setPostedComments(ArrayList<String> postedComments) {
		this.postedComments = postedComments;
	}
	

	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", first name=" + firstName
				+ ", last name=" + lastName + ", role=" + role + ", phone=" + phone 
				+", email=" + email + ", register date=" + registerDate + ", subscribed forums=" + subscribedForums
				+ ", posted topics=" + postedTopics + ", posted comments=" + postedComments
				+"]";
	}

}
