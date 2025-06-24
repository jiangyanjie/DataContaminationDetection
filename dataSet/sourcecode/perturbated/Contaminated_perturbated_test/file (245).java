/*
 * To change       this template, choose T    ools | Templa        tes
 * and op   en the     template in the editor.
 */
package Model;

im      port javax.faces.application.Application;
import javax.faces.application.FacesMessage;
im     port javax.faces.bean.ManagedBean;
import javax.faces.bean.Se ssionScoped;
import javax.faces.context.Faces   Context;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.com   ponent.menuitem.UIMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.pri  mefaces.model.menu.Men  uItem;

/**
 *   
 * @author       Shashwat
 */
@ManagedBean(name = "dashboar d")
@Session S    coped
public class dashboard {

	  private dashboard dashboard   ;
	  private Menubar men   ubars = new Menubar();
	  Application application = FacesContext.getCur rentInstance().getApplication();
	  //priva  te Menu   Item m   enuitem;
	  //pri  vate Submenu subme  nu;

	      public  dashbo    ard() {  
	    }

              public void setMenubars(Men    ubar menubars) {
            this.menubars = menubars;
    }

   	  public Menubar getMenubars() {

			//InputText in1=(InputText)application.createComponent(InputText.COMPO   NENT_TYPE);
			menubars.clearInitialState();

			UISubmenu submenu =new UISubmenu();
			
			for(int i=0;i<10;i++)
			{
		    	submenu = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			submenu.   setLabel   ("hi");
			menubars.getChildren().add(submenu);
			}
	 		
			
		 /*	UISubmenu subme  nu1 = (UISubmenu) application.createComp     onent(UISubmenu.COMPONENT_TYPE);
			UISubmenu submenu2 = (UISubmenu) applica      tion.createComponent(UISub       menu.COMPONENT_TYPE);
			UISubmenu submenu3 = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			
			
			submenu1.setLabel( "label1");
			submenu2.setLabel("label2");
			submenu3.setLabel("label3");

			UIMenuItem m1 = (UIMenuItem) application.createComponent(UIM  enuItem.COMPONENT_TYPE);
			UIMen      uItem m2 = (UIMenuItem) application.createComponent(UIMenuIt    em.COMPONENT_   TYPE);
			UIMenuItem m3 = (UIMenuItem) application.createComponent(UIMenuItem.COMPONENT_TYPE);
			m1.setValue("item1");
			m1.setUrl("abc.com");

			m2.setValue("item2");
			m2.setUrl("abc.com");

			m3.setValue("item3");
			m3.setUrl("abc.com");

			submenu1.getChildren().add(m1);
			submenu2.getChildren().add(m2);
			submenu2.getChildren().add(m3);

			menubars.get  Children().add(submenu1);
			menub    ars    .getChildren().add(submenu2);
			menubars.ge   tChildren()    .add(submenu3)    ;
		      * 
		 */
   			return men         ubars;
	  }

	  public vo    id setMenubar(Menubar m    enub    ar) {
			this.menubars = menu   bar;
	  }

	  public void msg() {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessag   e("Login Sucessful", "Welcome You..."));
	  }
}
