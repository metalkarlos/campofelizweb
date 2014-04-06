package com.web.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.hibernate.Session;

public class Utilities {
	
	public void imprimirJasperPdf(String nombreReporte, Map<String, Object> parametros) throws Exception {
		InputStream inputStream = null;
		
		//try{
			inputStream = new FacesUtil().getResourceAsStream("/reportes/"+nombreReporte+".jasper");

			if(inputStream != null){
		        Session session = HibernateUtil.getSessionFactory().openSession();
		        Connection connection = session.connection();
		        
				JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, connection);//new JREmptyDataSource());
				
				//crea en disco
				String rutaReporteDestino = System.getProperty("java.io.tmpdir");
				JasperExportManager.exportReportToPdfFile(jasperPrint, rutaReporteDestino+"/"+nombreReporte+".pdf");
				
				//muestra en browser
				FacesContext.getCurrentInstance().getExternalContext().responseReset(); 
				FacesContext.getCurrentInstance().getExternalContext().setResponseContentType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(nombreReporte+".pdf")); 
				//FacesContext.getCurrentInstance().getExternalContext().setResponseContentLength(contentLength); 
				FacesContext.getCurrentInstance().getExternalContext().setResponseHeader("Content-disposition", "inline; filename=\""+nombreReporte+".pdf\"");//inline //attachment
		        OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
		        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		        FacesContext.getCurrentInstance().responseComplete();
			}else{
				throw new Exception("No se ha encontrado archivo: " + nombreReporte);
			}
		/*}
		catch(Exception e){
			throw new Exception();
		} finally {
			if(inputStream != null){
				try{
					inputStream.close();
				}catch(Exception e){
					
				}
			}
		}*/
	}
	
}
