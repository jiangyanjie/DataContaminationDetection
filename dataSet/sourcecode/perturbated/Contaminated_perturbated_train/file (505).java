/*
 *     To change this template, choose      Tools | Templates
 * and      open   the    template in the edi tor.
 */
package br.com.seeds;

import java.awt.event.ActionEvent;
im    port java.awt.event.ActionListen   er;
import java.awt.event.MouseAdapter;
import java.awt.ev    ent.Mouse Event;
import java.io.*;
import jav  a.util.logging.Level;
import java.util.logging.Logger;
impor    t javax.swing.*;

/**
 *
 *     @author Guilherme
 */
public class ConfiguracoesServido     r extends javax.swing  .JDi    alog {

    int    finalizar    =   0;

              /**
     * Creates new form Configuracoe        sServidor
       */ 
    public C   onfiguracoesSer vi   do   r(java.awt.F          ram    e p              are  nt, bo olean modal) {
        super(p arent,    modal );
           initCompo           nent    s();
             setLoca  t i onRelativeTo(null);
                             t        x   tPorta.setDo cumen  t(n   ew OnlyNumberFie      ld(11));

          //Para Fechar       Sist   ema       M    en u PopUp
           jPane l1 .addMou        seL     istener(new      Mo useAdap     ter()        {

                public                  void                  mouseClick     ed(MouseEvent me)       { 
                     //  Ve   ri      ficando se                  o   b    otÃ£o d      ir    eito      do   mouse foi cli cado     
                                      i    f (   (me.getMod    ifiers() & Mo         useEvent.     B  U     TTON3_MASK) != 0) {
                                               JP    opupMenu   menus     = new JP    opupMenu()      ;     
                             JMenuIt  em i         tem = new JMe  nuItem("Clique-me par           a ma tar      o     pro     c       esso no    w indows      do java   ");
                                            me         n  u    s   .a   dd(                item);
 
                                       item   .addAct    ionLi                  s  te n       er(new A  ctionLis   tener()     {

                                            publi c         void act        ionPerf   ormed(              ActionEvent            ae) {
                                                                            /      /J   O                       ptionPane                 .showMessa  geDia  lo    g(null, "Fui     c      licado                  !");
                                                kill(   "j  ava w  .ex             e"    );        
                                                             /   /Sy  stem.exit(0);
                                   }
                                                           });
                                       men  us.     sh   ow   (j       Panel1,        me   .ge tX  (), me.g        etY  ())   ;
                                  }
                           }
                        });
    }

               public s   tatic       boole     an kill(String processo) {
        try {
              S    tri     ng line;
                       Proc       es        s  p = Runtime.getRuntime().ex   ec("     tasklist           .  exe /   fo csv /nh"     );
                        Buffered   Reade      r     input = new BufferedR   eader(new In  put      Str ea   mRead              e r(p.getInp  utStream(    )));
            whi le ((line = input.readLine   ()   ) !=       nul  l) {
                   if (!  line.       trim(    ).e              q    uals(""))    {
                       i    f (lin    e.  s    ubstring(       1, line. indexOf("\""  , 1)     ).equalsIg  noreCase(processo)) {      
                            Runtime      .getRuntime().exec("taskk    ill /F /IM " +       line.s    ub    string(1, l   ine.i        n  dexOf   ("\"     ", 1)));
                                      return true;
                                }
                                                                     }
                       }
                 input.close()        ;
               }        catch (Excepti on    err)                  {
                       e   rr.printS  tackTra  ce();
         }
          return fa    lse   ;
    }

    pro    tected JRootPane createRootPa  ne()   {
               JRootPane rootPane  = n   e     w JRo        otPane();
            KeyStroke stroke = KeyStrok    e      .getKeyStroke(" ESCAPE")   ;   
           Action action   Listener = ne  w       AbstractActi   on() {

                                 pu  blic void actio   nPerformed(A   ctionE  ven t actionEvent) {
                    Con      figuracoe   sS    e       rvidor    .t   his .dispose();
                }
        }   ;
           I     nput   Map   inpu    tMap = rootPane   .getInputMap     (    JComp       o     nent.WHEN_IN_FOCUSED_WINDO    W);
          inpu   tMap.p ut  (st   roke,    "ES     CAP    E");
            r  ootP a             ne.getAction Map().put("ESCA            PE       ", actionListe         ner    )   ;
        retu    r   n rootPa  ne;
               }

