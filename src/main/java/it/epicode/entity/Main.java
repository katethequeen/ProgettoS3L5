package it.epicode.entity;

import it.epicode.entity.entity_dao.CatalogoDAO;
import it.epicode.entity.entity_dao.PrestitoDAO;
import it.epicode.entity.entity_dao.UtenteDAO;
import it.epicode.entity.entity_enum.Periodicita;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {


    private static final CatalogoDAO catalogoService = new CatalogoDAO();
    private static final UtenteDAO utenteService = new UtenteDAO();
    private static final PrestitoDAO prestitoService = new PrestitoDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Aggiungi un elemento al catalogo");
            System.out.println("2. Rimuovi un elemento dal catalogo per ISBN");
            System.out.println("3. Cerca un elemento per ISBN");
            System.out.println("4. Cerca elementi per anno di pubblicazione");
            System.out.println("5. Cerca libri per autore");
            System.out.println("6. Cerca elementi per titolo");
            System.out.println("7. Cerca prestiti per numero di tessera utente");
            System.out.println("8. Cerca prestiti scaduti e non restituiti");
            System.out.println("9. Aggiungi utente");
            System.out.println("10. Aggiungi un prestito");
            System.out.println("11. Restituisci un prestito");
            System.out.println("12. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    aggiungiElemento();
                    break;
                case 2:
                    rimuoviElemento();
                    break;
                case 3:
                    cercaPerIsbn();
                    break;
                case 4:
                    cercaPerAnno();
                    break;
                case 5:
                    cercaPerAutore();
                    break;
                case 6:
                    cercaPerTitolo();
                    break;
                case 7:
                    cercaPrestitiPerNumeroTessera();
                    break;
                case 8:
                    cercaPrestitiScaduti();
                    break;
                case 9:
                    aggiungiUtente();
                    break;
                case 10:
                    aggiungiPrestito();
                    break;
                case 11:
                    restituisciPrestito();
                    break;
                case 12:
                    System.out.println("Uscita...");
                    return;
                default:
                    System.out.println("Scelta non valida, riprova.");
                    break;
            }
        }
    }

    private static void aggiungiElemento() {
        System.out.println("Scegli il tipo di elemento: 1. Libro 2. Rivista");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        System.out.print("Codice ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();

        System.out.print("Anno di Pubblicazione: ");
        int anno = scanner.nextInt();

        System.out.print("Numero di Pagine: ");
        int pagine = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        if (tipo == 1) {
            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitolo(titolo);
            libro.setAnnoPubblicazione(anno);
            libro.setNumeroPagine(pagine);

            System.out.print("Autore: ");
            libro.setAutore(scanner.nextLine());

            System.out.print("Genere: ");
            libro.setGenere(scanner.nextLine());

            catalogoService.save(libro);
            System.out.println("Libro aggiunto con successo!");

        } else if (tipo == 2) {
            Rivista rivista = new Rivista();
            rivista.setIsbn(isbn);
            rivista.setTitolo(titolo);
            rivista.setAnnoPubblicazione(anno);
            rivista.setNumeroPagine(pagine);

            System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            rivista.setPeriodicita(Periodicita.valueOf(scanner.nextLine().toUpperCase()));

            catalogoService.save(rivista);
            System.out.println("Rivista aggiunta con successo!");
        } else {
            System.out.println("Tipo non valido.");
        }
    }

    private static void rimuoviElemento() {
        System.out.print("Inserisci il codice ISBN dell'elemento da rimuovere: ");
        String isbn = scanner.nextLine();
        catalogoService.deleteByIsbn(isbn);
        System.out.println("Elemento rimosso con successo!");
    }

    private static void cercaPerIsbn() {
        System.out.print("Inserisci il codice ISBN: ");
        String isbn = scanner.nextLine();
        Catalogo elemento = catalogoService.findByIsbn(isbn);
        if (elemento != null) {
            System.out.println(elemento);
        } else {
            System.out.println("Elemento non trovato.");
        }
    }

    private static void cercaPerAnno() {
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        List<Catalogo> elementi = catalogoService.searchByAnnoPubblicazione(anno);
        if (elementi.isEmpty()) {
            System.out.println("Nessun elemento trovato.");
        } else {
            elementi.forEach(System.out::println);
        }
    }

    private static void cercaPerAutore() {
        System.out.print("Inserisci l'autore: ");
        String autore = scanner.nextLine();
        List<Libro> libri = catalogoService.searchByAutore(autore);
        if (libri.isEmpty()) {
            System.out.println("Nessun libro trovato.");
        } else {
            libri.forEach(System.out::println);
        }
    }

    private static void cercaPerTitolo() {
        System.out.print("Inserisci il titolo o parte di esso: ");
        String titolo = scanner.nextLine();
        List<Catalogo> elementi = catalogoService.searchByTitolo(titolo);
        if (elementi.isEmpty()) {
            System.out.println("Nessun elemento trovato.");
        } else {
            elementi.forEach(System.out::println);
        }
    }

    private static void cercaPrestitiPerNumeroTessera() {
        System.out.print("Inserisci il numero di tessera: ");
        String numeroTessera = scanner.nextLine();
        List<Prestito> prestiti = catalogoService.searchPrestitiByNumeroTessera(numeroTessera);
        if (prestiti.isEmpty()) {
            System.out.println("Nessun prestito trovato.");
        } else {
            prestiti.forEach(System.out::println);
        }
    }

    private static void cercaPrestitiScaduti() {
        List<Prestito> prestitiScaduti = catalogoService.searchPrestitiScaduti();
        if (prestitiScaduti.isEmpty()) {
            System.out.println("Nessun prestito scaduto trovato.");
        } else {
            prestitiScaduti.forEach(System.out::println);
        }
    }

    private static void aggiungiUtente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Data di Nascita: ");
        String dataDiNascita = scanner.nextLine();
        System.out.print("Numero di Tessera: ");
        String numeroDiTessera = scanner.nextLine();
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setDataDiNacita(dataDiNascita);
        utente.setNumeroDiTessera(numeroDiTessera);
        utenteService.addUtente(utente);
        System.out.println("Utente aggiunto con successo!");
    }

    private static void aggiungiPrestito() {
        System.out.print("ID Utente: ");
        Long utenteId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Codice ISBN dell'elemento: ");
        String isbn = scanner.nextLine();
        scanner.nextLine();
        Utente utente = utenteService.findUtenteById(utenteId);
        Catalogo elemento = catalogoService.findByIsbn(isbn);
        if (utente == null) {
            System.out.println("Utente non trovato.");
            return;
        }
        if (elemento == null) {
            System.out.println("Elemento non trovato.");
            return;
        }
        Prestito prestito = new Prestito();
        prestito.setUtente(utente);
        prestito.setElementoCatalogo(elemento);
        prestito.setDataInizioPrestito(LocalDate.now());
        prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));
        prestitoService.save(prestito);
        System.out.println("Prestito aggiunto con successo!");
    }

    private static void restituisciPrestito() {
        System.out.print("ID del Prestito: ");
        Long prestitoId = scanner.nextLong();
        scanner.nextLine();
        Prestito prestito = prestitoService.findPrestitoById(prestitoId);
        if (prestito == null) {
            System.out.println("Prestito non trovato.");
            return;
        }
        prestito.setDataRestituzioneEffettiva(LocalDate.now());
        prestitoService.update(prestito);
        System.out.println("Prestito restituito con successo!");
    }
}


