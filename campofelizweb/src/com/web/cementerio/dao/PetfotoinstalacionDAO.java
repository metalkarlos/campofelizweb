package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Petfotoinstalacion;

public class PetfotoinstalacionDAO {

	public PetfotoinstalacionDAO() {
	}
	
	public int maxIdfotoinstalacion(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idfotoinstalacion)+1 as max from Petfotoinstalacion ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	
  public Petfotoinstalacion getPetfotoinstalacionById(Session session, int idfotoinstalacion, int idestado) throws Exception {
		
	  Petfotoinstalacion petfotoinstalacion = null;
			 Criteria criteria = session.createCriteria(Petfotoinstalacion.class, "foto")
						 .add(Restrictions.eq("foto.idfotoinstalacion", idfotoinstalacion))
						 .add(Restrictions.eq("foto.setestado.idestado", idestado));
			 petfotoinstalacion = (Petfotoinstalacion) criteria.uniqueResult();
		return petfotoinstalacion;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petfotoinstalacion> lisPetfotoinstalacion(Session session, int idestado) throws Exception {
		List<Petfotoinstalacion> lisPetfotoinstalacion = null;
		
		String hql = " from Petfotoinstalacion ";
		hql += " where setestado.idestado = :idestado ";
		hql += " order by orden asc ";
		
		Query query = session.createQuery(hql)
				.setInteger("idestado", idestado);
		
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
