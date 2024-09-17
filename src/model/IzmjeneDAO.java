package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import misc.EntityManagerFactoryHolder;

public class IzmjeneDAO {
	private EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	LokacijaDAO lokacijaDAO = new LokacijaDAO();
	public void addChange(String table_name, String id, String column_name, String value) {
		Izmjene change = new Izmjene();
		change.setImeTabele(table_name);
		change.setStringIdEntitija(id);
		change.setImeKolone(column_name);
		change.setNovaVrijednost(value);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(change);
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
			statement = "SELECT COUNT(a) FROM Korisnik a WHERE a.username = '" + id + "'";
			break;
		case "Organizator":
			statement = "SELECT COUNT(a) FROM Organizator a WHERE a.username = '" + id + "'";
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
		em.getTransaction().begin();
		if (izmjena.getImeTabele().equals("Dogadjaj")) {
			switch(DogadjajColumnTypeOf(izmjena.getImeKolone())) {
			case "String":
				em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = :value WHERE a.dogadjaj_id = :id")
				.setParameter("value", izmjena.getNovaVrijednost()).setParameter("id", Integer.parseInt(izmjena.getStringIdEntitija()))
				.executeUpdate();
				break;
			case "int":
				em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = :value WHERE a.dogadjaj_id = :id")
				.setParameter("value", Integer.parseInt(izmjena.getNovaVrijednost())).setParameter("id", Integer.parseInt(izmjena.getStringIdEntitija()))
				.executeUpdate();
				break;
			case "Date":
				try {
					em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = :value WHERE a.dogadjaj_id = :id")
					.setParameter("value", formatter.parse(izmjena.getNovaVrijednost())).setParameter("id", Integer.parseInt(izmjena.getStringIdEntitija()))
					.executeUpdate();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			case "Lokacija":
				em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = :value WHERE a.dogadjaj_id = :id")
				.setParameter("value", lokacijaDAO.searchById(Integer.parseInt(izmjena.getNovaVrijednost())))
				.setParameter("id", Integer.parseInt(izmjena.getStringIdEntitija())).executeUpdate();
				break;
			case "boolean":
				em.createQuery("UPDATE Dogadjaj a SET a." + izmjena.getImeKolone() + " = :value WHERE a.dogadjaj_id = :id")
				.setParameter("value", Boolean.parseBoolean(izmjena.getNovaVrijednost()))
				.setParameter("id", Integer.parseInt(izmjena.getStringIdEntitija())).executeUpdate();
				break;
			}
		}
		else {
			em.createQuery("UPDATE " + izmjena.getImeTabele() + " a SET a." + izmjena.getImeKolone() + " = :value WHERE a.username = :id")
			.setParameter("value", izmjena.getNovaVrijednost()).setParameter("id", izmjena.getStringIdEntitija())
			.executeUpdate();
		}
		em.getTransaction().commit();
		em.close();
		deleteChange(izmjena);
	}
	private String DogadjajColumnTypeOf(String imeKolone) {
		switch(imeKolone) {
		case "naziv":
			return "String";
		case "opis":
			return "String";
		case "datum":
			return "Date";
		case "vrsta":
			return "String";
		case "povrsta":
			return "String";
		case "slika":
			return "String";
		case "naplataPriRezervaciji":
			return "boolean";
		case "zavrsio":
			return "boolean";
		case "maxKartiPoKorisniku":
			return "int";
		case "lokacija":
			return "Lokacija";
		}
		return "String";
	}
	public void deleteChange(Izmjene izmjena) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		izmjena = em.merge(izmjena);
		em.remove(izmjena);
		em.getTransaction().commit();
	}
}
