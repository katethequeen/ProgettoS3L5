package it.epicode.entity.entity_dao;

import it.epicode.entity.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;



public class UtenteDAO {
    @PersistenceContext
    private EntityManager em;

    public UtenteDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        this.em = emf.createEntityManager();
    }

    public void addUtente(Utente utente) {
        em.getTransaction().begin();
        em.persist(utente);
        em.getTransaction().commit();
    }

    public Utente findUtenteById(Long id) {
        return em.find(Utente.class, id);
    }

    public void deleteUtente(Long id) {
        em.getTransaction().begin();
        Utente utente = em.find(Utente.class, id);
        if (utente != null) {
            em.remove(utente);
        }
        em.getTransaction().commit();
    }
}
