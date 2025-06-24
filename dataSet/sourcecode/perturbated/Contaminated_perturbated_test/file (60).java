package     Command;

import Client.GUI;
import    MainWindow.MainWindow;
import Dialog.PopupWindowDimensions;
import Settings.Settings;
import   java.awt.*;
import java.awt.event.*;
i   mport java.util.List;
import javax.swing.*;
import javax.swing.event.*;
i     mport javax.swing.table.DefaultTableModel;


p ublic class CustomCommands  W   indow extends JFrame implements    WindowListener, PopupWindowDimensions, TableModelLi    ste      ner    {

                   static fina l long serialVersionUID = 1L;

    private s   tatic CustomCommandsW indow instan    ce;

         private Settings settings;
    private JT      ab  l   e         table;
        private Defaul tTableModel tableModel;
         privat     e List<Command> comman            ds;
      priva    te JButton de   le    t  e;


        public st        atic CustomCommands    Window    getInstance()   {
            if       (in  stanc  e == null  )
                 instance   = n   ew         CustomCommand    sWin    do   w();

        re  tu rn instan  ce;
       }

    private Cus   tomCommands  Window  () {
          this.    settings = Settings.getI      nsta      nc   e();

        s      etAlwaysOnTop(true);
           setDefaultCloseOperation (H    IDE_   O    N_   CLO SE      );
        setIc onImage( new   I  mageI  con("img/commands    -i  co      n.      png").getImage() );
               setResizable(f    alse);    
                        setSize(WI NDOW_WID    TH, W  INDOW_HEIGHT);
        setTitle("VlastnÃ­ pÅ    Ã­kazy   ");

        cre  at   eMainPanel     ();
        reloadCommands    Tab le(      );

        addWindowListener(this);
            }

             protected    fi  nal void createMa     inP anel()     {  
        J  Panel c     ontentPan    el = (JPanel) getC o    n tent      Pane();
        Spring  Layout layout = new Sprin  gLayout();
        contentPanel.setLayout(layout);

        t    ableM  odel =      new Defaul       t   TableModel     ();
        tableModel.add       Column("NÃ¡zev");
           tableModel. add   Column("PÅÃ­kaz");
        t   ableModel.addTableModelLis             tener(this);

        table = new JTabl    e(tabl  eModel  );
        table.   setSelectionMode(L   istSele   ctionModel.SINGLE_SELECTION);
        table.setShowVertic         alLines(false);
        table.getSelect    ionModel().add     Lis   tSel ectionL   iste       ner( n  ew TableSelectionListener () );

               int columnPart = WI      NDOW_WIDTH    / 5;
             table.getColumnMo   del( )    .getColumn(0). setP     referredWi     dth(columnPar  t);
        t       able.getColumnMod        el().getCol       umn(1).setPref       erredWidth(columnPart * 4);
          table.setRowMargin (10);
        table.setRowHeigh   t(30); 

        J    ScrollPane tablePanel = n       e    w JSc     rollPane(table); 
        tablePanel.setB    ackground(Color.WHITE);
         tableP      anel.setB  order( BorderFactor   y.creat    eEmptyBord    er() );
        GUI.setEx  actSiz   e(tablePa   nel   , WINDOW_WIDTH - 10, 210  );

         JPanel bu      ttonPanel = crea  t    eButtonPanel( );
              GU   I.setEx  a   ctS     ize(but    t    onPanel,    WIN            DOW_WIDTH, 60);

             layou    t.p          utCo  nstra     int(  SpringLayout.WEST, but   tonPa  nel,     0         , Sp    rin     gLayo ut.WEST, tablePanel);
        la    yout.putCons tr  aint(Spri ng      L     ayout.NORTH, buttonPanel, 0,    Spr  ingLa       yo     ut.SOU  TH, tablePanel)   ;  

        content  Pan    el.a        dd(tablePanel);
        conte         ntPanel.      add( Box.createV  er tical      Glue() );
          contentPa    nel. add(button      P    anel);
      }
            
    protect   ed      JPanel createB  uttonPanel() {
                        JBut    ton c  r    eate      = new JButton("PÅid            at");
                create.add  Ac     ti     o       nListen   er( new A   ction Lis       tener()   {
                @O        verride
                    p   u        bli         c          void ac   tionP     erfor    med(Ac  tionEvent e) {  
                               acti  onAddCommand();
                         }
          })              ;

                delete = n       ew          JB  u t ton("Odeb       rat ")         ;
             delete.setEnable  d(f    als      e);
         d           elete      .addActi    onL      istener( new    Act   i   onL istener()    {
                @Overrid  e
                                 publi    c    voi  d actio  nPer   formed(Act i        onEvent e) {
                     a    c  t   ionDelet  eCur   rent();
                              }
                   });

        JB      ut t on ca  ncel =  n e w                  JBut  t    on("Sto          rno   ");
             cancel.addActionLis  tener   ( new    ActionListener() {
                    @Over  ride
                     pub    lic void actionPer   fo    rmed    (ActionEvent e) {
                            actionCancel();
              }
                   }  );

        J     B       utton save = new JButt      on ("Ul   oÅ¾it   zmÄny"     );
          save.addActionListener( ne          w A   ctionL           ist      ene r       () {
                @Overr       ide
                  pu blic void      actionPer    fo  rmed(Action  Ev  ent    e)    {
                   saveChanges(           )      ;  
                close();
            }
                 });

        Box bu ttons = Box.creat  eHo  rizontalBox(  );
               butto       ns.add(  Bo  x.createRigidA     rea( ne      w Di mensi  on(10,          0)) );
           buttons.a   dd(create);
                 but          tons.ad     d( Box.createRigidArea( new      Dimensi  on(10,   0)) );
           butt  ons.add(dele te);
                 buttons.add( Box.cre   ateRigid  Ar       ea(         new D      im                e      nsion( WINDOW_WI    DTH    -      340, 0)  ) );    
                 but    tons    .add(cancel);
        b     utt       ons.ad      d( Box.c    reateRig         idArea( new Dimensi  o  n  (1     0, 0)) );
          butto               ns.add(sa  ve);

        JPa   nel bu    tt      onPanel = ne    w JPanel();
               buttonPanel.setL  ayout(   new BoxLayo       u      t  (bu   ttonPan                 el, BoxLay    out.X  _AXIS) );
        buttonPanel.a     dd(           buttons);
   
          re   turn    buttonP      anel;
    }

    pr               ivat  e          voi     d re   loadComm andsTa  ble() {
            whil     e ( ta     ble  M odel.  getRo       wCou  n    t()  > 0 )
                         tabl   eModel     .re  mo  veRow(0);

        co  mmands = se                 ttings.      getCommands();
              for (C     om     m              and c : commands) {
                                       S       tring[] row = {c.name,             c.co  ntent    };
              tableMod el.ad       dRow(row);
        }
        }

    privat       e vo  id actionAd    dC  ommand    () {
        String name = "";
               S       tring con tent = "";
         commands.add( new C     omman   d(name  , conte   nt) );
             String row[]   = {nam   e, content};     
                table Model.    ad      dRo w(row);
        table.editCellAt(table.ge tRowCou nt    () - 1, 0);
        table.     requestFocus()      ;
    }

    voi  d act io    nDeleteCurr    en  t()       {
            in           t  row = table  .getSelectedRow(  );
            if (row      > -1) {
                       comman ds.remove(  row      );
                            tableMo del.       removeRow(r ow);
         }
        delet  e.setEnabled(false);    
       }

              @Overr   ide
     publ         ic v  oid tableChanged(TableMode               lEvent e)   {    
        if ( e.getType            (         ) == T      able       Mod      elEvent .UPDATE )   {
                   int row  = e.getFirstRow();
               String ne   wNam  e =     (String) tableModel  .get    Val ueA     t(row, 0);
            Strin   g newConten   t = (Stri      ng) tableMo   del.getVa  l      ueAt(row, 1);
            Co  mman  d comman  d = c   omman  ds.get(row);
                          comman        d. name      = newName;
               command.content     = newCon tent     ;
              }
    }

    private         void saveChanges() {
               cleanUpTable();
           settings.setCommands(c  ommand   s)    ;
            settings.store();
         }
      
    private void cleanUp         Ta  ble() {
        for (int i = 0; i < commands.size(); ++ i) {
            co mmands   .get(i).name = commands.ge      t(i).nam  e.toLowerCase();
            if ( commands.get(i).name.isEmp  ty () ) {
                 commands.re          move(i);
                     ta    bleMode    l.removeRow(i);
                --i;
            }
          }
    }

    private void actionCancel() {
              reloadCommandsTable();
        close();
    }

           private voi     d close() {
        de     l ete.s     etEnabled(false);
        set  Visible(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        close();
    }

    @Override
    public void windowOpened(WindowEvent e) {
            setLocationRelativeTo( MainWindow.getInstance() );
    }

    public void windowClosed(WindowEven  t e) { }
    public vo   id windowIconified(WindowEvent e) { }
     public void windowDeiconified(WindowE vent e) { }
    public void windowActivated(WindowEvent e) { }
    publ   ic void windowDeactivated(WindowEvent e) { }

    private class TableSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if ( !e.getValueIsAdjusting() )
                delete.setEnabled(true);
        }

    }

}
