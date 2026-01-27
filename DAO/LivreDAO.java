package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Livre;
import exceptions.BaseDeDonneesException;
import exceptions.LivreException;
import exceptions.AnneeInvalideException;
import exceptions.LivreNonTrouveException;

public class LivreDAO {
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
            Livre livre = new Livre(titre, auteur, annee, genre);
           
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO livres (titre, auteur, annee, genre) VALUES (?, ?, ?, ?)"
            );
           
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getAnnee());
            ps.setString(4, livre.getGenre());
           
            ps.executeUpdate();
            ps.close();
           
        } catch (IllegalArgumentException e) {
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
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM livres");
           
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
            
            rs.close();
            stmt.close();
            return livres;
           
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la récupération des livres", e);
        }
    }

    public Livre getLivreParId(int id) throws LivreException {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM livres WHERE id = ?");
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Livre livre = new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getInt("annee"),
                    rs.getString("genre")
                );
                rs.close();
                ps.close();
                return livre;
            } else {
                rs.close();
                ps.close();
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la récupération du livre", e);
        }
    }

    public void supprimerLivre(int id) throws LivreException {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM livres WHERE id = ?");
            ps.setInt(1, id);
           
            int rowsAffected = ps.executeUpdate();
            ps.close();
           
            if (rowsAffected == 0) {
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
           
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la suppression du livre", e);
        }
    }

    public void modifierLivre(int id, String nouveauTitre, String nouvelAuteur, int nouvelleAnnee, String nouveauGenre) throws LivreException {
        try {
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
            ps.close();
            
            if (rowsAffected == 0) {
                throw new LivreNonTrouveException("Aucun livre trouvé avec l'ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            throw new LivreException("Modification invalide : " + e.getMessage());
        } catch (AnneeInvalideException e) {
            throw e;
        } catch (SQLException e) {
            throw new BaseDeDonneesException("Erreur lors de la modification", e);
        }
    }

    public List<Livre> rechercherParAuteur(String auteur) throws BaseDeDonneesException {
    try {
        List<Livre> livres = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM livres WHERE auteur LIKE ?"
        );
        ps.setString(1, "%" + auteur + "%");
        
        ResultSet rs = ps.executeQuery();
        
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
        
        rs.close();
        ps.close();
        return livres;
        
    } catch (SQLException e) {
        throw new BaseDeDonneesException("Erreur lors de la recherche par auteur", e);
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