package com.oyou.common.struts;

import java.util.StringTokenizer;


import com.oyou.common.reader.Reader;
import com.oyou.common.util.StringHelper;

/**
 * 
 * @author	Owen Ou
 *
 * May 12, 2007
 */
public class PropertyReaderImpl extends Reader implements PropertyReader {

	private PropertyReaderImpl() {
	}
	
	public PropertyReaderImpl(String filename, String encoder) {
		super(filename, encoder);
	}
	
	public Property getNextProperty() {
		Property prop = null;
		String buffer;
		while ((buffer = this.readLine()) != null) {
			if (StringHelper.isNotEmpty(buffer) && !buffer.startsWith("#")) {
				StringTokenizer st = new StringTokenizer(buffer, " =");
				String token = null;
				if (st.hasMoreTokens()) {
					prop = new Property();
					token = st.nextToken().trim();
					prop.setKey(token);
					if (buffer.length() > (token.length() + 1)) {
						prop.setValue(buffer.substring(token.length() + 1));
					} else {
						log.error("Error key " + token);
					}
					break;
				}
			} else {
				continue;
			}
		}
		return prop;
	}

}
