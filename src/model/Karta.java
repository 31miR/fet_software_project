package model;

import javax.persistence.*;

@Entity
public class Karta {
	@Id
	@GeneratedValue
	private int karta_id;
	private int cijena; //store prices as ints, eg. 3.45$ is 345 in here
	private int cijenaRezervacije;
	private String sjediste;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sektor_id")
	private Sektor sektor;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="dogadjaj_id")
	private Dogadjaj dogadjaj;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="korRezervisao")
	private Korisnik korRezervisao;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="korKupio")
	private Korisnik korKupio;
	public int getKarta_id() {
		return karta_id;
	}
	public void setKarta_id(int karta_id) {
		this.karta_id = karta_id;
	}
	public int getCijena() {
		return cijena;
	}
	public void setCijena(int cijena) {
		this.cijena = cijena;
	}
	public int getCijenaRezervacije() {
		return cijenaRezervacije;
	}
	public void setCijenaRezervacije(int cijenaRezervacije) {
		this.cijenaRezervacije = cijenaRezervacije;
	}
	public String getSjediste() {
		return sjediste;
	}
	public void setSjediste(String sjediste) {
		this.sjediste = sjediste;
	}
	public Sektor getSektor() {
		return sektor;
	}
	public void setSektor(Sektor sektor) {
		this.sektor = sektor;
	}
	public Dogadjaj getDogadjaj() {
		return dogadjaj;
	}
	public void setDogadjaj(Dogadjaj dogadjaj) {
		this.dogadjaj = dogadjaj;
	}
	public Korisnik getKorRezervisao() {
		return korRezervisao;
	}
	public void setKorRezervisao(Korisnik korRezervisao) {
		this.korRezervisao = korRezervisao;
	}
	public Korisnik getKorKupio() {
		return korKupio;
	}
	public void setKorKupio(Korisnik korKupio) {
		this.korKupio = korKupio;
	}
}
