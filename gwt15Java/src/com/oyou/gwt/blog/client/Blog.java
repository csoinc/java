package com.oyou.gwt.blog.client;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Blog.java,v 1.1 2008/06/29 14:23:47 oyou Exp $
 * @since Dec 5, 2007
 */
public class Blog implements EntryPoint, WindowResizeListener {
	public class BlogDataProviderImpl implements BlogDataProvider {
		private final BlogDataServiceAsync calService;

		public BlogDataProviderImpl() {
			calService = (BlogDataServiceAsync) GWT.create(BlogDataService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:83/blogger/blogrpc"; //Debug
				} else {
					moduleRelativeURL += "../../blogger/blogrpc"; //JSP
				}
			} else {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:83/blogger/blogrpc"; //Debug
				} else {
					moduleRelativeURL += "../../blogger/blogrpc"; //JSP 
				}
			}
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void updatePhotos(String cid, String gid, String uid, final BlogDataAcceptor acceptor) {
			calService.getPhotos(cid, gid, uid, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsPicture[] photos;
					photos = (IsPicture[]) result;
					acceptor.acceptPhotos(photos);
				}
			});
		}

		public void updateArticles(String cid, String gid, String uid, final BlogDataAcceptor acceptor) {
			calService.getArticles(cid, gid, uid, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsArticle[] articles;
					articles = (IsArticle[]) result;
					acceptor.acceptArticles(articles);
				}
			});
		}
		
		public void getAlbums(String cid, final BlogDataAcceptor acceptor) {
			calService.getAlbums(cid, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsGroup[] albums = (IsGroup[]) result;
					acceptor.acceptAlbums(albums);
				}
			});
		}

		public void getGroups(String cid, final BlogDataAcceptor acceptor) {
			calService.getGroups(cid, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsGroup[] groups = (IsGroup[]) result;
					acceptor.acceptGroups(groups);
				}
			});
		}
		
		public void getCategories(final BlogDataAcceptor acceptor) {
			calService.getCategories(new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsGroupType[] categories = (IsGroupType[]) result;
					acceptor.acceptCategories(categories);
				}
			});
		}

		public void updatePhoto(String mid, String comment, final BlogDataAcceptor acceptor) {
			calService.updatePhoto(mid, comment, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsErrorCode errorCode = (IsErrorCode)result;
					acceptor.acceptErrorCode(errorCode);
				}
			});
		}
		
	}

	public class SlideShow extends Timer {
		private IsPicture[] photos = null;

		private AlbumDetail albumDetail = null;

		private int index = 0;

		public SlideShow(AlbumDetail albumDetail, IsPicture[] photos, int index) {
			this.albumDetail = albumDetail;
			this.photos = photos;
			this.index = index;
		}

		public void run() {
			IsPicture photo = photos[index++];
			if (index >= photos.length)
				index = 0;
			albumDetail.setPhoto(photo);
		}

	}

	public class MySlideShow extends Timer {
		private IsPicture[] photos = null;

		private MyAlbumDetail albumDetail = null;

		private int index = 0;

		public MySlideShow(MyAlbumDetail albumDetail, IsPicture[] photos, int index) {
			this.albumDetail = albumDetail;
			this.photos = photos;
			this.index = index;
		}

		public void run() {
			IsPicture photo = photos[index++];
			if (index >= photos.length)
				index = 0;
			albumDetail.setPhoto(photo);
		}

	}
	
	public final static boolean DEBUG = false;
	
	public final static String UID = "1";

	private static Blog singleton;

	public static Blog get() {
		return singleton;
	}

	private final BlogDataProvider dataProvider = new BlogDataProviderImpl();

	private HTML hEnd = new HTML("Powered by: OYOU", true);

	private SlideShow slideShow = null;

	private MySlideShow mySlideShow = null;

	private VerticalPanel vpAlbum = new VerticalPanel();
	
	private VerticalPanel albumPanel = new VerticalPanel();

	private VerticalPanel articlePanel = new VerticalPanel();

	private VerticalPanel myAlbumPanel = new VerticalPanel();
	
	private VerticalPanel myArticlePanel = new VerticalPanel();

	private VerticalPanel endPanel = new VerticalPanel();

	private TabPanel tabs = new TabPanel();
	
	private MenuBar menuBar;
	
	private AlbumList albumList;

	private AlbumDetail albumDetail;

	private ArticleList articleList;

	private ArticleDetail articleDetail;

	private MyAlbumList myAlbumList;
	
	private MyAlbumDetail myAlbumDetail;

	private MyArticleList myArticleList;

	private MyArticleDetail myArticleDetail;

	public MenuBar createMenu() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);

		MenuBar menuBible = new MenuBar(true);
		menuBible.addItem("<font color='#000000'><i>Index</i></font>", true, new BlogCommand(BlogCommand.CMD_BIBLE_INDEX));
		menuBible.addItem("<font color='#000000'><i>Seleted</i></font>", true, new BlogCommand(BlogCommand.CMD_BIBLE_SELECTED));
		menuBible.addItem("<font color='#000000'><i>Search</i></font>", true, new BlogCommand(BlogCommand.CMD_BIBLE_SEARCH));

		MenuBar menuFile = new MenuBar(true);
		menuFile.addItem("<font color='#000000'><i>Upload Photo</i></font>", true, new BlogCommand(BlogCommand.CMD_NEW_PHOTO));
		menuFile.addItem("<font color='#000000'><i>Create Article</i></font>", true, new BlogCommand(BlogCommand.CMD_NEW_ARTICLE));
		menuFile.addItem("<font color='#000000'><i>Site Search</i></font>", true, new BlogCommand(BlogCommand.CMD_BLOG_SEARCH));
		menuFile.addItem("<font color='#000000'><i>Send Email</i></font>", true, new BlogCommand(BlogCommand.CMD_GROUP_EMAIL));
		menuFile.addItem("<font color='#000000'><i>" + Constants.EXIT + "</i></font>", true, new BlogCommand(BlogCommand.CMD_EXIT));

		MenuBar menuAcct = new MenuBar(true);
		menuAcct.addItem("<font color='#000000'><i>Login</i></font>", true, new BlogCommand(BlogCommand.CMD_LOGIN));
		menuAcct.addItem("<font color='#000000'><i>Logout</i></font>", true, new BlogCommand(BlogCommand.CMD_LOGOUT));
		menuAcct.addItem("<font color='#000000'><i>Register</i></font>", true, new BlogCommand(BlogCommand.CMD_REGISTER));
		menuAcct.addItem("<font color='#000000'><i>Forgot Password</i></font>", true, new BlogCommand(BlogCommand.CMD_FORGOT_PASSWORD));
		menuAcct.addItem("<font color='#000000'><i>Update Password</i></font>", true, new BlogCommand(BlogCommand.CMD_UPDATE_PASSWORD));
		menuAcct.addItem("<font color='#000000'><i>Update Profile</i></font>", true, new BlogCommand(BlogCommand.CMD_UPDATE_PROFILE));
		
		MenuBar menuView = new MenuBar(true);
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_PHOTO + "</i></font>", true, new BlogCommand(BlogCommand.CMD_VIEW_PHOTO));
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_ARTICLE + "</i></font>", true, new BlogCommand(BlogCommand.CMD_VIEW_ARTICLE));
		menuView.addItem("<font color='#000000'><u><i>" + Constants.BIBLE + "</i></u></font>", true, menuBible);

		MenuBar menuMy = new MenuBar(true);
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYPHOTO + "</i></font>", true, new BlogCommand(BlogCommand.CMD_VIEW_MYPHOTO));
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYARTICLE + "</i></font>", true, new BlogCommand(BlogCommand.CMD_VIEW_MYARTICLE));

		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.FILE + "</b></font>", true, menuFile));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.ACCOUNT + "</b></font>", true, menuAcct));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.VIEW + "</b></font>", true, menuView));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.PERSONALS + "</b></font>", true, menuMy));

		menu.setWidth("100%");

		return menu;
	}

	public void displayArticle(IsArticle article) {
		articleDetail.setArticle(article);
	}

	public void displayMyArticle(IsArticle article) {
		myArticleDetail.setArticle(article);
	}
	
	public void displayPhoto(IsPicture photo) {
		albumDetail.setPhoto(photo);
	}

	public void displayMyPhoto(IsPicture photo) {
		myAlbumDetail.setPhoto(photo);
	}
	
	public void login() {
		this.stopSlide();
		this.stopMySlide();
		Command cmd = new BlogCommand(BlogCommand.CMD_LOGIN);
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
		albumDetail = new AlbumDetail(dataProvider);
		albumDetail.setWidth("800px");
		
		HorizontalPanel hpAlbum = new HorizontalPanel();
		hpAlbum.add(albumList);
		hpAlbum.add(albumDetail);

		albumPanel.add(hpAlbum);
		//albumPanel.setTopWidget(albumList);
		//albumPanel.setBottomWidget(albumDetail);

		myAlbumList = new MyAlbumList(dataProvider);
		myAlbumList.setWidth("200px");
		myAlbumDetail = new MyAlbumDetail();
		myAlbumDetail.setWidth("800px");
		
		HorizontalPanel hpMyAlbum = new HorizontalPanel();
		hpMyAlbum.add(myAlbumList);
		hpMyAlbum.add(myAlbumDetail);

		myAlbumPanel.add(hpMyAlbum);

		articleList = new ArticleList(dataProvider);
		articleList.setWidth("200px");
		articleDetail = new ArticleDetail();
		articleDetail.setWidth("800px");
		
		HorizontalPanel hpArticle = new HorizontalPanel();
		hpArticle.add(articleList);
		hpArticle.add(articleDetail);

		articlePanel.add(hpArticle);

		myArticleList = new MyArticleList(dataProvider);
		myArticleList.setWidth("200px");
		myArticleDetail = new MyArticleDetail();
		myArticleDetail.setWidth("800px");
		
		HorizontalPanel hpMyArticle = new HorizontalPanel();
		hpMyArticle.add(myArticleList);
		hpMyArticle.add(myArticleDetail);

		myArticlePanel.add(hpMyArticle);
		
	    tabs.add(albumPanel, "Albums");
	    tabs.add(articlePanel, "Articles");
	    tabs.add(myAlbumPanel, "MyAlbums");
	    tabs.add(myArticlePanel, "MyArticles");
	    tabs.setWidth("1010px");
	    tabs.selectTab(0);
		
		endPanel.add(hEnd);
		endPanel.setWidth("1010px");
		endPanel.setCellHorizontalAlignment(hEnd, HorizontalPanel.ALIGN_CENTER);
		hEnd.setStyleName("album-Comment");

		menuBar = this.createMenu();
		// Create a dock panel that will contain the menu bar at the top,
		// the shortcuts to the left, and the mail list & details taking the rest.
		DockPanel outer = new DockPanel();
		outer.add(menuBar, DockPanel.NORTH);
		outer.add(tabs, DockPanel.CENTER);
		outer.add(endPanel, DockPanel.SOUTH);
		outer.setWidth("1010px");

		outer.setSpacing(4);
		outer.setCellWidth(tabs, "100%");

		vpAlbum.add(outer);
		vpAlbum.setCellHorizontalAlignment(outer, HorizontalPanel.ALIGN_CENTER);
		vpAlbum.setWidth("1010px");
		
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

	public void slidePhotos(IsPicture[] photos, int index) {
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
		}
	}

	public void slideMyPhotos(IsPicture[] photos, int index) {
		//albumDetail.start();
		myAlbumList.start();
		mySlideShow = new MySlideShow(myAlbumDetail, photos, index);
		mySlideShow.scheduleRepeating(7000);
	}

	public void stopMySlide() {
		if (mySlideShow != null) {
			mySlideShow.cancel();
			mySlideShow = null;
			//myAlbumDetail.stop();
			myAlbumList.stop();
		}
	}
	
    public void onWindowResized(int width, int height) {
    	vpAlbum.setSize("" + width, "" + height);
    }

    public void setSelectedTab(String tab) {
    	if (BlogCommand.CMD_VIEW_PHOTO.equals(tab)) {
    		tabs.selectTab(0);
    	} else if (BlogCommand.CMD_VIEW_ARTICLE.equals(tab)) {
    		tabs.selectTab(1);
    	} else if (BlogCommand.CMD_VIEW_MYPHOTO.equals(tab)) {
    		tabs.selectTab(2);
    	} else if (BlogCommand.CMD_VIEW_MYARTICLE.equals(tab)) {
    		tabs.selectTab(3);
    	}
    }
    
}
