package ma.emsi.microservices;

import ma.emsi.microservices.container.MiniIoCContainer;
import ma.emsi.microservices.dao.DaoImpl;
import ma.emsi.microservices.dao.DaoImplV2;
import ma.emsi.microservices.metier.IMetier;
import ma.emsi.microservices.metier.MetierImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DynamicInjectionTest {

    @Test
    @DisplayName("Validation de l'instanciation statique (Couplage fort)")
    public void testInstanciationStatique() {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);

        double resultat = metier.calcul();

        // 120.0 * 2.5 = 300.0
        assertThat(resultat).isEqualTo(300.0);
    }

    @Test
    @DisplayName("Validation de l'injection dynamique par Reflexion avec DaoImpl (config.properties)")
    public void testInjectionDynamiqueDaoImpl() {
        MiniIoCContainer container = new MiniIoCContainer();
        container.init("config.properties");

        IMetier metier = container.getMetier();

        assertThat(metier).isNotNull();
        assertThat(container.getDao()).isInstanceOf(DaoImpl.class);

        double resultat = metier.calcul();
        assertThat(resultat).isEqualTo(300.0);
    }

    @Test
    @DisplayName("Validation de l'injection dynamique par Reflexion avec DaoImplV2 (config-v2.properties)")
    public void testInjectionDynamiqueDaoImplV2() {
        MiniIoCContainer container = new MiniIoCContainer();
        container.init("config-v2.properties");

        IMetier metier = container.getMetier();

        assertThat(metier).isNotNull();
        assertThat(container.getDao()).isInstanceOf(DaoImplV2.class);

        // 280.0 * 2.5 = 700.0
        double resultat = metier.calcul();
        assertThat(resultat).isEqualTo(700.0);
    }

    @Test
    @DisplayName("Verification du comportement en cas d'absence d'injection (IllegalStateException)")
    public void testMetierSansDependance() {
        MetierImpl metier = new MetierImpl();
        assertThrows(IllegalStateException.class, metier::calcul);
    }

    @Test
    @DisplayName("Verification de la gestion d'erreur si fichier de configuration introuvable")
    public void testFichierInexistant() {
        MiniIoCContainer container = new MiniIoCContainer();
        assertThrows(RuntimeException.class, () -> container.init("fichier_inexistant.properties"));
    }
}
