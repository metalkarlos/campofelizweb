package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Petservicio;

public class PetservicioDAO {

	@SuppressWarnings("unchecked")
	public List<Petservicio> lisPetservicioPrincipales(Session session) throws Exception {
		List<Petservicio> lisPetservicio = null;
		
		String hql = " from Petservicio ";
		hql += " where setestado.idestado = :idestado ";
		hql += " and principal = :principal ";
		hql += " order by nombre ";
		
		Query query = session.createQuery(hql)
				.setInteger("idestado", 1)
				.setInteger("principal", 1);
		
		lisPetservicio = (List<Petservicio>) query.list();
		
		return lisPetservicio;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petservicio> lisPetservicioBusquedaByPage(Session session, String[] texto, int pageSize, int pageNumber, int args[]) throws Exception {
		List<Petservicio> lisPetservicio = null;
		
		Criteria criteria = session.createCriteria(Petservicio.class)
		.add( Restrictions.eq("setestado.idestado", 1));
		
		if(texto != null && texto.length > 0){
			String query = "(";
			for(int i=0;i<texto.length;i++)
			{
				query += "lower({alias}.descripcion) like lower('%"+texto[i]+"%') ";
				if(i<texto.length-1){
					query += "or ";
				}
			}
			query += ")";
			
			criteria.add(Restrictions.sqlRestriction(query));
		}
		
        criteria.addOrder(Order.asc("nombre"))
		.setMaxResults(pageSize)
		.setFirstResult(pageNumber);
        
		lisPetservicio = (List<Petservicio>) criteria.list();
		
		if(lisPetservicio != null && lisPetservicio.size() > 0)
		{
			Criteria criteriaCount = session.createCriteria(Petservicio.class)
					.add( Restrictions.eq("setestado.idestado", 1))
                    .setProjection( Projections.rowCount());

			if(texto != null && texto.length > 0){
				String query = "(";
				for(int i=0;i<texto.length;i++)
				{
					query += "lower({alias}.descripcion) like lower('%"+texto[i]+"%') ";
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
		else
		{
			args[0] = 0;
		}
		
		return lisPetservicio;
	}
	
	public Petservicio getPetservicioConObjetosById(Session session, int idservicio) throws Exception {
		Petservicio petservicio = null;
		
		String hql = " from Petservicio as serv left join fetch serv.setestado as servestado ";
		hql += " left join fetch serv.petfotoservicios as foto ";
		hql += " left join fetch foto.setestado as fotoestado ";
		hql += " where serv.idservicio = :idservicio ";
		hql += " and servestado.idestado = 1 ";
		hql += " and fotoestado.idestado = 1 ";

		Query query = session.createQuery(hql)
				.setInteger("idservicio", idservicio);
		
		petservicio = (Petservicio) query.uniqueResult();
		
		return petservicio;
	}
	
}
