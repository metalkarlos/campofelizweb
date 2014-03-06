package com.web.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.hibernate.Session;

import com.web.cementerio.global.Parametro;

public class Utilities {
	
	public void imprimirJasperPdf(String nombreReporte, Map<String, Object> parametros) throws Exception {
		
		try{
			String rutaReporteOrigen = Parametro.RUTA_REPORTES+nombreReporte+".jasper";
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Connection connection = session.connection();
	        
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporteOrigen, parametros, connection);//new JREmptyDataSource());
			
			//crea en disco
			String rutaReporteDestino = System.getProperty("java.io.tmpdir");
			JasperExportManager.exportReportToPdfFile(jasperPrint, rutaReporteDestino+nombreReporte+".pdf");
			
			//muestra en browser
			FacesContext.getCurrentInstance().getExternalContext().responseReset(); 
			FacesContext.getCurrentInstance().getExternalContext().setResponseContentType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(nombreReporte+".pdf")); 
			//FacesContext.getCurrentInstance().getExternalContext().setResponseContentLength(contentLength); 
			FacesContext.getCurrentInstance().getExternalContext().setResponseHeader("Content-disposition", "attachment; filename=\""+nombreReporte+".pdf\"");//inline 
	        OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	        FacesContext.getCurrentInstance().responseComplete();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new Exception();
		}
	}
	
}
