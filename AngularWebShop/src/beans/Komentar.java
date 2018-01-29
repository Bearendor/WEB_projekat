package beans;

import java.util.ArrayList;

public class Komentar {
	private int id = 0;
	private String pripadaTemi;
	private String autor;
	private String datumKomentara;
	private String roditeljskiKomentar;
	private ArrayList<String> podkomentari;
	private String tekstKomentara;
	private ArrayList<String> pozitivniGlasovi;
	private ArrayList<String> negativniGlasovi;
	private String menjan;
	
	public Komentar() {
		this.id ++;
	}
	
	public Komentar(String pripadaTemi, String autor, String datumKomentara, String roditeljskiKomentar, ArrayList<String> podkomentari, 
			String tekstKomentara, ArrayList<String> pozitivniGlasovi, ArrayList<String> negativniGlasovi, String menjan) {
		this.id ++;
		this.pripadaTemi = pripadaTemi;
		this.autor = autor;
		this.datumKomentara = datumKomentara;
		this.roditeljskiKomentar = roditeljskiKomentar;
		this.podkomentari = podkomentari;
		this.tekstKomentara = tekstKomentara;
		this.pozitivniGlasovi = pozitivniGlasovi;
		this.negativniGlasovi = negativniGlasovi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPripadaTemi() {
		return pripadaTemi;
	}

	public void setPripadaTemi(String pripadaTemi) {
		this.pripadaTemi = pripadaTemi;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDatumKomentara() {
		return datumKomentara;
	}

	public void setDatumKomentara(String datumKomentara) {
		this.datumKomentara = datumKomentara;
	}

	public String getRoditeljskiKomentar() {
		return roditeljskiKomentar;
	}

	public void setRoditeljskiKomentar(String roditeljskiKomentar) {
		this.roditeljskiKomentar = roditeljskiKomentar;
	}

	public ArrayList<String> getPodkomentari() {
		return podkomentari;
	}

	public void setPodkomentari(ArrayList<String> podkomentari) {
		this.podkomentari = podkomentari;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
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

	public String getMenjan() {
		return menjan;
	}

	public void setMenjan(String menjan) {
		this.menjan = menjan;
	}
	
}
