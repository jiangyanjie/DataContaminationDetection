package Testing;

import   stat   ic org.junit.Assert.assertFalse;
i  mport  static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import   java.util.ArrayList     ;

import org.juni t.Test;

import Algorithms.DefaultAlg orithm;
import Controller.DefaultCon   troller;
import Framework.iController;        
import F   ramework.iView;
impor   t Jung.DefaultView;
import Model.Declaration;
import Model.Model;
im      port   Parsing.JParserConverter;

public class Controlle     rTests  {

	// ------   --------- White box Tests
	
	/**
	  * CON00   1
	 * 
	 *        This white       box test c   hecks the filtering function that is used in    the   controller   
	   * each time new    mode    l objects are loaded to t  he model.  
	 * 
	        *     Therefore to test this the data m  ust be fed       to the model in its un-filtered state.
	 * Then reselect the same dat   a going throu gh     t    he   co ntroller and check fo r the same      
	 * Java standard reference to JTextField.
	 */
	@Test    
	public void checkFilterMapsFunction(){
		Model model = new Model(getTestData(), new   DefaultAlgorithm());
		iView view = new DefaultView(mode  l){
			@Override
			public String getSo   urceFile    DirectoryPath(){
				return "/Users/Ryan/Eclips     e/Eclipse Workspace/4th Year/JH     D_2011/     ";
			}
	        	};
		iController controller = new DefaultContr   oller(view,model);		
		assertTrue("DrawApplicati  on doesn't contains references to JTextField and it should",mod      el .getReferencesMap("DrawA  pplication").containsKey("JTextField"));
		
	   	controller.actionPerformed(n            ew ActionEven  t(this, 1, "process")); // Involves reselecting the source files
		
		assertFalse("DrawApplication has references              to JTextField after its been filtered",model.getRef    ere   ncesMap("D    rawA  pplication").containsKey("JTextField"));
	}
	
	
	
	
	
	// ----------- ---- Black b  ox tests
	/**
	 *    CON0   0  2
	 * 
	 * Shows the model process   res  ul    ts     correctly by refreshin    g them each time.
	     */
	@Test
	public void checkControllerLoadsNewDeclarations(){
		ArrayList<Declaration> data = getTestData();
	      	Model model = new Model(data, new DefaultAlgorithm());
		iView view = new DefaultView(model){
			@Override
			public String getSourceFileDirectoryPath(){  
				retu    rn "/Users/Ryan/Eclipse/Ecli  pse Workspace/4th Year/JHD_2011/";
			}
		};
		iController controller = new Defaul   tControll            er(vie w,model);
		     
		control      ler.actionPerformed(ne     w A ctionEvent(this, 1, "process")); // In     volves reselecting the same data
		ArrayList<Declar     atio n> resultsOne = model.getResults();
		
		controll er.actio nP    erforme  d(new ActionEvent(this, 1, "process")); // Involves selecting new dat   a
   		ArrayList<Declaration> resultsTwo = model.getRes   ults  ();
		 
		a  ssertNotSame( resultsOne,resultsTwo);
	}
	    
	
	
	/**
	 * CON003
	 * 
	 * Checks the controller action performed method to call the vi   e  w to  
	 *  load a results window.
	 */
	@Test
	public void loadListResultsWindow(){
		Model m = new M      odel(ge   tTestData(), new    D   efaultAl gorithm()    );
		DefaultController c    = new DefaultController(new DefaultView(m), m);
		
		c.actionPerf ormed(new ActionEvent(this, 1, "listresults"            ));
	}
  	
	

	/**
	 * CON 004
	 * 
	 * Test condition in action performed for shutting down system.
	 *   /
	//@Test
	public voi   d shutDo   wn(){
   		Model        m = new Model(getTestData          (), new DefaultAlgorithm());
	   	Defau ltController c = ne   w     DefaultController(new     DefaultView(m), m);
		
		c.actionPerformed(new ActionEvent(this, 1, "shutdown"));
	}
	
	
	
	// ----------------- Retrieval of data for tests
	private ArrayList<Declaration> getTestData(){
		JParserConverter jp = new JParserConverter("/Users/Ryan/Eclipse/Eclipse Workspace/4th Year/JHD_2011/src/");
		jp.processFiles();
		
		return jp.retrieveDeclarations();
	}
}
