package ma.emsi.microservices;

import ma.emsi.microservices.container.MiniIoCContainer;
import ma.emsi.microservices.metier.IMetier;

/**
 * Point d'entree principal : Demonstration de l'instanciation dynamique par Reflexion
 * et respect du principe Ouvert/Ferme (OCP).
 */
public class PresentationDynamique {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println(" TP 1 : PROGRAMMATION DYNAMIQUE PAR REFLEXION DANS UNE ARCHITECTURE MICROSERVICES");
        System.out.println("================================================================================\n");

        MiniIoCContainer container = new MiniIoCContainer();

        // 1. Chargement avec l'implementation de base (DaoImpl - Base SQL)
        System.out.println("--- 1. SCENARIO A : Configuration avec DAO SQL Standard (config.properties) ---");
        container.init("config.properties");
        IMetier metierA = container.getMetier();
        double resultatA = metierA.calcul();
        System.out.println("-> Resultat final calcule : " + resultatA);

        // 2. Chargement avec la deuxieme implementation (DaoImplV2 - Microservice Web Service)
        // Notez qu'AUCUNE ligne de code n'a ete modifiee dans MetierImpl ni dans PresentationDynamique !
        System.out.println("\n--- 2. SCENARIO B : Bascule vers Microservice Distant (config-v2.properties) ---");
        MiniIoCContainer containerV2 = new MiniIoCContainer();
        containerV2.init("config-v2.properties");
        IMetier metierB = containerV2.getMetier();
        double resultatB = metierB.calcul();
        System.out.println("-> Resultat final calcule : " + resultatB);

        System.out.println("\n================================================================================");
        System.out.println(" BILAN PEDAGOGIQUE : PRINCIPE OPEN/CLOSED RESPECTE");
        System.out.println(" - Le code metier est ferme a la modification (aucun changement requis)");
        System.out.println(" - L'application est ouverte a l'extension (nouveau microservice integre par reflexion)");
        System.out.println("================================================================================");
    }
}
