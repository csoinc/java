package com.oyou.gwt.album.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

/**
 * 
 * @author	Owen Ou
 * @version $Id: StatusBall.java,v 1.1 2008/06/29 14:24:23 oyou Exp $
 * @since Nov 23, 2007
 */
public class StatusBall extends Composite {
	private String b0 = "./images/ball-0.jpg";
	private String b90 = "./images/ball-90.jpg";
	private String b180 = "./images/ball-180.jpg";
	private String b270 = "./images/ball-270.jpg";
	private Image ball = new Image(b0);
	private int index = 0;
	
	public class Rotation extends Timer {

		public Rotation() {
		}

		public void run() {
			switch (index) { 
				case 0:
					ball.setUrl(b90); break;
				case 1:
					ball.setUrl(b180); break;
				case 2:
					ball.setUrl(b270); break;
				case 3:
					ball.setUrl(b0); break;
			}
			index++;
			if (index > 3) index = 0;
		}

	}

	Rotation show = null;
	
	public StatusBall() {
		initWidget(ball);
		this.setHeight("45px");
		this.setWidth("45px");
	}

	public void start() {
		show = new Rotation();
		show.scheduleRepeating(200);
	}
	
	public void stop() {
		if (show != null) {
			show.cancel();
			show = null;
		}
	}
	
}
