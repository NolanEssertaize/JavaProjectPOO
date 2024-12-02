package TPGestionCompte;
public class Operation {
    private double montant;
    private Mouvement type;
    
    public Operation() {}
    
    public Operation(double montant, Mouvement type) {
        this.montant = montant;
        this.type = type;
    }
    
    public String toString() {
        return (type == Mouvement.CREDIT ? "+" : "-") + 
               String.format("%.2f", montant).replace(",", ".");
    }
}