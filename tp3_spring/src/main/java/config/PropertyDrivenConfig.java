package config;

import dao.IDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Map;

@Configuration
@PropertySource("classpath:app.properties")
public class PropertyDrivenConfig {

    // Injection des beans IDao existants (indexés par nom)
    private final Map<String, IDao> candidates;

    public PropertyDrivenConfig(Map<String, IDao> candidates) {
        this.candidates = candidates;
    }

    @Value("${dao.target:dao}")
    private String target;

    @Bean
    public IDao selectedDao() {
        // On ignore les beans qui ne sont pas actifs pour ce profil
        IDao bean = candidates.get(target);
        if (bean == null) {
            throw new IllegalArgumentException(
                    "Implémentation inconnue: " + target + " (dao|dao2|daoFile|daoApi)"
            );
        }
        return bean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
