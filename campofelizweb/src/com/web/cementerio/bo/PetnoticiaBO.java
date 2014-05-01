package com.web.cementerio.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bean.UsuarioBean;
import com.web.cementerio.dao.PetfotonoticiaDAO;
import com.web.cementerio.dao.PetnoticiaDAO;
import com.web.cementerio.pojo.annotations.Petfotonoticia;
import com.web.cementerio.pojo.annotations.Petnoticia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.HibernateUtil;

public class PetnoticiaBO {

	private PetnoticiaDAO petnoticiaDAO;
	
	public PetnoticiaBO() {
		petnoticiaDAO = new PetnoticiaDAO();
	}
	
	public Petnoticia getPetnoticiaById(int idnoticia) throws Exception {
		Petnoticia petnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            petnoticia = petnoticiaDAO.getPetnoticiaById(session, idnoticia);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return petnoticia;
	}
	
	public Petnoticia getPetnoticiaConObjetosById(int idnoticia) throws Exception {
		Petnoticia petnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            petnoticia = petnoticiaDAO.getPetnoticiaConObjetosById(session, idnoticia);
        }
        catch(Exception ex){
            throw new Exception(ex);
        }
        finally{
            session.close();
        }
		
		return petnoticia;
	}
	
	public List<Petnoticia> lisPetnoticiaByPage(int pageSize, int pageNumber, int args[], String titulo, String descripcion) throws RuntimeException {
		List<Petnoticia> listPetnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            listPetnoticia = petnoticiaDAO.lisPetnoticiaByPage(session, pageSize, pageNumber, args, titulo, descripcion);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
        finally{
            session.close();
        }
		
		return listPetnoticia;
	}
	
	public List<Petnoticia> lisPetnoticiaBusquedaByPage(String[] texto, int pageSize, int pageNumber, int args[]) throws RuntimeException {
		List<Petnoticia> listPetnoticia = null;
		Session session = null;
		
		try{
            session = HibernateUtil.getSessionFactory().openSession();
            listPetnoticia = petnoticiaDAO.lisPetnoticiaBusquedaByPage(session, texto, pageSize, pageNumber, args);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
        finally{
            session.close();
        }
		
		return listPetnoticia;
	}
	
	public List<Petnoticia> lisPetnoticiaPrincipales() throws Exception {
		List<Petnoticia> lisPetnoticia = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			lisPetnoticia = petnoticiaDAO.lisPetnoticiaPrincipales(session);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return lisPetnoticia;
	}
	
	public boolean ingresar(Petnoticia petnoticia, Petfotonoticia petfotonoticia, UploadedFile uploadedFile) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			//noticia
			int maxIdnoticia = petnoticiaDAO.maxIdnoticia(session)+1;
			petnoticia.setIdnoticia(maxIdnoticia);
			Setestado setestadoPetnoticia = new Setestado();
			setestadoPetnoticia.setIdestado(1);
			petnoticia.setSetestado(setestadoPetnoticia);

			//auditoria
			Date fecharegistro = new Date();
			petnoticia.setFecharegistro(fecharegistro);
			petnoticia.setIplog(usuarioBean.getIp());
			petnoticia.setSetusuario(usuarioBean.getSetUsuario());
	
			//ingresar noticia
			petnoticiaDAO.savePetnoticia(session, petnoticia);
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(petnoticia, petfotonoticia, uploadedFile, session);
			}
			
			session.getTransaction().commit();
			
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception(e); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean eliminar(Petnoticia petnoticia) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			
			//se inactiva el registro
			petnoticia.getSetestado().setIdestado(2);
			
			//auditoria
			Date fecharegistro = new Date();
			petnoticia.setFechamodificacion(fecharegistro);
			petnoticia.setIplog(usuarioBean.getIp());
			petnoticia.setSetusuario(usuarioBean.getSetUsuario());
			
			petnoticiaDAO.updatePetnoticia(session, petnoticia);
			
			session.getTransaction().commit();
			
			ok = true;
		}catch(Exception e){
			session.getTransaction().rollback();
			throw new Exception(e); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	public boolean modificar(Petnoticia petnoticia, Petnoticia petnoticiaClon, List<Petfotonoticia> lisPetfotonoticia, List<Petfotonoticia> lisPetfotonoticiaClon, Petfotonoticia petfotonoticia, UploadedFile uploadedFile) throws Exception {
		boolean ok = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			PetfotonoticiaDAO petfotonoticiaDAO = new PetfotonoticiaDAO();
			Date fecharegistro = new Date();
			FileUtil fileUtil = new FileUtil();
			FacesUtil facesUtil = new FacesUtil();
			
			//Se graba la noticia si han habido cambios
			if(!petnoticia.equals(petnoticiaClon)){
				//auditoria
				petnoticia.setFechamodificacion(fecharegistro);
				petnoticia.setIplog(usuarioBean.getIp());
				petnoticia.setSetusuario(usuarioBean.getSetUsuario());
		
				//actualizar
				petnoticiaDAO.updatePetnoticia(session, petnoticia);
				ok = true;
			}
			
			//Si han quitado fotos se las inhabilita en la BD
			for(Petfotonoticia petfotonoticiaItem : lisPetfotonoticiaClon){
				if(!lisPetfotonoticia.contains(petfotonoticiaItem)){ 
					//inhabilitar
					petfotonoticiaItem.getSetestado().setIdestado(2);
					
					//auditoria
					fecharegistro = new Date();
					petfotonoticiaItem.setFechamodificacion(fecharegistro);
					petfotonoticiaItem.setIplog(usuarioBean.getIp());
					petfotonoticiaItem.setSetusuario(usuarioBean.getSetUsuario());
					
					//actualizar
					petfotonoticiaDAO.updatePetfotonoticia(session, petfotonoticiaItem);
					
					//eliminar foto del disco
					String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
					
					String rutaArchivo = rutaImagenes + petfotonoticiaItem.getRuta();
					
					fileUtil.deleteFile(rutaArchivo);
					ok = true;
				}
			}
			
			//Si subio foto se crea en disco y en base
			if(uploadedFile != null){
				creaFotoDiscoBD(petnoticia, petfotonoticia, uploadedFile, session);
				ok = true;
			}
			
			if(ok){
				session.getTransaction().commit();
			}
		}catch(Exception e){
			ok = false;
			session.getTransaction().rollback();
			throw new Exception(e); 
		}finally{
			session.close();
		}
		
		return ok;
	}
	
	private void creaFotoDiscoBD(Petnoticia petnoticia, Petfotonoticia petfotonoticia, UploadedFile uploadedFile, Session session) throws Exception {
		UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
		PetfotonoticiaDAO petfotonoticiaDAO = new PetfotonoticiaDAO();
		
		int maxIdfotonoticia = petfotonoticiaDAO.maxIdfotonoticia(session)+1;
		
		//foto en disco
		FileUtil fileUtil = new FileUtil();
		FacesUtil facesUtil = new FacesUtil();
		Calendar fecha = Calendar.getInstance();
		
		String rutaImagenes = facesUtil.getContextParam("imagesDirectory");
		String rutaNoticias =  "/noticia/" + fecha.get(Calendar.YEAR);
		String nombreArchivo = fecha.get(Calendar.DAY_OF_MONTH) + "-" + fecha.get(Calendar.MONTH) + "-" + fecha.get(Calendar.YEAR) + "-" + petnoticia.getIdnoticia() + "-" + maxIdfotonoticia + "." + fileUtil.getFileExtention(uploadedFile.getFileName()).toLowerCase();
		
		String rutaCompleta = rutaImagenes + rutaNoticias;
		
		if(fileUtil.createDir(rutaCompleta)){
			//crear foto en disco
			String rutaArchivo = rutaCompleta + "/" + nombreArchivo;
			fileUtil.createFile(rutaArchivo,uploadedFile.getContents());
		}
		
		//foto en BD
		petfotonoticia.setIdfotonoticia(maxIdfotonoticia);
		petfotonoticia.setPetnoticia(petnoticia);
		String rutaBD = rutaNoticias + "/" + nombreArchivo;
		petfotonoticia.setRuta(rutaBD);
		petfotonoticia.setNombrearchivo(nombreArchivo);
		petfotonoticia.setPerfil(1);//campo sin uso ya que tabla principal posee ruta de foto de perfil
		Setestado setestadoPetfotonoticia = new Setestado();
		setestadoPetfotonoticia.setIdestado(1);
		petfotonoticia.setSetestado(setestadoPetfotonoticia);
		
		//auditoria
		Date fecharegistro = new Date();
		petfotonoticia.setFecharegistro(fecharegistro);
		petfotonoticia.setIplog(usuarioBean.getIp());
		petfotonoticia.setSetusuario(usuarioBean.getSetUsuario());
		
		//ingresar foto en BD
		petfotonoticiaDAO.savePetfotonoticia(session, petfotonoticia);
	}
}
