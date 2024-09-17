package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import misc.EntityManagerFactoryHolder;

public class OrganizatorDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public Organizator searchByUserName(String name) {
		try {
			EntityManager em = emf.createEntityManager();
			Organizator ret = em.createQuery("SELECT a FROM Organizator a WHERE a.username = :name", Organizator.class)
					.setParameter("name", name)
					.getSingleResult();
			em.close();
			return ret;
		}
		catch (Exception e) {
			return null; //Makes this a total function, i.e. returns valid value for all inputs (I HOPE)
		}
	}
	public void addOrganizator(Organizator org) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(org);
		em.getTransaction().commit();
		em.close();
	}
	public void updateOrganizator(Organizator org) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(org);
		em.getTransaction().commit();
		em.close();
	}
	public List<Organizator> getPending() {
		EntityManager em = emf.createEntityManager();
		List<Organizator> ret = em.createQuery("SELECT a FROM Organizator a WHERE a.profileApproved = :falseValue", Organizator.class)
				.setParameter("falseValue", false).getResultList();
		em.close();
		return ret;
	}
	public void deleteOrganizator(Organizator organizator) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Pronađi entitet u bazi koristeći njegov ID
	        Organizator managedOrganizator = em.find(Organizator.class, organizator.getUsername());
	        
	        if (managedOrganizator != null) {
	            em.remove(managedOrganizator);
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
