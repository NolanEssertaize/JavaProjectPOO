import java.util.Random;

// Classe pour gérer le dé
class De {
    private Random random;
    
    public De() {
        this.random = new Random();
    }
    
    public int jeterDe() {
        return random.nextInt(6) + 1;
    }
}

// Classe représentant le joueur
class Joueur {
    private String nom;
    private int pointsDeVie;
    private De de;
    
    public Joueur(String nom) {
        this.nom = nom;
        this.pointsDeVie = 100;
        this.de = new De();
    }
    
    public boolean estVivant() {
        return pointsDeVie > 0;
    }
    
    public int jeterDe() {
        return de.jeterDe();
    }
    
    public void attaquer(MonstreNiveau1 monstre) {
        int jetJoueur = jeterDe();
        int jetMonstre = monstre.jeterDe();
        
        if (jetJoueur >= jetMonstre) {
            monstre.recoitDegat();
        }
    }
    
    public void recoitDegat(int degats) {
        int jetBouclier = jeterDe();
        if (jetBouclier > 2) {
            this.pointsDeVie -= degats;
        }
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getPointsDeVie() {
        return pointsDeVie;
    }
}

// Classe représentant un monstre de niveau 1
class MonstreNiveau1 {
    protected int degats;
    protected boolean vivant;
    protected De de;
    
    public MonstreNiveau1() {
        this.degats = 10;
        this.vivant = true;
        this.de = new De();
    }
    
    public int jeterDe() {
        return de.jeterDe();
    }
    
    public void attaquer(Joueur joueur) {
        if (vivant) {
            int jetMonstre = jeterDe();
            int jetJoueur = joueur.jeterDe();
            
            if (jetMonstre > jetJoueur) {
                joueur.recoitDegat(degats);
            }
        }
    }
    
    public void recoitDegat() {
        this.vivant = false;
    }
    
    public boolean estVivant() {
        return vivant;
    }
}

// Classe représentant un monstre de niveau 2
class MonstreNiveau2 extends MonstreNiveau1 {
    private int degatSort;
    
    public MonstreNiveau2() {
        super();
        this.degatSort = 5;
    }
    
    @Override
    public void attaquer(Joueur joueur) {
        // Attaque normale héritée du monstre niveau 1
        super.attaquer(joueur);
        
        // Sort magique
        if (vivant) {
            int jetSort = jeterDe();
            if (jetSort != 6) {
                joueur.recoitDegat(jetSort * degatSort);
            }
        }
    }
}

// Classe principale pour tester le jeu
public class Jeu {
    public static void main(String[] args) {
        Joueur joueur = new Joueur("Héros");
        Random random = new Random();
        int monstresNiv1Tues = 0;
        int monstresNiv2Tues = 0;
        
        while (joueur.estVivant()) {
            // Création d'un monstre aléatoire
            MonstreNiveau1 monstre;
            boolean estMonstreNiveau2 = random.nextBoolean();
            
            if (estMonstreNiveau2) {
                monstre = new MonstreNiveau2();
            } else {
                monstre = new MonstreNiveau1();
            }
            
            // Combat jusqu'à ce que le monstre soit mort ou le joueur mort
            while (monstre.estVivant() && joueur.estVivant()) {
                joueur.attaquer(monstre);
                if (!monstre.estVivant()) {
                    if (estMonstreNiveau2) {
                        monstresNiv2Tues++;
                    } else {
                        monstresNiv1Tues++;
                    }
                    break;
                }
                monstre.attaquer(joueur);
            }
        }
        
        // Affichage des résultats
        System.out.println(joueur.getNom() + " est mort...");
        System.out.println("Bravo, vous avez tué " + monstresNiv1Tues + 
                         " monstres de niveau 1 et " + monstresNiv2Tues + 
                         " monstres de niveau 2.");
        System.out.println("Vous avez gagné " + 
                         (monstresNiv1Tues + (monstresNiv2Tues * 2)) + " points.");
    }
}