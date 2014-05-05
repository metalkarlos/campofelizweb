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

	public int maxIdservicio(Session session) throws Exception {
		int max=0;
		
		Object object = session.createQuery("select max(idservicio) as max from Petservicio ").uniqueResult();
		max = (object==null?0:Integer.parseInt(object.toString()));
		
		return max;
	}
	
	@SuppressWarnings("unchecked")
	public List<Petservicio> lisPetservicioPrincipales(Session session) throws Exception {
		List<Petservicio> lisPetservicio = null;
		
		String hql = " from Petservicio ";
		hql += " where setestado.idestado = :idestado ";
		hql += " and principal = :principal ";
		hql += " order by nombre ";
		
		Query query = session.createQuery(hql)
				.setInteger("idestado", 1)
				.setBoolean("principal", true);
		
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
		
		Criteria criteria = session.createCriteria(Petservicio.class, "serv")
				.add( Restrictions.eq("serv.idservicio", idservicio))
				.createAlias("serv.setestado", "servestado", Criteria.LEFT_JOIN, Restrictions.eq("servestado.idestado", 1))
				.createAlias("serv.petfotoservicios", "foto", Criteria.LEFT_JOIN)
				.createAlias("foto.setestado", "fotoestado", Criteria.LEFT_JOIN, Restrictions.eq("fotoestado.idestado", 1));
		
		petservicio = (Petservicio) criteria.uniqueResult();
		
		return petservicio;
	}
	
	public void savePetservicio(Session session, Petservicio petservicio) throws Exception {
		session.save(petservicio);
	}
	
	public void updatePetservicio(Session session, Petservicio petservicio) throws Exception {
		session.update(petservicio);
	}
	
}
