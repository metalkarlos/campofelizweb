package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Pethome;

public class PethomeDAO {

	public int maxIdhome(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idhome) as max from Pethome").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	public Pethome getPethomebyId(Session session,int idhome) throws Exception{
		Pethome pethome = null;
		
		Criteria criteria = session.createCriteria(Pethome.class) 
				.add(Restrictions.eq("idhome", idhome))
				.add(Restrictions.eq("setestado.idestado", 1));
		
		pethome =(Pethome) criteria.uniqueResult();
		
		return pethome;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pethome> lisPethome(Session session) throws Exception{
		List<Pethome> lisPethome = null;
		
		Criteria criteria = session.createCriteria(Pethome.class) 
				.add(Restrictions.eq("setestado.idestado", 1))
				.addOrder(Order.asc("posicion"));
		
		lisPethome =(List<Pethome>)criteria.list();
		
		return lisPethome;
	}
	
	public void savePethome(Session session, Pethome pethome) throws Exception {
		session.save(pethome);
	}
	
	public void updatePethome(Session session, Pethome pethome) throws Exception {
		session.update(pethome);
	}
}
