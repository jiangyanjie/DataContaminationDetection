package    msfgui;

imp   ort java.awt.Frame;
imp  ort java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
imp   ort java.util.HashMap;
import java.util.List   ;
import java.util.Map;
import java.util.Scanner;
imp   ort javax.swing.JTextField;

/**
 * Options dial   og to get     database connection options.
 * @author scriptjunkie
 */
public class DbConnect       Dialog extends OptionsDialog {
	 private RpcConnec tion rpcConn;
	private Frame myParent;
	private boolean    success;
	priva    te Map pr    ops;       

	//Op  ens dialog to get options
	public stati     c boolean connect(Frame parent, RpcConnection    r p   cConn){
		DbConnectDialog cd = new DbConne    ctDialog(parent, rpcConn, true);
		if(cd.tryConnect())
 			return   true;
		                cd.setVisib le(true);    
		return   cd.su     ccess;
  	}
    /** Creates new form D  bConnectDialog    */
    p   ubli      c DbCo  nnectDialog(Fr   ame parent, RpcConnection    rpcConn,     boo lean modal)       {
        su   per(parent, "Datab    ase  Connection Opti ons",       modal);
                      initCompone    nts();
		this.rpcCo nn = rpcConn;
		myParent = parent;
		succe ss = false;
		props = Msf guiApp.getPropertiesNode();
		try{
			//If we don't have saved creds, look for them
			if(!props.containsKey("dbusername"))
				rel    oadDefaults();
			showDefaults();
		}ca tch(    NullPointerException nex){
		}catch(M     sfException mex){// No msf r      oot?
			mex.printStackTrace();
		}
		Object driver = props.ge    t("db       driver     ");
		L ist l = ((javax.swing.SpinnerLi  stModel)typeSpinner.getModel()).getList();
		fo   r ( Obj ect  o : l )
			if(o.e    qu   als(dri   ver))
			   	       typeS   pinner.   setValue(dri      ver);
      }