    public v   oid Cone    c tar() throws IOEx ce     ptio      n {

           if (txtN  omedaBased eDados.getTex   t      ().equals  ("")) {
            C  onfigur  ac oesServidor.        this      .d    i   spose();
           } els   e {
            Str ing nomeDa BaseDeDados;
                   no     meDaBaseDeDados =    txtNome        daBasedeDa   dos.getText( ).trim();
            F  ileW  riter a  rq =  new FileW  riter("          ../seeds     -java/based    edados .txt"); 
                                        Prin       tWriter     gravarArq = new Pr            intWriter(arq)    ;
                      gr           avarA    rq.pri   ntf(nom     eDaB    aseDeDa dos    );
                    ar    q.close();
                                 JOptionPan     e.sho wMessageD    ial og(roo tPane,
                                 "Sucesso!  ",
                           "ConexÃ     £o configurada",
                        JOptionPane.INFORMATION_MESSA        GE);
                 if (f i     naliza        r == 1) {
                    System           .exit(     0);
               }
            Confi gurac  oesServido r.this.       dispose();
            }
    }

    public void Conectar3() throws I     OException {

                        if     (txtPor    ta.get T   ext().equals("")) {
                  Co  nfigu    racoesServidor.this.d  ispose();
                } el      s   e {
                    String nomeDaP         orta   ;
                no    me   DaPor t   a  =    txtPorta.getText().trim();
            FileWr iter arq = new FileWriter("   ../seeds-java/p   orta.txt");  
                  PrintWriter gravarArq =       n   ew PrintWri    ter(arq);
                 grav   arArq.printf(nomeDaPorta);
                  arq.close();
            }    

     }

    pub   l   ic voi  d      Conectar2() throws IOException   {    

              if (txtEnderecoIPv4.getText().e     quals(""))      {
                           Co       nfiguracoesSe  rvidor.th    is.d   ispose();
                   } else {
               Stri        ng nomeDo  E       n    derecoIPv4;
                           nomeDoEnderecoIPv4 = txtEnderecoIPv4.   ge  tText().trim();
                         FileWriter arq =    new FileW   riter   ("../se  ed    s-  ja  va/ip      .txt");
                   Prin   tWriter gravar    Arq = ne    w PrintWriter(ar    q);
            grav   arArq.printf(nome     DoEnderecoIPv      4)     ;
                a     rq.close();
                  }

    }

