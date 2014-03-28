package com.web.cementerio.bo;

import java.util.Date;

import org.hibernate.Session;

import com.web.cementerio.dao.PetinformacionDAO;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.cementerio.bean.UsuarioBean;
import com.web.util.FacesUtil;
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
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			petinformacion.setSetestado(setestado);
			
			//Auditoría
			petinformacion.setFecharegistro(fecharegistro);
			petinformacion.setIplog(usuarioBean.getIp());
			
			petinformacionDAO.actualizarPetinformacion(session, petinformacion);
			
			session.getTransaction().commit();
		}catch(Exception e){
			 session.getTransaction().rollback();
			 throw new Exception(e);
		}finally{
			session.close();
		}
		
	}
}
