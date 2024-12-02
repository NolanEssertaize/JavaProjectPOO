package TPGestionCompte;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GestionDeComptes gestion = new GestionDeComptes();
    
    public static void main(String[] args) {
        int choix;
        do {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer
            
            switch (choix) {
                case 1:
                    creerCompteCourant();
                    break;
                case 2:
                    creerCompteEpargne();
                    break;
                case 3:
                    crediterCompte();
                    break;
                case 4:
                    debiterCompte();
                    break;
                case 5:
                    effectuerVirement();
                    break;
                case 6:
                    gestion.afficherComptes();
                    break;
                case 7:
                    gestion.trierComptes();
                    System.out.println("Comptes triés par solde :");
                    gestion.afficherComptes();
                    break;
                case 8:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        } while (choix != 8);
    }
    
    private static void afficherMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Créer un compte courant");
        System.out.println("2. Créer un compte épargne");
        System.out.println("3. Créditer un compte");
        System.out.println("4. Débiter un compte");
        System.out.println("5. Effectuer un virement");
        System.out.println("6. Afficher la liste des comptes");
        System.out.println("7. Trier les comptes");
        System.out.println("8. Quitter");
        System.out.print("Votre choix : ");
    }
    
    private static void creerCompteCourant() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        System.out.print("Découvert autorisé : ");
        double decouvert = scanner.nextDouble();
        System.out.print("Dépôt initial : ");
        double depotInitial = scanner.nextDouble();
        
        try {
            gestion.ajouterCompte(new CompteCourant(nom, decouvert, depotInitial));
            System.out.println("Compte courant créé avec succès");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
    private static void creerCompteEpargne() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        System.out.print("Taux d'abondement (en %) : ");
        double taux = scanner.nextDouble() / 100;
        System.out.print("Dépôt initial : ");
        double depotInitial = scanner.nextDouble();
        
        try {
            gestion.ajouterCompte(new CompteEpargne(nom, taux, depotInitial));
            System.out.println("Compte épargne créé avec succès");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void crediterCompte() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        Compte compte = gestion.rechercherCompte(nom);
        
        if (compte != null) {
            System.out.print("Montant à créditer : ");
            double montant = scanner.nextDouble();
            compte.crediter(montant);
            System.out.println("Crédit effectué avec succès");
        } else {
            System.out.println("Compte non trouvé");
        }
    }
    
    private static void debiterCompte() {
        System.out.print("Nom du propriétaire : ");
        String nom = scanner.nextLine();
        Compte compte = gestion.rechercherCompte(nom);
        
        if (compte != null) {
            System.out.print("Montant à débiter : ");
            double montant = scanner.nextDouble();
            compte.debiter(montant);
            System.out.println("Débit effectué avec succès");
        } else {
            System.out.println("Compte non trouvé");
        }
    }
    
    private static void effectuerVirement() {
        System.out.print("Nom du propriétaire source : ");
        String nomSource = scanner.nextLine();
        System.out.print("Nom du propriétaire destinataire : ");
        String nomDest = scanner.nextLine();
        
        Compte source = gestion.rechercherCompte(nomSource);
        Compte dest = gestion.rechercherCompte(nomDest);
        
        if (source != null && dest != null) {
            System.out.print("Montant du virement : ");
            double montant = scanner.nextDouble();
            try {
                source.debiter(montant, dest);
                System.out.println("Virement effectué avec succès");
            } catch (IllegalStateException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("Un des comptes n'a pas été trouvé");
        }
    }
}