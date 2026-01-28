import java.util.List;
import java.util.Scanner;

import models.Livre;
import DAO.LivreDAO;
import exceptions.BaseDeDonneesException;
import exceptions.LivreException;
import exceptions.LivreNonTrouveException;

public class Main {

    public static void main(String[] args) {
        LivreDAO dao = null;
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        try {
            // Initialisation
            System.out.println("Connexion à la base de données...");
            dao = new LivreDAO();
            System.out.println("Connexion réussie.\n");

            // Boucle principale du menu
            while (!quitter) {
                afficherMenu();
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // consomme le retour à la ligne

                switch (choix) {
                    case 1:
                        ajouterLivre(scanner, dao);
                        break;

                    case 2:
                        supprimerLivre(scanner, dao);
                        break;

                    case 3:
                        afficherListe(dao);
                        break;

                    case 4:
                        afficherNombreLivres(dao);
                        break;

                    case 5:
                        quitter = true;
                        System.out.println("Au revoir !");
                        break;

                    default:
                        System.out.println("Choix invalide. Réessayez.");
                }
            }

        } catch (BaseDeDonneesException e) {
            System.err.println("ERREUR CRITIQUE BD : " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cause : " + e.getCause().getMessage());
            }
        } finally {
            if (dao != null) {
                dao.fermer();
            }
            scanner.close();
        }
    }

    //MENU
    private static void afficherMenu() {
        System.out.println("******* MENU BIBLIOTHÈQUE *******");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Supprimer un livre");
        System.out.println("3. Afficher la liste des livres");
        System.out.println("4. Afficher le nombre total de livres");
        System.out.println("5. Quitter");
        System.out.println("*****************************");
    }

    //AJOUT
    private static void ajouterLivre(Scanner scanner, LivreDAO dao) {
        try {
            System.out.print("Titre : ");
            String titre = scanner.nextLine();

            System.out.print("Auteur : ");
            String auteur = scanner.nextLine();

            System.out.print("Année : ");
            int annee = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Genre (laisser vide pour défaut) : ");
            String genre = scanner.nextLine();

            if (genre.isEmpty()) {
                dao.ajouterLivre(titre, auteur, annee);
            } else {
                dao.ajouterLivre(titre, auteur, annee, genre);
            }

            System.out.println("Livre ajouté avec succès !");

        } catch (LivreException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    // SUPPRESSION
    private static void supprimerLivre(Scanner scanner, LivreDAO dao) {
        try {
            System.out.print("ID du livre à supprimer : ");
            int id = scanner.nextInt();
            scanner.nextLine();

            dao.supprimerLivre(id);
            System.out.println("Livre supprimé avec succès.");

        } catch (LivreNonTrouveException e) {
            System.err.println(e.getMessage());
        } catch (LivreException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    // AFFICHAGE
    private static void afficherListe(LivreDAO dao) throws BaseDeDonneesException {
        List<Livre> livres = dao.getTousLesLivres();

        System.out.println("\n****** LISTE DES LIVRES ******");
        if (livres.isEmpty()) {
            System.out.println("Aucun livre enregistré.");
        } else {
            for (Livre l : livres) {
                System.out.printf(
                    "ID: %d | %s | %s | %d | %s%n",
                    l.getId(),
                    l.getTitre(),
                    l.getAuteur(),
                    l.getAnnee(),
                    l.getGenre()
                );
            }
        }
        System.out.println("*****************************\n");
    }

    // COMPTAGE
    private static void afficherNombreLivres(LivreDAO dao) throws BaseDeDonneesException {
        int total = dao.getTousLesLivres().size();
        System.out.println("Nombre total de livres : " + total);
    }
}
