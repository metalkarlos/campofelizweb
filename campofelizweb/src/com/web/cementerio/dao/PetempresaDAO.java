package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Petempresa;

public class PetempresaDAO {
	
	public int getMaxidpetempresa(Session session)throws Exception{
		int maxid = 0;
				
		Object object = session.createQuery("select max(idempresa)+1 from Petempresa ").uniqueResult();
		maxid = (object ==null ?1: Integer.parseInt(object.toString()));
		
		return maxid;
	}
	
	public Petempresa getPetempresabyTipo(Session session,int idestado, int idtipo)throws Exception{
		Petempresa petempresa = null;
		
		Criteria criteria = session.createCriteria(Petempresa.class) 
				.add(Restrictions.eq("tipoempresa", idtipo))
				.add(Restrictions.eq("setestado.idestado", idestado));
		
		petempresa =(Petempresa) criteria.uniqueResult();
		
		return petempresa;
	}
	
	public Petempresa getPetempresabyId(Session session,int idestado, int idempresa)throws Exception{
		Petempresa petempresa = null;
		
		Criteria criteria = session.createCriteria(Petempresa.class) 
				.add(Restrictions.eq("idempresa", idempresa))
				.add(Restrictions.eq("setestado.idestado", idestado));
		
		petempresa =(Petempresa) criteria.uniqueResult();
		
		return petempresa;
	}

	@SuppressWarnings("unchecked")
	public List<Petempresa> getListPetempresa(Session session,int idestado)throws Exception{
		List<Petempresa> listPetempresa = null;
		
		Criteria criteria = session.createCriteria(Petempresa.class) 
				.add(Restrictions.eq("setestado.idestado", idestado))
				.addOrder(Order.asc("idempresa"));
		
		listPetempresa =(List<Petempresa>)criteria.list();
		
		return listPetempresa;
	}
	
	
	public void grabar (Session session, Petempresa petempresa)throws Exception{
		session.save(petempresa);
		
	}
	
	public void modificar (Session session, Petempresa petempresa) throws Exception{
		session.update(petempresa);
	}
}
