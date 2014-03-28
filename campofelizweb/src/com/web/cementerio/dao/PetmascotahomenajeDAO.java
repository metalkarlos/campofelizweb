package com.web.cementerio.dao;

import org.hibernate.Query;
import org.hibernate.Session;


import com.web.cementerio.pojo.annotations.Petmascotahomenaje;

public class PetmascotahomenajeDAO {
	
	public Petmascotahomenaje getPethomenajemascotaById(Session session, int idmascota) throws Exception {
		Petmascotahomenaje petmascotahomenaje = null;
		
		String hql = " from Petmascotahomenaje ";
		       hql += " where idmascota = :idmascota ";
		
		Query query = session.createQuery(hql)
				     .setInteger("idmascota", idmascota);
		
		petmascotahomenaje = (Petmascotahomenaje) query.uniqueResult();
		
		return petmascotahomenaje;
	}
	
	
	public int getMaxidpetmascotahomenaje(Session session)throws Exception{
		int maxid = 0;
				
		Object object = session.createQuery("select max(idmascota)+1 from Petmascotahomenaje ").uniqueResult();
		maxid = (object ==null ?1: Integer.parseInt(object.toString()));
		
		return maxid;
	}
	
    public void ingresarPetmascotahomenaje(Session session, Petmascotahomenaje petmascotahomenaje) throws Exception {
    	session.save(petmascotahomenaje);
    }
}
