package ma.emsi.microservices.dao;

/**
 * Deuxieme implementation de IDao : simulation d'une consommation de Web Service / Microservice distant.
 * Illustre le principe ouvert/ferme (Open/Closed Principle) : ajout d'une nouvelle fonctionnalite sans modifier le code existant.
 */
public class DaoImplV2 implements IDao {

    @Override
    public double getData() {
        System.out.println("[DaoImplV2] Appel d'un Microservice REST distant / Capteur IoT...");
        return 280.0;
    }
}
