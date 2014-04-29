package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import com.web.cementerio.pojo.annotations.Petmascotahomenaje;

public class PetmascotahomenajeDAO {
	
	public Petmascotahomenaje getPethomenajemascotaById(Session session, int idmascota, int idestado, boolean especie) throws Exception {
		
		Petmascotahomenaje petmascotahomenaje = null;
		if (!especie){
			Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
					 .add(Restrictions.eq("idmascota", idmascota))
					 .add(Restrictions.eq("setestado.idestado", idestado));
					
			petmascotahomenaje = (Petmascotahomenaje) criteria.uniqueResult();
	
		}else{
	          petmascotahomenaje  = (Petmascotahomenaje) session.createCriteria(Petmascotahomenaje.class)
	          .createAlias("petespecie", "e")
	          .add( Restrictions.eq("idmascota", idmascota) ).uniqueResult();
		}
		return petmascotahomenaje;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> getListpetmascotahomenaje(Session session, int idestado) throws Exception{
		List<Petmascotahomenaje> listPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
				.add(Restrictions.eq("setestado.idestado",idestado));
		
		criteria.addOrder(Order.desc("fechapublicacion"))
		.setMaxResults(6);
		
		listPetmascotahomenaje = (List<Petmascotahomenaje>)criteria.list();
		       
		return listPetmascotahomenaje;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petmascotahomenaje> getListpetmascotabycriteria(Session session, int idestado, int idespecie, String nombre){
		List<Petmascotahomenaje> listPetmascotahomeanje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
				 .add(Restrictions.eq("setestado.idestado",idestado))
				 .add(Restrictions.eq("petespecie.idespecie", idespecie))
				 .add(Restrictions.like("nombre","%" +nombre.replaceAll(" ", "%")+"%").ignoreCase());
		
		criteria.addOrder(Order.desc("fechapublicacion"));
		listPetmascotahomeanje = (List<Petmascotahomenaje>)criteria.list();
		
		return listPetmascotahomeanje;
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
    public void modificarPetmascotahomenaje(Session session, Petmascotahomenaje petmascotahomenaje) throws Exception {
    	session.update(petmascotahomenaje);
    }

}
