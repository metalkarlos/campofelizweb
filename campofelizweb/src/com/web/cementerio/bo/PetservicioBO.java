package com.web.cementerio.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.dao.PetservicioDAO;
import com.web.cementerio.pojo.annotations.Petservicio;
import com.web.util.HibernateUtil;

public class PetservicioBO {

	private PetservicioDAO petservicioDAO;
	
	public PetservicioBO() {
		petservicioDAO = new PetservicioDAO();
	}
	
	public List<Petservicio> lisPetservicioPrincipales() throws Exception {
		List<Petservicio> lisPetservicio = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetservicio = petservicioDAO.lisPetservicioPrincipales(session);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return lisPetservicio;
	}
}
