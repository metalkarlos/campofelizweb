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
	
	public List<Petservicio> lisPetservicioBusquedaByPage(String[] texto, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petservicio> lisPetservicio = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            lisPetservicio = petservicioDAO.lisPetservicioBusquedaByPage(session, texto, pageSize, pageNumber, args);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
        finally{
            session.close();
        }
		
		return lisPetservicio;
	}
	
	public Petservicio getPetservicioConObjetosById(int idservicio) throws Exception {
		Petservicio petservicio = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            petservicio = petservicioDAO.getPetservicioConObjetosById(session, idservicio);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return petservicio;
	}
}
