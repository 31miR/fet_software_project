package model;

import misc.EntityManagerFactoryHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;


public class DogadjajDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public Dogadjaj searchById(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			Dogadjaj ret = em.createQuery("SELECT a FROM Dogadjaj a WHERE a.dogadjaj_id = :id", Dogadjaj.class)
					.setParameter("id", id)
					.getSingleResult();
			em.close();
			return ret;
		}
		catch (Exception e) {
			return null; //Makes this a total function, i.e. returns valid value for all inputs (I HOPE)
		}
	}
	public List<Dogadjaj> getFiltered(List<String> filters, String searchString, String sortBy, boolean ascending,
										int start, int ammount) {
		String where_part = "";
		if (!searchString.equals("")) {
			where_part += "a.naziv LIKE '%" + searchString + "%'";
		}
		for(int i = 0; i < filters.size(); i += 2) {
			if (!where_part.equals("")) {
				where_part += " AND ";
			}
			if (filters.get(i).equals("grad")) {
				where_part += "b."+filters.get(i) + "='" + filters.get(i+1) + "'";
			}
			else {
				where_part += "a."+filters.get(i) + "='" + filters.get(i+1) + "'";
			}
		}
		String sort_part = sortBy + " " + (ascending ? "ASC" : "DESC");
		EntityManager em = emf.createEntityManager();
		List<Dogadjaj> ret = em.createQuery("Select a FROM Dogadjaj a INNER JOIN Lokacija b ON "
				+ "a.lokacija = b.lokacija_id" + (where_part.length() == 0 ? " WHERE a.dogadjajApproved = :trueValue"
													: " WHERE a.dogadjajApproved = :trueValue AND " + where_part)
				+ " ORDER BY " + sort_part, Dogadjaj.class).setParameter("trueValue", true)
				.setFirstResult(start).setMaxResults(ammount).getResultList();
		em.close();
		return ret;
	}
	public void addDogadjaj(Dogadjaj d) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
		em.close();
	}
	public void updateDogadjaj(Dogadjaj d) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(d);
		em.getTransaction().commit();
		em.close();
	} //This function should not be used when requesting updates from Organizator!
	public List<Dogadjaj> getPending() {
		EntityManager em = emf.createEntityManager();
		List<Dogadjaj> ret = em.createQuery("SELECT a FROM Dogadjaj a WHERE a.dogadjajApproved = :falseValue", Dogadjaj.class)
				.setParameter("falseValue", false).getResultList();
		em.close();
		return ret;
	}
	public void deleteDogadjaj(Dogadjaj dogadjaj) {
	    EntityManager em = emf.createEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        // Pronađi entitet u bazi koristeći njegov ID
	        Dogadjaj managedDogadjaj = em.find(Dogadjaj.class, dogadjaj.getDogadjaj_id());
	        
	        if (managedDogadjaj != null) {
	            em.remove(managedDogadjaj);
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
	//Every list in map starts with empty string and there is at least one key with empty string!
	public Map<String, List<String>> getTypeToSubTypeMapping() {
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		List<String> dummy = new ArrayList<>();
		dummy.add("");
		ret.put("", dummy);
		EntityManager em = emf.createEntityManager();
		List<Object[]> queryResult = em.createQuery("SELECT DISTINCT a.vrsta, a.podvrsta FROM Dogadjaj a", Object[].class).getResultList();
		for (Object[] i : queryResult) {
			if (ret.containsKey(String.valueOf(i[0]))) {
				ret.get(String.valueOf(i[0])).add(String.valueOf(i[1]));
			}
			else {
				dummy = new ArrayList<>();
				dummy.add("");
				ret.put(String.valueOf(i[0]), dummy);
				ret.get(String.valueOf(i[0])).add(String.valueOf(i[1]));
			}
		}
		return ret;
	}
	


	
}