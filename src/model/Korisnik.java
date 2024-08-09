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
	private double walletBalance;
	private double discountRate;
	private boolean profileApproved;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "korisnik")
	private List<Karta> karte;
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
	public double getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public boolean isProfileApproved() {
		return profileApproved;
	}
	public void setProfileApproved(boolean profileApproved) {
		this.profileApproved = profileApproved;
	}
	public List<Karta> getKarte() {
		return karte;
	}
	public void setKarte(List<Karta> karte) {
		this.karte = karte;
	}
}
