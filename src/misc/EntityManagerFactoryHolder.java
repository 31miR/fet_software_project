package misc;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryHolder {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}
