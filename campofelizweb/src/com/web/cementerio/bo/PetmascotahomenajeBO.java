package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetmascotahomenajeDAO;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetmascotahomenajeBO {
	
	PetmascotahomenajeDAO petmascotahomenajeDAO;
	
	public  PetmascotahomenajeBO(){
		petmascotahomenajeDAO = new PetmascotahomenajeDAO();
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
	
	
	public void ingresarPetmascotahomenajeBO(Petmascotahomenaje petmascotahomenaje) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("UsuarioBean");
			
			Integer idmascota = 0;
			idmascota= petmascotahomenajeDAO.getMaxidpetmascotahomenaje(session);
			petmascotahomenaje.setIdmascota(idmascota);
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(1);
			petmascotahomenaje.setSetestado(setestado);	
	
			//petmascotahomenaje.getPetfotomascotas().add(new Petfotomascota());
			
			//Auditoria
			petmascotahomenaje.setFecharegistro(fecharegistro);
			//petmascotahomenaje.setIplog(usuarioBean.getIp());
			
			
			petmascotahomenajeDAO.ingresarPetmascotahomenaje(session, petmascotahomenaje);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	

}
