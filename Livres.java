public class Livres {
    private String nom;
    private String auteur;
    private int annee;
    private static int nbLivres;

    public Livres(String nom, String auteur, int annee) throws AnneInvalideExeption{
        if( annee < 0 ){
            throw new AnneInvalideExeption("Année invalide: "+ annee);
        }
        this.nom = nom;
        this.auteur = auteur;
        this.annee = annee;
        nbLivres ++;
    }

    public String getNom(){
        return this.nom;
    }
    public String getAuteur(){
        return this.auteur;
    }
    public int getAnnee(){
        return this.annee;
    }
    public static int getNbLivres(){
        return nbLivres;
    }

    public void setNom(String nom){
        this.nom = nom;
    }
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }
    public void setAnnee(int annee){
        this.annee = annee;
    }
    
}
