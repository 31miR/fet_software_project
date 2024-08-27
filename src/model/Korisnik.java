package model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Korisnik {
	@Id
	private String username;
	private String password;
	private String email;
	private String name;
	private String lastName;
	private String address;
	private String phone;
	private int walletBalance;
	private int brojKupljenihKarti;
	private boolean profileApproved;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "korRezervisao")
	private List<Karta> kartaRezervisana;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "korKupio")
	private List<Karta> kartaKupljena;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(int walletBalance) {
		this.walletBalance = walletBalance;
	}
	public int getBrojKupljenihKarti() {
		return brojKupljenihKarti;
	}
	public void setBrojKupljenihKarti(int brojKupljenihKarti) {
		this.brojKupljenihKarti = brojKupljenihKarti;
	}
	public boolean isProfileApproved() {
		return profileApproved;
	}
	public void setProfileApproved(boolean profileApproved) {
		this.profileApproved = profileApproved;
	}
	public List<Karta> getKartaRezervisana() {
		return kartaRezervisana;
	}
	public void setKartaRezervisana(List<Karta> kartaRezervisana) {
		this.kartaRezervisana = kartaRezervisana;
	}
	public List<Karta> getKartaKupljena() {
		return kartaKupljena;
	}
	public void setKartaKupljena(List<Karta> kartaKupljena) {
		this.kartaKupljena = kartaKupljena;
	}
}
