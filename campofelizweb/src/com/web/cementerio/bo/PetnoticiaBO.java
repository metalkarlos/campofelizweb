package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetnoticiaDAO;
import com.web.cementerio.pojo.annotations.Petnoticia;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetnoticiaBO {

	PetnoticiaDAO petnoticiaDAO;
	
	public PetnoticiaBO() {
		petnoticiaDAO = new PetnoticiaDAO();
	}
	
	public Petnoticia getPetnoticiaById(int idnoticia) throws Exception {
		Petnoticia petnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            petnoticia = petnoticiaDAO.getPetnoticiaById(session, idnoticia);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return petnoticia;
	}
	
	public List<Petnoticia> lisPetnoticiaByPage(int pageSize, int pageNumber, int args[], String titulo, String descripcion) throws RuntimeException {
		List<Petnoticia> listPetnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            listPetnoticia = petnoticiaDAO.lisPetnoticiaByPage(session, pageSize, pageNumber, args, titulo, descripcion);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
        finally{
            session.close();
        }
		
		return listPetnoticia;
	}
	
	public boolean grabar(Petnoticia petnoticia) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			int maxid = petnoticiaDAO.maxIdnoticia(session)+1;
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			petnoticia.setIdnoticia(maxid);
			petnoticia.setFecharegistro(fecharegistro);
			petnoticia.setIplog(usuarioBean.getIp());
			petnoticia.setSetestado(usuarioBean.getSetUsuario().getSetestado());
			petnoticia.setSetusuario(usuarioBean.getSetUsuario());
	
			petnoticiaDAO.savePetnoticia(session, petnoticia);
			
			session.getTransaction().commit();
			
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception(e); 
		}finally{
			session.close();
		}
		
		return ok;
	}
}
