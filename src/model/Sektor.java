package model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Sektor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sektor_id;
	private String naziv;
	private int kapacitet;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sektor")
	private List<Karta> karta;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="lokacija_id")
	private Lokacija lokacija;
	public int getSektor_id() {
		return sektor_id;
	}
	public void setSektor_id(int sektor_id) {
		this.sektor_id = sektor_id;
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
	public List<Karta> getKarta() {
		return karta;
	}
	public void setKarta(List<Karta> karta) {
		this.karta = karta;
	}
	public Lokacija getLokacija() {
		return lokacija;
	}
	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
}
