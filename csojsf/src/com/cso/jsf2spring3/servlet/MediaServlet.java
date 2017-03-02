package com.cso.jsf2spring3.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;

import com.cso.jsf2spring3.entity.Media;
import com.cso.jsf2spring3.service.MediaService;

/**
 * 
 * @author ouow(Owen)
 * 
 * @since 2012-08-20
 */

@Component("MediaServlet")
@RequestMapping("/mediaservice")
public class MediaServlet implements HttpRequestHandler {

	//private static final long serialVersionUID = 7624447812640134248L;

	private static final Log log = LogFactory.getLog(MediaService.class);

	@Autowired
	private MediaService mediaService;
	
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log.debug(">>MediaServlet");
		String filename = request.getParameter("n");
		String type = request.getParameter("t");
		if (filename != null && filename.trim().length() > 0) {
			if (type.equalsIgnoreCase("TN")) {
				;
			} else if (type.equalsIgnoreCase("LG")) {
				;
			}
			outputMedia(request, response, filename);
		} else {
			outputXML(request, response);
		}
	}

	void outputMedia(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists())
			return;
		response.setContentLength((int) file.length());
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] buf = new byte[1024 * 10];
			int count;
			while ((count = fis.read(buf)) > -1) {
				os.write(buf, 0, count);
			}
			response.setHeader("Pragma", "no-cache");
			os.flush();
		} catch (FileNotFoundException e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		} catch (IOException ex) {
			log.warn(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void outputXML(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//MediaService mediaService = new MediaService();
		String mediaUrl = "http://" + request.getServerName() + ":" + request.getLocalPort() + request.getContextPath() + "/resources/upload/";
		log.debug("mediaUrl: " + mediaUrl);
		
		List<Media> mediaList = new ArrayList<Media>();
		
		Media m = new Media();
		m.setContentType("image/jpeg");
		m.setFilename("hp_pic_1.jpg");
		m.setId(Integer.valueOf(1));
		m.setTitle("Picture 1");
		mediaList.add(m);
		
		m = new Media();
		m.setContentType("image/jpeg");
		m.setFilename("hp_pic_2.jpg");
		m.setId(Integer.valueOf(2));
		m.setTitle("Picture 2");
		mediaList.add(m);

		m = new Media();
		m.setContentType("image/jpeg");
		m.setFilename("hp_pic_3.jpg");
		m.setId(Integer.valueOf(3));
		m.setTitle("Picture 3");
		mediaList.add(m);

		m = new Media();
		m.setContentType("image/jpeg");
		m.setFilename("hp_pic_4.jpg");
		m.setId(Integer.valueOf(4));
		m.setTitle("Picture 4");
		mediaList.add(m);
		
		Document doc = mediaService.getDocumentFromMedia(mediaList, mediaUrl);
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer t = tf.newTransformer();
			t.setOutputProperty("encoding", "iso-8859-1");
			t.setOutputProperty("indent", "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource domsource = new DOMSource(doc);
			StreamResult sr = new StreamResult(response.getOutputStream());
			t.transform(domsource, sr);
		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace(System.err);
		} catch (TransformerException te) {
			te.printStackTrace(System.err);
		}
	}

}