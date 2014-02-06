package com.oyou.gwt.console.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.oyou.gwt.console.client.ConsoleDataProvider.ConsoleDataAcceptor;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Console.java,v 1.1 2008/06/29 14:24:29 oyou Exp $
 * @since Nov 12, 2007
 */
public class Console implements EntryPoint, WindowResizeListener {
	private class ConsoleDataAcceptorImpl implements ConsoleDataAcceptor {
		public void accept(IsError data) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			sbStatus.stop();
			ciImport.reset();
			if (IsError.SUCCESS.equals(data.getMessage())) {
				errorDialog.setInfo("Success.");
			} else {
				errorDialog.setError("Failed.");
			}
		}

		public void failed(Throwable caught) {
			sbStatus.stop();
			if (caught instanceof InvocationException) {
				showErrorDialog(NO_CONNECTION_MESSAGE);
			} if (caught instanceof ConsoleException) {
				//process business exception here
				if (ErrorConstants.error_message_authority.equals(caught.getMessage())) {
					Console.get().login();
				} else {
					showErrorDialog(caught.getMessage());
				}
			} else {
				showErrorDialog(caught.getMessage());
			}
		}
		
		private void showErrorDialog(String msg) {
			if (errorDialog == null) {
				errorDialog = new ErrorDialog();
			}
			errorDialog.setError(msg);
		}
	}
	
	public class ConsoleDataProviderImpl implements ConsoleDataProvider {
		private final ConsoleDataServiceAsync calService;

		public ConsoleDataProviderImpl() {
			calService = (ConsoleDataServiceAsync) GWT.create(ConsoleDataService.class);
			ServiceDefTarget target = (ServiceDefTarget) calService;
			String moduleRelativeURL = GWT.getModuleBaseURL();
			if (moduleRelativeURL != null && !"".equals(moduleRelativeURL)) {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:83/blogger/consolerpc"; //Debug
				} else {
					moduleRelativeURL += "../../consolerpc"; //JSP 
				}
			} else {
				if (DEBUG) {
					moduleRelativeURL = "http://localhost:83/blogger/consolerpc"; //Debug
				} else {
					moduleRelativeURL += "../../consolerpc"; //JSP 
				}
			}
			target.setServiceEntryPoint(moduleRelativeURL);
		}

		public void importPhotos(String photoPath, String albumId, String comment, final ConsoleDataAcceptor acceptor) {
			calService.importPhotos(photoPath, albumId, comment, new AsyncCallback() {
				public void onFailure(Throwable caught) {
					acceptor.failed(caught);
				}

				public void onSuccess(Object result) {
					acceptor.accept((IsError)result);
				}
			});
		}
	}

	public final static boolean DEBUG = false;
	private static final String NO_CONNECTION_MESSAGE = "Unable to connect RPC server!";
	private static Console singleton;

	public static Console get() {
		return singleton;
	}


	private ErrorDialog errorDialog = null;

	private ConsoleDataAcceptor acceptor = new ConsoleDataAcceptorImpl();
	
	private ConsoleDataProvider provider = new ConsoleDataProviderImpl();

	//private VerticalPanel centerPanel = new VerticalPanel();
    private StatusBall sbStatus = new StatusBall();
    private ConsoleImport ciImport =  new ConsoleImport();
    
    private VerticalPanel vpConsole = new VerticalPanel();
	private DockPanel outer = new DockPanel();
    
    public MenuBar createMenu() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);

		MenuBar menuBible = new MenuBar(true);
		menuBible.addItem("<font color='#000000'><i>Index</i></font>", true, new ConsoleCommand());
		menuBible.addItem("<font color='#000000'><i>Seleted</i></font>", true, new ConsoleCommand());
		menuBible.addItem("<font color='#000000'><i>Search</i></font>", true, new ConsoleCommand());

		MenuBar menuFile = new MenuBar(true);
		menuFile.addItem("<font color='#000000'><i>New Photo</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_NEW_PHOTO));
		menuFile.addItem("<font color='#000000'><i>New Article</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_NEW_ARTICLE));
		menuFile.addItem("<font color='#000000'><i>" + Constants.EXIT + "</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_EXIT));

		MenuBar menuAcct = new MenuBar(true);
		menuAcct.addItem("<font color='#000000'><i>Login</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_LOGIN));
		menuAcct.addItem("<font color='#000000'><i>Logout</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_LOGOUT));
		menuAcct.addItem("<font color='#000000'><i>Register</i></font>", true, new ConsoleCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new ConsoleCommand());
		menuAcct.addItem("<font color='#000000'><i>...</i></font>", true, new ConsoleCommand());
		
		MenuBar menuView = new MenuBar(true);
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_PHOTO + "</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_VIEW_PHOTO));
		menuView.addItem("<font color='#000000'><i>" + Constants.VIEW_ARTICLE + "</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_VIEW_ARTICLE));
		menuView.addItem("<font color='#000000'><u><i>" + Constants.BIBLE + "</i></u></font>", true, menuBible);

		MenuBar menuMy = new MenuBar(true);
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYPHOTO + "</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_VIEW_MYPHOTO));
		menuMy.addItem("<font color='#000000'><i>" + Constants.VIEW_MYARTICLE + "</i></font>", true, new ConsoleCommand(ConsoleCommand.CMD_VIEW_MYARTICLE));

		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.FILE + "</b></font>", true, menuFile));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.ACCOUNT + "</b></font>", true, menuAcct));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.VIEW + "</b></font>", true, menuView));
		menu.addItem(new MenuItem("<font color='#000000'><b>" + Constants.PERSONALS + "</b></font>", true, menuMy));

		menu.setWidth("1000px");

		return menu;
	}

	public void importPhotos(String photoPath, String albumId, String comment) {
		sbStatus.start();
		provider.importPhotos(photoPath, albumId, comment, acceptor);
	}

	public void login() {
		Command cmd = new ConsoleCommand(ConsoleCommand.CMD_LOGIN);
		cmd.execute();
	}
	
	/**
	 * This method constructs the application user interface by instantiating
	 * controls and hooking up event listeners.
	 */
	public void onModuleLoad() {
		singleton = this;

	    TabPanel tabs = new TabPanel();
	    tabs.add(ciImport, "Import");
	    tabs.add(new HTML("Upload"), "Uploads");
	    //tabs.add(null, "Users");
	    tabs.setWidth("1000px");
	    tabs.selectTab(0);

		outer.add(this.createMenu(), DockPanel.NORTH);
		outer.add(tabs, DockPanel.CENTER);
		outer.add(sbStatus, DockPanel.SOUTH);
		outer.setWidth("1000px");

		outer.setSpacing(4);
		outer.setCellWidth(tabs, "1000px");

		vpConsole.add(outer);
		vpConsole.setWidth("100%");
		vpConsole.setCellHorizontalAlignment(outer, HorizontalPanel.ALIGN_CENTER);

	    // Hook the window resize event, so that we can adjust the UI.
	    Window.addWindowResizeListener(this);

	    // Get rid of scrollbars, and clear out the window's built-in margin,
	    // because we want to take advantage of the entire client area.
	    Window.enableScrolling(true);
	    Window.setMargin("0px");
		
		RootPanel.get().add(vpConsole);

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
	
    public void onWindowResized(int width, int height) {
    	vpConsole.setSize("" + width, "" + height);
    }
	
}
