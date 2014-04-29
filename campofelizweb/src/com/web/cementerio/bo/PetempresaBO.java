package com.web.cementerio.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetempresaDAO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.HibernateUtil;

public class PetempresaBO {
	PetempresaDAO petempresaDAO = null;
	
	
	public PetempresaBO(){
		petempresaDAO = new PetempresaDAO();
	}
	
	
	public Petempresa getPetempresabyTipo(int idestado, int tipo) throws Exception{
		Petempresa petempresa =null;
		Session session=null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petempresa = petempresaDAO.getPetempresabyTipo(session, idestado, tipo);
				
		} catch (Exception e) {
			throw new Exception(e);
		}finally{
			session.close();
		}
		return petempresa;
	}

	public Petempresa getPetempresabyId(int idestado, int idempresa) throws Exception{
		Petempresa petempresa =null;
		Session session=null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petempresa = petempresaDAO.getPetempresabyId(session, idestado, idempresa);
				
		} catch (Exception e) {
			throw new Exception(e);
		}finally{
			session.close();
		}
		return petempresa;
	}
	
	
	
	public List<Petempresa>Listpetempresa(int idestado)throws Exception{
		List<Petempresa> listpetempresa=null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetempresa = (List<Petempresa>) petempresaDAO.getListPetempresa(session, idestado);
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		finally{
			 session.close();
		}
		
		return listpetempresa; 
	}
	
	
	public void grabar(Petempresa petempresa, int idestado)throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			petempresa.setIdempresa(petempresaDAO.getMaxidpetempresa(session));
		    
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petempresa.setSetestado(setestado);
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petempresa.setSetusuario(setusuario);
			
			//Auditoria
			petempresa.setFecharegistro(fecharegistro);
			petempresa.setIplog(usuarioBean.getIp());
			
			
			petempresaDAO.grabar(session, petempresa);
			session.beginTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	public void eliminar(Petempresa petempresa, int idestado)throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fechamodificacion = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petempresa.setSetestado(setestado);
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petempresa.setSetusuario(setusuario);
			
			//Auditoria
			petempresa.setFechamodificacion(fechamodificacion);
			petempresa.setIplog(usuarioBean.getIp());
			
			
			petempresaDAO.modificar(session, petempresa);
			session.beginTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
		
	
	public boolean modificar(Petempresa petempresa,Petempresa petempresaclone, int idestado)throws Exception{
		Session session = null;
		boolean ok = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!petempresa.equals(petempresaclone)){
				Date fechamodificacion = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
			    
				Setestado setestado = new Setestado();
				setestado.setIdestado(idestado);
				petempresa.setSetestado(setestado);
				
				Setusuario setusuario = new Setusuario();
				setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				petempresa.setSetusuario(setusuario);
				
				//Auditoria
				petempresa.setFechamodificacion(fechamodificacion);
				petempresa.setIplog(usuarioBean.getIp());
				
				
				petempresaDAO.modificar(session, petempresa);
				session.beginTransaction().commit();
				ok= true;
			}	
			return ok;
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	public PetempresaDAO getPetempresaDAO() {
		return petempresaDAO;
	}

	public void setPetempresaDAO(PetempresaDAO petempresaDAO) {
		this.petempresaDAO = petempresaDAO;
	}
	

}
