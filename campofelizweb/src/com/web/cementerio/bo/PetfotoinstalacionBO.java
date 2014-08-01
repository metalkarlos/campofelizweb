package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotoinstalacionDAO;
import com.web.cementerio.pojo.annotations.Petfotoinstalacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetfotoinstalacionBO {

	PetfotoinstalacionDAO petfotoinstalacionDAO;
	
	public PetfotoinstalacionBO() {
		petfotoinstalacionDAO = new PetfotoinstalacionDAO();
	}
	
	public List<Petfotoinstalacion> lisPetfotoinstalacion(int idestado) throws Exception {
		List<Petfotoinstalacion> lisPetfotoinstalacion = null;
		Session session = null;
	
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            lisPetfotoinstalacion = petfotoinstalacionDAO.lisPetfotoinstalacion(session,idestado);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return lisPetfotoinstalacion;
	}
	
	public Petfotoinstalacion getPetfotoinstalacionbyId(int idfotoinstalacion, int idestado)throws Exception{
		Petfotoinstalacion petfotoinstalacion = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			petfotoinstalacion = petfotoinstalacionDAO.getPetfotoinstalacionById(session, idfotoinstalacion, idestado);
		} catch (Exception e) {
			throw new Exception(e); 
		}
		finally{
			session.close();
		}
		return petfotoinstalacion;
	}
	
	public void procesar(UploadedFile uploadedFile, String descripcionFoto,Petfotoinstalacion petfotoinstalacion,List<Petfotoinstalacion>listpetfotoinstalacion,List<Petfotoinstalacion>listpetfotoinstalacionclone)throws Exception{
		Session session = null;
		boolean ok=false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			//ingreso
			if(uploadedFile!=null){
				ingresarPetfotoinstalacion(session,1,petfotoinstalacion,uploadedFile,descripcionFoto);
				ok = true;
			}
			if(listpetfotoinstalacion.size() >0 && listpetfotoinstalacionclone.size() >0){
                //modificacion 
				if (modificarPetfotoinstalacion(session,listpetfotoinstalacion,listpetfotoinstalacionclone)){
				   ok = true;
				}
				//eliminacion
				if(listpetfotoinstalacion.size() != listpetfotoinstalacionclone.size()){
				  eliminarPetfotoinstalacion(session,listpetfotoinstalacion,listpetfotoinstalacionclone,2);
				  ok = true;
				}
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}	
		catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally {
			session.close();
		}	
	}
	
	public void ingresarPetfotoinstalacion (Session session, int idestado,Petfotoinstalacion petfotoinstalacion,  UploadedFile uploadedFile, String descripcionFoto)throws Exception{
		try {
			
			
			Date fecharegistro = new Date();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
					
			petfotoinstalacion.setIdfotoinstalacion(petfotoinstalacionDAO.maxIdfotoinstalacion(session));
				    
		    Setestado setestado = new Setestado();
			setestado.setIdestado(idestado);
			petfotoinstalacion.setSetestado(setestado);
					
			Setusuario setusuario = new Setusuario();
			setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
			petfotoinstalacion.setSetusuario(setusuario);
					
			//Auditoria
			petfotoinstalacion.setFecharegistro(fecharegistro);
			petfotoinstalacion.setIplog(usuarioBean.getIp());
					
			if(descripcionFoto ==null || descripcionFoto.length()>0){
				petfotoinstalacion.setDescripcion(descripcionFoto);	
			}
					
			//foto en disco
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			Calendar fecha = Calendar.getInstance();
				
					
			String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
			String rutaMascota =  "/instalaciones/" + fecha.get(Calendar.YEAR);
			String nombreArchivo = fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + petfotoinstalacion.getIdfotoinstalacion() + "." +fileUtil.getFileExtention(uploadedFile.getFileName()).toLowerCase();
					
			String rutaCompleta = rutaImagenes + rutaMascota;
			//asignar ruta y nombre de archivo en objeto
			petfotoinstalacion.setRuta(rutaMascota+"/"+nombreArchivo);
			petfotoinstalacion.setNombrearchivo(nombreArchivo);
			
			petfotoinstalacionDAO.savePetfotoinstalacion(session, petfotoinstalacion);
			
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
	
	
	public boolean modificarPetfotoinstalacion(Session session,List<Petfotoinstalacion>listpetfotoinstalacion ,List<Petfotoinstalacion>listpetfotoinstalacionclone) throws Exception{
		boolean actualiza=false;
		try {
			for(Petfotoinstalacion petfoto:listpetfotoinstalacion){
				for(Petfotoinstalacion petfotoclone:listpetfotoinstalacionclone){
				  if(petfoto.getIdfotoinstalacion() == petfotoclone.getIdfotoinstalacion()){
					if(!petfoto.equals(petfotoclone)){
						//auditoria
						Date fechamodificacion= new Date();
						UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
						petfoto.setFechamodificacion(fechamodificacion);
						petfoto.setIplog(usuarioBean.getIp());
							
						Setusuario setusuario = new Setusuario();
						setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
						petfoto.setSetusuario(setusuario);
					
						petfotoinstalacionDAO.updatePetfotoinstalacion(session, petfoto);
						actualiza = true;
					}
						
				   }	
					
				}	
			}
			return actualiza;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	}
	
	public void eliminarPetfotoinstalacion(Session session,List<Petfotoinstalacion>listpetfotoinstalacion ,List<Petfotoinstalacion>listpetfotoinstalacionclone, int idestado) throws Exception{
		try {
		   for(Petfotoinstalacion petfoto:listpetfotoinstalacionclone){
			 if(!listpetfotoinstalacion.contains(petfoto)){
				//auditoria
				Date fechamodificacion= new Date();
				UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
				petfoto.setFechamodificacion(fechamodificacion);
				petfoto.setIplog(usuarioBean.getIp());
						
				Setusuario setusuario = new Setusuario();
				setusuario.setIdusuario(usuarioBean.getSetUsuario().getIdusuario());
				petfoto.setSetusuario(setusuario);
				
				Setestado setestado = new Setestado();
				setestado.setIdestado(idestado);
				petfoto.setSetestado(setestado);
							
				petfotoinstalacionDAO.updatePetfotoinstalacion(session, petfoto);
			}	
		  }
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}
	}
	
}
