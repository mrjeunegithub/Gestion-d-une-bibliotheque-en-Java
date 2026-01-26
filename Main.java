import java.util.List;

public class Main {
    public static void main(String[] args) {
        LivreDAO dao = null;

        try {
            // 1. Initialisation du DAO
            System.out.println("Connexion à la base de données...");
            dao = new LivreDAO();

            // 2. Ajout de quelques livres de test
            System.out.println("\nAjout de livres...");
            try {
                dao.ajouterLivre("Le Petit Prince", "Antoine de Saint-Exupéry", 1943, "Conte");
                dao.ajouterLivre("L'Étranger", "Albert Camus", 1942); // Utilise le genre par défaut
                System.out.println("Livres ajoutés avec succès !");
            } catch (LivreException e) {
                System.err.println("Erreur métier lors de l'ajout : " + e.getMessage());
            }

            // 3. Test de la validation (doit déclencher une exception)
            System.out.println("\nTest de validation (Année négative) :");
            try {
                dao.ajouterLivre("Futur Livre", "Inconnu", -2024);
            } catch (LivreException e) {
                System.out.println("Succès du test : L'exception a bien été capturée : " + e.getMessage());
            }

            // 4. Affichage de tous les livres
            afficherListe(dao);

            // 5. Test de recherche par auteur
            System.out.println("\nRecherche des livres de 'Camus' :");
            List<Livre> resultats = dao.rechercherParAuteur("Camus");
            for (Livre l : resultats) {
                System.out.println("- " + l.getTitre() + " (" + l.getAnnee() + ")");
            }

        } catch (BaseDeDonneesException e) {
            System.err.println("ERREUR CRITIQUE BD : " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Cause : " + e.getCause().getMessage());
            }
        } finally {
            // 6. Toujours fermer la connexion
            if (dao != null) {
                dao.fermer();
                System.out.println("\nConnexion fermée.");
            }
        }
    }

    // Petite méthode utilitaire pour afficher la liste proprement
    private static void afficherListe(LivreDAO dao) throws BaseDeDonneesException {
        System.out.println("\n═══════════════ LISTE DES LIVRES EN BASE ═══════════════");
        List<Livre> catalogue = dao.getTousLesLivres();
        if (catalogue.isEmpty()) {
            System.out.println("La base est vide.");
        } else {
            for (Livre l : catalogue) {
                System.out.printf("ID: %d | %-30s | %-25s | %d | %s%n", 
                    l.getId(), l.getTitre(), l.getAuteur(), l.getAnnee(), l.getGenre());
            }
        }
        System.out.println("════════════════════════════════════════════════════════\n");
    }
}