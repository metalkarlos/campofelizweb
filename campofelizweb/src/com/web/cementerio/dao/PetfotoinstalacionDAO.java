package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotoinstalacion;

public class PetfotoinstalacionDAO {

	public PetfotoinstalacionDAO() {
	}
	
	public int maxIdfotoinstalacion(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idfotoinstalacion) as max from Petfotoinstalacion ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petfotoinstalacion> lisPetfotoinstalacion(Session session) throws Exception {
		List<Petfotoinstalacion> lisPetfotoinstalacion = null;
		
		String hql = " from Petfotoinstalacion ";
		hql += " where setestado.idestado = :idestado ";
		hql += " order by idfotoinstalacion ";
		
		Query query = session.createQuery(hql)
				.setInteger("idestado", 1);
		
		lisPetfotoinstalacion = (List<Petfotoinstalacion>) query.list();
		
		return lisPetfotoinstalacion;
	}
	
	public void savePetfotoinstalacion(Session session, Petfotoinstalacion petfotoinstalacion) throws Exception {
		session.save(petfotoinstalacion);
	}
	
	public void updatePetfotoinstalacion(Session session, Petfotoinstalacion petfotoinstalacion) throws Exception {
		session.update(petfotoinstalacion);
	}

}
