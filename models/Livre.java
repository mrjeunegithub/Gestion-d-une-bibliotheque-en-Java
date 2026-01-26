public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int annee;
    private String genre;
    private static int nbLivres = 0;

    public Livre(String titre, String auteur, int annee, String genre) throws AnneeInvalideExeption {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        if (annee < 0) {
            throw new AnneeInvalideExeption("Année invalide: " + annee);
        }
        
        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.annee = annee;
        this.genre = (genre == null || genre.trim().isEmpty()) ? "non spécifié" : genre.trim();
        nbLivres++;
    }

    public Livre(int id, String titre, String auteur, int annee, String genre) throws AnneeInvalideExeption {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        if (annee < 0) {
            throw new AnneeInvalideExeption("Année invalide: " + annee);
        }
        
        this.id = id;
        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.annee = annee;
        this.genre = (genre == null || genre.trim().isEmpty()) ? "non spécifié" : genre.trim();

    }
    
    public int getId() { return this.id; }
    public String getTitre() { return this.titre; }
    public String getAuteur() { return this.auteur; }
    public int getAnnee() { return this.annee; }
    public String getGenre() { return this.genre; }
    public static int getNbLivres() { return nbLivres; }

    public void setTitre(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        this.titre = titre.trim();
    }
    
    public void setAuteur(String auteur) {
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        this.auteur = auteur.trim();
    }
    
    public void setAnnee(int annee) {
        if (annee < 0) {
            throw new IllegalArgumentException("L'année ne peut pas être négative");
        }
        this.annee = annee;
    }
    
    public void setGenre(String genre) {
        this.genre = (genre == null || genre.trim().isEmpty()) ? "non spécifié" : genre.trim();
    }
    
    @Override
    public String toString() {
        return "Livre{id=" + id + ", titre='" + titre + "', auteur='" + auteur + 
               "', année=" + annee + ", genre='" + genre + "'}";
    }
}
