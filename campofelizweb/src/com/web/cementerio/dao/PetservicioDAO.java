package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petservicio;

public class PetservicioDAO {

	@SuppressWarnings("unchecked")
	public List<Petservicio> lisPetservicioPrincipales(Session session) throws Exception {
		List<Petservicio> lisPetservicio = null;
		
		String hql = " from Petservicio ";
		hql += " where setestado.idestado = :idestado ";
		hql += " and principal = :principal ";
		hql += " order by nombre ";
		
		Query query = session.createQuery(hql)
				.setInteger("idestado", 1)
				.setInteger("principal", 1);
		
		lisPetservicio = (List<Petservicio>) query.list();
		
		return lisPetservicio;
	}
}
