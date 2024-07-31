package model;

import misc.EntityManagerFactoryHolder;
import javax.persistence.*;

public class KorisnikDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	
	public Korisnik searchByUserName(String name) {
		try {
			EntityManager em = emf.createEntityManager();
			Korisnik ret = em.createQuery("SELECT a FROM Korisnik a WHERE a.username = :name", Korisnik.class)
					.setParameter("name", name)
					.getSingleResult();
			em.close();
			return ret;
		}
		catch (Exception e) {
			return null; //Makes this a total function, i.e. returns valid value for all inputs (I HOPE)
		}
	}
	
	public void addKorisnik(Korisnik kor) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(kor);
		em.getTransaction().commit();
		em.close();
	}
}
