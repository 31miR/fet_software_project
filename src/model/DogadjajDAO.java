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
			if (filters.get(i).equals("grad")) {
				where_part += "b."+filters.get(i) + "=" + filters.get(i+1);
			}
			else {
				where_part += "a."+filters.get(i) + "=" + filters.get(i+1);
			}
		}
		String sort_part = sortBy + " " + (ascending ? "ASC" : "DESC");
		EntityManager em = emf.createEntityManager();
		List<Dogadjaj> ret = em.createQuery("Select a FROM Dogadjaj a INNER JOIN Lokacija b ON "
				+ "a.lokacija = b.lokacija_id" + (where_part.length() == 0 ? "" : " WHERE " + where_part)
				+ " ORDER BY " + sort_part, Dogadjaj.class).setFirstResult(start).setMaxResults(ammount).getResultList();
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
	public int countTickets(Dogadjaj dogadjaj, Sektor sektor, boolean onlyFree) {//if sektor is null, it will count all the cards for Dogadjaj
		
		return 0; //TODO: implement this
	}
	public List<Dogadjaj> getLimitedPending(int start, int ammount) {
		EntityManager em = emf.createEntityManager();
		List<Dogadjaj> ret = em.createQuery("SELECT a FROM Dogadjaj a WHERE a.dogadjajApproved = :falseValue", Dogadjaj.class).setFirstResult(start)
				.setParameter("falseValue", false).setMaxResults(ammount).getResultList();
		em.close();
		return ret;
	}
	public void deleteDogadjaj(Dogadjaj dogadjaj) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(dogadjaj);
		em.remove(dogadjaj);
		em.getTransaction().commit();
	}
}
