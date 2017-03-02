package com.cso.jsf2spring3.service;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cso.jsf2spring3.entity.Media;

@Service("mediaService")
public class MediaServiceImpl implements MediaService {
	private static final Log log = LogFactory.getLog(MediaService.class);

	public String getVideoContentType(String filename) {

		if (filename.indexOf(".MPG") != -1 || filename.indexOf(".mpg") != -1) {
			return "video/mpeg";
		} else if (filename.indexOf(".MP4") != -1 || filename.indexOf(".mp4") != -1) {
			return "video/mp4";
		} else if (filename.indexOf(".MOV") != -1 || filename.indexOf(".mov") != -1) {
			return "video/webm"; //QuickTime
		} else if (filename.indexOf(".WMV") != -1 || filename.indexOf(".wmv") != -1) {
			return "video/x-ms-wmv";
		} else if (filename.indexOf(".RV") != -1 || filename.indexOf(".rv") != -1) {
			return "video/vnd.rn-realvideo";
		} else if (filename.indexOf(".FLV") != -1 || filename.indexOf(".flv") != -1) {
			return "video/x-flv";
		} else {
			return "video/video";
		}

	}

	public Document getDocumentFromMedia(List<Media> mediaList, String mediaUrl) {

		log.debug("Media size: " + mediaList.size());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		DOMImplementation impl = null;
		Document xmldoc = null;
		Element root = null;
		Element item = null;
		Element e = null;
		try {
			builder = dbf.newDocumentBuilder();
			impl = builder.getDOMImplementation();
			xmldoc = impl.createDocument(null, null, null);
			Element xmlroot = xmldoc.createElement("xml");
			xmldoc.appendChild(xmlroot);
			root = xmldoc.createElement("data");
			xmlroot.appendChild(root);
			for (Media media : mediaList) {
				String mediaName = media.getFilename();
				if (mediaName == null || mediaName.trim().length() == 0)
					continue;
				item = xmldoc.createElement("item");
				root.appendChild(item);
				// id
				e = xmldoc.createElement("id");
				e.appendChild(xmldoc.createTextNode(media.getId().toString()));
				item.appendChild(e);
				// name
				e = xmldoc.createElement("name");
				e.appendChild(xmldoc.createTextNode(media.getTitle()));
				item.appendChild(e);
				// description
				e = xmldoc.createElement("description");
				String title = media.getTitle();
				e.appendChild(xmldoc.createTextNode(title == null ? "" : title));
				item.appendChild(e);
				String mediaType = "image";
				if (media.getContentType().indexOf("image") != -1) {
					e = xmldoc.createElement("image");
					e.appendChild(xmldoc.createTextNode(mediaUrl + mediaName));
					item.appendChild(e);
				} else if (media.getContentType().indexOf("video") != -1) {
					e = xmldoc.createElement("video");
					e.appendChild(xmldoc.createTextNode(mediaUrl + mediaName));
					item.appendChild(e);
					mediaType = "video";
					String videoType = getVideoContentType(mediaName);
					if (videoType != null && videoType.length() > 0) {
						e = xmldoc.createElement("type");
						e.appendChild(xmldoc.createTextNode(videoType));
						item.appendChild(e);
					}
				}
				// media type
				e = xmldoc.createElement("mediatype");
				e.appendChild(xmldoc.createTextNode(mediaType));
				item.appendChild(e);
				// image large
				if (media.getContentType().indexOf("image") != -1) {
					e = xmldoc.createElement("image_large");
					e.appendChild(xmldoc.createTextNode(mediaUrl + mediaName + "?t=lg"));
					item.appendChild(e);
				}
				// image tn
				e = xmldoc.createElement("tn");
				e.appendChild(xmldoc.createTextNode(mediaUrl + mediaName + "?t=tn"));
				item.appendChild(e);
			}
		} catch (ParserConfigurationException pce) {
			log.error(pce.getMessage());
			pce.printStackTrace();

		}
		return xmldoc;
	}

}