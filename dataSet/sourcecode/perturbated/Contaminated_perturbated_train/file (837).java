package    tabs;

imp      ort java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimensio  n;
import java.awt.Font;
import java.util. Map;
import java.util.TreeM ap;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrol   lPane;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import   javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLD  ocument;
import javax.swing.text.html.HTMLEditorKit;

import       ai  bat.AIBatWindow     ;
import aibat.U  til;

publ   ic abs     tract class ContentTab extend        s JPanel {
    //       allConte nt maps sect  ion title s to their content
     protected Map<S  tring,    String> allContent = n    ew TreeMap<String, String>();
  
    prote     cted String ta    bN   ame        = "def      ault";

      // Add "(?    : -    )?" to i     nclude the     "   - " in the regex
                pri     vate static final      Str     ing TIME_REGEX = "(\\d     {   2}:\\d{2}:\\d{3}(?: \\ (\\d+\\))?  )";
      private static     final St      ring LINK_REGEX =     "<a href=\"$1\"> $1</a>";
    public static       final String FORMAT_T  O_HTML = "<b><a href=\"%2$s\"       style=\"font-family:georgia;font-size:16\">%1$s</a></b>< br />%3  $       s<br />";
    public static final String FORMAT_TO_PLAIN    = "<b ><u><a style=\"f   ont- family:georgia;font-size:16\">%1  $s    </a></u></b><br />   %4$s<br />";
        public static final String FORMAT_TO_BBCODE = "   %2$s\n";
      protected String   DEFAULT_ TEXT = "<a style=\"font-   family:  georgia;font-s  ize:16\">Nothing to see!</a>";
   
    private JEditor   Pane   t    extArea = new JEditorPane();
      
    pub  lic ContentTab() {
	drawGUI();
	// fi   llAllContent();
	// showTe xt(allContentToStrin   g(FORMAT_T    O_HT  ML));
    }

    // TODO Keep from sc ro    lling to     bot    tom
    private void drawGUI() {
	setBackground(Color.LIGHT_GRAY);

	F      ont font = UI       Manager.getFo    nt("Label.font");
	String bodyRule = "body { font-fam ily: " + font.getFamily() + "; "
		+ "font-size: " + font.getSize() + "pt; }";
	// F   ormat text  Area to do HTML
	textArea.setEditorKit(new HTMLEditorKit());
	textArea.s    etForeground(Color.BLACK);
	((HTMLDocument) textArea.getDocument()).getStyleSheet().addR        ule(
		bodyRule);

	  textArea.setEditable(false);
	// TODO fix so it's not *always* TEXT_CURSOR
	// textArea.setCur      sor(new C urs         or(C   urs   or.TEXT_CURSOR));
	textArea.addHyperlinkListener(ne    w HyperlinkCopi    er());
	textArea.s    etText("Conten  t Pane");
	JScrollPane scr      ollP     ane = new JScrollPane(te        xtArea);

	//      Replace with cool co        de
	scrollPane.setPreferredSize(new Dimension(600, 500));
	thi   s.ad d(sc  rollPan       e);

    }    

       p   ub  lic void showText(String toSho   w) {
	if (toS   how      ==   null || toShow.length() == 0)
	     textArea.setTe    xt(DEFAULT_TEXT); 
	else
	    t    extA  rea.se      tText(toShow);
    }

    pu    blic String getTabName() {
	fo   r      (S     tr  ing s : allCon     te       nt.values())
	    if (s != null && s.length() > 0)
		return "*"  + tabName;
     	return   tabName;
        }

    private class HyperlinkCopier implements HyperlinkListener {
	@Override
	pu      blic   void hyperlinkUpdate(HyperlinkEvent e)   {
	       if (e.getEven      tType().equals(Hyperl    inkEvent.EventTyp           e.ACTIVATED))     {
		String toCopy     = e.getD      escription();
		    if     (toCopy.ma     tches(TIME_REGEX)) {
		         Util.copyStringToClipboard(to    Co  py, Util.co    pyMessage(toCopy));
		}
		else {
		    toCopy = toCopy.rep   laceAll("<br />", "\n");
		      toCopy = "[quote=\""       + AIB  a   tWindow.VERSION + "\"]" + toCopy
			    + "[  /quote]";
		    Util.copySt   ri   ngToClipboard(toCopy, Util.co      pyMessage(toCopy));
		}
	        }  
     	}
     }
  
    protected        abstract void fillAllCont ent();

    pr  otecte  d String al        lCont  entToString(String      format) {
	Str     ingBuff    er    result = new StringBuff er();
	for (Map.E     ntry<Stri   ng, S   t ring> e nt     ry : a   llContent.entrySet()) {

	    String    title = entry.    getKey()   ;
	           Str    ing content = entry.getValue();

	        if   (cont     ent == nul    l || content.length() == 0)
		cont  inue;

	      // This    messy part disp  la   ys an html ver  sion     bu  t    links to BBCode
	    //  F     IXM              E     figure out why     " as in Suggest ed Tags cuts off in cop y from
	    //   hyperl   ink but not   fr om copy     all warnin      gs
	    String HTMLContent = conte    nt.replaceAll(TIME_  REGEX, LINK_REGEX)
		    .repla  ceAll("\\n", "<b    r     />   ") + "<br />";
	           Strin    g BBCodeCo     nten   t = "[b][u  ]" + title + "[/u][/b]\n " + content;
	    String plainContent =       content.replaceAll("  \\n", "<      br />");
	    res        u    lt.append(String.f   ormat(forma       t, tit       le, BBCodeConten t,
		    HTMLContent, plainContent                ));
	}
	     r etur          n result.toString();
    }

       // public static   vo   id main(Strin      g[] args) {
    // Sy    stem.out.   p       rintln(Stri  ng   .format   (F ORMAT_TO_HTML, "  1 d", "2d     ", "HT  ML     "));
    // }

        // Autogenerated GUI       stuff.
    // Gr    idBagLay      out g  ridBagLayout = new GridBa     gLayout();
    // gridBagLayout.co lumnWidths = new int[] { 154, 520, 0 };
      // grid  BagLayou     t.rowHeights = new in  t[] { 129, 0, 303, 0 };
     // g ridBagLayout.columnWeights = n  ew       double[] { 0.0, 1.0, Do     uble.MIN_VALUE
    // };
     // gridBa    gLayout.rowWeigh   ts     =   new double[] {    0.0   , 0  .0,   1.0,
    // Double.MIN_VALUE };
       // setLayou   t(gridBagLayout);
    //
    // GridBagConstraints gbc_scrollPane = new GridBagCo  nstraints      ();
    /    / gbc_scrollPane.grid height = 3;
        // gbc_scrollPane.       fill = G   ri dB        agConstrain   t   s.BOTH;
    // gbc_scr      ollPane.in  sets = new Insets(5      , 0, 5   , 50);
    // gbc_scrollPane.gridx = 1;
    // gbc_scrollPan   e.gridy = 0;
    //  add(scrollPane, gbc_scrollPane);
    //
    // GridBagConstraints gbc_lblNavigationPane = new GridBagConstra   ints();
    //      gbc_lblNavigationPane.insets = new Insets(0, 0, 5, 5);
    // gbc_lblNavigationPane.gridx   = 0   ;
    // gbc_lb  lNavigation Pane.gridy = 1;
    // add(lblNavigationPane, gbc_lblNavigat      ionPa  ne  );
    //
    // GridBagConstraints    g        bc_dtrpnNavigationPa   ne = new GridBagConstraints();
    // gbc_dtrpnNavigationPane.insets = new Insets(0, 5, 0, 5);
    // gbc_dtrpnNavigationPane.fill = GridBagConstraints.BOTH;
    // gbc_dtrpnNav  igationPane.gridx = 0;
    /     / gbc_dtrpnNavigationPane.gridy = 2;
    // dtrpnNavigationPane.setBackground(Color.LIGHT_GRAY);
    // dtrpnNavigationPane.setText(  "Contents");
    // add(dtrpnNavigationPane, gbc_dtrpnNavigationPane);

}
