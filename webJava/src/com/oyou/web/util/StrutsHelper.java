package com.oyou.web.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.reader.Encoder;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;

public class StrutsHelper {
	private static final Log log = LogFactory.getLog(StrutsHelper.class);

	public static String getEncoding(HttpServletRequest request) {
		String encoding = (String) request.getSession().getAttribute(StrutsSession.KEY_ENCODING);
		return encoding;
	}

	public static void setEncoding(HttpServletRequest request, String encoding) {
		request.getSession().setAttribute(StrutsSession.KEY_ENCODING, encoding);
	}

	public static String getLanguage(HttpServletRequest request) {
		String language = (String) request.getSession().getAttribute(StrutsSession.KEY_LANGUAGE);
		return language;
	}

	public static String getLanguage(HttpSession session) {
		String language = (String) session.getAttribute(StrutsSession.KEY_LANGUAGE);
		return language;
	}

	public static void setLanguage(HttpServletRequest request, String language) {
		request.getSession().setAttribute(StrutsSession.KEY_LANGUAGE, language);
	}

	public static String getCategory(HttpSession session) {
		String category = (String) session.getAttribute(StrutsSession.KEY_CATEGORY);
		return category;
	}

	public static void setCategory(HttpServletRequest request, String category) {
		request.getSession().setAttribute(StrutsSession.KEY_CATEGORY, category);
	}

	public static String getPathInfo(HttpServletRequest request) {
		String path = (String) request.getSession().getAttribute(StrutsSession.KEY_PATHINFO);
		return path;
	}

	public static void setPathInfo(HttpServletRequest request, String path) {
		request.getSession().setAttribute(StrutsSession.KEY_PATHINFO, path);
	}

	public static void setLayoutEncoding(HttpServletRequest request, HttpServletResponse response, String path) {
		String[] inEncodings = { "sort", "treeview" };
		for (int i = 0; i < inEncodings.length; i++) {
			String inPath = inEncodings[i];
			if (path.indexOf(inPath) != -1) {
				processEncoding(request, response);
				break;
			}
		}
	}

	public static void processEncoding(HttpServletRequest request, HttpServletResponse response) {
		String encoding = StrutsHelper.getEncoding(request);
		try {
			if (StringHelper.isNotEmpty(encoding)) {
				if (encoding.equals(Encoder.GB2312)) {
					request.setCharacterEncoding(Encoder.GB2312);
					log.debug("== set encoding " + encoding);
					response.setCharacterEncoding(Encoder.GB2312);
					response.setContentType("text/html; charset=GB2312");
					response.setLocale(Locale.CHINESE);
				} else if (encoding.equals(Encoder.BIG5)) {
					request.setCharacterEncoding(Encoder.BIG5);
					log.debug("== set encoding " + encoding);
					response.setCharacterEncoding(Encoder.BIG5);
					response.setContentType("text/html; charset=BIG5");
					response.setLocale(Locale.CHINESE);
				} else {
					request.setCharacterEncoding(Encoder.UTF8);
					log.debug("== set encoding " + encoding);
					response.setCharacterEncoding(Encoder.UTF8);
					response.setContentType("text/html; charset=UTF-8");
					response.setLocale(Locale.CHINESE);
				}
			} else {
				request.setCharacterEncoding(Encoder.UTF8);
				log.debug("== set default encoding UTF-8");
				response.setCharacterEncoding(Encoder.UTF8);
				response.setContentType("text/html; charset=UTF-8");
				response.setLocale(Locale.CHINESE);
			}
		} catch (UnsupportedEncodingException ue) {
			log.error(ue.getMessage());
			ue.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getWebRootPath(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		String ctxPath = request.getContextPath();
		sb.append("/");
		sb.append(ctxPath);
		return sb.toString();
	}

	public static Map<String, String> getURLParams(String url) {
		Map<String, String> map = null;
		if (StringHelper.isNotEmpty(url) && (url.indexOf("?") < url.length())) {
			map = new HashMap<String, String>();
			String[] paramPairs = url.substring(url.indexOf("?") + 1).split("&");
			for (int i = 0; i < paramPairs.length; i++) {
				String paramPair = paramPairs[i];
				String[] param = paramPair.split("=");
				if (param[0] != null) {
					map.put(param[0], param[1]);
				}
			}
		}
		return map;
	}
}
