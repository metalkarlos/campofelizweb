package com.web.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.web.cementerio.global.Parametro;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from [hibernate].cfg.xml
			FileUtil fileUtil = new FileUtil();
			Properties systemProperties = fileUtil.getPropertiesFile(Parametro.PARAMETROS_PROPERTIES_PATH);
			String resource = systemProperties.getProperty("postgrescfgfile");
			return new Configuration().configure(resource).buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
