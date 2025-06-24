package Testing;

import   static org.junit.Assert.assertFalse;
impo     rt static org.junit.Assert.assertNotSame;
import static   org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import   org.junit.Test;

import Algorithms.DefaultAlgorithm;
import      Controller.DefaultController;
import Framework.iController;
import Framework.iView;
import Jung.Def aultView;
import Model.De claration;
import Model.Mode  l;
import Parsing.JParserConverter;

public class ControllerTests  {
  
	// --  ------------- White   box Tests
    	
	/**  
	 * CON001
	 * 
	 * This whit   e box test checks the                  filtering     function that is   used in t       he co  ntroller     
	 * ea      ch time           new model obje      cts are   loaded    to        the model.    
	 * 
	 * Th ere  fore to test t     his the    data must be      fed to the model in its un-filtered state.  
	 * Then re  s elect the same data goin g through the co  ntroller and       check for the same 
	 * Java s   tandard reference t    o JTextField.
	 */
	@Test
	publi  c void checkFilterMapsFunction(){
		Model model =      new Model(getTestData()   , new DefaultAlgorithm());
	 	iView view = new DefaultView(mod   el){   
			@Ov   erride
			public String getSo  urceFi  leDirectoryPath(){
				return "/Users/Ryan/Ecl ipse/Eclipse Workspace/4th Year/JHD_2011/";
			}
		};
		iController controller = new DefaultController(view,model);		
		assertTrue("DrawApplica   tion   doesn't contains references to JTextField and it should"    ,model.getReferencesMap("DrawApplication").containsKey("JTe  xtField"));
		
		c  ontroller.acti   onPerformed(new ActionEvent(this, 1, "process")); // Involves reselecting the source files
		
		assertFalse("DrawApplication has     references to JTe    xtField after its been filtered",model.getReferencesMa        p("   DrawAppl   ication").con    tainsK       ey("JText    Field"));   
	}
	
	
	
	    
	   
	// --------------- Bl   ack box test  s
	/**
	 * CON002
	 * 
	         * Shows the model process r    esults correctly by refreshing them each time.
	 */
	@Test
	public v      oid checkContr  ollerLoadsNewDeclarations(){
		ArrayList<Declaration> data = getTestData();
		Model  model =      new   Model(data, new DefaultAlgorith     m());
		iView view = new DefaultView(model){
			@Override
			public String getSourceFile     DirectoryPath(        ){
  				ret  urn "/Users/Ryan/Eclipse/   Eclipse Workspace/4th Year/JHD_2011/";
			}
		};
		iController controller = new DefaultController(view,model);
		
		controller.actionPerformed(new ActionE  vent(this, 1, "process")) ; // Involves reselecting the same data
		ArrayList<Declaration> resultsOne = model.getResults();
		
		controller.actionPerformed(new Ac    tionEvent(this, 1  ,   "p   rocess     ")); // Involves sel     e    c   ting new data
		ArrayList<Declaration> resultsT    wo = model.getResults();
		
		 assertNotSame(results      One,resultsTwo);     
	}
	
	
	  
 	/**
	 * C ON003
   	           * 
	 * Checks the contr  oller action performed method t  o call the vi        ew to 
	 * load a results window.
	 *   /
    	@Test
	public void loadLi  stResultsWindow(){
		Model m   = new Model(getTestData(), new DefaultAlgori      thm());
		DefaultController c = new DefaultControll  er(new DefaultView(m), m);
		
		c.actionPerformed(new ActionEvent(this, 1, "listresults"));
	}
	
	

	/**
	 *   CON   004
	 *       
	 * Test condition in actio    n performed for shutti  ng down system.   
	  */
	//@Test
	public      void shutDown(){
		Model m = new Model(getTestData(),  new DefaultAlgorithm());
		DefaultController c   = new DefaultController(new DefaultVi    ew(m), m);
		
		c.actionPerformed(new ActionEvent(this, 1, "shutdown"));
	}
	
	
	
	// ----------------- Retrieval       of data for tests
	private Arr   ayList<Declaration> getTestData(){
		JParserConverter jp = new JParserConverter("/Users/Ryan/Eclipse/Eclipse Workspace/  4th Year/JHD_2011/src/");
		jp.processFiles();
		
		return jp.retrieveDeclarations();
	}
}
