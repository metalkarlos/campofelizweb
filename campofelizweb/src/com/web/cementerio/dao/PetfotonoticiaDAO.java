package com.web.cementerio.dao;

import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotonoticia;

public class PetfotonoticiaDAO {

	public PetfotonoticiaDAO() {
	}
	
	public int maxIdfotonoticia(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idfotonoticia) as max from Petfotonoticia ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public void savePetfotonoticia(Session session, Petfotonoticia petfotonoticia) throws Exception {
		session.save(petfotonoticia);
	}
	
	public void updatePetfotonoticia(Session session, Petfotonoticia petfotonoticia) throws Exception {
		session.update(petfotonoticia);
	}

}
