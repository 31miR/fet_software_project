package controller;

import model.Korisnik;
import model.KorisnikDAO;

public class KorisnikController {
	 private KorisnikDAO korisnikDAO = new KorisnikDAO();

	    public Korisnik getKorisnik(String username) {
	        return korisnikDAO.searchByUserName(username);
	    }

	    public void addKorisnik(Korisnik korisnik) {
	        korisnikDAO.addKorisnik(korisnik);
	    }
}
