package HibernateUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;

import com.cias.entity.Banks;
import com.cias.utility.AES;
import com.cias.utility.ConnectionException;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(HibernateUtil.class);
	
	public static SessionFactory getSessionFactory(Banks db) {
		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

			// Configuration properties
			Map<String, Object> settings = new HashMap<>();
			settings.put(Environment.DRIVER, db.getDriverClass());
			settings.put(Environment.URL, db.getDatabaseUrl());
			settings.put(Environment.USER, db.getUsername());
			//settings.put(Environment.PASS, AES.decrypt(db.getPassword()));
			settings.put(Environment.PASS, db.getPassword());
			settings.put(Environment.SHOW_SQL, true);
			settings.put(Environment.HBM2DDL_AUTO, "create");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
			settings.put(Environment.C3P0_MIN_SIZE, 5);
			settings.put(Environment.C3P0_MAX_SIZE, 300);
			settings.put(Environment.C3P0_TIMEOUT, 100);
			settings.put(Environment.C3P0_MAX_STATEMENTS, 10);
			settings.put(Environment.C3P0_IDLE_TEST_PERIOD, 3000);
			settings.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
			logger.info(Environment.DRIVER + " " + db.getDriverClass() + " :: " + Environment.URL + " " + db.getDatabaseUrl() + " :: " + Environment.PASS + " " + db.getPassword());

			registryBuilder.applySettings(settings);
			registry = registryBuilder.build();

			MetadataSources sources = new MetadataSources(registry);
			Metadata metadata = sources.getMetadataBuilder().build();

			sessionFactory = metadata.getSessionFactoryBuilder().build();
		} catch (Exception e) {
			e.printStackTrace();
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
			throw new ConnectionException(e.getMessage());
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}