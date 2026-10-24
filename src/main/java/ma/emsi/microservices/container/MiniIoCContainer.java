package ma.emsi.microservices.container;

import ma.emsi.microservices.dao.IDao;
import ma.emsi.microservices.metier.IMetier;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Conteneur IoC leger illustrant le principe d'Inversion de Controle (IoC)
 * et d'Injection de Dependances par Reflexion (Java Reflection API).
 */
public class MiniIoCContainer {

    private IDao dao;
    private IMetier metier;

    /**
     * Initialise le conteneur a partir d'un fichier de configuration properties.
     *
     * @param configFile Chemin du fichier dans le classpath (ex: "config.properties")
     */
    public void init(String configFile) {
        try {
            Properties properties = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream(configFile);
            if (is == null) {
                throw new IllegalArgumentException("Fichier de configuration introuvable dans le classpath: " + configFile);
            }
            properties.load(is);

            String daoClassName = properties.getProperty("dao.class");
            String metierClassName = properties.getProperty("metier.class");

            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("[MiniIoCContainer] Lecture de la configuration : " + configFile);
            System.out.println("  -> Classe DAO specifiee    : " + daoClassName);
            System.out.println("  -> Classe Metier specifiee : " + metierClassName);
            System.out.println("--------------------------------------------------------------------------------");

            // 1. Instanciation dynamique de la classe DAO
            Class<?> cDao = Class.forName(daoClassName);
            this.dao = (IDao) cDao.getDeclaredConstructor().newInstance();
            System.out.println("[Reflection API] Instance DAO creee dynamiquement : " + this.dao.getClass().getName());

            // 2. Instanciation dynamique de la classe Metier
            Class<?> cMetier = Class.forName(metierClassName);
            this.metier = (IMetier) cMetier.getDeclaredConstructor().newInstance();
            System.out.println("[Reflection API] Instance Metier creee dynamiquement : " + this.metier.getClass().getName());

            // 3. Injection dynamique de la dependance via la methode setDao(IDao)
            Method setDaoMethod = cMetier.getMethod("setDao", IDao.class);
            setDaoMethod.invoke(this.metier, this.dao);
            System.out.println("[Reflection API] Injection effectuee : setDao(" + this.dao.getClass().getSimpleName() + ")");

        } catch (Exception e) {
            System.err.println("[Erreur IoC] Echec du chargement ou de l'injection dynamique : " + e.getMessage());
            throw new RuntimeException("Erreur de configuration IoC par reflexion", e);
        }
    }

    public IDao getDao() {
        return dao;
    }

    public IMetier getMetier() {
        return metier;
    }
}
