package com.cso.jsf2spring3.service;

import java.util.List;

import org.w3c.dom.Document;

import com.cso.jsf2spring3.entity.Media;

public interface MediaService {

	public String getVideoContentType(String filename);

	public Document getDocumentFromMedia(List<Media> mediaList, String mediaUrl);

}