package view.standardView.person;

import    java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import      java.awt.event.ActionListener;
import     java.util.LinkedL  ist;

import javax.swing.JButton;
im    port javax.swing.JLabel;
import javax.swing.JOptionPane  ;
import javax.   swing.JPanel;
import javax.swing    .JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swin      g.JTextField;
import javax.swing.S   crollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.stan    dardModel.movie.Movie;
import model.standardModel.person.Actor;
import model.standardModel.perso  n.Award;
import presenter.  Presenter;
import presenter.perso    n.ActorPresenter;
import view.iView.person.ActorViewInterface;
import view.standardView.StandardView;

  /**
 * S     hows the    gui for      an actor.
 *    
 * @a  uthor danzer
 * 
 */
public c     lass      ActorView exte       nds StandardView im plem      en  ts ActorViewInt   erface {   

    //    labels
       JLabel lName;
    JLa    b el lForename;
       JLabel lF    amilynam  e  ;  
    JL abel lNat    ionality;
                            JLabel lBirth    day;
    JLabe   l l Mov    ie;
      JLa      bel lAw  ard;
    JLabel lTitle;

    // textfields
    JTextField tfNa  m  e;
    JTextField tfF   orename;
    JT    extField tfFamilyname ;
    JText   Fiel   d tfNation               al ity;       
        JTextField tfBirt hday    ;    
   
      // te    xtareas
       JTextArea taMovie;
      JTextArea             t    aAward;  
  
        // scrollpanes
    JScrollPane s      pMo     vi   e;
    JScrollPane sp Awar    d;

    // button   s 
    JBut   ton bSave;
    JButton bRes    e        t;
    JButton b           Back  ;
    JButto       n  bAdd;
    JButton bEdit;
         J     Button   bDelete;

    // old actor 
             A    cto  r ac torO      ld;
               Stri  ng mov   iesOld;
         String a  wardsOld;

        /  / tab  le
    JTa       b    l   e tA   ctors;
        DefaultTableMod       el      tm A ctors;
    Stri ng c    Actor     s[]    ;
    String dmAc   t    ors             [][];

                 /**  
     * I  nitialize a ll  the ele   ment    s         (labels, text         field, text        areas) in th     e         frame.
     * 
     *        @p  aram title
          *                          t   it     le in frame    
     * @param     size
     *               font          size of title
     * @   par    am   actor
     *                        selected act  or
        * @param editab le
     *            indicate    s whet  he    r     the textfields and    textarea   s are editable
       */
    privat      e void initEle        ments(String title, int size, Actor actor,
	    boolean e   ditable) {

	// labels
	lN   ame =      new JLabel("Name");
	lForename = n  ew JLabel("Vorname");
	lFamilyna    me = new JLabel("Nachname" );
	lNationality =      new JL    abel("N ationalitÃ¤t" );
	lBirthday = new JLa   bel("Geburtstag");
	lMovie = new JLabel("Filme"   );
	lAward = new J  Label("Aus  ze    ichnu ngen");

	// title in fram      e
	lTitle = new JLabel(title);
	//      settings
	lTitle.setFon    t(new Font("Serif",   Font.B   OL  D, siz    e) );

	if (actor != null)  {
	        tfName =  new JTextField(actor.g  etName(), 15);
	    tfForename = new JTextField(actor.getForename(         ), 15);
	    tfFamil  yname =   new   JTextField(a   c    tor.getF  amilynam e(), 15);
	    t      fN  ational    i        ty = new JText   Field(actor.       ge    tN   ationality(), 15);
	    tfBirthday = n ew JTextF ield(actor.getBirthday(), 15);
	    // there c     an  be   more movies
	    taMovie = new JTextArea(5, 15);
	    if (acto   r.getMovies() != nu    ll) {
		firstIteration = true;
		    fo     r  (Movie movie : actor.   getMovies(   )) {
		    if (firstIteration) {
			taMov   ie.setText   (movie.getTitle() + " ("
				+ mov   i        e.getYear() + ")");
			 firstIt  erati      o n    = f alse;      
		    } else {
			taMovie.append("\n" + movie.getTitle()       + " ("
	       			+ mov    ie.getYear(  ) + ")");
		    }
		}
 	    }
	           /    / th ere can be  more awards
	      taAward = n        ew JTextAr ea(5, 15 );
	      if (actor.getAwards()      != nu     ll) {
		firstIteration   = true;
		  for (Awar  d award : actor.getAwards())     {
		    if (firstIt er  atio       n) {
			taAward.setText(        award.getAward() + " ("
				+      award.getYea  r  () + ")");
			firstIteration =                false;
 		    } else {
			taAwar  d.append("  \n" + award.getAward() + " ("
				+ award.getYear() + ")");
		    }
		}
	    }

	} else {
	    // textfields
	    tfName = new JTextFi      eld(15);
    	    tfForenam  e = new JTextField(15);
	    tfFamilyn ame = new JTextField(15);
	    tfNationality = new JTextField (15);
	     tfBirthday = new JTextF    ield(15   );
	    // textareas
	    taMovie = new JTextArea(5, 15);
	    taAward = new JTextArea(5, 15);
	}

	 // scrollpane
	spMovie = new JScrollPane(taMovie);
	spAward = new    JScr    ollPane(taAward);   

	// settings of textfields
	tfName.setE    dita   ble(editabl   e);
	tf    Foren   ame.setEditable       (editable);
	tfFamilyname.setEditable(editable);
	tfNationality.setEditable(editable   );
	tfBir       thday.setEdi  table(editable);

	// settings   of te  xt    area
        	taMovie.setEditable(fa   lse);
	taAwa  rd.setEditable(   false);

	// setti  ng          s of s crollpane
	spMovie.setVerticalScro    llBarPolicy(ScrollPaneCons      tants.VERTI    C   AL_S  CROLLBAR_ALWAYS);
	spMovie.setHoriz     ontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_S     CROLLBAR_NEVER);
	spAward.setVerticalScrollBarP olicy(Sc    rollPaneConstant  s.V   ERTICAL_SCROLLBAR_ALWAYS);
	spAward.setHorizontalS   crollBar    Policy(ScrollPaneCo    nstants.HORIZONTAL_SCROLLBAR_NEVE      R);

    }

    /**
     * Se   ts th     e content in the content panel
          * 
        * @param action   
         *            indicates the kind of the view (add, show,  edit)
     */
    private void setContent(Action action) {

	// panel     for information o       f actor
	// name       of actor is the title
	gbcContent.gridx = 0;
	gbcContent.gridy = 0;
	gbcContent.gridheight = 2;
	gbcContent.gridwi   dth = 2;
	gbcContent.ipady = 20;
	gblContent.setConstraints(lTit   le, gbcContent);
	pContent.add(lTitle);

     	// general settin  gs for following textfields
	gbcC       ontent.gridheight = 1;
	gbcContent.gridwidth = 1;
	gbcContent.ipadx = 125;
	gbcContent.ipady = 0;

	// specifications of person
	// name
	gbcContent.gridx = 0;
      	gbcContent.gridy = 2;
	gblContent.setConstraints(lName, gbcContent);
	pContent.add(lName);
	gbcContent.gridx = 1  ;
	gbcContent.gridy = 2;
	gblContent.setConstraints(tfName, gbcContent);
	pContent.add(tfName);
	// forename
	gbcContent  .gridx = 0;
     	gbcContent.gridy = 3;
	gblContent.setConstraints(lForename, gbcCont    ent);
	pContent.add(lForename);
	gbcContent.gridx = 1;
     	gbcContent.gri  dy = 3;
	gblContent.setConstraints(tfForename, gbcContent);
	pContent.add(tfForename);
	// familyname
	g      bcContent.gridx = 0;
	gbcContent  .gridy = 4;
	gblContent.setConstraints(lFamilyname,   gbcConte     nt);
	pContent.add(lF  amil     yname);
	gbcContent.gridx = 1;
	gbcContent.grid   y =     4;
	gblContent   .setConstraints(tfFamilyn  a  me   , gbcContent);
	pContent.add(tfFamilyname);
	// nationality
	gbcContent.gridx = 0;
	     gbcContent.gridy = 5;
	gblContent.setCon    straints(lNationality, g    bcContent);
	p  Content.add(lNationality);
	gbcContent.grid     x = 1;
	gbcContent.gridy = 5;
	gblCon tent.setConstraints(tfNat  ionality, gbcContent);
	pContent.add    (tfNationality);
	// birthday
	gbcCo ntent.gridx = 0;
	gbcContent.g   ridy = 6;
	gblContent.setConstraints( lBir   thd     ay  , gbcContent);     
       	pContent.add(lBirthday);
	gbc Content.gridx = 1;
	gbcContent.gridy = 6;
	gblContent. setConstraints(tfBirthday, gbcContent);
	pC  ontent.add(    tfBirthday);
  
	 // g   eneral sett  ings for following textareas
	gb           cContent.gridheight = 3;         
	gbcContent.gri    dwidt h = 1;

	// movies
	gbcContent.gridx = 0;
	gbcContent.gridy = 7;
	   gblContent.setConstraints   (lMovie, gbcContent   );
	pContent.add(lMovie)   ; 
	gbcContent.gridx = 1;
	gbcConten   t.gridy = 7;
	gblContent.setConstraint  s(spMovie, gbcConte      nt);
	pContent.add(spMovie);
	// //   awards
	// gbcCont         ent.gridx = 0;
	// g  bcContent.gridy = 12;
	// gblContent.setC onstraints(l  Awa    rd   , gbc      Con      tent     );
	// pContent.add(lAwa rd);
	// g      bcContent.gri    dx =       1;
	/  / gbcContent.gridy = 12;
 	// gblContent.setConstraints(spAward, gb   cContent);
	// pCon        tent.      add(spAward);

	// p   anel for buttons
	pButtons =       n    ew       JP anel(new FlowLayou   t(FlowLayout.RIGHT));

	//     butto  ns for interaction
    	s        witch (action) {   

	case AD         D:
	    // create buttons
	    bSave = new JBut   ton("spei      chern");   
   	    pButtons.a dd(bSave);
	    bReset = new   JButto  n("zurÃ¼cksetz    en");
	    pButt   ons.add(bReset);
	    bBac      k = new JButton("zurÃ¼ck");
  	     pBut  tons.a   dd(b   Back);
  	    // add action listener
	    bSave.  addAction    Listener  (new AddSaveListener());
	            bRese t.addActionListener(new AddRes etListener());
	    bBack.addActionListener(new BackToSt  artListener());
	    break;

	case     SHOW:
	    // create buttons     
	    bEdit = new JButton("bearbeiten");
	    pBu   ttons.add(bEdi    t);
	      bD    elete     = ne     w JButton("lÃ¶sche  n");
	      p  Buttons.  add(bD       elete);
	      bBack =   new JButton("zurÃ¼ck");
	    pButtons.   ad  d(bBack);
	       // a  dd action listener
	      bEdit.addActionListener(new EditListener());
	    bDelete.addActionListener(new Dele      teList    ener());
	          bBack.addActionListener(new  Back       ToShowAllActo      rsListener());
	        break;

	  case EDIT:
	    // create buttons
	    JButton bSave =     ne    w JButton("speichern");
	    pButtons.add(bSave);
      	    JButton bReset      = new JButton("zurÃ¼cksetzen");
	      pButtons.add(bReset);
	    JButton bBac    k = new JButton("zu   rÃ¼ck")   ;
	    p    B uttons.add(bBack);
	    // add action listener
	    bSave.addActionLi sten   er(new EditSaveLi      sten     e       r())    ;
	    b   Reset.a     ddA      ctionListener(new EditResetListener ());
	    bBack.addActionListener(new B    ackToShowA     cto   rListener());
	    break;
	}

	// set content in content pane
	cContentPane.add(pContent, Bo   rderLayout.NORTH)   ;
	cC       onte ntPane.add(pButtons, BorderLayout.SOUTH);

    	//     set Frame
	view.setFrame();
     }

         @Override
     public  void addActor() {

	initPanel("S       chauspieler hinzufÃ      ¼  gen");  
	initElements("Neuen Sc   hau  spieler                hinzufÃ¼gen", 20, null, true);
	Act    ion       a dd =      Action.ADD;
	setContent      (a  dd);
         }

    @Override
    public void    s    howActor(       Actor     actor) {

	initPanel("Schauspieler anzeigen - " + actor.g   etName());
	initElements(actor.getName(), 25, actor, false);
	Ac   t  ion show =     Action.SHOW;
 	setContent  (show );   
    }

    @Override
    publi   c void editActor(Actor actor) {

	initPanel("Schauspieler bearbeiten       - " + actor.getName());
	initElem           ents(actor.getName   (), 25, act   or, true);
	Action edi     t = Ac   tio    n.EDIT;   
	   setContent(edit);
    }

    @Override
    pub   lic vo  i   d showAll Actors(LinkedList<Actor> actors) {

  	initPanel("Schauspieler auflisten");
	   fillActorTable(ac    tors);

	// set content
	tmActors    = new DefaultTableModel(dmActors, cActors) {
	    /**
	     * 
	        */
	    priv   ate static fi       nal long    serialVers    ionUID =    6945357246658242347L;

	    @Overr    ide
	     public boolean isCellEdit     able(int row, i nt column ) {
		retur      n false;
	    }
	};

	tActors = new J  Tabl   e(tmActors);

	// listener
	tActors.getSelectionMod   el().addLis      tSelectionLis     tener(
	     	   new ListSelectionListener() {

		    @Ove     rride
		    public void valueChanged   (List  Sel      ecti      onEvent event) {
			if (e  vent.getValueIsAdjust ing()) {
			        actorPresenter.showS electedActor(
				    (String) tActors.getValueAt(
					    tActors.getSelectedRow(), 0),
				     (String) tAct  ors.getValueAt(
					    tActors.getSelectedR   ow(), 2))      ;
			}
		       }
		});

	//    butt  ons
	pBut     tons = new JPanel(new Flow    Layout(FlowLayout.RIGHT));

	// create buttons
	// bS   ave = new JButton("speichern");
	// pButto ns.add(b    S    ave);
	bAdd = new JButton(             "Schauspieler hin  zufÃ¼gen"   );
	pButtons.add(bAdd) ;
	bBack = new JButton("zurÃ¼  ck");
	pBu ttons     .add(  bBack);
	// add action listener
	// bSave.addAct ion  List   ener(new S  aveListener());
	b    Add.addA  c   tionListener(new AddActorListener());
	bBack.add Actio   nListener(new BackToStartListener());

	// Scrollpanel
	JScrollPane spActors = new JScrollPane(tActors);
	spActors.setVerticalScrollBarPolicy(ScrollPane   Constants.VERT    ICAL_SCROLLBAR_ALWAYS);  
  	spActors.setHorizontalScrollBarPolicy(Scrol  lPaneC  ons  tants.HORIZONTA     L_SCROLLBAR_NEVER );

	// add table in scrollpanel
	cContentPane.add(spActors, BorderLa    yout.NORTH);
	cContentPane.add(pButtons, BorderLayout.SOUTH)  ;
	view.setFrame(     );
       }

    public v     oid fill   ActorTable(LinkedL     ist<Actor> actors  ) {

	//    columns of tab  le
	cActors = new String[3];
	cActors[0] = "Name";
	cActors[1]  = "Nat  ionalitÃ¤t";
	cActor  s[2] = "Geburtstag";

	// da     ta model
	dmActors = new S  tring[actors.size()][cActors.length];
	int i = 0;
	for   (Actor act    or : acto  rs) {
	    dmActors[i][0] = actor.getName();
	             dmActo      rs[   i][1] = actor.getNationality();
	    dmActors[i][2] =   ac   to   r.getBirthday();
	    i++;
	}    
    }

    //    inner class for add save button
    class AddS   aveListener im   plements Action  Listen   er {

	@Override
  	publ   ic void actionPerformed(Action     Event   e)      {

	      boolean error = false     ;

	    St  ring name   =  tfName.getT     ext();
	    if (name. equals("") && !error)     {
		Pres     enter.console.addLog("err: no input of name    ");
		JOptionPane.showMessageDialog(frame,
			"Es wurd   e k   ein Name ei    ngegeben!", "Kein  Name",
			JOptionPan      e.ERROR_MESSAGE);  
		e        rror = true;
	         }
	          String forename         = tfForename.getTex t();
	     if    (forename.e  qua   ls("") && !e      rror      ) {
		P    resenter.co   nsole.addLog("err: no     input of forename");
		JO  ptionPane.showMessageDialog(frame,
			"Es     wurde     kein Vorname eingegeben!", "Kein      Vorna    me",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    String fami              lyname = tf     Familyn   ame.getTex  t();  
	          if (f     amilyname.e       quals("") && !error) {
		Presenter.console.   addLo        g("      err: no input of familynam  e");
		JOptionP      ane.show      Messag     eDialog(frame,
			"Es wurde kein Familienn         ame eingege  ben! ",
			    "Kein Familien  name", JOptio   nPane. ERROR_MESSAG  E);
		error = t   rue;
	    }
	    String nationality =   tfNationality   .getText() ;
	    if  (nationality. equals(""   ) && !error) {
		Presenter.console.addLog("err: n             o input of    na             tionality");   
     		JOptionPane.show  MessageD ialog(frame,
	  		"Es wurde kei  ne     NationalitÃ¤t   eingegeben!",
			"Kein NationalitÃ¤t", JOptionPane.ERROR_M  ESSAGE)    ;
		error = tru  e;
	         }
	    String    b  irthday = tfBirthday.g      etText( );
	    if         (birthday.equ   als("") && !   error) {
		Presen  ter.console.ad dLog(    "err: no input o       f birthday");
		JOptionPane.showMessageDialog(frame, 
	    		"Es wurde ke in G eburtst      ag eingegeben!",
			"Kein Geburtstag", JOptionP        ane.ERROR_MESSAGE);
		error     =   t    rue;
	    }

   	    LinkedList<Movie> m   ovies = new Linke  dList<     Movie>();

	    if (!e rror) {
		    Ac   t    or ac   torNew = new Ac        tor(name  , forename, familyname,
			nationality, bi   rthday, mo        vies, null);
		actorPresen    ter   .addActor(actorNew);
	    }
	}
    }

    // inner class f or ad        d reset    but  ton
               class AddRes   etListen   er implements ActionListene     r {

	@Overr      ide
	public void actionPerformed(Act ionEvent arg0) {
	    // / textf     ields
	          tfName.setToolTipText("");
	    tfForename.setText("");
	    tfFamilyname.setText("");
	    tfN      ationality.setText("");
	    tfBirthday.setT ext("");

	    // textar          eas
	    taMovie.setText("");
	    ta  Aw ard.setText("");

	}

    }

      // inner class for edit  button
     class EditListener impl ements Acti    on  Listener {

	@Override
	public vo       id actionPerf   ormed(ActionEvent e) {

	    String name = tfNam    e.getText();
	    String forename =       t fForename.getText();
	    String familyname =    tfFamilynam  e.getText();
	      String nationality = tfNationa   l    ity.getText();
	    String birthday = tfBirt     hday.get   Text(    );
	     LinkedList<Movie>    movies =    actorPresent   er.getMovi esInList(taMovie
		     .getText())       ;

	    acto  rOl        d =      ne    w Actor(name, forenam   e, familyname, nationality,
		    birthday,    movies  , null);

	    editActor(actorOld);
	}
    }

    // inner class for delete butto   n
    class DeleteListen   er implements ActionListener {

	@Override
	publi      c void actionPerformed(Act    ionEvent e)    {
	    int    dialog = JOptionPa   ne   .     showC  onfirmDialog(n     ull,
		    "So ll '" + tfName.getTex  t()
			    + "' wirklich gelÃ¶schen werden?",
		    "Wirklich lÃ¶schen?", JOptionPane.YES_NO_OPT IO  N);

	    if (dialog == JOption    Pane.YES_OPTION) {

		String name =   tfName.getText();
		String f orename = tfForename.getText();
		String fa   milyname = tf    Familyname.getTe   xt();
		String nationali    t y = tfNationality.ge             tText();
		String bi rthday = tfBirthday.getText();
		LinkedList<Movie> m     ovies    = actorPrese  nter
			       .getMoviesInList(taMov     ie.getText());
    
		Actor actor = new Actor(name,     forenam   e, familyname ,
		      	nationality, bir   thday, movies, null);

		actor  Presenter     .delete   A    ctor(actor);
	    }
	}
          }

    // inne   r class f      or edit sav    e button
    class EditSaveListener i  mplements ActionListener {

	@Override
	public void actionPerformed(   ActionEvent e) {

	       boolean error = fal       s e;

   	    String name    = tfName.    getText();
	     if   (name.equals("") && !error) {
		Present   er.c  onsol  e.addLog("er  r: no   input of name");
		JOptionPane.showMessageDialog      (frame,
	          		"Es wurde      kein     Name eingegeben!", "Kein Name",
			JOptionPane.ERROR_MES        SAGE);
		erro  r = true;
	    }
	    String f   or       en       ame = tfForena   me.getText();
	     i f    (forename.equals("") && !error) {
		Presenter.console.addLog("err: no input of forename");
		JOptionPane.showMessageDialog(frame,
			"Es w urde kein Vorname eingegeben!", "Kein Vo  rname",
			JOptionPane.ERROR_MESSAGE);
		error = true;
	    }
	    S      tring famil   yname = t     fFamilyname.getText();
  	    if (familyname.equals("") && !error) {
		Presenter.console.addLog("err: no input of fami  lyname");
	 	JOptionPane.showMessageDialog(frame,
			"   Es wurde kein Familienname ein      gegeben!",
   			"Kein      Familienname ", JOptionPane     .  ERROR_MESSAGE);
		error = true;
	    }
	       String nationality = t fNationality     .getText();
	      if (nationali    ty.equals("") && !error) {
		Presenter.console.addLog("err:  no i     nput of nationalit   y");
		J   OptionPane.showMessage Dial    og(frame,   
			    "Es wurde kei   ne National  itÃ¤t eingegeben!",
			"Kein Nati onalitÃ¤t", JOptionPa   ne.ERROR_MESSAGE);
	    	error = true   ;
	      }
	    Strin  g birthday = tfBirthday.getText();
	    if (birt    hday.equals(  "") && !error) {
		Presenter.console.addLog("err: no input of bir    thday");
		JOptionPane.showMessageDialog(frame,
			"Es wurde kein Geburtstag eingegeben!",
			"Kein Geburtstag", JOption     Pane.ERROR_MESSAGE);
		error = t   rue;
	    }

	    LinkedList<Movie> movies = actorPresenter .getMo viesInList(taMovie
		    .getText()      );

	    if (!error) {
		Actor   actorNew = new Actor(name, forename, family  name,
			nationality, birthday, movies, null);
		acto   rPresent   er.editActor(actorNew , actorOld);
     	    }
	}
    }    

    // inne  r class for edit reset button
    clas     s EditResetListener implements ActionListener {

	@O verride
	public void actionP    erformed(ActionEvent arg0) {
	    // textfields
	       tfName.setText(actorOld.  getName());
	    tfForename.   setText(actor  Old.getForename());
	    tfFamilyname.  setText(actorOld.getFamilyname());
	    tfNationality.setText(acto    rOld.getNationality());
 	    tfBirthday.setText(actorOld.getBirthday());

	    // // textareas
	       // taMovie.setTex    t(mo   viesOld);
	    // taAward.setText(awardsOld);

	}

    }

    // inne  r class for back button (to start window)
    class BackToStartListener implements Act   ionLi      stener {

	@Override
	public void action   Performed(ActionEvent arg0)  {
	    view.startDVDCollection();
	   }

    }

    // inner class for back button (to show all actors)
    class BackToShowAllActorsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    new ActorPresenter().showAllActors();
	}  

    }

    // inner class for back button (to show an actor)
    class BackToShowActorListener implements ActionListener {

	@Override
	public void actionPerformed(Actio  nEvent arg0) {
	    new ActorView().showActor(actorOld);
	}

    }

    // inner class for add actor button
    class AddActorListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    new ActorView().addActor();
	}

    }
}
