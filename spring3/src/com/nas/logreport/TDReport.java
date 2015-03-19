/**
 * NAS TD Log Report
 * @author Owen.Ouyang
 * 
 */
package com.nas.logreport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Owen.Ouyang
 * 
 */
public class TDReport {
	protected static final Log log = LogFactory.getLog(TDReport.class);

	public static String KEY_ORDER_REQUEST = "OCA.ORDER_REQUEST";
	public static String KEY_CREDITREFNUMBER = "OCA.CREDITREFNUMBER";
	public static String KEY_APPRAISAL_TYPE = "OCA.APPRAISAL_TYPE";

	public static String MQHandlerTDXMLStart = "MQHandlerTD - Incoming TD Message Before Parse:";
	public static String MQHandlerTDNASARStart = "MQHandlerTD - Request Created:AS";
	public static String NASARNo = "App Req #";

	public static String REPORT_NAME_TD = "D:/UserData/TDReport/MQHandlerTD.csv";

	public static TreeMap<String, TDRow> tMap = new TreeMap<String, TDRow>();

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String logPath74 = "D:/UserData/Logs74";
		String logPath75 = "D:/UserData/Logs75";

		TDReport report = new TDReport();

		File logDir = new File(logPath74);

		if (logDir.isDirectory()) {
			File[] files = logDir.listFiles();
			for (File file : files) {
				log.debug("Log Filename:" + file.getAbsolutePath());
				String filename = file.getAbsolutePath();
				report.processLogFile(tMap, filename);

			}

		}

		logDir = new File(logPath75);

		if (logDir.isDirectory()) {
			File[] files = logDir.listFiles();
			for (File file : files) {
				log.debug("Log Filename:" + file.getAbsolutePath());
				String filename = file.getAbsolutePath();
				report.processLogFile(tMap, filename);

			}

		}

		for (String key : tMap.keySet()) {
			log.debug(key + " :: " + tMap.get(key).toString());
		}
		
