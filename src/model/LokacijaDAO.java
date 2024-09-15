package model;

import misc.EntityManagerFactoryHolder;

import java.util.List;

import javax.persistence.*;


public class LokacijaDAO {
	EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public Lokacija searchById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Lokacija ret = em.createQuery("SELECT a FROM Lokacija a WHERE a.lokacija_id = :id", Lokacija.class)
				.setParameter("id", id).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return ret;
	}
	public void addLocation(Lokacija lokacija) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(lokacija);
		em.getTransaction().commit();
		em.close();
	} //addLocation is separated from updateLocation so addLocation can never update already existing one
	public void updateLocation(Lokacija lokacija) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(lokacija);
		em.getTransaction().commit();
		em.close();
	}
	public List<Lokacija> getAllLocations() {
		EntityManager em = emf.createEntityManager();
		List<Lokacija> ret = em.createQuery("SELECT a FROM Lokacija a", Lokacija.class).getResultList();
		em.close();
		return ret;
	} //It is assumed this app will never have too many locations so we will just return them all for now
	public List<String> getAllCities() {
		EntityManager em = emf.createEntityManager();
		List<String> ret = em.createQuery("SELECT DISTINCT a.grad FROM Lokacija a", String.class).getResultList();
		em.close();
		return ret;
	}
}