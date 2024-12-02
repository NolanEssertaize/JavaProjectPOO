package TPGestionCompte;
import java.util.List;
import java.util.ArrayList;

public abstract class Compte implements Comparable<Compte> {
    protected List<Operation> operations;
    protected String proprietaire;
    
    public Compte(String proprietaire, double depotInitial) {
        if (depotInitial < 0) {
            throw new IllegalArgumentException("Le dépôt initial ne peut pas être négatif");
        }
        this.proprietaire = proprietaire;
        this.operations = new ArrayList<>();
        if (depotInitial > 0) {
            this.crediter(depotInitial);
        }
    }
    
    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du crédit doit être positif");
        }
        operations.add(new Operation(montant, Mouvement.CREDIT));
    }
    
    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant du débit doit être positif");
        }
        if (!peutDebiter(montant)) {
            throw new IllegalStateException("Solde insuffisant pour effectuer cette opération");
        }
        operations.add(new Operation(montant, Mouvement.DEBIT));
    }
    
    public void crediter(double montant, Compte compteADebiter) {
        if (!compteADebiter.peutDebiter(montant)) {
            throw new IllegalStateException("Le compte source n'a pas les fonds suffisants");
        }
        this.crediter(montant);
        compteADebiter.debiter(montant);
    }
    
    public void debiter(double montant, Compte compteACrediter) {
        if (!this.peutDebiter(montant)) {
            throw new IllegalStateException("Solde insuffisant pour effectuer ce virement");
        }
        this.debiter(montant);
        compteACrediter.crediter(montant);
    }
    
    protected abstract boolean peutDebiter(double montant);
    
    public double calculSolde() {
        double solde = 0;
        for (Operation op : operations) {
            String strMontant = op.toString();
            if (strMontant.startsWith("+")) {
                solde += Double.parseDouble(strMontant.substring(1).replace(",", "."));
            } else {
                solde -= Double.parseDouble(strMontant.substring(1).replace(",", "."));
            }
        }
        return solde;
    }
    
    public abstract double calculBenefice();
    
    public double soldeFinal() {
        return calculSolde() + calculBenefice();
    }
    
    public abstract void information();
    
    @Override
    public int compareTo(Compte autre) {
        return Double.compare(this.soldeFinal(), autre.soldeFinal());
    }
    
    public String getProprietaire() {
        return proprietaire;
    }
}