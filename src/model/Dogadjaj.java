package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Dogadjaj {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dogadjaj_id;
	private String naziv;
	private String opis;
	private java.util.Date datum;
	private String vrsta;
	private String podvrsta;
	private String slika; //lokacija slike u projektu
	private boolean zavrsio;
	private int maxKartiPoKorisniku;
	private boolean naplataPriRezervaciji;
	private boolean dogadjajApproved;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="organizator_id")
	private Organizator organizator;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="lokacija_id")
	private Lokacija lokacija;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dogadjaj")
	private List<Karta> karta;
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
	public String getVrsta() {
		return vrsta;
	}
	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}
	public String getPodvrsta() {
		return podvrsta;
	}
	public void setPodvrsta(String podvrsta) {
		this.podvrsta = podvrsta;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public boolean isZavrsio() {
		return zavrsio;
	}
	public void setZavrsio(boolean zavrsio) {
		this.zavrsio = zavrsio;
	}
	public int getMaxKartiPoKorisniku() {
		return maxKartiPoKorisniku;
	}
	public void setMaxKartiPoKorisniku(int maxKartiPoKorisniku) {
		this.maxKartiPoKorisniku = maxKartiPoKorisniku;
	}
	public boolean isNaplataPriRezervaciji() {
		return naplataPriRezervaciji;
	}
	public void setNaplataPriRezervaciji(boolean naplataPriRezervaciji) {
		this.naplataPriRezervaciji = naplataPriRezervaciji;
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
	public List<Karta> getKarta() {
		return karta;
	}
	public void setKarta(List<Karta> karta) {
		this.karta = karta;
	}
}
