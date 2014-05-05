package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
	public List<Petmascotahomenaje> lisPetmascotaBusquedaByPage(Session session, String[] texto, int pageSize, int pageNumber, int args[], int idestado) throws Exception {
		List<Petmascotahomenaje> listPetmascotahomenaje = null;
		
		Criteria criteria = session.createCriteria(Petmascotahomenaje.class)
				 .add(Restrictions.eq("setestado.idestado", idestado));
		
		if(texto != null && texto.length > 0){
			String query = "(";
			for(int i=0;i<texto.length;i++)
			{
				query += "lower({alias}.nombre) like lower('%"+texto[i]+"%') ";
				if(i<texto.length-1){
					query += "or ";
				}
			}
			query += ")";
			
			criteria.add(Restrictions.sqlRestriction(query));
		}
		criteria.addOrder(Order.desc("fechapublicacion"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
		
		listPetmascotahomenaje = (List<Petmascotahomenaje>)criteria.list();
		
		if(listPetmascotahomenaje.size() >0 && !listPetmascotahomenaje.isEmpty()){
			Criteria criteriaCount = session.createCriteria(Petmascotahomenaje.class)
					.add(Restrictions.eq("setestado.idestado", idestado))
					.setProjection( Projections.rowCount());
			
			if(texto != null && texto.length > 0){
				String query = "(";
				for(int i=0;i<texto.length;i++)
				{
					query += "lower({alias}.nombre) like lower('%"+texto[i]+"%') ";
					if(i<texto.length-1){
						query += "or ";
					}
				}
				query += ")";
				
				criteria.add(Restrictions.sqlRestriction(query));
			}
				
				criteriaCount.setMaxResults(pageSize)
			    .setFirstResult(pageNumber);
				
				Object object = criteriaCount.uniqueResult();
				int count = (object==null?0:Integer.parseInt(object.toString()));
				args[0] = count;
	  
		}
	    else{
		   	args[0] = 0;
		}
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