    /**
     *     T hi     s method        is     calle    d from withi         n the cons   tructor t          o initialize the form.
       * WARNING:    Do NOT     modify t   his c  o    d e   . T   he content    of   this m  ethod i   s        always
       * regen   e   r   ated by the Form Editor.
     */
    @S   uppressWarnings("unchecked"   )
         // <editor  -fold defaultstate="c ollaps ed" desc="Generate  d Code">//GEN-       BEGI     N:ini   tCompone   nt s
     private void initComponents() {      

        jPanel1 =   new ja   vax.swing.JPane         l();
        j     Button 1       =  new j    avax.swing.JButton();       
        jPanel2 = new javax.swing.JPan  el();
        txt Nomed   a   BasedeD   a dos = new  java x  .s  wing     .JTextField();              
                jL  abel1 = new     j   av  ax.swing.JLab     el ();
        jLabe     l2    = new j    avax.s  wing.JLabel();   
        txtE    nderecoIPv4 = ne    w javax.swing.JTe   xtFie   ld();
             jLabel3 =    n    ew javax.swing.JLabel(    );
        txtPorta = new javax.swing       .JForm    attedTextField(    );
        jButton3 = n        ew   j    a    vax.swing.JButton();    
        jButton4 = new javax. s      wing.J        Button  (       );
         j    Button2 =   new javax.    swing.JButton        ();

        setDefaultCloseOperati   on(javax.swi          ng.Wi       ndowConstants.DI     SPOSE_O N  _CLOSE);
        setTitle("ConfiguraÃ§Ãµ           es   de conexÃ£o.         ");

            j Pane      l1.setBac  kground(new       java.awt.C     olor(25         5, 255, 255));

        jButton1.setFo  n    t(new java.awt.Font("Ta   homa    ", 1, 1         2)); //       NO I18N
        jButton1.setI        con(new javax.swing .Im   ageIcon(ge   tClass(). getResource(   "/br/com/image   ns/view-refresh.png")));   /              / NOI18N
        jButton1.se                  tText("A tua l   izar")        ;
                  jButton1.     addActionListener (new java.a      wt.ev      ent.A  ctio   nListen          er(  ) {
                           public voi           d actionPerformed(java.awt.event.A        ctio  nEvent ev  t) {
                        jButton     1Action  Perf      ormed(evt   )  ;
                              }
                  });

             jPanel2.setBorder(javax        .   swin    g.Border         Factory.createLineBorder(new java.awt.Color (204, 204, 204)));

           txtNomedaBase    de  Dad  os.set    Font (n  ew java.awt.Font("Ta       h om         a", 0, 14        )); //               NOI1  8N
            txtNomed   aBasedeDados. addFocu      sL   i   stener(new j   ava .awt.event.   Fo    cu    sAdapte   r() {
                       pu blic v     oid focu              sLost    (     java.awt  .event.FocusEvent evt) {
                    txt     NomedaBasedeD  adosFocusLost(ev  t);
                             }
        }) ;   
           tx tNo   medaBasedeD ados.addKeyListe      ne     r(new jav   a.awt     .event.K  eyAdapter() {   
            public   v   o     i   d keyTyped(ja va  .awt.even   t.K    e  yEvent evt) {
                   tx  tNom eda  Ba  sedeDadosKeyType   d(evt  );
               }
           });

        jLabel1.s    etText("Nome d    a ba    se de d         ados   : ")      ;

        jLabel2.set  Text("En  dereÃ§o IPv4:");

        txtEnde   recoIPv4.s  etFont(new java.awt.Font("  T ahoma", 0, 14)     ); // NOI18N

           jLabel3.setText(    "P    orta:");

          tx    tPor ta.setFont(new java.a  wt.Font("Tahoma",       0,   14)); //           NOI18N

        javax.swing.  Grou pLay          out j    Panel   2     Layout =    new javax.swing.GroupLayout(jPanel2);
        jPa  nel2.setLayout(       j    Panel2Layou   t   );
                jPanel2Layout.s etHorizontalGroup(
                  jPanel      2La        yout.createPa  rallelGroup(javax.sw          ing.GroupLayout.Alignment.LEADI  NG)
            .   addGroup(jP  anel2Lay     out.createSeque         nt        i    alGroup()
                     .addContainerGap()
                .addG  roup(j Panel2Layou           t.cre    ateParallelGroup(javax.swing.      G     roupLayout.Al    ign     ment.LEADI      NG, fals   e)   
                                .   addC       omponent(jLa     bel1, javax.swing.Grou           pLayout   .DEFAU     LT_SIZ   E, 142, Short.  MAX_VALUE)
                            .addCompone   nt(j   Label2, javax.swing.        GroupLayout.DEFAULT_SIZE, javax.swing.G roupLayo   ut.D EFAULT_SIZE, Short.MAX_VAL    UE)
                    .addComponent(jL     abel3, javax.s wing.Gro    upLayout                     .DEFAULT_SIZE, ja     vax.swing.GroupLayout.DEFAULT_SIZE, Shor     t.MAX_VALUE))
                         .a d  dP   ref       erredGap(j       avax.swing.L   ayoutStyle.ComponentPlaceme     nt.RELATE  D)
                .addGroup(jPanel2Layout.createParallelGroup(javax.sw       i     ng.Gro   upLayout.Alignment.LEADI  NG)
                               .addGrou       p(jPanel2Layout     .     cre  a  teSe qu entialGroup   ()
                             .addGap(0, 1, Short  .M      AX_VALUE         )
                                 .ad  dComponent(txtNomedaBasedeDados, jav        ax.swing.GroupL   ayo   ut.PREFER     RED_   SIZE, 292      , ja v ax.swi   ng.GroupLayout.PREFE  RRED_SIZE    ))
                                  .   addCompo   nent(txtEnder     eco          IPv4)
                     .addComponent(t        xtPorta))
                         .addCont       ainerGap())
        );
            jPa    nel2Layout.setVerticalGroup(
            jPanel2Layout.crea  tePa      rallel   Group(javax.    s wing.Gr   oup  Layout     .Alignment.LEADING)
                        .addGroup(jPa   n       el2La  yo    ut.createS   equentialGroup    ()
                      .addCon   tainerG    ap()
                   .addGroup  (jP     anel2La yout.createParallelGroup  (  ja     vax.swing.GroupLayout.Ali   gn   men   t.BASEL INE       )
                             .addComp on ent(txtNomedaBase      deDados, ja  vax.swing.G    r     oupL   a   yout.PREFERRED_SIZE, javax.swi    n    g.GroupLayout.DEFAUL T_SIZE, java                       x.swi ng.GroupLayout .PRE FERRED_SI   ZE)  
                       .addCom   ponent(jL    abel1))
                        .addGap(18, 18,     18)
                      .ad   dGroup(jPanel2Layout.cr    eateP   a       ra    l  lelGroup(javax.swing.Gr ou  pLayout.Alignment.BAS     ELINE)
                       .addComponent(jLabel2)
                      .addComponent(txtEnd  erecoIPv4,      ja   vax.swing.GroupLayout.PREFERR           ED_SIZE, javax.swing.Gr   oupLayout.DE  FAULT_S    IZE, j  ava   x.swing.   GroupL  a         y  out.PREFERRED_SIZE))
                        .addGap(18, 18    , 18       )
                       .add Group(jP    an  e     l2Lay      out.c  reatePara    llelGroup(javax.swing.Gro   up   Lay  out.Al   ignment.BASELINE)
                       .addComponent(j   Label3)      
                                  .addCo  mpo      nent(txtPorta, javax.swing.Grou        p      Lay     out.PREFER  RED_SIZ E, javax.swing.Grou   pLayout.D  E              FA        ULT_SIZE, javax.swing.GroupLayo   ut.PR   EFERRED_S    IZE))
                     .ad    dContaine     rGap(javax.    swi   ng.GroupLayout.DEFAULT_  SIZE,    Short.MAX_VALUE))
                );         
   
                     jButton3             .setFont(new ja   va.awt.Font("Tahom    a       ", 1, 12) ); // N     O   I18N
          jButton3.setI  con(new javax.swing.Ima    geI  con(ge   tClas   s().getResource("/   br    /com/i      mag              ens/cancela   r.png"))); // NOI18N
         jButton3            .setText     ("Sa   ir")    ;
        jButt   on3.a  ddA  ction   Listener(ne   w java.awt.event.ActionLis    tener() {
                   public void      a     ctio   nP   er      formed(j  ava.awt.event.ActionE   vent    evt)        {
                              jButton3Ac      tionPe      rformed(evt);
            }
             });

          jButton 4.setFont(new java             .awt.Font("Tahoma", 1, 12   )); /    / NOI18N
                          jButt   on4.setIcon(new java       x.swing.ImageIcon(getCl  as   s   ().getResou r    ce("/     br/com/   imagens/ent   rar.png")   )); // NOI18N
        jButton4.setText("Concluir e fechar o s   istema.   ");
          jB  utt on4.addActionListen    er(new java.awt.event.                     Act ion  Listener()     {
                         pub    lic void action  Per   form  ed(java.aw  t.e   vent.ActionEvent evt      )     {
                j   Butt  on4ActionPerforme  d(ev  t);
                 }
              });    

          jButton2.setFo   nt  (n   ew java.awt.Font("T    ahoma", 1, 12    )    )   ; // NOI1   8            N
             jButton2.setIcon(new javax.swing.ImageIcon(   getClass().getResource("/br/com/imagen  s/bu     sc      ar.png"))   ); // N OI    18N   
             jB  utto  n2.set    Text("Em     us o");
        jB       utt  on2.addActionListene  r(new ja  va.awt  .event.Acti onListener() {   
                       public vo    id actionPerformed(java.awt.event.Acti          onEvent evt) {
                                 jButton2ActionPe     rformed(evt);
            }
             });

        javax.   swing. GroupLayo                    ut jPa  nel1Layout =     new java  x.s        wing.G              roupLayout(jPan    el1)      ;
        jPanel1.setLayout(jPanel  1Layo   ut);
        j     Panel       1Layout.setHorizontalGroup(
            j   Panel1Lay        out             .createP     aral    le     lGroup(javax.        swing.G   roupLayout.Alignment.LEADIN     G)
            .ad  dGroup(jPanel1La    yout.creat eS  equentia   lG       r       oup()
                     .ad    d ContainerGap()            
                .addGr  oup(jPanel1La     you     t.createParallelGrou  p(javax .swing.Group    Layout.Alignm    ent.LEADING)   
                                .     addComponent(jPanel2, javax.s    win   g.GroupLayou  t.DEFAU LT_SIZE , javax.swi    ng.GroupLa you  t.     DEFAULT_SIZE, Shor   t.MAX_   VALUE)
                             . addGroup(ja     vax.swing   .Gr     oupLayou       t.Alignment.TRAILING, jPanel1Layout.cre    ateSequentialGroup()
                                       .add          Component(jButton1)    
                        .addPreferred Gap(javax.swing.La        yout Style.ComponentP      la  cem      ent.RE LA      TED)
                             .ad      dComponent(jButton3, java  x.swing.GroupLayout      .PREFERRED_SIZE, 109, javax.swing.Group  Layout.P        REFERRED_      SIZE)
                                                 .addPreferredGap(ja     vax.swing.LayoutStyle.ComponentPlacement.RELATED)
                           .addComponent(jBu   tto n2, javax.swi ng.GroupLayout.P    REFERRED_SIZE, 224, jav  ax.swing.    GroupLayout .PREFERR ED_SIZE)
                               .addGap(0, 0, Short.MAX_VALU    E))
                      .  addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, ja   vax.swing    .Gr   oupLay out.DEFAULT_SIZ   E        , Short.MAX_VAL   UE))
                       .ad          dContainerGap()) 
        )    ;
         jPanel1Layout.setVerticalGro      up(
                      jPanel1    Layout.creat   ePara   llel  Group(j     avax.   sw   ing.GroupLayout.Alignment.LEA DING)
                       .        ad  dGroup(java  x.swing.Gro    upLay     ou   t.Alignment  .TRAIL      ING, j  Panel1L     ayout.create SequentialGroup()
                  .addContaine       rG    ap()
                .     addCo    mpone    nt(jPan     e  l2, javax.swing.GroupLayout.PREFERRED_   SIZE        ,     javax.swing.Group Layout.DE    F    AULT_S   IZE, javax.s    w   ing.GroupLayout.PREFERRED_SIZ    E)
                      .addGap(38, 38, 38)
                         .addGroup(jPan       el1La yout.createParallelGro up(ja    vax.swing.GroupLayout.Alignment.BAS  ELINE)
                    .addComp   o  nent(jBu  tton1, javax.swing.GroupLayout. PREFERR          ED_SIZE, 47,     javax.swing.G  ro  upLayout.PREF   ERRED_SIZE)
                          .addCo   mponent(jButton3)
                        .addComp  one    nt(jBut           ton2, javax.swing.Gr oupLa    yout.PREFERRED_SIZE, 47, ja  vax.swing.GroupLayout.PR          EFERR           ED     _SIZE))
                 .   addGap(59,     59, 59)
                        .addComponent(jButton4, javax.swing.      GroupLayout.PR      EFERRED    _SIZE, 44, javax.swing.Grou  pLayout.PREF    ERRED_S    IZE)
                     .addGap(18, 18,     18)      )
         );

