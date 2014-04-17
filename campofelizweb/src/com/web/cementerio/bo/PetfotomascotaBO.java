package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;


import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotomascotaDAO;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetfotomascotaBO {
	PetfotomascotaDAO petfotomascotaDAO = null;
	
	public PetfotomascotaBO(){
		petfotomascotaDAO = new PetfotomascotaDAO();
	}
	
	public List<Petfotomascota>getListpetfotomascota (int idmascota, int idestado)throws Exception{
		Session session = null;
		List<Petfotomascota> listpetfotomascota = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetfotomascota = petfotomascotaDAO.getListpetfotomascota(session, idmascota, idestado);			
		} catch (Exception e) {
			throw new Exception(e);
		}finally{
			session.close();	
		}
		return listpetfotomascota;
	}
	
	public void ingresarPetfotomascotaBO(List<Petfotomascota> lisPetfotomascota, int idestado, Petmascotahomenaje petmascotahomenaje)throws Exception{
		Session session = null;
		
		try {
			if (!lisPetfotomascota.isEmpty()){
				
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				
				for (Petfotomascota petfotomascota :lisPetfotomascota){
					
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
		}finally{
           session.close();
		}
	
	}
	
	
	public void modificarPetfotomascotaBO(List<Petfotomascota> lisPetfotomascota, int idestado, Petmascotahomenaje petmascotahomenaje)throws Exception{
		Session session = null;
		
		try{
               if (!lisPetfotomascota.isEmpty()){
				
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				
				for (Petfotomascota petfotomascota:lisPetfotomascota){
					
					petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
					
					Date fechamodificacion= new Date();
					
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					
					
					//Auditoria
					petfotomascota.setFechamodificacion(fechamodificacion);
					petfotomascotaDAO.modificarFotomascota(session, petfotomascota);
				}
				
			}
		}
		catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
           session.close();
		}
	}
	
	
	public PetfotomascotaDAO getPetfotomascotaDAO() {
		return petfotomascotaDAO;
	}

	public void setPetfotomascotaDAO(PetfotomascotaDAO petfotomascotaDAO) {
		this.petfotomascotaDAO = petfotomascotaDAO;
	}

	
	
}
