package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.web.cementerio.pojo.annotations.Petvenunciado;

public class PetvenunciadoDAO {
	
	@SuppressWarnings("unchecked")
	public List<Petvenunciado> getListpetvenunciado(Session session) throws Exception{
		List<Petvenunciado> listpetenunciado=null;
		
		Criteria criteria = session.createCriteria(Petvenunciado.class);
		
		listpetenunciado =(List<Petvenunciado>)criteria.list();
	    return listpetenunciado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petvenunciado> getListpetenunciadobyId(Session session, int idenunciado) throws Exception{
		List<Petvenunciado> listpetenunciado=null;
		Disjunction sentenciaOR = Restrictions.disjunction();
		Criterion   critidenunciado = Restrictions.eq("idenunciado", idenunciado);
		
		sentenciaOR.add(Restrictions.eq("idpadre",idenunciado ));
		
		Criteria criteria = session.createCriteria(Petvenunciado.class) 
				     .add(Restrictions.and(critidenunciado, sentenciaOR));
				 criteria.addOrder(Order.desc("idpadre"));
		
		listpetenunciado = (List<Petvenunciado>)criteria.list();
		return listpetenunciado;
	}

}
