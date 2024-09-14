package model;

import misc.EntityManagerFactoryHolder;

import java.util.List;

import javax.persistence.*;

public class IzmjeneDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	public void addChange(String table_name, String id, String column_name, String value) {
		Izmjene change = new Izmjene();
		change.setImeTabele(table_name);
		change.setStringIdEntitija(id);
		change.setImeKolone(column_name);
		change.setNovaVrijednost(value);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(change);
		em.getTransaction().commit();
		em.close();
	}
	public List<Izmjene> getLimitedChanges(int start, int ammount) {
		EntityManager em = emf.createEntityManager();
		List<Izmjene> ret = em.createQuery("SELECT a FROM Izmjene a", Izmjene.class).setFirstResult(start)
				.setMaxResults(ammount).getResultList();
		em.close();
		return ret;
	}
	public List<Izmjene> getAllChanges() {
		EntityManager em = emf.createEntityManager();
		List<Izmjene> ret = em.createQuery("SELECT a FROM Izmjene a", Izmjene.class).getResultList();
		em.close();
		return ret;
	}
	private boolean doesEntityExist(String table_name, String id) {
		String statement;
		switch (table_name) {
		case "Korisnik":
			statement = "SELECT COUNT(a) FROM Korisnik a WHERE a.username = " + id;
			break;
		case "Organizator":
			statement = "SELECT COUNT(a) FROM Organizator a WHERE a.username = " + id;
			break;
		case "Dogadjaj":
			statement = "SELECT COUNT(a) FROM Dogadjaj a WHERE a.dogadjaj_id = " + id;
			break;
		default:
			return false;
		}
		EntityManager em = emf.createEntityManager();
		Long count = em.createQuery(statement, Long.class).getSingleResult();
		em.close();
		return count.equals(1L) ? true : false;
	}
	public void acceptChange(Izmjene izmjena) {
		if (!doesEntityExist(izmjena.getImeTabele(), izmjena.getStringIdEntitija())) {
			return;
		}
		EntityManager em = emf.createEntityManager();
		switch (izmjena.getImeTabele()) {
		case "Korisnik":
			em.getTransaction().begin();
			em.createQuery("UPDATE Korisnik a SET a." + izmjena.getImeKolone() + " = '"
					+ izmjena.getNovaVrijednost() + "' WHERE a.username = '"
					+ izmjena.getStringIdEntitija() + "'").executeUpdate();
			em.getTransaction().commit();
			break;
		case "Organizator":
			em.getTransaction().begin();
			em.createQuery("UPDATE Organizator a SET a." + izmjena.getImeKolone() + " = '"
					+ izmjena.getNovaVrijednost() + "' WHERE a.username = '"
					+ izmjena.getStringIdEntitija() + "'").executeUpdate();
			em.getTransaction().commit();
			break;
		case "Dogadjaj":
			em.getTransaction().begin();
			em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = '"
					+ izmjena.getNovaVrijednost() + "' WHERE a.dogadjaj_id = '"
					+ izmjena.getStringIdEntitija() + "'").executeUpdate();
			em.getTransaction().commit();
			break;
		default:
			return;
		}
		em.close();
		deleteChange(izmjena);
	}
	public void deleteChange(Izmjene izmjena) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		izmjena = em.merge(izmjena);
		em.remove(izmjena);
		em.getTransaction().commit();
	}
}
