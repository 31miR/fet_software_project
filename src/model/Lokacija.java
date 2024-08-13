package model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Lokacija {
	@Id
	@GeneratedValue
	private int lokacija_id;
	private String naziv;
	private int kapacitet;
	private String grad;
	private String adresa;
	private String slika; //lokacija slike
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lokacija")
	private List<Dogadjaj> dogadjaji;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lokacija")
	private List<Sektor> sektori;
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
	public List<Dogadjaj> getDogadjaji() {
		return dogadjaji;
	}
	public void setDogadjaji(List<Dogadjaj> dogadjaji) {
		this.dogadjaji = dogadjaji;
	}
	public List<Sektor> getSektori() {
		return sektori;
	}
	public void setSektori(List<Sektor> sektori) {
		this.sektori = sektori;
	}
}
