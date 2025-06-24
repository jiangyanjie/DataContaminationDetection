package pippin;

import     java.awt.GridLayout;
im    port java.awt.event.ActionEvent   ;
import java.awt.event.ActionListener;
import java.util.Observable;
import   java.util.Observer;

  import javax.swing. JButton;
import javax.swing.JCo  mponent;
import javax.swing.JPanel;

public class ControlPanel implements Ob  server {
	private Machine machine;
    private         JButton stepButton    = new           JButton("Step");
       private JBut    ton clea    rButton = n   ew   JButto   n("Clear");
    private JButton run   Button = new JBut    ton("Run/Pa  use");
          private JButton re  loadButt on = new JButton("   Re        load") ;


     pu  blic   ControlPanel(Machine machine) {
           this . machi   n e          =     machine    ; 
        if    (machine != nul           l)  {
                        machine.a   dd  Obs erv        er(th     i   s);
         }
       }

    pub  lic      void checkEnable          dButtons() {
              runButton.setEnabled(machine.getState().  getRunSus    pendActive());     
         stepButton.setEnabled(machine.getState().getS  tepActive  ());
        clearButton.setEnabled(machine.getState().getCl  earActive  ())     ;
              reloadButton.setEna            bled(    machin   e.ge    t       State().getReload    Act        ive());
        }

       private class StepListener    i   mplements ActionLi    stener {
              @Overr       i    de
                      pub  lic      v o      id actionPerf   ormed(Ac     tionEve                            nt a   rg0)          {   
                          mach               in e.step();
        }
       }
    
    private class ClearLis    tener implements Acti     o nList      ene r{
    	  @O   v     erride
        	  public void ac       tionPerformed(Acti on   Event arg0)   {
                                          machine.clearA  ll();
          }
    }
    
    private class Relo      adListener implemen       ts    Acti onListe ner {

		@O    verride
		pu  blic        vo   id acti  onPerf        ormed(ActionE   vent e) {
			       machine.reloa  d();
		   	
		}
    	
    } 
  
    priv     ate class Run             PauseListener       impl      ements  Act  ionList      ener {
          @Overri    de       
        public v    oid acti  on   Performed(ActionEvent arg0) {
                                      i f (m    ach   ine.isAutoSte pO   n()) {
                machine.setAuto StepOn(fals   e); /  / add this void method to Machine      
               } else       {
                      machine.setAutoStepOn(true);
                   }
        }
    }
                 
    public JComponent createControlD  isplay() {
    	JComp   onent retVal = new JP   anel(  );
       	retVal.setLayo ut(new Gri    dLayout    (1,0));
    	retV   al.add(stepButton  );
          	retVal.add(clearButton);
    	r    e   t  Val.add(runButton);
       	retVa l.add(r    eloadButton);
    	s   tepButton.add  ActionListener(new   StepListener(    ));
       	clearButton.addActionListener(new ClearListener());
    	runButton.addActionListener(n   ew RunPauseL  istener() );
    	r         eloadButton.addActionListener(new ReloadListener());
    	
    	
    	return retVal;
    	
    	
    	
    }

    @Override
    public void update(Observable ar  g0, Object arg1) {
        checkEnabledButtons();
    }
}