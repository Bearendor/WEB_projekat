package beans;

public class Message {
	
	private String id;
	private String posiljalac;
	private String primalac;
	private String naslov;
	private String sadrzaj;
	private String procitana;
	
	public Message() {	
	}
	
	public Message(String ID, String posiljalac, String primalac, String naslov, String sadrzaj, String procitana) {
		this.id = ID;
		this.posiljalac = posiljalac;
		this.primalac = primalac;
		this.naslov = naslov;
		this.sadrzaj = sadrzaj;
		this.procitana = procitana;
	}

	public String getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}

	public String getPrimalac() {
		return primalac;
	}

	public void setPrimalac(String primalac) {
		this.primalac = primalac;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getProcitana() {
		return procitana;
	}

	public void setProcitana(String procitana) {
		this.procitana = procitana;
	}
	@Override
	public String toString() {
		return "Message: \n {"+"'id':"+id+ "\n 'posiljalac':"+posiljalac+", \n"+"'primalac':"+primalac+", \n"+"'naslov':"+naslov+", \n"+"'sadrzaj':"+sadrzaj+", \n"+"'procitana':"+procitana+"}";
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		id = iD;
	}

	

}
