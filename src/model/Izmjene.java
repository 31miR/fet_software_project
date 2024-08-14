package model;

import javax.persistence.*;

class IzmjeneId {
	@SuppressWarnings("unused")
	private String imeTabele;
	@SuppressWarnings("unused")
	private String stringIdEntitija;
	@SuppressWarnings("unused")
	private String imeKolone;
}

@Entity
@IdClass(IzmjeneId.class)
public class Izmjene {
	@Id
	private String imeTabele;
	@Id
	private String stringIdEntitija;
	@Id
	private String imeKolone;
	private String novaVrijednost;
	public String getImeTabele() {
		return imeTabele;
	}
	public void setImeTabele(String imeTabele) {
		this.imeTabele = imeTabele;
	}
	public String getStringIdEntitija() {
		return stringIdEntitija;
	}
	public void setStringIdEntitija(String stringIdEntitija) {
		this.stringIdEntitija = stringIdEntitija;
	}
	public String getImeKolone() {
		return imeKolone;
	}
	public void setImeKolone(String imeKolone) {
		this.imeKolone = imeKolone;
	}
	public String getNovaVrijednost() {
		return novaVrijednost;
	}
	public void setNovaVrijednost(String novaVrijednost) {
		this.novaVrijednost = novaVrijednost;
	}
}
