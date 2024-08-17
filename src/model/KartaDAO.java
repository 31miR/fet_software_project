package model;

import misc.EntityManagerFactoryHolder;

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
}
