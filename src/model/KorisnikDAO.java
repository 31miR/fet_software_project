package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import misc.EntityManagerFactoryHolder;

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
	public void updateKorisnik(Korisnik kor) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(kor);
		em.getTransaction().commit();
		em.close();
	}
	public List<Korisnik> getPending() {
		EntityManager em = emf.createEntityManager();
		List<Korisnik> ret = em.createQuery("SELECT a FROM Korisnik a WHERE a.profileApproved = :falseValue", Korisnik.class)
				.setParameter("falseValue", false).getResultList();
		em.close();
		return ret;
	}
	public void deleteKorisnik(Korisnik korisnik) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Pronađi entitet u bazi koristeći njegov ID
	        Korisnik managedKorisnik = em.find(Korisnik.class, korisnik.getUsername());
	        
	        if (managedKorisnik != null) {
	            em.remove(managedKorisnik);
	        }
	        
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        e.printStackTrace(); // Log or handle the exception as necessary
	    } finally {
	        em.close();
	    }
	}

}
