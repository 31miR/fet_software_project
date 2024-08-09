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
	private String noviString;
	private java.util.Date noviDatum;
	private int noviInt;
	private double noviDouble;
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
	public String getNoviString() {
		return noviString;
	}
	public void setNoviString(String noviString) {
		this.noviString = noviString;
	}
	public java.util.Date getNoviDatum() {
		return noviDatum;
	}
	public void setNoviDatum(java.util.Date noviDatum) {
		this.noviDatum = noviDatum;
	}
	public int getNoviInt() {
		return noviInt;
	}
	public void setNoviInt(int noviInt) {
		this.noviInt = noviInt;
	}
	public double getNoviDouble() {
		return noviDouble;
	}
	public void setNoviDouble(double noviDouble) {
		this.noviDouble = noviDouble;
	}
}
