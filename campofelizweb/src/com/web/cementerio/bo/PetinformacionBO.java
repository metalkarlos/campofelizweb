package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotoinformacionDAO;
import com.web.cementerio.dao.PetinformacionDAO;
import com.web.cementerio.pojo.annotations.Petfotoinformacion;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetinformacionBO {

	PetinformacionDAO petinformacionDAO;
	
	public PetinformacionBO() {
		petinformacionDAO = new PetinformacionDAO();
	}
	
	public Petinformacion getPetinformacionById(int idinformacion, int idestado) throws Exception {
		Petinformacion petinformacion = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			petinformacion = petinformacionDAO.getPetinformacionById(session, idinformacion,idestado);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return petinformacion;
	}
	
	

	public  void modificarPetinformacion(Petinformacion petinformacion,Petinformacion petinformacionclone,List<Petfotoinformacion> listPetfotoinformacion,List<Petfotoinformacion> listPetfotoinformacionclone,
										 UploadedFile uploadedFile,int idestado, String descripcionimagen) throws Exception{
		Session session = null;
		boolean ok = false;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
	
			if(!petinformacion.equals(petinformacionclone)){
			
				Date fechamodificacion = new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				
				Setestado setestado = new Setestado();
				setestado.setIdestado(idestado);
				petinformacion.setSetestado(setestado);
				
				Setusuario setusuario = new Setusuario();
				setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				petinformacion.setSetusuario(setusuario);
				
				//Auditoría
				petinformacion.setFechamodificacion(fechamodificacion);
				petinformacion.setIplog(usuarioBean.getIp());
				
				petinformacionDAO.actualizarPetinformacion(session, petinformacion);
				ok = true;
			}	
			
			if(uploadedFile !=null){
			   ingresarPetfotoinformacion(session, idestado, petinformacion,uploadedFile,descripcionimagen);
			   ok = true;
			}
				
			if(!(listPetfotoinformacion.isEmpty() && listPetfotoinformacionclone.isEmpty()) && (listPetfotoinformacion.size() != listPetfotoinformacionclone.size())){
			   modificarPetfotoinformacion(session, listPetfotoinformacion, listPetfotoinformacionclone, petinformacion, uploadedFile, 2);
			   ok = true;
			}
				
			if(ok){
			  session.getTransaction().commit();
			}
			
		}catch(Exception e){
			 session.getTransaction().rollback();
			 throw new Exception(e);
		}finally{
			session.close();
		}
		
	}
	
	
	public void ingresarPetfotoinformacion(Session session, int idestado, Petinformacion petinformacion, UploadedFile uploadedFile, String descripcionimagen)throws Exception{
		
		PetfotoinformacionDAO petfotoinformacionDAO = new PetfotoinformacionDAO();
		Petfotoinformacion petfotoinformacion = new Petfotoinformacion();
		
		try {
			petfotoinformacion.setPetinformacion(petinformacion);
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
					
			petfotoinformacion.setIdfotoinformacion(petfotoinformacionDAO.getMaxidpetfotoinformacion(session));
				    
		    Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petfotoinformacion.setSetestado(setestado);
					
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petfotoinformacion.setSetusuario(setusuario);
					
			//Auditoria
			petfotoinformacion.setFecharegistro(fecharegistro);
			petfotoinformacion.setIplog(usuarioBean.getSetUsuario().getIplog());
					
			
					
			//foto en disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
				
					
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  "/quienessomos/" + fecha.get(Calendar.YEAR);
			String nombreArchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + petinformacion.getIdinformacion() + "-" + petfotoinformacion.getIdfotoinformacion() +  "." +fileUtil.getFileExtention(uploadedFile.getFileName()).toLowerCase();
					
			String rutaCompleta = rutaImagenes + rutaMascota;
			//asignar ruta y nombre de archivo en objeto
			petfotoinformacion.setRuta(rutaMascota+"/"+nombreArchivo);
			petfotoinformacion.setNombrearchivo(uploadedFile.getFileName().toLowerCase());
			
			petfotoinformacionDAO.ingresarFotoinformacion(session, petfotoinformacion);
			
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
	
	
	public void modificarPetfotoinformacion(Session session,List<Petfotoinformacion> lisPetfotoinformacion,List<Petfotoinformacion> lisPetfotoinformacionclone,  
			                               Petinformacion petinformacion,  UploadedFile uploadedFile,int idestado)throws Exception{
		PetfotoinformacionDAO petfotoinformacionDAO = new PetfotoinformacionDAO();
		FacesUtil facesUtil = new FacesUtil();
		FileUtil fileUtil = new FileUtil();
		try {
			for (Petfotoinformacion petfotoinformacion : lisPetfotoinformacionclone){
				
				if(!lisPetfotoinformacion.contains(petfotoinformacion)){
					
					petfotoinformacion.setPetinformacion(petinformacion);
						
					Date fechamodificacion= new Date();
					UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
						
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					petfotoinformacion.setSetestado(setestado);
						
					Setusuario setusuario = new Setusuario();
					setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
					petfotoinformacion.setSetusuario(setusuario);
						
					//Auditoria
					petfotoinformacion.setFechamodificacion(fechamodificacion);
					petfotoinformacion.setIplog(usuarioBean.getSetUsuario().getIplog());
					petfotoinformacionDAO.modificarFotomascota(session, petfotoinformacion);
						
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotoinformacion.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					
				}
					
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	}
	
}
