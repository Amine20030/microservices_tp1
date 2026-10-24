package ma.emsi.microservices;

import ma.emsi.microservices.dao.DaoImpl;
import ma.emsi.microservices.metier.MetierImpl;

/**
 * Demonstration de l'instanciation statique.
 * Couplage fort : l'application depend directement des classes concretes avec 'new'.
 */
public class PresentationStatique {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println(" DEMONSTRATION 1 : Instanciation Statique (Couplage Fort - 'new')");
        System.out.println("================================================================================");

        // Instanciation statique
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);

        double resultat = metier.calcul();
        System.out.println("-> Resultat obtenu avec couplage statique : " + resultat);
        System.out.println("Inconvenient : Pour changer d'implementation DAO, il faut modifier le code source");
        System.out.println("et recompiler toute l'application (Violation du principe OCP).");
    }
}
