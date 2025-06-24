package cookmaster.client;

import java.awt.Label;

import     com.google.gwt.core.client.EntryPoint;
import    com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;  
import com.google.gwt.user.client.Windo  w;
import com.google.gwt.user.client.ui.DockLayoutPanel; 
import com.google.gwt.user.client.ui.HTML    ;
import com.google.gwt.user.client.ui.HorizontalSpli tPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Vert      icalPanel;
import com.google.gwt.user.client.ui.Widget;
import cookmaster.client.CookRec  eipeF       orm;

publ  ic class Cookmaster implements EntryPoint {

	priv    ate void refreshWatchList()
	{
	}
	
	@Override
	     public void onModuleLoad() {
		// Creat    e   table for stock data.
		Timer refreshTimer = new Timer() {
			
			@Override
			public void run() {      
				refre    shWatchList();
			}
		};
	      final CookReceipeForm recepie_form = CookReceipeForm.   Create CookRece   ipeForm();
	    fin    al U    sersForm us            er_form = UsersFor m .CreatedUserForm();
	    // Create    a tab panel
	    Ta   bLayoutP    ane l     tabPanel = new TabLayoutPanel   (2.5, Unit.EM);
	    tabPanel    .setAnimationDuration(500);
	    tabP    anel.getE lement().getSty  le().setM    arginBottom(10.0, Unit.P   X) ;
	    // Add a      tab wi th an ima     ge
	      tabPanel.add(r   ec    epie_form.getForm(), "    R  ecepie");
	    tabPanel.add(user_form.getForm(), "User Form");
	    /    / Return the     co  ntent
	     tabPanel.s   electTab(0);
	    //tabPanel.s   etHeight("2   00px");
	    //Ro     otPanel.get   (   "cookmaster").add(tabPanel);
            	    DockLayoutP         anel p =    new DockLa   youtPanel(Unit.PX);
   	       p.ad     dNorth(onIn     iti       alize() , 4 0); 
	    p.add(tabPa    nel);
	    
	    RootL  ayoutPanel.get().   add(p);
	    
	}
       	 
    	  public Wi dget onIni    tialize()     {
		    /   /   Create a               command that wil      l exec      ute      on menu item    selectio   n
		    Comman       d menuComman     d = new Command() {
		             priv     ate        int  curPhrase       =   0;
		      priv        ate final  String[] phr   ases = {"hi", "ho", "hu"};
	
		      public vo   id    exec      u        te  () {
		        Window.alert(phrases[curPhrase]);
	   	        curPhrase      = (curPhrase + 1) % p   hrases.length;   
		              }
		       };
	
	  	    //  Create a menu bar
		    MenuBar menu = new    MenuBar();
		        men u    .setAutoOpen(true);
		    menu.setWidth(" 500px");
		    menu.setAnimationEnabled(true);
	
		    // Create a    sub menu of     rec     en t    documents 
		    MenuBar rece         ntDoc    sMenu = new Menu   Bar(true);
		    St  ring[] recentDocs = {"recent",    "files"      }       ;
		            for (int i        =           0;     i < rece ntDocs.   length; i++)   {   
        		         recentDocsMe     nu.addIte m(recentDocs[   i], menuCommand);          
	     	    }
	
		    // C   reate the               file menu
		    MenuBa   r fileMenu = n ew M  enuBar( t  rue);
		                fileMenu.setAnimationEnabled(true);
   		    menu.addI          tem(ne  w MenuItem("file cat"       , fil   eMe  nu));
  		    Stri        ng   [   ] fileOptions  = {"fil e", "o ptions"};
	   	    for   (in   t i    = 0; i < fileOptions.lengt   h; i++) {
	   	      if (i == 3) {
	 	        fi    l  eMen         u.    ad    d   Separato   r();      
		                 fileMenu.addItem        (       fileOpti     ons[i], recentDocsMe     n  u);
		        fileMenu.addSeparat  o r();
		          } else {
		           fileMenu.addItem(fileOpti    on   s[i],     menu   Command);
		      }
		                  }
 	
		    // C     reate the e     dit menu
	  	    MenuBar editMenu = new MenuBar(tru e);
		    menu.a     ddItem(new      MenuItem("e  dit", e   ditMe    nu));
		    String[] editOp t  ions   =        {"edit"     , "options    "};
  		    f or (int i = 0 ; i < editOptions.length; i++) {
		          editMenu.addItem(  editOptions[i]                 , menuCom  mand);
	    	            }           
	
		    // Cr  eate the GWT menu
   		      MenuBar     gwtMenu     = new MenuBar(true); 
		    men    u.addIt   em(new MenuItem("GWT", true, g   w  tMenu));
  		    String[] gw  tOptions = {"Menu", "Bar", "Options" };
		    for (int     i = 0; i < gwtOptions.   length; i++) {
 		           gwtMenu.addItem(gwtOptions[i], menuC    ommand);
		    }
	
		         // Create the he    lp menu
		    MenuBar helpMenu = new MenuBar(true);
        		    menu.addSeparator();
		    menu.addItem  (new MenuItem("h elp", helpMenu));   
		    String[] helpOptio    ns = {"help", "options"};
		    for (int i = 0; i < helpOptions.length; i++) {
     		      helpMenu.addItem(helpOptions[i], menuCommand);
		    }
	
		    // Return the menu
		    menu.ensureDebugId("cwMenuBar");
		    return menu;
		  }
}
