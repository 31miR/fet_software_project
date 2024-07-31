package model;

import misc.EntityManagerFactoryHolder;
import javax.persistence.*;

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
}
