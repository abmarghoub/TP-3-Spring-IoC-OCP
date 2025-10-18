// test/OcpPropertyTest.java
import dao.IDao;
import metier.IMetier;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import presentation.Presentation2;

import static org.junit.Assert.assertEquals;

public class OcpPropertyTest {

    @Test
    public void propertyTargetDaoApi_choisitDaoApi_440() {
        // Définir la propriété avant l'initialisation du contexte
        System.setProperty("dao.target", "daoApi"); // correspond à DaoApi (220)

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // Ne pas activer de profil, on utilise PropertyDrivenConfig
        ctx.register(Presentation2.class);
        ctx.refresh();

        IMetier metier = ctx.getBean(IMetier.class);
        assertEquals(440.0, metier.calcul(), 1e-6);

        ctx.close();
    }
}
