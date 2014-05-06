package com.web.cementerio.dao;

import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotoguia;


public class PetfotoguiaDAO {

	public PetfotoguiaDAO(){
		
	}
	
	public int maxIdPetfotoguia(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idfotoguia) as max from Petfotoguia ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public void savePetfotoguia(Session session, Petfotoguia petfotoguia )throws Exception {
		session.save(petfotoguia);
	}
	
	public void updatePetfotoguia(Session session,  Petfotoguia petfotoguia ) throws Exception {
		session.update(petfotoguia);
	}
}
