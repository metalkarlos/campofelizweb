package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotoguiaDAO;
import com.web.cementerio.dao.PetguiaDAO;
import com.web.cementerio.pojo.annotations.Petfotoguia;
import com.web.cementerio.pojo.annotations.Petguia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetguiaBO {
	
PetguiaDAO petguiaDAO;
	
	public  PetguiaBO(){
		petguiaDAO = new PetguiaDAO();
	}
	
	public Petguia getPetguiabyId(int idguia, int idestado)throws Exception{
		Petguia petguia = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petguia = petguiaDAO.getPetguiaById(session, idguia, idestado);
		} catch (Exception e) {
			throw new Exception(e); 
		}
		finally{
			session.close();
		}
		return petguia;
	}
	

	public List<Petguia> getlistPetguiaPrincipal(boolean principal, int idestado) throws Exception{
		List<Petguia> listPetguia=null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listPetguia = petguiaDAO.getListpetguiaByPrincipal(session, principal, idestado);
		} catch (Exception e) {
			throw new Exception(e);
		}
		finally{
			session.close();
		}
		return listPetguia;
		
	}
	
	
	public List<Petguia> lisPetguiaBusquedaByPage(String[] texto, int pageSize, int pageNumber, int args[], int idestado) throws RuntimeException {
		List<Petguia> listpetguia = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetguia = petguiaDAO.lisPetguiaBusquedaByPage(session, texto, pageSize, pageNumber, args, idestado);
			
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
		finally{
			session.close();
		}
		
		
		return listpetguia;
	}
	
	
	
	public boolean ingresarPetguiaBO(Petguia petguia,int idestado, UploadedFile uploadedFile) throws Exception{
		Session session = null;
		boolean ok = false;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Integer idguia = 0;
			idguia= petguiaDAO.getMaxidpetguia(session);
			petguia.setIdguia(idguia);
			
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petguia.setSetestado(setestado);	
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petguia.setSetusuario(setusuario);
	
			
			//Auditoria
			petguia.setFecharegistro(fecharegistro);
			petguia.setIplog(usuarioBean.getIp());
			
			if(petguia.getRutafoto() ==null){
			 petguia.setRutafoto("/mascota/huella.jpg");	
			}
			
			petguiaDAO.savePetguia(session, petguia);
			
			if(uploadedFile !=null){
			  ingresarPetfotoguia(session, 1, petguia,uploadedFile);
			}
			
			session.getTransaction().commit();
			ok = true;
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
		return ok;
	}
	
	public boolean modificar(Petguia petguia,Petguia petguiaclone,List<Petfotoguia> listPetfotoguia, List<Petfotoguia> listPetfotoguiaclone,int idestado,UploadedFile uploadedFile) throws Exception{
		Session session = null;
		boolean ok = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!petguia.equals(petguiaclone)){
			
				//auditoria
				Date fechamodificacion= new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				petguia.setFechamodificacion(fechamodificacion);
				petguia.setIplog(usuarioBean.getSetUsuario().getIplog());
				
				Setusuario setusuario = new Setusuario();
				setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				petguia.setSetusuario(setusuario);
		
				
				petguiaDAO.updatePetguia(session, petguia);
				ok = true;
			}
			if(uploadedFile !=null){
			   ingresarPetfotoguia(session, 1, petguia,uploadedFile);
			   ok = true;
			}
			
			
			if( ((!listPetfotoguiaclone.isEmpty()) && (!listPetfotoguia.isEmpty())) && (listPetfotoguia.size() != listPetfotoguiaclone.size())){
		       modificarPetfotoguia(session,idestado, listPetfotoguia, listPetfotoguiaclone,petguia, uploadedFile) ;
		       ok = true;
			}	
			
			
			if(ok){
				session.getTransaction().commit();
			}
			return ok;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	
	public void eliminarBO(Petguia petguia,List<Petfotoguia> listpetfotoguiaclone, int idestado)throws Exception{
		Session session = null;
		PetfotoguiaDAO petfotoguiaDAO = new PetfotoguiaDAO(); 
		FacesUtil facesUtil = new FacesUtil();
		FileUtil fileUtil = new FileUtil();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			
			Date fechamodificacion = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petguia.setSetestado(setestado);	
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petguia.setSetusuario(setusuario);
			
			petguia.setFechamodificacion(fechamodificacion);
			petguia.setIplog(usuarioBean.getSetUsuario().getIplog());
			
			petguiaDAO.updatePetguia(session, petguia);
			
			//inactivar registros asociados en petfotoguia
			if(!listpetfotoguiaclone.isEmpty()){
			   	
				for(Petfotoguia petfotoguia: listpetfotoguiaclone){
					
					//auditoria
					petfotoguia.setFechamodificacion(fechamodificacion);
					petfotoguia.setIplog(usuarioBean.getSetUsuario().getIplog());
					
					setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotoguia.setSetusuario(setusuario);
					
					setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotoguia.setSetestado(setestado);	
			
					petfotoguiaDAO.updatePetfotoguia(session, petfotoguia);
				
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotoguia.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					
					
				}
				
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception();
		}finally{
			session.close();
		}
		
	}

	public void ingresarPetfotoguia(Session session, int idestado, Petguia petguia,  UploadedFile uploadedFile)throws Exception{
		PetfotoguiaDAO petfotoguiaDAO = new PetfotoguiaDAO();
		Petfotoguia petfotoguia = new Petfotoguia();
		try {
			petfotoguia.setPetguia(petguia);
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
					
			petfotoguia.setIdfotoguia(petfotoguiaDAO.maxIdPetfotoguia(session));
				    
		    Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petfotoguia.setSetestado(setestado);
					
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petfotoguia.setSetusuario(setusuario);
					
			//Auditoria
			petfotoguia.setFecharegistro(fecharegistro);
			petfotoguia.setIplog(usuarioBean.getSetUsuario().getIplog());
					
			
					
			//foto en disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
					
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  "/guia/" + fecha.get(Calendar.YEAR);
			String nombreArchivo = fecha.get(Calendar.MONTH) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + fecha.get(Calendar.YEAR) + "-" + petguia.getIdguia() + "-" + petfotoguia.getIdfotoguia() + "-" + uploadedFile.getFileName().toLowerCase();
					
			String rutaCompleta = rutaImagenes + rutaMascota;
			//asignar ruta y nombre de archivo en objeto
			petfotoguia.setRuta(rutaMascota+"/"+nombreArchivo);
			petfotoguia.setNombrearchivo(uploadedFile.getFileName().toLowerCase());
			
			petfotoguiaDAO.savePetfotoguia(session, petfotoguia);
			
			if(fileUtil.createDir(rutaCompleta)){
				//crear foto en disco
				String rutaArchivo = rutaCompleta + "/" + nombreArchivo;
				fileUtil.createFile(rutaArchivo,uploadedFile.getContents());
			}
					
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	
	}
	
	
	public void modificarPetfotoguia(Session session,int idestado,List<Petfotoguia> lisPetfotoguia,List<Petfotoguia> lisPetfotoguiaclone,  Petguia petguia,  UploadedFile uploadedFile)throws Exception{
		PetfotoguiaDAO petfotoguiaDAO = new PetfotoguiaDAO();
		FacesUtil facesUtil = new FacesUtil();
		FileUtil fileUtil = new FileUtil();
		
		try {
			for (Petfotoguia petfotoguia : lisPetfotoguiaclone){
				
				if(!lisPetfotoguia.contains(petfotoguia)){
					
					petfotoguia.setPetguia(petguia);
						
					Date fechamodificacion= new Date();
					UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
						
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotoguia.setSetestado(setestado);
						
					Setusuario setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotoguia.setSetusuario(setusuario);
						
					//Auditoria
					petfotoguia.setFechamodificacion(fechamodificacion);
					petfotoguia.setIplog(usuarioBean.getSetUsuario().getIplog());
					petfotoguiaDAO.updatePetfotoguia(session, petfotoguia);
						
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotoguia.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					
				}
					
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	}

	public PetguiaDAO getPetguiaDAO() {
		return petguiaDAO;
	}

	public void setPetguiaDAO(PetguiaDAO petguiaDAO) {
		this.petguiaDAO = petguiaDAO;
	}

}
