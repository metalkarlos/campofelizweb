package com.web.util;

import java.io.InputStream;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacesUtil {
	
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private HttpServletRequest request = ((HttpServletRequest)facesContext.getExternalContext().getRequest());
	private HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();

	public String getIp() {
		return request.getRemoteAddr();
	}
	
	public String getSid(){
		return request.getSession().getId();
	}
	
	public Object getSessionBean(String beanName){
		//return request.getSession().getAttribute(beanName);
		return getFacesContext().getExternalContext().getSessionMap().get(beanName);
	}
	
	public void setSessionBean(String attributeName, Object objectBean){
		request.getSession().setAttribute(attributeName, objectBean);
	}
	
	public void removeSessionBean(String beanName){
		getFacesContext().getExternalContext().getSessionMap().remove(beanName);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void redirect(String url) throws Exception{
		if(url != null){
			getFacesContext().getExternalContext().redirect(url);
		}
	}
	
	public void logout() {
		getFacesContext().getExternalContext().invalidateSession();
    }
	
	public InputStream getResourceAsStream(String pathrecurso){
		
		InputStream content = facesContext.getExternalContext().getResourceAsStream(pathrecurso);
		
		return content;
	}
	
	public String getParametrosUrl(){
		return request.getQueryString();
	}
	
	public Object getParametroUrl(String paramName){
		Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
		Object value = 0;
		
		if(params != null && !params.isEmpty()){
			value = params.get(paramName);
		}
		
		return value;
	}
	
	public String getContextParam(String paramName) throws Exception {
		
		String paramValue = facesContext.getExternalContext().getInitParameter(paramName);
		
		return paramValue;
	}
	
	public void redirectByPropertyFileKey(String Key) throws Exception {
		FileUtil fileUtil = new FileUtil();
		String value = fileUtil.getPropertyValue(Key);
		
		FacesUtil facesUtil = new FacesUtil();
		facesUtil.redirect(value);
	}
}
