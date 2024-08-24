package model;

import misc.EntityManagerFactoryHolder;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class SektorDAO {
    EntityManagerFactory emf = EntityManagerFactoryHolder.getEntityManagerFactory();

    public void addSektor(Sektor sektor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(sektor);
        em.getTransaction().commit();
        em.close();
    }

    public List<Sektor> getAllSectors() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Sektor> query = em.createQuery("SELECT s FROM Sektor s", Sektor.class);
        List<Sektor> sektori = query.getResultList();
        em.close();
        return sektori;
    }
}