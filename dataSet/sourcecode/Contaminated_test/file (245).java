/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.model.menu.MenuItem;

/**
 *
 * @author Shashwat
 */
@ManagedBean(name = "dashboard")
@SessionScoped
public class dashboard {

	  private dashboard dashboard;
	  private Menubar menubars = new Menubar();
	  Application application = FacesContext.getCurrentInstance().getApplication();
	  //private MenuItem menuitem;
	  //private Submenu submenu;

	  public dashboard() {
	  }

    public void setMenubars(Menubar menubars) {
        this.menubars = menubars;
    }

	  public Menubar getMenubars() {

			//InputText in1=(InputText)application.createComponent(InputText.COMPONENT_TYPE);
			menubars.clearInitialState();

			UISubmenu submenu =new UISubmenu();
			
			for(int i=0;i<10;i++)
			{
			submenu = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			submenu.setLabel("hi");
			menubars.getChildren().add(submenu);
			}
			
			
		/*	UISubmenu submenu1 = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			UISubmenu submenu2 = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			UISubmenu submenu3 = (UISubmenu) application.createComponent(UISubmenu.COMPONENT_TYPE);
			
			
			submenu1.setLabel("label1");
			submenu2.setLabel("label2");
			submenu3.setLabel("label3");

			UIMenuItem m1 = (UIMenuItem) application.createComponent(UIMenuItem.COMPONENT_TYPE);
			UIMenuItem m2 = (UIMenuItem) application.createComponent(UIMenuItem.COMPONENT_TYPE);
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

			menubars.getChildren().add(submenu1);
			menubars.getChildren().add(submenu2);
			menubars.getChildren().add(submenu3);
		 * 
		 */
			return menubars;
	  }

	  public void setMenubar(Menubar menubar) {
			this.menubars = menubar;
	  }

	  public void msg() {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login Sucessful", "Welcome You..."));
	  }
}
