package com.web.cementerio.dao;



import org.hibernate.Query;
import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petinformacion;

public class PetinformacionDAO {

	public Petinformacion getPetinformacionById(Session session, int idinformacion) throws Exception {
		Petinformacion petinformacion = null;
		
		String hql = " from Petinformacion ";
		hql += " where idinformacion = :idinformacion ";
		
		Query query = session.createQuery(hql)
				.setInteger("idinformacion", idinformacion);
		
		petinformacion = (Petinformacion) query.uniqueResult();
		
		return petinformacion;
	}
	

	public void ingresarPetinformacion(Session session, Petinformacion petinformacion) throws Exception {
		session.save(petinformacion);
	}
	
	public void actualizarPetinformacion(Session session, Petinformacion petinformacion) throws Exception {
		session.update(petinformacion);
	}
}
