package beans;

import java.util.ArrayList;

public class Tema {
	private String podforum;
	private String naslov;
	private String tip;
	private String autor;
	private String sadrzaj;
	private String datumKreiranja;
	private ArrayList<String> pozitivniGlasovi;
	private ArrayList<String> negativniGlasovi;
	
	public Tema() {
		
	}
	
	public Tema (String podforum, String naslov, String tip, String autor, String datumKreiranja,
				String sadrzaj, ArrayList<String> pozitivniGlasovi, ArrayList<String> negativniGlasovi) {
		this.podforum = podforum;
		this.naslov = naslov;
		this.tip = tip;
		this.autor = autor;
		this.sadrzaj = sadrzaj;
		this.datumKreiranja = datumKreiranja;
		this.pozitivniGlasovi = pozitivniGlasovi;
		this.negativniGlasovi = negativniGlasovi;	
	}

	public String getPodforum() {
		return podforum;
	}

	public void setPodforum(String podforum) {
		this.podforum = podforum;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getDatumKreiranja() {
		return datumKreiranja;
	}

	public void setDatumKreiranja(String datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}

	public ArrayList<String> getPozitivniGlasovi() {
		return pozitivniGlasovi;
	}

	public void setPozitivniGlasovi(ArrayList<String> pozitivniGlasovi) {
		this.pozitivniGlasovi = pozitivniGlasovi;
	}

	public ArrayList<String> getNegativniGlasovi() {
		return negativniGlasovi;
	}

	public void setNegativniGlasovi(ArrayList<String> negativniGlasovi) {
		this.negativniGlasovi = negativniGlasovi;
	}
	
	@Override
	public String toString() {
		return "Tema [podforum=" + podforum + ", naslov=" + naslov + ", tip=" + tip
				+ ", autor=" + autor + ", sadrzaj=" + sadrzaj + ", pozitivniGlasovi=" + pozitivniGlasovi 
				+", negativniGlasovi=" + negativniGlasovi + "]";
	}
	
}
