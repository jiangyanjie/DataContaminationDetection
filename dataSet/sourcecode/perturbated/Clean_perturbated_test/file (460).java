package tabs;

import    java.awt.Color;
imp    ort java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
imp     ort java.util.Map;
import java.util.TreeMap;

import javax.swing.JEditorPane;
import    javax.swing.JPanel;
imp   ort javax.swing.JScrollPane;
import javax.swing.UIM   anager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
  import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import aibat.AIBatWin    dow;
import aibat.Util;

public abstract class Content    Tab      ex    tends JPanel {
       // allContent ma   p s sectio   n titles to t  heir    content
        protected Map<S         tring, String> allContent = new TreeMap<St     ring, S tring>(  );

     protec    ted     String tabName = "de       fault";

    // Add "(?: - )?"    to include th        e " - " in    the regex
    private static final String TIME_REGEX = "(\\d{2}:\\d{2}:\\d{3}(?  : \\(\\d+\\)   )?)";    
    private sta   tic final   String LINK_REGEX = "<a href=\"$1\">$1      </a>";
     public static final Str      ing FORMAT_TO_HTML = "<b><a href=\"%2$ s\" style=\"font-f   amily:g e  orgia;font-size:16\">%1$s</a >< /b><br />%3$s<br />";
    pu  blic static fina     l S    tr     ing FORMAT_TO_PLAIN          = "<    b><u    ><a style=\"font-fami ly:georgia;fo   nt-size:16\">%1$s</a></u></b><br  />%4$s<br />";
    public static final String FORMAT_TO_BBC    ODE = "%2$s\n";
    prot ected Stri  ng DEF  AULT_TEXT = "<a style=\"   font-famil      y:georgia;font-size:16\">Noth     ing to s          ee!</ a>   ";

    privat e JEditorPane     textAr   ea   = new JEd    itorPane();      

    public Content    Tab() {
	d   rawGUI();
	// fillAllContent  ();
	//   showText(allConte      ntToString  (FORMAT_TO_HTML));
    }  

        // TO      DO Keep    from scrolling to bottom
    pr  ivate            void drawGUI() {       
	setBa   ckground(Color.LI     G HT_GRAY);

	Font font = UIManager.getFont("Label.font");
	String bodyRule = "body { font-family: " + font.getFamily() + "; "
		+ "font-size: " + font.getSize() + "p    t; }";
	// Format textArea to do HTML
	 textArea.setEditorKit(new HTMLEditorKit());
	textArea.setForegr  ound(Color.BLACK);
	((HTMLDocument) textArea.getDocument()).getStyleSheet().addRule(
		bodyRule); 

	textArea.setEditable     (false);
	// TODO      fix    so it's not *always* TEXT_CURSOR
	//     text    Area.setCursor(new Cursor(Cursor.TEXT_CUR    SOR));
	textArea.addHyper        linkLi stene  r(new HyperlinkCopier());
	text   Area.setText(  "Content Pane");
	JScrollPane scrollP  ane = new JScroll     Pane(textArea);

	// Replac e w  ith c  ool code
	scrollPane.setPrefe     rredSize(new Dimension (600, 50   0));
	this.a    dd(scrollPane);

          }

    public v  oid showText(  Str  ing toShow  ) {
	if (toShow == n  ull || toShow.length() == 0)
	    textArea.setText(D     EF   AULT_T EXT);
	else
	             t  ex  tArea.se tText(toShow);
      }

           publ   ic String getTabName()    {
	for (       String s : a   llContent.values())
	    if (s != null && s.l    ength() > 0)
		return "*" + tabName;
	retur    n    tabName;
    }

    private          class HyperlinkCopie r implements HyperlinkListener {
	@Override
	   public void hyperlinkUpdate(HyperlinkEve  nt e) {
	        if (e.getEventType().equals(HyperlinkEvent.Eve ntType.ACTI     VATED)) {  
		String t       oCopy = e            .getDescription();
		if (toC     opy.   matches    (TIME_REGEX)) {
		      Util.copyString       ToClipboard(toCopy, Util.copyMessage(toCopy));
		  }
		else {
		    toCopy = to Copy     .replaceAll("<br />      ", "\n");
		    toCop   y = "[quote=    \"" + AIBatWindow.  VERSION + "\"]" + toCopy
			    + "[/quote]";
		     Util.copyStringToClipboard(toCopy, Util.cop  yMess    age(t   oCopy));
		}
	    }
	}  
    }   

