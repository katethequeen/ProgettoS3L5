package it.epicode.entity.entity_dao;

import it.epicode.entity.Prestito;
import it.epicode.entity.exceptions.PrestitoNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class PrestitoDAO {

    @PersistenceContext
    private EntityManager em;

    public PrestitoDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        this.em = emf.createEntityManager();
    }

    public void save(Prestito prestito) {
        em.getTransaction().begin();
        em.persist(prestito);
        em.getTransaction().commit();
    }

    public Prestito findPrestitoById(Long id) {

        Prestito prestito = em.find(Prestito.class, id);
        if (prestito == null) {
            throw new PrestitoNotFoundException("Prestito con ID " + id + " non trovato.");
        }
        return prestito;
    }

    public void update(Prestito prestito) {
        em.getTransaction().begin();
        if (em.find(Prestito.class, prestito.getId()) == null) {
            throw new PrestitoNotFoundException("Prestito con ID " + prestito.getId() + " non trovato.");
        }
        em.merge(prestito);
        em.getTransaction().commit();
    }

    public void deletePrestito(Long id) {
        em.getTransaction().begin();
        Prestito prestito = em.find(Prestito.class, id);
        if (prestito == null) {
            throw new PrestitoNotFoundException("Prestito con ID " + id + " non trovato.");
        }
        em.remove(prestito);
        em.getTransaction().commit();
    }
}
