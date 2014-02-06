package com.oyou.gwt.menu.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.oyou.gwt.menu.client.MenuDataProvider.MenuDataAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Menu.java,v 1.1 2008/06/29 14:24:36 oyou Exp $
 * @since Nov 12, 2007
 */
public class Menu implements EntryPoint {

	private class MenuDataAcceptorImpl implements MenuDataAcceptor {
		public void acceptMenus(IsMenu[] datas) {
			setCategories(datas);
		}
		
		public void failed(Throwable caught) {
			if (caught instanceof InvocationException) {
				showErrorDialog(NO_CONNECTION_MESSAGE);
			}  else {
				showErrorDialog(caught.getMessage());
			}
		}
		
		private void showErrorDialog(String msg) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			errorDialog.setError(msg);
			errorDialog.center();
		}
	}
	
	public class MenuDataProviderImpl implements MenuDataProvider {
		private final MenuDataServiceAsync calService;

		public MenuDataProviderImpl() {
			calService = (MenuDataServiceAsync) GWT.create(MenuDataService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8090/ajaxweb/blogAjax"; //Debug
				} else {
					moduleRelativeURL += "../../blogAjax"; //JSP - OYOSOFT 
				}
			} else {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:8090/ajaxweb/blogAjax"; //Debug
				} else {
					moduleRelativeURL += "../../blogAjax"; //JSP - OYOSOFT 
				}
			}
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void getMenus(final MenuDataAcceptor acceptor) {
			calService.getMenus(new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					IsMenu[] categories = (IsMenu[]) result;
					acceptor.acceptMenus(categories);
				}
			});
		}
		
	}

	public final static boolean DEBUG = false;
	private static Menu singleton;
	private static final String NO_CONNECTION_MESSAGE = "Unable to connect RPC server!";
	private IsMenu[] categories = null;
	private ErrorDialog errorDialog = null;

	public static Menu get() {
		return singleton;
	}

	private final MenuDataProvider dataProvider = new MenuDataProviderImpl();

	private MenuBar menuBar;
	
	public MenuBar createMenu() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);

		MenuBar menuBible = new MenuBar(true);
		menuBible.addItem("<font color='#000000'><i>Index</i></font>", true, new MenuCommand());
		menuBible.addItem("<font color='#000000'><i>Seleted</i></font>", true, new MenuCommand());
		menuBible.addItem("<font color='#000000'><i>Search</i></font>", true, new MenuCommand());

		MenuBar menuFile = new MenuBar(true);
		menuFile.addItem("<font color='#000000'><i>New Photo</i></font>", true, new MenuCommand(MenuCommand.CMD_NEW_PHOTO, null));
		menuFile.addItem("<font color='#000000'><i>New Article</i></font>", true, new MenuCommand(MenuCommand.CMD_NEW_ARTICLE, null));
		menuFile.addItem("<font color='#000000'><i>" + Constants.EXIT + "</i></font>", true, new MenuCommand(MenuCommand.CMD_EXIT, null));

		MenuBar menuAcct = new MenuBar(true);
		menuAcct.addItem("<font color='#000000'><i>Login</i></font>", true, new MenuCommand(MenuCommand.CMD_LOGIN, null));
		menuAcct.addItem("<font color='#000000'><i>Logout</i></font>", true, new MenuCommand(MenuCommand.CMD_LOGOUT, null));
		menuAcct.addItem("<font color='#000000'><i>Register</i></font>", true, new MenuCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new MenuCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new MenuCommand());
		
		MenuBar menuView = new MenuBar(true);
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_PHOTO + "</i></font>", true, new MenuCommand(MenuCommand.CMD_VIEW_PHOTO, null));
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_ARTICLE + "</i></font>", true, new MenuCommand(MenuCommand.CMD_VIEW_ARTICLE, null));
		menuView.addItem("<font color='#000000'><u><i>" + Constants.BIBLE + "</i></u></font>", true, menuBible);

		MenuBar menuMy = new MenuBar(true);
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYPHOTO + "</i></font>", true, new MenuCommand(MenuCommand.CMD_VIEW_MYPHOTO, null));
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYARTICLE + "</i></font>", true, new MenuCommand(MenuCommand.CMD_VIEW_MYARTICLE, null));

		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.FILE + "</b></font>", true, menuFile));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.ACCOUNT + "</b></font>", true, menuAcct));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.VIEW + "</b></font>", true, menuView));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.PERSONALS + "</b></font>", true, menuMy));

		menu.setWidth("100%");

		return menu;
	}

	/**
	 * This method constructs the application user interface by instantiating
	 * controls and hooking up event listeners.
	 */
	public void onModuleLoad() {
		singleton = this;

		menuBar = this.createMenu();
		// Create a dock panel that will contain the menu bar at the top,
		// the shortcuts to the left, and the mail list & details taking the rest.
		DockPanel outer = new DockPanel();
		outer.add(menuBar, DockPanel.NORTH);
		outer.setWidth("1000px");
		outer.setSpacing(4);

		RootPanel.get().add(outer);

	}

	private void setCategories(IsMenu[] categories) {
		this.categories = categories;
	}
    
}
