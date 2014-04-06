package com.web.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
            // Create the SessionFactory from [hibernate].cfg.xml
            FileUtil fileUtil = new FileUtil();
            String resource = fileUtil.getPropertyValue("postgrescfgfile");
            //String resource = "postgresql.cfg.xml";
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
