package com.web.cementerio.bo;

import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.dao.PetfotoinstalacionDAO;
import com.web.cementerio.pojo.annotations.Petfotoinstalacion;
import com.web.util.HibernateUtil;

public class PetfotoinstalacionBO {

	PetfotoinstalacionDAO petfotoinstalacionDAO;
	
	public PetfotoinstalacionBO() {
		petfotoinstalacionDAO = new PetfotoinstalacionDAO();
	}
	
	public List<Petfotoinstalacion> lisPetfotoinstalacion() throws Exception {
		List<Petfotoinstalacion> lisPetfotoinstalacion = null;
		Session session = null;
	
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            lisPetfotoinstalacion = petfotoinstalacionDAO.lisPetfotoinstalacion(session);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return lisPetfotoinstalacion;
	}
	
}
