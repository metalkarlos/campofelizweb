package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotomascotaDAO;
import com.web.cementerio.dao.PetmascotahomenajeDAO;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotahomenajeBO {
	
	PetmascotahomenajeDAO petmascotahomenajeDAO;
	
	public  PetmascotahomenajeBO(){
		petmascotahomenajeDAO = new PetmascotahomenajeDAO();
	}
	
	public Petmascotahomenaje getPetmascotahomenajebyId(int idmascota, int idestado,boolean especie)throws Exception{
		Petmascotahomenaje petmascotahomenaje = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petmascotahomenaje = petmascotahomenajeDAO.getPethomenajemascotaById(session,idmascota, idestado,especie);
		} catch (Exception e) {
			throw new Exception(e); 
		}
		finally{
			session.close();
		}
		return petmascotahomenaje;
	}
	
	public List<Petmascotahomenaje> getListpetmascotahomenaje(int idestado) throws Exception{
		List<Petmascotahomenaje> listpetmascotahomenaje = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetmascotahomenaje = petmascotahomenajeDAO.getListpetmascotahomenaje(session, idestado);
		} catch (Exception e) {
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return listpetmascotahomenaje;
	}
	
	public List<Petmascotahomenaje> getListpetmascotahomenajebycriteria(int idestado, int idespecie, String nombre) throws Exception{
		List<Petmascotahomenaje> listpetmascotahomenaje = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetmascotahomenaje = petmascotahomenajeDAO.getListpetmascotabycriteria(session, idestado, idespecie, nombre);
		} catch (Exception e) {
			throw new Exception(e);
		}finally{
			session.close();
		}
	   return listpetmascotahomenaje;
	}
	
	public void modificarPetmascotahomenajeBO(Petmascotahomenaje petmascotahomenaje,int idestado) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			//auditoria
			Date fechamodificacion= new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			petmascotahomenaje.setFechamodificacion(fechamodificacion);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			petmascotahomenaje.getSetusuario().setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			
			petmascotahomenajeDAO.modificarPetmascotahomenaje(session, petmascotahomenaje);
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	
	public void ingresarPetmascotahomenajeBO(Petmascotahomenaje petmascotahomenaje,List<Petfotomascota> lisPetfotomascota,int idestado) throws Exception{
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Integer idmascota = 0;
			idmascota= petmascotahomenajeDAO.getMaxidpetmascotahomenaje(session);
			petmascotahomenaje.setIdmascota(idmascota);
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petmascotahomenaje.setSetestado(setestado);	
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petmascotahomenaje.setSetusuario(setusuario);
	
			
			//Auditoria
			petmascotahomenaje.setFecharegistro(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			
			
			petmascotahomenajeDAO.ingresarPetmascotahomenaje(session, petmascotahomenaje);
			
			ingresarPetfotomascota(session, lisPetfotomascota, idestado, petmascotahomenaje);
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	

	public void ingresarPetfotomascota(Session session,List<Petfotomascota> lisPetfotomascota, int idestado, Petmascotahomenaje petmascotahomenaje)throws Exception{
		//Petfotomascota petfotomascota =null;
		PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
		try {
			if (!lisPetfotomascota.isEmpty()){
				
				for (Petfotomascota petfotomascota : lisPetfotomascota){
					
					petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
					Date fecharegistro = new Date();
					UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
					
					petfotomascota.setIdfotomascota(petfotomascotaDAO.getMaxidpetfotomascota(session));
				    
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotomascota.setSetestado(setestado);
					
					Setusuario setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotomascota.setSetusuario(setusuario);
					
					//Auditoria
					petfotomascota.setFecharegistro(fecharegistro);
					petmascotahomenaje.setIplog(usuarioBean.getIp());
					
					petfotomascotaDAO.ingresarFotomascota(session, petfotomascota);
				}
				
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}/*finally{
           session.close();
		}*/
	
	}
	
}
