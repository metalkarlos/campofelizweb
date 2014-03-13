package com.web.cementerio.bo;

import org.hibernate.Session;

import com.web.cementerio.dao.PetinformacionDAO;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.util.HibernateUtil;

public class PetinformacionBO {

	PetinformacionDAO petinformacionDAO;
	
	public PetinformacionBO() {
		petinformacionDAO = new PetinformacionDAO();
	}
	
	public Petinformacion getPetinformacionById(int idinformacion) throws Exception {
		Petinformacion petinformacion = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petinformacion = petinformacionDAO.getPetinformacionById(session, idinformacion);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return petinformacion;
	}
	
	
	public void ingresarPetinformacion(Petinformacion petinformacion) throws Exception{
	
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petinformacionDAO.ingresarPetinformacion(session, petinformacion);
			}catch(Exception e){
				throw new Exception (e);
			}finally{
				session.close();
			}
					
		}
	
	public void actualizarPetinformacion(Petinformacion petinformacion) throws Exception{
		Session session = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petinformacionDAO.actualizarPetinformacion(session, petinformacion);
		}catch(Exception e){
			 throw new Exception(e);
		}finally{
			session.close();
		}
	}
}
