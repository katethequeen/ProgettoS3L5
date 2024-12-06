package it.epicode.entity.entity_dao;

import it.epicode.entity.Catalogo;
import it.epicode.entity.Libro;
import it.epicode.entity.Prestito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;


public class CatalogoDAO {

    @PersistenceContext
    private EntityManager em;

    public CatalogoDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        this.em = emf.createEntityManager();
    }

    public void save(Catalogo elementoCatalogo) {
        em.getTransaction().begin();
        em.persist(elementoCatalogo);
        em.getTransaction().commit();
    }

    public Catalogo findByIsbn(String isbn) {

        return em.createQuery("SELECT e FROM Catalogo e WHERE e.isbn = :isbn", Catalogo.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    public List<Catalogo> searchByAnnoPubblicazione(int anno) {
        return em.createQuery("SELECT e FROM Catalogo e WHERE e.annoPubblicazione = :anno", Catalogo.class).setParameter("anno", anno).getResultList();
    }

    public List<Libro> searchByAutore(String autore) {
        return em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class).setParameter("autore", autore).getResultList();
    }

    public List<Catalogo> searchByTitolo(String titolo) {
        return em.createQuery("SELECT e FROM Catalogo e WHERE e.titolo LIKE :titolo", Catalogo.class).setParameter("titolo", "%" + titolo + "%").getResultList();
    }

    public List<Prestito> searchPrestitiByNumeroTessera(String numeroTessera) {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroDiTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", Prestito.class).setParameter("numeroTessera", numeroTessera).getResultList();
    }

    public List<Prestito> searchPrestitiScaduti() {
        return em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL", Prestito.class).setParameter("oggi", LocalDate.now()).getResultList();
    }

    public void deleteByIsbn(String isbn) {
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Catalogo e WHERE e.isbn = :isbn");
        query.setParameter("isbn", isbn);
        query.executeUpdate();
        em.getTransaction().commit();
    }


}
