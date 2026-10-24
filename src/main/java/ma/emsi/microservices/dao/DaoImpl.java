package ma.emsi.microservices.dao;

/**
 * Premiere implementation de IDao : simulation d'un acces a une base de donnees.
 */
public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("[DaoImpl] Recuperation des donnees depuis la Base de Donnees SQL...");
        return 120.0;
    }
}
