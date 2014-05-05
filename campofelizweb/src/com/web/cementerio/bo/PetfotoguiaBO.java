package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;
import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotoguiaDAO;
import com.web.cementerio.pojo.annotations.Petfotoguia;
import com.web.cementerio.pojo.annotations.Petguia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetfotoguiaBO {
	
	PetfotoguiaDAO petfotoguiaDAO = new PetfotoguiaDAO();

	
	public void ingresarPetfotoguiaBO(List<Petfotoguia> lisPetfotoguia, int idestado, Petguia petguia,UploadedFile uploadedFile)throws Exception{
		Session session = null;
		
		try {
			if (!lisPetfotoguia.isEmpty()){
				
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				
				for (Petfotoguia petfotoguia :lisPetfotoguia){
					
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
					petfotoguia.setIplog(usuarioBean.getIp());
					
					petfotoguiaDAO.savePetfotoguia(session, petfotoguia);
					
					
					//foto en disco
					FileUtil fileUtil = new FileUtil();
					Calendar fecha = Calendar.getInstance();
					
					String rutaImagenes = fileUtil.getPropertyValue("rutaImagen");
					String rutaGuia =  "/guia/" + fecha.get(Calendar.YEAR);
					String nombreArchivo = fecha.get(Calendar.MONTH) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + fecha.get(Calendar.YEAR) + "-" + petguia.getIdguia() + "-" + petguia.getIdguia() + "-" + fileUtil.getFileExtention(uploadedFile.getFileName());
					
					String rutaCompleta = rutaImagenes + rutaGuia;
					
					if(fileUtil.createDir(rutaCompleta)){
						//crear foto en disco
						fileUtil.createFile(rutaGuia+"/"+nombreArchivo,uploadedFile.getContents());
					}
				
				}
				
			}
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		}finally{
           session.close();
		}
	
	}
	
	
	public void modificarPetfotoguiaBO(List<Petfotoguia> lisPetfotoguia, int idestado, Petguia petguia,UploadedFile uploadedFile)throws Exception{
		Session session = null;
		
		try{
               if (!lisPetfotoguia.isEmpty()){
				
				session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				
				
				for (Petfotoguia petfotoguia:lisPetfotoguia){
					
					petfotoguia.setPetguia(petguia);
					
					Date fechamodificacion= new Date();
					
					Setestado setestado = new Setestado();
					setestado.setIdestado(idestado);
					
					
					//Auditoria
					petfotoguia.setFechamodificacion(fechamodificacion);
					petfotoguiaDAO.updatePetfotoguia(session, petfotoguia);
					
					//eliminar foto del disco
					Calendar fecha = Calendar.getInstance();
					FileUtil fileUtil = new FileUtil();
					String rutaImagenes = fileUtil.getPropertyValue("rutaImagen");
					String rutaGuia =  "/guia/" + fecha.get(Calendar.YEAR);
					String nombreArchivo = fecha.get(Calendar.MONTH) + "-" + fecha.get(Calendar.DAY_OF_MONTH) + "-" + fecha.get(Calendar.YEAR) + "-" + petguia.getIdguia() + "-" + petfotoguia.getIdfotoguia() + "-" + fileUtil.getFileExtention(uploadedFile.getFileName());
					
					String rutaCompleta = rutaImagenes + rutaGuia;
					
					fileUtil.deleteFile(rutaCompleta+"/"+nombreArchivo);
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
	
	
	

	public PetfotoguiaDAO getPetfotoguiaDAO() {
		return petfotoguiaDAO;
	}


	public void setPetfotoguiaDAO(PetfotoguiaDAO petfotoguiaDAO) {
		this.petfotoguiaDAO = petfotoguiaDAO;
	}

}
