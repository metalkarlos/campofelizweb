package com.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 2505646493843988772L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private String imagePath;

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        try {
			this.imagePath = getServletContext().getInitParameter("imagesDirectory");
		} catch (Exception e) {
			throw new ServletException(e);
		}

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\images".
        // In UNIX, it is just straightforward "/images".
        // If you have stored files in the WebContent of a WAR, for example in the
        // "/WEB-INF/images" folder, then you can retrieve the absolute path by:
        // this.imagePath = getServletContext().getRealPath("/WEB-INF/images");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Get requested image by path info.
        String requestedImage = request.getPathInfo();
        
        // Obtener parametros
        /*Map<String,String[]> params = request.getParameterMap();
		Object value = 0;
		
		if(params != null && !params.isEmpty()){
			value = params.get("w");
		}
		
		// Obtener el ancho requerido
		int width = Integer.parseInt(value.toString());*/
		
		String param = request.getParameter("w");
		int width = 0;
		
		if(param != null && param.trim().length() > 0){
			try{
				width = Integer.parseInt(param);
			}catch(Exception e){
				width = 0;
			}
		}

        File image = null;
        
        // Check if file name is actually supplied to the request URI.
        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            //response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            //return;
        	image = new File(getServletContext().getRealPath("/resources/images/huella.jpg"));
        }else{
	        // Decode the file name (might contain spaces and on) and prepare file object.
	        image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));
        }

        // Check if file actually exists in filesystem.
        if (!image.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            //response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            //return;
        	image = new File(getServletContext().getRealPath("/resources/images/huella.jpg"));
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(image.getName());

        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        if (contentType == null || !contentType.startsWith("image")) {
            // Do your thing if the file appears not being a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}
