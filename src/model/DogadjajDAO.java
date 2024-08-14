package model;

import misc.EntityManagerFactoryHolder;

import java.util.List;

import javax.persistence.*;


public class DogadjajDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public List<Dogadjaj> getFiltered(List<String> filters, String sortBy, boolean ascending, int start, int ammount) {
		String where_part = "";
		for(int i = 0; i < filters.size(); i += 2) {
			if (i != 0) {
				where_part += ",";
			}
			where_part += "a."+filters.get(i) + "=" + filters.get(i+1);
		}
		String sort_part = sortBy + " " + (ascending ? "ASC" : "DESC");
		String limit_part = String.valueOf(start) + ", " + String.valueOf(ammount);
		EntityManager em = emf.createEntityManager();
		List<Dogadjaj> ret = em.createQuery("Select a FROM Dogadjaj a WHERE " + where_part + " ORDER BY "
				+ sort_part + " LIMIT " + limit_part, Dogadjaj.class).getResultList();
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
}
