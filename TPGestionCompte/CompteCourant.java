package TPGestionCompte;
public class CompteCourant extends Compte {
    private double decouvertAutorise;
    
    public CompteCourant(String proprietaire, double decouvertAutorise, double depotInitial) {
        super(proprietaire, depotInitial);
        if (decouvertAutorise < 0) {
            throw new IllegalArgumentException("Le découvert autorisé doit être positif");
        }
        this.decouvertAutorise = decouvertAutorise;
    }
    
    @Override
    protected boolean peutDebiter(double montant) {
        return (calculSolde() - montant) >= -decouvertAutorise;
    }
    
    @Override
    public double calculBenefice() {
        return 0;
    }
    
    @Override
    public void information() {
        System.out.println("Résumé du compte courant de " + proprietaire);
        System.out.println("*******************************************");
        System.out.printf("Solde : %.2f\n", calculSolde());
        System.out.printf("Découvert autorisé : %.2f\n", decouvertAutorise);
        System.out.printf("Solde minimum possible : %.2f\n", -decouvertAutorise);
        System.out.println("Opérations :");
        for (Operation op : operations) {
            System.out.println(op);
        }
        System.out.println("*******************************************");
    }
}