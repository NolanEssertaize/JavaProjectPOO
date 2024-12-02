package TPGestionCompte;
public class CompteEpargne extends Compte {
    private double tauxAbondement;
    
    public CompteEpargne(String proprietaire, double tauxAbondement, double depotInitial) {
        super(proprietaire, depotInitial);
        if (tauxAbondement < 0) {
            throw new IllegalArgumentException("Le taux d'abondement doit être positif");
        }
        this.tauxAbondement = tauxAbondement;
    }
    
    @Override
    protected boolean peutDebiter(double montant) {
        return calculSolde() >= montant;
    }
    
    @Override
    public double calculBenefice() {
        return calculSolde() * tauxAbondement;
    }
    
    @Override
    public void information() {
        System.out.println("Résumé du compte épargne de " + proprietaire);
        System.out.println("*******************************************");
        System.out.printf("Solde : %.2f\n", calculSolde());
        System.out.printf("Bénéfices : %.2f\n", calculBenefice());
        System.out.printf("Solde final : %.2f\n", soldeFinal());
        System.out.printf("Taux d'abondement : %.1f %%\n", tauxAbondement * 100);
        System.out.println("Opérations :");
        for (Operation op : operations) {
            System.out.println(op);
        }
        System.out.println("*******************************************");
    }
}