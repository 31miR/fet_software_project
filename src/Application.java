import java.util.List;

import javax.persistence.*;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
	     EntityManager em = emf.createEntityManager();
	     
	     List<Product> results = em.createQuery("SELECT a FROM Product a", Product.class).getResultList();
	     
	     System.out.println(results.size());
	     for (Product i : results) {
	    	 System.out.println("" + i.getId() + ", " + i.getName() + ", " + i.getPrice());
	     }
	     
	     long nextId = results.size() + 1L;
	     // Create and persist a new entity
	     em.getTransaction().begin();
	     Product product = new Product(nextId, "Laptop", 2.5);
	     em.persist(product);
	     em.getTransaction().commit();

	     emf.close();
	     em.close();
	}

}
