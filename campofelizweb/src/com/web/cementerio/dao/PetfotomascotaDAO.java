package com.web.cementerio.dao;

import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotomascota;

public class PetfotomascotaDAO {
	
	public int getMaxidpetfotomascota(Session session)throws Exception{
		
		int maxid = 0;
				
		Object object = session.createQuery("select max(idfotomascota)+1 from Petfotomascota ").uniqueResult();
		maxid = (object ==null ?1: Integer.parseInt(object.toString()));
		
		return maxid;
	}

	
	public void ingresarFotomascota(Session session, Petfotomascota petfotomascota)throws Exception{
		session.save(petfotomascota);
	}
}
