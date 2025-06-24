package cookmaster.client;

import java.awt.Label;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import cookmaster.client.CookReceipeForm;

public class Cookmaster implements EntryPoint {

	private void refreshWatchList()
	{
	}
	
	@Override
	public void onModuleLoad() {
		// Create table for stock data.
		Timer refreshTimer = new Timer() {
			
			@Override
			public void run() {
				refreshWatchList();
			}
		};
	    final CookReceipeForm recepie_form = CookReceipeForm.CreateCookReceipeForm();
	    final UsersForm user_form = UsersForm.CreatedUserForm();
	    // Create a tab panel
	    TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
	    tabPanel.setAnimationDuration(500);
	    tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
	    // Add a tab with an image
	    tabPanel.add(recepie_form.getForm(), "Recepie");
	    tabPanel.add(user_form.getForm(), "User Form");
	    // Return the content
	    tabPanel.selectTab(0);
	    //tabPanel.setHeight("200px");
	    //RootPanel.get("cookmaster").add(tabPanel);
	    DockLayoutPanel p = new DockLayoutPanel(Unit.PX);
	    p.addNorth(onInitialize() , 40);
	    p.add(tabPanel);
	    
	    RootLayoutPanel.get().add(p);
	    
	}
	
	  public Widget onInitialize() {
		    // Create a command that will execute on menu item selection
		    Command menuCommand = new Command() {
		      private int curPhrase = 0;
		      private final String[] phrases = {"hi", "ho", "hu"};
	
		      public void execute() {
		        Window.alert(phrases[curPhrase]);
		        curPhrase = (curPhrase + 1) % phrases.length;
		      }
		    };
	
		    // Create a menu bar
		    MenuBar menu = new MenuBar();
		    menu.setAutoOpen(true);
		    menu.setWidth("500px");
		    menu.setAnimationEnabled(true);
	
		    // Create a sub menu of recent documents
		    MenuBar recentDocsMenu = new MenuBar(true);
		    String[] recentDocs = {"recent", "files"};
		    for (int i = 0; i < recentDocs.length; i++) {
		      recentDocsMenu.addItem(recentDocs[i], menuCommand);
		    }
	
		    // Create the file menu
		    MenuBar fileMenu = new MenuBar(true);
		    fileMenu.setAnimationEnabled(true);
		    menu.addItem(new MenuItem("file cat", fileMenu));
		    String[] fileOptions = {"file", "options"};
		    for (int i = 0; i < fileOptions.length; i++) {
		      if (i == 3) {
		        fileMenu.addSeparator();
		        fileMenu.addItem(fileOptions[i], recentDocsMenu);
		        fileMenu.addSeparator();
		      } else {
		        fileMenu.addItem(fileOptions[i], menuCommand);
		      }
		    }
	
		    // Create the edit menu
		    MenuBar editMenu = new MenuBar(true);
		    menu.addItem(new MenuItem("edit", editMenu));
		    String[] editOptions = {"edit", "options"};
		    for (int i = 0; i < editOptions.length; i++) {
		      editMenu.addItem(editOptions[i], menuCommand);
		    }
	
		    // Create the GWT menu
		    MenuBar gwtMenu = new MenuBar(true);
		    menu.addItem(new MenuItem("GWT", true, gwtMenu));
		    String[] gwtOptions = {"Menu", "Bar", "Options" };
		    for (int i = 0; i < gwtOptions.length; i++) {
		      gwtMenu.addItem(gwtOptions[i], menuCommand);
		    }
	
		    // Create the help menu
		    MenuBar helpMenu = new MenuBar(true);
		    menu.addSeparator();
		    menu.addItem(new MenuItem("help", helpMenu));
		    String[] helpOptions = {"help", "options"};
		    for (int i = 0; i < helpOptions.length; i++) {
		      helpMenu.addItem(helpOptions[i], menuCommand);
		    }
	
		    // Return the menu
		    menu.ensureDebugId("cwMenuBar");
		    return menu;
		  }
}