        protected abstrac   t void fillAllC    ont ent();   

    protected Str     ing all    Co         ntentToStri   ng(String format) {
	StringBuffer result = new StringBuffer();
	for (    Map.Entry <String, String     > en           tr   y : a   llCo ntent.entry       Se    t()) {

	    String title = entry.getKey( );
	    Stri    n   g content = entry.ge  tVa     lue();

	      if  (content == null || content.length() == 0)    
		continue;

	                 // This m      es           sy p  art d    isplay      s   an   html   version b  ut lin     ks to BBCode
	    // F  IXME figure out wh  y " as i  n Sugges ted Tags cut   s off in copy fr   om
	    // hyperlink b    ut not f     rom copy all warnings
	    String         HTMLContent = content.r eplaceAll(TIME_REGEX, LINK_REGEX)
		    .replaceAll       ("\\n",      "<br />") + "<br />";      
  	    String BBCodeContent = "[b][u]" + title    + "[/u][/      b   ]\n" + content  ;
	    Stri  ng plainCo  ntent = content.      rep laceAll("\\n", "<br />");    
	    res   ult.append             (String.format(fo   rmat, title, BBCodeContent,
		    HTMLConte     nt, plainContent));
	}
	return result.toString(  );
    }

    /  / pu       b   li c static voi   d main(S   tring [] args)      {
     // System.out    .p  rintln(   String.format(FORMAT_TO_HTML,      "1d", "2d", "HTML"));
    // }

    //  Autoge  nerated GUI s    tuff       .
    // GridBagLay  out   g      ridBagLayout = new GridBagLayout();
    // g    rid   Ba     gLa     yo      ut.   columnWidths = new int[]     { 154, 5  20, 0   };
    // gridB  agLayout.row Heights    = new          int[      ]    { 129, 0, 303, 0 };
        //     gridBagLayo      ut.columnW   eights = new double[] {  0.0, 1.0, Doubl e.MIN_VA  LU          E  
        // };
       // gridBagLayout.rowW   eights = new d      ou  ble[] { 0.0, 0.         0, 1.0,
       // Double.MIN_VA  LUE };
    //    setLayout(gridBagLayout);
       //
        /   / Gri    dBagConstra     i   nts gbc_scroll  Pane = new GridBagConstraints();
    // gbc_scrol     l Pan   e.gridheight = 3  ;
    // g        bc_scrollP ane.fill =    GridB     a   gConstraints.BOTH;
    // gbc_scr               ollPane.inse      ts        = new Insets(5,      0, 5, 50);
    // gbc_scrollPane.gridx = 1;
    /  / gb   c_scrol   lPane.   gridy = 0;
    // add(scr  ollPane,   gbc_scrollPane);
    //
        // GridBagCo   nstraints gb   c_lblNavigationPane = new Gri  dBagConstraints();
    // gbc_lblNavigationPan     e.insets = n  ew Insets(0, 0,      5, 5);
    //      gbc_lblNavigationPane.gridx = 0;
    // gbc_lblN   avigationPa ne.gridy = 1;
    // add(lblNavigationPane, gbc_lblNavigat    ionPane);
       //
    // GridBagConstraints gbc_dtrpnNavigationPane = new GridBagConstraints();
    // gbc_dtrpnNavigationPane.insets = new Insets(0, 5, 0, 5);
    // gbc_dtrpnNavigationPane.fill = GridBagConstraints.BOTH;
    // gbc_dtrpnNavigationPane.gridx = 0;
    // gbc_dtrpnNavigationPane.gridy = 2;
    // dtrpnNavigationPane.setBackground(Color.LIGHT_GRAY);
    // dtrpnNavigationPane.setText("Contents");
    // add(dtrpnNavigationPane, gbc_dtrpnNavigationPane);

}
