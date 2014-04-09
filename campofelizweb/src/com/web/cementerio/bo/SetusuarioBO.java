package com.web.cementerio.bo;

import org.hibernate.Session;

import com.web.cementerio.dao.SetusuarioDAO;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.HibernateUtil;

public class SetusuarioBO {
	
	private SetusuarioDAO setusuarioDAO;
	
	public SetusuarioBO() throws RuntimeException {
		setusuarioDAO = new SetusuarioDAO();
	}

	public Setusuario getByUserPasswd(String nombre, String clave ) throws Exception {
		Setusuario setusuario = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			setusuario = setusuarioDAO.getByUserPasswd(session, nombre, clave);
		}catch(Exception he){
			throw new Exception(he);
		}finally{
			session.close();
		}
		
		return setusuario;
	}
	
}
