package SE_08.NMCNPM1.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "SE08.repository.khoanthu", // Chỉ định repository cho schema khoanthu
        entityManagerFactoryRef = "khoanthuEntityManagerFactory", // Tham chiếu đến factory entity cho khoanthu
        transactionManagerRef = "khoanthuTransactionManager" // Tham chiếu đến transaction manager cho khoanthu
)
public class KhoanThuDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.khoanthu")
    public DataSource khoanthuDataSource() {
        return new HikariDataSource(); // Sử dụng HikariDataSource
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean khoanthuEntityManagerFactory(
            @Qualifier("khoanthuDataSource") DataSource khoanthuDataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(khoanthuDataSource);
        em.setPackagesToScan("SE08.model.khoanthu"); // Chỉ định package chứa các entity của schema khoanthu
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceUnitName("khoanthu");

        return em;
    }

    @Bean
    public PlatformTransactionManager khoanthuTransactionManager(
            @Qualifier("khoanthuEntityManagerFactory") EntityManagerFactory khoanthuEntityManagerFactory) {
        return new JpaTransactionManager(khoanthuEntityManagerFactory);
    }
}

