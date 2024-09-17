package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lokacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lokacija_id;
	private String naziv;
	private int kapacitet;
	private String grad;
	private String adresa;
	private String slika; //lokacija slike
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lokacija")
	private List<Dogadjaj> dogadjaj;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lokacija")
	private List<Sektor> sektor;
	public int getLokacija_id() {
		return lokacija_id;
	}
	public void setLokacija_id(int lokacija_id) {
		this.lokacija_id = lokacija_id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getKapacitet() {
		return kapacitet;
	}
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public List<Dogadjaj> getDogadjaj() {
		return dogadjaj;
	}
	public void setDogadjaj(List<Dogadjaj> dogadjaj) {
		this.dogadjaj = dogadjaj;
	}
	public List<Sektor> getSektor() {
		return sektor;
	}
	public void setSektor(List<Sektor> sektor) {
		this.sektor = sektor;
	}
}
