package beans;

public class Complaint {
	
	private int id;
	private String tekstZalbe;
	private String datumZalbe;
	private String entitetZalbe;
	private String zalPodforum;
	private String idEntiteta;
	private String podnosilacZalbe;
	
	public Complaint () {
	}
	
	public Complaint(int id, String tekstZalbe, String datumZalbe, String entitetZalbe, 
			String zalPodforum, String idEntiteta, String podnosilacZalbe) {
		
		this.id = id;
		this.tekstZalbe = tekstZalbe;
		this.datumZalbe = datumZalbe;
		this.entitetZalbe = entitetZalbe;
		this.zalPodforum = zalPodforum;
		this.idEntiteta = idEntiteta;
		this.podnosilacZalbe = podnosilacZalbe;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTekstZalbe() {
		return tekstZalbe;
	}

	public void setTekstZalbe(String tekstZalbe) {
		this.tekstZalbe = tekstZalbe;
	}

	public String getDatumZalbe() {
		return datumZalbe;
	}

	public void setDatumZalbe(String datumZalbe) {
		this.datumZalbe = datumZalbe;
	}

	public String getEntitetZalbe() {
		return entitetZalbe;
	}

	public void setEntitetZalbe(String entitetZalbe) {
		this.entitetZalbe = entitetZalbe;
	}

	public String getZalPodforum() {
		return zalPodforum;
	}

	public void setZalPodforum(String zalPodforum) {
		this.zalPodforum = zalPodforum;
	}

	public String getIdEntiteta() {
		return idEntiteta;
	}

	public void setIdEntiteta(String idEntiteta) {
		this.idEntiteta = idEntiteta;
	}

	public String getPodnosilacZalbe() {
		return podnosilacZalbe;
	}

	public void setPodnosilacZalbe(String podnosilacZalbe) {
		this.podnosilacZalbe = podnosilacZalbe;
	}
	
	@Override
	public String toString() {
		return "Complaint: \n {"+"'id':"+id+ "\n 'tekstZalbe':"+tekstZalbe+", \n"+"'datumZalbe':"+datumZalbe+", \n"
				+"'entitetZalbe':"+entitetZalbe+", \n"+"'zalPodforum':"+zalPodforum+", \n"+"'idEntiteta':"+idEntiteta
				+", \n"+"'podnosilacZalbe':"+podnosilacZalbe+"\n}";
	}
}
