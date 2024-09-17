package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import misc.EntityManagerFactoryHolder;

public class AdministratorDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	
	public Administrator searchByUserName(String name) {
		try {
			EntityManager em = emf.createEntityManager();
			Administrator ret = em.createQuery("SELECT a FROM Administrator a WHERE a.username = :name", Administrator.class)
					.setParameter("name", name)
					.getSingleResult();
			em.close();
			return ret;
		}
		catch (Exception e) {
			return null; //Makes this a total function, i.e. returns valid value for all inputs (I HOPE)
		}
	}
	
	public void addAdministrator(Administrator adm) { //This one is useless
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(adm);
		em.getTransaction().commit();
		em.close();
	}
}
