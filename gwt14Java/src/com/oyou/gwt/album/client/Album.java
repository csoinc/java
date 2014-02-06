package com.oyou.gwt.album.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Album.java,v 1.1 2008/06/29 14:24:23 oyou Exp $
 * @since Nov 12, 2007
 */
public class Album implements EntryPoint, WindowResizeListener {
	public class AlbumDataProviderImpl implements AlbumDataProvider {
		private final AlbumDataServiceAsync calService;

		public AlbumDataProviderImpl() {
			calService = (AlbumDataServiceAsync) GWT.create(AlbumDataService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				} else {
					moduleRelativeURL += "../../blogajax"; //JSP 
				}
			} else {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8080/blogger2/blogajax"; //Debug
				} else {
					moduleRelativeURL += "../../blogajax"; //JSP 
				}
			}
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void updatePhotos(String cid, String gid, final AlbumDataAcceptor acceptor) {
			calService.getPhotos(cid, gid, "", new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsPhoto[] photos;
					photos = (IsPhoto[]) result;
					acceptor.accept(photos);
				}
			});
		}

		public void getAlbums(String cid, final AlbumDataAcceptor acceptor) {
			calService.getAlbums(cid, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsAlbum[] albums = (IsAlbum[]) result;
					acceptor.acceptAlbums(albums);
				}
			});
		}

		public void getCategories(final AlbumDataAcceptor acceptor) {
			calService.getCategories(new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsCategory[] categories = (IsCategory[]) result;
					acceptor.acceptCategories(categories);
				}
			});
		}
		
	}

	public class SlideShow extends Timer {
		private IsPhoto[] photos = null;

		private AlbumDetail albumDetail = null;

		private int index = 0;

		public SlideShow(AlbumDetail albumDetail, IsPhoto[] photos, int index) {
			this.albumDetail = albumDetail;
			this.photos = photos;
			this.index = index;
		}

		public void run() {
			IsPhoto photo = photos[index++];
			if (index >= photos.length)
				index = 0;
			albumDetail.setPhoto(photo);
		}

	}

	public final static boolean DEBUG = false;

	private static Album singleton;

	public static Album get() {
		return singleton;
	}

	private final AlbumDataProvider dataProvider = new AlbumDataProviderImpl();

	private SlideShow slideShow = null;

	private VerticalPanel vpAlbum = new VerticalPanel();
	
	private VerticalPanel centerPanel = new VerticalPanel();

	private MenuBar menuBar;
	
	private AlbumList albumList;

	private AlbumDetail albumDetail;

	public MenuBar createMenu() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);

		MenuBar menuBible = new MenuBar(true);
		menuBible.addItem("<font color='#000000'><i>Index</i></font>", true, new AlbumCommand());
		menuBible.addItem("<font color='#000000'><i>Seleted</i></font>", true, new AlbumCommand());
		menuBible.addItem("<font color='#000000'><i>Search</i></font>", true, new AlbumCommand());

		MenuBar menuFile = new MenuBar(true);
		menuFile.addItem("<font color='#000000'><i>New Photo</i></font>", true, new AlbumCommand(AlbumCommand.CMD_NEW_PHOTO));
		menuFile.addItem("<font color='#000000'><i>New Article</i></font>", true, new AlbumCommand(AlbumCommand.CMD_NEW_ARTICLE));
		menuFile.addItem("<font color='#000000'><i>" + Constants.EXIT + "</i></font>", true, new AlbumCommand(AlbumCommand.CMD_EXIT));

		MenuBar menuAcct = new MenuBar(true);
		menuAcct.addItem("<font color='#000000'><i>Login</i></font>", true, new AlbumCommand(AlbumCommand.CMD_LOGIN));
		menuAcct.addItem("<font color='#000000'><i>Logout</i></font>", true, new AlbumCommand(AlbumCommand.CMD_LOGOUT));
		menuAcct.addItem("<font color='#000000'><i>Register</i></font>", true, new AlbumCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new AlbumCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new AlbumCommand());
		
		MenuBar menuView = new MenuBar(true);
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_PHOTO + "</i></font>", true, new AlbumCommand(AlbumCommand.CMD_VIEW_PHOTO));
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_ARTICLE + "</i></font>", true, new AlbumCommand(AlbumCommand.CMD_VIEW_ARTICLE));
		menuView.addItem("<font color='#000000'><u><i>" + Constants.BIBLE + "</i></u></font>", true, menuBible);

		MenuBar menuMy = new MenuBar(true);
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYPHOTO + "</i></font>", true, new AlbumCommand(AlbumCommand.CMD_VIEW_MYPHOTO));
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYARTICLE + "</i></font>", true, new AlbumCommand(AlbumCommand.CMD_VIEW_MYARTICLE));

		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.FILE + "</b></font>", true, menuFile));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.ACCOUNT + "</b></font>", true, menuAcct));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.VIEW + "</b></font>", true, menuView));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.PERSONALS + "</b></font>", true, menuMy));

		menu.setWidth("100%");

		return menu;
	}

	public void displayPhoto(IsPhoto photo) {
		albumDetail.setPhoto(photo);
	}

	public void login() {
		this.stopSlide();
		Command cmd = new AlbumCommand(AlbumCommand.CMD_LOGIN);
		cmd.execute();
	}

	/**
	 * This method constructs the application user interface by instantiating
	 * controls and hooking up event listeners.
	 */
	public void onModuleLoad() {
		singleton = this;

		albumList = new AlbumList(dataProvider);
		albumList.setWidth("200px");
		albumDetail = new AlbumDetail();
		albumDetail.setWidth("800px");
		
		HorizontalPanel hpAlbum = new HorizontalPanel();
		hpAlbum.add(albumList);
		hpAlbum.add(albumDetail);

		centerPanel.add(hpAlbum);
		//centerPanel.setTopWidget(albumList);
		//centerPanel.setBottomWidget(albumDetail);

		menuBar = this.createMenu();
		// Create a dock panel that will contain the menu bar at the top,
		// the shortcuts to the left, and the mail list & details taking the rest.
		DockPanel outer = new DockPanel();
		outer.add(menuBar, DockPanel.NORTH);
		outer.add(centerPanel, DockPanel.CENTER);
		outer.setWidth("1000px");

		outer.setSpacing(4);
		outer.setCellWidth(centerPanel, "1000px");

		vpAlbum.add(outer);
		vpAlbum.setCellHorizontalAlignment(outer, HorizontalPanel.ALIGN_CENTER);
		vpAlbum.setWidth("100%");
		
	    // Hook the window resize event, so that we can adjust the UI.
	    Window.addWindowResizeListener(this);

	    // Get rid of scrollbars, and clear out the window's built-in margin,
	    // because we want to take advantage of the entire client area.
	    Window.enableScrolling(true);
	    Window.setMargin("0px");

		// Finally, add the outer panel to the RootPanel, so that it will be
		// displayed.s

		RootPanel.get().add(vpAlbum);

		// Call the window resized handler to get the initial sizes setup. Doing
	    // this in a deferred command causes it to occur after all widgets' sizes
	    // have been computed by the browser.
	    DeferredCommand.addCommand(new Command() {
	      public void execute() {
	        onWindowResized(Window.getClientWidth(), Window.getClientHeight());
	      }
	    });
	    onWindowResized(Window.getClientWidth(), Window.getClientHeight());

	}

	public void slidePhotos(IsPhoto[] photos, int index) {
		//albumDetail.start();
		albumList.start();
		slideShow = new SlideShow(albumDetail, photos, index);
		slideShow.scheduleRepeating(4000);
	}

	public void stopSlide() {
		if (slideShow != null) {
			slideShow.cancel();
			slideShow = null;
			//albumDetail.stop();
			albumList.stop();
		    onWindowResized(Window.getClientWidth(), Window.getClientHeight());
		}
	}

    public void onWindowResized(int width, int height) {
    	vpAlbum.setSize("" + width, "" + height);
    }

}
