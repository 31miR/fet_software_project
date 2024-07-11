import javax.persistence.*;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
	     EntityManager em = emf.createEntityManager();

	     // Create and persist a new entity
	     em.getTransaction().begin();
	     Product product = new Product(1L, "Laptop", 2.5);
	     em.persist(product);
	     em.getTransaction().commit();

	     emf.close();
	     em.close();
	}

}