		report.generateReport(tMap);

	}

	public void generateReport(TreeMap<String, TDRow> tMap) {
		try {
			PrintWriter writer = new PrintWriter(REPORT_NAME_TD);
			
			writer.println("Timestamp|AppraisalType,App Reference #,NAS(Appraisal Request)#,");
			
			for (String key : tMap.keySet()) {
				TDRow row = tMap.get(key);
				writer.println(row.getTimestamp() + "," + row.getAppraisalType() + "," + row.getCreditReferencenNo() + "," + row.getAppraisalRequestNo());
			}
			
			writer.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public String getTimeFromFilename(String filename) {
		Scanner s1 = new Scanner(filename).useDelimiter("\\s*NAS.log.\\s*");
		s1.next();
		if (s1.hasNext()) {
			return s1.next().split(" ")[0];
		}
		return null;
	}

	public void processLogFile(TreeMap<String, TDRow> tMap, String filename) throws Exception {
		String timestamp = this.getTimeFromFilename(filename);
		log.debug("Filename Time:" + timestamp);

		FileInputStream inputStream = null;
		Scanner sc = null;

		try {
			inputStream = new FileInputStream(filename);
			sc = new Scanner(inputStream, "UTF-8");
			boolean isMQHandlerTDXML = false;
			StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\"?>");
			TDRow row = new TDRow();

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (isMQHandlerTDXML) {
					if (line.startsWith(timestamp)) {
						isMQHandlerTDXML = false;
						log.debug("Get TD XML String:" + xmlString.toString());
						this.parseXMLDOM(row, xmlString.toString());
						//report.parseXMLJDOM(xmlString.toString()); //Use DOM 
					} else {
						xmlString.append(line);
					}
				} else {
					if (line.startsWith(timestamp) && line.contains(MQHandlerTDXMLStart)) {
						this.parseTimestamp(row, line);
						isMQHandlerTDXML = true;
						xmlString = new StringBuilder("<?xml version=\"1.0\"?>");
						log.debug("Match TD xml start line:" + line);
					} else if (line.startsWith(timestamp) && line.contains(MQHandlerTDNASARStart)) {
						log.debug("Match TD NAS AR#:" + line);
						this.parseAppraisalRequestNo(row, line);

						tMap.put(row.getTimestamp(), row);

						row = new TDRow();
					}
				}

			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
			log.error(fe.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

	/**
	 * Paese the line to get the Appraisal Request No
	 * @param row
	 * @param line
	 * @return
	 */
	public TDRow parseAppraisalRequestNo(TDRow row, String line) {
		log.debug("Match TD NAS AR#:" + line);
		Scanner s1 = new Scanner(line).useDelimiter("\\s*" + MQHandlerTDNASARStart + "\\s*");
		Scanner s2 = new Scanner(line).useDelimiter("\\s*" + NASARNo);

		s1.next();
		if (s1.hasNext()) {
			String creditReferenceNo = s1.next().split(" ")[0];
			if (creditReferenceNo == null || !creditReferenceNo.equals(row.getCreditReferencenNo()))
				return row;
			row.setCreditReferencenNo(creditReferenceNo);
			log.debug("Credit Reference Number:" + creditReferenceNo);
		}

		s2.next();
		if (s2.hasNext()) {
			row.setAppraisalRequestNo(s2.next());
			log.debug("App Reg#:" + row.getAppraisalRequestNo());
		}
		s1.close();
		s2.close();
		return row;
	}

	/**
	 * Get the timestamp string of the line
	 * @param row
	 * @param line
	 * @return
	 */
	public TDRow parseTimestamp(TDRow row, String line) {
		String time = line.substring(0, line.indexOf("."));
		log.debug("Timestamp:" + time);
		row.setTimestamp(time);
		return row;
	}

	/**
	 * Parse the TD xml string by JDOM parser
	 * @param xmlStr
	 * @return
	 */
	public TDRow parseXMLJDOM(String xmlStr) {
		TDRow row = new TDRow();
		SAXBuilder builder = new SAXBuilder();
		try {

			org.jdom2.Document document = (org.jdom2.Document) builder.build(new ByteArrayInputStream(xmlStr.getBytes()));
			org.jdom2.Element rootNode = document.getRootElement();
			List<org.jdom2.Element> list = rootNode.getChildren("OCA.ORDER_REQUEST");

			for (int i = 0; i < list.size(); i++) {

				org.jdom2.Element node = (org.jdom2.Element) list.get(i);

				log.debug("Appraisal Type: " + node.getChildText(KEY_APPRAISAL_TYPE));
				log.debug("CreditReferenceNo: " + node.getChildText(KEY_CREDITREFNUMBER));

			}

		} catch (IOException io) {
			log.error(io.getMessage());
			io.printStackTrace();
		} catch (JDOMException jdomex) {
			log.error(jdomex.getMessage());
			jdomex.printStackTrace();
		}
		return row;
	}

	/**
	 * Parse the TD xml string by DOM parser
	 * @param row
	 * @param xmlStr
	 * @return
	 */
	public TDRow parseXMLDOM(TDRow row, String xmlStr) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
			doc.getDocumentElement().normalize();
			log.debug("Root element:" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(KEY_ORDER_REQUEST);

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				log.debug("Current Element:" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					row.setCreditReferencenNo(eElement.getElementsByTagName(KEY_CREDITREFNUMBER).item(0).getTextContent());
					row.setAppraisalType(eElement.getElementsByTagName(KEY_APPRAISAL_TYPE).item(0).getTextContent());
					log.debug(KEY_CREDITREFNUMBER + ":" + eElement.getElementsByTagName(KEY_CREDITREFNUMBER).item(0).getTextContent());
					log.debug(KEY_APPRAISAL_TYPE + ":" + eElement.getElementsByTagName(KEY_APPRAISAL_TYPE).item(0).getTextContent());

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return row;
	}

}