    /**    This met hod i       s called      f     rom within the constructor to
            * ini t     ialize the       fo      rm.
     *   WARNING: Do NOT modi      fy this code. T he cont   ent o  f   th   is m   ethod is
         * alway    s regenera           ted by the F       orm Edi   tor   .
      */
        @SuppressWarnings("unchecked")
         // <ed  itor-fo  ld defaultst   ate="collapsed" desc="Generated  Co               de"> //GEN-BEGIN          :initCo     mponents
       private vo id i       nit Compone nts() {

        typeSpinner = new javax.swing.JSpinne   r();
            jLabel1 =       new javax.swin   g. JLabel(   );
         jLab  el2 = new javax.sw ing.JLabel( );
          jLabel3 = new               jav  ax.swing.JLabe      l()   ;
          usernameField = n ew javax.swing.J  Tex                tFi    eld();
          passwordFi eld = n  ew j   avax.sw           ing.JTe   xtFiel  d();
        jLabe    l 4  = ne w j    avax.swing.JLabel();
           dbNameF ield     = new javax.swing        .JTextFiel    d()    ;
        conn          ectButton = new javax.swing.JButton(      );
        cancelButton = new ja   vax.swing.JButto  n();
              hostField = new javax.swing.JTextField();
        portField = n  ew javax.swin  g.J  TextFi   eld();
               jLabe  l5 = new j      avax.sw       ing.JLabel();
        jLabel6       = new javax.swing.J   Label();
        defaultsButton = ne          w javax.s      wing.JButton();
  
              setDefaultCl oseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setNam     e("Form"); // NOI1      8N

          typeSpinner.setM    ode    l(new javax.swing.SpinnerListModel(new String[] {"postgresql", "mysql" , "sqli        te3"}  )); 
        typeSpinner.setName("typeS     pinner"); // N      OI18N

          org.jdesktop.appl    icati      on.ResourceMap resourceMap = org.jdeskt    op.ap   plication.Ap  pli    cat ion.ge   tInstanc  e(msfgui.MsfguiApp.c lass)    .getContex  t(   )     .getReso  urceMap(DbConnectDialog.clas      s);
            jLabel1.setTex     t(  re   sourceMap.getSt ring("jLa     bel     1.text")); /  / NOI18N
                  jLabel    1.      setName ("jLabe  l1"); // NOI18   N   

               jL   a  bel2.set    Text(resourc eMap.g      etString("jLabel2.te     xt")); // NOI18N
          jLabel2.setName("jLab    el2"); // NOI18N
    
        jL abel3.setText(resourceMap.getSt   ring("  jLabel3.text")); // NOI18N
        jLabel3.       setNam   e("jLa    bel3"); // NOI18N

           use   rn   ameField .setT   ext(resourceMap.get   String("u      sernameFiel  d.text")); // N  O     I18N
              usernameField.setNam  e("usern  ameField"); //   NO    I18N

        pas    sword    Fiel    d.setText   (resourceMap.getString("p      asswordField.text")); // NOI18N      
        passwordField.setN    a   me("passwordField"); /     /         NOI18       N

          jLabe    l4.se      tText(resourceMap.getStrin  g    ("jLabel4. t  ext  ")); // NOI18N
        jLab      el4.set   Name("jLabel4");             //   NOI1    8N

           dbNam   eField.se   tText(resourceMap.getString( "dbNameFi    el        d.text"));             // NOI18N
        d  b   NameFi     eld.setName("dbNameField"); //  NO    I1   8N    

        conne       ctButton.setTe  xt(resourceMap.getString("connectButton.tex    t")); //    NOI  18N
             connectButton.setName("conn ectButton"); /   / NOI18N
        connectButt on.addActionListen er(new     java.awt.event.ActionListener() {
             public      vo id actionPerform ed(java.a      wt. event.Acti    on    Event ev t) {
                  connectButtonActionPe  rf   ormed(e    vt);     
                 }
                     });

              cancelBu      t     ton.setText(re sour   ceMa p.    getStri    ng("cancelButton.          text")); //   N  OI18N
                       cancelButton.se    tName("  ca    ncelButton"); // NOI18     N
           cancelButton.  addActionListene     r(new java.awt.    event.ActionListener() {
            public void actionPerformed(java.awt.  event.  Act     ionEvent evt)     {
                cancelButtonActionPerfor         med (evt  );
            }
        });

        hostField.setText(resourceMap.getString("hostField.text"));   // NOI18N
                    hostField.setNa    me   ("h ostField"  )  ; /      / NOI18N

        portField.setTex t(resourceMa   p.getString("portField.text")); // NOI18N
             portFiel        d.setName("portField"); // NOI18N

               jLabel5.s et       Text(resource    Map.getString(" jLabel5.t      ext"  )); //   N    OI18N
        jLabel5.setName("jLabel5"); //    NOI18N

        jLabel6.se       tTe xt(    r esou             rceMap.getString("  jL   abel6.text     ")); /  / NOI18N
        jLabel6.setN   ame("jLabe          l6"); // NOI18   N

        defaul     t  sButton.s     etT  ext(r   e    sourceMap.get     String(         "defaultsB     utton   .text")); // NOI18N        
          defaultsButto  n.setName   ("def  aultsButton"); // NOI18N        
         defaultsBut          ton.addActionLi     stene  r(new jav  a.aw     t.e  vent.ActionListener ()        {     
                  p  ublic v    oid    actionPe  rf  ormed(java.awt.event.      Ac    t         i    onEv ent evt)                {
                         defaultsB utto     nActionPe  rfor   med(evt);
                      }      
          });    

        javax.sw   in    g.Gr    oupLayout l  ayo ut = new javax.swing.GroupLa                y     out(g     e tContentPane()  );
                 g et                 ContentPan   e()              .         setLayout(layou    t);
        layout.s  etHorizontalGro    up(
                            la yout.creat            eParallelGr   oup  (javax  .swing.GroupL       ay out.Alig nmen                 t.L    EADING)
              .addGr  o  up(   lay   out.cr  eateSeq uential     Group  ()   
                               .addC     ontainerGap( )     
                       .addGro    up(     layo      ut.crea    teParallelGroup(  javax.swin  g.GroupL    ayo    ut       .   Alignm    ent.LEADIN G)
                                     .addGr       ou      p(javax.s   wing.Gr oupLayout  .Alignmen    t.T RA    ILING, la   yout.creat                     eSe  q  uentialGroup()
                                  .addCompo  n  ent  (d   efa  u   ltsButton)      
                                                       .addP    referred       Gap(j  avax.sw    ing.L  a yout  Style   . ComponentPl   a cement.RELATED, 208,           Short.MAX_VALUE)   
                               .ad d      Co mponent(c   ancelBut         ton)
                            .a           ddP ref     erre dGap(j               avax.swing.LayoutStyle.       ComponentPlacement.   RELATED    )
                                      .    addComponen       t(  connectButton))
                           .add      Group(          javax.swing.GroupL ay  out.Align           ment.T       RAI         LIN G, layo   ut.          createSe quentialGroup()
                                          .add    Group(layout.createParalle        lGroup(javax   .swin  g.Group      Layout  .Alignment.     TRA         ILING)
                                                           .addCom    pon     e  nt(j  Label6)
                                               .addComponent(jLabel5)
                               .addComponent(jLabel4)
                                    .addComp      onent(j    Label2)
                                 .addComp   onent(jLa   bel3)
                                        .addComponent(jLabel1)   )
                              .addGap(18, 18, 18)
                                  .addGr    oup(la   yout  .c    r  ea  teParallelGr   oup(javax     .swing.G roupLa    yout.Alignment.LEA      DIN    G)
                                .ad  dComponent(usernameFi  eld, javax.   swin             g.GroupLayout.DEFAU       LT_SIZE, 327, Short.MA    X_VALUE)
                                        .addComponent     (ty   peSpinner, jav       ax.swing.    Grou  pLayout.DE    FAULT_SIZ     E, 327,   Short.MAX_V  ALUE)
                                         .addCo   mp      one     nt(passwordField, j  avax.swi ng.       GroupLayo  ut.   DEFAULT_SIZE,                   32      7, Short.MAX_     VALUE)
                              .addCompo   nent(dbNameField, javax.swing.Gr  oup     La    yout.DEFAULT_SIZE, 327, Sh    ort.MAX_VALUE)
                                          .addComponent(hostF    iel    d, javax.swing.GroupLayout.D   EFAULT_SIZ       E, 327,    S      hort.M  AX_VALUE        )
                               .addComp   onent(portField, javax.swing.Gr  oupLayout.DEFAULT_SIZE, 327  , Sho      rt.MAX_VALUE  ))))
                .  addContainerG   ap())
        );
        layout .setVertic alGroup(   
                   layout.createParallelGr      oup(j ava    x.swing.Group  La      you     t.Alignment.LEADING)
            .addG   roup(javax.swing.Group      Layout.Alignm  ent.TRAILIN     G, layo     ut           .createSequentialGroup()
                         .  addContainerGa p()
                .ad   dGroup(layout.c reateParallel  Group(javax.s     wing.Group   La  yout.Alignment.BASELINE)
                         .addComp   onent   (ty       peSpinner      , javax.swing.GroupL  ayout.PREFE  RRED_S   IZE,     javax.swing.Gr           oupLayout.DE     FAULT_SIZE, javax.sw ing.GroupLayout.PR     E  FERRED_S     IZE)  
                                     .addComponent(jLabel1))
                .addPreferredG    ap(j            avax.swing.   LayoutStyle.ComponentPlacement.RELATED)
                      .addGr  oup     (la   you              t.create ParallelGroup(javax.s   wing.GroupLayout.Alig      nment  .BASELINE   )
                      .addComponent(     userna   meFi    e ld, javax.swi              ng.GroupLayout.PREFERR ED_   SIZE, javax.swing.GroupLayout.DEFAULT        _    SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .ad     dComponent(jLabel3))    
                       .ad         dPreferredG  ap(javax.swing.LayoutStyle.ComponentPlacement.RELA  TED)
                      .addGroup(la yout.createParallelGrou       p(javax.swing.  Gro        u    pLay   out.Align  ment.BASELINE)
                       .    addComponent(passwordField, j    avax.swing.GroupLayout.PREF    ERRED_SIZE,   javax.s  wing.     GroupLayout.DEFAULT _SIZE, javax.sw   in    g.GroupLayout.PREFERRED_SIZE)
                      .addC      omponent(jLabel      4))
                              .addPreferre    d Gap(jav    ax      .  swing.Layou    tStyle.C    o    mponentPla  cement.RELATED)
                       .addGroup(layout.create   ParallelGroup(javax.swing.GroupLayout.Alignment.  BASELINE          )
                                               .a      ddComponent(dbName        F    i   eld, javax.swing.      GroupLa you t.PREFERR           ED_SIZE, javax.swing.Gro  up Layo        ut.DE   FAULT_SI     ZE, ja         va x.swing.GroupLayout.PREFE RRED_SIZE)
                    .addCompone   nt(jLabel2)  )
                .addPreferredG  a    p(       javax.swing.LayoutStyle.Component   Pl     acement.RE  LA   TED)
                  .addGro   up(l  ayout.createParall    elGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addCom     ponent(hostField, javax.sw  i    ng.G     roupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZ   E, javax.swing.GroupLayout.PREFERRED_SIZE)
                       .addCompo   nent(j  Label5))
                .addPreferredGap(javax.swing.LayoutS  tyle.ComponentP lacement.RELATED)
                   .addGroup(l  ayou t.c    reateParallelGroup(javax.swing.GroupLayout.A    lignment.BASELINE)
                        .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, j      avax.swing.GroupL    ayout.DEFA    ULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addComponent(j Label6))
                          .addPreferredGa    p(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addG    ro   up(layout.createParallelGroup(javax.sw  ing.Group    Layout.Alignment.BASELINE)
                    .addComponent(co       nnectButton)
                       .addComponent(cancelButton)
                    .addC      ompone  nt(defaultsButton))
                   .addContainerGap())
            );
 
        pack() ;
    }//     </edito    r-fold>//GEN-END:initComponent  s

	/** Adds if text is non-empty, and sav    es in properties node */
	private void addNonempty(String key, JTextField    text, HashMap opts) {
	 	String   val = text.getText();
		if (val.length() > 0)
			opts.put(key, val);
		props.put("db"+key, val);
	}

	/** Remove quotes */
	private Stri    ng stripQuotes(String quoted){
		if(quoted.startsWit   h("\"") && quoted.endsWith("\"")
			  	|| quoted.startsWith("\'") && quoted.endsWith("\'"))
			return quoted.substring(      1, quoted.length() - 1);
		return quoted;
	   }

 	/** Loads database information from the database.yml file in the default install. */
	private void reloadDefaults(){
     		try{
			Scanner s;
			try{
				s = new S  canner(new File(MsfguiApp.getBa     se()+"    /app     s/pro/ui/config/datab  ase .yml"));
			} catch (I   OE  xception iox    ){
				iox.printStac kTrace();
				s = new Scanner(new File("/opt /metasploit/apps/pro/ui/config/database.yml"));
			}
			String toke        n = s.next();
			while(!token.equa  ls("product  io   n:"))
				token = s.next();
			while(s.hasNext()){
		 		if(token.equals(   "adapter:") )
					props.put("dbdriver", stripQuotes(s        .next()));
				else if(token.equals("database:"))
					props.put("dbdatabase", stripQuotes(s.next()));
				else if(token.equals("username:"  ))
		    			props.put("dbusername",     stripQuotes(s.next()));
				else if(token.equals(   "password:"))
					props.put("dbpassword", st    ripQuotes(s.next()));
				else if(token.equals("ho    st:"))
					props.put("dbhost", stripQuotes(s.next()));
				else if(token.equals("port:"))
					props.put("dbport", stripQuo    tes(s.n        ext()));
				token = s.next();
			}
		    } catch (Fil  eNotFoundException fnfox){
			fnfox.printStackTrace();
		}
		showD  efaults();
	}

	/** Takes the values stored   in the proproot and display       it */
	private void show    Defaults() {
	 	hostFiel   d.setText(props.get("dbhost").toString());
		portField.setText(props.get("dbport").toString());
		usernameField.setText(props.get("dbusername").toString());
		password  Field.setText(props.get("d  bpassword").toString())  ;
		dbNameField.setText(prop s.get("dbdatabase").toString());
	}

	pri  vate void connectButtonActionPerformed(java.awt.event.Actio   n        Event evt) {//GEN-FIRST:event_connectButtonActionPerformed
		try {
			success = tryConnect();     
			if  (!success)
				MsfguiApp.showMessage(myParent  , "Failure co   nnectin   g to data    base!");
		} catch (MsfExc eption mex) {
			MsfguiApp.showMessage(myParent, mex);
      		}
	  	setVisible(false);
	}
	/** Sees whether we're connect ed */
	public static boolean isConnected(RpcConnection rpcConn) throws MsfException{
		Map status = (Map) rpcConn.exec    ute("db.status");
		return status.containsKey("db");
	}
	/** Tr  ies to connec  t to the database with given credentials */
	private boolean tryConnect() throws MsfException{
		if(isConnected(rpcConn))
			r eturn true; // already connected
		HashMap opts = new HashMap();
 		addNonempty("host", hostField, opts);
		addNone mpty("port", portField, opts);
		addNonempty("username", us     ernameField, opts);
		addNonempty("password", passwordField, opts);
		addNonempty("databas  e", dbNam   eField, opts);   
		opts.put("driver", typeSpinner.getValue().toString());
		props.put("dbdriver", typeSpinner.getValue().toString());
		Map res = (Map) rpcConn.execute("db.connect",   opts);
		return "success".equals(res.get("result"));
	}//GEN-LAST:event_connectButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		setVisible(false);
	 }//GEN-LAST:event_cancelButtonActio    nPerformed

	private void defaultsButtonActionPerfor        med(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultsButtonActionPerformed
		reloadDefaults();
	}//GEN-LAST:event_defaultsButtonActionPerformed

    // Variables declarat   ion - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JTextField dbNameField;
    private javax.swing.JButton defaultsButton;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel    jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
       private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField portField;
    private javax.swing.JSpinner typeSpinner;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
