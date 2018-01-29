package beans;

import java.util.ArrayList;

public class Podforum {

	private String naziv;
	private String opis;
	private String ikonica;
	private ArrayList<String> spisakPravila;
	private String odgovorniModerator;
	private ArrayList<String> spisakModeratora;
	
	private int count;
	
	public Podforum() {
		//this.count = 1;
	}
	
	public Podforum(String naziv, String opis, String ikonica, ArrayList<String> spisakPravila, String odgovorniModerator, ArrayList<String> spisakModeratora) {
		this.naziv = naziv;
		this.opis = opis;
		this.ikonica = ikonica;
		this.spisakPravila = spisakPravila;
		this.odgovorniModerator = odgovorniModerator;
		this.spisakModeratora = spisakModeratora;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getIkonica() {
		return ikonica;
	}

	public void setIkonica(String ikonica) {
		this.ikonica = ikonica;
	}

	public String getOdgovorniModerator() {
		return odgovorniModerator;
	}

	public void setOdgovorniModerator(String odgovorniModerator) {
		this.odgovorniModerator = odgovorniModerator;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<String> getSpisakPravila() {
		return spisakPravila;
	}

	public void setSpisakPravila(ArrayList<String> spisakPravila) {
		this.spisakPravila = spisakPravila;
	}

	public ArrayList<String> getSpisakModeratora() {
		return spisakModeratora;
	}

	public void setSpisakModeratora(ArrayList<String> spisakModeratora) {
		this.spisakModeratora = spisakModeratora;
	}
	
	@Override
	public String toString() {
		return "[naziv=" + naziv + ", opis=" + opis + ", ikonica=" + ikonica
				+ ", odgovorni moderator=" + odgovorniModerator +  "]"; 

	}
}
