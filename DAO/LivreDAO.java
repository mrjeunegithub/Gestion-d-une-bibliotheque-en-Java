import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LivreDAO {
    private Connection conn;

    public LivreDAO() throws BaseDeDonneesException {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:livres.db");
           
            conn.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS livres (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titre TEXT NOT NULL," +
                "auteur TEXT NOT NULL," +
                "annee INTEGER NOT NULL," +
                "genre TEXT DEFAULT 'non spécifié')"
            );
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la connexion à la base de données", e);
        }
    }

    public void ajouterLivre(String titre, String auteur, int annee) throws LivreException {
        ajouterLivre(titre, auteur, annee, "non spécifié");
    }

    public void ajouterLivre(String titre, String auteur, int annee, String genre) throws LivreException {
        try {
            // Création de l'objet Livre (Valide titre/auteur via IllegalArgumentException et année via AnneeInvalideException)
            Livre livre = new Livre(titre, auteur, annee, genre);
           
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO livres (titre, auteur, annee, genre) VALUES (?, ?, ?, ?)"
            );
           
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getAnnee());
            ps.setString(4, livre.getGenre());
           
            ps.executeUpdate();
           
        } catch (IllegalArgumentException e) {
            // On attrape les erreurs de titre/auteur vides et on les gère ou les relance
            throw new LivreException("Données du livre invalides : " + e.getMessage());
        } catch (AnneeInvalideException e) {
            throw e; 
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de l'ajout du livre", e);
        }
    }

    public List<Livre> getTousLesLivres() throws BaseDeDonneesException {
        try {
            List<Livre> livres = new ArrayList<>();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM livres");
           
            while (rs.next()) {
                try {
                    livres.add(new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("annee"),
                        rs.getString("genre")
                    ));
                } catch (LivreException | IllegalArgumentException e) {
                    System.err.println("Livre invalide en base (ID: " + rs.getInt("id") + "): " + e.getMessage());
                }
            }
            return livres;
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la récupération des livres", e);
        }
    }

    // Méthode pour ajouter un livre dans la base de données
    public void ajouterLivre(String titre, String auteur, int annee) throws LivreException {
        ajouterLivre(titre, auteur, annee, "non spécifié");  // Appelle la méthode avec genre par défaut
    }

    // Méthode pour ajouter un livre avec genre dans la base de données
    public void ajouterLivre(String titre, String auteur, int annee, String genre) throws LivreException {
        try {
            // Crée un objet Livre (qui valide automatiquement les données)
            Livre livre = new Livre(titre, auteur, annee, genre);
           
            // Prépare la requête SQL avec des paramètres (? = placeholder)
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO livres (titre, auteur, annee, genre) VALUES (?, ?, ?, ?)"
            );
           
            // Remplace les ? par les vraies valeurs
            ps.setString(1, livre.getTitre());   // 1er ? = titre
            ps.setString(2, livre.getAuteur());  // 2ème ? = auteur
            ps.setInt(3, livre.getAnnee());      // 3ème ? = annee
            ps.setString(4, livre.getGenre());   // 4ème ? = genre
           
            // Exécute la requête d'insertion
            ps.executeUpdate();
           
        } catch (IllegalArgumentException | AnneeInvalideException e) {
            // Si la validation échoue, on relance l'exception telle quelle
            throw e;
        } catch (SQLException e) {
            // Si l'insertion échoue, on relance avec notre exception personnalisée
            throw new BaseDeDonneesException("Erreur lors de l'ajout du livre", e);
        }
    }

    // Méthode pour récupérer tous les livres de la base de données
    public List<Livre> getTousLesLivres() throws BaseDeDonneesException {
        try {
            // Crée une liste vide pour stocker les livres
            List<Livre> livres = new ArrayList<>();
           
            // Exécute la requête SELECT pour récupérer tous les livres
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM livres");
           
            // Parcourt chaque ligne du résultat
            while (rs.next()) {
                try {
                    // Crée un objet Livre à partir des données de la ligne
                    livres.add(new Livre(
                        rs.getInt("id"),           // Récupère l'ID
                        rs.getString("titre"),     // Récupère le titre
                        rs.getString("auteur"),    // Récupère l'auteur
                        rs.getInt("annee"),        // Récupère l'année
                        rs.getString("genre")      // Récupère le genre
                    ));
                } catch (LivreException e) {
                    // Si un livre en BD est invalide, on continue mais on pourrait logger l'erreur
                    System.err.println("Livre invalide en base de données (ID: " + rs.getInt("id") + "): " + e.getMessage());
                }
            }
           
            // Retourne la liste complète
            return livres;
           
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la récupération des livres", e);
        }
    }

    // Méthode pour récupérer un livre par son ID
    public Livre getLivreParId(int id) throws LivreException {
        try {
            // Prépare la requête SELECT avec un paramètre
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM livres WHERE id = ?");
            ps.setInt(1, id);
            
            // Exécute la requête
            ResultSet rs = ps.executeQuery();
            
            // Si un livre est trouvé
            if (rs.next()) {
                return new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("annee"),
                    rs.getString("genre")
                );
            } else {
                // Si aucun livre n'est trouvé
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la récupération du livre", e);
        }
    }

    // Méthode pour supprimer un livre par son ID
    public void supprimerLivre(int id) throws LivreException {
        try {
            // Prépare la requête DELETE avec un paramètre
            PreparedStatement ps = conn.prepareStatement("DELETE FROM livres WHERE id = ?");
            ps.setInt(1, id);  // Remplace le ? par l'ID
           
            // Exécute la suppression et récupère le nombre de lignes affectées
            int rowsAffected = ps.executeUpdate();
           
            // Si aucune ligne n'a été supprimée, le livre n'existe pas
            if (rowsAffected == 0) {
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
           
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la suppression du livre", e);
        }
    }

    public void modifierLivre(int id, String nouveauTitre, String nouvelAuteur, int nouvelleAnnee, String nouveauGenre) throws LivreException {
        try {
            // Validation via création temporaire
            Livre livreTemp = new Livre(nouveauTitre, nouvelAuteur, nouvelleAnnee, 
                                        nouveauGenre != null ? nouveauGenre : "non spécifié");
            
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE livres SET titre = ?, auteur = ?, annee = ?, genre = ? WHERE id = ?"
            );
            
            ps.setString(1, livreTemp.getTitre());
            ps.setString(2, livreTemp.getAuteur());
            ps.setInt(3, livreTemp.getAnnee());
            ps.setString(4, livreTemp.getGenre());
            ps.setInt(5, id);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            throw new LivreException("Modification invalide : " + e.getMessage());
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la modification", e);
        }
    }

    public void fermer() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture: " + e.getMessage());
        }
    }
}