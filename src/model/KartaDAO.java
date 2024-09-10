package model;

import misc.EntityManagerFactoryHolder;

import java.util.List;

import javax.persistence.*;


public class KartaDAO {
	EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public void updateTicket(Karta karta) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(karta);
		em.getTransaction().commit();
	}
	public void generateTickets(Dogadjaj dogadjaj, Sektor sektor, int ammount, int price, int res_price) {
		EntityManager em = emf.createEntityManager();
		for (int i = 0; i < ammount; ++i) {
			Karta dummy = new Karta();
			dummy.setCijena(price);
			dummy.setCijenaRezervacije(res_price);
			dummy.setDogadjaj(dogadjaj);
			dummy.setSektor(sektor);
			dummy.setSjediste("neodredjeno");
			dogadjaj.getKarta().add(dummy);
			sektor.getKarta().add(dummy);
			em.getTransaction().begin();
			em.persist(dummy);
			em.merge(dogadjaj);
			em.merge(sektor);
			em.getTransaction().commit();
		}
		em.close();
	}
	public void addTicket(Karta karta) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(karta);
		karta.getDogadjaj().getKarta().add(karta);
		karta.getSektor().getKarta().add(karta);
		em.merge(karta.getDogadjaj());
		em.merge(karta.getSektor());
		em.getTransaction().commit();
		em.close();
	}
	public List<Karta> getFreeTickets(Dogadjaj dogadjaj, Sektor sektor) {
		EntityManager em = emf.createEntityManager();
		List<Karta> ret = em.createQuery("SELECT a FROM Karta a WHERE a.dogadjaj = :dogadjaj AND a.sektor = :sektor AND "
				+ "a.korKupio IS NULL AND a.korRezervisao IS NULL", Karta.class)
				.setParameter("dogadjaj", dogadjaj).setParameter("sektor", sektor)
				.getResultList();
		em.close();
		return ret;
	}
	//Basically, this one forces payment for rezerved tickets if the Event has finished (assuming it was not paid already)
	public void forceUserReservedPayment(Korisnik korisnik) {
		KorisnikDAO korisnikDAO = new KorisnikDAO();
		EntityManager em = emf.createEntityManager();
		List<Karta> ticketsToPay = em.createQuery("SELECT a FROM Karta a INNER JOIN Dogadjaj b ON "
				+ "a.dogadjaj = b.dogadjaj_id WHERE "
				+ "b.zavrsio = :trueValue AND "
				+ "b.naplataPriRezervaciji = :falseValue AND "
				+ "a.korRezervisao = :korisnik", Karta.class)
				.setParameter("trueValue", true)
				.setParameter("falseValue", false)
				.setParameter("korisnik", korisnik).getResultList();
		em.getTransaction().begin();
		for (Karta i : ticketsToPay) {
			korisnik.setWalletBalance(korisnik.getWalletBalance() - i.getCijenaRezervacije());
			korisnikDAO.updateKorisnik(korisnik);
			i.setKorRezervisao(null);
			updateTicket(i);
		}
		em.getTransaction().commit();
		em.close();
	}
	//Remove reserved tickets for which reservation has been paid already and the event has finished before user bought them
	public void removeUserReservedAndPaid(Korisnik korisnik) {
		EntityManager em = emf.createEntityManager();
		List<Karta> ticketsToRemove = em.createQuery("SELECT a FROM Karta a INNER JOIN Dogadjaj b ON "
				+ "a.dogadjaj = b.dogadjaj_id WHERE "
				+ "b.zavrsio = :trueValue AND "
				+ "b.naplataPriRezervaciji = :trueValue AND "
				+ "a.korRezervisao = :korisnik", Karta.class)
				.setParameter("trueValue", true)
				.setParameter("korisnik", korisnik).getResultList();
		em.getTransaction().begin();
		for (Karta i : ticketsToRemove) {
			i.setKorRezervisao(null);
			updateTicket(i);
		}
		em.getTransaction().commit();
		em.close();
	}
	public List<Karta> getReservedTicketsForUser(Korisnik korisnik) {
		EntityManager em = emf.createEntityManager();
		List<Karta> ret = em.createQuery("SELECT a FROM Karta a WHERE a.korRezervisao = :korisnik", Karta.class)
				.setParameter("korisnik", korisnik).getResultList();
		em.close();
		return ret;
	}
	public List<Karta> getBoughtTicketsForUser(Korisnik korisnik) {
		EntityManager em = emf.createEntityManager();
		List<Karta> ret = em.createQuery("SELECT a FROM Karta a WHERE a.korKupio = :korisnik", Karta.class)
				.setParameter("korisnik", korisnik).getResultList();
		em.close();
		return ret;
	}
	public int countBoughtTicketsForUser(Korisnik korisnik) {
		EntityManager em = emf.createEntityManager();
		long ret = (long)em.createQuery("SELECT COUNT(a) FROM Karta a WHERE a.korKupio = :korisnik", Long.class)
				.setParameter("korisnik", korisnik).getSingleResult();
		em.close();
		return (int)ret;
	}
}
