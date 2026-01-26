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
        
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.genre = (genre == null || genre.trim().isEmpty()) ? "non spécifié" : genre.trim();
        nbLivres++;
    }

    public Livre(int id, String titre, String auteur, int annee, String genre) throws AnneeInvalideExeption {
        this(titre, auteur, annee, genre); // Appelle le constructeur complet
        this.id = id; 
    }
    
    public int getId() { return this.id; }
    public String getTitre(){ return this.titre; }
    public String getAuteur(){ return this.auteur; }
    public int getAnnee(){ return this.annee; }
    public String getGenre() { return this.genre;}
    public static int getNbLivres(){ return nbLivres; }

    public void settitre(String titre){ this.titre = titre; }
    public void setAuteur(String auteur){ this.auteur = auteur; }
    public void setAnnee(int annee){ this.annee = annee; }
    public void setGenre(String genre) { this.genre = (genre == null || genre.trim().isEmpty()) ? "non spécifié" : genre; }
    
}
