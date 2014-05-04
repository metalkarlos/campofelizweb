package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotomascotaDAO;
import com.web.cementerio.dao.PetmascotahomenajeDAO;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;

import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
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
	
	public List<Petmascotahomenaje> lisPetmascotahomenajeBusquedaByPage(String[] texto, int pageSize, int pageNumber, int args[], int idestado) throws RuntimeException {
		List<Petmascotahomenaje> listpetmascotahomenaje = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			listpetmascotahomenaje = petmascotahomenajeDAO.lisPetmascotaBusquedaByPage(session, texto, pageSize, pageNumber, args, idestado);
			
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
		finally{
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
	
	
	public void eliminarBO(Petmascotahomenaje petmascotahomenaje,List<Petfotomascota> listpetfotomascotaclone, int idestado)throws Exception{
		Session session = null;
		PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO(); 
		FacesUtil facesUtil = new FacesUtil();
		FileUtil fileUtil = new FileUtil();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			
			Date fechamodificacion = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petmascotahomenaje.setSetestado(setestado);	
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petmascotahomenaje.setSetusuario(setusuario);
			
			petmascotahomenaje.setFechamodificacion(fechamodificacion);
			petmascotahomenaje.setIplog(usuarioBean.getSetUsuario().getIplog());
			
			petmascotahomenajeDAO.modificarPetmascotahomenaje(session, petmascotahomenaje);
			
			//inactivar registros asociados en petfotomascotahomenaje
			if(!listpetfotomascotaclone.isEmpty()){
			   	
				for(Petfotomascota petfotomascota: listpetfotomascotaclone){
					
					//auditoria
					petfotomascota.setFechamodificacion(fechamodificacion);
					petfotomascota.setIplog(usuarioBean.getSetUsuario().getIplog());
					
					setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotomascota.setSetusuario(setusuario);
					
					setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotomascota.setSetestado(setestado);	
			
					petfotomascotaDAO.modificarFotomascota(session, petfotomascota);
				
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotomascota.getRuta();
					
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
	
	
	public void ingresarPetmascotahomenajeBO(Petmascotahomenaje petmascotahomenaje,int idestado, UploadedFile uploadedFile) throws Exception{
		Session session = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			Integer idmascota = 0;
			idmascota= petmascotahomenajeDAO.getMaxidpetmascotahomenaje(session);
			petmascotahomenaje.setIdmascota(idmascota);
			petmascotahomenaje.setNombre(petmascotahomenaje.getNombre().toUpperCase());
			
			Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petmascotahomenaje.setSetestado(setestado);	
			
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petmascotahomenaje.setSetusuario(setusuario);
	
			
			//Auditoria
			petmascotahomenaje.setFecharegistro(fecharegistro);
			petmascotahomenaje.setIplog(usuarioBean.getIp());
			
			if(uploadedFile ==null){
			 petmascotahomenaje.setRutafoto("/mascota/huella.jpg");	
			}
			
			petmascotahomenajeDAO.ingresarPetmascotahomenaje(session, petmascotahomenaje);
			
			if(uploadedFile !=null){
			  ingresarPetfotomascota(session, 1, petmascotahomenaje,uploadedFile);
			}
			
			session.getTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}
	}
	
	public boolean modificarPetmascotahomenajeBO(Petmascotahomenaje petmascotahomenaje,Petmascotahomenaje petmascotahomenajeclone,UploadedFile uploadedFile,
											  List<Petfotomascota> listPetfotomascota, List<Petfotomascota> listPetfotomascotaclone,int idestado) throws Exception{
		Session session = null;
		boolean ok = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			if(!petmascotahomenaje.equals(petmascotahomenajeclone)){
			
				//auditoria
				Date fechamodificacion= new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				petmascotahomenaje.setFechamodificacion(fechamodificacion);
				petmascotahomenaje.setIplog(usuarioBean.getSetUsuario().getIplog());
				
				Setusuario setusuario = new Setusuario();
				setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				petmascotahomenaje.setSetusuario(setusuario);
		
				
				petmascotahomenajeDAO.modificarPetmascotahomenaje(session, petmascotahomenaje);
				ok = true;
			}
			if(uploadedFile !=null){
			   ingresarPetfotomascota(session, 1, petmascotahomenaje,uploadedFile);
			   ok = true;
			}
			
			if(!(listPetfotomascotaclone.isEmpty() && listPetfotomascotaclone.isEmpty()) && (listPetfotomascota.size() != listPetfotomascotaclone.size())){
		       modificarPetfotomascota(session,idestado, listPetfotomascota, listPetfotomascotaclone,petmascotahomenaje, uploadedFile) ;
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
	
	

	public void ingresarPetfotomascota(Session session, int idestado, Petmascotahomenaje petmascotahomenaje,  UploadedFile uploadedFile)throws Exception{
		PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
		Petfotomascota petfotomascota = new Petfotomascota();
		try {
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
			petfotomascota.setIplog(usuarioBean.getSetUsuario().getIplog());
					
			
					
			//foto en disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
					
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  "/mascota/" + fecha.get(Calendar.YEAR);
			String nombreArchivo = fecha.get(Calendar.MONTH) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + fecha.get(Calendar.YEAR) + "-" + petmascotahomenaje.getIdmascota() + "-" + petfotomascota.getIdfotomascota() + "-" + uploadedFile.getFileName().toLowerCase();
					
			String rutaCompleta = rutaImagenes + rutaMascota;
			//asignar ruta y nombre de archivo en objeto
			petfotomascota.setRuta(rutaMascota+"/"+nombreArchivo);
			petfotomascota.setNombrearchivo(uploadedFile.getFileName().toLowerCase());
			
			petfotomascotaDAO.ingresarFotomascota(session, petfotomascota);
			
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
	
	
	public void modificarPetfotomascota(Session session,int idestado,List<Petfotomascota> lisPetfotomascota,List<Petfotomascota> lisPetfotomascotaclone,  Petmascotahomenaje petmascotahomenaje,  UploadedFile uploadedFile)throws Exception{
		PetfotomascotaDAO petfotomascotaDAO = new PetfotomascotaDAO();
		FacesUtil facesUtil = new FacesUtil();
		FileUtil fileUtil = new FileUtil();
		try {
			for (Petfotomascota petfotomascota : lisPetfotomascotaclone){
				
				if(!lisPetfotomascota.contains(petfotomascota)){
					
					petfotomascota.setPetmascotahomenaje(petmascotahomenaje);
						
					Date fechamodificacion= new Date();
					UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
						
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotomascota.setSetestado(setestado);
						
					Setusuario setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotomascota.setSetusuario(setusuario);
						
					//Auditoria
					petfotomascota.setFechamodificacion(fechamodificacion);
					petfotomascota.setIplog(usuarioBean.getSetUsuario().getIplog());
					petfotomascotaDAO.modificarFotomascota(session, petfotomascota);
						
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotomascota.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					
				}
					
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	}
	
}
