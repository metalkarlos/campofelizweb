package com.web.cementerio.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Setusuario;

public class SetusuarioDAO {
	
	public Setusuario getByUserPasswd(Session session, String nombre, String clave) throws Exception {
		Setusuario setusuario = null;
		
		Criteria criteria = session.createCriteria(Setusuario.class);
		criteria.add( Restrictions.like("nombre", nombre) );
		criteria.add( Restrictions.like("clave", clave) );
		
		setusuario = (Setusuario) criteria.uniqueResult();
		
		return setusuario;
	}
	
}
