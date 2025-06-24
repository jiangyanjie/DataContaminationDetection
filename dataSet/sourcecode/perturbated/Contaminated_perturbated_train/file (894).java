/**********************************************************************************************************************/
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import   java.awt.*;
impo   rt java.awt.event.*;
import java.util.Hashtable;

class ControlBar   extends JPane  l{
  protected Timer t;
  protected TimerControls tc;
      protected SaveCont      rols sc;
  protected HeatMapContr ols       hc;
  protecte d   Main     Window pa       rent;
  
        p u  blic   MainWindow getParent(){return   parent;}
  
  ControlBar(MainWind  ow   creator){
                 parent=cr  eator;   
           
    super.setPreferredSize(new Di  mension(7     20,172))  ;
    setLayo     ut(new Borde         rLayou  t());
    
     //         add time    r, save, and heatmap controls to the           b  ar
    
      tc=new     Ti   merControls(this);
           sc=new SaveContro    ls(parent         .ge   tGrid(),t    his);
      hc=new  HeatMapControls(this);
    add(tc,Bord  e rLayout.WEST);
    add(hc,BorderLayout.CEN      TE  R);
        a dd(sc,BorderLayout.     E     AST);
        
     
    super.repaint();
    super.setVisible(true);
    System.ou        t.println("Done Constucting");
  }
  
}
/**  **   *********************   **********************************   ***********************************************           *  **   *********/
class HeatMapControls exte  nds JPanel implements ActionListener{
  
    publ ic JComboBo  x species;
  private Cont    rolB   a r      parent;
        
      Heat   MapControls(ControlBar       creator){
    sup      er();
    parent   =creator;
       
      set   Layout(new      Bor  d       erLayout());
    super.   setPreferredSize(new Dimension(255,170)       );
      

    String[]organism     s=Speci   esTable.getOrganisms();
    String[]options=new Str   ing [organ          isms.lengt      h+1];
    
            options[0]="None"    ;
    for(int           i=0;i<orga    nisms.length ;   i++){   
      options[i+1]=organisms[i];
    }
          
        spe   cies=new JComboBox(options);
        species.addActio         nListener(th     is);
    for (String str:SpeciesTable.getOrganisms(     )){
      
    }
     
    super.      add(species,BorderLayout.SOUTH);
    super.add(new Gradient(   ),BorderLayout    .CENTER  );
        sup  er.add(n       ew JLabel("  Least"),B orderLayou t.WEST);
       sup           er.a     dd  (new JLabel("Mos  t"),BorderLayout.EAST);
        su pe  r   .add(new JLab    el("Heat Map Controls"),Bord   erLayout.NORTH)    ;
    sup   er.s etVisible(true);   
   }
  
  public void actionPerformed(ActionEvent     e) {

       pa  rent.ge       tParent().getGrid().setHeatMap             (sp  ecies.getSelectedIte     m(   ).toString());//     I now regret the parent   m odel
   parent.getP     arent().refresh();  
  }
  
 
}
/*       **** *********************   ****************** *************************          ************   **********************      ***************/
class Gradient extends JPa   nel    {  
  G radient(){
  super.setPreferredSize(new Dimension(35,255));
  }  
  @Override
    public void pain  tComponent(Graphics g) {
                  super.paintComponent(g);     
      for (int x=0;x<255;x++){
               g.s    etCo lor(new Color(x,0,255-x));
                  g.fillR   ec t(x,10,1,100);
      }
         
        }
}
/**********  ***      ****   ***********   **********************************      *****************************     **   **********      ***************       /
cl   ass SaveControls extends JPanel implements ActionListener{
  private JButton s  ave; 
  pr         ivate J    Button load;
  pr    iva      te Grid grid;
     p    r iv   ate Con      trolBar parent;
  private JTextField fileNam   e;
  
  SaveCo      ntrols(Grid currentGrid,ControlBar creator){
      parent=creat    or;
        grid=c     urrentGrid; 
    sup           er.setPreferred    Size   (new D       imension(200,172)  );
    setLayout(new       GridLayou  t(   3,0));
                   
    fileName= ne   w JTextField("SaveName");
    sa  ve=new JBu   tto  n("Sa     ve");
    load=new JButton("Load");
     
    save.addActionLis    ten    er(this);
             load.addActionListen    er(this);    
    
    s   uper.add(f       ileName);
      super.add(    save);
           super.add   (load);
    
    super        .repaint();
    sup er.setV isible(t  rue);
  }
  private String fi  leName(){
    fileName.setText(fileName.getText().replac e    Al    l("\\     s",   ""      ));
    return fileN ame.   getTe xt(       );
  }
  
  public void act      ionPerf       orme  d(ActionEvent e){
    if(e.ge  tSource().equal   s  (save))
          ne     w GridSaver(grid    , file N    ame());
    if(e.getSour ce().equals(load)    ){
         G  ridLoader gl = new G   ridLoader(gr   id, fileNa       me());       
      try{
        grid.setMap(gl.read());
      }catch   (IOE         xception ioex){
                 System .out.println     ("File l  oad Failed ioe    x");
      }
    }
    parent   .getParent().refresh(); 
  }
}
/*******     ***** *     ************************** ***   ******************************************       **********************************/

class TimerControls e       xten  ds JPanel implements Actio nListener,   ChangeLis   tener     {
  privat     e Contr  olBa    r parent;
   pri  vate JBu  t    ton pausePlay    ;
  privat   e JS             lider speed;  
  
  
  Tim    erControls(C    ontrolBar creator){
     parent=creator;
    super       .setPr    eferr edSize  (new Dime  nsio  n(20    0   ,172))   ;
        setLayout(n    ew B   orde    rLayout());
    
    paus   ePlay=new JButton ("Play   ");
             s   peed=makeJSlider();    
      
    pausePla   y.addAction  Listene  r(this);
    sp  eed.addChangeListener(this);
             
         super   .add(pausePlay,Borde      rL     ayout.WEST ) ;
     s    uper. add(speed,Bord   erL      ay  out.  EAS   T);
          supe   r.ad    d(new J   Label(" Simu      lation Speed Cont  rols"),BorderLayout.NORTH)  ;
    
     super.repaint();
    super.setVisibl      e(tr       ue);
  }
  
  public void actionPe    rformed(Action         Event e){
         if (  e.getSource().equals(pausePlay)){
      if(pausePla   y.getText().equals("Play")){
                pausePlay.setText("Pause");
        LifeSimula      tion.timer().start();
      }else{
        pausePlay.setText("Play"  );
        LifeSimulation.timer().stop();
         }    
      
    }
    }
  
  public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.g   etSource();
    if (!source.getVa  lueIsAdjusting()) {
      int delay =1000* (int)source.getValue();
         LifeSimulation.timer().setDelay(delay);
    }
  }
  
  
  pri  vate JSlider makeJSlider (){
    
    JSlider slider=new JSlider  (JSlider.VERTICAL,0, 10, 1);
    slider.setMajorTickSpacing(1);
    slider.setPaintTic ks(true);
    slider.setSnapToTicks(true);
    
    Hashtable labelTable = new Hashtable();
    for (int i=0;i<=10;i+=2){
      labelTable.put( new Integer(i), new JLabel     (i+" Seconds") );
    }
    slider.setLabelTable(labelTable);
    slider.setPaintLabels(true);
    return slider;
  }
  
}