package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import misc.EntityManagerFactoryHolder;

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
    
     public Sektor getSektorById(int sektorId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Sektor.class, sektorId);
        } finally {
            em.close();
        }
    }
    
     public void updateSektor(Sektor sektor) {
         EntityManager em = emf.createEntityManager();
         try {
             em.getTransaction().begin();
             em.merge(sektor);
             em.getTransaction().commit();
         } catch (Exception e) {
             if (em.getTransaction().isActive()) {
                 em.getTransaction().rollback();
             }
             e.printStackTrace();
         } finally {
             em.close();
         }
     }
    
     public List<Sektor> getAllSectorsInLokacija(Lokacija lokacija) {
    	 EntityManager em = emf.createEntityManager();
    	 List<Sektor> ret = em.createQuery("SELECT a FROM Sektor a WHERE a.lokacija = :lokacija", Sektor.class)
    			 			.setParameter("lokacija", lokacija).getResultList();
    	 em.close();
    	 return ret;
     }
}