              jPanel1Layout. linkS  i   ze(javax.swi    ng.Sw    ing    Consta       nts.VERTICAL, new jav     a.awt.C  o   mponent[]        {jButton1,    jBut ton3, jButt       on4});

           javax.swi   ng.GroupLayout layo  ut = new javax.swing.Grou  pLayout(getC    ontentPa  ne());
        get    Conten tPane()  .setLayout(lay  out);
             layout.setHorizontal    Gr oup(
            layout.c       reateP      arallelGroup(ja  vax.swing.Grou    pLay    ou  t   .Ali            gnment.LEADING)
                       .addComponent(jPanel1, javax.s   wing.   GroupLayout.DEFAULT_SIZE, javax.swing.GroupL        a   yout    .DEFAULT_SI    ZE,      Short.MAX_V     ALUE)
           );  
        layout.setV      ertical        Grou   p(
                   lay  out              .cre  ateParallelGroup(javax.   swi  ng    .GroupLa  yout.Alignment.LEADING)
                  .    addCo   mpon  ent(jPanel1, javax.swing.G  roup  Layout.DEFAULT_SIZE, javax.swing   . GroupLayout.DE             FAULT_  SIZE, Short.MAX_VALUE)
           );

                      pack   ();
       }/     /    </editor-fo  ld>/    /G  EN-EN     D:initC omponents

    private voi d j  Butto      n1ActionPerformed(java   .awt.event.ActionEvent   evt) {//GEN    -FIRS     T:event_j    B   u    tton1 ActionPerformed
        if        (txtNom   edaBas  edeDado  s.get   Text(     ).contains("."              )) {
                      JOption     Pane.s   howMessageDial og( rootPa     ne,
                        "NÃ£o informar a extensÃ    £                      o do a  rqu   ivo.\n"
                              + "Informe apenas o nome da base de dados a ser utiliza      d      a.\n"
                          +                 "",
                                  " Nome   de arquivo invÃ¡lido", JOptio    nPan       e.W  ARNING_MES      SA     GE);

               txtNomedaBasedeDados.setText(txtNomed    a     Based     eD    ado        s.   g          etText().repla   ce(".sq        l", ""));
            txtNomedaBased   eDa   dos.setT  ext(tx        tNomedaBasedeDados.getText().replace(".SQ  L", ""));
                txt  NomedaBasedeDados.se tText        (txtNomedaBas          edeDados.     getT  ext(     )    .r         ep       lace(".sQL", ""));
                 txtNo medaBasedeDado     s     .set         Text(txtN   omedaB   ase     de           Dad  os.getText().rep  lace(".sQ   l"      ,    ""   ))  ;
                   txtNome       daBasedeDa  dos.set    Text(txtNom     edaBasedeDados.    getText().repla  c   e(".s         qL"       , "      ")) ;
            txtNomedaBasedeDa  dos.setText(txtNom     ed   aBa     sedeD ado        s.   getText(                ).replace(".SqL", "")   );
                    txtNom      edaBasedeDados  .setT   e  xt   (txt N   omedaBas  ed  eDados.getText(   ).   r  epla     ce(".Sql", ""));
            txtNomedaBase  deDados.setText(txtN     omedaBasedeDa dos.getT   ext(     ).replace(".SQl     ", "")       )  ;

                txtNo    me daBa sede    Dados.setText(txtNomeda   BasedeDado    s. getText().replace (   ".txt" , ""));
                         txtNo medaBa   sedeD  ados.setText(txtNomedaBase    deDados.get   Te   xt   ().replace(".     TXT", ""));
            txtN omedaBasedeDado   s.setText( txt    NomedaBasedeDados.getText().repla         ce(".Txt", "            ")   );
            txtNome  daBasedeDados   .setText(txtNomedaBasedeDados.ge      tT    ext()   .re place(   ".TXt", ""));
            txtNomedaBase      deD         ados.setText   (txtNomedaBa   sedeDados.getT  ext().repl         ace( ".t XT" ,      "            "));
            t  xtNom  edaBasedeDados.setText(txtNomedaBasedeDados   .getText   ().replace(".txT",    ""));
            txtNomed        a   Bas       edeDados.s      et Text(txtNome   daBasedeDados.getT          ext().replace (".tXt"  , ""));
               txtNomedaBase  de           Dados.      setText(txt N  ome  daBas   ed e   Dados.getText    ().replace(".     TxT", ""));

            txtNo    m      edaB      a sedeDa    dos.setText(t    xtNomedaBase          deDados.getText().replace(".doc"    ,  ""              ));
            txtNomedaBasedeDados.s   etText(txtNomed   aBasedeDados.getText().replace(".D      OC",        ""));
                   txtNomedaBasede       Da        d  os.se         tText(txtNomedaBas   edeD        ados.getText()   .replac e(".Doc", "")   )    ;
                   txtNomedaB  ase   deDados.setText(txtNomedaBasedeDados.getTe   x   t(  )    .  replace(".doC", "  "));
                    tx  tN ome    daBasedeDados.set      Text(txtN omed   aBa      se   d     eDad os.getTex    t   ().replace(".D   oc ", "   "));
              txtNome         daBasedeDados.setText(txtN      o            medaBasedeD ados.getText().replace(".doC",  ""));
            txtNomeda    BasedeDa           dos.setText(txtNome daBasedeDados.getText().replace(".D oC", ""));
                       txt Nomeda   BasedeDad  os.setText(txtNome         daBasedeDa     dos.getTe    xt().replace(".dOc", ""       ) );

                  txtNomedaB     asedeDado s.s             e    tTe   xt  (txtNome       daBasedeDados.g  e tTe    xt().rep    lace(".pdf",   ""));
                     txtNomedaBasedeD ados     .   setText(txtNomedaBasede   Dados.getText().rep   lace(".PDF", ""));
                      txtNomedaBase      deDados.  setText(txtNomedaBase  deDados.getText().rep    la        ce(".Pdf"   , "")); 
                   tx     tNomedaBase      de   Da dos.setText(txtNomed          a  BasedeDados.getText().r    eplace(".pdF", "" ));
              txtNomedaBasedeDa  dos .   se  tText(t  xtNomedaBasedeDados.getText().replac   e(".Pdf"          , ""));
             txtN  o        me        d aBase   deDados.setText(txtNomedaBasedeDados.      getTe   xt           ().r      eplace(".PDf", ""));
            txtNomedaBasedeDados.setText(t  x        tNomedaBasedeDados.getText().    replac            e(".PdF   ", ""))   ;
              txtNo       medaBasedeDado        s.setTe  xt(txt   No      medaBasedeDad     o         s.getText().repl     ace(".PdF",    ""))   ;       
            txtN omedaBasedeDados.s  etTex          t(t  xtNomedaB                 ased   eDados.getTe   xt().replac   e(    ".docx", ""));
             txtNomedaBasede        Dados.set  Text(tx    tNom  ed aB        asedeDado   s.ge      tText().replace(".             DOC      X"         , ""));

        }   else {
             try            {        
                        Conectar3();
                  Con    ectar2    ();
                         Conectar();

                   } catc h (IOException ex) {
                                             Logger.get   Logger(                            ConfiguracoesServidor.class  .ge tName()).log(Level.S       EVE  RE       , n            ull, ex);
                 }
        }
         }//GEN-L   AST :event_jB     utton1    Acti      onPer  fo      r m ed

        priva   te void jButto          n2ActionP  erformed(     java.   awt.event.ActionEv  ent ev    t) {//GEN-FIRST:event_jBu   tton         2ActionP      er   formed    
            String lin   ha;   
        St  ring linha2;
        String lin      ha3;
                                 try {
            FileReader a rq = new FileReade     r("../s    ee       d s-j     ava/bas    ededados.txt" );
            FileRead    er   arq    2 = n  ew FileReader("  ../seeds-java/ip.txt"  );
              FileReader arq3 = new Fi  le      Reader(    "../seeds-java/porta.txt");
            BufferedReader lerArq =         new Bu fferedRe ader      (arq);
                      Buff  e     redReader lerA   rq2    = new Buff     eredReader(ar  q2);
                B  ufferedReader    lerArq3              = new Buffered  Reader(a  r   q3) ;
               l   in    ha =           lerArq.rea   dLine(); /     / lÃª a pr  imeira linha
                     linha 2 = lerArq2 .readLine(); // lÃª a     primeira  li      n   ha   
             linha3 = l        erAr  q3.readLine()  ; // l   Ãª a p       rimeir a linha   
                 // a variÃ¡v   el "l         inha" rec ebe o     valor "nu        ll" q    uando   o proc   es  s o
              /      / de repetiÃ §Ã£o     a         tingir o final do a rquivo texto
             while (linha !=      null) {
                                        J  OptionPane.showMessageDialog     (rootPane,  
                                "Base      de dados Mys  ql em uso:   "
                            + "" + linha + " \n"
                        +    "End    ereÃ§o IPv4 em uso    : "
                              + "" + linh a2 + " \n"
                             + "Porta em uso:      "
                           + ""     + linha3 + "      ",
                             "", JOptionP   ane.INFORMATION_MESSAGE);
                txtNomeda      Basede  Da dos.set   Tex   t(linha);  
                     tx                 tEnderecoIPv4.setText(linh     a2);
                    txtPorta.s  etTe xt(lin   ha3);
                   linha = lerArq.readLi    n    e(); // lÃª da segunda atÃ© a       Ãºltima linha
            }
            arq.close();
        } catch (IO    Exception e) {
            Syst  em      .err.printf("Erro   na abertura do arq     u    ivo: %s.\n",
                                          e.getMessage());
        }
          }//GEN-LAS    T:event_jButton2ActionPerformed

        p   rivate vo i         d jButton3ActionPerforme      d(java.awt.ev      ent.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerfo    rmed
            Confi guracoesServidor.this.dispose();
    }/  /GEN-LAST:event_jButto           n3Ac      tionPerformed

    private void jBu    tton4     A ct    ionPerformed(java.aw   t.event.ActionEvent        evt) {//GEN-FIRST:event_jButton4ActionPerformed
              if     (txtNome       daBa   sedeDados.getText().contains(".")) {
            JOptio      nPane.showMessageDialog(rootPane,
                    "NÃ£     o info  rmar a exten         s Ã£o do arquivo.\n"
                        + "Inf orme apenas o nome d  a base de da     dos   a ser util  izada.\n"   
                    + "",
                         "Nome de arquivo invÃ¡lido", JOptio nPane.WA   RNING_MESSAGE);

                   txtNomedaBasedeDados.setText(txtNomedaBas edeDa  dos.getText     ().replace(".sql", ""));
            txtNomedaBased  eDados.  setText(txt     NomedaBasede   Dados.getText().replace(".SQL", ""   ));
              txtN  ome daBasedeDados.setTex  t(tx    tNomedaBased    eDados.getText().replac    e(".sQL  ", ""));
              txtNomed    aBase   de     Dados.setText(txtNomedaBasedeDados.getText().replace(".sQl", ""));
                      txtNomedaBasedeD   ados.setText  (txtNomedaBasedeDados.getText().replace(".sqL", ""));
            txtNomedaBasedeDados.set       Text(txtNomedaBasedeDados.getT   e      xt().replace(".SqL ", ""));
                txtNomedaBased eDados.setText     (txtNomedaBasedeDado    s.getT      ext().replace(".   Sql", ""   ));   
              txtNomedaBasedeDados.setText(txtNomedaBasede    Dados    .getText().repl  a    ce(".SQl", ""       ));

                 txtNomedaBasedeDados.s etText(txtNo  medaBas  edeDado  s.getText(   ).replace(".txt", ""));
                  txtNomedaBasedeDados.se   tTex    t(txtNomedaBasedeDados   .getText().    replace(".TXT",   ""));
              txtNo     m edaBasedeDado     s.setTex  t(txtNomed  aBasedeDad     os.getText().replace(".Txt",    ""));
            txtN      omedaBasedeDados.setText(tx      tNomedaBasedeDados.getText().replace(".TXt   ", ""));
                   txtNomedaBasedeDa    dos.setText(txt   NomedaBasedeDados.getText().replace(".tXT", ""));
            txt   Nom  edaBasedeDados.setText(txtNomedaBasede Dados.getText().replac      e              (".txT", ""));
                txtNom      edaBasedeDados.setText(txtNomedaBasedeD     ados.getText().replace(".tXt", ""));  
            txtNomedaBasedeDados       .   setText(txtNomedaBa        sedeDados.getText().replace(".T  xT", ""));

                  txtNome    daB        asedeDados.set  Text(txtNomedaBasedeDados.getText().replace(".doc", ""));
            txtN  omed aB asedeDados.setText(txtNomedaBasedeDados.get      Text(). replace(".DOC", ""));
             txtNomedaBasedeDados.        setText(txtN omedaBasedeD   ados.getText().replace(".Doc", ""));
                   txtNomedaB   asedeDados.setText(txtNo     medaBa    sed   eDados.getTe  xt().replace(".do C", ""));
            txtNo  medaBasedeDados.setText(txtNomedaBa sedeDados.getText().rep    lac       e(" .Doc", ""));
               txtN  omedaBasedeDad  os .setText  (txtNomedaBasedeDados.getText   (  ).replace(".doC", ""));
            t   xtNomed       aBasedeDados.setText(txtNomedaBasedeDado   s.getT  e     xt().    repla  ce(".DoC", ""));
                  txtNomedaBas  edeDados.setText(txt  NomedaBasedeDados.getT   ext().     rep    lace(    ".dOc", "" ));

                 txtNomed  aB      asedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdf", ""    ));
            txtNomedaBasedeDados.setT   ext(txtNomedaBa  sedeDados  .getText().replace(".PDF", ""));
            txtNom   edaBasedeDados.     setText(tx  tNo    medaBasedeDados.getText().replace(".Pdf", ""  ));
                        txt  N      omedaB  asedeDados.setText(txtNomedaBasedeDados.getText().replace(".pdF", ""));
                     txtNomedaBasedeDados.setText(txtNomedaBasedeDados  .getText().replac     e(".Pdf", ""));
                                 tx    tNomedaBasedeDados.setText  (txtNomed   aBas  edeDados.getTex  t().replace(".PDf", ""));
                  txtNomedaBasedeDados.setText(      txtNomedaBasedeDados.getText().replace(".P   dF", ""));
            txtNomedaBasedeDados.setT   ext(txtNomeda     Bas edeDados.getText().replace(".PdF", "     "));
               txtNomedaBase    deDados.setText(txt NomedaBased     eDados.getText().replac  e(".docx    ",     ""));
              txtNomedaBasedeDados.setText(   txtNomedaBasedeDados.getText().replace(".DOCX", ""));

                 txtEnderecoI   Pv4.setText(txtEnderecoIPv4.get Text().replace(".  .", "."));
            txtPorta.setT  ext(txtPorta.getText().replace  (":  ", ""));

        } else  {
            try {
                      fin aliza  r = 1;
                Conectar3();
                 Conectar2();
                  Conectar();
            }   catch (IOExceptio     n ex) {
                Logger.getLogger(ConfiguracoesServidor.      class.getName()).log(Level.SEVERE, nul l, e     x);
            }
        }
    }//GEN-LAST:event_jButton4ActionPe  rformed

    private      voi      d txtNomedaBasedeDadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomedaBasedeDadosKeyTyped
               txtNomedaBasedeDados.   setText(t      xtNomedaBas  edeDados.getText(  ).toString().toLowerCase());
     }//GE   N-LAST:event_txtNomedaBasedeDadosKeyTyped
   
    privat    e void txtNomedaB  asedeDadosFocusLost(java.awt.e vent.FocusEvent evt) {//GEN-FIRST:event_txtNomedaBas     edeDadosFocusLost
        txtNo   medaBasedeDados.setText(t   xtNomedaBasedeDados.getText().toSt  ring          ().toLowerCase());
       }//GEN-LAST:event_txtNomedaBasedeD   adosFocusLost

    /**
     * @param args the command line arguments
       */
            public s   ta  tic void m ain(String args [])  {
           /*
         * Set the Nimbus look and feel
             */
        /  /   < editor-fold defa    ultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /  *
         *  If Nimbus (introduced in Java SE 6) is not ava  ila    ble, stay    with the
         * defa   ult look and feel. For details see
         * http://download  .oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.Look    AndFeelInfo info : javax  .sw    ing.UIManager.getInstalledLookAndFeels()  ) {
                if ("Nim     bus".equals(info.getName())) {
                       javax.swing.UIManager.setLookAnd   Feel(in fo.  getC  lassName());
                        break;
                }
            }
        } catch (ClassNotFoundExce    ption ex) {
            java.util.logging.Logge  r.getLogger(ConfiguracoesServ  idor.class.getName()).log(java.util.l    ogging.Level.SEVERE, null, ex);
        } catch (Insta         ntiationException ex) {
                 java.util.logging.Logger.getLogger(ConfiguracoesServidor.class.getNam   e()).log(java.util  .logging.Level.SEVERE, null, ex);
        } catch (Ille    galAccessExc   eption ex) {
            j    ava.  util.logging.Logger.getLogger   (ConfiguracoesServidor  .class.getName()).log(java.util.log  ging.Level.SEVERE, nul       l, ex);
        } ca    tch (javax.swing.U  nsupportedLookAndFeelException ex) {
              java.util.lo gging.Logger.getLogger(Con   figuracoesServidor.class.getName()).log(java.util.logging.Level.SEVER      E, null, ex);
              }
        //</editor-fold>

        /*
             * Crea  te and display the dialog
         */
        java.awt.EventQueu     e.invokeLater(new Runnable() {

            public v   oid run() {
                   ConfiguracoesServidor dia log = new ConfiguracoesServidor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                     });
                //IF VAI PERMITIR ABRIR SO UM FRAME POR VEZ
                //if(dialog.isDisplayable() == false){
                dialog   .setVisible(true);
                //}
            }
        });
    }     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtEnderecoIPv4;
    private javax.swing.JTextField txtNomedaBasedeDados;
    private javax.swing.JFormattedTextField txtPorta;
    // End of variables declaration//GEN-END:variables
}
