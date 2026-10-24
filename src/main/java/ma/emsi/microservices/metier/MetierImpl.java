package ma.emsi.microservices.metier;

import ma.emsi.microservices.dao.IDao;

/**
 * Implementation de IMetier avec couplage faible vers IDao.
 */
public class MetierImpl implements IMetier {

    // Couplage faible : l'attribut est de type interface IDao
    private IDao dao;

    public MetierImpl() {
    }

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        if (dao == null) {
            throw new IllegalStateException("La dependance IDao n'a pas ete injectee dans MetierImpl.");
        }
        double data = dao.getData();
        // Logique metier appliquee (ex: calcul de taxe, conversion ou ponderation)
        double resultat = data * 2.5;
        System.out.println("[MetierImpl] Calcul effectue : " + data + " * 2.5 = " + resultat);
        return resultat;
    }

    /**
     * Methode setter utilisee pour l'injection dynamique par reflexion.
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public IDao getDao() {
        return dao;
    }
}
