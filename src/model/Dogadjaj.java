package model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Dogadjaj {
	@Id
	@GeneratedValue
	private int dogadjaj_id;
	private String naziv;
	private String opis;
	private java.util.Date datum;
	private boolean dogadjajApproved;
	@ManyToOne(fetch = FetchType.LAZY)
	private Organizator organizator;
	@ManyToOne(fetch = FetchType.LAZY)
	private Lokacija lokacija;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dogadjaj")
	private List<Karta> karte;
	public int getDogadjaj_id() {
		return dogadjaj_id;
	}
	public void setDogadjaj_id(int dogadjaj_id) {
		this.dogadjaj_id = dogadjaj_id;
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
	public java.util.Date getDatum() {
		return datum;
	}
	public void setDatum(java.util.Date datum) {
		this.datum = datum;
	}
	public boolean isDogadjajApproved() {
		return dogadjajApproved;
	}
	public void setDogadjajApproved(boolean dogadjajApproved) {
		this.dogadjajApproved = dogadjajApproved;
	}
	public Organizator getOrganizator() {
		return organizator;
	}
	public void setOrganizator(Organizator organizator) {
		this.organizator = organizator;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
	public List<Karta> getKarte() {
		return karte;
	}
	public void setKarte(List<Karta> karte) {
		this.karte = karte;
	}
}
