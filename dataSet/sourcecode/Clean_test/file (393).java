package br.com.andreluisgomes.test.config;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.andreluisgomes.automation.utils.PagesConfig;

@Configuration
@ComponentScan(basePackages = "br.com.andreluisgomes")
@EnableTransactionManagement
@EnableWebMvc
@PropertySource("classpath:jetty-config/jetty.properties")
public class AppConfigTest {

	// Beam for PhantomJS WebDriver
	@Bean(name = "driver", destroyMethod = "quit")
	public WebDriver driver() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setJavascriptEnabled(true);
		desiredCapabilities.setCapability("takesScreenshot", false);

		desiredCapabilities.setCapability("phantomjs.binary.path",
				getPhantomJSDriverPath());
		WebDriver driver = new PhantomJSDriver(desiredCapabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return driver;
	}

	// Get driver for OS
	private String getPhantomJSDriverPath() {
		String os = System.getProperty("os.name");
		String phantomJSWebDriver = null;
		if (os.contains("Windows")) {
			phantomJSWebDriver = "src/test/resources/phantomjs/phantomjs.exe";
		}
		if (os.contains("Linux")) {
			// PhantomJS path for Jenkins Job
			phantomJSWebDriver = "/usr/lib64/phantomjs-1.9.8-linux-x86_64/bin/phantomjs";
		}
		System.out.println(os);

		return phantomJSWebDriver;
	}

	// Read jetty.properties to set Jetty Host and Jetty Port
	@Bean(name = "pagesConfig")
	public PagesConfig pagesConfig(Environment env) {
		PagesConfig conf = new PagesConfig();
		conf.setJettyHost("localhost");

		String jettyFreePort = System.getProperty("jetty.free.port");
		String logLIne = "************************************************************************************************************";
		if(jettyFreePort != null){
			conf.setJettyPort(jettyFreePort);
			System.out.println(logLIne);
			System.out.println("[INFO] : Setting -Djetty.free.port="+ jettyFreePort +" with free port reserved by build-helper-maven-plugin.");
			System.out.println(logLIne);
		}else{
			System.out.println(logLIne);
			System.out.println("[ERROR] : Integration Tests needs -Djetty.free.port=PORT propertie. Please, configure one.");
			conf.setJettyPort("8080");
			System.out.println("[WARNING] : Setting -Djetty.free.port=8080 by default.");
			System.out.println(logLIne);
		}
		return conf;
	}

	@Profile("AUTOMATION")
	@PropertySource("classpath:db-config/automation.properties")
	public static class AutomationDataSource {
		@Bean
		public DataSource dataSource(Environment env) {
			DriverManagerDataSource h2 = new DriverManagerDataSource();
			h2.setDriverClassName(env
					.getProperty("hibernate.connection.driver_class"));
			h2.setUrl(env.getProperty("hibernate.connection.url"));
			h2.setUsername(env.getProperty("hibernate.connection.username"));
			h2.setPassword(env.getProperty("hibernate.connection.password"));
			return h2;
		}

		@Bean(name = "sessionFactory")
		public LocalSessionFactoryBean sessionFactory(Environment environment) {
			LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
			Properties hibernateProperties = new Properties();

			hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
					"create-drop");
			hibernateProperties.setProperty("hibernate.dialect",
					"org.hibernate.dialect.OracleDialect");
			hibernateProperties.setProperty("hibernate.show_sql", "true");
			hibernateProperties.setProperty("hibernate.c3p0.idle_test_periods",
					"300");
			hibernateProperties.setProperty("hibernate.c3p0.max_size", "100");
			hibernateProperties.setProperty("hibernate.c3p0.max_statements",
					"50");
			hibernateProperties.setProperty("hibernate.c3p0.min_size", "1");
			hibernateProperties.setProperty("hibernate.c3p0.timeout", "900");
			hibernateProperties.setProperty("hibernate.hbm2ddl.import_files",
					"demandas.sql");

			localSessionFactoryBean.setHibernateProperties(hibernateProperties);
			localSessionFactoryBean
					.setPackagesToScan("com.walmart.imt.facility");
			localSessionFactoryBean.setDataSource(dataSource(environment));

			return localSessionFactoryBean;
		}

		@Bean
		public HibernateTransactionManager transactionManager(
				SessionFactory sessionFactory) {
			HibernateTransactionManager htm = new HibernateTransactionManager();
			htm.setSessionFactory(sessionFactory);
			return htm;
		}

		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
			return new PersistenceExceptionTranslationPostProcessor();
		}

	}
}
