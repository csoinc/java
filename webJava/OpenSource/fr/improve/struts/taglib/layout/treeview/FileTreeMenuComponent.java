package fr.improve.struts.taglib.layout.treeview;

import java.io.File;

import fr.improve.struts.taglib.layout.menu.MenuComponent;

/**
 * Example of a lazy initialized MenuComponent showing a file system content.
 * 
 * @author jnribette
 */
public class FileTreeMenuComponent extends MenuComponent {
	private File file;
	private boolean inited = false;
	
	public FileTreeMenuComponent() {
		this(new File("/"));
	}
	
	public FileTreeMenuComponent(File in_file) {
		file = in_file;
	}

	
	public MenuComponent[] getMenuComponents() {
		synchronized(this) {
			if (!inited) {
				inited = true;				
				File[] files = file.listFiles();
				if (files!=null && files.length!=0) {
					System.out.println("Loading childs of " + file.getAbsolutePath());
					for (int i = 0; i < files.length; i++) {
						File subFile = files[i];
						addMenuComponent(new FileTreeMenuComponent(subFile));
					}
				}
			}
			return super.getMenuComponents();
		}
	}
	
	/**
	 * The title of the item if the file name.
	 */
	public String getTitle() {
		return file.getName();
	}
	
	public boolean hasMenuComponents() {
		return file.isDirectory();
	}
}
