public class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private static int nbLivres;

    public Livre(String titre, String auteur, int annee) throws AnneInvalideExeption{
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }

         if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        
        if( annee < 0 ){
            throw new AnneInvalideExeption("Année invalide: "+ annee);
        }
        this.nom = nom;
        this.auteur = auteur;
        this.annee = annee;
        nbLivres ++;
    }

    public Livre(int id, String titre, String auteur, int annee) {
        this(titre, auteur, annee);  // Appelle le premier constructeur pour valider
        this.id = id;                // Ajoute l'ID
    }
    
    public String getNom(){ return this.nom; }
    public String getAuteur(){ return this.auteur; }
    public int getAnnee(){ return this.annee; }
    public static int getNbLivres(){ return nbLivres; }

    public void setNom(String nom){ this.nom = nom; }
    public void setAuteur(String auteur){ this.auteur = auteur; }
    public void setAnnee(int annee){ this.annee = annee; }
    
}
