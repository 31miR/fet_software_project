package misc;

import javax.persistence.*;

public class EntityManagerFactoryHolder {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
}
