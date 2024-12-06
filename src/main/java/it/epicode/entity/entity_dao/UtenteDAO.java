package it.epicode.entity.entity_dao;

import it.epicode.entity.Utente;
import it.epicode.entity.exceptions.UtenteNotFoundException;
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
        Utente utente = em.find(Utente.class, id);
        if (utente == null) {
            throw new UtenteNotFoundException("Utente con ID " + id + " non trovato.");
        }
        return utente;
    }

    public void deleteUtente(Long id) {
        em.getTransaction().begin();
        Utente utente = em.find(Utente.class, id);
        if (utente == null) {
            throw new UtenteNotFoundException("Utente con ID " + id + " non trovato.");
        }
        em.remove(utente);
        em.getTransaction().commit();
    }
}
