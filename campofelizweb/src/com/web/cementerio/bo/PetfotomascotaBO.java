package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;


import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotomascotaDAO;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetfotomascotaBO {
	PetfotomascotaDAO petfotomascotaDAO = null;
	
	public PetfotomascotaBO(){
		petfotomascotaDAO = new PetfotomascotaDAO();
	}
	
	public void ingresarPetfotomascotaBO(List<Petfotomascota> lisPetfotomascota, int idestado, int idmascota)throws Exception{
		Session session = null;
		Petfotomascota petfotomascota =null;
		try {
			if (!lisPetfotomascota.isEmpty()){
				
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				
				for (int i =0; i<lisPetfotomascota.size(); i++){
					
					petfotomascota = new Petfotomascota();
					
					Petmascotahomenaje petmascotahomenaje = new Petmascotahomenaje();
					petmascotahomenaje.setIdmascota(idmascota);
					petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
					
					Date fecharegistro = new Date();
					UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("UsuarioBean");
					petfotomascota.setIdfotomascota(petfotomascotaDAO.getMaxidpetfotomascota(session));
				    
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					
					//Auditoria
					petfotomascota.setFecharegistro(fecharegistro);
					//petmascotahomenaje.setIplog(usuarioBean.getIp());
					
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

	public PetfotomascotaDAO getPetfotomascotaDAO() {
		return petfotomascotaDAO;
	}

	public void setPetfotomascotaDAO(PetfotomascotaDAO petfotomascotaDAO) {
		this.petfotomascotaDAO = petfotomascotaDAO;
	}

	
	
}
