package com.web.cementerio.dao;

import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotoservicio;

public class PetfotoservicioDAO {

	public PetfotoservicioDAO() {
	}
	
	public int maxIdfotoservicio(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idfotoservicio) as max from Petfotoservicio ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public void savePetfotoservicio(Session session, Petfotoservicio petfotoservicio) throws Exception {
		session.save(petfotoservicio);
	}
	
	public void updatePetfotoservicio(Session session, Petfotoservicio petfotoservicio) throws Exception {
		session.update(petfotoservicio);
	}

}
