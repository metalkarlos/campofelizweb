package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.web.cementerio.pojo.annotations.Petfotomascota;


public class PetfotomascotaDAO {
	
	@SuppressWarnings("unchecked")
	public List<Petfotomascota> getListpetfotomascota(Session session, int idmascota, int idestado)throws Exception{
		List<Petfotomascota> listpetfotomascota = null;
		
		String hql = "   from Petfotomascota ";
		       hql += " where idmascota = :idmascota";
		       hql += "   and setestado.idestado = :idestado";
		
		Query query = session.createQuery(hql)
				.setInteger("idmascota", idmascota)
				.setInteger("idestado" , idestado);
		
		listpetfotomascota = (List<Petfotomascota>) query.list();
		
		return listpetfotomascota;
	}
	
	public int getMaxidpetfotomascota(Session session)throws Exception{
		
		int maxid = 0;
				
		Object object = session.createQuery("select max(idfotomascota)+1 from Petfotomascota ").uniqueResult();
		maxid = (object ==null ?1: Integer.parseInt(object.toString()));
		
		return maxid;
	}

	
	public void ingresarFotomascota(Session session, Petfotomascota petfotomascota)throws Exception{
		session.save(petfotomascota);

	}

	public void modificarFotomascota(Session session, Petfotomascota petfotomascota)throws Exception{
		session.update(petfotomascota);
	}
	
	
}
