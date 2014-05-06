package com.web.cementerio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.cementerio.pojo.annotations.Petguia;


public class PetguiaDAO {
	
		public PetguiaDAO(){
			
		}
		
		public Petguia getPetguiaById(Session session, int idguia, int idestado) throws Exception {
				
			    Petguia petguia=null;
			
				petguia  = (Petguia) session.createCriteria(Petguia.class)
			    .createAlias("petfotoguia", "e")
			    .add( Restrictions.eq("idguia", idguia) ).uniqueResult();
				
				return petguia;
			}
		
		
	
		
		@SuppressWarnings("unchecked")
		public List<Petguia> lisPetguiaBusquedaByPage(Session session, String[] texto, int pageSize, int pageNumber, int args[], int idestado) throws Exception {
			List<Petguia> listPetguia = null;
			
			Criteria criteria = session.createCriteria(Petguia.class)
					 .add(Restrictions.eq("setestado.idestado", idestado));
			
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
			criteria.addOrder(Order.desc("fechapublicacion"))
			.setMaxResults(pageSize)
			.setFirstResult(pageNumber);
			
			listPetguia = (List<Petguia>)criteria.list();
			
			if(listPetguia.size() >0 && !listPetguia.isEmpty()){
				Criteria criteriaCount = session.createCriteria(Petguia.class)
						.add(Restrictions.eq("setestado.idestado", idestado))
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
		    else{
			   	args[0] = 0;
			}
			return listPetguia;
			
		}
		
		
		
		public int getMaxidpetguia(Session session)throws Exception{
			int maxid = 0;
					
			Object object = session.createQuery("select max(idguia)+1 from Petguia ").uniqueResult();
			maxid = (object ==null ?1: Integer.parseInt(object.toString()));
			
			return maxid;
		}
		
	    public void savePetguia(Session session, Petguia petguia) throws Exception {
	    	session.save(petguia);
	    }
	    public void updatePetguia(Session session, Petguia petguia) throws Exception {
	    	session.update(petguia);
	    }

}